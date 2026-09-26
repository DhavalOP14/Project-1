-- ====================================================================
-- AI-Powered Demand Forecasting and Inventory Optimization System
-- Database Schema: MySQL 8+
-- ====================================================================

CREATE DATABASE IF NOT EXISTS demand_inventory_db;
USE demand_inventory_db;

-- 1. Roles Table
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- 2. Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE RESTRICT
);

-- 3. Categories Table
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Products Table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    category_id BIGINT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    unit_cost DECIMAL(10, 2) NOT NULL,
    lead_time_days INT NOT NULL DEFAULT 7,
    order_cost DECIMAL(10, 2) NOT NULL DEFAULT 50.00,
    holding_cost_rate DECIMAL(5, 4) NOT NULL DEFAULT 0.20,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT
);

-- 5. Inventory Table
CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL UNIQUE,
    current_stock INT NOT NULL DEFAULT 0,
    safety_stock INT NOT NULL DEFAULT 0,
    reorder_point INT NOT NULL DEFAULT 0,
    optimal_stock_level INT NOT NULL DEFAULT 0,
    max_stock_level INT NOT NULL DEFAULT 0,
    status ENUM('IN_STOCK', 'LOW_STOCK', 'OUT_OF_STOCK', 'OVERSTOCKED') NOT NULL DEFAULT 'IN_STOCK',
    last_restocked_at TIMESTAMP NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

-- 6. Sales Table
CREATE TABLE IF NOT EXISTS sales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sale_code VARCHAR(60) NOT NULL UNIQUE,
    sale_date DATE NOT NULL,
    total_amount DECIMAL(12, 2) NOT NULL,
    notes VARCHAR(255),
    created_by BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_sales_user FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE SET NULL
);

-- 7. Sale Items Table (Historical transaction items)
CREATE TABLE IF NOT EXISTS sale_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sale_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(12, 2) NOT NULL,
    CONSTRAINT fk_sale_items_sale FOREIGN KEY (sale_id) REFERENCES sales (id) ON DELETE CASCADE,
    CONSTRAINT fk_sale_items_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE RESTRICT
);

-- 8. Demand Forecasts Table
CREATE TABLE IF NOT EXISTS demand_forecasts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    forecast_date DATE NOT NULL,
    target_period ENUM('WEEKLY', 'MONTHLY', 'QUARTERLY') NOT NULL DEFAULT 'MONTHLY',
    predicted_demand INT NOT NULL,
    confidence_lower INT NOT NULL,
    confidence_upper INT NOT NULL,
    algorithm_used VARCHAR(50) NOT NULL,
    historical_data_points INT NOT NULL,
    accuracy_metric DECIMAL(5, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_forecasts_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

-- 9. Reorder Recommendations Table
CREATE TABLE IF NOT EXISTS reorder_recommendations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    recommended_quantity INT NOT NULL,
    urgency ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') NOT NULL,
    reason VARCHAR(255) NOT NULL,
    estimated_cost DECIMAL(12, 2) NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'ORDERED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_reorder_product FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

-- Indexes for performance
CREATE INDEX idx_products_sku ON products (sku);
CREATE INDEX idx_sales_date ON sales (sale_date);
CREATE INDEX idx_sale_items_product ON sale_items (product_id);
CREATE INDEX idx_forecasts_product ON demand_forecasts (product_id, forecast_date);
