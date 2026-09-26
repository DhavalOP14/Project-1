import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { BrainCircuit, Lock, User, ArrowRight, UserPlus } from "lucide-react";
import { useAuth } from "../../context/AuthContext";
import InputField from "../../components/common/InputField";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      await login({ username, password });
      navigate("/dashboard");
    } catch (err) {
      setError(err.response?.data?.message || "Invalid username or password");
    } finally {
      setLoading(false);
    }
  };

  const handleQuickLogin = (user, pass) => {
    setUsername(user);
    setPassword(pass);
  };

  return (
    <div className="min-h-screen bg-slate-100 flex flex-col justify-center items-center p-4">
      <div className="w-full max-w-md bg-white rounded-2xl shadow-xl border border-slate-200 overflow-hidden">
        {/* Header Banner */}
        <div className="bg-indigo-600 p-8 text-center text-white">
          <div className="w-14 h-14 bg-white/10 rounded-2xl flex items-center justify-center mx-auto mb-3 backdrop-blur-xs border border-white/20">
            <BrainCircuit size={32} className="text-white" />
          </div>
          <h2 className="text-2xl font-bold tracking-tight">DemandOpt AI</h2>
          <p className="text-xs text-indigo-200 mt-1 max-w-xs mx-auto">
            AI-Powered Demand Forecasting and Inventory Optimization System
          </p>
        </div>

        {/* Form Body */}
        <form onSubmit={handleSubmit} className="p-8">
          <h3 className="text-lg font-bold text-slate-900 mb-1">Welcome Back</h3>
          <p className="text-xs text-slate-500 mb-6">Sign in to access inventory models and predictions</p>

          {error && (
            <div className="mb-4 p-3 bg-rose-50 border border-rose-200 text-rose-700 text-xs rounded-lg">
              {error}
            </div>
          )}

          <InputField
            label="Username or Email"
            name="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="admin or manager"
            required
          />

          <InputField
            label="Password"
            name="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="••••••••"
            required
          />

          <button
            type="submit"
            disabled={loading}
            className="w-full mt-2 py-2.5 px-4 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg text-sm font-semibold transition shadow-md flex items-center justify-center gap-2 disabled:opacity-50"
          >
            {loading ? "Authenticating..." : "Sign In to System"}
            <ArrowRight size={16} />
          </button>

          <div className="mt-4 text-center text-xs text-slate-600">
            Don't have an account?{" "}
            <Link to="/register" className="text-indigo-600 font-semibold hover:underline">
              Create an account / Register
            </Link>
          </div>

          {/* Quick Demo Logins */}
          <div className="mt-6 pt-5 border-t border-slate-100">
            <p className="text-xs font-semibold text-slate-400 uppercase tracking-wider text-center mb-3">
              One-Click Demo Credentials
            </p>
            <div className="grid grid-cols-2 gap-2">
              <button
                type="button"
                onClick={() => handleQuickLogin("admin", "Password@123")}
                className="px-3 py-2 text-xs font-medium bg-slate-50 hover:bg-indigo-50 hover:text-indigo-600 border border-slate-200 rounded-lg text-slate-700 transition text-center"
              >
                Log in as <strong className="block text-slate-900">Admin</strong>
              </button>
              <button
                type="button"
                onClick={() => handleQuickLogin("manager", "Password@123")}
                className="px-3 py-2 text-xs font-medium bg-slate-50 hover:bg-indigo-50 hover:text-indigo-600 border border-slate-200 rounded-lg text-slate-700 transition text-center"
              >
                Log in as <strong className="block text-slate-900">Manager</strong>
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;
