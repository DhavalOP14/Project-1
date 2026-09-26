import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Plus, Package } from "lucide-react";
import Header from "../../components/layout/Header";
import ProductTable from "../../components/product/ProductTable";
import SearchBar from "../../components/common/SearchBar";
import { getAllProducts, deleteProduct } from "../../services/productService";
import { useAuth } from "../../context/AuthContext";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const { isAdmin } = useAuth();

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      const data = await getAllProducts();
      setProducts(data);
    } catch (err) {
      console.error("Failed to load products", err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this product? All associated inventory will also be removed.")) {
      return;
    }
    try {
      await deleteProduct(id);
      setProducts(products.filter((p) => p.id !== id));
    } catch (err) {
      alert(err.response?.data?.message || "Failed to delete product");
    }
  };

  const filteredProducts = products.filter(
    (p) =>
      p.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      p.sku?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      p.category?.name?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="flex-1 pb-10">
      <Header
        title="Products Catalog"
        subtitle="Manage product specifications, lead times, and inventory cost parameters"
      />

      <div className="p-6 max-w-7xl mx-auto space-y-4">
        <div className="flex flex-col sm:flex-row justify-between items-center gap-4">
          <SearchBar
            value={searchTerm}
            onChange={setSearchTerm}
            placeholder="Search by SKU, name, or category..."
          />

          {isAdmin && (
            <Link
              to="/products/add"
              className="inline-flex items-center gap-2 px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg text-sm font-medium transition shadow-sm"
            >
              <Plus size={16} /> Add Product
            </Link>
          )}
        </div>

        {loading ? (
          <div className="flex justify-center py-12">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600"></div>
          </div>
        ) : (
          <ProductTable products={filteredProducts} onDelete={handleDelete} />
        )}
      </div>
    </div>
  );
};

export default ProductList;
