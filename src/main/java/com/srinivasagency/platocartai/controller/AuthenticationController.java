package com.srinivasagency.platocartai.controller;

import com.srinivasagency.platocartai.model.AuthenticationRequest;
import com.srinivasagency.platocartai.model.AuthenticationResponse;
import com.srinivasagency.platocartai.service.MyUserDetailsService;
import com.srinivasagency.platocartai.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://srinivas-agency-frontend.vercel.app"})
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping("api/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        // Fetch userId from userDetailsService or user repository
        String userId = null;
        try {
            com.srinivasagency.platocartai.model.User user = userDetailsService.getUserByEmail(authenticationRequest.getEmail());
            if (user != null) {
                userId = user.getId();
            }
        } catch (Exception e) {
            // handle exception if needed
        }

        final String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();
        String simpleRole = role.startsWith("ROLE_") ? role.substring(5) : role;

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("userId", userId);
        response.put("role", simpleRole); // send USER or ADMIN
        return ResponseEntity.ok(response);
    }

}
