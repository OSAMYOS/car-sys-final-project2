package com.example.carsysfinalproject.controller;


import com.example.carsysfinalproject.model.core.dto.auth.AuthRequestDTO;
import com.example.carsysfinalproject.model.core.dto.auth.AuthResponseDTO;
import com.example.carsysfinalproject.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/api/login")
    public AuthResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return new AuthResponseDTO(jwtService.GenerateToken(authRequestDTO.getEmail()),authRequestDTO.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
}
