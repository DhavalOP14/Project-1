import api from "./api";

export const recordSale = async (data) => {
  const response = await api.post("/sales", data);
  return response.data;
};

export const getAllSales = async () => {
  const response = await api.get("/sales");
  return response.data;
};

export const getSaleById = async (id) => {
  const response = await api.get(`/sales/${id}`);
  return response.data;
};
