import React, { useState } from "react";
import axios from "axios";

const SalesUpload = () => {
  const [file, setFile] = useState(null);

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile && selectedFile.type === "text/csv") {
      setFile(selectedFile);
    } else {
      alert("❗ Please select a valid .csv file");
    }
  };

  const handleUpload = async () => {
    if (!file) {
      alert("⚠️ Please select a file first.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      const res = await axios.post("http://localhost:8080/api/sales/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      console.log("✅ Server Response:", res.data);
      alert("✅ Upload successful");
    } catch (error) {
      console.error("❌ Upload failed", error);
      if (error.response) {
        alert("❌ Error: " + error.response.data);
      } else {
        alert("❌ Upload failed. Check server is running.");
      }
    }
  };

  return (
    <div className="p-4 border rounded shadow-md w-full max-w-md mx-auto bg-white mt-10">
      <h2 className="text-xl font-semibold mb-2 text-gray-800">📤 Upload Sales CSV</h2>
      <input
        type="file"
        accept=".csv"
        onChange={handleFileChange}
        className="mb-4"
      />
      <button
        onClick={handleUpload}
        className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded"
      >
        Upload
      </button>
    </div>
  );
};

export default SalesUpload;
