package com.inventory.forecasting_system.model;



import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class SalesRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Product product;

    private LocalDate saleDate;
    private Integer quantitySold;

    public  SalesRecord(){

    }

    public SalesRecord(Long id, Product product, LocalDate saleDate, Integer quantitySold) {
        this.id = id;
        this.product = product;
        this.saleDate = saleDate;
        this.quantitySold = quantitySold;
    }





    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }

    public Integer getQuantitySold() { return quantitySold; }
    public void setQuantitySold(Integer quantitySold) { this.quantitySold = quantitySold; }

    // --- Manual Builder ---
    public static class Builder {
        private Product product;
        private LocalDate saleDate;
        private Integer quantitySold;

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder saleDate(LocalDate saleDate) {
            this.saleDate = saleDate;
            return this;
        }

        public Builder quantitySold(Integer quantitySold) {
            this.quantitySold = quantitySold;
            return this;
        }

        public SalesRecord build() {
            SalesRecord record = new SalesRecord();
            record.setProduct(this.product);
            record.setSaleDate(this.saleDate);
            record.setQuantitySold(this.quantitySold);
            return record;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
