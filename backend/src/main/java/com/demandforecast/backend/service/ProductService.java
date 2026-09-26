package com.demandforecast.backend.service;

import com.demandforecast.backend.dto.product.CreateProductRequest;
import com.demandforecast.backend.dto.product.ProductResponse;
import com.demandforecast.backend.dto.product.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(CreateProductRequest request);
    ProductResponse updateProduct(Long id, UpdateProductRequest request);
    void deleteProduct(Long id);
}
