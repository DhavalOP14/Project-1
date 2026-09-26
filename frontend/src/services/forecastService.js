import api from "./api";

export const generateForecast = async (data) => {
  const response = await api.post("/forecasts/generate", data);
  return response.data;
};

export const getForecastHistory = async (productId) => {
  const response = await api.get(`/forecasts/product/${productId}`);
  return response.data;
};

export const getAllLatestForecasts = async () => {
  const response = await api.get("/forecasts/latest");
  return response.data;
};

export const runBatchForecasting = async () => {
  const response = await api.post("/forecasts/batch-run");
  return response.data;
};
