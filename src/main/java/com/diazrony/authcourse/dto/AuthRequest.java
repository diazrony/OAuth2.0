package com.diazrony.authcourse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {
    @JsonProperty(value = "client_id")
    private String clientId;

    @JsonProperty(value = "clien_secret")
    private String clientSecret;
}
