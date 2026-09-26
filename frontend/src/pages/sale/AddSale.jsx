import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { ArrowLeft } from "lucide-react";
import Header from "../../components/layout/Header";
import SaleForm from "../../components/sale/SaleForm";
import { recordSale } from "../../services/saleService";

const AddSale = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleRecord = async (payload) => {
    try {
      setLoading(true);
      setError("");
      await recordSale(payload);
      navigate("/sales");
    } catch (err) {
      setError(err.response?.data?.message || "Failed to record sale order");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex-1 pb-10">
      <Header
        title="Record New Sales Order"
        subtitle="Deduct inventory automatically and trigger demand recalculation"
      />

      <div className="p-6 max-w-4xl mx-auto space-y-4">
        <Link
          to="/sales"
          className="inline-flex items-center gap-1.5 text-xs font-semibold text-slate-500 hover:text-slate-800 transition"
        >
          <ArrowLeft size={14} /> Back to Sales List
        </Link>

        {error && (
          <div className="p-4 bg-rose-50 border border-rose-200 text-rose-700 text-sm rounded-xl">
            {error}
          </div>
        )}

        <SaleForm onSubmit={handleRecord} loading={loading} />
      </div>
    </div>
  );
};

export default AddSale;
