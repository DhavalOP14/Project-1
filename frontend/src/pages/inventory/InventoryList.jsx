import React, { useState, useEffect } from "react";
import Header from "../../components/layout/Header";
import InventoryTable from "../../components/inventory/InventoryTable";
import StockUpdateModal from "../../components/inventory/StockUpdateModal";
import SearchBar from "../../components/common/SearchBar";
import { getAllInventory, updateStock } from "../../services/inventoryService";
import { useToast } from "../../context/ToastContext";

const InventoryList = () => {
  const [inventory, setInventory] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [statusFilter, setStatusFilter] = useState("ALL");
  const [loading, setLoading] = useState(true);
  const [selectedItem, setSelectedItem] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { success, error } = useToast();

  useEffect(() => {
    fetchInventory();
  }, []);

  const fetchInventory = async () => {
    try {
      setLoading(true);
      const data = await getAllInventory();
      setInventory(data);
    } catch (err) {
      console.error("Failed to load inventory", err);
    } finally {
      setLoading(false);
    }
  };

  const handleOpenAdjustModal = (item) => {
    setSelectedItem(item);
    setIsModalOpen(true);
  };

  const handleSaveStock = async (payload) => {
    try {
      await updateStock(payload);
      await fetchInventory();
      success("Stock adjustment applied successfully!");
    } catch (err) {
      error(err.response?.data?.message || "Failed to adjust stock");
    }
  };

  const filteredInventory = inventory.filter((item) => {
    const matchesSearch =
      item.productName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      item.productSku?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      item.categoryName?.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesStatus = statusFilter === "ALL" || item.status === statusFilter;
    return matchesSearch && matchesStatus;
  });

  return (
    <div className="flex-1 pb-10">
      <Header
        title="Inventory Stock Levels & Safety Stock"
        subtitle="Real-time stock monitoring, Reorder Point (ROP) thresholds, and buffer levels"
      />

      <div className="p-6 max-w-7xl mx-auto space-y-4">
        {/* Filter bar */}
        <div className="flex flex-col sm:flex-row justify-between items-center gap-4">
          <SearchBar
            value={searchTerm}
            onChange={setSearchTerm}
            placeholder="Search stock by SKU, product, category..."
          />

          <div className="flex items-center gap-2">
            {["ALL", "IN_STOCK", "LOW_STOCK", "OUT_OF_STOCK", "OVERSTOCKED"].map((st) => (
              <button
                key={st}
                onClick={() => setStatusFilter(st)}
                className={`px-3 py-1.5 rounded-lg text-xs font-semibold transition ${
                  statusFilter === st
                    ? "bg-slate-900 text-white shadow-xs"
                    : "bg-white text-slate-600 border border-slate-200 hover:bg-slate-50"
                }`}
              >
                {st.replace("_", " ")}
              </button>
            ))}
          </div>
        </div>

        {loading ? (
          <div className="flex justify-center py-12">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600"></div>
          </div>
        ) : (
          <InventoryTable
            inventory={filteredInventory}
            onOpenAdjustModal={handleOpenAdjustModal}
          />
        )}
      </div>

      <StockUpdateModal
        item={selectedItem}
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSave={handleSaveStock}
      />
    </div>
  );
};

export default InventoryList;
