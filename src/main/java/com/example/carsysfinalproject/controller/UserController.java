package com.example.carsysfinalproject.controller;

import com.example.carsysfinalproject.model.core.dto.reservation.UserDto;
import com.example.carsysfinalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public void saveUser(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/get")
    public UserDto findUserById(@RequestParam("id") int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be less than or equal to 0");
        } else {
            return userService.findUserById(id);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GUARD') or hasAuthority('STUDENT')")
    @PostMapping("/getByEmail")
    public UserDto findUserByEmail(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        } else {
            return userService.findUserByEmail(email);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAdminUsers")
    public List<UserDto> getAdminUsers() {
        return userService.getAdminUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getGuardUsers")
    public List<UserDto> getGuardUsers() {
        return userService.getGuardUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getStudentUsers")
    public List<UserDto> getStudentUsers() {
        return userService.getStudentUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getActiveUsers")
    public List<UserDto> getActiveUsers() {
        return userService.getActiveUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getDeletedUsers")
    public List<UserDto> getDeletedUsers() {
        return userService.getDeletedUsers();
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/update")
    public void updateUser(@RequestBody UserDto user) {
        userService.update(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id cannot be less than or equal to 0");
        } else {
            userService.delete(id);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteAll")
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

}
