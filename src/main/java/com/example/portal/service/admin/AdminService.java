package com.example.portal.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.example.portal.dto.UserResponseDTO;
import com.example.portal.dto.admin.CreateAdminRequest;
import com.example.portal.dto.admin.UpdateAdminRequest;
import com.example.portal.model.User;

public interface AdminService {

    User createAdmin(CreateAdminRequest request);

    List<UserResponseDTO> getAllAdmins();

    Map<String, Object> getAdmins(int page, int size);

    void updateAdmin(Long id, UpdateAdminRequest request);

    void deleteAdmin(Long id);

    // search admin
    Map<String, Object> searchAdmins(String keyword, int page, int size);

}