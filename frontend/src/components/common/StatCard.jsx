import React from "react";

const StatCard = ({ title, value, subtitle, icon: Icon, color = "indigo", trend }) => {
  const colorStyles = {
    indigo: "bg-indigo-50 text-indigo-600 border-indigo-200",
    emerald: "bg-emerald-50 text-emerald-600 border-emerald-200",
    amber: "bg-amber-50 text-amber-600 border-amber-200",
    rose: "bg-rose-50 text-rose-600 border-rose-200",
    blue: "bg-blue-50 text-blue-600 border-blue-200",
  };

  return (
    <div className="bg-white p-5 rounded-xl border border-slate-200 shadow-sm flex items-start justify-between">
      <div>
        <p className="text-sm font-medium text-slate-500">{title}</p>
        <h3 className="text-2xl font-bold text-slate-900 mt-1">{value}</h3>
        {subtitle && <p className="text-xs text-slate-500 mt-1">{subtitle}</p>}
        {trend !== undefined && (
          <p className={`text-xs font-semibold mt-2 ${trend >= 0 ? "text-emerald-600" : "text-rose-600"}`}>
            {trend >= 0 ? "↑" : "↓"} {Math.abs(trend)}% from last cycle
          </p>
        )}
      </div>
      {Icon && (
        <div className={`p-3 rounded-lg border ${colorStyles[color] || colorStyles.indigo}`}>
          <Icon size={22} />
        </div>
      )}
    </div>
  );
};

export default StatCard;
