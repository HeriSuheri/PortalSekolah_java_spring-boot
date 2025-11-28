package com.example.portal.controller.guru;

import com.example.portal.dto.admin.ApiResponse;
import com.example.portal.dto.guru.*;
import com.example.portal.service.guru.GuruService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/guru")
public class GuruController {

    private final GuruService guruService;

    public GuruController(GuruService guruService) {
        this.guruService = guruService;
    }

    @PostMapping
    public ResponseEntity<GuruDTO> createGuru(@RequestBody CreateGuruRequest request) {
        return ResponseEntity.ok(guruService.createGuru(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuruDTO> updateGuru(
            @PathVariable Long id,
            @RequestBody UpdateGuruRequest request,
            Authentication auth) {
        return ResponseEntity.ok(guruService.updateGuru(id, request, auth));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteGuru(@PathVariable Long id) {
        guruService.deleteGuru(id);
        return ResponseEntity.ok(Map.of("message", "Guru berhasil dihapus"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuruDTO> getGuruById(@PathVariable Long id) {
        return ResponseEntity.ok(guruService.getGuruById(id));
    }

    @GetMapping
    public ResponseEntity<?> getGurus(
            @RequestParam int page,
            @RequestParam int size) {
        Map<String, Object> response = guruService.getGurus(page, size);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil ambil data guru", response));
    }

    // search guru
    @GetMapping("/search")
    public ResponseEntity<?> searchGurus(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size) {
        Map<String, Object> response = guruService.searchGurus(keyword, page, size);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil cari admin", response));
    }

}