package com.example.portal.service.siswa;

import com.example.portal.dto.siswa.CreateSiswaRequest;
import com.example.portal.dto.siswa.UpdateSiswaRequest;
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
}