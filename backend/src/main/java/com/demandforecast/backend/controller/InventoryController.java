package com.demandforecast.backend.controller;

import com.demandforecast.backend.dto.inventory.InventoryResponse;
import com.demandforecast.backend.dto.inventory.UpdateStockRequest;
import com.demandforecast.backend.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory", description = "Endpoints for inventory tracking, safety stock, and reorder levels")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @Operation(summary = "Get full inventory status list")
    public ResponseEntity<List<InventoryResponse>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get inventory level for a specific product")
    public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryByProductId(productId));
    }

    @PostMapping("/update")
    @Operation(summary = "Manually adjust or restock inventory quantity")
    public ResponseEntity<InventoryResponse> updateStock(@Valid @RequestBody UpdateStockRequest request) {
        return ResponseEntity.ok(inventoryService.updateStock(request));
    }

    @GetMapping("/alerts")
    @Operation(summary = "Get active low-stock and out-of-stock alerts")
    public ResponseEntity<List<InventoryResponse>> getLowStockAlerts() {
        return ResponseEntity.ok(inventoryService.getLowStockAlerts());
    }
}
