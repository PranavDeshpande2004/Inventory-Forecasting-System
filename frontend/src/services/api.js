import axios from "axios";

const BASE_URL = "http://localhost:8080/api";

export const getProducts = async () => {
  const res = await axios.get(`${BASE_URL}/products`);
  return res.data;
};

export const getForecast = async (productId) => {
  const res = await axios.get(`${BASE_URL}/sales/forecast/${productId}`);
  return res.data;
};


