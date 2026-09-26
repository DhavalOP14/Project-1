package com.demandforecast.backend.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateProductRequest {

    @NotBlank(message = "SKU is required")
    private String sku;

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than zero")
    private BigDecimal unitPrice;

    @NotNull(message = "Unit cost is required")
    @DecimalMin(value = "0.01", message = "Unit cost must be greater than zero")
    private BigDecimal unitCost;

    @NotNull(message = "Lead time days is required")
    @Min(value = 1, message = "Lead time must be at least 1 day")
    private Integer leadTimeDays;

    private BigDecimal orderCost;
    private BigDecimal holdingCostRate;
    private Integer initialStock;

    public CreateProductRequest() {}

    public CreateProductRequest(String sku, String name, String description, Long categoryId,
                                BigDecimal unitPrice, BigDecimal unitCost, Integer leadTimeDays,
                                BigDecimal orderCost, BigDecimal holdingCostRate, Integer initialStock) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.unitPrice = unitPrice;
        this.unitCost = unitCost;
        this.leadTimeDays = leadTimeDays;
        this.orderCost = orderCost;
        this.holdingCostRate = holdingCostRate;
        this.initialStock = initialStock;
    }

    public static CreateProductRequestBuilder builder() { return new CreateProductRequestBuilder(); }

    public static class CreateProductRequestBuilder {
        private String sku;
        private String name;
        private String description;
        private Long categoryId;
        private BigDecimal unitPrice;
        private BigDecimal unitCost;
        private Integer leadTimeDays;
        private BigDecimal orderCost;
        private BigDecimal holdingCostRate;
        private Integer initialStock;

        public CreateProductRequestBuilder sku(String sku) { this.sku = sku; return this; }
        public CreateProductRequestBuilder name(String name) { this.name = name; return this; }
        public CreateProductRequestBuilder description(String description) { this.description = description; return this; }
        public CreateProductRequestBuilder categoryId(Long categoryId) { this.categoryId = categoryId; return this; }
        public CreateProductRequestBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public CreateProductRequestBuilder unitCost(BigDecimal unitCost) { this.unitCost = unitCost; return this; }
        public CreateProductRequestBuilder leadTimeDays(Integer leadTimeDays) { this.leadTimeDays = leadTimeDays; return this; }
        public CreateProductRequestBuilder orderCost(BigDecimal orderCost) { this.orderCost = orderCost; return this; }
        public CreateProductRequestBuilder holdingCostRate(BigDecimal holdingCostRate) { this.holdingCostRate = holdingCostRate; return this; }
        public CreateProductRequestBuilder initialStock(Integer initialStock) { this.initialStock = initialStock; return this; }
        public CreateProductRequest build() { return new CreateProductRequest(sku, name, description, categoryId, unitPrice, unitCost, leadTimeDays, orderCost, holdingCostRate, initialStock); }
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
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
    public Integer getInitialStock() { return initialStock; }
    public void setInitialStock(Integer initialStock) { this.initialStock = initialStock; }
}
