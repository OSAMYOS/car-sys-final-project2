package com.example.carsysfinalproject.model.core.dto.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthResponseDTO implements Serializable {
    private final String jwtToken;
    private final String email;
}
