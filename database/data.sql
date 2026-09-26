-- ====================================================================
-- AI-Powered Demand Forecasting and Inventory Optimization System
-- Initial Seed Data: MySQL 8+
-- ====================================================================

USE demand_inventory_db;

-- 1. Seed Roles
INSERT INTO roles (id, name, description) VALUES
(1, 'ROLE_ADMIN', 'Full system administrator with user, product, inventory, and forecasting access'),
(2, 'ROLE_INVENTORY_MANAGER', 'Inventory manager responsible for stock operations, sales logging, and purchase orders')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- 2. Seed Users (Default password for both: Password@123)
-- BCrypt hash for 'Password@123'
INSERT INTO users (id, username, email, password, first_name, last_name, role_id, is_active) VALUES
(1, 'admin', 'admin@inventoryai.com', '$2a$10$wE73q1eE5l2O2c9wTkgE1uB9h3e4U7d9v5q9x0c1v2b3n4m5l6k7j', 'Admin', 'User', 1, TRUE),
(2, 'manager', 'manager@inventoryai.com', '$2a$10$wE73q1eE5l2O2c9wTkgE1uB9h3e4U7d9v5q9x0c1v2b3n4m5l6k7j', 'Inventory', 'Manager', 2, TRUE)
ON DUPLICATE KEY UPDATE username=VALUES(username);

-- 3. Seed Categories
INSERT INTO categories (id, name, description) VALUES
(1, 'Electronics & Gadgets', 'Computing hardware, accessories, and audio equipment'),
(2, 'Office Supplies', 'Workplace stationery, paper, and printing consumables'),
(3, 'Apparel & Wearables', 'Corporate uniforms, hoodies, and ergonomic accessories'),
(4, 'Industrial Components', 'Sensors, cables, electrical relays, and replacement components')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- 4. Seed Products
INSERT INTO products (id, sku, name, description, category_id, unit_price, unit_cost, lead_time_days, order_cost, holding_cost_rate) VALUES
(1, 'ELC-1001', 'Wireless Ergonomic Mouse', 'Ergonomic optical mouse with dual Bluetooth & 2.4GHz connectivity', 1, 45.00, 22.50, 7, 30.00, 0.18),
(2, 'ELC-1002', 'Mechanical RGB Keyboard', 'Tactile switch mechanical gaming and office keyboard', 1, 85.00, 48.00, 10, 45.00, 0.20),
(3, 'ELC-1003', 'USB-C Multiport Dock 7-in-1', 'Aluminum multiport hub with 4K HDMI, 100W PD, and USB 3.2 ports', 1, 65.00, 32.00, 12, 40.00, 0.15),
(4, 'OFF-2001', 'Heavy-Duty Thermal Receipt Paper (Pack of 50)', 'BPA-free high sensitivity thermal roll paper for POS systems', 2, 38.00, 18.00, 5, 20.00, 0.25),
(5, 'OFF-2002', 'Ergonomic Mesh Lumbar Support', 'Breathable lumbar cushion for office chairs', 2, 28.00, 12.50, 8, 25.00, 0.20),
(6, 'APP-3001', 'Unisex Corporate Tech Fleece Jacket', 'Water-resistant thermal fleece jacket with secure zip pockets', 3, 75.00, 38.00, 14, 50.00, 0.22),
(7, 'IND-4001', 'Digital Precision Caliper 150mm', 'Stainless steel electronic micrometer measuring tool', 4, 32.00, 15.00, 6, 25.00, 0.15)
ON DUPLICATE KEY UPDATE sku=VALUES(sku);

-- 5. Seed Inventory Levels
INSERT INTO inventory (id, product_id, current_stock, safety_stock, reorder_point, optimal_stock_level, max_stock_level, status, last_restocked_at) VALUES
(1, 1, 24, 25, 45, 120, 200, 'LOW_STOCK', '2026-09-01 10:00:00'),
(2, 2, 78, 20, 40, 90, 150, 'IN_STOCK', '2026-09-10 14:30:00'),
(3, 3, 5, 18, 35, 80, 140, 'CRITICAL', '2026-08-20 09:15:00'),
(4, 4, 210, 30, 60, 150, 220, 'OVERSTOCKED', '2026-09-15 16:45:00'),
(5, 5, 42, 15, 30, 70, 120, 'IN_STOCK', '2026-09-05 11:20:00'),
(6, 6, 0, 12, 25, 60, 100, 'OUT_OF_STOCK', '2026-08-10 08:00:00'),
(7, 7, 55, 15, 28, 65, 110, 'IN_STOCK', '2026-09-12 13:10:00')
ON DUPLICATE KEY UPDATE current_stock=VALUES(current_stock);

