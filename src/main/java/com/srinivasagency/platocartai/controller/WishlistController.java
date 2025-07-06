package com.srinivasagency.platocartai.controller;

import com.srinivasagency.platocartai.model.Wishlist;
import com.srinivasagency.platocartai.model.Product;
import com.srinivasagency.platocartai.repository.WishlistRepository;
import com.srinivasagency.platocartai.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://srinivas-agency-frontend.vercel.app"})
@RequestMapping("/api/wishlists")
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getWishlistByUserId(@PathVariable("userId") String userId) {
        List<Wishlist> wishlists = wishlistRepository.findByUserId(userId);
        List<Map<String, Object>> response = new ArrayList<>();

        for (Wishlist wishlist : wishlists) {
            Product product = productService.getProductById(wishlist.getProductId()).orElse(null);
            if (product != null) {
                response.add(Map.of(
                    "wishlistId", wishlist.getId(),
                    "productId", product.getId(),
                    "product", product
                ));
            }
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Wishlist> addToWishlist(@RequestBody Wishlist wishlist) {
        if (wishlist.getUserId() == null || wishlist.getUserId().equals("undefined")) {
            return ResponseEntity.badRequest().build();
        }
        if (wishlist.getProductId() == null || wishlist.getProductId().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Prevent duplicate wishlist entries for the same user and product
        List<Wishlist> existing = wishlistRepository.findByUserId(wishlist.getUserId());
        for (Wishlist w : existing) {
            if (w.getProductId().equals(wishlist.getProductId())) {
                // Update price if different
                if (wishlist.getPrice() != null && !wishlist.getPrice().equals(w.getPrice())) {
                    w.setPrice(wishlist.getPrice());
                }
                if (wishlist.isBulkProduct() != w.isBulkProduct()) {
                    w.setBulkProduct(wishlist.isBulkProduct());
                }
                wishlistRepository.save(w);
                return ResponseEntity.ok(w); // Already exists, return existing
            }
        }

        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return ResponseEntity.ok(savedWishlist);
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<?> deleteWishlist(@PathVariable("wishlistId") String wishlistId) {
        wishlistRepository.deleteById(wishlistId);
        return ResponseEntity.ok().build();
    }
}
