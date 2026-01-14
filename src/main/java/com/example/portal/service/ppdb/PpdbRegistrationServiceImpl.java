package com.example.portal.service.ppdb;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.portal.repository.ppdb.PpdbRegistrationRepository;
import com.example.portal.service.auth.EmailService;
import com.example.portal.model.PpdbRegistration;
import com.example.portal.model.enums.StatusPembayaran;
import com.example.portal.model.enums.StatusValidasi;
import com.example.portal.dto.ppdb.CreatePpdbRegistrationRequest;
import com.example.portal.dto.ppdb.PpdbRegistrationResponse;
import com.example.portal.dto.ppdb.UpdatePpdbRegistrationRequest;
import com.example.portal.mapper.ppdb.PpdbRegistrationMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class PpdbRegistrationServiceImpl implements PpdbRegistrationService {

    private final PpdbRegistrationRepository repository;
    private final EmailService emailService;

    public PpdbRegistrationServiceImpl(PpdbRegistrationRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    private String generateNoPendaftaran() {
        String tahun = String.valueOf(LocalDate.now().getYear());
        long count = repository.countByCreatedAtYear(LocalDate.now().getYear());
        return String.format("PPDB-%s-%03d", tahun, count + 1);
    }

    @Override
    public PpdbRegistrationResponse register(CreatePpdbRegistrationRequest request) {
        PpdbRegistration entity = new PpdbRegistration();
        entity.setNama(request.getNama());
        entity.setTanggalLahir(request.getTanggalLahir());
        entity.setAlamat(request.getAlamat());
        entity.setNoHandphone(request.getNoHandphone());
        entity.setEmail(request.getEmail());

        entity.setStatus(StatusValidasi.MENUNGGU_VALIDASI);
        entity.setStatusPembayaran(StatusPembayaran.MENUNGGU_PEMBAYARAN);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // generate nomor pendaftaran otomatis
        entity.setNoPendaftaran(generateNoPendaftaran());

        repository.save(entity);
        // kirim email bukti registrasi
        emailService.sendRegistrationEmail(entity.getEmail(), entity.getNoPendaftaran(), entity.getNama());

        return PpdbRegistrationMapper.toResponse(entity);

    }

    @Override
    public PpdbRegistrationResponse findByNoPendaftaran(String noPendaftaran) {
        return repository.findByNoPendaftaran(noPendaftaran)
                .map(PpdbRegistrationMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("No pendaftaran tidak ditemukan"));
    }

    // @Override
    // public PpdbRegistrationResponse updateStatus(Long id,
    // UpdatePpdbRegistrationRequest request) {
    // PpdbRegistration entity = repository.findById(id)
    // .orElseThrow(() -> new RuntimeException("Data tidak ditemukan"));

    // entity.setStatus(request.getStatus());
    // entity.setStatusPembayaran(request.getStatusPembayaran());
    // entity.setCatatanValidasi(request.getCatatanValidasi());
    // entity.setValidatedByAdminId(request.getValidatedByAdminId());
    // entity.setValidatedAt(LocalDateTime.now());
    // entity.setUpdatedAt(LocalDateTime.now());

    // repository.save(entity);
    // return PpdbRegistrationMapper.toResponse(entity);
    // }

    @Override
    public PpdbRegistrationResponse updateStatus(Long id, UpdatePpdbRegistrationRequest request) {
        PpdbRegistration entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data tidak ditemukan"));

        // gunakan mapper untuk update field
        PpdbRegistrationMapper.updateEntity(entity, request);

        entity.setValidatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        repository.save(entity);
        return PpdbRegistrationMapper.toResponse(entity);
    }
}