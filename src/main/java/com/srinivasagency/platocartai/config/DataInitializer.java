package com.srinivasagency.platocartai.config;

import com.srinivasagency.platocartai.model.Product;
import com.srinivasagency.platocartai.model.User;
import com.srinivasagency.platocartai.model.Wishlist;
import com.srinivasagency.platocartai.repository.ProductRepository;
import com.srinivasagency.platocartai.repository.UserRepository;
import com.srinivasagency.platocartai.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize products
        if (productRepository.findAll().isEmpty()) {
            Product product1 = new Product();
            product1.setId("685ced1391f76d6e2ab56ef1");
            product1.setName("Product 1");
            product1.setDescription("Description for Product 1");
            product1.setPrice(19.99);
            product1.setImageUrl("https://example.com/product1.jpg");
            product1.setBulkImageUrl("https://example.com/product1_bulk.jpg");
            product1.setSize("10x10");
            product1.setFrom("China");
            product1.setMaterial("Cotton");
            product1.setBulkProductPrice(199.90);

            Product product2 = new Product();
            product2.setName("Product 2");
            product2.setDescription("Description for Product 2");
            product2.setPrice(29.99);
            product2.setImageUrl("https://example.com/product2.jpg");
            product2.setBulkImageUrl("https://example.com/product2_bulk.jpg");
            product2.setSize("12x12");
            product2.setFrom("USA");
            product2.setMaterial("Polyester");
            product2.setBulkProductPrice(299.90);

             Product product3 = new Product();
            product3.setName("Product 3");
            product3.setDescription("Description for Product 3");
            product3.setPrice(39.99);
            product3.setImageUrl("https://example.com/product3.jpg");
            product3.setBulkImageUrl("https://example.com/product3_bulk.jpg");
            product3.setSize("14x14");
            product3.setFrom("India");
            product3.setMaterial("Silk");
            product3.setBulkProductPrice(399.90);

            productRepository.saveAll(List.of(product1, product2,product3));
        }

        // Initialize admin user
        if (userRepository.findByEmail("Admin@gmail.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("Admin");
            adminUser.setEmail("Admin@gmail.com");
            adminUser.setPassword(bCryptPasswordEncoder.encode("Admin123"));
            adminUser.setRole("ADMIN");
            userRepository.save(adminUser);
        }

        // Initialize wishlist
        if (wishlistRepository.findAll().isEmpty() && !productRepository.findAll().isEmpty()) {
            Wishlist wishlist1 = new Wishlist();
            wishlist1.setUserId("1");
            wishlist1.setProductId("685ced1391f76d6e2ab56ef1");
            wishlistRepository.save(wishlist1);
        }
    }
}
