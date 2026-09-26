package com.demandforecast.backend.controller;

import com.demandforecast.backend.dto.sale.RecordSaleRequest;
import com.demandforecast.backend.dto.sale.SaleResponse;
import com.demandforecast.backend.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sales", description = "Endpoints for logging sales orders and tracking historical demand")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    @Operation(summary = "Record a new sale transaction and adjust inventory")
    public ResponseEntity<SaleResponse> recordSale(
            @Valid @RequestBody RecordSaleRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails != null ? userDetails.getUsername() : null;
        return new ResponseEntity<>(saleService.recordSale(request, username), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all historical sales records")
    public ResponseEntity<List<SaleResponse>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get details of a specific sale transaction")
    public ResponseEntity<SaleResponse> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }
}
