package com.demandforecast.backend.service.impl;

import com.demandforecast.backend.dto.reorder.ReorderRecommendationResponse;
import com.demandforecast.backend.dto.reorder.UpdateRecommendationStatusRequest;
import com.demandforecast.backend.entity.Inventory;
import com.demandforecast.backend.entity.Product;
import com.demandforecast.backend.entity.ReorderRecommendation;
import com.demandforecast.backend.enums.RecommendationStatus;
import com.demandforecast.backend.exception.ResourceNotFoundException;
import com.demandforecast.backend.repository.InventoryRepository;
import com.demandforecast.backend.repository.ProductRepository;
import com.demandforecast.backend.repository.ReorderRecommendationRepository;
import com.demandforecast.backend.service.OptimizationService;
import com.demandforecast.backend.service.ReorderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReorderServiceImpl implements ReorderService {

    private final ReorderRecommendationRepository reorderRepo;
    private final ProductRepository productRepo;
    private final InventoryRepository inventoryRepo;
    private final OptimizationService optimizationService;

    public ReorderServiceImpl(ReorderRecommendationRepository reorderRepo,
                              ProductRepository productRepo,
                              InventoryRepository inventoryRepo,
                              OptimizationService optimizationService) {
        this.reorderRepo = reorderRepo;
        this.productRepo = productRepo;
        this.inventoryRepo = inventoryRepo;
        this.optimizationService = optimizationService;
    }

    @Override
    public List<ReorderRecommendationResponse> getAllRecommendations() {
        return reorderRepo.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReorderRecommendationResponse> getRecommendationsByStatus(RecommendationStatus status) {
        return reorderRepo.findByStatusOrderByCreatedAtDesc(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReorderRecommendationResponse updateStatus(Long id, UpdateRecommendationStatusRequest request) {
        ReorderRecommendation rec = reorderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reorder recommendation not found with id: " + id));

        rec.setStatus(request.getStatus());

        if (request.getStatus() == RecommendationStatus.ORDERED) {
            Inventory inventory = inventoryRepo.findByProductId(rec.getProduct().getId()).orElse(null);
            if (inventory != null) {
                inventory.setCurrentStock(inventory.getCurrentStock() + rec.getRecommendedQuantity());
                inventory.setLastRestockedAt(LocalDateTime.now());
                optimizationService.evaluateStockStatus(inventory);
                inventoryRepo.save(inventory);
            }
        }

        rec = reorderRepo.save(rec);
        return mapToResponse(rec);
    }

    @Override
    @Transactional
    public void triggerReorderAnalysis() {
        List<Product> products = productRepo.findAll();
        for (Product product : products) {
            optimizationService.recalculateInventoryOptimization(product.getId());
        }
    }

    private ReorderRecommendationResponse mapToResponse(ReorderRecommendation rec) {
        Product p = rec.getProduct();
        Inventory inv = inventoryRepo.findByProductId(p.getId()).orElse(null);

        return ReorderRecommendationResponse.builder()
                .id(rec.getId())
                .productId(p.getId())
                .productSku(p.getSku())
                .productName(p.getName())
                .categoryName(p.getCategory() != null ? p.getCategory().getName() : "Uncategorized")
                .currentStock(inv != null ? inv.getCurrentStock() : 0)
                .safetyStock(inv != null ? inv.getSafetyStock() : 0)
                .reorderPoint(inv != null ? inv.getReorderPoint() : 0)
                .recommendedQuantity(rec.getRecommendedQuantity())
                .urgency(rec.getUrgency())
                .reason(rec.getReason())
                .estimatedCost(rec.getEstimatedCost())
                .status(rec.getStatus())
                .leadTimeDays(p.getLeadTimeDays())
                .createdAt(rec.getCreatedAt())
                .updatedAt(rec.getUpdatedAt())
                .build();
    }
}
