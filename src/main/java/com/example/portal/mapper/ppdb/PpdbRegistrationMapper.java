package com.example.portal.mapper.ppdb;

import com.example.portal.dto.ppdb.PpdbRegistrationResponse;
import com.example.portal.dto.ppdb.UpdatePpdbRegistrationRequest;
import com.example.portal.model.PpdbRegistration;

public class PpdbRegistrationMapper {
    public static PpdbRegistrationResponse toResponse(PpdbRegistration entity) {
        PpdbRegistrationResponse dto = new PpdbRegistrationResponse();
        dto.setNoPendaftaran(entity.getNoPendaftaran());
        dto.setNama(entity.getNama());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());
        dto.setStatusPembayaran(entity.getStatusPembayaran());
        dto.setJumlahDibayar(entity.getJumlahDibayar());
        dto.setValidatedAt(entity.getValidatedAt());
        dto.setCatatanValidasi(entity.getCatatanValidasi());
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

        if (request.getStatus() != null)
            entity.setStatus(request.getStatus());
        if (request.getStatusPembayaran() != null)
            entity.setStatusPembayaran(request.getStatusPembayaran());
        if (request.getCatatanValidasi() != null)
            entity.setCatatanValidasi(request.getCatatanValidasi());
        if (request.getValidatedByAdminId() != null)
            entity.setValidatedByAdminId(request.getValidatedByAdminId());
    }

}
