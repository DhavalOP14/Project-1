package com.demandforecast.backend.dto.product;

import com.demandforecast.backend.dto.category.CategoryResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private CategoryResponse category;
    private BigDecimal unitPrice;
    private BigDecimal unitCost;
    private Integer leadTimeDays;
    private BigDecimal orderCost;
    private BigDecimal holdingCostRate;
    private Integer currentStock;
    private String stockStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponse() {}

    public ProductResponse(Long id, String sku, String name, String description, CategoryResponse category,
                           BigDecimal unitPrice, BigDecimal unitCost, Integer leadTimeDays,
                           BigDecimal orderCost, BigDecimal holdingCostRate, Integer currentStock,
                           String stockStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.category = category;
        this.unitPrice = unitPrice;
        this.unitCost = unitCost;
        this.leadTimeDays = leadTimeDays;
        this.orderCost = orderCost;
        this.holdingCostRate = holdingCostRate;
        this.currentStock = currentStock;
        this.stockStatus = stockStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductResponseBuilder builder() { return new ProductResponseBuilder(); }

    public static class ProductResponseBuilder {
        private Long id;
        private String sku;
        private String name;
        private String description;
        private CategoryResponse category;
        private BigDecimal unitPrice;
        private BigDecimal unitCost;
        private Integer leadTimeDays;
        private BigDecimal orderCost;
        private BigDecimal holdingCostRate;
        private Integer currentStock;
        private String stockStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ProductResponseBuilder id(Long id) { this.id = id; return this; }
        public ProductResponseBuilder sku(String sku) { this.sku = sku; return this; }
        public ProductResponseBuilder name(String name) { this.name = name; return this; }
        public ProductResponseBuilder description(String description) { this.description = description; return this; }
        public ProductResponseBuilder category(CategoryResponse category) { this.category = category; return this; }
        public ProductResponseBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public ProductResponseBuilder unitCost(BigDecimal unitCost) { this.unitCost = unitCost; return this; }
        public ProductResponseBuilder leadTimeDays(Integer leadTimeDays) { this.leadTimeDays = leadTimeDays; return this; }
        public ProductResponseBuilder orderCost(BigDecimal orderCost) { this.orderCost = orderCost; return this; }
        public ProductResponseBuilder holdingCostRate(BigDecimal holdingCostRate) { this.holdingCostRate = holdingCostRate; return this; }
        public ProductResponseBuilder currentStock(Integer currentStock) { this.currentStock = currentStock; return this; }
        public ProductResponseBuilder stockStatus(String stockStatus) { this.stockStatus = stockStatus; return this; }
        public ProductResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ProductResponseBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public ProductResponse build() { return new ProductResponse(id, sku, name, description, category, unitPrice, unitCost, leadTimeDays, orderCost, holdingCostRate, currentStock, stockStatus, createdAt, updatedAt); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public CategoryResponse getCategory() { return category; }
    public void setCategory(CategoryResponse category) { this.category = category; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public Integer getLeadTimeDays() { return leadTimeDays; }
    public void setLeadTimeDays(Integer leadTimeDays) { this.leadTimeDays = leadTimeDays; }
    public BigDecimal getOrderCost() { return orderCost; }
    public void setOrderCost(BigDecimal orderCost) { this.orderCost = orderCost; }
    public BigDecimal getHoldingCostRate() { return holdingCostRate; }
    public void setHoldingCostRate(BigDecimal holdingCostRate) { this.holdingCostRate = holdingCostRate; }
    public Integer getCurrentStock() { return currentStock; }
    public void setCurrentStock(Integer currentStock) { this.currentStock = currentStock; }
    public String getStockStatus() { return stockStatus; }
    public void setStockStatus(String stockStatus) { this.stockStatus = stockStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
