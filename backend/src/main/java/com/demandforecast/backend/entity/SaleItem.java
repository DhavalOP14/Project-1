package com.demandforecast.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_items")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    @JsonIgnore
    private Sale sale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    public SaleItem() {}

    public SaleItem(Long id, Sale sale, Product product, Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {
        this.id = id;
        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    public static SaleItemBuilder builder() { return new SaleItemBuilder(); }

    public static class SaleItemBuilder {
        private Long id;
        private Sale sale;
        private Product product;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;

        public SaleItemBuilder id(Long id) { this.id = id; return this; }
        public SaleItemBuilder sale(Sale sale) { this.sale = sale; return this; }
        public SaleItemBuilder product(Product product) { this.product = product; return this; }
        public SaleItemBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public SaleItemBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public SaleItemBuilder subtotal(BigDecimal subtotal) { this.subtotal = subtotal; return this; }
        public SaleItem build() { return new SaleItem(id, sale, product, quantity, unitPrice, subtotal); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Sale getSale() { return sale; }
    public void setSale(Sale sale) { this.sale = sale; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
