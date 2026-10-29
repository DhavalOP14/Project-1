import React, { useState, useEffect } from "react";
import InputField from "../common/InputField";
import SelectField from "../common/SelectField";
import { getAllCategories } from "../../services/categoryService";

const ProductForm = ({ initialData, onSubmit, isEdit = false, loading = false }) => {
  const [formData, setFormData] = useState({
    sku: "",
    name: "",
    description: "",
    categoryId: "",
    unitPrice: "",
    unitCost: "",
    leadTimeDays: 7,
    orderCost: 50.0,
    holdingCostRate: 0.2,
    initialStock: 0,
  });

  const [categories, setCategories] = useState([]);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    fetchCategories();
    if (initialData) {
      setFormData({
        sku: initialData.sku || "",
        name: initialData.name || "",
        description: initialData.description || "",
        categoryId: initialData.category?.id || initialData.categoryId || "",
        unitPrice: initialData.unitPrice || "",
        unitCost: initialData.unitCost || "",
        leadTimeDays: initialData.leadTimeDays || 7,
        orderCost: initialData.orderCost || 50.0,
        holdingCostRate: initialData.holdingCostRate || 0.2,
        initialStock: initialData.currentStock || 0,
      });
    }
  }, [initialData]);

  const fetchCategories = async () => {
    try {
      const data = await getAllCategories();
      setCategories(data.map((c) => ({ value: c.id, label: c.name })));
    } catch (err) {
      console.error("Failed to load categories", err);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    if (errors[name]) {
      setErrors((prev) => ({ ...prev, [name]: null }));
    }
  };

  const validate = () => {
    const errs = {};
    if (!formData.sku?.trim() && !isEdit) errs.sku = "SKU is required";
    if (!formData.name?.trim()) errs.name = "Product name is required";
    if (!formData.categoryId) errs.categoryId = "Category is required";
    if (!formData.unitPrice || Number(formData.unitPrice) <= 0) errs.unitPrice = "Valid price > 0 required";
    if (!formData.unitCost || Number(formData.unitCost) <= 0) errs.unitCost = "Valid cost > 0 required";
    if (!formData.leadTimeDays || Number(formData.leadTimeDays) < 1) errs.leadTimeDays = "Lead time min 1 day";
    setErrors(errs);
    return Object.keys(errs).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validate()) return;

    const payload = {
      ...formData,
      categoryId: Number(formData.categoryId),
      unitPrice: Number(formData.unitPrice),
      unitCost: Number(formData.unitCost),
      leadTimeDays: Number(formData.leadTimeDays),
      orderCost: Number(formData.orderCost),
      holdingCostRate: Number(formData.holdingCostRate),
      initialStock: Number(formData.initialStock || 0),
    };
    onSubmit(payload);
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white p-6 rounded-xl border border-slate-200 shadow-sm max-w-2xl">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {!isEdit && (
          <InputField
            label="Product SKU"
            name="sku"
            value={formData.sku}
            onChange={handleChange}
            placeholder="e.g. ELC-1001"
            required
            error={errors.sku}
          />
        )}
        <div className={isEdit ? "md:col-span-2" : ""}>
          <InputField
            label="Product Name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="e.g. Wireless Ergonomic Mouse"
            required
            error={errors.name}
          />
        </div>

        <SelectField
          label="Category"
          name="categoryId"
          value={formData.categoryId}
          onChange={handleChange}
          options={categories}
          required
          error={errors.categoryId}
        />

        <InputField
          label="Unit Selling Price (₹)"
          name="unitPrice"
          type="number"
          step="0.01"
          value={formData.unitPrice}
          onChange={handleChange}
          placeholder="0.00"
          required
          error={errors.unitPrice}
        />

        <InputField
          label="Unit Purchase Cost (₹)"
          name="unitCost"
          type="number"
          step="0.01"
          value={formData.unitCost}
          onChange={handleChange}
          placeholder="0.00"
          required
          error={errors.unitCost}
        />

        <InputField
          label="Supplier Lead Time (Days)"
          name="leadTimeDays"
          type="number"
          min="1"
          value={formData.leadTimeDays}
          onChange={handleChange}
          required
          error={errors.leadTimeDays}
        />

        <InputField
          label="Ordering / Setup Cost (₹)"
          name="orderCost"
          type="number"
          step="0.01"
          value={formData.orderCost}
          onChange={handleChange}
        />

        <InputField
          label="Holding Cost Rate (e.g. 0.20 = 20%)"
          name="holdingCostRate"
          type="number"
          step="0.01"
          min="0"
          max="1"
          value={formData.holdingCostRate}
          onChange={handleChange}
        />

        {!isEdit && (
          <InputField
            label="Initial Stock Quantity"
            name="initialStock"
            type="number"
            min="0"
            value={formData.initialStock}
            onChange={handleChange}
          />
        )}
      </div>

      <div className="mt-4">
        <label className="block text-sm font-medium text-slate-700 mb-1">Description</label>
        <textarea
          name="description"
          rows={3}
          value={formData.description}
          onChange={handleChange}
          placeholder="Optional product specifications, details..."
          className="w-full px-3 py-2 bg-white border border-slate-300 rounded-lg text-sm text-slate-800 focus:outline-none focus:ring-2 focus:ring-indigo-500"
        />
      </div>

      <div className="mt-6 flex justify-end gap-3">
        <button
          type="submit"
          disabled={loading}
          className="px-5 py-2.5 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg text-sm font-medium transition shadow-sm disabled:opacity-50"
        >
          {loading ? "Saving..." : isEdit ? "Update Product" : "Create Product"}
        </button>
      </div>
    </form>
  );
};

export default ProductForm;
