package com.demandforecast.backend.service;

import com.demandforecast.backend.entity.Inventory;
import com.demandforecast.backend.entity.Product;

public interface OptimizationService {
    void recalculateInventoryOptimization(Long productId);
    void evaluateStockStatus(Inventory inventory);
    void generateReorderRecommendationIfNeeded(Product product, Inventory inventory, int predictedDemand);
}
