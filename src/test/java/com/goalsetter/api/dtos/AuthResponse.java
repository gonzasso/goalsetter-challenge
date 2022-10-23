package com.goalsetter.api.dtos;

import lombok.Data;

@Data
public class AuthResponse {

    private String token;

    private String refreshToken;

    private String refreshTokenExpiration;
}
