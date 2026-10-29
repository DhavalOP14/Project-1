import React from "react";
import { Receipt } from "lucide-react";

const SaleTable = ({ sales = [] }) => {
  return (
    <div className="overflow-x-auto bg-white rounded-xl border border-slate-200 shadow-sm">
      <table className="w-full text-left border-collapse text-sm">
        <thead>
          <tr className="bg-slate-50 border-b border-slate-200 text-slate-600 font-semibold uppercase text-xs tracking-wider">
            <th className="py-3 px-4">Order Code</th>
            <th className="py-3 px-4">Sale Date</th>
            <th className="py-3 px-4">Items Summary</th>
            <th className="py-3 px-4 text-center">Total Quantity</th>
            <th className="py-3 px-4 text-right">Total Amount</th>
            <th className="py-3 px-4">Logged By</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100 text-slate-700">
          {sales.length === 0 ? (
            <tr>
              <td colSpan={6} className="text-center py-8 text-slate-400">
                No sales records found.
              </td>
            </tr>
          ) : (
            sales.map((s) => {
              const totalQty = s.items?.reduce((acc, it) => acc + it.quantity, 0) || 0;
              return (
                <tr key={s.id} className="hover:bg-slate-50/70 transition-colors">
                  <td className="py-3 px-4 font-mono font-medium text-indigo-600 flex items-center gap-1.5">
                    <Receipt size={15} className="text-slate-400" />
                    {s.saleCode}
                  </td>
                  <td className="py-3 px-4 text-slate-600">{s.saleDate}</td>
                  <td className="py-3 px-4">
                    <div className="max-w-xs text-xs text-slate-600 truncate">
                      {s.items?.map((it) => `${it.productName} (x${it.quantity})`).join(", ")}
                    </div>
                  </td>
                  <td className="py-3 px-4 text-center font-medium text-slate-800">{totalQty}</td>
                  <td className="py-3 px-4 text-right font-bold text-slate-900">
                    ₹{Number(s.totalAmount).toFixed(2)}
                  </td>
                  <td className="py-3 px-4 text-xs font-mono text-slate-500">
                    {s.createdByUsername || "System"}
                  </td>
                </tr>
              );
            })
          )}
        </tbody>
      </table>
    </div>
  );
};

export default SaleTable;
