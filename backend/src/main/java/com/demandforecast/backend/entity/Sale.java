package com.demandforecast.backend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sale_code", nullable = false, unique = true, length = 60)
    private String saleCode;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 255)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SaleItem> items = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Sale() {}

    public Sale(Long id, String saleCode, LocalDate saleDate, BigDecimal totalAmount, String notes, User createdBy, List<SaleItem> items) {
        this.id = id;
        this.saleCode = saleCode;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.notes = notes;
        this.createdBy = createdBy;
        this.items = items != null ? items : new ArrayList<>();
    }

    public static SaleBuilder builder() { return new SaleBuilder(); }

    public static class SaleBuilder {
        private Long id;
        private String saleCode;
        private LocalDate saleDate;
        private BigDecimal totalAmount;
        private String notes;
        private User createdBy;
        private List<SaleItem> items = new ArrayList<>();

        public SaleBuilder id(Long id) { this.id = id; return this; }
        public SaleBuilder saleCode(String saleCode) { this.saleCode = saleCode; return this; }
        public SaleBuilder saleDate(LocalDate saleDate) { this.saleDate = saleDate; return this; }
        public SaleBuilder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public SaleBuilder notes(String notes) { this.notes = notes; return this; }
        public SaleBuilder createdBy(User createdBy) { this.createdBy = createdBy; return this; }
        public SaleBuilder items(List<SaleItem> items) { this.items = items; return this; }
        public Sale build() { return new Sale(id, saleCode, saleDate, totalAmount, notes, createdBy, items); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSaleCode() { return saleCode; }
    public void setSaleCode(String saleCode) { this.saleCode = saleCode; }
    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public List<SaleItem> getItems() { return items; }
    public void setItems(List<SaleItem> items) { this.items = items; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
