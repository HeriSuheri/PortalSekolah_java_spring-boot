package com.example.portal.service;

import com.example.portal.dto.RegisterUserRequest;
import com.example.portal.dto.UserResponseDTO;
import com.example.portal.mapper.UserMapper;
import com.example.portal.model.User;
import com.example.portal.model.Role;
import com.example.portal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // @Override
    // public UserResponseDTO createUser(RegisterUserRequest request) {
    // if (userRepository.existsByUsername(request.getUsername())) {
    // throw new RuntimeException("Username already exists");
    // }

    // User user = UserMapper.toEntity(request);
    // // TODO: hash password di sini
    // userRepository.save(user);
    // return UserMapper.toDTO(user);
    // }


    // BELUM DIPAKE
    @Override
    public UserResponseDTO createUser(RegisterUserRequest request) {
        if (userRepository.existsByNomorInduk(request.getNomorInduk())) {
            throw new RuntimeException("Nomor Induk sudah terdaftar");
        }

        User user = UserMapper.toEntity(request);
        // TODO: hash password di sini
        userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers(Role role) {
        List<User> users = (role != null) ? userRepository.findByRole(role) : userRepository.findAll();
        return users.stream().map(UserMapper::toDTO).toList();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDTO(user);
    }
}