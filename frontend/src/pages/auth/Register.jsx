import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { BrainCircuit, ArrowRight, UserPlus } from "lucide-react";
import { useAuth } from "../../context/AuthContext";
import InputField from "../../components/common/InputField";
import SelectField from "../../components/common/SelectField";

const Register = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    username: "",
    email: "",
    password: "",
    roleName: "ROLE_INVENTORY_MANAGER",
  });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    setError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (formData.password.length < 6) {
      setError("Password must be at least 6 characters long");
      return;
    }

    setLoading(true);
    try {
      await register(formData);
      navigate("/dashboard");
    } catch (err) {
      setError(
        err.response?.data?.message ||
        err.response?.data?.errors?.username ||
        err.response?.data?.errors?.email ||
        "Registration failed. Please check your information."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-slate-100 flex flex-col justify-center items-center p-4">
      <div className="w-full max-w-lg bg-white rounded-2xl shadow-xl border border-slate-200 overflow-hidden">
        {/* Header Banner */}
        <div className="bg-indigo-600 p-6 text-center text-white">
          <div className="w-12 h-12 bg-white/10 rounded-xl flex items-center justify-center mx-auto mb-2 backdrop-blur-xs border border-white/20">
            <BrainCircuit size={28} className="text-white" />
          </div>
          <h2 className="text-xl font-bold tracking-tight">DemandOpt AI</h2>
          <p className="text-xs text-indigo-200 mt-0.5">
            Create an account to access inventory optimization & forecasting
          </p>
        </div>

        {/* Form Body */}
        <form onSubmit={handleSubmit} className="p-8">
          <div className="flex items-center gap-2 mb-1">
            <UserPlus size={20} className="text-indigo-600" />
            <h3 className="text-lg font-bold text-slate-900">New User Registration</h3>
          </div>
          <p className="text-xs text-slate-500 mb-5">Enter your details to register as a system user</p>

          {error && (
            <div className="mb-4 p-3 bg-rose-50 border border-rose-200 text-rose-700 text-xs rounded-lg">
              {error}
            </div>
          )}

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
            <InputField
              label="First Name"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              placeholder="e.g. John"
              required
            />
            <InputField
              label="Last Name"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              placeholder="e.g. Doe"
              required
            />
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
            <InputField
              label="Username"
              name="username"
              value={formData.username}
              onChange={handleChange}
              placeholder="e.g. johndoe"
              required
            />
            <InputField
              label="Email Address"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="e.g. john@example.com"
              required
            />
          </div>

          <InputField
            label="Password"
            name="password"
            type="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="Min. 6 characters"
            required
          />

          <SelectField
            label="System Role"
            name="roleName"
            value={formData.roleName}
            onChange={handleChange}
            options={[
              { value: "ROLE_INVENTORY_MANAGER", label: "Inventory Manager (Stock, Orders & Sales)" },
              { value: "ROLE_ADMIN", label: "Administrator (Full System & Catalog Access)" },
            ]}
            required
          />

          <button
            type="submit"
            disabled={loading}
            className="w-full mt-3 py-2.5 px-4 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg text-sm font-semibold transition shadow-md flex items-center justify-center gap-2 disabled:opacity-50"
          >
            {loading ? "Creating Account..." : "Create Account & Sign In"}
            <ArrowRight size={16} />
          </button>

          <div className="mt-5 text-center text-xs text-slate-500">
            Already have an account?{" "}
            <Link to="/login" className="text-indigo-600 font-semibold hover:underline">
              Sign In here
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Register;
