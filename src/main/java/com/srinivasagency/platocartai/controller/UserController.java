package com.srinivasagency.platocartai.controller;

import com.srinivasagency.platocartai.model.User;
import com.srinivasagency.platocartai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.srinivasagency.platocartai.model.Address;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/me")
    public ResponseEntity<Optional<User>> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            Optional<User> userOptional = Optional.ofNullable(user);
            return ResponseEntity.ok(userOptional);
        }
        return ResponseEntity.ok(Optional.empty());
    }

    @GetMapping("/{userId}/addresses")
    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable("userId") String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get().getAddresses());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{userId}/addresses")
    public ResponseEntity<?> addUserAddress(@PathVariable("userId") String userId, @RequestBody Address address) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getAddresses() == null) {
                    user.setAddresses(new java.util.ArrayList<>());
                }
                // Ensure address has an ID
                if (address.getId() == null || address.getId().isEmpty()) {
                    address.setId(UUID.randomUUID().toString());
                }
                user.getAddresses().add(address);
                User savedUser = userRepository.save(user);
                return ResponseEntity.ok(savedUser);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving address: " + e.getMessage());
        }
    }

    @PutMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<Address> updateUserAddress(@PathVariable String userId, @PathVariable String addressId, @RequestBody Address updatedAddress) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Address> addresses = user.getAddresses();
            if (addresses != null) {
                for (int i = 0; i < addresses.size(); i++) {
                    Address address = addresses.get(i);
                    if (address.getId().equals(addressId)) {
                        addresses.set(i, updatedAddress);
                        user.setAddresses(addresses);
                        userRepository.save(user);
                        return ResponseEntity.ok(updatedAddress);
                    }
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody User user) {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already in use");
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRole("USER");
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error during signup: " + e.getMessage());
        }
    }
}
