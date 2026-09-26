import React from "react";
import { Check, Truck, XCircle, AlertCircle } from "lucide-react";

const ReorderTable = ({ recommendations = [], onUpdateStatus }) => {
  const getUrgencyBadge = (urgency) => {
    switch (urgency) {
      case "CRITICAL":
        return <span className="px-2 py-0.5 text-xs font-bold rounded-md bg-rose-100 text-rose-800 border border-rose-200">CRITICAL</span>;
      case "HIGH":
        return <span className="px-2 py-0.5 text-xs font-bold rounded-md bg-amber-100 text-amber-800 border border-amber-200">HIGH</span>;
      case "MEDIUM":
        return <span className="px-2 py-0.5 text-xs font-semibold rounded-md bg-blue-100 text-blue-800 border border-blue-200">MEDIUM</span>;
      default:
        return <span className="px-2 py-0.5 text-xs font-medium rounded-md bg-slate-100 text-slate-700">LOW</span>;
    }
  };

  const getStatusBadge = (status) => {
    switch (status) {
      case "ORDERED":
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-emerald-100 text-emerald-800 flex items-center gap-1"><Truck size={12} /> Ordered / Received</span>;
      case "APPROVED":
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-indigo-100 text-indigo-800 flex items-center gap-1"><Check size={12} /> Approved</span>;
      case "REJECTED":
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-slate-100 text-slate-500">Dismissed</span>;
      default:
        return <span className="px-2.5 py-1 text-xs font-semibold rounded-full bg-amber-50 text-amber-700 border border-amber-200">Pending Review</span>;
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
            <th className="py-3 px-4 text-center">Reorder Point (ROP)</th>
            <th className="py-3 px-4 text-center">Suggested Order Qty (EOQ)</th>
            <th className="py-3 px-4 text-center">Urgency</th>
            <th className="py-3 px-4">Algorithmic Reason</th>
            <th className="py-3 px-4 text-right">Est. Cost</th>
            <th className="py-3 px-4 text-center">Status</th>
            <th className="py-3 px-4 text-center">Actions</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100 text-slate-700">
          {recommendations.length === 0 ? (
            <tr>
              <td colSpan={10} className="text-center py-8 text-slate-400">
                All inventory levels are optimal. No reorders needed at this time!
              </td>
            </tr>
          ) : (
            recommendations.map((r) => (
              <tr key={r.id} className="hover:bg-slate-50/70 transition-colors">
                <td className="py-3 px-4 font-mono font-medium text-indigo-600">{r.productSku}</td>
                <td className="py-3 px-4 font-medium text-slate-900">{r.productName}</td>
                <td className="py-3 px-4 text-center font-bold text-slate-800">{r.currentStock}</td>
                <td className="py-3 px-4 text-center text-slate-600">{r.reorderPoint}</td>
                <td className="py-3 px-4 text-center">
                  <span className="font-bold text-base text-indigo-600 bg-indigo-50 px-2.5 py-1 rounded-md border border-indigo-200">
                    +{r.recommendedQuantity} units
                  </span>
                </td>
                <td className="py-3 px-4 text-center">{getUrgencyBadge(r.urgency)}</td>
                <td className="py-3 px-4 text-xs text-slate-600 max-w-xs">{r.reason}</td>
                <td className="py-3 px-4 text-right font-medium text-slate-900">
                  ${Number(r.estimatedCost || 0).toFixed(2)}
                </td>
                <td className="py-3 px-4 text-center">{getStatusBadge(r.status)}</td>
                <td className="py-3 px-4 text-center">
                  {r.status === "PENDING" && (
                    <div className="flex items-center justify-center gap-1.5">
                      <button
                        onClick={() => onUpdateStatus(r.id, "APPROVED")}
                        className="p-1.5 text-indigo-600 hover:bg-indigo-50 rounded transition"
                        title="Approve Recommendation"
                      >
                        <Check size={16} />
                      </button>
                      <button
                        onClick={() => onUpdateStatus(r.id, "ORDERED")}
                        className="p-1.5 text-emerald-600 hover:bg-emerald-50 rounded transition"
                        title="Mark as Ordered & Receive Stock"
                      >
                        <Truck size={16} />
                      </button>
                      <button
                        onClick={() => onUpdateStatus(r.id, "REJECTED")}
                        className="p-1.5 text-slate-400 hover:text-rose-600 hover:bg-rose-50 rounded transition"
                        title="Dismiss"
                      >
                        <XCircle size={16} />
                      </button>
                    </div>
                  )}
                  {r.status === "APPROVED" && (
                    <button
                      onClick={() => onUpdateStatus(r.id, "ORDERED")}
                      className="px-2.5 py-1 text-xs font-medium text-white bg-emerald-600 hover:bg-emerald-700 rounded-md transition flex items-center gap-1 mx-auto"
                    >
                      <Truck size={13} /> Order Now
                    </button>
                  )}
                  {r.status === "ORDERED" && (
                    <span className="text-xs text-slate-400 font-medium">Completed</span>
                  )}
                  {r.status === "REJECTED" && (
                    <span className="text-xs text-slate-400">Dismissed</span>
                  )}
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default ReorderTable;
