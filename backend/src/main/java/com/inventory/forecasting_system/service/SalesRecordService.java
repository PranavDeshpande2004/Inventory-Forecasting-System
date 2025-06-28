package com.inventory.forecasting_system.service;

import com.inventory.forecasting_system.model.Product;
import com.inventory.forecasting_system.model.SalesRecord;
import com.inventory.forecasting_system.repository.ProductRepository;
import com.inventory.forecasting_system.repository.SalesRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SalesRecordService {

    @Autowired
    private SalesRecordRepository salesRepo;

    @Autowired
    private ProductRepository productRepo;

    public List<SalesRecord> getAllSales() {
        return salesRepo.findAll();
    }

    public SalesRecord addSalesRecord(SalesRecord record) {
        return salesRepo.save(record);
    }

    public List<SalesRecord> getSalesByProductAndDate(Long productId, LocalDate start, LocalDate end) {
        Product product = productRepo.findById(productId).orElse(null);
        return salesRepo.findByProductAndSaleDateBetween(product, start, end);
    }

    public String importCSV(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Long productId = Long.parseLong(data[0]);
                LocalDate date = LocalDate.parse(data[1]);
                int qty = Integer.parseInt(data[2]);

                Product product = productRepo.findById(productId).orElse(null);
                if (product != null) {
                    SalesRecord record = SalesRecord.builder()
                            .product(product)
                            .saleDate(date)
                            .quantitySold(qty)
                            .build();
                    salesRepo.save(record);
                }
            }
            return "CSV upload successful!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload CSV.";
        }
    }

    public void saveSalesFromCsv(MultipartFile file) throws IOException, IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String productName = parts[0].trim();
                    Optional<Product> productOpt = productRepo.findByName(productName);
                    if (productOpt.isEmpty()) continue; // skip if product not found
                    Product product = productOpt.get();
                    int quantity = Integer.parseInt(parts[1]);
                    LocalDate date = LocalDate.parse(parts[2]);

                    SalesRecord record = new SalesRecord();
                    record.setProduct(product);
                    record.setQuantitySold(quantity);
                    record.setSaleDate(date);
                    salesRepo.save(record);
                    System.out.println("✅ Saved: " + product.getName() + " | Qty: " + quantity + " | Date: " + date);

                }
            }
        }
    }
}

