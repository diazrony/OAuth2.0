package com.diazrony.authcourse.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "diazrony.jwt")
public class JWTProperties {

    private Security security;
    private String timezone;
    private String issuer;
    private Token token;
    private Excluded excluded;
    @Data
    public static class Security {
        private boolean enabled;
    }
    @Data
    public static class Token {
        private Auth auth;
        private String secret;
        private Integer expiresIn;

        @Data
        public static class Auth {
            private String path;
        }
    }

    @Data
    public static class Excluded {
        private String path;
    }
}
