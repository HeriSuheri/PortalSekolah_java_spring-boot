package com.example.portal.controller.admin;

import com.example.portal.dto.UserResponseDTO;
import com.example.portal.dto.admin.ApiResponse;
import com.example.portal.dto.admin.CreateAdminRequest;
import com.example.portal.dto.admin.UpdateAdminRequest;
import com.example.portal.exception.ResourceNotFoundException;
// import com.example.portal.mapper.UserMapper;
import com.example.portal.model.Role;
import com.example.portal.model.User;
import com.example.portal.repository.UserRepository;
import com.example.portal.service.admin.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;
    // private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<User> createAdmin(@RequestBody @Valid CreateAdminRequest request) {
        User created = adminService.createAdmin(request);
        return ResponseEntity.ok(created);
    }

    // tanpa pagination
    // @GetMapping
    // public ResponseEntity<?> getAllAdmins() {
    // List<UserResponseDTO> admins = adminService.getAllAdmins();
    // return ResponseEntity.ok(Map.of(
    // "admins", admins,
    // "count", admins.size()));
    // }

    // dengan pagination tanpa mapper
    // @GetMapping
    // public ResponseEntity<?> getAdmins(@RequestParam int page, @RequestParam int
    // size) {
    // Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
    // Page<User> admins = userRepository.findByRole(Role.ADMIN, pageable);

    // Map<String, Object> response = new HashMap<>();
    // response.put("content", admins.getContent());
    // response.put("totalElements", admins.getTotalElements());
    // response.put("totalPages", admins.getTotalPages());
    // response.put("currentPage", admins.getNumber());

    // return ResponseEntity.ok(new ApiResponse(true, "Berhasil ambil data",
    // response));
    // }

    // get admin dengan pagination dengan mapper
    // 1
    // @GetMapping
    // public ResponseEntity<?> getAdmins(
    // @RequestParam int page,
    // @RequestParam int size) {
    // Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
    // Page<User> admins = userRepository.findByRole(Role.ADMIN, pageable);

    // List<UserResponseDTO> dtoList = admins.getContent().stream()
    // .map(UserMapper::toDTO)
    // .toList();

    // Map<String, Object> response = new HashMap<>();
    // response.put("content", dtoList);
    // response.put("totalElements", admins.getTotalElements());
    // response.put("totalPages", admins.getTotalPages());
    // response.put("currentPage", admins.getNumber());

    // return ResponseEntity.ok(new ApiResponse(true, "Berhasil ambil data",
    // response));
    // }

    // get admin dengan paginasi dan mapper
    // 2
    @GetMapping
    public ResponseEntity<?> getAdmins(
            @RequestParam int page,
            @RequestParam int size) {
        Map<String, Object> response = adminService.getAdmins(page, size);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil ambil data", response));
    }

    // search admin
    @GetMapping("/search")
    public ResponseEntity<?> searchAdmins(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size) {
        Map<String, Object> response = adminService.searchAdmins(keyword, page, size);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil cari admin", response));
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<?> updateAdmin(@PathVariable Long id, @RequestBody
    // UpdateAdminRequest request) {
    // public ResponseEntity<?> updateAdmin(@PathVariable Long id, @RequestBody
    // @Valid UpdateAdminRequest request) {
    // try {
    // adminService.updateAdmin(id, request);
    // return ResponseEntity.ok(new ApiResponse(true, "Admin berhasil diperbarui"));
    // } catch (ResourceNotFoundException e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
    // ApiResponse(false, e.getMessage()));
    // } catch (Exception e) {
    // log.error("Gagal update admin", e);
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(new ApiResponse(false, "Gagal update admin"));
    // }
    // }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Long id, @RequestBody @Valid UpdateAdminRequest request) {
        adminService.updateAdmin(id, request);
        return ResponseEntity.ok(new ApiResponse(true, "Admin berhasil diperbarui"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.ok(new ApiResponse(true, "Admin berhasil dihapus"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            log.error("Gagal hapus admin", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Gagal hapus admin"));
        }
    }

}