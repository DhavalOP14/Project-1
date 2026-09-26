# AI-Powered Demand Forecasting and Inventory Optimization System

A Java Full Stack web application designed to help businesses predict future product demand, optimize inventory holding levels, eliminate stockouts, and automate Economic Order Quantity (EOQ) replenishment.

---

## 🏗️ Architecture & Technology Stack

* **Backend**: Java 21+ / Spring Boot 3
  * **Spring Security & JJWT**: Stateless JWT authentication with role-based authorization (`ROLE_ADMIN`, `ROLE_INVENTORY_MANAGER`)
  * **Spring Data JPA & Hibernate**: Relational persistence and automated schema synchronization
  * **SpringDoc OpenAPI (Swagger UI)**: Interactive REST API documentation at `/swagger-ui.html`
  * **AI/ML Forecasting Engine**: Statistical and Machine Learning algorithms (Holt-Winters / Exponential Smoothing, Linear Trend Regression, Weighted Moving Average, Hybrid Ensemble)
  * **Optimization Module**: Automated Lead Time Demand, Safety Stock, Reorder Point (ROP), and Economic Order Quantity (EOQ)

* **Frontend**: React 18 + Vite
  * **Tailwind CSS 4**: Modern responsive interface
  * **Recharts**: Interactive time-series forecast visualizations with 95% confidence bounds
  * **Lucide React**: Clean iconography
  * **Axios**: Centralized API client with JWT interceptors and auto-logout on token expiration

* **Database**: MySQL 8.0+
  * Complete schemas and seed data located in `/database`

---

## 📂 Project Directory Structure

```text
Project-1/
├── backend/
│   ├── src/main/java/com/demandforecast/backend/
│   │   ├── config/             # CorsConfig, OpenApiConfig, DataInitializer
│   │   ├── constant/           # RoleConstants
│   │   ├── controller/         # Auth, Category, Product, Inventory, Sale, Forecast, Reorder, Dashboard
│   │   ├── dto/                # Grouped by module (auth, category, product, inventory, sale, forecast, reorder, dashboard)
│   │   ├── entity/             # User, Role, Category, Product, Inventory, Sale, SaleItem, DemandForecast, ReorderRecommendation
│   │   ├── enums/              # StockStatus, UrgencyLevel, RecommendationStatus, ForecastPeriod, ForecastMethod
│   │   ├── exception/          # GlobalExceptionHandler, ResourceNotFound, BadRequest, DuplicateResource
│   │   ├── repository/         # Spring Data JPA repositories with time-series historical sales queries
│   │   ├── security/           # JwtService, JwtAuthenticationFilter, SecurityConfig, CustomUserDetails
│   │   ├── service/            # Business logic interfaces
│   │   │   └── impl/           # Implementations including OptimizationService and ForecastService
│   │   └── DemandForecastApplication.java
│   ├── src/main/resources/     # application.properties, application-dev.properties
│   ├── mvnw & mvnw.cmd
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── common/         # InputField, SelectField, ProtectedRoute, Pagination, SearchBar, StatCard
│   │   │   ├── layout/         # DashboardLayout, Sidebar, Header
│   │   │   ├── product/        # ProductForm, ProductTable
│   │   │   ├── inventory/      # InventoryTable, StockUpdateModal
│   │   │   ├── sale/           # SaleForm, SaleTable
│   │   │   ├── forecast/       # ForecastChart, ForecastTable
│   │   │   └── reorder/        # ReorderTable
│   │   ├── pages/
│   │   │   ├── auth/           # Login.jsx
│   │   │   ├── dashboard/      # Dashboard.jsx (KPIs, Charts, Alerts)
│   │   │   ├── product/        # ProductList.jsx, AddProduct.jsx, EditProduct.jsx
│   │   │   ├── inventory/      # InventoryList.jsx
│   │   │   ├── sale/           # SalesList.jsx, AddSale.jsx
│   │   │   ├── forecast/       # ForecastView.jsx (Interactive AI Console)
│   │   │   └── reorder/        # ReorderRecommendations.jsx
│   │   ├── routes/             # AppRoutes.jsx
│   │   ├── services/           # api.js, authService, productService, inventoryService, etc.
│   │   ├── context/            # AuthContext.jsx
│   │   ├── App.jsx
│   │   └── main.jsx
│   ├── package.json
│   └── vite.config.js
│
├── database/
│   ├── schema.sql              # MySQL DDL script
│   ├── data.sql                # Rich demo dataset with realistic historical sales & forecasts
│   └── README.md
│
├── docker-compose.yml
├── .gitignore
└── README.md
```

---

## 🚀 Running the Application

### 1. Default Demo Credentials

| Username | Password | Role | Access Level |
|:---|:---|:---|:---|
| `admin` | `Password@123` | `ROLE_ADMIN` | Full administrative control, catalog modifications, batch forecasting |
| `manager` | `Password@123` | `ROLE_INVENTORY_MANAGER` | Inventory tracking, manual stock adjustments, order replenishments |

---

### 2. Backend Setup

```bash
cd backend
# Run with Spring Boot
mvnw.cmd spring-boot:run
```
* API Documentation (Swagger UI): `http://localhost:8080/swagger-ui.html`

---

### 3. Frontend Setup

```bash
cd frontend
cmd.exe /c "npm install"
cmd.exe /c "npm run dev"
```
* Web Dashboard: `http://localhost:5173`

---

## 📈 Machine Learning & Optimization Formulas

* **Safety Stock**: $$SS = Z \times \sigma_{\text{daily}} \times \sqrt{L}$$
* **Reorder Point**: $$ROP = (d_{\text{daily}} \times L) + SS$$
* **Economic Order Quantity (EOQ)**: $$EOQ = \sqrt{\frac{2 \times D \times S}{H}}$$
* **Confidence Interval**: $$\hat{y} \pm 1.96 \times \text{StdErr}$$
