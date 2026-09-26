package com.demandforecast.backend.dto.sale;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SaleItemRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private BigDecimal unitPrice;

    public SaleItemRequest() {}

    public SaleItemRequest(Long productId, Integer quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public static SaleItemRequestBuilder builder() { return new SaleItemRequestBuilder(); }

    public static class SaleItemRequestBuilder {
        private Long productId;
        private Integer quantity;
        private BigDecimal unitPrice;

        public SaleItemRequestBuilder productId(Long productId) { this.productId = productId; return this; }
        public SaleItemRequestBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public SaleItemRequestBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public SaleItemRequest build() { return new SaleItemRequest(productId, quantity, unitPrice); }
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
