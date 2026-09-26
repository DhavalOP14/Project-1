import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Plus } from "lucide-react";
import Header from "../../components/layout/Header";
import SaleTable from "../../components/sale/SaleTable";
import SearchBar from "../../components/common/SearchBar";
import { getAllSales } from "../../services/saleService";

const SalesList = () => {
  const [sales, setSales] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchSales();
  }, []);

  const fetchSales = async () => {
    try {
      setLoading(true);
      const data = await getAllSales();
      setSales(data);
    } catch (err) {
      console.error("Failed to load sales", err);
    } finally {
      setLoading(false);
    }
  };

  const filteredSales = sales.filter(
    (s) =>
      s.saleCode?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      s.createdByUsername?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      s.notes?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      s.items?.some((it) => it.productName?.toLowerCase().includes(searchTerm.toLowerCase()))
  );

  return (
    <div className="flex-1 pb-10">
      <Header
        title="Sales Transactions & Historical Demand Records"
        subtitle="Track orders, volume patterns, and consumption history used by AI models"
      />

      <div className="p-6 max-w-7xl mx-auto space-y-4">
        <div className="flex flex-col sm:flex-row justify-between items-center gap-4">
          <SearchBar
            value={searchTerm}
            onChange={setSearchTerm}
            placeholder="Search by order code, product, customer..."
          />

          <Link
            to="/sales/add"
            className="inline-flex items-center gap-2 px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg text-sm font-medium transition shadow-sm"
          >
            <Plus size={16} /> Record Sale
          </Link>
        </div>

        {loading ? (
          <div className="flex justify-center py-12">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600"></div>
          </div>
        ) : (
          <SaleTable sales={filteredSales} />
        )}
      </div>
    </div>
  );
};

export default SalesList;
