package com.example.portal.dto.ppdb;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.portal.model.enums.StatusPembayaran;
import com.example.portal.model.enums.StatusValidasi;

public class PpdbRegistrationResponse {
    private String noPendaftaran;
    private String nama;
    private String email;
    private StatusValidasi status;
    private StatusPembayaran statusPembayaran;
    private BigDecimal jumlahDibayar;
    private LocalDateTime validatedAt;
    private String catatanValidasi;

    public String getNoPendaftaran() {
        return noPendaftaran;
    }

    public void setNoPendaftaran(String noPendaftaran) {
        this.noPendaftaran = noPendaftaran;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StatusValidasi getStatus() {
        return status;
    }

    public void setStatus(StatusValidasi status) {
        this.status = status;
    }

    public StatusPembayaran getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(StatusPembayaran statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public BigDecimal getJumlahDibayar() {
        return jumlahDibayar;
    }

    public void setJumlahDibayar(BigDecimal jumlahDibayar) {
        this.jumlahDibayar = jumlahDibayar;
    }

    public LocalDateTime getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(LocalDateTime validatedAt) {
        this.validatedAt = validatedAt;
    }

    public String getCatatanValidasi() {
        return catatanValidasi;
    }

    public void setCatatanValidasi(String catatanValidasi) {
        this.catatanValidasi = catatanValidasi;
    }

}