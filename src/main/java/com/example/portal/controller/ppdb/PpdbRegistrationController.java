package com.example.portal.controller.ppdb;

import org.springframework.web.bind.annotation.*;
import com.example.portal.service.ppdb.PpdbRegistrationService;
import com.example.portal.dto.ppdb.CreatePpdbRegistrationRequest;
import com.example.portal.dto.ppdb.PpdbRegistrationResponse;
import com.example.portal.dto.ppdb.UpdatePpdbRegistrationRequest;

@RestController
@RequestMapping("/api/ppdb")
public class PpdbRegistrationController {

    private final PpdbRegistrationService service;

    public PpdbRegistrationController(PpdbRegistrationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public PpdbRegistrationResponse register(@RequestBody CreatePpdbRegistrationRequest request) {
        return service.register(request);
    }

    @GetMapping("/{noPendaftaran}")
    public PpdbRegistrationResponse findByNoPendaftaran(@PathVariable String noPendaftaran) {
        return service.findByNoPendaftaran(noPendaftaran);
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
}