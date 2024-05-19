package com.example.carsysfinalproject.service;

import com.example.carsysfinalproject.model.core.dto.reservation.UserDto;
import com.example.carsysfinalproject.model.core.entity.reservation.User;
import com.example.carsysfinalproject.model.core.validation.InputValidator;
import com.example.carsysfinalproject.model.dao.UserDao;
import com.example.carsysfinalproject.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
public class UserService {
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRepository userRepository;

    public UserService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<UserDto> getAllUsers() {
        return userDao.getAllUsers();
    }

    public UserDto findUserById(int id) {
        return userDao.findUserById(id);
    }

    public UserDto findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }
    public List<UserDto> getAdminUsers() {
        return userDao.getAdminUsers();
    }

    public List<UserDto> getGuardUsers() {
        return userDao.getGuardUsers();
    }

    public List<UserDto> getStudentUsers() {
        return userDao.getStudentUsers();
    }

    public List<UserDto> getActiveUsers() {
        return userDao.getActiveUsers();
    }

    public List<UserDto> getDeletedUsers() {
        return userDao.getDeletedUsers();
    }

    public void save(UserDto userDto) {
        userDto.setFirstName(userDto.getFirstName().trim());
        userDto.setLastName(userDto.getLastName().trim());
        userDto.setEmail(userDto.getEmail().trim().toLowerCase());
        userDto.setPhoneNumber(userDto.getPhoneNumber().trim());

        InputValidator inputValidator = new InputValidator(userDto);
        Assert.isTrue(inputValidator.validateFirstName(), "Invalid first name");
        Assert.isTrue(inputValidator.validateLastName(), "Invalid last name");
        Assert.isTrue(inputValidator.validateEmail(), "Invalid email");
        Assert.isTrue(inputValidator.validatePassword(), "Invalid password");
        Assert.isTrue(inputValidator.validatePhoneNumber(), "Invalid phone number");
        Assert.isTrue(inputValidator.validateUserRole(), "Invalid user role");

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setUserStatus("ACTIVE");

        userDao.save(userDto);
    }

    public void update(UserDto userDto) {
        userDao.update(userDto);
    }

    public void delete(int id) {
        User user = userRepository.findById(id).get();
        if(nonNull(user))
            user.setUserStatus("DELETED");
        userRepository.save(user);
    }

    public void deleteAllUsers() {
        userDao.deleteAllUsers();
    }
}
