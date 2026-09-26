package com.demandforecast.backend.service.impl;

import com.demandforecast.backend.dto.inventory.InventoryResponse;
import com.demandforecast.backend.dto.inventory.UpdateStockRequest;
import com.demandforecast.backend.entity.Inventory;
import com.demandforecast.backend.entity.Product;
import com.demandforecast.backend.exception.BadRequestException;
import com.demandforecast.backend.exception.ResourceNotFoundException;
import com.demandforecast.backend.repository.InventoryRepository;
import com.demandforecast.backend.repository.ProductRepository;
import com.demandforecast.backend.service.InventoryService;
import com.demandforecast.backend.service.OptimizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final OptimizationService optimizationService;

    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                ProductRepository productRepository,
                                OptimizationService optimizationService) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.optimizationService = optimizationService;
    }

    @Override
    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponse getInventoryByProductId(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product id: " + productId));
        return mapToResponse(inventory);
    }

    @Override
    @Transactional
    public InventoryResponse updateStock(UpdateStockRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        Inventory inventory = inventoryRepository.findByProductId(request.getProductId())
                .orElseGet(() -> Inventory.builder()
                        .product(product)
                        .currentStock(0)
                        .safetyStock(10)
                        .reorderPoint(25)
                        .optimalStockLevel(80)
                        .maxStockLevel(150)
                        .build());

        int newStock = inventory.getCurrentStock() + request.getQuantityChange();
        if (newStock < 0) {
            throw new BadRequestException("Adjustment would cause stock to be negative: " + newStock);
        }

        inventory.setCurrentStock(newStock);
        if (request.getQuantityChange() > 0) {
            inventory.setLastRestockedAt(LocalDateTime.now());
        }

        optimizationService.evaluateStockStatus(inventory);
        inventory = inventoryRepository.save(inventory);
        optimizationService.generateReorderRecommendationIfNeeded(product, inventory, 30);
        return mapToResponse(inventory);
    }

    @Override
    public List<InventoryResponse> getLowStockAlerts() {
        return inventoryRepository.findLowStockOrCritical().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        Product p = inventory.getProduct();
        BigDecimal unitCost = p.getUnitCost() != null ? p.getUnitCost() : BigDecimal.ZERO;
        BigDecimal totalVal = unitCost.multiply(BigDecimal.valueOf(inventory.getCurrentStock()));

        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(p.getId())
                .productSku(p.getSku())
                .productName(p.getName())
                .categoryName(p.getCategory() != null ? p.getCategory().getName() : "Uncategorized")
                .currentStock(inventory.getCurrentStock())
                .safetyStock(inventory.getSafetyStock())
                .reorderPoint(inventory.getReorderPoint())
                .optimalStockLevel(inventory.getOptimalStockLevel())
                .maxStockLevel(inventory.getMaxStockLevel())
                .status(inventory.getStatus())
                .unitPrice(p.getUnitPrice())
                .unitCost(p.getUnitCost())
                .totalInventoryValue(totalVal)
                .lastRestockedAt(inventory.getLastRestockedAt())
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }
}
