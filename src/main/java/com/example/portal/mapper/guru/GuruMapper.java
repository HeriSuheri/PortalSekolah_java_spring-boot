package com.example.portal.mapper.guru;

import com.example.portal.dto.guru.GuruDTO;
import com.example.portal.model.Guru;

public class GuruMapper {
    public static GuruDTO toDTO(Guru guru) {
        GuruDTO dto = new GuruDTO();
        dto.setId(guru.getId());
        dto.setNip(guru.getNip());
        dto.setNama(guru.getNama());
        dto.setTanggalLahir(guru.getTanggalLahir());
        dto.setEmail(guru.getUser().getEmail());
        dto.setFotoUrl(guru.getUser().getFotoUrl());
        return dto;
    }
}