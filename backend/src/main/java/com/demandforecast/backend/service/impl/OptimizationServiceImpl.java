package com.demandforecast.backend.service.impl;

import com.demandforecast.backend.entity.Inventory;
import com.demandforecast.backend.entity.Product;
import com.demandforecast.backend.entity.ReorderRecommendation;
import com.demandforecast.backend.enums.RecommendationStatus;
import com.demandforecast.backend.enums.StockStatus;
import com.demandforecast.backend.enums.UrgencyLevel;
import com.demandforecast.backend.repository.InventoryRepository;
import com.demandforecast.backend.repository.ProductRepository;
import com.demandforecast.backend.repository.ReorderRecommendationRepository;
import com.demandforecast.backend.repository.SaleRepository;
import com.demandforecast.backend.service.OptimizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class OptimizationServiceImpl implements OptimizationService {

    private static final Logger log = LoggerFactory.getLogger(OptimizationServiceImpl.class);
    private static final double SERVICE_LEVEL_Z = 1.65; // 95% service level

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ReorderRecommendationRepository reorderRecommendationRepository;

    public OptimizationServiceImpl(InventoryRepository inventoryRepository,
                                   ProductRepository productRepository,
                                   SaleRepository saleRepository,
                                   ReorderRecommendationRepository reorderRecommendationRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.reorderRecommendationRepository = reorderRecommendationRepository;
    }

    @Override
    @Transactional
    public void recalculateInventoryOptimization(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return;

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseGet(() -> Inventory.builder()
                        .product(product)
                        .currentStock(0)
                        .build());

        List<Object[]> dailyData = saleRepository.findDailySalesQuantityByProductId(productId);

        double avgDailyDemand = 2.0;
        double stdDevDemand = 1.0;

        if (dailyData != null && !dailyData.isEmpty()) {
            double sum = 0.0;
            for (Object[] row : dailyData) {
                Number qty = (Number) row[1];
                sum += qty.doubleValue();
            }
            avgDailyDemand = Math.max(0.5, sum / Math.max(1, dailyData.size()));

            double varianceSum = 0.0;
            for (Object[] row : dailyData) {
                Number qty = (Number) row[1];
                varianceSum += Math.pow(qty.doubleValue() - avgDailyDemand, 2);
            }
            stdDevDemand = Math.sqrt(varianceSum / Math.max(1, dailyData.size()));
        }

        int leadTime = Math.max(1, product.getLeadTimeDays());

        // Safety Stock = Z * stdDev * sqrt(LeadTime)
        int safetyStock = (int) Math.ceil(SERVICE_LEVEL_Z * stdDevDemand * Math.sqrt(leadTime));
        safetyStock = Math.max(5, safetyStock);

        // Lead Time Demand = avgDailyDemand * LeadTime
        int leadTimeDemand = (int) Math.ceil(avgDailyDemand * leadTime);

        // Reorder Point (ROP) = Lead Time Demand + Safety Stock
        int reorderPoint = leadTimeDemand + safetyStock;

        // Annual Demand D = avgDailyDemand * 365
        double annualDemand = avgDailyDemand * 365.0;
        double orderCost = product.getOrderCost() != null ? product.getOrderCost().doubleValue() : 50.0;
        double unitCost = product.getUnitCost() != null ? product.getUnitCost().doubleValue() : 10.0;
        double holdingRate = product.getHoldingCostRate() != null ? product.getHoldingCostRate().doubleValue() : 0.20;
        double holdingCostPerUnit = Math.max(0.5, unitCost * holdingRate);

        // EOQ = sqrt((2 * D * S) / H)
        int eoq = (int) Math.round(Math.sqrt((2 * annualDemand * orderCost) / holdingCostPerUnit));
        eoq = Math.max(10, eoq);

        int optimalStock = reorderPoint + eoq;
        int maxStock = (int) Math.round(optimalStock * 1.5);

        inventory.setSafetyStock(safetyStock);
        inventory.setReorderPoint(reorderPoint);
        inventory.setOptimalStockLevel(optimalStock);
        inventory.setMaxStockLevel(maxStock);

        evaluateStockStatus(inventory);
        inventoryRepository.save(inventory);

        generateReorderRecommendationIfNeeded(product, inventory, (int) Math.round(avgDailyDemand * 30));
    }

    @Override
    public void evaluateStockStatus(Inventory inventory) {
        int current = inventory.getCurrentStock() != null ? inventory.getCurrentStock() : 0;
        int rop = inventory.getReorderPoint() != null ? inventory.getReorderPoint() : 0;
        int max = inventory.getMaxStockLevel() != null ? inventory.getMaxStockLevel() : 100;

        if (current <= 0) {
            inventory.setStatus(StockStatus.OUT_OF_STOCK);
        } else if (current <= rop) {
            inventory.setStatus(StockStatus.LOW_STOCK);
        } else if (max > 0 && current > max) {
            inventory.setStatus(StockStatus.OVERSTOCKED);
        } else {
            inventory.setStatus(StockStatus.IN_STOCK);
        }
    }

    @Override
    @Transactional
    public void generateReorderRecommendationIfNeeded(Product product, Inventory inventory, int predictedDemand) {
        int current = inventory.getCurrentStock() != null ? inventory.getCurrentStock() : 0;
        int rop = inventory.getReorderPoint() != null ? inventory.getReorderPoint() : 0;
        int optimal = inventory.getOptimalStockLevel() != null ? inventory.getOptimalStockLevel() : 100;

        if (current <= rop) {
            int neededQty = Math.max(10, optimal - current);
            UrgencyLevel urgency;
            String reason;

            if (current == 0) {
                urgency = UrgencyLevel.CRITICAL;
                reason = "Out of stock! Immediate replenishment required (forecasted demand: " + predictedDemand + " units).";
            } else if (current <= inventory.getSafetyStock()) {
                urgency = UrgencyLevel.HIGH;
                reason = "Current stock (" + current + ") has breached safety stock (" + inventory.getSafetyStock() + ").";
            } else {
                urgency = UrgencyLevel.MEDIUM;
                reason = "Stock reached reorder point (" + rop + "). Recommended EOQ reorder to maintain optimal inventory.";
            }

            BigDecimal unitCost = product.getUnitCost() != null ? product.getUnitCost() : BigDecimal.valueOf(10.0);
            BigDecimal estimatedCost = unitCost.multiply(BigDecimal.valueOf(neededQty)).setScale(2, RoundingMode.HALF_UP);

            List<ReorderRecommendation> existing = reorderRecommendationRepository.findByStatusOrderByCreatedAtDesc(RecommendationStatus.PENDING);
            boolean alreadyRecommended = existing.stream().anyMatch(r -> r.getProduct().getId().equals(product.getId()));

            if (!alreadyRecommended) {
                ReorderRecommendation rec = ReorderRecommendation.builder()
                        .product(product)
                        .recommendedQuantity(neededQty)
                        .urgency(urgency)
                        .reason(reason)
                        .estimatedCost(estimatedCost)
                        .status(RecommendationStatus.PENDING)
                        .build();
                reorderRecommendationRepository.save(rec);
            }
        }
    }
}
