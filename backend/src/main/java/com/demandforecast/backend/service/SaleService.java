package com.demandforecast.backend.service;

import com.demandforecast.backend.dto.sale.RecordSaleRequest;
import com.demandforecast.backend.dto.sale.SaleResponse;

import java.util.List;

public interface SaleService {
    SaleResponse recordSale(RecordSaleRequest request, String username);
    List<SaleResponse> getAllSales();
    SaleResponse getSaleById(Long id);
}
