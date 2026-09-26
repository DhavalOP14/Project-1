package com.demandforecast.backend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "unit_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "lead_time_days", nullable = false)
    private Integer leadTimeDays = 7;

    @Column(name = "order_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal orderCost = BigDecimal.valueOf(50.00);

    @Column(name = "holding_cost_rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal holdingCostRate = BigDecimal.valueOf(0.20);

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product() {}

    public Product(Long id, String sku, String name, String description, Category category,
                   BigDecimal unitPrice, BigDecimal unitCost, Integer leadTimeDays,
                   BigDecimal orderCost, BigDecimal holdingCostRate) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.category = category;
        this.unitPrice = unitPrice;
        this.unitCost = unitCost;
        this.leadTimeDays = leadTimeDays != null ? leadTimeDays : 7;
        this.orderCost = orderCost != null ? orderCost : BigDecimal.valueOf(50.00);
        this.holdingCostRate = holdingCostRate != null ? holdingCostRate : BigDecimal.valueOf(0.20);
    }

    public static ProductBuilder builder() { return new ProductBuilder(); }

    public static class ProductBuilder {
        private Long id;
        private String sku;
        private String name;
        private String description;
        private Category category;
        private BigDecimal unitPrice;
        private BigDecimal unitCost;
        private Integer leadTimeDays = 7;
        private BigDecimal orderCost = BigDecimal.valueOf(50.00);
        private BigDecimal holdingCostRate = BigDecimal.valueOf(0.20);

        public ProductBuilder id(Long id) { this.id = id; return this; }
        public ProductBuilder sku(String sku) { this.sku = sku; return this; }
        public ProductBuilder name(String name) { this.name = name; return this; }
        public ProductBuilder description(String description) { this.description = description; return this; }
        public ProductBuilder category(Category category) { this.category = category; return this; }
        public ProductBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public ProductBuilder unitCost(BigDecimal unitCost) { this.unitCost = unitCost; return this; }
        public ProductBuilder leadTimeDays(Integer leadTimeDays) { this.leadTimeDays = leadTimeDays; return this; }
        public ProductBuilder orderCost(BigDecimal orderCost) { this.orderCost = orderCost; return this; }
        public ProductBuilder holdingCostRate(BigDecimal holdingCostRate) { this.holdingCostRate = holdingCostRate; return this; }
        public Product build() { return new Product(id, sku, name, description, category, unitPrice, unitCost, leadTimeDays, orderCost, holdingCostRate); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
