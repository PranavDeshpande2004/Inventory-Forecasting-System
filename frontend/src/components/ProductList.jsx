import React, { useEffect, useState } from "react";
import { getProducts, getForecast } from "../services/api";
import ForecastChart from "./ForecastChart";

function ProductList() {
  const [products, setProducts] = useState([]);
  const [forecast, setForecast] = useState(null);
  const [selectedProduct, setSelectedProduct] = useState(null);

  useEffect(() => {
    getProducts().then(setProducts);
  }, []);

  const handleForecast = async (product) => {
    const forecastData = await getForecast(product.id);
    setForecast(forecastData.forecast);
    setSelectedProduct(product.name);
  };

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-2">📦 Product Inventory</h2>
      <ul className="space-y-2">
        {products.map((p) => (
          <li key={p.id} className="border p-2 rounded flex justify-between items-center">
            <div>
              <strong>{p.name}</strong> — Stock: {p.stock}
            </div>
            <button
              onClick={() => handleForecast(p)}
              className="bg-blue-500 text-white px-3 py-1 rounded"
            >
              Forecast Sales
            </button>
          </li>
        ))}
      </ul>

      {forecast !== null && (
        <div className="mt-6">
          <h3 className="text-lg font-semibold">📈 Forecast for: {selectedProduct}</h3>
          <ForecastChart forecast={forecast} />
        </div>
      )}
    </div>
  );
}

export default ProductList;
