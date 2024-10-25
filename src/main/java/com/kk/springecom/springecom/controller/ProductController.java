package com.kk.springecom.springecom.controller;

import com.kk.springecom.springecom.model.Product;
import com.kk.springecom.springecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        Product savedProduct = null;
        try {
            savedProduct = productService.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/addTestProducts")
    public ResponseEntity<?> addTestProducts() {
        try {
            {
                List<Product> productsAdded = productService.addTestProducts();
                return new ResponseEntity<>(productsAdded, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            Optional<Product> productById = productService.getProductById(id);
            if (productById.isPresent()) {
//                product.setId(productId);
                return new ResponseEntity<>(productService.addOrUpdateProduct(product, imageFile), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
        try {
            Optional<Product> productById = productService.getProductById(id);
            if (productById.isPresent()) {
                productService.deleteProduct(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(
            value = "product/{productId}/image",
            produces = MediaType.IMAGE_JPEG_VALUE // to send response in this format
//            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
        Optional<Product> productById = productService.getProductById(productId);
        return productById.map(product -> new ResponseEntity<>(product.getImageData(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        System.out.println("Searching with keyword: " + keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


}