package com.demandforecast.backend.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class UpdateProductRequest {

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

    public UpdateProductRequest() {}

    public UpdateProductRequest(String name, String description, Long categoryId,
                                BigDecimal unitPrice, BigDecimal unitCost, Integer leadTimeDays,
                                BigDecimal orderCost, BigDecimal holdingCostRate) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.unitPrice = unitPrice;
        this.unitCost = unitCost;
        this.leadTimeDays = leadTimeDays;
        this.orderCost = orderCost;
        this.holdingCostRate = holdingCostRate;
    }

    public static UpdateProductRequestBuilder builder() { return new UpdateProductRequestBuilder(); }

    public static class UpdateProductRequestBuilder {
        private String name;
        private String description;
        private Long categoryId;
        private BigDecimal unitPrice;
        private BigDecimal unitCost;
        private Integer leadTimeDays;
        private BigDecimal orderCost;
        private BigDecimal holdingCostRate;

        public UpdateProductRequestBuilder name(String name) { this.name = name; return this; }
        public UpdateProductRequestBuilder description(String description) { this.description = description; return this; }
        public UpdateProductRequestBuilder categoryId(Long categoryId) { this.categoryId = categoryId; return this; }
        public UpdateProductRequestBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public UpdateProductRequestBuilder unitCost(BigDecimal unitCost) { this.unitCost = unitCost; return this; }
        public UpdateProductRequestBuilder leadTimeDays(Integer leadTimeDays) { this.leadTimeDays = leadTimeDays; return this; }
        public UpdateProductRequestBuilder orderCost(BigDecimal orderCost) { this.orderCost = orderCost; return this; }
        public UpdateProductRequestBuilder holdingCostRate(BigDecimal holdingCostRate) { this.holdingCostRate = holdingCostRate; return this; }
        public UpdateProductRequest build() { return new UpdateProductRequest(name, description, categoryId, unitPrice, unitCost, leadTimeDays, orderCost, holdingCostRate); }
    }

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
}
