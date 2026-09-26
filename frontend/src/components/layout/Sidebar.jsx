import React from "react";
import { NavLink } from "react-router-dom";
import {
  LayoutDashboard,
  Package,
  Boxes,
  ShoppingCart,
  TrendingUp,
  RefreshCw,
  LogOut,
  BrainCircuit,
} from "lucide-react";
import { useAuth } from "../../context/AuthContext";

const Sidebar = () => {
  const { logout, user } = useAuth();

  const links = [
    { name: "Dashboard", path: "/dashboard", icon: LayoutDashboard },
    { name: "Products", path: "/products", icon: Package },
    { name: "Inventory Stock", path: "/inventory", icon: Boxes },
    { name: "Sales Records", path: "/sales", icon: ShoppingCart },
    { name: "AI Demand Forecast", path: "/forecasts", icon: TrendingUp },
    { name: "Reorder Optimization", path: "/reorders", icon: RefreshCw },
  ];

  return (
    <aside className="w-64 bg-slate-900 text-slate-300 min-h-screen flex flex-col justify-between shrink-0 shadow-lg">
      <div>
        {/* App Brand Header */}
        <div className="h-16 flex items-center px-6 border-b border-slate-800 gap-3">
          <div className="p-2 bg-indigo-600 rounded-lg text-white">
            <BrainCircuit size={20} />
          </div>
          <div>
            <h1 className="font-bold text-white text-sm tracking-wide">DemandOpt AI</h1>
            <p className="text-xs text-indigo-400">Inventory Forecasting</p>
          </div>
        </div>

        {/* Navigation Menu */}
        <nav className="p-4 space-y-1">
          {links.map((link) => {
            const Icon = link.icon;
            return (
              <NavLink
                key={link.path}
                to={link.path}
                className={({ isActive }) =>
                  `flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium transition-all ${
                    isActive
                      ? "bg-indigo-600 text-white shadow-sm"
                      : "text-slate-400 hover:bg-slate-800 hover:text-slate-200"
                  }`
                }
              >
                <Icon size={18} />
                <span>{link.name}</span>
              </NavLink>
            );
          })}
        </nav>
      </div>

      {/* User Info & Logout footer */}
      <div className="p-4 border-t border-slate-800">
        <div className="flex items-center gap-3 mb-3 px-2">
          <div className="w-8 h-8 rounded-full bg-indigo-500 text-white flex items-center justify-center font-bold text-xs uppercase">
            {user?.username?.charAt(0) || "U"}
          </div>
          <div className="overflow-hidden">
            <p className="text-sm font-medium text-white truncate">{user?.firstName} {user?.lastName}</p>
            <p className="text-xs text-slate-400 truncate">{user?.role?.replace("ROLE_", "")}</p>
          </div>
        </div>
        <button
          onClick={logout}
          className="w-full flex items-center justify-center gap-2 px-3 py-2 text-sm font-medium text-rose-400 hover:bg-rose-950/40 hover:text-rose-300 rounded-lg transition-colors border border-rose-900/30"
        >
          <LogOut size={16} />
          <span>Sign Out</span>
        </button>
      </div>
    </aside>
  );
};

export default Sidebar;
