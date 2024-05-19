package com.example.carsysfinalproject.model.core.validation;
import com.example.carsysfinalproject.model.core.dto.reservation.UserDto;
import com.example.carsysfinalproject.model.core.exception.InvalidInputException;

public class InputValidator {
    private UserDto userDto;

    public InputValidator(UserDto userDto) {
        this.userDto = userDto;
    }

    String namePattern = "^[a-zA-Z\\s]+$";
    String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    String passwordPattern = "^[a-zA-Z0-9!@#$%^&*()-_+=]{3,}$";
    String phoneNumberPattern = "^07\\d{8}$";
    String userRolePattern = "^(ADMIN|GUARD|STUDENT)$";

    public boolean validateFirstName(){
        if(userDto.getFirstName() == null || !userDto.getFirstName().matches(namePattern)){
            throw new InvalidInputException("Invalid name format");
        }
        return true;
    }

    public boolean validateLastName(){
        if(userDto.getLastName() == null || !userDto.getLastName().matches(namePattern)){
            throw new InvalidInputException("Invalid name format");
        }
        return true;
    }

    public boolean validateEmail(){
        if(userDto.getEmail() == null || !userDto.getEmail().matches(emailPattern)){
            throw new InvalidInputException("Invalid email format");
        }
        return true;
    }

    public boolean validatePassword(){
        if(userDto.getPassword() == null || !userDto.getPassword().matches(passwordPattern)){
            throw new InvalidInputException("Invalid password format");
        }
        return true;
    }

    public boolean validatePhoneNumber(){
        if(userDto.getPhoneNumber() == null || !userDto.getPhoneNumber().matches(phoneNumberPattern)){
            throw new InvalidInputException("Invalid phoneNumber format");
        }
        return true;
    }

    public boolean validateUserRole(){
        if(userDto.getUserRole() == null || !userDto.getUserRole().matches(userRolePattern)){
            throw new InvalidInputException("Invalid userRole format");
        }
        return true;
    }
}
