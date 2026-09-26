package com.demandforecast.backend.dto.forecast;

import com.demandforecast.backend.enums.ForecastPeriod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ForecastResponse {
    private Long id;
    private Long productId;
    private String productSku;
    private String productName;
    private LocalDate forecastDate;
    private ForecastPeriod targetPeriod;
    private Integer predictedDemand;
    private Integer confidenceLower;
    private Integer confidenceUpper;
    private String algorithmUsed;
    private Integer historicalDataPoints;
    private BigDecimal accuracyMetric;
    private Double growthTrendPercentage;
    private List<Map<String, Object>> trendData;
    private LocalDateTime createdAt;

    public ForecastResponse() {}

    public ForecastResponse(Long id, Long productId, String productSku, String productName,
                            LocalDate forecastDate, ForecastPeriod targetPeriod, Integer predictedDemand,
                            Integer confidenceLower, Integer confidenceUpper, String algorithmUsed,
                            Integer historicalDataPoints, BigDecimal accuracyMetric,
                            Double growthTrendPercentage, List<Map<String, Object>> trendData,
                            LocalDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.productSku = productSku;
        this.productName = productName;
        this.forecastDate = forecastDate;
        this.targetPeriod = targetPeriod;
        this.predictedDemand = predictedDemand;
        this.confidenceLower = confidenceLower;
        this.confidenceUpper = confidenceUpper;
        this.algorithmUsed = algorithmUsed;
        this.historicalDataPoints = historicalDataPoints;
        this.accuracyMetric = accuracyMetric;
        this.growthTrendPercentage = growthTrendPercentage;
        this.trendData = trendData;
        this.createdAt = createdAt;
    }

    public static ForecastResponseBuilder builder() { return new ForecastResponseBuilder(); }

    public static class ForecastResponseBuilder {
        private Long id;
        private Long productId;
        private String productSku;
        private String productName;
        private LocalDate forecastDate;
        private ForecastPeriod targetPeriod;
        private Integer predictedDemand;
        private Integer confidenceLower;
        private Integer confidenceUpper;
        private String algorithmUsed;
        private Integer historicalDataPoints;
        private BigDecimal accuracyMetric;
        private Double growthTrendPercentage;
        private List<Map<String, Object>> trendData;
        private LocalDateTime createdAt;

        public ForecastResponseBuilder id(Long id) { this.id = id; return this; }
        public ForecastResponseBuilder productId(Long productId) { this.productId = productId; return this; }
        public ForecastResponseBuilder productSku(String productSku) { this.productSku = productSku; return this; }
        public ForecastResponseBuilder productName(String productName) { this.productName = productName; return this; }
        public ForecastResponseBuilder forecastDate(LocalDate forecastDate) { this.forecastDate = forecastDate; return this; }
        public ForecastResponseBuilder targetPeriod(ForecastPeriod targetPeriod) { this.targetPeriod = targetPeriod; return this; }
        public ForecastResponseBuilder predictedDemand(Integer predictedDemand) { this.predictedDemand = predictedDemand; return this; }
        public ForecastResponseBuilder confidenceLower(Integer confidenceLower) { this.confidenceLower = confidenceLower; return this; }
        public ForecastResponseBuilder confidenceUpper(Integer confidenceUpper) { this.confidenceUpper = confidenceUpper; return this; }
        public ForecastResponseBuilder algorithmUsed(String algorithmUsed) { this.algorithmUsed = algorithmUsed; return this; }
        public ForecastResponseBuilder historicalDataPoints(Integer historicalDataPoints) { this.historicalDataPoints = historicalDataPoints; return this; }
        public ForecastResponseBuilder accuracyMetric(BigDecimal accuracyMetric) { this.accuracyMetric = accuracyMetric; return this; }
        public ForecastResponseBuilder growthTrendPercentage(Double growthTrendPercentage) { this.growthTrendPercentage = growthTrendPercentage; return this; }
        public ForecastResponseBuilder trendData(List<Map<String, Object>> trendData) { this.trendData = trendData; return this; }
        public ForecastResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ForecastResponse build() { return new ForecastResponse(id, productId, productSku, productName, forecastDate, targetPeriod, predictedDemand, confidenceLower, confidenceUpper, algorithmUsed, historicalDataPoints, accuracyMetric, growthTrendPercentage, trendData, createdAt); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductSku() { return productSku; }
    public void setProductSku(String productSku) { this.productSku = productSku; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public LocalDate getForecastDate() { return forecastDate; }
    public void setForecastDate(LocalDate forecastDate) { this.forecastDate = forecastDate; }
    public ForecastPeriod getTargetPeriod() { return targetPeriod; }
    public void setTargetPeriod(ForecastPeriod targetPeriod) { this.targetPeriod = targetPeriod; }
    public Integer getPredictedDemand() { return predictedDemand; }
    public void setPredictedDemand(Integer predictedDemand) { this.predictedDemand = predictedDemand; }
    public Integer getConfidenceLower() { return confidenceLower; }
    public void setConfidenceLower(Integer confidenceLower) { this.confidenceLower = confidenceLower; }
    public Integer getConfidenceUpper() { return confidenceUpper; }
    public void setConfidenceUpper(Integer confidenceUpper) { this.confidenceUpper = confidenceUpper; }
    public String getAlgorithmUsed() { return algorithmUsed; }
    public void setAlgorithmUsed(String algorithmUsed) { this.algorithmUsed = algorithmUsed; }
    public Integer getHistoricalDataPoints() { return historicalDataPoints; }
    public void setHistoricalDataPoints(Integer historicalDataPoints) { this.historicalDataPoints = historicalDataPoints; }
    public BigDecimal getAccuracyMetric() { return accuracyMetric; }
    public void setAccuracyMetric(BigDecimal accuracyMetric) { this.accuracyMetric = accuracyMetric; }
    public Double getGrowthTrendPercentage() { return growthTrendPercentage; }
    public void setGrowthTrendPercentage(Double growthTrendPercentage) { this.growthTrendPercentage = growthTrendPercentage; }
    public List<Map<String, Object>> getTrendData() { return trendData; }
    public void setTrendData(List<Map<String, Object>> trendData) { this.trendData = trendData; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
