package com.demandforecast.backend.dto.inventory;

import jakarta.validation.constraints.NotNull;

public class UpdateStockRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Stock quantity adjustment is required")
    private Integer quantityChange;

    private String adjustmentType;
    private String reason;

    public UpdateStockRequest() {}

    public UpdateStockRequest(Long productId, Integer quantityChange, String adjustmentType, String reason) {
        this.productId = productId;
        this.quantityChange = quantityChange;
        this.adjustmentType = adjustmentType;
        this.reason = reason;
    }

    public static UpdateStockRequestBuilder builder() { return new UpdateStockRequestBuilder(); }

    public static class UpdateStockRequestBuilder {
        private Long productId;
        private Integer quantityChange;
        private String adjustmentType;
        private String reason;

        public UpdateStockRequestBuilder productId(Long productId) { this.productId = productId; return this; }
        public UpdateStockRequestBuilder quantityChange(Integer quantityChange) { this.quantityChange = quantityChange; return this; }
        public UpdateStockRequestBuilder adjustmentType(String adjustmentType) { this.adjustmentType = adjustmentType; return this; }
        public UpdateStockRequestBuilder reason(String reason) { this.reason = reason; return this; }
        public UpdateStockRequest build() { return new UpdateStockRequest(productId, quantityChange, adjustmentType, reason); }
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantityChange() { return quantityChange; }
    public void setQuantityChange(Integer quantityChange) { this.quantityChange = quantityChange; }
    public String getAdjustmentType() { return adjustmentType; }
    public void setAdjustmentType(String adjustmentType) { this.adjustmentType = adjustmentType; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
