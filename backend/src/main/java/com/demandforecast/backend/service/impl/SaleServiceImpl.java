package com.demandforecast.backend.service.impl;

import com.demandforecast.backend.dto.sale.RecordSaleRequest;
import com.demandforecast.backend.dto.sale.SaleItemRequest;
import com.demandforecast.backend.dto.sale.SaleItemResponse;
import com.demandforecast.backend.dto.sale.SaleResponse;
import com.demandforecast.backend.entity.*;
import com.demandforecast.backend.exception.BadRequestException;
import com.demandforecast.backend.exception.ResourceNotFoundException;
import com.demandforecast.backend.repository.InventoryRepository;
import com.demandforecast.backend.repository.ProductRepository;
import com.demandforecast.backend.repository.SaleRepository;
import com.demandforecast.backend.repository.UserRepository;
import com.demandforecast.backend.service.OptimizationService;
import com.demandforecast.backend.service.SaleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    private final OptimizationService optimizationService;

    public SaleServiceImpl(SaleRepository saleRepository, ProductRepository productRepository,
                           InventoryRepository inventoryRepository, UserRepository userRepository,
                           OptimizationService optimizationService) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.optimizationService = optimizationService;
    }

    @Override
    @Transactional
    public SaleResponse recordSale(RecordSaleRequest request, String username) {
        User user = null;
        if (username != null && !username.isBlank()) {
            user = userRepository.findByUsername(username).orElse(null);
        }

        String saleCode = request.getSaleCode();
        if (saleCode == null || saleCode.isBlank()) {
            saleCode = "ORD-" + System.currentTimeMillis() % 1000000;
        }

        LocalDate saleDate = request.getSaleDate() != null ? request.getSaleDate() : LocalDate.now();

        Sale sale = Sale.builder()
                .saleCode(saleCode)
                .saleDate(saleDate)
                .totalAmount(BigDecimal.ZERO)
                .notes(request.getNotes())
                .createdBy(user)
                .items(new ArrayList<>())
                .build();

        BigDecimal grandTotal = BigDecimal.ZERO;

        for (SaleItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemReq.getProductId()));

            Inventory inventory = inventoryRepository.findByProductId(product.getId())
                    .orElseThrow(() -> new BadRequestException("No inventory found for product: " + product.getName()));

            if (inventory.getCurrentStock() < itemReq.getQuantity()) {
                throw new BadRequestException("Insufficient inventory for " + product.getName() +
                        ". Current stock: " + inventory.getCurrentStock() + ", Requested: " + itemReq.getQuantity());
            }

            inventory.setCurrentStock(inventory.getCurrentStock() - itemReq.getQuantity());
            optimizationService.evaluateStockStatus(inventory);
            inventoryRepository.save(inventory);

            BigDecimal unitPrice = itemReq.getUnitPrice() != null ? itemReq.getUnitPrice() : product.getUnitPrice();
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            grandTotal = grandTotal.add(subtotal);

            SaleItem item = SaleItem.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .unitPrice(unitPrice)
                    .subtotal(subtotal)
                    .build();
            sale.getItems().add(item);
        }

        sale.setTotalAmount(grandTotal);
        sale = saleRepository.save(sale);

        for (SaleItem item : sale.getItems()) {
            optimizationService.recalculateInventoryOptimization(item.getProduct().getId());
        }

        return mapToResponse(sale);
    }

    @Override
    public List<SaleResponse> getAllSales() {
        return saleRepository.findAllByOrderBySaleDateDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SaleResponse getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
        return mapToResponse(sale);
    }

    private SaleResponse mapToResponse(Sale sale) {
        List<SaleItemResponse> items = sale.getItems().stream()
                .map(item -> SaleItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productSku(item.getProduct().getSku())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return SaleResponse.builder()
                .id(sale.getId())
                .saleCode(sale.getSaleCode())
                .saleDate(sale.getSaleDate())
                .totalAmount(sale.getTotalAmount())
                .notes(sale.getNotes())
                .createdByUsername(sale.getCreatedBy() != null ? sale.getCreatedBy().getUsername() : "System")
                .items(items)
                .createdAt(sale.getCreatedAt())
                .build();
    }
}
