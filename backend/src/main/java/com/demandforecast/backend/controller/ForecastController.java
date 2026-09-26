package com.demandforecast.backend.controller;

import com.demandforecast.backend.dto.forecast.ForecastGenerateRequest;
import com.demandforecast.backend.dto.forecast.ForecastResponse;
import com.demandforecast.backend.service.ForecastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forecasts")
@Tag(name = "Demand Forecasting", description = "AI/ML demand prediction and time-series analysis endpoints")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate AI demand forecast for a product")
    public ResponseEntity<ForecastResponse> generateForecast(@Valid @RequestBody ForecastGenerateRequest request) {
        return ResponseEntity.ok(forecastService.generateForecast(request));
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get forecast history for a product")
    public ResponseEntity<List<ForecastResponse>> getForecastHistory(@PathVariable Long productId) {
        return ResponseEntity.ok(forecastService.getForecastHistory(productId));
    }

    @GetMapping("/latest")
    @Operation(summary = "Get all latest demand forecasts across products")
    public ResponseEntity<List<ForecastResponse>> getAllLatestForecasts() {
        return ResponseEntity.ok(forecastService.getAllLatestForecasts());
    }

    @PostMapping("/batch-run")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Trigger automated batch forecasting across all products (Admin only)")
    public ResponseEntity<String> runBatchForecasting() {
        forecastService.runAutomatedBatchForecasting();
        return ResponseEntity.ok("Batch demand forecasting process completed.");
    }
}