-- 6. Historical Sales Data (Representative sample for time-series demand models)
INSERT INTO sales (id, sale_code, sale_date, total_amount, notes, created_by) VALUES
(1, 'ORD-202607-001', '2026-07-05', 450.00, 'Online Store batch order', 1),
(2, 'ORD-202607-002', '2026-07-12', 1245.00, 'Corporate client bulk purchase', 2),
(3, 'ORD-202607-003', '2026-07-20', 890.00, 'Retail replenishment order', 1),
(4, 'ORD-202607-004', '2026-07-28', 1650.00, 'Monthly office supplier order', 2),
(5, 'ORD-202608-001', '2026-08-04', 780.00, 'Walk-in wholesale purchase', 1),
(6, 'ORD-202608-002', '2026-08-11', 2100.00, 'Regional branch restocking', 2),
(7, 'ORD-202608-003', '2026-08-18', 1420.00, 'Educational lab equip order', 1),
(8, 'ORD-202608-004', '2026-08-25', 960.00, 'End-of-month clearance items', 2),
(9, 'ORD-202609-001', '2026-09-03', 1830.00, 'Quarterly IT accessories refresh', 1),
(10, 'ORD-202609-002', '2026-09-10', 2450.00, 'B2B Client hardware upgrade', 2),
(11, 'ORD-202609-003', '2026-09-17', 1580.00, 'Mid-month supply top-up', 1),
(12, 'ORD-202609-004', '2026-09-24', 1190.00, 'Weekly distributor order', 2)
ON DUPLICATE KEY UPDATE sale_code=VALUES(sale_code);

-- 7. Historical Sale Items
INSERT INTO sale_items (id, sale_id, product_id, quantity, unit_price, subtotal) VALUES
(1, 1, 1, 10, 45.00, 450.00),
(2, 2, 1, 15, 45.00, 675.00),
(3, 2, 2, 5, 85.00, 425.00),
(4, 2, 4, 3, 38.00, 114.00),
(5, 3, 3, 10, 65.00, 650.00),
(6, 3, 5, 8, 28.00, 224.00),
(7, 4, 1, 20, 45.00, 900.00),
(8, 4, 2, 8, 85.00, 680.00),
(9, 5, 7, 15, 32.00, 480.00),
(10, 5, 4, 8, 38.00, 304.00),
(11, 6, 2, 18, 85.00, 1530.00),
(12, 6, 3, 8, 65.00, 520.00),
(13, 7, 1, 16, 45.00, 720.00),
(14, 7, 6, 9, 75.00, 675.00),
(15, 8, 5, 15, 28.00, 420.00),
(16, 8, 7, 16, 32.00, 512.00),
(17, 9, 1, 22, 45.00, 990.00),
(18, 9, 3, 12, 65.00, 780.00),
(19, 10, 2, 20, 85.00, 1700.00),
(20, 10, 6, 10, 75.00, 750.00),
(21, 11, 1, 18, 45.00, 810.00),
(22, 11, 4, 15, 38.00, 570.00),
(23, 12, 5, 14, 28.00, 392.00),
(24, 12, 7, 22, 32.00, 704.00)
ON DUPLICATE KEY UPDATE quantity=VALUES(quantity);

-- 8. Seed Sample Forecast Records
INSERT INTO demand_forecasts (id, product_id, forecast_date, target_period, predicted_demand, confidence_lower, confidence_upper, algorithm_used, historical_data_points, accuracy_metric) VALUES
(1, 1, '2026-10-01', 'MONTHLY', 85, 74, 96, 'EXPONENTIAL_SMOOTHING', 24, 94.50),
(2, 2, '2026-10-01', 'MONTHLY', 55, 48, 62, 'LINEAR_TREND', 20, 92.10),
(3, 3, '2026-10-01', 'MONTHLY', 42, 35, 49, 'HOLT_WINTERS_SEASONAL', 18, 91.80),
(4, 6, '2026-10-01', 'MONTHLY', 38, 30, 46, 'ENSEMBLE', 16, 89.40)
ON DUPLICATE KEY UPDATE predicted_demand=VALUES(predicted_demand);

-- 9. Seed Reorder Recommendations
INSERT INTO reorder_recommendations (id, product_id, recommended_quantity, urgency, reason, estimated_cost, status) VALUES
(1, 3, 75, 'CRITICAL', 'Current stock (5) is critically below Safety Stock (18) and Lead Time Demand', 2400.00, 'PENDING'),
(2, 6, 60, 'CRITICAL', 'Product is completely OUT_OF_STOCK with high upcoming forecasted demand (38)', 2280.00, 'PENDING'),
(3, 1, 95, 'HIGH', 'Current stock (24) is below Reorder Point (45) with rising trend', 2137.50, 'PENDING')
ON DUPLICATE KEY UPDATE recommended_quantity=VALUES(recommended_quantity);
