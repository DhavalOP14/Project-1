package com.demandforecast.backend.dto.dashboard;

import com.demandforecast.backend.dto.forecast.ForecastResponse;
import com.demandforecast.backend.dto.inventory.InventoryResponse;
import com.demandforecast.backend.dto.reorder.ReorderRecommendationResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardSummaryResponse {
    private long totalProducts;
    private BigDecimal totalInventoryValue;
    private long inStockCount;
    private long lowStockCount;
    private long outOfStockCount;
    private long overstockedCount;
    private long totalSalesCount;
    private Double totalSalesRevenue;
    private long pendingReordersCount;
    private long criticalAlertsCount;
    private List<InventoryResponse> lowStockAlerts;
    private List<ForecastResponse> recentForecasts;
    private List<ReorderRecommendationResponse> topReorderSuggestions;
    private List<Map<String, Object>> monthlySalesTrend;

    public DashboardSummaryResponse() {}

    public DashboardSummaryResponse(long totalProducts, BigDecimal totalInventoryValue, long inStockCount,
                                    long lowStockCount, long outOfStockCount, long overstockedCount,
                                    long totalSalesCount, Double totalSalesRevenue, long pendingReordersCount,
                                    long criticalAlertsCount, List<InventoryResponse> lowStockAlerts,
                                    List<ForecastResponse> recentForecasts,
                                    List<ReorderRecommendationResponse> topReorderSuggestions,
                                    List<Map<String, Object>> monthlySalesTrend) {
        this.totalProducts = totalProducts;
        this.totalInventoryValue = totalInventoryValue;
        this.inStockCount = inStockCount;
        this.lowStockCount = lowStockCount;
        this.outOfStockCount = outOfStockCount;
        this.overstockedCount = overstockedCount;
        this.totalSalesCount = totalSalesCount;
        this.totalSalesRevenue = totalSalesRevenue;
        this.pendingReordersCount = pendingReordersCount;
        this.criticalAlertsCount = criticalAlertsCount;
        this.lowStockAlerts = lowStockAlerts;
        this.recentForecasts = recentForecasts;
        this.topReorderSuggestions = topReorderSuggestions;
        this.monthlySalesTrend = monthlySalesTrend;
    }

    public static DashboardSummaryResponseBuilder builder() { return new DashboardSummaryResponseBuilder(); }

    public static class DashboardSummaryResponseBuilder {
        private long totalProducts;
        private BigDecimal totalInventoryValue;
        private long inStockCount;
        private long lowStockCount;
        private long outOfStockCount;
        private long overstockedCount;
        private long totalSalesCount;
        private Double totalSalesRevenue;
        private long pendingReordersCount;
        private long criticalAlertsCount;
        private List<InventoryResponse> lowStockAlerts;
        private List<ForecastResponse> recentForecasts;
        private List<ReorderRecommendationResponse> topReorderSuggestions;
        private List<Map<String, Object>> monthlySalesTrend;

        public DashboardSummaryResponseBuilder totalProducts(long totalProducts) { this.totalProducts = totalProducts; return this; }
        public DashboardSummaryResponseBuilder totalInventoryValue(BigDecimal totalInventoryValue) { this.totalInventoryValue = totalInventoryValue; return this; }
        public DashboardSummaryResponseBuilder inStockCount(long inStockCount) { this.inStockCount = inStockCount; return this; }
        public DashboardSummaryResponseBuilder lowStockCount(long lowStockCount) { this.lowStockCount = lowStockCount; return this; }
        public DashboardSummaryResponseBuilder outOfStockCount(long outOfStockCount) { this.outOfStockCount = outOfStockCount; return this; }
        public DashboardSummaryResponseBuilder overstockedCount(long overstockedCount) { this.overstockedCount = overstockedCount; return this; }
        public DashboardSummaryResponseBuilder totalSalesCount(long totalSalesCount) { this.totalSalesCount = totalSalesCount; return this; }
        public DashboardSummaryResponseBuilder totalSalesRevenue(Double totalSalesRevenue) { this.totalSalesRevenue = totalSalesRevenue; return this; }
        public DashboardSummaryResponseBuilder pendingReordersCount(long pendingReordersCount) { this.pendingReordersCount = pendingReordersCount; return this; }
        public DashboardSummaryResponseBuilder criticalAlertsCount(long criticalAlertsCount) { this.criticalAlertsCount = criticalAlertsCount; return this; }
        public DashboardSummaryResponseBuilder lowStockAlerts(List<InventoryResponse> lowStockAlerts) { this.lowStockAlerts = lowStockAlerts; return this; }
        public DashboardSummaryResponseBuilder recentForecasts(List<ForecastResponse> recentForecasts) { this.recentForecasts = recentForecasts; return this; }
        public DashboardSummaryResponseBuilder topReorderSuggestions(List<ReorderRecommendationResponse> topReorderSuggestions) { this.topReorderSuggestions = topReorderSuggestions; return this; }
        public DashboardSummaryResponseBuilder monthlySalesTrend(List<Map<String, Object>> monthlySalesTrend) { this.monthlySalesTrend = monthlySalesTrend; return this; }
        public DashboardSummaryResponse build() { return new DashboardSummaryResponse(totalProducts, totalInventoryValue, inStockCount, lowStockCount, outOfStockCount, overstockedCount, totalSalesCount, totalSalesRevenue, pendingReordersCount, criticalAlertsCount, lowStockAlerts, recentForecasts, topReorderSuggestions, monthlySalesTrend); }
    }

    public long getTotalProducts() { return totalProducts; }
    public void setTotalProducts(long totalProducts) { this.totalProducts = totalProducts; }
    public BigDecimal getTotalInventoryValue() { return totalInventoryValue; }
    public void setTotalInventoryValue(BigDecimal totalInventoryValue) { this.totalInventoryValue = totalInventoryValue; }
    public long getInStockCount() { return inStockCount; }
    public void setInStockCount(long inStockCount) { this.inStockCount = inStockCount; }
    public long getLowStockCount() { return lowStockCount; }
    public void setLowStockCount(long lowStockCount) { this.lowStockCount = lowStockCount; }
    public long getOutOfStockCount() { return outOfStockCount; }
    public void setOutOfStockCount(long outOfStockCount) { this.outOfStockCount = outOfStockCount; }
    public long getOverstockedCount() { return overstockedCount; }
    public void setOverstockedCount(long overstockedCount) { this.overstockedCount = overstockedCount; }
    public long getTotalSalesCount() { return totalSalesCount; }
    public void setTotalSalesCount(long totalSalesCount) { this.totalSalesCount = totalSalesCount; }
    public Double getTotalSalesRevenue() { return totalSalesRevenue; }
    public void setTotalSalesRevenue(Double totalSalesRevenue) { this.totalSalesRevenue = totalSalesRevenue; }
    public long getPendingReordersCount() { return pendingReordersCount; }
    public void setPendingReordersCount(long pendingReordersCount) { this.pendingReordersCount = pendingReordersCount; }
    public long getCriticalAlertsCount() { return criticalAlertsCount; }
    public void setCriticalAlertsCount(long criticalAlertsCount) { this.criticalAlertsCount = criticalAlertsCount; }
    public List<InventoryResponse> getLowStockAlerts() { return lowStockAlerts; }
    public void setLowStockAlerts(List<InventoryResponse> lowStockAlerts) { this.lowStockAlerts = lowStockAlerts; }
    public List<ForecastResponse> getRecentForecasts() { return recentForecasts; }
    public void setRecentForecasts(List<ForecastResponse> recentForecasts) { this.recentForecasts = recentForecasts; }
    public List<ReorderRecommendationResponse> getTopReorderSuggestions() { return topReorderSuggestions; }
    public void setTopReorderSuggestions(List<ReorderRecommendationResponse> topReorderSuggestions) { this.topReorderSuggestions = topReorderSuggestions; }
    public List<Map<String, Object>> getMonthlySalesTrend() { return monthlySalesTrend; }
    public void setMonthlySalesTrend(List<Map<String, Object>> monthlySalesTrend) { this.monthlySalesTrend = monthlySalesTrend; }
}
