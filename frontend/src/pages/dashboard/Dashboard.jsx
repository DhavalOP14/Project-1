import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import {
  Package,
  IndianRupee,
  AlertTriangle,
  RefreshCw,
  TrendingUp,
  Boxes,
  ArrowRight,
} from "lucide-react";
import Header from "../../components/layout/Header";
import StatCard from "../../components/common/StatCard";
import { getDashboardSummary } from "../../services/dashboardService";
import {
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  PieChart,
  Pie,
  Cell,
  Legend,
} from "recharts";

const Dashboard = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchSummary();
  }, []);

  const fetchSummary = async () => {
    try {
      const res = await getDashboardSummary();
      setData(res);
    } catch (err) {
      console.error("Failed to load dashboard summary", err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex-1 flex items-center justify-center min-h-[500px]">
        <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  const stockDistribution = [
    { name: "In Stock", value: data?.inStockCount || 0, color: "#10b981" },
    { name: "Low Stock", value: data?.lowStockCount || 0, color: "#f59e0b" },
    { name: "Out of Stock", value: data?.outOfStockCount || 0, color: "#ef4444" },
    { name: "Overstocked", value: data?.overstockedCount || 0, color: "#3b82f6" },
  ];

  return (
    <div className="flex-1 pb-10">
      <Header
        title="Inventory & Demand Forecasting Overview"
        subtitle="AI-driven inventory analytics, stock monitoring, and automated replenishment"
      />

      <div className="p-6 space-y-6 max-w-7xl mx-auto">
        {/* Top KPI Cards */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          <StatCard
            title="Total Products"
            value={data?.totalProducts || 0}
            subtitle="Catalog items tracked"
            icon={Package}
            color="indigo"
          />
          <StatCard
            title="Inventory Value"
            value={`₹${Number(data?.totalInventoryValue || 0).toLocaleString("en-IN", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`}
            subtitle="Current warehouse asset value"
            icon={IndianRupee}
            color="emerald"
          />
          <StatCard
            title="Stock Alerts"
            value={data?.criticalAlertsCount || 0}
            subtitle={`${data?.outOfStockCount || 0} Out of Stock, ${data?.lowStockCount || 0} Low`}
            icon={AlertTriangle}
            color="rose"
          />
          <StatCard
            title="Pending Reorders"
            value={data?.pendingReordersCount || 0}
            subtitle="Optimized EOQ recommendations"
            icon={RefreshCw}
            color="amber"
          />
        </div>

        {/* Charts Section */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Monthly Sales Revenue Trend */}
          <div className="lg:col-span-2 bg-white p-5 rounded-xl border border-slate-200 shadow-sm">
            <div className="flex items-center justify-between mb-4">
              <div>
                <h3 className="font-bold text-slate-800 text-sm">Monthly Historical Sales Revenue</h3>
                <p className="text-xs text-slate-500">Sales data analyzed by machine learning models</p>
              </div>
              <span className="text-xs font-semibold text-emerald-600 bg-emerald-50 px-2.5 py-1 rounded-md">
                Total: ₹{Number(data?.totalSalesRevenue || 0).toLocaleString("en-IN", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
              </span>
            </div>

            <div className="h-64 w-full">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={data?.monthlySalesTrend || []} margin={{ top: 10, right: 10, left: -10, bottom: 0 }}>
                  <CartesianGrid strokeDasharray="3 3" stroke="#f1f5f9" />
                  <XAxis dataKey="month" stroke="#94a3b8" fontSize={11} />
                  <YAxis stroke="#94a3b8" fontSize={11} />
                  <Tooltip
                    formatter={(val) => [`₹${Number(val).toFixed(2)}`, "Revenue"]}
                    contentStyle={{
                      backgroundColor: "#ffffff",
                      borderRadius: "8px",
                      border: "1px solid #e2e8f0",
                      boxShadow: "0 4px 6px -1px rgb(0 0 0 / 0.1)",
                      fontSize: "12px",
                      fontWeight: "500",
                    }}
                    itemStyle={{ color: "#0f172a", fontWeight: "600" }}
                    labelStyle={{ color: "#64748b", fontWeight: "600" }}
                  />
                  <Bar dataKey="revenue" fill="#4f46e5" radius={[4, 4, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </div>

          {/* Stock Health Breakdown */}
          <div className="bg-white p-5 rounded-xl border border-slate-200 shadow-sm flex flex-col justify-between">
            <div>
              <h3 className="font-bold text-slate-800 text-sm mb-1">Inventory Health Breakdown</h3>
              <p className="text-xs text-slate-500 mb-4">Current stock status across catalog</p>
            </div>

            <div className="h-48 w-full flex items-center justify-center">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={stockDistribution}
                    innerRadius={50}
                    outerRadius={75}
                    paddingAngle={3}
                    dataKey="value"
                  >
                    {stockDistribution.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={entry.color} />
                    ))}
                  </Pie>
                  <Tooltip
                    formatter={(val, name) => [`${val} products`, name]}
                    contentStyle={{
                      backgroundColor: "#ffffff",
                      borderRadius: "8px",
                      border: "1px solid #e2e8f0",
                      boxShadow: "0 4px 6px -1px rgb(0 0 0 / 0.1)",
                      fontSize: "12px",
                      fontWeight: "500",
                    }}
                    itemStyle={{ color: "#0f172a", fontWeight: "600" }}
                    labelStyle={{ color: "#64748b", fontWeight: "600" }}
                  />
                </PieChart>
              </ResponsiveContainer>
            </div>

            <div className="grid grid-cols-2 gap-2 pt-2 border-t border-slate-100 text-xs">
              {stockDistribution.map((item) => (
                <div key={item.name} className="flex items-center gap-1.5">
                  <span className="w-2.5 h-2.5 rounded-full" style={{ backgroundColor: item.color }} />
                  <span className="text-slate-600">{item.name}:</span>
                  <span className="font-bold text-slate-900">{item.value}</span>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Bottom Section: Active Alerts & Suggested Orders */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {/* Critical Low-Stock Alerts */}
          <div className="bg-white p-5 rounded-xl border border-slate-200 shadow-sm">
            <div className="flex items-center justify-between mb-4">
              <div className="flex items-center gap-2">
                <AlertTriangle size={18} className="text-rose-500" />
                <h3 className="font-bold text-slate-800 text-sm">Active Low-Stock Alerts</h3>
              </div>
              <Link to="/inventory" className="text-xs text-indigo-600 hover:text-indigo-800 font-medium flex items-center gap-1">
                View all inventory <ArrowRight size={12} />
              </Link>
            </div>

            <div className="space-y-2.5">
              {(!data?.lowStockAlerts || data.lowStockAlerts.length === 0) ? (
                <p className="text-xs text-slate-400 py-4 text-center">No critical stock alerts!</p>
              ) : (
                data.lowStockAlerts.slice(0, 4).map((al) => (
                  <div key={al.id} className="flex items-center justify-between p-3 bg-slate-50 rounded-lg border border-slate-100 text-sm">
                    <div>
                      <p className="font-medium text-slate-900">{al.productName}</p>
                      <p className="text-xs text-slate-500 font-mono">SKU: {al.productSku} | Safety Stock: {al.safetyStock}</p>
                    </div>
                    <div className="text-right">
                      <span className={`inline-block px-2 py-0.5 rounded text-xs font-bold ${
                        al.currentStock === 0 ? "bg-rose-100 text-rose-700" : "bg-amber-100 text-amber-700"
                      }`}>
                        {al.currentStock} in stock (ROP: {al.reorderPoint})
                      </span>
                    </div>
                  </div>
                ))
              )}
            </div>
          </div>

          {/* AI Reorder Recommendations */}
          <div className="bg-white p-5 rounded-xl border border-slate-200 shadow-sm">
            <div className="flex items-center justify-between mb-4">
              <div className="flex items-center gap-2">
                <RefreshCw size={18} className="text-indigo-600" />
                <h3 className="font-bold text-slate-800 text-sm">Top Replenishment Suggestions</h3>
              </div>
              <Link to="/reorders" className="text-xs text-indigo-600 hover:text-indigo-800 font-medium flex items-center gap-1">
                Manage orders <ArrowRight size={12} />
              </Link>
            </div>

            <div className="space-y-2.5">
              {(!data?.topReorderSuggestions || data.topReorderSuggestions.length === 0) ? (
                <p className="text-xs text-slate-400 py-4 text-center">No pending replenishment recommendations.</p>
              ) : (
                data.topReorderSuggestions.slice(0, 4).map((rec) => (
                  <div key={rec.id} className="flex items-center justify-between p-3 bg-indigo-50/50 rounded-lg border border-indigo-100 text-sm">
                    <div>
                      <p className="font-medium text-slate-900">{rec.productName}</p>
                      <p className="text-xs text-slate-500 truncate max-w-xs">{rec.reason}</p>
                    </div>
                    <div className="text-right shrink-0">
                      <span className="font-bold text-indigo-600 block text-sm whitespace-nowrap">+{rec.recommendedQuantity} units</span>
                      <span className="text-xs text-slate-500 whitespace-nowrap">₹{Number(rec.estimatedCost || 0).toFixed(2)}</span>
                    </div>
                  </div>
                ))
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
