package com.demandforecast.backend.entity;

import com.demandforecast.backend.enums.ForecastPeriod;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "demand_forecasts")
public class DemandForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "forecast_date", nullable = false)
    private LocalDate forecastDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_period", nullable = false, length = 30)
    private ForecastPeriod targetPeriod = ForecastPeriod.MONTHLY;

    @Column(name = "predicted_demand", nullable = false)
    private Integer predictedDemand;

    @Column(name = "confidence_lower", nullable = false)
    private Integer confidenceLower;

    @Column(name = "confidence_upper", nullable = false)
    private Integer confidenceUpper;

    @Column(name = "algorithm_used", nullable = false, length = 50)
    private String algorithmUsed;

    @Column(name = "historical_data_points", nullable = false)
    private Integer historicalDataPoints;

    @Column(name = "accuracy_metric", precision = 5, scale = 2)
    private BigDecimal accuracyMetric;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public DemandForecast() {}

    public DemandForecast(Long id, Product product, LocalDate forecastDate, ForecastPeriod targetPeriod,
                          Integer predictedDemand, Integer confidenceLower, Integer confidenceUpper,
                          String algorithmUsed, Integer historicalDataPoints, BigDecimal accuracyMetric) {
        this.id = id;
        this.product = product;
        this.forecastDate = forecastDate;
        this.targetPeriod = targetPeriod != null ? targetPeriod : ForecastPeriod.MONTHLY;
        this.predictedDemand = predictedDemand;
        this.confidenceLower = confidenceLower;
        this.confidenceUpper = confidenceUpper;
        this.algorithmUsed = algorithmUsed;
        this.historicalDataPoints = historicalDataPoints;
        this.accuracyMetric = accuracyMetric;
    }

    public static DemandForecastBuilder builder() { return new DemandForecastBuilder(); }

    public static class DemandForecastBuilder {
        private Long id;
        private Product product;
        private LocalDate forecastDate;
        private ForecastPeriod targetPeriod = ForecastPeriod.MONTHLY;
        private Integer predictedDemand;
        private Integer confidenceLower;
        private Integer confidenceUpper;
        private String algorithmUsed;
        private Integer historicalDataPoints;
        private BigDecimal accuracyMetric;

        public DemandForecastBuilder id(Long id) { this.id = id; return this; }
        public DemandForecastBuilder product(Product product) { this.product = product; return this; }
        public DemandForecastBuilder forecastDate(LocalDate forecastDate) { this.forecastDate = forecastDate; return this; }
        public DemandForecastBuilder targetPeriod(ForecastPeriod targetPeriod) { this.targetPeriod = targetPeriod; return this; }
        public DemandForecastBuilder predictedDemand(Integer predictedDemand) { this.predictedDemand = predictedDemand; return this; }
        public DemandForecastBuilder confidenceLower(Integer confidenceLower) { this.confidenceLower = confidenceLower; return this; }
        public DemandForecastBuilder confidenceUpper(Integer confidenceUpper) { this.confidenceUpper = confidenceUpper; return this; }
        public DemandForecastBuilder algorithmUsed(String algorithmUsed) { this.algorithmUsed = algorithmUsed; return this; }
        public DemandForecastBuilder historicalDataPoints(Integer historicalDataPoints) { this.historicalDataPoints = historicalDataPoints; return this; }
        public DemandForecastBuilder accuracyMetric(BigDecimal accuracyMetric) { this.accuracyMetric = accuracyMetric; return this; }
        public DemandForecast build() { return new DemandForecast(id, product, forecastDate, targetPeriod, predictedDemand, confidenceLower, confidenceUpper, algorithmUsed, historicalDataPoints, accuracyMetric); }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
}
