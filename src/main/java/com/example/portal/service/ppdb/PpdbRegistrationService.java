package com.example.portal.service.ppdb;

import java.util.List;
import java.util.Map;

import com.example.portal.dto.ppdb.CreatePpdbRegistrationRequest;
import com.example.portal.dto.ppdb.PpdbRegistrationResponse;
import com.example.portal.dto.ppdb.UpdatePpdbRegistrationRequest;
import com.example.portal.dto.siswa.SiswaDTO;

public interface PpdbRegistrationService {
    List<PpdbRegistrationResponse> getAllByYear(int tahun);

    PpdbRegistrationResponse register(CreatePpdbRegistrationRequest request);

    PpdbRegistrationResponse registerByAdmin(CreatePpdbRegistrationRequest request);

    PpdbRegistrationResponse findByNoPendaftaran(String noPendaftaran);

    Map<String, Object> getPpdbPage(int tahun, int page, int size);

    Map<String, Object> search(String keyword, int tahun, int page, int size);

    // PpdbRegistrationResponse updateStatus(Long id, String status, String
    // statusPembayaran, String catatanValidasi);
    PpdbRegistrationResponse updateStatus(Long id, UpdatePpdbRegistrationRequest request);

    void deleteById(Long id);
}
