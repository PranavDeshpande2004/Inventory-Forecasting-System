from flask import Flask, request, jsonify
import pickle
import pandas as pd
from datetime import datetime

app = Flask(__name__)

# Load the model and encoder
model = pickle.load(open('xgb_model.pkl', 'rb'))
le = pickle.load(open('label_encoder.pkl', 'rb'))

@app.route('/forecast', methods=['POST'])
def forecast():
    try:
        data = request.json.get("sales_history", [])
        print(" Received request:", data)

        predictions = []
        for entry in data:
            product_name = entry['product_name']
            date_str = entry['sale_date']
            date = datetime.strptime(date_str, "%Y-%m-%d")

            product_encoded = le.transform([product_name])[0]
            features = [[
                product_encoded,
                date.day,
                date.month,
                date.weekday()
            ]]
            prediction = model.predict(features)[0]
            predictions.append({
                "product": product_name,
                "forecasted_quantity": round(prediction)
            })

        return jsonify(predictions)
    except Exception as e:
        print(" Error:", e)
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
