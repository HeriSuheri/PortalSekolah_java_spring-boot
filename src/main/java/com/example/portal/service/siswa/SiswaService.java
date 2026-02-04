package com.example.portal.service.siswa;

import com.example.portal.dto.siswa.CreateSiswaRequest;
import com.example.portal.dto.siswa.UpdateSiswaRequest;
import com.example.portal.dto.siswa.berhenti.BerhentiDTO;
import com.example.portal.dto.siswa.SiswaDTO;

import java.util.List;
import java.util.Map;

public interface SiswaService {
    SiswaDTO create(CreateSiswaRequest request);

    SiswaDTO update(Long id, UpdateSiswaRequest request);

    void delete(Long id);

    SiswaDTO getById(Long id);

    List<SiswaDTO> getAll();

    Map<String, Object> getSiswaPage(int page, int size);

    Map<String, Object> search(String keyword, int page, int size);

    List<SiswaDTO> getByClassroom(Long classroomId);

    void berhentiSiswa(Long siswaId, String alasan);

    // khusus menu arsip (BERHENTI)
    Map<String, Object> getSiswaBerhentiPage(int tahunBerhenti, int page, int size);

    Map<String, Object> searchBerhenti(String keyword, int tahunBerhenti, int page, int size);

    BerhentiDTO undoBerhenti(Long siswaId);

}