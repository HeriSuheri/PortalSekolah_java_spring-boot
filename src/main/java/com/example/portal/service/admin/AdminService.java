package com.example.portal.service.admin;

import java.util.List;

import com.example.portal.dto.UserResponseDTO;
import com.example.portal.dto.admin.CreateAdminRequest;
import com.example.portal.dto.admin.UpdateAdminRequest;
import com.example.portal.model.User;

public interface AdminService {

    User createAdmin(CreateAdminRequest request);

    List<UserResponseDTO> getAllAdmins();

    void updateAdmin(Long id, UpdateAdminRequest request);

    void deleteAdmin(Long id);


}