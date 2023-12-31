package com.diazrony.authcourse.service;

import com.diazrony.authcourse.dto.AuthRequest;
import com.diazrony.authcourse.dto.AuthResponse;
import com.diazrony.authcourse.dto.JWTProperties;
import com.google.common.truth.Truth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JWTProperties properties;
    private final JWTManager jwtManager;

    public AuthService(JWTProperties properties, JWTManager jwtManager) {
        this.properties = properties;
        this.jwtManager = jwtManager;
    }

    public AuthResponse login(AuthRequest request) throws Exception {
        return AuthResponse.builder()
                .tokeType("bearer")
                .accessToken(jwtManager.generateToken(request))
                .clientId(request.getClientId())
                .expiresIn(properties.getToken().getExpiresIn())
                .issueAt(System.currentTimeMillis() + "")
                .build();
    }
}
