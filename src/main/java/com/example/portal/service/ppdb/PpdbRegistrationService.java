package com.example.portal.service.ppdb;

import java.util.Map;

import com.example.portal.dto.ppdb.CreatePpdbRegistrationRequest;
import com.example.portal.dto.ppdb.PpdbRegistrationResponse;
import com.example.portal.dto.ppdb.UpdatePpdbRegistrationRequest;

public interface PpdbRegistrationService {
    PpdbRegistrationResponse register(CreatePpdbRegistrationRequest request);

    PpdbRegistrationResponse registerByAdmin(CreatePpdbRegistrationRequest request);

    PpdbRegistrationResponse findByNoPendaftaran(String noPendaftaran);

    Map<String, Object> getPpdbPage(int page, int size);

    Map<String, Object> search(String keyword, int page, int size);

    // PpdbRegistrationResponse updateStatus(Long id, String status, String statusPembayaran, String catatanValidasi);
    PpdbRegistrationResponse updateStatus(Long id, UpdatePpdbRegistrationRequest request);

    void deleteById(Long id);
}
