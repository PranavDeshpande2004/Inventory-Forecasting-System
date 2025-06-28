// import React, { useState } from "react";
// import axios from "axios";

// const ForecastForm = () => {
//   const [productName, setProductName] = useState("");
//   const [results, setResults] = useState([]);

//   const handleForecast = async () => {
//     if (!productName) {
//       alert("❗ Please enter a product name.");
//       return;
//     }

//     try {
//       // Step 1: Get processed sales_history from Spring Boot
//       const historyRes = await axios.get(
//         `http://localhost:8080/api/sales/forecast/${encodeURIComponent(productName)}`
//       );

//       const sales_history = historyRes.data.sales_history;

//       if (!sales_history || sales_history.length === 0) {
//         alert("No historical data found.");
//         return;
//       }

//       // Step 2: Send to Flask for forecast
//       const response = await axios.post("http://localhost:5000/forecast", {
//         sales_history,
//       });

//       setResults(response.data);
//     } catch (err) {
//       console.error("❌ Forecast error:", err);
//       alert("Forecast failed. Check backend.");
//     }
//   };

//   return (
//     <div className="p-4 bg-gray-50 border rounded shadow max-w-lg mx-auto mt-5">
//       <h2 className="text-xl font-semibold mb-2">🔮 Forecast Product Sales</h2>
//       <input
//         type="text"
//         placeholder="Enter product name"
//         value={productName}
//         onChange={(e) => setProductName(e.target.value)}
//         className="border px-3 py-2 rounded w-full mb-3"
//       />
//       <button
//         onClick={handleForecast}
//         className="bg-blue-600 text-white px-4 py-2 rounded"
//       >
//         📈 Forecast
//       </button>

//       {results.length > 0 && (
//         <div className="mt-5">
//           <h3 className="font-bold">Results:</h3>
//           <ul className="list-disc ml-6">
//             {results.map((item, index) => (
//               <li key={index}>
//                 {item.product}: <strong>{item.forecasted_quantity}</strong>
//               </li>
//             ))}
//           </ul>
//         </div>
//       )}
//     </div>
//   );
// };

// export default ForecastForm;


import React, { useState } from "react";
import axios from "axios";

const ForecastForm = () => {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleForecast = async () => {
    setLoading(true);
    try {
      const response = await axios.get("http://localhost:8080/api/sales/forecast");

      // If Flask returns an array
      const data = response.data;

      if (Array.isArray(data)) {
        setResults(data);
      } else if (data && data.forecast) {
        setResults(data.forecast); // In case Flask returns { forecast: [...] }
      } else {
        setResults([]);
      }

      console.log("📊 Forecast results:", data);
    } catch (error) {
      console.error("❌ Forecast failed", error);
      alert("Forecasting failed. Check backend server or input format.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-4 border rounded bg-gray-100 shadow">
      <button
        onClick={handleForecast}
        className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
      >
        🔮 Forecast Sales
      </button>

      {loading && <p className="mt-4 text-blue-600">Processing forecast...</p>}

      {results.length > 0 && (
        <div className="mt-6">
          <h2 className="text-lg font-semibold mb-2">📈 Forecast Results</h2>
          <ul className="list-disc pl-5 space-y-1">
            {results.map((item, idx) => (
              <li key={idx}>
                <span className="font-medium">{item.product}</span>:{" "}
                <span className="text-green-700">{item.forecasted_quantity}</span>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default ForecastForm;

