import React, { useState } from "react";
import { X } from "lucide-react";
import InputField from "../common/InputField";
import SelectField from "../common/SelectField";

const StockUpdateModal = ({ item, isOpen, onClose, onSave }) => {
  const [adjustmentType, setAdjustmentType] = useState("RESTOCK");
  const [quantity, setQuantity] = useState("");
  const [reason, setReason] = useState("");
  const [error, setError] = useState("");

  if (!isOpen || !item) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    const qty = Number(quantity);
    if (!qty || qty <= 0) {
      setError("Please enter a valid positive quantity");
      return;
    }

    // If DAMAGE or CORRECTION_NEGATIVE, change is negative
    const finalChange = adjustmentType === "DAMAGE" ? -qty : qty;

    onSave({
      productId: item.productId,
      quantityChange: finalChange,
      adjustmentType,
      reason,
    });
    onClose();
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 backdrop-blur-xs p-4">
      <div className="bg-white rounded-xl shadow-xl w-full max-w-md overflow-hidden border border-slate-200">
        <div className="flex items-center justify-between px-6 py-4 border-b border-slate-100">
          <div>
            <h3 className="font-bold text-slate-900">Stock Adjustment</h3>
            <p className="text-xs text-slate-500 font-mono mt-0.5">{item.productSku} - {item.productName}</p>
          </div>
          <button onClick={onClose} className="p-1 text-slate-400 hover:text-slate-600 rounded">
            <X size={18} />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="p-6">
          <div className="mb-4 p-3 bg-slate-50 rounded-lg flex justify-between items-center text-sm border border-slate-100">
            <span className="text-slate-500">Current Stock Level:</span>
            <span className="font-bold text-slate-800 text-base">{item.currentStock} units</span>
          </div>

          <SelectField
            label="Adjustment Type"
            value={adjustmentType}
            onChange={(e) => setAdjustmentType(e.target.value)}
            options={[
              { value: "RESTOCK", label: "Restock / Received Purchase Order (+)" },
              { value: "RETURN", label: "Customer Return (+)" },
              { value: "DAMAGE", label: "Damaged / Scrapped / Expired (-)" },
              { value: "INVENTORY_COUNT", label: "Physical Count Adjustment (+)" },
            ]}
          />

          <InputField
            label="Quantity"
            type="number"
            min="1"
            value={quantity}
            onChange={(e) => {
              setQuantity(e.target.value);
              setError("");
            }}
            placeholder="e.g. 25"
            required
            error={error}
          />

          <InputField
            label="Reason / Reference Note"
            value={reason}
            onChange={(e) => setReason(e.target.value)}
            placeholder="e.g. PO-8493 delivered, damaged in transit..."
          />

          <div className="mt-6 flex justify-end gap-2">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 text-sm font-medium text-slate-600 hover:bg-slate-100 rounded-lg transition"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-4 py-2 text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 rounded-lg shadow-sm transition"
            >
              Apply Adjustment
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default StockUpdateModal;
