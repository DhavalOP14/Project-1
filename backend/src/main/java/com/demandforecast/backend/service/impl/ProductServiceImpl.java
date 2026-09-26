package com.demandforecast.backend.service.impl;

import com.demandforecast.backend.dto.category.CategoryResponse;
import com.demandforecast.backend.dto.product.CreateProductRequest;
import com.demandforecast.backend.dto.product.ProductResponse;
import com.demandforecast.backend.dto.product.UpdateProductRequest;
import com.demandforecast.backend.entity.Category;
import com.demandforecast.backend.entity.Inventory;
import com.demandforecast.backend.entity.Product;
import com.demandforecast.backend.enums.StockStatus;
import com.demandforecast.backend.exception.DuplicateResourceException;
import com.demandforecast.backend.exception.ResourceNotFoundException;
import com.demandforecast.backend.repository.CategoryRepository;
import com.demandforecast.backend.repository.InventoryRepository;
import com.demandforecast.backend.repository.ProductRepository;
import com.demandforecast.backend.service.OptimizationService;
import com.demandforecast.backend.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryRepository inventoryRepository;
    private final OptimizationService optimizationService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                              InventoryRepository inventoryRepository, OptimizationService optimizationService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.inventoryRepository = inventoryRepository;
        this.optimizationService = optimizationService;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return mapToResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new DuplicateResourceException("Product with SKU already exists: " + request.getSku());
        }
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        Product product = Product.builder()
                .sku(request.getSku().toUpperCase().trim())
                .name(request.getName().trim())
                .description(request.getDescription())
                .category(category)
                .unitPrice(request.getUnitPrice())
                .unitCost(request.getUnitCost())
                .leadTimeDays(request.getLeadTimeDays() != null ? request.getLeadTimeDays() : 7)
                .orderCost(request.getOrderCost() != null ? request.getOrderCost() : BigDecimal.valueOf(50.00))
                .holdingCostRate(request.getHoldingCostRate() != null ? request.getHoldingCostRate() : BigDecimal.valueOf(0.20))
                .build();
        product = productRepository.save(product);

        int initialStock = request.getInitialStock() != null ? request.getInitialStock() : 0;
        Inventory inventory = Inventory.builder()
                .product(product)
                .currentStock(initialStock)
                .safetyStock(10)
                .reorderPoint(25)
                .optimalStockLevel(80)
                .maxStockLevel(150)
                .status(initialStock == 0 ? StockStatus.OUT_OF_STOCK : StockStatus.IN_STOCK)
                .lastRestockedAt(initialStock > 0 ? LocalDateTime.now() : null)
                .build();
        inventoryRepository.save(inventory);
        optimizationService.recalculateInventoryOptimization(product.getId());
        return mapToResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        product.setName(request.getName().trim());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        product.setUnitPrice(request.getUnitPrice());
        product.setUnitCost(request.getUnitCost());
        product.setLeadTimeDays(request.getLeadTimeDays());
        if (request.getOrderCost() != null) product.setOrderCost(request.getOrderCost());
        if (request.getHoldingCostRate() != null) product.setHoldingCostRate(request.getHoldingCostRate());

        product = productRepository.save(product);
        optimizationService.recalculateInventoryOptimization(product.getId());
        return mapToResponse(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {
        Inventory inventory = inventoryRepository.findByProductId(product.getId()).orElse(null);
        int currentStock = inventory != null ? inventory.getCurrentStock() : 0;
        String status = inventory != null ? inventory.getStatus().name() : StockStatus.OUT_OF_STOCK.name();

        CategoryResponse catDto = CategoryResponse.builder()
                .id(product.getCategory().getId())
                .name(product.getCategory().getName())
                .description(product.getCategory().getDescription())
                .createdAt(product.getCategory().getCreatedAt())
                .build();

        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .category(catDto)
                .unitPrice(product.getUnitPrice())
                .unitCost(product.getUnitCost())
                .leadTimeDays(product.getLeadTimeDays())
                .orderCost(product.getOrderCost())
                .holdingCostRate(product.getHoldingCostRate())
                .currentStock(currentStock)
                .stockStatus(status)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
