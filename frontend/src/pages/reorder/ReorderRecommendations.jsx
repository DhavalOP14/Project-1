import React, { useState, useEffect } from "react";
import { RefreshCw, Zap } from "lucide-react";
import Header from "../../components/layout/Header";
import ReorderTable from "../../components/reorder/ReorderTable";
import { useToast } from "../../context/ToastContext";
import {
  getAllRecommendations,
  getRecommendationsByStatus,
  updateRecommendationStatus,
  triggerReorderAnalysis,
} from "../../services/reorderService";

const ReorderRecommendations = () => {
  const [recommendations, setRecommendations] = useState([]);
  const [activeFilter, setActiveFilter] = useState("ALL");
  const [loading, setLoading] = useState(true);
  const [analyzing, setAnalyzing] = useState(false);
  const { success, error } = useToast();

  useEffect(() => {
    fetchRecommendations();
  }, [activeFilter]);

  const fetchRecommendations = async () => {
    try {
      setLoading(true);
      const data =
        activeFilter === "ALL"
          ? await getAllRecommendations()
          : await getRecommendationsByStatus(activeFilter);
      setRecommendations(data);
    } catch (err) {
      console.error("Failed to load recommendations", err);
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateStatus = async (id, status) => {
    try {
      await updateRecommendationStatus(id, status);
      await fetchRecommendations();
      success(`Recommendation marked as ${status.toLowerCase()} successfully!`);
    } catch (err) {
      error(err.response?.data?.message || "Failed to update recommendation status");
    }
  };

  const handleRunAnalysis = async () => {
    try {
      setAnalyzing(true);
      await triggerReorderAnalysis();
      await fetchRecommendations();
      success("Inventory optimization analysis recalculated successfully!");
    } catch (err) {
      error("Failed to run optimization analysis");
    } finally {
      setAnalyzing(false);
    }
  };

  return (
    <div className="flex-1 pb-10">
      <Header
        title="Inventory Optimization & Reorder Recommendations"
        subtitle="Automated Economic Order Quantity (EOQ), Safety Stock buffers, and stockout prevention"
      />

      <div className="p-6 max-w-7xl mx-auto space-y-5">
        {/* Top Control Bar */}
        <div className="flex flex-col sm:flex-row justify-between items-center gap-4 bg-white p-4 rounded-xl border border-slate-200 shadow-sm">
          <div className="flex items-center gap-2">
            {["ALL", "PENDING", "APPROVED", "ORDERED", "REJECTED"].map((st) => (
              <button
                key={st}
                onClick={() => setActiveFilter(st)}
                className={`px-3 py-1.5 rounded-lg text-xs font-semibold transition ${
                  activeFilter === st
                    ? "bg-slate-900 text-white shadow-xs"
                    : "bg-slate-50 text-slate-600 hover:bg-slate-100"
                }`}
              >
                {st}
              </button>
            ))}
          </div>

          <button
            onClick={handleRunAnalysis}
            disabled={analyzing}
            className="inline-flex items-center gap-2 px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg text-xs font-semibold transition shadow-sm disabled:opacity-50"
          >
            <Zap size={14} />
            {analyzing ? "Analyzing Inventory..." : "Run Optimization Algorithm"}
          </button>
        </div>

        {/* Reorders Table */}
        {loading ? (
          <div className="flex justify-center py-12">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600"></div>
          </div>
        ) : (
          <ReorderTable
            recommendations={recommendations}
            onUpdateStatus={handleUpdateStatus}
          />
        )}
      </div>
    </div>
  );
};

export default ReorderRecommendations;
