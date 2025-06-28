import React from "react";
import ProductList from "./components/ProductList";
import Dashboard from "./pages/Dashboard";

function App() {
  return (
    <div className="max-w-2xl mx-auto mt-10">
      <ProductList />
      <Dashboard/>
    </div>
  );
}

export default App;
