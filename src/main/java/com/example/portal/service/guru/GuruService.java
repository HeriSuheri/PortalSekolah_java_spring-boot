package com.example.portal.service.guru;

import com.example.portal.dto.guru.CreateGuruRequest;
import com.example.portal.dto.guru.UpdateGuruRequest;
import com.example.portal.dto.guru.GuruDTO;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface GuruService {
    GuruDTO createGuru(CreateGuruRequest request);

    GuruDTO updateGuru(Long id, UpdateGuruRequest request, Authentication auth);

    void deleteGuru(Long id);

    GuruDTO getGuruById(Long id);

    List<GuruDTO> getAllGuru();

    Map<String, Object> getGurus(int page, int size); // pagination

    // search admin
    Map<String, Object> searchGurus(String keyword, int page, int size);
}