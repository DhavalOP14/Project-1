import api from "./api";

export const getAllInventory = async () => {
  const response = await api.get("/inventory");
  return response.data;
};

export const getInventoryByProductId = async (productId) => {
  const response = await api.get(`/inventory/product/${productId}`);
  return response.data;
};

export const updateStock = async (data) => {
  const response = await api.post("/inventory/update", data);
  return response.data;
};

export const getLowStockAlerts = async () => {
  const response = await api.get("/inventory/alerts");
  return response.data;
};
