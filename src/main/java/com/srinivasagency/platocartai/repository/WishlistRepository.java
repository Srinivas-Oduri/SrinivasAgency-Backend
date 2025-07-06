package com.srinivasagency.platocartai.repository;

import com.srinivasagency.platocartai.model.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WishlistRepository extends MongoRepository<Wishlist, String> {
    List<Wishlist> findByUserId(String userId);
}
