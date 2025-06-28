# 📦 Inventory & Sales Forecasting System

A robust full-stack application for managing inventory and predicting future product demand using machine learning. This system helps businesses maintain optimal stock levels and make data-driven decisions to reduce stockouts and overstocking.

---

## 🚀 Key Features

- 🔐 **JWT Authentication**
  - User registration and login with secure token-based authentication.
  - Stateless session handling using Spring Security.

- 📦 **Inventory Management**
  - Add, edit, and delete product entries.
  - Reorder level notifications based on current stock.

- 📊 **Sales Data Handling**
  - Upload historical sales records via CSV.
  - Automatically stores and maps data to associated products.

- 🔮 **Sales Forecasting**
  - Predicts future sales using a trained machine learning model.
  - Communicates with a Python Flask microservice for real-time forecasting.

- 💻 **Full Stack Integration**
  - React.js for interactive UI
  - Spring Boot for business logic and API endpoints
  - Flask for predictive analytics
  - MySQL for persistent data storage

---

## 🛠 Tech Stack

| Layer        | Technology Used                        |
|--------------|-----------------------------------------|
| **Frontend** | React.js, Tailwind CSS, Axios           |
| **Backend**  | Spring Boot, Spring Security, JWT, JPA  |
| **Database** | MySQL                                   |
| **ML API**   | Python, Flask                           |  
| **Tools**    | Maven, Postman, Git, IntelliJ           |

---

## 🔄 Workflow Overview

1. 👨‍💼 **User Login**: Authenticates via JWT.
2. 📋 **Product Management**: Admin adds or updates products.
3. 📤 **Upload Sales Data**: Upload CSV with product name and sale date.
4. 📥 **Backend Parses & Stores**: Spring Boot processes and stores data in MySQL.
5. 🔁 **Forecasting Request**: Java backend sends `sales_history` to Python API.
6. 🧠 **Python ML Model**: Predicts next quantity based on historical sales.
7. 📈 **Results Rendered**: Forecast appears in the React dashboard.

---



## 🧪 Sample Forecast Output

```json
[
  { "product": "Staples", "forecasted_quantity": 3 },
  { "product": "Easy-staple paper", "forecasted_quantity": 4 },
  { "product": "Storex Dura Pro Binders", "forecasted_quantity": 1 }
]




