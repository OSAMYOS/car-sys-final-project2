package com.example.carsysfinalproject.model.dao;

import com.example.carsysfinalproject.model.core.dto.reservation.UserDto;
import com.example.carsysfinalproject.model.core.entity.reservation.User;
import com.example.carsysfinalproject.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDto findUserById(int id) {
        User user = userRepository.findById(id).get();
        return nonNull(user) ? toDto(user) : null;
    }

    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return nonNull(user) ? toDto(user) : null;
    }

    public List<UserDto> getAdminUsers() {
        return userRepository.findAllByAdminRole("ADMIN")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getGuardUsers() {
        return userRepository.findAllByGuardRole("GUARD")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getStudentUsers() {
        return userRepository.findAllByStudentRole("STUDENT")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getActiveUsers() {
        return userRepository.findAllActiveUsers("ACTIVE")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getDeletedUsers() {
        return userRepository.findAllDeletedUsers("DELETED")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void save(UserDto userDto) {
        User user = toUserEntity(userDto);
        userRepository.save(user);
    }

    public void update(UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.getId());
        if (user.isPresent()) {
            User existingUser = user.get();
            if (nonNull(userDto.getFirstName())) {
                existingUser.setFirstName(userDto.getFirstName());
            }
            if (nonNull(userDto.getLastName())) {
                existingUser.setLastName(userDto.getLastName());
            }
            if (nonNull(userDto.getPhoneNumber())) {
                existingUser.setPhoneNumber(userDto.getPhoneNumber());
            }
            if (nonNull(userDto.getCarModel())) {
                existingUser.setCarModel(userDto.getCarModel());
            }
            if (nonNull(userDto.getCarColor())) {
                existingUser.setCarColor(userDto.getCarColor());
            }
            if (nonNull(userDto.getCarPlateNumber())) {
                existingUser.setCarPlateNumber(userDto.getCarPlateNumber());
            }
            if (nonNull(userDto.getFaculty())) {
                existingUser.setFaculty(userDto.getFaculty());
            }
            userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userDto.getId());
        }
    }

    public void delete(int id) {
        userRepository.deleteByUserId(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    private User toUserEntity(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUserRole(userDto.getUserRole());
        user.setCarModel(userDto.getCarModel());
        user.setCarColor(userDto.getCarColor());
        user.setCarPlateNumber(userDto.getCarPlateNumber());
        user.setFaculty(userDto.getFaculty());
        user.setUserStatus(userDto.getUserStatus());
        return user;
    }

    private UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setUserRole(user.getUserRole());
        userDto.setCarModel(user.getCarModel());
        userDto.setCarColor(user.getCarColor());
        userDto.setCarPlateNumber(user.getCarPlateNumber());
        userDto.setFaculty(user.getFaculty());
        userDto.setUserStatus(user.getUserStatus());
        return userDto;
    }
}
