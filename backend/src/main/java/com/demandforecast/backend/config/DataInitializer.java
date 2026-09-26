package com.demandforecast.backend.config;

import com.demandforecast.backend.constant.RoleConstants;
import com.demandforecast.backend.dto.forecast.ForecastGenerateRequest;
import com.demandforecast.backend.entity.*;
import com.demandforecast.backend.enums.ForecastPeriod;
import com.demandforecast.backend.enums.StockStatus;
import com.demandforecast.backend.repository.*;
import com.demandforecast.backend.service.ForecastService;
import com.demandforecast.backend.service.OptimizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final SaleRepository saleRepository;
    private final PasswordEncoder passwordEncoder;
    private final OptimizationService optimizationService;
    private final ForecastService forecastService;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           InventoryRepository inventoryRepository,
                           SaleRepository saleRepository,
                           PasswordEncoder passwordEncoder,
                           OptimizationService optimizationService,
                           ForecastService forecastService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.saleRepository = saleRepository;
        this.passwordEncoder = passwordEncoder;
        this.optimizationService = optimizationService;
        this.forecastService = forecastService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (roleRepository.count() > 0) {
            log.info("Database already initialized. Skipping seed.");
            return;
        }

        log.info("Initializing default database records...");

        Role adminRole = roleRepository.save(Role.builder()
                .name(RoleConstants.ROLE_ADMIN)
                .description("System Administrator")
                .build());

        Role managerRole = roleRepository.save(Role.builder()
                .name(RoleConstants.ROLE_INVENTORY_MANAGER)
                .description("Inventory & Operations Manager")
                .build());

        User admin = userRepository.save(User.builder()
                .username("admin")
                .email("admin@inventoryai.com")
                .password(passwordEncoder.encode("Password@123"))
                .firstName("System")
                .lastName("Admin")
                .role(adminRole)
                .isActive(true)
                .build());

        User manager = userRepository.save(User.builder()
                .username("manager")
                .email("manager@inventoryai.com")
                .password(passwordEncoder.encode("Password@123"))
                .firstName("Inventory")
                .lastName("Manager")
                .role(managerRole)
                .isActive(true)
                .build());

        Category catElectronics = categoryRepository.save(Category.builder()
                .name("Electronics & Peripherals")
                .description("Hardware peripherals, audio devices, and docking hubs")
                .build());

        Category catOffice = categoryRepository.save(Category.builder()
                .name("Office Supplies")
                .description("Desk ergonomics, printer consumables, and accessories")
                .build());

        Category catApparel = categoryRepository.save(Category.builder()
                .name("Apparel & Safety")
                .description("Uniforms, safety gear, and corporate apparel")
                .build());

        Product p1 = productRepository.save(Product.builder()
                .sku("ELC-101")
                .name("Wireless Ergonomic Mouse")
                .description("Rechargeable dual-mode ergonomic optical mouse")
                .category(catElectronics)
                .unitPrice(BigDecimal.valueOf(49.99))
                .unitCost(BigDecimal.valueOf(24.50))
                .leadTimeDays(7)
                .orderCost(BigDecimal.valueOf(35.00))
                .holdingCostRate(BigDecimal.valueOf(0.18))
                .build());

        Product p2 = productRepository.save(Product.builder()
                .sku("ELC-102")
                .name("Mechanical RGB Keyboard")
                .description("Hot-swappable mechanical keyboard with quiet linear switches")
                .category(catElectronics)
                .unitPrice(BigDecimal.valueOf(89.00))
                .unitCost(BigDecimal.valueOf(45.00))
                .leadTimeDays(10)
                .orderCost(BigDecimal.valueOf(40.00))
                .holdingCostRate(BigDecimal.valueOf(0.20))
                .build());

        Product p3 = productRepository.save(Product.builder()
                .sku("ELC-103")
                .name("USB-C Multiport Dock 7-in-1")
                .description("Dual 4K HDMI, Gigabit Ethernet, 100W PD passthrough")
                .category(catElectronics)
                .unitPrice(BigDecimal.valueOf(69.95))
                .unitCost(BigDecimal.valueOf(32.00))
                .leadTimeDays(12)
                .orderCost(BigDecimal.valueOf(45.00))
                .holdingCostRate(BigDecimal.valueOf(0.15))
                .build());

        Product p4 = productRepository.save(Product.builder()
                .sku("OFF-201")
                .name("Thermal Receipt Paper (Box of 50)")
                .description("BPA-free high contrast receipt paper for POS terminals")
                .category(catOffice)
                .unitPrice(BigDecimal.valueOf(36.00))
                .unitCost(BigDecimal.valueOf(17.50))
                .leadTimeDays(5)
                .orderCost(BigDecimal.valueOf(20.00))
                .holdingCostRate(BigDecimal.valueOf(0.25))
                .build());

        Product p5 = productRepository.save(Product.builder()
                .sku("APP-301")
                .name("High-Visibility Safety Vest")
                .description("ANSI Class 2 reflective zippered vest with utility pockets")
                .category(catApparel)
                .unitPrice(BigDecimal.valueOf(25.00))
                .unitCost(BigDecimal.valueOf(11.00))
                .leadTimeDays(8)
                .orderCost(BigDecimal.valueOf(25.00))
                .holdingCostRate(BigDecimal.valueOf(0.15))
                .build());

        inventoryRepository.save(Inventory.builder()
                .product(p1).currentStock(22).safetyStock(20).reorderPoint(42).optimalStockLevel(110).maxStockLevel(180)
                .status(StockStatus.LOW_STOCK).lastRestockedAt(LocalDateTime.now().minusDays(20)).build());

        inventoryRepository.save(Inventory.builder()
                .product(p2).currentStock(85).safetyStock(18).reorderPoint(38).optimalStockLevel(90).maxStockLevel(150)
                .status(StockStatus.IN_STOCK).lastRestockedAt(LocalDateTime.now().minusDays(5)).build());

        inventoryRepository.save(Inventory.builder()
                .product(p3).currentStock(4).safetyStock(15).reorderPoint(32).optimalStockLevel(75).maxStockLevel(130)
                .status(StockStatus.LOW_STOCK).lastRestockedAt(LocalDateTime.now().minusDays(30)).build());

        inventoryRepository.save(Inventory.builder()
                .product(p4).currentStock(230).safetyStock(25).reorderPoint(50).optimalStockLevel(120).maxStockLevel(200)
                .status(StockStatus.OVERSTOCKED).lastRestockedAt(LocalDateTime.now().minusDays(2)).build());

        inventoryRepository.save(Inventory.builder()
                .product(p5).currentStock(0).safetyStock(12).reorderPoint(28).optimalStockLevel(60).maxStockLevel(100)
                .status(StockStatus.OUT_OF_STOCK).lastRestockedAt(LocalDateTime.now().minusDays(45)).build());

        createSampleSale("ORD-202607-01", LocalDate.now().minusMonths(2).minusDays(15), admin, List.of(p1, p2), List.of(12, 6));
        createSampleSale("ORD-202607-02", LocalDate.now().minusMonths(2).minusDays(7), manager, List.of(p3, p4), List.of(8, 15));
        createSampleSale("ORD-202608-01", LocalDate.now().minusMonths(1).minusDays(20), admin, List.of(p1, p3), List.of(16, 10));
        createSampleSale("ORD-202608-02", LocalDate.now().minusMonths(1).minusDays(10), manager, List.of(p2, p5), List.of(14, 20));
        createSampleSale("ORD-202609-01", LocalDate.now().minusDays(18), admin, List.of(p1, p4), List.of(22, 18));
        createSampleSale("ORD-202609-02", LocalDate.now().minusDays(8), manager, List.of(p2, p3), List.of(18, 12));
        createSampleSale("ORD-202609-03", LocalDate.now().minusDays(2), admin, List.of(p1, p5), List.of(14, 15));

        for (Product p : List.of(p1, p2, p3, p4, p5)) {
            optimizationService.recalculateInventoryOptimization(p.getId());
            try {
                forecastService.generateForecast(ForecastGenerateRequest.builder()
                        .productId(p.getId())
                        .targetPeriod(ForecastPeriod.MONTHLY)
                        .preferredAlgorithm("ENSEMBLE")
                        .build());
            } catch (Exception ex) {
                log.warn("Forecasting init notice for {}: {}", p.getName(), ex.getMessage());
            }
        }

        log.info("Database initialization completed successfully!");
    }

    private void createSampleSale(String code, LocalDate date, User user, List<Product> products, List<Integer> quantities) {
        Sale sale = Sale.builder()
                .saleCode(code)
                .saleDate(date)
                .totalAmount(BigDecimal.ZERO)
                .notes("Standard customer order")
                .createdBy(user)
                .items(new ArrayList<>())
                .build();

        BigDecimal grandTotal = BigDecimal.ZERO;
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            int qty = quantities.get(i);
            BigDecimal subtotal = p.getUnitPrice().multiply(BigDecimal.valueOf(qty));
            grandTotal = grandTotal.add(subtotal);

            sale.getItems().add(SaleItem.builder()
                    .sale(sale)
                    .product(p)
                    .quantity(qty)
                    .unitPrice(p.getUnitPrice())
                    .subtotal(subtotal)
                    .build());
        }
        sale.setTotalAmount(grandTotal);
        saleRepository.save(sale);
    }
}
