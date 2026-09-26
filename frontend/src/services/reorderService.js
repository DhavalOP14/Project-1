import api from "./api";

export const getAllRecommendations = async () => {
  const response = await api.get("/reorders");
  return response.data;
};

export const getRecommendationsByStatus = async (status) => {
  const response = await api.get(`/reorders/status/${status}`);
  return response.data;
};

export const updateRecommendationStatus = async (id, status) => {
  const response = await api.put(`/reorders/${id}/status`, { status });
  return response.data;
};

export const triggerReorderAnalysis = async () => {
  const response = await api.post("/reorders/analyze");
  return response.data;
};
