import React from "react";
import {
  ResponsiveContainer,
  ComposedChart,
  Line,
  Area,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from "recharts";

const ForecastChart = ({ data = [], productName }) => {
  if (!data || data.length === 0) {
    return (
      <div className="h-64 flex items-center justify-center bg-slate-50 border border-dashed border-slate-200 rounded-xl text-slate-400 text-sm">
        Select a product and generate an AI forecast to view demand trend projections.
      </div>
    );
  }

  // Format chart data points
  const formattedData = data.map((d) => ({
    date: d.date,
    "Actual Demand": d.actualSales !== undefined ? d.actualSales : null,
    "Predicted Demand": d.predictedDemand !== undefined ? d.predictedDemand : null,
    "Upper Bound": d.confidenceUpper !== undefined ? d.confidenceUpper : null,
    "Lower Bound": d.confidenceLower !== undefined ? d.confidenceLower : null,
  }));

  return (
    <div className="bg-white p-5 rounded-xl border border-slate-200 shadow-sm">
      <div className="mb-4 flex items-center justify-between">
        <div>
          <h4 className="font-bold text-slate-800 text-sm">Demand Forecast Model & Confidence Band</h4>
          {productName && <p className="text-xs text-slate-500 font-mono mt-0.5">{productName}</p>}
        </div>
        <span className="text-xs bg-indigo-50 text-indigo-700 px-2.5 py-1 rounded-full font-medium border border-indigo-100">
          95% Confidence Interval
        </span>
      </div>

      <div className="h-72 w-full">
        <ResponsiveContainer width="100%" height="100%">
          <ComposedChart data={formattedData} margin={{ top: 10, right: 20, left: 0, bottom: 0 }}>
            <CartesianGrid strokeDasharray="3 3" stroke="#f1f5f9" />
            <XAxis dataKey="date" stroke="#94a3b8" fontSize={11} />
            <YAxis stroke="#94a3b8" fontSize={11} />
            <Tooltip
              contentStyle={{
                backgroundColor: "#0f172a",
                borderRadius: "8px",
                border: "none",
                color: "#fff",
                fontSize: "12px",
              }}
            />
            <Legend wrapperStyle={{ fontSize: "12px", paddingTop: "8px" }} />
            
            {/* Historical Actual Sales */}
            <Line
              type="monotone"
              dataKey="Actual Demand"
              stroke="#4f46e5"
              strokeWidth={2.5}
              dot={{ r: 4, fill: "#4f46e5" }}
            />

            {/* Upper & Lower Confidence Area */}
            <Area
              type="monotone"
              dataKey="Upper Bound"
              stroke="transparent"
              fill="#c7d2fe"
              fillOpacity={0.3}
            />
            <Area
              type="monotone"
              dataKey="Lower Bound"
              stroke="transparent"
              fill="#e0e7ff"
              fillOpacity={0.2}
            />

            {/* AI Projected Demand Line */}
            <Line
              type="monotone"
              dataKey="Predicted Demand"
              stroke="#059669"
              strokeWidth={3}
              strokeDasharray="5 5"
              dot={{ r: 5, fill: "#059669" }}
            />
          </ComposedChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};

export default ForecastChart;
