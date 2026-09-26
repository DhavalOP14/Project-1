import React from "react";
import { PlusCircle, AlertTriangle } from "lucide-react";

const InventoryTable = ({ inventory = [], onOpenAdjustModal }) => {
  const getStatusBadge = (status) => {
    switch (status) {
      case "IN_STOCK":
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-emerald-100 text-emerald-800">In Stock</span>;
      case "LOW_STOCK":
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-amber-100 text-amber-800 flex items-center gap-1"><AlertTriangle size={12} /> Low Stock</span>;
      case "OUT_OF_STOCK":
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-rose-100 text-rose-800 flex items-center gap-1"><AlertTriangle size={12} /> Out of Stock</span>;
      case "OVERSTOCKED":
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-blue-100 text-blue-800">Overstocked</span>;
      default:
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-slate-100 text-slate-800">{status}</span>;
    }
  };

  return (
    <div className="overflow-x-auto bg-white rounded-xl border border-slate-200 shadow-sm">
      <table className="w-full text-left border-collapse text-sm">
        <thead>
          <tr className="bg-slate-50 border-b border-slate-200 text-slate-600 font-semibold uppercase text-xs tracking-wider">
            <th className="py-3 px-4">SKU</th>
            <th className="py-3 px-4">Product Name</th>
            <th className="py-3 px-4 text-center">Current Stock</th>
            <th className="py-3 px-4 text-center">Safety Stock</th>
            <th className="py-3 px-4 text-center">Reorder Point (ROP)</th>
            <th className="py-3 px-4 text-center">Optimal Level</th>
            <th className="py-3 px-4 text-right">Inventory Value</th>
            <th className="py-3 px-4 text-center">Status</th>
            <th className="py-3 px-4 text-center">Actions</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100 text-slate-700">
          {inventory.length === 0 ? (
            <tr>
              <td colSpan={9} className="text-center py-8 text-slate-400">
                No inventory records found.
              </td>
            </tr>
          ) : (
            inventory.map((inv) => (
              <tr key={inv.id} className="hover:bg-slate-50/70 transition-colors">
                <td className="py-3 px-4 font-mono font-medium text-indigo-600">{inv.productSku}</td>
                <td className="py-3 px-4 font-medium text-slate-900">{inv.productName}</td>
                <td className="py-3 px-4 text-center">
                  <span className={`font-bold text-base ${
                    inv.currentStock === 0 ? "text-rose-600" :
                    inv.currentStock <= inv.reorderPoint ? "text-amber-600" : "text-slate-800"
                  }`}>
                    {inv.currentStock}
                  </span>
                </td>
                <td className="py-3 px-4 text-center font-medium text-slate-500">{inv.safetyStock}</td>
                <td className="py-3 px-4 text-center font-semibold text-slate-700">{inv.reorderPoint}</td>
                <td className="py-3 px-4 text-center text-slate-600">{inv.optimalStockLevel}</td>
                <td className="py-3 px-4 text-right font-medium text-slate-900">
                  ${Number(inv.totalInventoryValue || 0).toFixed(2)}
                </td>
                <td className="py-3 px-4 text-center">{getStatusBadge(inv.status)}</td>
                <td className="py-3 px-4 text-center">
                  <button
                    onClick={() => onOpenAdjustModal(inv)}
                    className="inline-flex items-center gap-1 px-2.5 py-1 text-xs font-medium text-indigo-600 bg-indigo-50 hover:bg-indigo-100 rounded-md transition"
                  >
                    <PlusCircle size={14} /> Adjust / Restock
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default InventoryTable;
