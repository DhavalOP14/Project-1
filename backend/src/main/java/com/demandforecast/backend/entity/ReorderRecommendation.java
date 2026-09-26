package com.demandforecast.backend.entity;

import com.demandforecast.backend.enums.RecommendationStatus;
import com.demandforecast.backend.enums.UrgencyLevel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reorder_recommendations")
public class ReorderRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "recommended_quantity", nullable = false)
    private Integer recommendedQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UrgencyLevel urgency;

    @Column(nullable = false, length = 255)
    private String reason;

    @Column(name = "estimated_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal estimatedCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RecommendationStatus status = RecommendationStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ReorderRecommendation() {}

    public ReorderRecommendation(Long id, Product product, Integer recommendedQuantity, UrgencyLevel urgency,
                                 String reason, BigDecimal estimatedCost, RecommendationStatus status) {
        this.id = id;
        this.product = product;
        this.recommendedQuantity = recommendedQuantity;
        this.urgency = urgency;
        this.reason = reason;
        this.estimatedCost = estimatedCost;
        this.status = status != null ? status : RecommendationStatus.PENDING;
    }

    public static ReorderRecommendationBuilder builder() { return new ReorderRecommendationBuilder(); }

    public static class ReorderRecommendationBuilder {
        private Long id;
        private Product product;
        private Integer recommendedQuantity;
        private UrgencyLevel urgency;
        private String reason;
        private BigDecimal estimatedCost;
        private RecommendationStatus status = RecommendationStatus.PENDING;

        public ReorderRecommendationBuilder id(Long id) { this.id = id; return this; }
        public ReorderRecommendationBuilder product(Product product) { this.product = product; return this; }
        public ReorderRecommendationBuilder recommendedQuantity(Integer recommendedQuantity) { this.recommendedQuantity = recommendedQuantity; return this; }
        public ReorderRecommendationBuilder urgency(UrgencyLevel urgency) { this.urgency = urgency; return this; }
        public ReorderRecommendationBuilder reason(String reason) { this.reason = reason; return this; }
        public ReorderRecommendationBuilder estimatedCost(BigDecimal estimatedCost) { this.estimatedCost = estimatedCost; return this; }
        public ReorderRecommendationBuilder status(RecommendationStatus status) { this.status = status; return this; }
        public ReorderRecommendation build() { return new ReorderRecommendation(id, product, recommendedQuantity, urgency, reason, estimatedCost, status); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
