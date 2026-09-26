import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { ArrowLeft } from "lucide-react";
import Header from "../../components/layout/Header";
import ProductForm from "../../components/product/ProductForm";
import { createProduct } from "../../services/productService";

const AddProduct = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleCreate = async (payload) => {
    try {
      setLoading(true);
      setError("");
      await createProduct(payload);
      navigate("/products");
    } catch (err) {
      setError(err.response?.data?.message || "Failed to create product");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex-1 pb-10">
      <Header
        title="Add New Product"
        subtitle="Configure SKU, supplier lead time, holding rates, and pricing parameters"
      />

      <div className="p-6 max-w-4xl mx-auto space-y-4">
        <Link
          to="/products"
          className="inline-flex items-center gap-1.5 text-xs font-semibold text-slate-500 hover:text-slate-800 transition"
        >
          <ArrowLeft size={14} /> Back to Products Catalog
        </Link>

        {error && (
          <div className="p-4 bg-rose-50 border border-rose-200 text-rose-700 text-sm rounded-xl">
            {error}
          </div>
        )}

        <ProductForm onSubmit={handleCreate} loading={loading} />
      </div>
    </div>
  );
};

export default AddProduct;
