import React, { useState, useEffect } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import { ArrowLeft } from "lucide-react";
import Header from "../../components/layout/Header";
import ProductForm from "../../components/product/ProductForm";
import { getProductById, updateProduct } from "../../services/productService";

const EditProduct = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(false);
  const [fetching, setFetching] = useState(true);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    fetchProduct();
  }, [id]);

  const fetchProduct = async () => {
    try {
      setFetching(true);
      const data = await getProductById(id);
      setProduct(data);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to load product details");
    } finally {
      setFetching(false);
    }
  };

  const handleUpdate = async (payload) => {
    try {
      setLoading(true);
      setError("");
      await updateProduct(id, payload);
      navigate("/products");
    } catch (err) {
      setError(err.response?.data?.message || "Failed to update product");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex-1 pb-10">
      <Header
        title={`Edit Product: ${product?.sku || ""}`}
        subtitle="Modify product attributes and optimization variables"
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

        {fetching ? (
          <div className="flex justify-center py-12">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600"></div>
          </div>
        ) : (
          <ProductForm initialData={product} onSubmit={handleUpdate} isEdit loading={loading} />
        )}
      </div>
    </div>
  );
};

export default EditProduct;
