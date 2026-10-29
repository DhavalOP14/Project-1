import React, { useState, useEffect } from "react";
import { Plus, Trash2, ShoppingCart } from "lucide-react";
import InputField from "../common/InputField";
import SelectField from "../common/SelectField";
import { getAllProducts } from "../../services/productService";

const SaleForm = ({ onSubmit, loading = false }) => {
  const [saleDate, setSaleDate] = useState(new Date().toISOString().split("T")[0]);
  const [notes, setNotes] = useState("");
  const [items, setItems] = useState([{ productId: "", quantity: 1, unitPrice: 0, maxStock: 0 }]);
  const [products, setProducts] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const data = await getAllProducts();
      setProducts(data);
    } catch (err) {
      console.error("Failed to load products", err);
    }
  };

  const handleProductChange = (index, productId) => {
    const selected = products.find((p) => String(p.id) === String(productId));
    const newItems = [...items];
    newItems[index] = {
      productId: Number(productId),
      quantity: 1,
      unitPrice: selected ? Number(selected.unitPrice) : 0,
      maxStock: selected ? Number(selected.currentStock) : 0,
    };
    setItems(newItems);
  };

  const handleQuantityChange = (index, qty) => {
    const newItems = [...items];
    newItems[index].quantity = Number(qty);
    setItems(newItems);
  };

  const addItemRow = () => {
    setItems([...items, { productId: "", quantity: 1, unitPrice: 0, maxStock: 0 }]);
  };

  const removeItemRow = (index) => {
    if (items.length === 1) return;
    setItems(items.filter((_, idx) => idx !== index));
  };

  const calculateTotal = () => {
    return items.reduce((acc, it) => acc + (it.unitPrice * (it.quantity || 0)), 0);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError("");

    // Validate
    for (const it of items) {
      if (!it.productId) {
        setError("Please select a product for all item rows");
        return;
      }
      if (!it.quantity || it.quantity < 1) {
        setError("Quantity must be at least 1");
        return;
      }
      if (it.quantity > it.maxStock) {
        setError(`Quantity (${it.quantity}) exceeds available stock (${it.maxStock})`);
        return;
      }
    }

    const payload = {
      saleDate,
      notes,
      items: items.map((it) => ({
        productId: it.productId,
        quantity: it.quantity,
        unitPrice: it.unitPrice,
      })),
    };

    onSubmit(payload);
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white p-6 rounded-xl border border-slate-200 shadow-sm max-w-3xl">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
        <InputField
          label="Transaction Date"
          type="date"
          value={saleDate}
          onChange={(e) => setSaleDate(e.target.value)}
          required
        />
        <InputField
          label="Reference / Customer Notes"
          value={notes}
          onChange={(e) => setNotes(e.target.value)}
          placeholder="e.g. Retail POS Walk-in, Client order..."
        />
      </div>

      <div className="border-t border-slate-100 pt-4 mb-4">
        <div className="flex justify-between items-center mb-3">
          <h4 className="text-sm font-bold text-slate-800 flex items-center gap-1.5">
            <ShoppingCart size={16} /> Order Line Items
          </h4>
          <button
            type="button"
            onClick={addItemRow}
            className="inline-flex items-center gap-1 text-xs font-semibold text-indigo-600 bg-indigo-50 hover:bg-indigo-100 px-2.5 py-1.5 rounded-lg transition"
          >
            <Plus size={14} /> Add Product
          </button>
        </div>

        {error && (
          <div className="mb-4 p-3 bg-rose-50 border border-rose-200 text-rose-700 text-xs rounded-lg">
            {error}
          </div>
        )}

        <div className="space-y-3">
          {items.map((row, idx) => (
            <div key={idx} className="flex flex-col sm:flex-row items-center gap-3 p-3 bg-slate-50 rounded-lg border border-slate-200">
              <div className="flex-1 w-full sm:w-auto">
                <SelectField
                  name={`prod-${idx}`}
                  value={row.productId}
                  onChange={(e) => handleProductChange(idx, e.target.value)}
                  options={products.map((p) => ({
                    value: p.id,
                    label: `${p.sku} - ${p.name} (Available: ${p.currentStock})`,
                  }))}
                  placeholder="Select Product..."
                />
              </div>

              <div className="w-24">
                <InputField
                  name={`qty-${idx}`}
                  type="number"
                  min="1"
                  max={row.maxStock || 9999}
                  value={row.quantity}
                  onChange={(e) => handleQuantityChange(idx, e.target.value)}
                  placeholder="Qty"
                />
              </div>

              <div className="w-28 text-right font-medium text-sm text-slate-800">
                ₹{(row.unitPrice * (row.quantity || 0)).toFixed(2)}
              </div>

              <button
                type="button"
                onClick={() => removeItemRow(idx)}
                disabled={items.length === 1}
                className="p-2 text-slate-400 hover:text-rose-600 rounded transition disabled:opacity-30"
              >
                <Trash2 size={16} />
              </button>
            </div>
          ))}
        </div>
      </div>

      <div className="border-t border-slate-100 pt-4 flex flex-col sm:flex-row justify-between items-center gap-4">
        <div className="text-slate-600 text-sm">
          Grand Total:{" "}
          <span className="text-xl font-bold text-slate-900">₹{calculateTotal().toFixed(2)}</span>
        </div>

        <div className="flex gap-2">
          <button
            type="submit"
            disabled={loading}
            className="px-5 py-2.5 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg text-sm font-medium transition shadow-sm disabled:opacity-50"
          >
            {loading ? "Recording..." : "Complete & Deduct Stock"}
          </button>
        </div>
      </div>
    </form>
  );
};

export default SaleForm;
