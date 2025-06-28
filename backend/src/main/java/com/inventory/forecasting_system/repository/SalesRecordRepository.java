package com.inventory.forecasting_system.repository;

import com.inventory.forecasting_system.model.Product;
import com.inventory.forecasting_system.model.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesRecordRepository extends JpaRepository<SalesRecord,Long> {
    List<SalesRecord>findByProductAndSaleDateBetween(Product product, LocalDate start,LocalDate end);
    List<SalesRecord> findByProductNameIgnoreCase(String productName);
}
