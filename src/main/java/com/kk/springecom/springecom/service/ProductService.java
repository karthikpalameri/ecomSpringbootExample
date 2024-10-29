package com.kk.springecom.springecom.service;

import com.kk.springecom.springecom.model.Product;
import com.kk.springecom.springecom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
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

    @Transactional
    public Product addOrUpdateProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getName());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return productRepo.save(product);
    }

    public List<Product> addTestProducts() {
        // Create 5 random Product objects
        Product p1 = new Product(0, "Laptop", "High-end gaming laptop", "Dell",
                new BigDecimal("1500.00"), "Electronics",
                new Date(), true, 10,
                "laptop.png", "image/png", new byte[0]);

        Product p2 = new Product(0, "Smartphone", "Latest model smartphone", "Apple",
                new BigDecimal("1200.00"), "Mobile",
                new Date(), true, 50,
                "iphone.png", "image/png", new byte[0]);

        Product p3 = new Product(0, "Headphones", "Noise-cancelling headphones", "Sony",
                new BigDecimal("300.00"), "Accessories",
                new Date(), true, 100,
                "headphones.png", "image/png", new byte[0]);

        Product p4 = new Product(0, "Tablet", "Portable tablet", "Samsung",
                new BigDecimal("500.00"), "Electronics",
                new Date(), true, 25,
                "tablet.png", "image/png", new byte[0]);

        Product p5 = new Product(0, "Smartwatch", "Advanced fitness tracker", "Fitbit",
                new BigDecimal("250.00"), "Wearables",
                new Date(), true, 75,
                "smartwatch.png", "image/png", new byte[0]);
        List<Product> demoAddProductList = Arrays.asList(p1, p2, p3, p4, p5);
        List<Product> products = productRepo.saveAll(demoAddProductList);
        System.out.println("5 Products saved to the database");

        return products;
    }

    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }

//    public Product updateProduct(Product product, MultipartFile image) throws IOException {
//        product.setImageName(image.getName());
//        product.setImageType(image.getContentType());
//        product.setImageData(image.getBytes());
//        return productRepo.save(product);
//    }
}