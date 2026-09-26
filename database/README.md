# Database Configuration & Scripts

This folder contains database definition and seeding scripts for the **AI-Powered Demand Forecasting and Inventory Optimization System**.

## Files
* `schema.sql`: Table definitions, foreign key constraints, indexes, and enums for MySQL 8+.
* `data.sql`: Seed data for Roles, Users, Categories, Products, Inventory levels, Historical Sales, Forecasts, and Reorder Recommendations.

## Setup Instructions

### MySQL CLI:
```bash
mysql -u root -p < schema.sql
mysql -u root -p < data.sql
```

### Pre-configured Default Credentials:
| Username | Password | Role | Description |
|:---|:---|:---|:---|
| `admin` | `Password@123` | `ROLE_ADMIN` | Complete access to user management, configurations, products, forecasting and inventory |
| `manager` | `Password@123` | `ROLE_INVENTORY_MANAGER` | Inventory tracking, sales logging, order replenishment |
