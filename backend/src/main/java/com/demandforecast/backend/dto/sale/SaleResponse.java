package com.demandforecast.backend.dto.sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SaleResponse {
    private Long id;
    private String saleCode;
    private LocalDate saleDate;
    private BigDecimal totalAmount;
    private String notes;
    private String createdByUsername;
    private List<SaleItemResponse> items;
    private LocalDateTime createdAt;

    public SaleResponse() {}

    public SaleResponse(Long id, String saleCode, LocalDate saleDate, BigDecimal totalAmount,
                        String notes, String createdByUsername, List<SaleItemResponse> items,
                        LocalDateTime createdAt) {
        this.id = id;
        this.saleCode = saleCode;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.notes = notes;
        this.createdByUsername = createdByUsername;
        this.items = items;
        this.createdAt = createdAt;
    }

    public static SaleResponseBuilder builder() { return new SaleResponseBuilder(); }

    public static class SaleResponseBuilder {
        private Long id;
        private String saleCode;
        private LocalDate saleDate;
        private BigDecimal totalAmount;
        private String notes;
        private String createdByUsername;
        private List<SaleItemResponse> items;
        private LocalDateTime createdAt;

        public SaleResponseBuilder id(Long id) { this.id = id; return this; }
        public SaleResponseBuilder saleCode(String saleCode) { this.saleCode = saleCode; return this; }
        public SaleResponseBuilder saleDate(LocalDate saleDate) { this.saleDate = saleDate; return this; }
        public SaleResponseBuilder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public SaleResponseBuilder notes(String notes) { this.notes = notes; return this; }
        public SaleResponseBuilder createdByUsername(String createdByUsername) { this.createdByUsername = createdByUsername; return this; }
        public SaleResponseBuilder items(List<SaleItemResponse> items) { this.items = items; return this; }
        public SaleResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public SaleResponse build() { return new SaleResponse(id, saleCode, saleDate, totalAmount, notes, createdByUsername, items, createdAt); }
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
    public String getCreatedByUsername() { return createdByUsername; }
    public void setCreatedByUsername(String createdByUsername) { this.createdByUsername = createdByUsername; }
    public List<SaleItemResponse> getItems() { return items; }
    public void setItems(List<SaleItemResponse> items) { this.items = items; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
