package com.kk.springecom.springecom.service;

import com.kk.springecom.springecom.model.Product;
import com.kk.springecom.springecom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getName());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return productRepo.save(product);
    }
}
