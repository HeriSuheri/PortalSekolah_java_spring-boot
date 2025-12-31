package com.example.portal.controller.classroom;

import com.example.portal.dto.classroom.ClassroomDTO;
import com.example.portal.dto.classroom.CreateClassroomRequest;
import com.example.portal.dto.classroom.UpdateClassroomRequest;
import com.example.portal.dto.admin.ApiResponse;
import com.example.portal.service.classroom.ClassroomService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CreateClassroomRequest request) {
        ClassroomDTO dto = classroomService.create(request);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil tambah classroom", dto));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id,
            @Valid @RequestBody UpdateClassroomRequest request) {
        ClassroomDTO dto = classroomService.update(id, request);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil update classroom", dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        classroomService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil hapus classroom", null));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        ClassroomDTO dto = classroomService.getById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil ambil classroom", dto));
    }

    // GET
    @GetMapping
    public ResponseEntity<ApiResponse> getClassrooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ClassroomDTO> response = classroomService.search(null, page, size);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil ambil data classroom", response));
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchClassrooms(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ClassroomDTO> response = classroomService.search(keyword, page, size);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil cari classroom", response));
    }
}