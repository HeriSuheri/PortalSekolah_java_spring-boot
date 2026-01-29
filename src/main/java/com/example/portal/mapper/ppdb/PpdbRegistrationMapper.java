package com.example.portal.mapper.ppdb;

import com.example.portal.dto.ppdb.PpdbRegistrationResponse;
import com.example.portal.dto.ppdb.UpdatePpdbRegistrationRequest;
import com.example.portal.model.PpdbRegistration;

public class PpdbRegistrationMapper {
    public static PpdbRegistrationResponse toResponse(PpdbRegistration entity) {
        PpdbRegistrationResponse dto = new PpdbRegistrationResponse();
        dto.setId(entity.getId());
        dto.setNoPendaftaran(entity.getNoPendaftaran());
        dto.setNama(entity.getNama());
        dto.setAlamat(entity.getAlamat());
        dto.setTanggalLahir(entity.getTanggalLahir());
        dto.setNoHandphone(entity.getNoHandphone());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());
        dto.setStatusPembayaran(entity.getStatusPembayaran());
        dto.setJumlahDibayar(entity.getJumlahDibayar());
        dto.setValidatedAt(entity.getValidatedAt());
        dto.setCatatanValidasi(entity.getCatatanValidasi());
        dto.setValidatedByAdminId(entity.getValidatedByAdminId());
        dto.setHasClassroom(entity.getHasClassroom());
        dto.setJenisKelamin(entity.getJenisKelamin());
        dto.setNamaAyah(entity.getNamaAyah());
        dto.setNamaIbu(entity.getNamaIbu());
        return dto;
    }

    public static void updateEntity(PpdbRegistration entity, UpdatePpdbRegistrationRequest request) {
        if (request.getNama() != null)
            entity.setNama(request.getNama());
        if (request.getTanggalLahir() != null)
            entity.setTanggalLahir(request.getTanggalLahir());
        if (request.getAlamat() != null)
            entity.setAlamat(request.getAlamat());
        if (request.getNoHandphone() != null)
            entity.setNoHandphone(request.getNoHandphone());
        if (request.getEmail() != null)
            entity.setEmail(request.getEmail());


        if (request.getJumlahDibayar() != null)
            entity.setJumlahDibayar(request.getJumlahDibayar());
        if (request.getStatus() != null)
            entity.setStatus(request.getStatus());
        if (request.getStatusPembayaran() != null)
            entity.setStatusPembayaran(request.getStatusPembayaran());
        if (request.getCatatanValidasi() != null)
            entity.setCatatanValidasi(request.getCatatanValidasi());
        if (request.getValidatedByAdminId() != null)
            entity.setValidatedByAdminId(request.getValidatedByAdminId());

        entity.setJenisKelamin(request.getJenisKelamin());
        entity.setNamaAyah(request.getNamaAyah());
        entity.setNamaIbu(request.getNamaIbu());
    }

}
