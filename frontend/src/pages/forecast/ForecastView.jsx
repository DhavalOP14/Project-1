import React, { useState, useEffect } from "react";
import { Sparkles, BrainCircuit, Play, History } from "lucide-react";
import Header from "../../components/layout/Header";
import ForecastChart from "../../components/forecast/ForecastChart";
import ForecastTable from "../../components/forecast/ForecastTable";
import SelectField from "../../components/common/SelectField";
import { getAllProducts } from "../../services/productService";
import {
  generateForecast,
  getAllLatestForecasts,
  runBatchForecasting,
} from "../../services/forecastService";
import { useAuth } from "../../context/AuthContext";

const ForecastView = () => {
  const [products, setProducts] = useState([]);
  const [selectedProductId, setSelectedProductId] = useState("");
  const [period, setPeriod] = useState("MONTHLY");
  const [algorithm, setAlgorithm] = useState("ENSEMBLE");
  const [forecasts, setForecasts] = useState([]);
  const [activeForecast, setActiveForecast] = useState(null);
  const [loading, setLoading] = useState(false);
  const [batchLoading, setBatchLoading] = useState(false);
  const { isAdmin } = useAuth();

  useEffect(() => {
    fetchInitialData();
  }, []);

  const fetchInitialData = async () => {
    try {
      const [prods, latest] = await Promise.all([
        getAllProducts(),
        getAllLatestForecasts(),
      ]);
      setProducts(prods);
      setForecasts(latest);
      if (prods.length > 0) {
        setSelectedProductId(String(prods[0].id));
      }
    } catch (err) {
      console.error("Failed to load initial forecasting data", err);
    }
  };

  const handleGenerateForecast = async (e) => {
    e.preventDefault();
    if (!selectedProductId) return;

    try {
      setLoading(true);
      const result = await generateForecast({
        productId: Number(selectedProductId),
        targetPeriod: period,
        preferredAlgorithm: algorithm,
      });
      setActiveForecast(result);

      // Refresh latest table
      const updated = await getAllLatestForecasts();
      setForecasts(updated);
    } catch (err) {
      alert(err.response?.data?.message || "Failed to generate demand forecast");
    } finally {
      setLoading(false);
    }
  };

  const handleBatchRun = async () => {
    if (!window.confirm("Run batch demand forecasting across all products in catalog?")) return;
    try {
      setBatchLoading(true);
      await runBatchForecasting();
      const updated = await getAllLatestForecasts();
      setForecasts(updated);
      alert("Batch demand forecasting completed successfully!");
    } catch (err) {
      alert("Failed to run batch forecasting");
    } finally {
      setBatchLoading(false);
    }
  };

  const currentSelectedProduct = products.find((p) => String(p.id) === String(selectedProductId));

  return (
    <div className="flex-1 pb-10">
      <Header
        title="AI Demand Forecasting Engine"
        subtitle="Time-series machine learning models, seasonal indexation, and consumption projections"
      />

      <div className="p-6 max-w-7xl mx-auto space-y-6">
        {/* Interactive Model Generator Control */}
        <div className="bg-white p-6 rounded-xl border border-slate-200 shadow-sm">
          <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4 mb-5 pb-4 border-b border-slate-100">
            <div className="flex items-center gap-2">
              <div className="p-2 bg-indigo-50 text-indigo-600 rounded-lg">
                <BrainCircuit size={22} />
              </div>
              <div>
                <h3 className="font-bold text-slate-900 text-sm">Real-Time Demand Prediction Console</h3>
                <p className="text-xs text-slate-500">Select target product and modeling parameters to compute future demand</p>
              </div>
            </div>

            {isAdmin && (
              <button
                onClick={handleBatchRun}
                disabled={batchLoading}
                className="inline-flex items-center gap-1.5 px-3.5 py-2 bg-slate-900 hover:bg-slate-800 text-white rounded-lg text-xs font-semibold transition disabled:opacity-50"
              >
                <Sparkles size={14} className="text-indigo-400" />
                {batchLoading ? "Processing Batch..." : "Batch Run All Products"}
              </button>
            )}
          </div>

          <form onSubmit={handleGenerateForecast} className="grid grid-cols-1 sm:grid-cols-4 gap-4 items-end">
            <div className="sm:col-span-2">
              <SelectField
                label="Target Product"
                value={selectedProductId}
                onChange={(e) => setSelectedProductId(e.target.value)}
                options={products.map((p) => ({
                  value: String(p.id),
                  label: `${p.sku} - ${p.name} (Stock: ${p.currentStock})`,
                }))}
                placeholder="Choose product to forecast..."
                required
              />
            </div>

            <SelectField
              label="Forecasting Horizon"
              value={period}
              onChange={(e) => setPeriod(e.target.value)}
              options={[
                { value: "WEEKLY", label: "Weekly Horizon" },
                { value: "MONTHLY", label: "Monthly Horizon (Default)" },
                { value: "QUARTERLY", label: "Quarterly Horizon" },
              ]}
            />

            <SelectField
              label="Machine Learning Algorithm"
              value={algorithm}
              onChange={(e) => setAlgorithm(e.target.value)}
              options={[
                { value: "ENSEMBLE", label: "Hybrid Ensemble (Recommended)" },
                { value: "EXPONENTIAL_SMOOTHING", label: "Exponential Smoothing" },
                { value: "LINEAR_TREND", label: "Linear Trend Regression" },
                { value: "MOVING_AVERAGE", label: "Weighted Moving Average" },
              ]}
            />

            <div className="sm:col-span-4 flex justify-end">
              <button
                type="submit"
                disabled={loading || !selectedProductId}
                className="inline-flex items-center gap-2 px-5 py-2.5 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg text-sm font-semibold transition shadow-sm disabled:opacity-50"
              >
                <Play size={16} />
                {loading ? "Computing Predictions..." : "Generate Demand Forecast"}
              </button>
            </div>
          </form>
        </div>

        {/* Prediction Chart Results */}
        {activeForecast && (
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <div className="bg-emerald-50 border border-emerald-200 p-4 rounded-xl text-center">
              <span className="text-xs font-semibold text-emerald-800 uppercase tracking-wider">Predicted Demand</span>
              <p className="text-3xl font-extrabold text-emerald-700 mt-1">{activeForecast.predictedDemand}</p>
              <p className="text-xs text-emerald-600 mt-1">Expected units for {activeForecast.targetPeriod.toLowerCase()}</p>
            </div>

            <div className="bg-indigo-50 border border-indigo-200 p-4 rounded-xl text-center">
              <span className="text-xs font-semibold text-indigo-800 uppercase tracking-wider">95% Confidence Band</span>
              <p className="text-2xl font-bold text-indigo-700 mt-2 font-mono">
                {activeForecast.confidenceLower} — {activeForecast.confidenceUpper}
              </p>
              <p className="text-xs text-indigo-600 mt-1">Lower and upper boundaries</p>
            </div>

            <div className="bg-slate-50 border border-slate-200 p-4 rounded-xl text-center">
              <span className="text-xs font-semibold text-slate-700 uppercase tracking-wider">Model Accuracy</span>
              <p className="text-3xl font-extrabold text-slate-800 mt-1">{activeForecast.accuracyMetric}%</p>
              <p className="text-xs text-slate-500 mt-1">Based on historical error variance</p>
            </div>

            <div className="bg-blue-50 border border-blue-200 p-4 rounded-xl text-center">
              <span className="text-xs font-semibold text-blue-800 uppercase tracking-wider">Projected Growth</span>
              <p className="text-3xl font-extrabold text-blue-700 mt-1">
                {activeForecast.growthTrendPercentage >= 0 ? "+" : ""}{activeForecast.growthTrendPercentage}%
              </p>
              <p className="text-xs text-blue-600 mt-1">Trend momentum vs previous period</p>
            </div>
          </div>
        )}

        {/* Interactive Chart */}
        <ForecastChart
          data={activeForecast?.trendData}
          productName={currentSelectedProduct ? `${currentSelectedProduct.sku} - ${currentSelectedProduct.name}` : ""}
        />

        {/* Historical Forecast Records Table */}
        <div className="space-y-3">
          <div className="flex items-center gap-2">
            <History size={18} className="text-slate-500" />
            <h3 className="font-bold text-slate-800 text-sm">Catalog Demand Forecasts</h3>
          </div>
          <ForecastTable forecasts={forecasts} />
        </div>
      </div>
    </div>
  );
};

export default ForecastView;
