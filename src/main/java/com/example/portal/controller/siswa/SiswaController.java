package com.example.portal.controller.siswa;

import com.example.portal.dto.admin.ApiResponse;
import com.example.portal.dto.siswa.CreateSiswaRequest;
import com.example.portal.dto.siswa.UpdateSiswaRequest;
import com.example.portal.dto.siswa.SiswaDTO;
import com.example.portal.service.siswa.SiswaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/siswa")
public class SiswaController {

    private final SiswaService siswaService;

    public SiswaController(SiswaService siswaService) {
        this.siswaService = siswaService;
    }

    // CREATE siswa
    @PostMapping
    public ResponseEntity<SiswaDTO> create(@Valid @RequestBody CreateSiswaRequest request) {
        return ResponseEntity.ok(siswaService.create(request));
    }

    // UPDATE siswa
    @PutMapping("/{id}")
    public ResponseEntity<SiswaDTO> update(@PathVariable Long id,
            @Valid @RequestBody UpdateSiswaRequest request) {
        return ResponseEntity.ok(siswaService.update(id, request));
    }

    // DELETE siswa
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        siswaService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Siswa berhasil dihapus"));
    }

    // GET siswa by ID
    @GetMapping("/{id}")
    public ResponseEntity<SiswaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(siswaService.getById(id));
    }

    // GET semua siswa (non-paging, misalnya untuk dropdown)
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll() {
        List<SiswaDTO> data = siswaService.getAll();
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil ambil semua siswa", data));
    }

    // GET siswa dengan paging
    @GetMapping
    public ResponseEntity<ApiResponse> getSiswaPage(@RequestParam int page,
            @RequestParam int size) {
        Map<String, Object> data = siswaService.getSiswaPage(page, size);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil ambil data siswa", data));
    }

    // SEARCH siswa dengan keyword + paging
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> search(@RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size) {
        Map<String, Object> data = siswaService.search(keyword, page, size);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil cari siswa", data));
    }

    // GET siswa per classroom (untuk menu Akademik -> Kelas)
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<ApiResponse> getByClassroom(@PathVariable Long classroomId) {
        List<SiswaDTO> data = siswaService.getByClassroom(classroomId);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil ambil siswa per kelas", data));
    }
}