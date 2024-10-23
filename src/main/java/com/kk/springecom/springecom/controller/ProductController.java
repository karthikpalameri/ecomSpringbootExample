package com.kk.springecom.springecom.controller;

import com.kk.springecom.springecom.model.Product;
import com.kk.springecom.springecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
/*
    CORS related annotations for control access.
 */
//@CrossOrigin(
//        origins = "http://localhost:3000", // Allow requests only from this origin (your front-end application)
//        allowedMethods = {"GET", "POST"}, // Specify which HTTP methods are allowed for CORS requests
//        allowedHeaders = {"Content-Type", "Authorization"}, // Specify which headers are allowed in requests
//        exposedHeaders = {"Custom-Header"}, // Specify which headers can be exposed to the client in the response
//        allowCredentials = "true", // Indicates whether or not to allow credentials (like cookies) to be sent with requests
//        maxAge = 3600 // Cache preflight response for 1 hour (3600 seconds) to reduce the number of preflight requests
//)
@CrossOrigin // Allow all origins
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") int productId) {
        Optional<Product> product = productService.getProductById(productId);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile image) {
        Product savedProduct = null;
        try {
            savedProduct = productService.addProduct(product, image);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
