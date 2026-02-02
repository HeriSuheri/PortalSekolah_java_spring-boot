
package com.example.portal.mapper;

import java.time.LocalDate;

import com.example.portal.dto.RegisterUserRequest;
import com.example.portal.dto.UserResponseDTO;
import com.example.portal.dto.admin.CreateAdminRequest;
import com.example.portal.model.Role;
import com.example.portal.model.User;

public class UserMapper {

    // BELUM DIPAKE
    public static User toEntity(RegisterUserRequest dto) {
        User user = new User();
        user.setPassword(dto.getPassword()); // hash nanti
        user.setNomorInduk(dto.getNomorInduk());
        user.setRole(dto.getRole());
        // user.setNama(dto.getNama());
        user.setTanggalLahir(dto.getTanggalLahir());
        return user;
    }

    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setNomorInduk(user.getNomorInduk());
        dto.setRole(user.getRole());
        dto.setNama(user.getNama()); // ⬅ ini penting
        dto.setTanggalLahir(user.getTanggalLahir());
        dto.setFotoUrl(user.getFotoUrl());
        dto.setEmail(user.getEmail());
        dto.setIsActive(user.getIsActive());
        return dto;
    }

     public static User fromCreateAdminRequest(CreateAdminRequest req) {
        User user = new User();
        user.setNomorInduk(req.getNomorInduk());
        user.setNama(req.getNama());
        user.setEmail(req.getEmail());
        user.setTanggalLahir(LocalDate.parse(req.getTanggalLahir()));
        user.setIsActive(req.getIsActive());
        user.setRole(Role.ADMIN);
        user.setPassword(null); // login awal pakai tanggal lahir
        return user;
    }

}