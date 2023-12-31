package com.diazrony.authcourse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    @JsonProperty(value = "token_type")
    private String tokeType;

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expires_in")
    private Integer expiresIn;

    @JsonProperty(value = "issue_at")
    private String issueAt;

    @JsonProperty(value = "client_id")
    private String clientId;
}
