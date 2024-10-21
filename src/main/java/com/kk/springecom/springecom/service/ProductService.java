package com.kk.springecom.springecom.service;

import com.kk.springecom.springecom.model.Product;
import com.kk.springecom.springecom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Optional<Product> getProductById(int productId) {
        return productRepo.findById(productId);
    }
}
