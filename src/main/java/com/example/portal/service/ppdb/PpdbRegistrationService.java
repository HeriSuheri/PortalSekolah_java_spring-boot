package com.example.portal.service.ppdb;

import com.example.portal.dto.ppdb.CreatePpdbRegistrationRequest;
import com.example.portal.dto.ppdb.PpdbRegistrationResponse;
import com.example.portal.dto.ppdb.UpdatePpdbRegistrationRequest;

public interface PpdbRegistrationService {
    PpdbRegistrationResponse register(CreatePpdbRegistrationRequest request);

    PpdbRegistrationResponse findByNoPendaftaran(String noPendaftaran);

    // PpdbRegistrationResponse updateStatus(Long id, String status, String statusPembayaran, String catatanValidasi);
    PpdbRegistrationResponse updateStatus(Long id, UpdatePpdbRegistrationRequest request);
}
