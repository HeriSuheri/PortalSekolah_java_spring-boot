package com.example.portal.mapper.siswa;

import com.example.portal.dto.siswa.SiswaDTO;
import com.example.portal.model.Siswa;

public class SiswaMapper {

    public static SiswaDTO toDTO(Siswa entity) {
        if (entity == null)
            return null;

        SiswaDTO dto = new SiswaDTO();
        dto.setId(entity.getId());
        dto.setNama(entity.getNama());
        dto.setNis(entity.getNis());
        dto.setTanggalLahir(entity.getTanggalLahir());
        dto.setAlamat(entity.getAlamat());
        dto.setNamaAyah(entity.getNamaAyah());
        dto.setNamaIbu(entity.getNamaIbu());
        dto.setNoHandphone(entity.getNoHandphone());

        if (entity.getClassroom() != null) {
            dto.setClassroomId(entity.getClassroom().getId());
            dto.setClassroomName(entity.getClassroom().getName());
        }

        if (entity.getUser() != null) {
            dto.setEmail(entity.getUser().getEmail());
            dto.setFotoUrl(entity.getUser().getFotoUrl());
        }

        return dto;
    }
}