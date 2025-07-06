package com.srinivasagency.platocartai.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private final String userId;

    public AuthenticationResponse(String jwt, String userId) {
        this.jwt = jwt;
        this.userId = userId;
    }

    public String getJwt() {
        return jwt;
    }

    public String getUserId() {
        return userId;
    }
}
