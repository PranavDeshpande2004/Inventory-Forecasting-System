import React from "react";
import SalesUpload from "../components/SalesUpload";
import ForecastForm from "../components/ForeCastForm";

const Dashboard = () => {
  return (
    <div className="p-8">
      <h1 className="text-2xl mb-4">📈 Inventory Dashboard</h1>
      <SalesUpload />
      <h1 className="text-2xl mb-4">📊 Inventory Forecasting Dashboard</h1>
      <ForecastForm />
    </div>
    
  );
};

export default Dashboard;
