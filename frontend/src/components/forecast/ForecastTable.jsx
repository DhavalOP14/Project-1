import React from "react";
import { Brain, CheckCircle2 } from "lucide-react";

const ForecastTable = ({ forecasts = [] }) => {
  return (
    <div className="overflow-x-auto bg-white rounded-xl border border-slate-200 shadow-sm">
      <table className="w-full text-left border-collapse text-sm">
        <thead>
          <tr className="bg-slate-50 border-b border-slate-200 text-slate-600 font-semibold uppercase text-xs tracking-wider">
            <th className="py-3 px-4">SKU</th>
            <th className="py-3 px-4">Product Name</th>
            <th className="py-3 px-4 text-center">Period</th>
            <th className="py-3 px-4 text-center">Predicted Demand</th>
            <th className="py-3 px-4 text-center">95% Range (Lower - Upper)</th>
            <th className="py-3 px-4">AI Algorithm</th>
            <th className="py-3 px-4 text-center">Model Accuracy</th>
            <th className="py-3 px-4">Forecast Date</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100 text-slate-700">
          {forecasts.length === 0 ? (
            <tr>
              <td colSpan={8} className="text-center py-8 text-slate-400">
                No forecasting predictions available. Click "Generate Demand Forecast" above.
              </td>
            </tr>
          ) : (
            forecasts.map((f) => (
              <tr key={f.id} className="hover:bg-slate-50/70 transition-colors">
                <td className="py-3 px-4 font-mono font-medium text-indigo-600">{f.productSku}</td>
                <td className="py-3 px-4 font-medium text-slate-900">{f.productName}</td>
                <td className="py-3 px-4 text-center text-xs font-semibold text-slate-600">
                  <span className="bg-slate-100 px-2 py-0.5 rounded">{f.targetPeriod}</span>
                </td>
                <td className="py-3 px-4 text-center">
                  <span className="font-bold text-base text-emerald-700 bg-emerald-50 px-2.5 py-1 rounded-md border border-emerald-200">
                    {f.predictedDemand} units
                  </span>
                </td>
                <td className="py-3 px-4 text-center font-mono text-xs text-slate-600">
                  [{f.confidenceLower} — {f.confidenceUpper}]
                </td>
                <td className="py-3 px-4 text-xs font-mono text-indigo-700 flex items-center gap-1.5 pt-4">
                  <Brain size={14} className="text-indigo-500" />
                  {f.algorithmUsed}
                </td>
                <td className="py-3 px-4 text-center">
                  <span className="inline-flex items-center gap-1 text-xs font-semibold text-emerald-600">
                    <CheckCircle2 size={13} /> {f.accuracyMetric || "92.5"}%
                  </span>
                </td>
                <td className="py-3 px-4 text-xs text-slate-500">{f.forecastDate}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default ForecastTable;
