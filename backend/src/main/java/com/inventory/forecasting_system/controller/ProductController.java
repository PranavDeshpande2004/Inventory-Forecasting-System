package com.inventory.forecasting_system.controller;

import com.inventory.forecasting_system.model.Product;
import com.inventory.forecasting_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
    @Autowired
    public ProductService productService;
    @GetMapping
    public List<Product> getAll(){
        return productService.getAllProduct();
    }
    @PostMapping
    public Product add(@RequestBody Product product){
        return productService.addProduct(product);
    }
    @GetMapping("/{id}")
    public Product getByID(@PathVariable Long id){
        return  productService.getProductById(id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        productService.deleteproduct(id);
    }


}
