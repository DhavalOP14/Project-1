package com.demandforecast.backend.service;

import com.demandforecast.backend.dto.inventory.InventoryResponse;
import com.demandforecast.backend.dto.inventory.UpdateStockRequest;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getAllInventory();
    InventoryResponse getInventoryByProductId(Long productId);
    InventoryResponse updateStock(UpdateStockRequest request);
    List<InventoryResponse> getLowStockAlerts();
}
