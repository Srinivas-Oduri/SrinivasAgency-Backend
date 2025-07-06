package com.srinivasagency.platocartai.repository;

import com.srinivasagency.platocartai.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
