package com.github.mkapiczy.oauth_server.entity.dto;

public class TokenResponse {
    public String accessToken;
    public String refreshToken;

    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
