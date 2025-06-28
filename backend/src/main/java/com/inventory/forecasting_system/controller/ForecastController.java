package com.inventory.forecasting_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.forecasting_system.dto.SalesRequest;
import com.inventory.forecasting_system.model.Product;
import com.inventory.forecasting_system.model.SalesRecord;
import com.inventory.forecasting_system.repository.ProductRepository;
import com.inventory.forecasting_system.repository.SalesRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*") // allow frontend
public class ForecastController {
    @Autowired
    private SalesRecordRepository salesRecordRepository;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/forecast")
    public ResponseEntity<?> uploadAndForecast(@RequestParam("file") MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            List<SalesRequest> history = new ArrayList<>();

            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // ✅ Ensure CSV has all 3 columns
                if (parts.length >= 3) {
                    String productName = parts[0].trim();
                    String saleDate = parts[2].trim();

                    // ✅ Basic check on date format
                    if (!productName.isEmpty() && saleDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        history.add(new SalesRequest(productName, saleDate));
                    } else {
                        System.out.println("⚠️ Skipped invalid row: " + Arrays.toString(parts));
                    }
                }
            }

            // 🚨 Check if list is empty
            if (history.isEmpty()) {
                return ResponseEntity.badRequest().body("❌ No valid rows in the uploaded CSV.");
            }

            // Wrap into Flask expected JSON
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("sales_history", history);

            ObjectMapper mapper = new ObjectMapper();
            System.out.println("📤 Final JSON to Flask: " + mapper.writeValueAsString(requestBody));

            // Send to Flask
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://localhost:5000/forecast", requestEntity, String.class);

            return ResponseEntity.ok(response.getBody());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Error processing CSV file.");
        }
    }

//    @GetMapping("/forecast/{productName}")
//    public ResponseEntity<?> getForecastData(@PathVariable String productName) {
//
//        // Fetch product sales history from DB (filter by product name)
//        List<SalesRecord> records = salesRecordRepository
//                .findByProductNameIgnoreCase(productName);
//
//        List<SalesRequest> history = records.stream()
//                .map(record -> new SalesRequest(record.getProduct().getName(), record.getSaleDate().toString()))
//                .collect(Collectors.toList());
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("sales_history", history);
//
//        return ResponseEntity.ok(response);
//    }


    @GetMapping("/forecast")
    public ResponseEntity<?> getForecastData() {
        // Step 1: Fetch all sales records
        List<SalesRecord> salesRecords = salesRecordRepository.findAll();

        // Step 2: Convert to sales_history format
        List<Map<String, String>> salesHistory = new ArrayList<>();

        for (SalesRecord record : salesRecords) {
            Optional<Product> productOpt = productRepository.findById(record.getProduct().getId());
            if (productOpt.isPresent()) {
                Map<String, String> entry = new HashMap<>();
                entry.put("product_name", productOpt.get().getName());
                entry.put("sale_date", record.getSaleDate().toString()); // Format: yyyy-MM-dd
                salesHistory.add(entry);
            }
        }

        // Step 3: Send to Flask server
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("sales_history", salesHistory);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> flaskResponse = restTemplate.postForEntity(
                "http://localhost:5000/forecast",
                request,
                String.class
        );

        return ResponseEntity.ok(flaskResponse.getBody());
    }



}
