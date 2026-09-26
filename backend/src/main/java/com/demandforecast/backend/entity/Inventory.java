package com.demandforecast.backend.entity;

import com.demandforecast.backend.enums.StockStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(name = "current_stock", nullable = false)
    private Integer currentStock = 0;

    @Column(name = "safety_stock", nullable = false)
    private Integer safetyStock = 0;

    @Column(name = "reorder_point", nullable = false)
    private Integer reorderPoint = 0;

    @Column(name = "optimal_stock_level", nullable = false)
    private Integer optimalStockLevel = 0;

    @Column(name = "max_stock_level", nullable = false)
    private Integer maxStockLevel = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StockStatus status = StockStatus.IN_STOCK;

    @Column(name = "last_restocked_at")
    private LocalDateTime lastRestockedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Inventory() {}

    public Inventory(Long id, Product product, Integer currentStock, Integer safetyStock,
                     Integer reorderPoint, Integer optimalStockLevel, Integer maxStockLevel,
                     StockStatus status, LocalDateTime lastRestockedAt) {
        this.id = id;
        this.product = product;
        this.currentStock = currentStock != null ? currentStock : 0;
        this.safetyStock = safetyStock != null ? safetyStock : 0;
        this.reorderPoint = reorderPoint != null ? reorderPoint : 0;
        this.optimalStockLevel = optimalStockLevel != null ? optimalStockLevel : 0;
        this.maxStockLevel = maxStockLevel != null ? maxStockLevel : 0;
        this.status = status != null ? status : StockStatus.IN_STOCK;
        this.lastRestockedAt = lastRestockedAt;
    }

    public static InventoryBuilder builder() { return new InventoryBuilder(); }

    public static class InventoryBuilder {
        private Long id;
        private Product product;
        private Integer currentStock = 0;
        private Integer safetyStock = 0;
        private Integer reorderPoint = 0;
        private Integer optimalStockLevel = 0;
        private Integer maxStockLevel = 0;
        private StockStatus status = StockStatus.IN_STOCK;
        private LocalDateTime lastRestockedAt;

        public InventoryBuilder id(Long id) { this.id = id; return this; }
        public InventoryBuilder product(Product product) { this.product = product; return this; }
        public InventoryBuilder currentStock(Integer currentStock) { this.currentStock = currentStock; return this; }
        public InventoryBuilder safetyStock(Integer safetyStock) { this.safetyStock = safetyStock; return this; }
        public InventoryBuilder reorderPoint(Integer reorderPoint) { this.reorderPoint = reorderPoint; return this; }
        public InventoryBuilder optimalStockLevel(Integer optimalStockLevel) { this.optimalStockLevel = optimalStockLevel; return this; }
        public InventoryBuilder maxStockLevel(Integer maxStockLevel) { this.maxStockLevel = maxStockLevel; return this; }
        public InventoryBuilder status(StockStatus status) { this.status = status; return this; }
        public InventoryBuilder lastRestockedAt(LocalDateTime lastRestockedAt) { this.lastRestockedAt = lastRestockedAt; return this; }
        public Inventory build() { return new Inventory(id, product, currentStock, safetyStock, reorderPoint, optimalStockLevel, maxStockLevel, status, lastRestockedAt); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
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
    public LocalDateTime getLastRestockedAt() { return lastRestockedAt; }
    public void setLastRestockedAt(LocalDateTime lastRestockedAt) { this.lastRestockedAt = lastRestockedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
