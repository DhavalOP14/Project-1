package com.demandforecast.backend.service.impl;

import com.demandforecast.backend.dto.dashboard.DashboardSummaryResponse;
import com.demandforecast.backend.dto.inventory.InventoryResponse;
import com.demandforecast.backend.entity.Inventory;
import com.demandforecast.backend.entity.Sale;
import com.demandforecast.backend.enums.RecommendationStatus;
import com.demandforecast.backend.enums.StockStatus;
import com.demandforecast.backend.repository.*;
import com.demandforecast.backend.service.DashboardService;
import com.demandforecast.backend.service.ForecastService;
import com.demandforecast.backend.service.InventoryService;
import com.demandforecast.backend.service.ReorderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProductRepository productRepo;
    private final InventoryRepository inventoryRepo;
    private final SaleRepository saleRepo;
    private final ReorderRecommendationRepository reorderRepo;
    private final InventoryService inventoryService;
    private final ForecastService forecastService;
    private final ReorderService reorderService;

    public DashboardServiceImpl(ProductRepository productRepo,
                                InventoryRepository inventoryRepo,
                                SaleRepository saleRepo,
                                ReorderRecommendationRepository reorderRepo,
                                InventoryService inventoryService,
                                ForecastService forecastService,
                                ReorderService reorderService) {
        this.productRepo = productRepo;
        this.inventoryRepo = inventoryRepo;
        this.saleRepo = saleRepo;
        this.reorderRepo = reorderRepo;
        this.inventoryService = inventoryService;
        this.forecastService = forecastService;
        this.reorderService = reorderService;
    }

    @Override
    public DashboardSummaryResponse getDashboardSummary() {
        long totalProducts = productRepo.count();

        List<Inventory> allInventory = inventoryRepo.findAll();
        BigDecimal totalValue = BigDecimal.ZERO;
        long inStock = 0;
        long lowStock = 0;
        long outOfStock = 0;
        long overstocked = 0;

        for (Inventory inv : allInventory) {
            BigDecimal cost = inv.getProduct().getUnitCost() != null ? inv.getProduct().getUnitCost() : BigDecimal.ZERO;
            totalValue = totalValue.add(cost.multiply(BigDecimal.valueOf(inv.getCurrentStock())));

            if (inv.getStatus() == StockStatus.OUT_OF_STOCK) outOfStock++;
            else if (inv.getStatus() == StockStatus.LOW_STOCK) lowStock++;
            else if (inv.getStatus() == StockStatus.OVERSTOCKED) overstocked++;
            else inStock++;
        }

        long totalSalesCount = saleRepo.count();
        Double totalRevenue = saleRepo.calculateTotalSalesRevenue();
        if (totalRevenue == null) totalRevenue = 0.0;

        long pendingReorders = reorderRepo.countByStatus(RecommendationStatus.PENDING);
        long criticalAlerts = outOfStock + lowStock;

        List<InventoryResponse> lowStockAlerts = inventoryService.getLowStockAlerts();

        List<Sale> allSales = saleRepo.findAllByOrderBySaleDateDesc();
        Map<String, Double> monthlyTotals = new TreeMap<>();
        DateTimeFormatter monthFmt = DateTimeFormatter.ofPattern("MMM yyyy");

        for (Sale sale : allSales) {
            String month = sale.getSaleDate().format(monthFmt);
            double amount = sale.getTotalAmount() != null ? sale.getTotalAmount().doubleValue() : 0.0;
            monthlyTotals.put(month, monthlyTotals.getOrDefault(month, 0.0) + amount);
        }

        List<Map<String, Object>> trendList = new ArrayList<>();
        monthlyTotals.forEach((month, revenue) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("month", month);
            map.put("revenue", revenue);
            trendList.add(map);
        });

        return DashboardSummaryResponse.builder()
                .totalProducts(totalProducts)
                .totalInventoryValue(totalValue)
                .inStockCount(inStock)
                .lowStockCount(lowStock)
                .outOfStockCount(outOfStock)
                .overstockedCount(overstocked)
                .totalSalesCount(totalSalesCount)
                .totalSalesRevenue(totalRevenue)
                .pendingReordersCount(pendingReorders)
                .criticalAlertsCount(criticalAlerts)
                .lowStockAlerts(lowStockAlerts)
                .recentForecasts(forecastService.getAllLatestForecasts().stream().limit(5).collect(Collectors.toList()))
                .topReorderSuggestions(reorderService.getAllRecommendations().stream().limit(5).collect(Collectors.toList()))
                .monthlySalesTrend(trendList)
                .build();
    }
}
