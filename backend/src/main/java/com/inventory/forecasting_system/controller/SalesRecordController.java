package com.inventory.forecasting_system.controller;

import com.inventory.forecasting_system.model.SalesRecord;
import com.inventory.forecasting_system.service.SalesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*")
public class SalesRecordController {

    @Autowired
    private SalesRecordService salesService;

    @GetMapping
    public List<SalesRecord> getAllSales() {
        return salesService.getAllSales();
    }

    @PostMapping
    public SalesRecord addSales(@RequestBody SalesRecord record) {
        return salesService.addSalesRecord(record);
    }

    @GetMapping("/{productId}")
    public List<SalesRecord> getSalesByProductAndDate(
            @PathVariable Long productId,
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return salesService.getSalesByProductAndDate(productId, start, end);
    }

//    @PostMapping("/upload")
//    public String uploadCSV(@RequestParam("file") MultipartFile file) {
//        return salesService.importCSV(file);
//    }

//    @GetMapping("/forecast/{productId}")
//    public ResponseEntity<?> forecastSales(@PathVariable Long productId) {
//        try {
//            // 1. Fetch past 60 days sales
//            LocalDate end = LocalDate.now();
//            LocalDate start = end.minusDays(60);
//
//            List<SalesRecord> history = salesService.getSalesByProductAndDate(productId, start, end);
//
//            // 2. Convert to List of integers (quantitySold)
//            List<Integer> pastSales = history.stream()
//                    .map(SalesRecord::getQuantitySold)
//                    .collect(Collectors.toList());
//
//            // 3. Call Flask API with pastSales
//            RestTemplate restTemplate = new RestTemplate();
//            String flaskUrl = "http://localhost:5000/forecast";
//
//            Map<String, Object> requestPayload = new HashMap<>();
//            requestPayload.put("sales_history", pastSales);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);
//
//            ResponseEntity<Map> response = restTemplate.postForEntity(flaskUrl, request, Map.class);
//
//            return ResponseEntity.ok(response.getBody());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error during forecasting");
//        }
//    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        try {
            salesService.saveSalesFromCsv(file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }


}

