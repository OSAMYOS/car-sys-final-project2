package com.example.carsysfinalproject.model.core.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String userRole;
    private String carModel;
    private String carColor;
    private String carPlateNumber;
    private String faculty;
    private String userStatus;
}
