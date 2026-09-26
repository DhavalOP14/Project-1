package com.demandforecast.backend.dto.reorder;

import com.demandforecast.backend.enums.RecommendationStatus;
import com.demandforecast.backend.enums.UrgencyLevel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReorderRecommendationResponse {
    private Long id;
    private Long productId;
    private String productSku;
    private String productName;
    private String categoryName;
    private Integer currentStock;
    private Integer safetyStock;
    private Integer reorderPoint;
    private Integer recommendedQuantity;
    private UrgencyLevel urgency;
    private String reason;
    private BigDecimal estimatedCost;
    private RecommendationStatus status;
    private Integer leadTimeDays;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReorderRecommendationResponse() {}

    public ReorderRecommendationResponse(Long id, Long productId, String productSku, String productName,
                                         String categoryName, Integer currentStock, Integer safetyStock,
                                         Integer reorderPoint, Integer recommendedQuantity, UrgencyLevel urgency,
                                         String reason, BigDecimal estimatedCost, RecommendationStatus status,
                                         Integer leadTimeDays, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.productSku = productSku;
        this.productName = productName;
        this.categoryName = categoryName;
        this.currentStock = currentStock;
        this.safetyStock = safetyStock;
        this.reorderPoint = reorderPoint;
        this.recommendedQuantity = recommendedQuantity;
        this.urgency = urgency;
        this.reason = reason;
        this.estimatedCost = estimatedCost;
        this.status = status;
        this.leadTimeDays = leadTimeDays;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ReorderRecommendationResponseBuilder builder() { return new ReorderRecommendationResponseBuilder(); }

    public static class ReorderRecommendationResponseBuilder {
        private Long id;
        private Long productId;
        private String productSku;
        private String productName;
        private String categoryName;
        private Integer currentStock;
        private Integer safetyStock;
        private Integer reorderPoint;
        private Integer recommendedQuantity;
        private UrgencyLevel urgency;
        private String reason;
        private BigDecimal estimatedCost;
        private RecommendationStatus status;
        private Integer leadTimeDays;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ReorderRecommendationResponseBuilder id(Long id) { this.id = id; return this; }
        public ReorderRecommendationResponseBuilder productId(Long productId) { this.productId = productId; return this; }
        public ReorderRecommendationResponseBuilder productSku(String productSku) { this.productSku = productSku; return this; }
        public ReorderRecommendationResponseBuilder productName(String productName) { this.productName = productName; return this; }
        public ReorderRecommendationResponseBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public ReorderRecommendationResponseBuilder currentStock(Integer currentStock) { this.currentStock = currentStock; return this; }
        public ReorderRecommendationResponseBuilder safetyStock(Integer safetyStock) { this.safetyStock = safetyStock; return this; }
        public ReorderRecommendationResponseBuilder reorderPoint(Integer reorderPoint) { this.reorderPoint = reorderPoint; return this; }
        public ReorderRecommendationResponseBuilder recommendedQuantity(Integer recommendedQuantity) { this.recommendedQuantity = recommendedQuantity; return this; }
        public ReorderRecommendationResponseBuilder urgency(UrgencyLevel urgency) { this.urgency = urgency; return this; }
        public ReorderRecommendationResponseBuilder reason(String reason) { this.reason = reason; return this; }
        public ReorderRecommendationResponseBuilder estimatedCost(BigDecimal estimatedCost) { this.estimatedCost = estimatedCost; return this; }
        public ReorderRecommendationResponseBuilder status(RecommendationStatus status) { this.status = status; return this; }
        public ReorderRecommendationResponseBuilder leadTimeDays(Integer leadTimeDays) { this.leadTimeDays = leadTimeDays; return this; }
        public ReorderRecommendationResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ReorderRecommendationResponseBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public ReorderRecommendationResponse build() { return new ReorderRecommendationResponse(id, productId, productSku, productName, categoryName, currentStock, safetyStock, reorderPoint, recommendedQuantity, urgency, reason, estimatedCost, status, leadTimeDays, createdAt, updatedAt); }
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
    public Integer getRecommendedQuantity() { return recommendedQuantity; }
    public void setRecommendedQuantity(Integer recommendedQuantity) { this.recommendedQuantity = recommendedQuantity; }
    public UrgencyLevel getUrgency() { return urgency; }
    public void setUrgency(UrgencyLevel urgency) { this.urgency = urgency; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public BigDecimal getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(BigDecimal estimatedCost) { this.estimatedCost = estimatedCost; }
    public RecommendationStatus getStatus() { return status; }
    public void setStatus(RecommendationStatus status) { this.status = status; }
    public Integer getLeadTimeDays() { return leadTimeDays; }
    public void setLeadTimeDays(Integer leadTimeDays) { this.leadTimeDays = leadTimeDays; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
