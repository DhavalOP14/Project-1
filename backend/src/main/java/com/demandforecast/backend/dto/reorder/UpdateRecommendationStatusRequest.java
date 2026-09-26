package com.demandforecast.backend.dto.reorder;

import com.demandforecast.backend.enums.RecommendationStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateRecommendationStatusRequest {

    @NotNull(message = "Status is required")
    private RecommendationStatus status;

    public UpdateRecommendationStatusRequest() {}

    public UpdateRecommendationStatusRequest(RecommendationStatus status) {
        this.status = status;
    }

    public static UpdateRecommendationStatusRequestBuilder builder() { return new UpdateRecommendationStatusRequestBuilder(); }

    public static class UpdateRecommendationStatusRequestBuilder {
        private RecommendationStatus status;
        public UpdateRecommendationStatusRequestBuilder status(RecommendationStatus status) { this.status = status; return this; }
        public UpdateRecommendationStatusRequest build() { return new UpdateRecommendationStatusRequest(status); }
    }

    public RecommendationStatus getStatus() { return status; }
    public void setStatus(RecommendationStatus status) { this.status = status; }
}
