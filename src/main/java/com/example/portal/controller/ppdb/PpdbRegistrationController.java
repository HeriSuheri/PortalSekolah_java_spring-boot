package com.example.portal.controller.ppdb;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.portal.service.ppdb.PpdbRegistrationService;
import com.example.portal.dto.admin.ApiResponse;
import com.example.portal.dto.ppdb.CreatePpdbRegistrationRequest;
import com.example.portal.dto.ppdb.PpdbRegistrationResponse;
import com.example.portal.dto.ppdb.UpdatePpdbRegistrationRequest;
import com.example.portal.repository.ppdb.PpdbRegistrationRepository;

@RestController
@RequestMapping("/api/ppdb")
public class PpdbRegistrationController {

    private final PpdbRegistrationService service;
    private final PpdbRegistrationRepository ppdbRegistrationRepository;

    public PpdbRegistrationController(PpdbRegistrationService service,
            PpdbRegistrationRepository ppdbRegistrationRepository) {
        this.service = service;
        this.ppdbRegistrationRepository = ppdbRegistrationRepository;
    }

    // via web
    @PostMapping("/register")
    public PpdbRegistrationResponse register(@RequestBody CreatePpdbRegistrationRequest request) {
        return service.register(request);
    }

    // by admin sekolah
    @PostMapping("/register-by-admin")
    public PpdbRegistrationResponse registerByAdmin(@RequestBody CreatePpdbRegistrationRequest request) {
        return service.registerByAdmin(request);
    }

    @GetMapping("/{noPendaftaran}")
    public PpdbRegistrationResponse findByNoPendaftaran(@PathVariable String noPendaftaran) {
        return service.findByNoPendaftaran(noPendaftaran);
    }
    // @GetMapping("/{noPendaftaran}")
    // public ResponseEntity<?> findByNoPendaftaran(@PathVariable String
    // noPendaftaran) {
    // boolean exists =
    // ppdbRegistrationRepository.existsByNoPendaftaran(noPendaftaran);

    // if (exists) {
    // return ResponseEntity.ok(new ApiResponse(true, "Data sudah terdaftar",
    // null));
    // } else {
    // return ResponseEntity.ok(service.findByNoPendaftaran(noPendaftaran));
    // }
    // }

    // GET ppdb dengan paging
    @GetMapping
    public ResponseEntity<ApiResponse> getPpdbPage(@RequestParam int page,
            @RequestParam int size) {
        Map<String, Object> data = service.getPpdbPage(page, size);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil ambil data calon siswa", data));
    }

    // SEARCH siswa dengan keyword + paging
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> search(@RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size) {
        Map<String, Object> data = service.search(keyword, page, size);
        return ResponseEntity.ok(new ApiResponse(true, "Berhasil cari calon siswa",
                data));
        // List<?> items = (List<?>) data.get("items");

        // if (items == null || items.isEmpty()) {
        // return ResponseEntity.ok(new ApiResponse(false, "Data tidak ditemukan",
        // data));
        // } else {
        // return ResponseEntity.ok(new ApiResponse(true, "Berhasil cari calon siswa",
        // data));
        // }

    }

    // @PutMapping("/{id}/update-status")
    // public PpdbRegistrationResponse updateStatus(
    // @PathVariable Long id,
    // @RequestParam String status,
    // @RequestParam String statusPembayaran,
    // @RequestParam(required = false) String catatanValidasi) {
    // return service.updateStatus(id, status, statusPembayaran, catatanValidasi);
    // }
    @PutMapping("/{id}/update-status")
    public PpdbRegistrationResponse updateStatus(
            @PathVariable Long id,
            @RequestBody UpdatePpdbRegistrationRequest request) {
        return service.updateStatus(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Data berhasil dihapus", null));
    }

}