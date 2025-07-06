package com.srinivasagency.platocartai.repository;

import com.srinivasagency.platocartai.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<Cart, String> {
    List<Cart> findByUserId(String userId);
}
