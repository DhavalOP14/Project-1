package com.demandforecast.backend.service;

import com.demandforecast.backend.dto.reorder.ReorderRecommendationResponse;
import com.demandforecast.backend.dto.reorder.UpdateRecommendationStatusRequest;
import com.demandforecast.backend.enums.RecommendationStatus;

import java.util.List;

public interface ReorderService {
    List<ReorderRecommendationResponse> getAllRecommendations();
    List<ReorderRecommendationResponse> getRecommendationsByStatus(RecommendationStatus status);
    ReorderRecommendationResponse updateStatus(Long id, UpdateRecommendationStatusRequest request);
    void triggerReorderAnalysis();
}
