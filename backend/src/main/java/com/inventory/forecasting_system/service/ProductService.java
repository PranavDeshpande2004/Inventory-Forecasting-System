package com.inventory.forecasting_system.service;

import com.inventory.forecasting_system.model.Product;
import com.inventory.forecasting_system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepo;

    public List<Product>getAllProduct(){
        return productRepo.findAll();
    }

    public Product addProduct(Product product){
        return productRepo.save(product);
    }

    public Product getProductById(Long id){
        return  productRepo.findById(id).orElse(null);
    }

    public void deleteproduct(Long id){
        productRepo.deleteById(id);
    }

}
