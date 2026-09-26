import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import DashboardLayout from "../components/layout/DashboardLayout";
import ProtectedRoute from "../components/common/ProtectedRoute";

import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";
import Dashboard from "../pages/dashboard/Dashboard";
import ProductList from "../pages/product/ProductList";
import AddProduct from "../pages/product/AddProduct";
import EditProduct from "../pages/product/EditProduct";
import InventoryList from "../pages/inventory/InventoryList";
import SalesList from "../pages/sale/SalesList";
import AddSale from "../pages/sale/AddSale";
import ForecastView from "../pages/forecast/ForecastView";
import ReorderRecommendations from "../pages/reorder/ReorderRecommendations";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      {/* Protected Application Routes */}
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <DashboardLayout />
          </ProtectedRoute>
        }
      >
        <Route index element={<Navigate to="/dashboard" replace />} />
        <Route path="dashboard" element={<Dashboard />} />

        {/* Product routes */}
        <Route path="products" element={<ProductList />} />
        <Route
          path="products/add"
          element={
            <ProtectedRoute requireAdmin>
              <AddProduct />
            </ProtectedRoute>
          }
        />
        <Route
          path="products/edit/:id"
          element={
            <ProtectedRoute requireAdmin>
              <EditProduct />
            </ProtectedRoute>
          }
        />

        {/* Inventory routes */}
        <Route path="inventory" element={<InventoryList />} />

        {/* Sales routes */}
        <Route path="sales" element={<SalesList />} />
        <Route path="sales/add" element={<AddSale />} />

        {/* Forecasting & Reorder routes */}
        <Route path="forecasts" element={<ForecastView />} />
        <Route path="reorders" element={<ReorderRecommendations />} />
      </Route>

      {/* Fallback */}
      <Route path="*" element={<Navigate to="/dashboard" replace />} />
    </Routes>
  );
};

export default AppRoutes;
