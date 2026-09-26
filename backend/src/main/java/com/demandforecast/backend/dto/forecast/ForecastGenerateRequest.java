package com.demandforecast.backend.dto.forecast;

import com.demandforecast.backend.enums.ForecastPeriod;
import jakarta.validation.constraints.NotNull;

public class ForecastGenerateRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    private ForecastPeriod targetPeriod;
    private String preferredAlgorithm;
    private Integer horizonPeriods;

    public ForecastGenerateRequest() {}

    public ForecastGenerateRequest(Long productId, ForecastPeriod targetPeriod, String preferredAlgorithm, Integer horizonPeriods) {
        this.productId = productId;
        this.targetPeriod = targetPeriod;
        this.preferredAlgorithm = preferredAlgorithm;
        this.horizonPeriods = horizonPeriods;
    }

    public static ForecastGenerateRequestBuilder builder() { return new ForecastGenerateRequestBuilder(); }

    public static class ForecastGenerateRequestBuilder {
        private Long productId;
        private ForecastPeriod targetPeriod;
        private String preferredAlgorithm;
        private Integer horizonPeriods;

        public ForecastGenerateRequestBuilder productId(Long productId) { this.productId = productId; return this; }
        public ForecastGenerateRequestBuilder targetPeriod(ForecastPeriod targetPeriod) { this.targetPeriod = targetPeriod; return this; }
        public ForecastGenerateRequestBuilder preferredAlgorithm(String preferredAlgorithm) { this.preferredAlgorithm = preferredAlgorithm; return this; }
        public ForecastGenerateRequestBuilder horizonPeriods(Integer horizonPeriods) { this.horizonPeriods = horizonPeriods; return this; }
        public ForecastGenerateRequest build() { return new ForecastGenerateRequest(productId, targetPeriod, preferredAlgorithm, horizonPeriods); }
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public ForecastPeriod getTargetPeriod() { return targetPeriod; }
    public void setTargetPeriod(ForecastPeriod targetPeriod) { this.targetPeriod = targetPeriod; }
    public String getPreferredAlgorithm() { return preferredAlgorithm; }
    public void setPreferredAlgorithm(String preferredAlgorithm) { this.preferredAlgorithm = preferredAlgorithm; }
    public Integer getHorizonPeriods() { return horizonPeriods; }
    public void setHorizonPeriods(Integer horizonPeriods) { this.horizonPeriods = horizonPeriods; }
}
