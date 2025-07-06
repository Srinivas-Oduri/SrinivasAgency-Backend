package com.srinivasagency.platocartai.controller;

import com.srinivasagency.platocartai.model.Product;
import com.srinivasagency.platocartai.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:3000", "https://srinivas-agency-frontend.vercel.app"})
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        product.setId(id);
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setImageUrl(product.getImageUrl());
            existingProduct.setBulkImageUrl(product.getBulkImageUrl());
            existingProduct.setSize(product.getSize());
            existingProduct.setFrom(product.getFrom());
            existingProduct.setMaterial(product.getMaterial());
            existingProduct.setBulkProductPrice(product.getBulkProductPrice());
            return productRepository.save(existingProduct);
        } else {
            return product;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
    }
}
