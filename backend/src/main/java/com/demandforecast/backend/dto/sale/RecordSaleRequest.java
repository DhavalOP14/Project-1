package com.demandforecast.backend.dto.sale;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public class RecordSaleRequest {

    private String saleCode;
    private LocalDate saleDate;
    private String notes;

    @NotEmpty(message = "Sale must contain at least one item")
    @Valid
    private List<SaleItemRequest> items;

    public RecordSaleRequest() {}

    public RecordSaleRequest(String saleCode, LocalDate saleDate, String notes, List<SaleItemRequest> items) {
        this.saleCode = saleCode;
        this.saleDate = saleDate;
        this.notes = notes;
        this.items = items;
    }

    public static RecordSaleRequestBuilder builder() { return new RecordSaleRequestBuilder(); }

    public static class RecordSaleRequestBuilder {
        private String saleCode;
        private LocalDate saleDate;
        private String notes;
        private List<SaleItemRequest> items;

        public RecordSaleRequestBuilder saleCode(String saleCode) { this.saleCode = saleCode; return this; }
        public RecordSaleRequestBuilder saleDate(LocalDate saleDate) { this.saleDate = saleDate; return this; }
        public RecordSaleRequestBuilder notes(String notes) { this.notes = notes; return this; }
        public RecordSaleRequestBuilder items(List<SaleItemRequest> items) { this.items = items; return this; }
        public RecordSaleRequest build() { return new RecordSaleRequest(saleCode, saleDate, notes, items); }
    }

    public String getSaleCode() { return saleCode; }
    public void setSaleCode(String saleCode) { this.saleCode = saleCode; }
    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public List<SaleItemRequest> getItems() { return items; }
    public void setItems(List<SaleItemRequest> items) { this.items = items; }
}
