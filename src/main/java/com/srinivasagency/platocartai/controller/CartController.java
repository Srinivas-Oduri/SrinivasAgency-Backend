package com.srinivasagency.platocartai.controller;

import com.srinivasagency.platocartai.model.Cart;
import com.srinivasagency.platocartai.model.Product;
import com.srinivasagency.platocartai.repository.CartRepository;
import com.srinivasagency.platocartai.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.srinivasagency.platocartai.service.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://srinivas-agency-frontend.vercel.app"})
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCartByUserId(@PathVariable("userId") String userId) {
        logger.info("getCartByUserId called for userId: {}", userId);
        List<Cart> carts = cartRepository.findByUserId(userId);
        logger.info("Found {} carts for userId: {}", carts.size(), userId);
        List<CartItem> response = new ArrayList<>();

        for (Cart cart : carts) {
            logger.info("Processing cart with id: {} and productIds: {}", cart.getId(), cart.getProductIds());
            if (cart.getProductIds() != null) {
                for (String productId : cart.getProductIds()) {
                    logger.info("Fetching product with id: {}", productId);
                    Optional<Product> productOptional = productService.getProductById(productId);
                    Product product = productOptional.orElse(null);
                    if (product != null) {
                        logger.info("Found product with id: {}", productId);
                        response.add(new CartItem(cart.getId(), product));
                    } else {
                        logger.warn("Product not found for productId: {}", productId);
                    }
                }
            }
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Cart> addToCart(@RequestBody Cart cart) {
        if (cart.getUserId() == null || cart.getUserId().equals("undefined")) {
            return ResponseEntity.badRequest().build();
        }
        if (cart.getProductIds() == null || cart.getProductIds().isEmpty()) {
            return ResponseEntity.badRequest().build(); // Don't allow empty productIds
        }

        // Find existing cart for user
        List<Cart> existingCarts = cartRepository.findByUserId(cart.getUserId());
        Cart userCart;
        if (!existingCarts.isEmpty()) {
            userCart = existingCarts.get(0);
            if (userCart.getProductIds() == null) {
                userCart.setProductIds(new ArrayList<>());
            }
            // Add new productIds if not already present and not null
            for (String productId : cart.getProductIds()) {
                if (productId != null && !userCart.getProductIds().contains(productId)) {
                    userCart.getProductIds().add(productId);
                }
            }
        } else {
            userCart = new Cart();
            userCart.setUserId(cart.getUserId());
            // Filter out null productIds
            List<String> validProductIds = cart.getProductIds().stream().filter(id -> id != null).toList();
            userCart.setProductIds(new ArrayList<>(validProductIds));
            userCart.setBulkProduct(cart.isBulkProduct()); // Set the isBulkProduct flag
        }

        Cart savedCart = cartRepository.save(userCart);
        return ResponseEntity.ok(savedCart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable String id, @RequestBody Cart cart) {
        cart.setId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Get the email from the authentication object
        com.srinivasagency.platocartai.model.User user = userDetailsService.getUserByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.badRequest().body(null); // Or throw an exception
        }

        if (!user.getId().equals(cart.getUserId())) {
            return ResponseEntity.status(403).build(); // Forbidden if user IDs don't match
        }
        Cart savedCart = cartRepository.save(cart);
        return ResponseEntity.ok(savedCart);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable("id") String id) {
        cartRepository.deleteById(id);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<?> removeProductFromCart(
        @PathVariable("userId") String userId,
        @PathVariable("productId") String productId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        if (!carts.isEmpty()) {
            Cart cart = carts.get(0);
            if (cart.getProductIds() != null && cart.getProductIds().contains(productId)) {
                cart.getProductIds().remove(productId);
                cartRepository.save(cart);
            }
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/removeNullProducts/{userId}")
    public ResponseEntity<Void> removeNullProductsFromCart(@PathVariable String userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        if (!carts.isEmpty()) {
            Cart cart = carts.get(0);
            if (cart.getProductIds() != null) {
                cart.getProductIds().removeIf(productId -> productId == null);
                cartRepository.save(cart);
            }
        }
        return ResponseEntity.ok().build();
    }

    public static class CartItem {
        private String cartId;
        private Product product;

        public CartItem(String cartId, Product product) {
            this.cartId = cartId;
            this.product = product;
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }
}
