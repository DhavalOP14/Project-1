import React from "react";
import { Edit2, Trash2 } from "lucide-react";
import { Link } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

const ProductTable = ({ products = [], onDelete }) => {
  const { isAdmin } = useAuth();

  const getStatusBadge = (status) => {
    switch (status) {
      case "IN_STOCK":
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-emerald-100 text-emerald-800">In Stock</span>;
      case "LOW_STOCK":
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-amber-100 text-amber-800">Low Stock</span>;
      case "OUT_OF_STOCK":
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-rose-100 text-rose-800">Out of Stock</span>;
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
            <th className="py-3 px-4">Category</th>
            <th className="py-3 px-4 text-right">Unit Price</th>
            <th className="py-3 px-4 text-right">Unit Cost</th>
            <th className="py-3 px-4 text-center">Lead Time</th>
            <th className="py-3 px-4 text-center">Current Stock</th>
            <th className="py-3 px-4 text-center">Status</th>
            {isAdmin && <th className="py-3 px-4 text-center">Actions</th>}
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100 text-slate-700">
          {products.length === 0 ? (
            <tr>
              <td colSpan={isAdmin ? 9 : 8} className="text-center py-8 text-slate-400">
                No products found.
              </td>
            </tr>
          ) : (
            products.map((p) => (
              <tr key={p.id} className="hover:bg-slate-50/70 transition-colors">
                <td className="py-3 px-4 font-mono font-medium text-indigo-600">{p.sku}</td>
                <td className="py-3 px-4 font-medium text-slate-900">{p.name}</td>
                <td className="py-3 px-4 text-slate-500">{p.category?.name || "General"}</td>
                <td className="py-3 px-4 text-right font-medium">${Number(p.unitPrice).toFixed(2)}</td>
                <td className="py-3 px-4 text-right text-slate-500">${Number(p.unitCost).toFixed(2)}</td>
                <td className="py-3 px-4 text-center">{p.leadTimeDays} days</td>
                <td className="py-3 px-4 text-center font-bold text-slate-800">{p.currentStock}</td>
                <td className="py-3 px-4 text-center">{getStatusBadge(p.stockStatus)}</td>
                {isAdmin && (
                  <td className="py-3 px-4 text-center">
                    <div className="flex items-center justify-center gap-2">
                      <Link
                        to={`/products/edit/${p.id}`}
                        className="p-1.5 text-slate-500 hover:text-indigo-600 hover:bg-indigo-50 rounded transition"
                        title="Edit Product"
                      >
                        <Edit2 size={16} />
                      </Link>
                      <button
                        onClick={() => onDelete(p.id)}
                        className="p-1.5 text-slate-500 hover:text-rose-600 hover:bg-rose-50 rounded transition"
                        title="Delete Product"
                      >
                        <Trash2 size={16} />
                      </button>
                    </div>
                  </td>
                )}
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default ProductTable;
