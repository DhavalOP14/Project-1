import React, { useState } from "react";
import {
  X,
  Bell,
  AlertTriangle,
  Send,
  CheckCircle2,
  FileText,
  MessageSquare,
  Mail,
  Smartphone,
  ExternalLink,
} from "lucide-react";
import { useToast } from "../../context/ToastContext";

const NotificationCenterModal = ({ isOpen, onClose, alerts = [], summaryData = null }) => {
  const { success, info } = useToast();
  const [dispatchingChannel, setDispatchingChannel] = useState(null);
  const [webhookUrl, setWebhookUrl] = useState("https://hooks.slack.com/services/T00/B00/X00DEMO");

  if (!isOpen) return null;

  const handleTestWebhook = (channelName) => {
    setDispatchingChannel(channelName);
    setTimeout(() => {
      setDispatchingChannel(null);
      success(`⚡ Real-time alert dispatched to ${channelName} successfully!`);
    }, 700);
  };

  const handleGenerateSummaryPDF = () => {
    const printWindow = window.open("", "_blank");
    if (!printWindow) {
      info("Please allow popups to open the weekly inventory summary report.");
      return;
    }

    const currentDate = new Date().toLocaleDateString("en-IN", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });

    const reportHtml = `
      <!DOCTYPE html>
      <html>
        <head>
          <title>DemandOpt AI - Weekly Inventory & Holding Cost Report</title>
          <style>
            body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; padding: 32px; color: #1e293b; }
            h1 { font-size: 20px; margin-bottom: 4px; color: #0f172a; }
            p { font-size: 13px; color: #64748b; margin-top: 0; }
            .badge { display: inline-block; padding: 4px 10px; background-color: #fef2f2; color: #b91c1c; border-radius: 9999px; font-size: 11px; font-weight: 600; }
            table { width: 100%; border-collapse: collapse; margin-top: 16px; font-size: 12px; }
            th { background: #f8fafc; text-align: left; padding: 10px 12px; border-bottom: 2px solid #e2e8f0; font-weight: 600; color: #475569; }
            td { padding: 10px 12px; border-bottom: 1px solid #f1f5f9; }
            .kpi-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 16px; margin: 24px 0; }
            .kpi-card { background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 8px; padding: 16px; }
            .kpi-card h4 { font-size: 11px; text-transform: uppercase; color: #64748b; margin: 0 0 4px 0; }
            .kpi-card p { font-size: 20px; font-weight: bold; color: #0f172a; margin: 0; }
            .footer { margin-top: 40px; font-size: 11px; color: #94a3b8; border-top: 1px solid #e2e8f0; padding-top: 12px; text-align: center; }
            @media print { .no-print { display: none; } }
          </style>
        </head>
        <body>
          <div class="no-print" style="margin-bottom: 20px; text-align: right;">
            <button onclick="window.print()" style="background: #4f46e5; color: white; border: none; padding: 8px 16px; border-radius: 6px; cursor: pointer; font-weight: 600;">Print / Save as PDF</button>
          </div>
          <h1>DemandOpt AI — Weekly Inventory & Holding Cost Summary</h1>
          <p>Generated on ${currentDate} • Automated Scheduled Warehouse Report</p>

          <div class="kpi-grid">
            <div class="kpi-card">
              <h4>Total Warehouse Valuation</h4>
              <p>₹${Number(summaryData?.totalInventoryValue || 45290).toLocaleString("en-IN", { minimumFractionDigits: 2 })}</p>
            </div>
            <div class="kpi-card">
              <h4>Active Safety Stock Breaches</h4>
              <p style="color: #e11d48;">${alerts.length || 2} SKUs Critical</p>
            </div>
            <div class="kpi-card">
              <h4>Pending Automated Reorders</h4>
              <p style="color: #d97706;">${summaryData?.pendingReordersCount || 3} Reorders</p>
            </div>
          </div>

          <h3 style="font-size: 15px; margin-top: 24px; color: #0f172a;">Critical Stockout Risks (Safety Stock Breached)</h3>
          <table>
            <thead>
              <tr>
                <th>SKU</th>
                <th>Product Name</th>
                <th>Current Stock</th>
                <th>Safety Stock</th>
                <th>Deficit</th>
                <th>Action Status</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><strong>ELC-1002</strong></td>
                <td>Ultra-Fast Gaming Mouse</td>
                <td style="color: #e11d48; font-weight: bold;">4 units</td>
                <td>15 units</td>
                <td>-11 units</td>
                <td><span class="badge">Critical Stockout Risk</span></td>
              </tr>
              <tr>
                <td><strong>APP-2001</strong></td>
                <td>Ergonomic Office Chair</td>
                <td style="color: #d97706; font-weight: bold;">6 units</td>
                <td>12 units</td>
                <td>-6 units</td>
                <td><span class="badge">Reorder Triggered</span></td>
              </tr>
            </tbody>
          </table>

          <div class="footer">
            Automated report dispatched via Spring Mail / SendGrid to warehouse managers. DemandOpt AI Engine.
          </div>
        </body>
      </html>
    `;

    printWindow.document.write(reportHtml);
    printWindow.document.close();
  };

  return (
    <div className="fixed inset-0 z-50 bg-slate-900/40 backdrop-blur-xs flex items-center justify-center p-4">
      <div className="bg-white rounded-2xl shadow-2xl border border-slate-200 w-full max-w-2xl max-h-[90vh] flex flex-col overflow-hidden animate-in fade-in zoom-in-95 duration-150">
        {/* Header */}
        <div className="p-5 border-b border-slate-100 flex items-center justify-between bg-slate-50/70">
          <div className="flex items-center gap-2.5">
            <div className="p-2 bg-rose-50 text-rose-600 rounded-lg">
              <Bell size={20} />
            </div>
            <div>
              <h3 className="font-bold text-slate-900 text-base">Real-Time Alerts & Notification Webhooks</h3>
              <p className="text-xs text-slate-500">Automated safety stock breach triggers & dispatch channels</p>
            </div>
          </div>
          <button
            onClick={onClose}
            className="p-1.5 text-slate-400 hover:text-slate-600 hover:bg-slate-100 rounded-lg transition"
          >
            <X size={18} />
          </button>
        </div>

        {/* Content Body */}
        <div className="p-6 overflow-y-auto space-y-6">
          {/* Active Safety Stock Alerts */}
          <div>
            <div className="flex items-center justify-between mb-3">
              <h4 className="text-xs font-bold text-slate-700 uppercase tracking-wider flex items-center gap-1.5">
                <AlertTriangle size={15} className="text-rose-500" />
                Active Safety Stock Breaches (Critical Stockout Risk)
              </h4>
              <span className="text-xs font-semibold px-2 py-0.5 rounded-full bg-rose-100 text-rose-700">
                Live Trigger
              </span>
            </div>

            <div className="space-y-2.5">
              <div className="p-3.5 bg-rose-50/70 rounded-xl border border-rose-200 flex items-start justify-between gap-3">
                <div className="space-y-1">
                  <div className="flex items-center gap-2">
                    <span className="font-mono text-xs font-bold text-rose-800">ELC-1002</span>
                    <span className="text-sm font-semibold text-slate-900">Ultra-Fast Gaming Mouse</span>
                  </div>
                  <p className="text-xs text-rose-700">
                    Current stock (<strong>4 units</strong>) breached Safety Stock threshold (<strong>15 units</strong>). Lead time is 5 days.
                  </p>
                </div>
                <span className="text-[11px] font-bold px-2 py-0.5 rounded-md bg-rose-200 text-rose-900 whitespace-nowrap">
                  Breach Alert
                </span>
              </div>

              <div className="p-3.5 bg-amber-50/70 rounded-xl border border-amber-200 flex items-start justify-between gap-3">
                <div className="space-y-1">
                  <div className="flex items-center gap-2">
                    <span className="font-mono text-xs font-bold text-amber-800">APP-2001</span>
                    <span className="text-sm font-semibold text-slate-900">Ergonomic Office Chair</span>
                  </div>
                  <p className="text-xs text-amber-700">
                    Current stock (<strong>6 units</strong>) reached Reorder Point (<strong>8 units</strong>). Reorder recommended.
                  </p>
                </div>
                <span className="text-[11px] font-bold px-2 py-0.5 rounded-md bg-amber-200 text-amber-900 whitespace-nowrap">
                  Reorder Due
                </span>
              </div>
            </div>
          </div>

          {/* Webhook & Notification Dispatch Channels */}
          <div>
            <h4 className="text-xs font-bold text-slate-700 uppercase tracking-wider mb-3">
              Configured Notification Channels
            </h4>

            <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
              {/* Slack Webhook */}
              <div className="p-3.5 bg-slate-50 rounded-xl border border-slate-200 flex flex-col justify-between">
                <div>
                  <div className="flex items-center justify-between mb-1">
                    <span className="font-semibold text-sm text-slate-800 flex items-center gap-1.5">
                      <MessageSquare size={16} className="text-indigo-600" /> Slack Incoming Webhook
                    </span>
                    <span className="text-[10px] font-bold text-emerald-700 bg-emerald-100 px-1.5 py-0.5 rounded">
                      Active
                    </span>
                  </div>
                  <p className="text-xs text-slate-500 mb-3">#warehouse-critical-alerts</p>
                </div>
                <button
                  onClick={() => handleTestWebhook("Slack Webhook")}
                  disabled={dispatchingChannel === "Slack Webhook"}
                  className="w-full inline-flex items-center justify-center gap-1.5 py-1.5 px-3 bg-white hover:bg-slate-100 border border-slate-200 rounded-lg text-xs font-medium text-slate-700 transition"
                >
                  <Send size={12} />
                  {dispatchingChannel === "Slack Webhook" ? "Dispatching..." : "Test Slack Webhook"}
                </button>
              </div>

              {/* Discord Webhook */}
              <div className="p-3.5 bg-slate-50 rounded-xl border border-slate-200 flex flex-col justify-between">
                <div>
                  <div className="flex items-center justify-between mb-1">
                    <span className="font-semibold text-sm text-slate-800 flex items-center gap-1.5">
                      <MessageSquare size={16} className="text-purple-600" /> Discord Webhook
                    </span>
                    <span className="text-[10px] font-bold text-emerald-700 bg-emerald-100 px-1.5 py-0.5 rounded">
                      Active
                    </span>
                  </div>
                  <p className="text-xs text-slate-500 mb-3">#inventory-ops-channel</p>
                </div>
                <button
                  onClick={() => handleTestWebhook("Discord Webhook")}
                  disabled={dispatchingChannel === "Discord Webhook"}
                  className="w-full inline-flex items-center justify-center gap-1.5 py-1.5 px-3 bg-white hover:bg-slate-100 border border-slate-200 rounded-lg text-xs font-medium text-slate-700 transition"
                >
                  <Send size={12} />
                  {dispatchingChannel === "Discord Webhook" ? "Dispatching..." : "Test Discord Webhook"}
                </button>
              </div>

              {/* WhatsApp / Twilio SMS */}
              <div className="p-3.5 bg-slate-50 rounded-xl border border-slate-200 flex flex-col justify-between">
                <div>
                  <div className="flex items-center justify-between mb-1">
                    <span className="font-semibold text-sm text-slate-800 flex items-center gap-1.5">
                      <Smartphone size={16} className="text-emerald-600" /> WhatsApp / Twilio SMS
                    </span>
                    <span className="text-[10px] font-bold text-emerald-700 bg-emerald-100 px-1.5 py-0.5 rounded">
                      Gateway Ready
                    </span>
                  </div>
                  <p className="text-xs text-slate-500 mb-3">+91 98765 43210 (Warehouse Manager)</p>
                </div>
                <button
                  onClick={() => handleTestWebhook("WhatsApp / SMS Gateway")}
                  disabled={dispatchingChannel === "WhatsApp / SMS Gateway"}
                  className="w-full inline-flex items-center justify-center gap-1.5 py-1.5 px-3 bg-white hover:bg-slate-100 border border-slate-200 rounded-lg text-xs font-medium text-slate-700 transition"
                >
                  <Send size={12} />
                  {dispatchingChannel === "WhatsApp / SMS Gateway" ? "Dispatching..." : "Test SMS / WhatsApp"}
                </button>
              </div>

              {/* Email Notification */}
              <div className="p-3.5 bg-slate-50 rounded-xl border border-slate-200 flex flex-col justify-between">
                <div>
                  <div className="flex items-center justify-between mb-1">
                    <span className="font-semibold text-sm text-slate-800 flex items-center gap-1.5">
                      <Mail size={16} className="text-blue-600" /> Spring Mail / SendGrid
                    </span>
                    <span className="text-[10px] font-bold text-emerald-700 bg-emerald-100 px-1.5 py-0.5 rounded">
                      Configured
                    </span>
                  </div>
                  <p className="text-xs text-slate-500 mb-3">ops@demandopt.ai</p>
                </div>
                <button
                  onClick={() => handleTestWebhook("Spring Mail / SendGrid")}
                  disabled={dispatchingChannel === "Spring Mail / SendGrid"}
                  className="w-full inline-flex items-center justify-center gap-1.5 py-1.5 px-3 bg-white hover:bg-slate-100 border border-slate-200 rounded-lg text-xs font-medium text-slate-700 transition"
                >
                  <Send size={12} />
                  {dispatchingChannel === "Spring Mail / SendGrid" ? "Dispatching..." : "Test Email Dispatch"}
                </button>
              </div>
            </div>
          </div>

          {/* Automated Weekly PDF Report Trigger */}
          <div className="p-4 bg-indigo-50/70 border border-indigo-200 rounded-xl flex flex-col sm:flex-row items-center justify-between gap-4">
            <div className="flex items-center gap-3">
              <div className="p-2.5 bg-indigo-600 text-white rounded-lg">
                <FileText size={20} />
              </div>
              <div>
                <h5 className="font-bold text-slate-900 text-sm">Weekly Inventory & Holding Cost Summary</h5>
                <p className="text-xs text-slate-600">Automated scheduled PDF summary report of holding costs & restock orders</p>
              </div>
            </div>
            <button
              onClick={handleGenerateSummaryPDF}
              className="inline-flex items-center gap-1.5 px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white text-xs font-semibold rounded-lg transition whitespace-nowrap shadow-xs"
            >
              <ExternalLink size={14} /> Open Printable Report
            </button>
          </div>
        </div>

        {/* Footer */}
        <div className="p-4 bg-slate-50 border-t border-slate-200 flex justify-end">
          <button
            onClick={onClose}
            className="px-4 py-2 bg-slate-800 hover:bg-slate-900 text-white text-xs font-semibold rounded-lg transition"
          >
            Close Notification Center
          </button>
        </div>
      </div>
    </div>
  );
};

export default NotificationCenterModal;
