package com.demandforecast.backend.service;

import com.demandforecast.backend.dto.forecast.ForecastGenerateRequest;
import com.demandforecast.backend.dto.forecast.ForecastResponse;

import java.util.List;

public interface ForecastService {
    ForecastResponse generateForecast(ForecastGenerateRequest request);
    List<ForecastResponse> getForecastHistory(Long productId);
    List<ForecastResponse> getAllLatestForecasts();
    void runAutomatedBatchForecasting();
}
