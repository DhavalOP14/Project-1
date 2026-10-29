import React, { useState } from "react";
import { Bell, Sparkles } from "lucide-react";
import { useAuth } from "../../context/AuthContext";
import NotificationCenterModal from "../alerts/NotificationCenterModal";

const Header = ({ title, subtitle }) => {
  const { user } = useAuth();
  const [showNotifications, setShowNotifications] = useState(false);

  return (
    <>
      <header className="h-16 bg-white border-b border-slate-200 px-6 flex items-center justify-between shadow-xs">
        <div>
          <h2 className="text-lg font-bold text-slate-900">{title}</h2>
          {subtitle && <p className="text-xs text-slate-500">{subtitle}</p>}
        </div>

        <div className="flex items-center gap-3">
          <div className="hidden sm:flex items-center gap-2 px-3 py-1 bg-indigo-50 border border-indigo-100 rounded-full text-xs font-medium text-indigo-700">
            <Sparkles size={14} className="text-indigo-600" />
            <span>AI Engine Active</span>
          </div>

          {/* Real-time Alerts Notification Bell */}
          <button
            onClick={() => setShowNotifications(true)}
            className="relative p-2 text-slate-600 hover:text-indigo-600 hover:bg-slate-100 rounded-full transition"
            title="Real-Time Alerts & Notification Webhooks"
          >
            <Bell size={19} />
            <span className="absolute top-1 right-1 flex h-2.5 w-2.5">
              <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-rose-400 opacity-75"></span>
              <span className="relative inline-flex rounded-full h-2.5 w-2.5 bg-rose-600"></span>
            </span>
          </button>

          <div className="flex items-center gap-2 pl-2 border-l border-slate-200">
            <span className="text-sm font-medium text-slate-700">
              {user?.firstName} {user?.lastName}
            </span>
            <span className="text-xs bg-slate-100 text-slate-600 px-2 py-0.5 rounded font-mono font-medium">
              {user?.role === "ROLE_ADMIN" ? "Admin" : "Manager"}
            </span>
          </div>
        </div>
      </header>

      <NotificationCenterModal
        isOpen={showNotifications}
        onClose={() => setShowNotifications(false)}
      />
    </>
  );
};

export default Header;
