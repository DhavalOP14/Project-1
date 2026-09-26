package com.demandforecast.backend.dto.inventory;

import com.demandforecast.backend.enums.StockStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InventoryResponse {
    private Long id;
    private Long productId;
    private String productSku;
    private String productName;
    private String categoryName;
    private Integer currentStock;
    private Integer safetyStock;
    private Integer reorderPoint;
    private Integer optimalStockLevel;
    private Integer maxStockLevel;
    private StockStatus status;
    private BigDecimal unitPrice;
    private BigDecimal unitCost;
    private BigDecimal totalInventoryValue;
    private LocalDateTime lastRestockedAt;
    private LocalDateTime updatedAt;

    public InventoryResponse() {}

    public InventoryResponse(Long id, Long productId, String productSku, String productName, String categoryName,
                             Integer currentStock, Integer safetyStock, Integer reorderPoint,
                             Integer optimalStockLevel, Integer maxStockLevel, StockStatus status,
                             BigDecimal unitPrice, BigDecimal unitCost, BigDecimal totalInventoryValue,
                             LocalDateTime lastRestockedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.productSku = productSku;
        this.productName = productName;
        this.categoryName = categoryName;
        this.currentStock = currentStock;
        this.safetyStock = safetyStock;
        this.reorderPoint = reorderPoint;
        this.optimalStockLevel = optimalStockLevel;
        this.maxStockLevel = maxStockLevel;
        this.status = status;
        this.unitPrice = unitPrice;
        this.unitCost = unitCost;
        this.totalInventoryValue = totalInventoryValue;
        this.lastRestockedAt = lastRestockedAt;
        this.updatedAt = updatedAt;
    }

    public static InventoryResponseBuilder builder() { return new InventoryResponseBuilder(); }

    public static class InventoryResponseBuilder {
        private Long id;
        private Long productId;
        private String productSku;
        private String productName;
        private String categoryName;
        private Integer currentStock;
        private Integer safetyStock;
        private Integer reorderPoint;
        private Integer optimalStockLevel;
        private Integer maxStockLevel;
        private StockStatus status;
        private BigDecimal unitPrice;
        private BigDecimal unitCost;
        private BigDecimal totalInventoryValue;
        private LocalDateTime lastRestockedAt;
        private LocalDateTime updatedAt;

        public InventoryResponseBuilder id(Long id) { this.id = id; return this; }
        public InventoryResponseBuilder productId(Long productId) { this.productId = productId; return this; }
        public InventoryResponseBuilder productSku(String productSku) { this.productSku = productSku; return this; }
        public InventoryResponseBuilder productName(String productName) { this.productName = productName; return this; }
        public InventoryResponseBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public InventoryResponseBuilder currentStock(Integer currentStock) { this.currentStock = currentStock; return this; }
        public InventoryResponseBuilder safetyStock(Integer safetyStock) { this.safetyStock = safetyStock; return this; }
        public InventoryResponseBuilder reorderPoint(Integer reorderPoint) { this.reorderPoint = reorderPoint; return this; }
        public InventoryResponseBuilder optimalStockLevel(Integer optimalStockLevel) { this.optimalStockLevel = optimalStockLevel; return this; }
        public InventoryResponseBuilder maxStockLevel(Integer maxStockLevel) { this.maxStockLevel = maxStockLevel; return this; }
        public InventoryResponseBuilder status(StockStatus status) { this.status = status; return this; }
        public InventoryResponseBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public InventoryResponseBuilder unitCost(BigDecimal unitCost) { this.unitCost = unitCost; return this; }
        public InventoryResponseBuilder totalInventoryValue(BigDecimal totalInventoryValue) { this.totalInventoryValue = totalInventoryValue; return this; }
        public InventoryResponseBuilder lastRestockedAt(LocalDateTime lastRestockedAt) { this.lastRestockedAt = lastRestockedAt; return this; }
        public InventoryResponseBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public InventoryResponse build() { return new InventoryResponse(id, productId, productSku, productName, categoryName, currentStock, safetyStock, reorderPoint, optimalStockLevel, maxStockLevel, status, unitPrice, unitCost, totalInventoryValue, lastRestockedAt, updatedAt); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductSku() { return productSku; }
    public void setProductSku(String productSku) { this.productSku = productSku; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public Integer getCurrentStock() { return currentStock; }
    public void setCurrentStock(Integer currentStock) { this.currentStock = currentStock; }
    public Integer getSafetyStock() { return safetyStock; }
    public void setSafetyStock(Integer safetyStock) { this.safetyStock = safetyStock; }
    public Integer getReorderPoint() { return reorderPoint; }
    public void setReorderPoint(Integer reorderPoint) { this.reorderPoint = reorderPoint; }
    public Integer getOptimalStockLevel() { return optimalStockLevel; }
    public void setOptimalStockLevel(Integer optimalStockLevel) { this.optimalStockLevel = optimalStockLevel; }
    public Integer getMaxStockLevel() { return maxStockLevel; }
    public void setMaxStockLevel(Integer maxStockLevel) { this.maxStockLevel = maxStockLevel; }
    public StockStatus getStatus() { return status; }
    public void setStatus(StockStatus status) { this.status = status; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public BigDecimal getTotalInventoryValue() { return totalInventoryValue; }
    public void setTotalInventoryValue(BigDecimal totalInventoryValue) { this.totalInventoryValue = totalInventoryValue; }
    public LocalDateTime getLastRestockedAt() { return lastRestockedAt; }
    public void setLastRestockedAt(LocalDateTime lastRestockedAt) { this.lastRestockedAt = lastRestockedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
