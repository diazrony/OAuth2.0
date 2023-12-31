package com.diazrony.authcourse.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.diazrony.authcourse.dto.AuthRequest;
import com.diazrony.authcourse.dto.JWTProperties;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.TimeZone;

@Component
public class JWTManager {
    private final JWTProperties properties;
    private static final String PRIVATE_KEY_PEM = "private_key.pem";
    private static final String PUBLIC_KEY_PEM = "public_key.pem";

    public JWTManager(JWTProperties properties) {
        this.properties = properties;
    }

    public String generateToken(AuthRequest request) throws Exception {
        Algorithm algorithm = Algorithm.RSA256(readRSAPublicKey(), readRSAPrivateKey());
        Instant now = Instant.now().atZone(TimeZone.getTimeZone(properties.getTimezone()).toZoneId()).toInstant();

        return JWT.create()
                .withIssuer(properties.getIssuer())
                .withSubject(request.getClientId())
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(properties.getToken().getExpiresIn()))
                .sign(algorithm);
    }

    private void verifyToken(String token) throws Exception {
        Algorithm algorithm = Algorithm.RSA256(readRSAPublicKey(), readRSAPrivateKey());
        JWT.require(algorithm)
                .withIssuer(properties.getIssuer())
                .build()
                .verify(token);
    }

    private static RSAPrivateKey readRSAPrivateKey() throws Exception {
        Resource resource = new ClassPathResource(PRIVATE_KEY_PEM);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            StringBuilder keyPEM = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("-----BEGIN PRIVATE KEY-----") || line.startsWith("-----END PRIVATE KEY-----")) {
                    continue;
                }
                keyPEM.append(line);
            }

            byte[] encoded = Base64.decodeBase64(keyPEM.toString());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            return (RSAPrivateKey) privateKey;
        }
    }

    private static RSAPublicKey readRSAPublicKey() throws Exception {
        Resource resource = new ClassPathResource(PUBLIC_KEY_PEM);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            StringBuilder keyPEM = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("-----BEGIN PUBLIC KEY-----") || line.startsWith("-----END PUBLIC KEY-----")) {
                    continue;
                }
                keyPEM.append(line);
            }

            byte[] encoded = Base64.decodeBase64(keyPEM.toString());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            return (RSAPublicKey) publicKey;
        }
    }

}
