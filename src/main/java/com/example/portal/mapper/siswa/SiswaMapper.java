package com.example.portal.mapper.siswa;

import com.example.portal.dto.siswa.SiswaDTO;
import com.example.portal.dto.siswa.berhenti.BerhentiDTO;
import com.example.portal.model.Siswa;
import com.example.portal.model.PpdbRegistration;

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
        dto.setJenisKelamin(entity.getJenisKelamin());
        dto.setStatusSiswa(entity.getStatusSiswa());

        // classroom
        if (entity.getClassroom() != null) {
            dto.setClassroomId(entity.getClassroom().getId());
            dto.setClassroomName(entity.getClassroom().getName());
        }

        // user
        if (entity.getUser() != null) {
            dto.setEmail(entity.getUser().getEmail());
            dto.setFotoUrl(entity.getUser().getFotoUrl());
        }

        // ✅ tambahan sinkron
        // dto.setStatus(entity.getStatus()); // kalau di Siswa masih String
        // dto.setStatusPembayaran(entity.getStatusPembayaran());
        // dto.setJumlahBayar(entity.getJumlahBayar());

        // relasi ke PPDB Registration
        if (entity.getPpdbRegistration() != null) {
            PpdbRegistration reg = entity.getPpdbRegistration();
            dto.setPpdbRegistrationId(reg.getId()); // ✅ balikin ID
            // dto.setNoPendaftaran(reg.getNoPendaftaran()); // balikin nomor pendaftaran
        }

        return dto;
    }

    public static BerhentiDTO berhenti(Siswa entity) {
        if (entity == null)
            return null;

        BerhentiDTO dto = new BerhentiDTO();
        dto.setId(entity.getId());
        dto.setNama(entity.getNama());
        dto.setNis(entity.getNis());
        dto.setTanggalLahir(entity.getTanggalLahir());
        dto.setNoHandphone(entity.getNoHandphone());
        dto.setJenisKelamin(entity.getJenisKelamin());
        // dto.setArchivedAt(entity.getArchivedAt());
        dto.setArchivedDateOnly(
                entity.getArchivedAt() != null
                        ? entity.getArchivedAt().toLocalDate().toString()
                        : null);
        dto.setCatatan(entity.getCatatan());
        dto.setStatusSiswa(entity.getStatusSiswa());

        // classroom
        if (entity.getClassroom() != null) {
            dto.setClassroomId(entity.getClassroom().getId());
            dto.setClassroomName(entity.getClassroom().getName());
        }

        // user
        if (entity.getUser() != null) {
            dto.setEmail(entity.getUser().getEmail());
        }

        return dto;
    }
}