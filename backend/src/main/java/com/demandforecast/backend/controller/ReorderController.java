package com.demandforecast.backend.controller;

import com.demandforecast.backend.dto.reorder.ReorderRecommendationResponse;
import com.demandforecast.backend.dto.reorder.UpdateRecommendationStatusRequest;
import com.demandforecast.backend.enums.RecommendationStatus;
import com.demandforecast.backend.service.ReorderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reorders")
@Tag(name = "Reorder Recommendations", description = "Endpoints for inventory replenishment and EOQ order recommendations")
public class ReorderController {

    private final ReorderService reorderService;

    public ReorderController(ReorderService reorderService) {
        this.reorderService = reorderService;
    }

    @GetMapping
    @Operation(summary = "Get all reorder recommendations")
    public ResponseEntity<List<ReorderRecommendationResponse>> getAllRecommendations() {
        return ResponseEntity.ok(reorderService.getAllRecommendations());
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get recommendations filtered by status (PENDING, APPROVED, ORDERED, REJECTED)")
    public ResponseEntity<List<ReorderRecommendationResponse>> getRecommendationsByStatus(@PathVariable RecommendationStatus status) {
        return ResponseEntity.ok(reorderService.getRecommendationsByStatus(status));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update recommendation status (e.g. approve, order, or reject)")
    public ResponseEntity<ReorderRecommendationResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRecommendationStatusRequest request
    ) {
        return ResponseEntity.ok(reorderService.updateStatus(id, request));
    }

    @PostMapping("/analyze")
    @Operation(summary = "Manually trigger inventory reorder optimization analysis")
    public ResponseEntity<String> triggerAnalysis() {
        reorderService.triggerReorderAnalysis();
        return ResponseEntity.ok("Reorder analysis triggered successfully.");
    }
}
