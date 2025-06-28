import React from "react";
import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Tooltip,
} from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Tooltip);

function ForecastChart({ forecast }) {
  const data = {
    labels: ["Predicted Sales (Next Day)"],
    datasets: [
      {
        label: "Units",
        data: [forecast],
        backgroundColor: ["rgba(54, 162, 235, 0.6)"],
      },
    ],
  };

  return <Bar data={data} />;
}

export default ForecastChart;
