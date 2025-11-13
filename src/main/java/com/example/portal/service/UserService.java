package com.example.portal.service;

import com.example.portal.dto.RegisterUserRequest;
import com.example.portal.dto.UserResponseDTO;
import com.example.portal.model.Role;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(RegisterUserRequest request);

    List<UserResponseDTO> getAllUsers(Role role);

    UserResponseDTO getUserById(Long id);
}