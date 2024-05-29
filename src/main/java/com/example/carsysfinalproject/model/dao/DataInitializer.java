package com.example.carsysfinalproject.model.dao;

import com.example.carsysfinalproject.model.core.dto.reservation.UserDto;
import com.example.carsysfinalproject.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        if (userService.getAllUsers().isEmpty()) {
            UserDto userDto = new UserDto();
            userDto.setId(1);
            userDto.setFirstName("Osama");
            userDto.setLastName("Abdallah");
            userDto.setEmail("admin@ju.edu.jo");
            userDto.setPassword("12345678");
            userDto.setPhoneNumber("0778926680");
            userDto.setUserRole("ADMIN");
            userDto.setUserStatus("ACTIVE");
            userService.save(userDto);
        }
    }
}