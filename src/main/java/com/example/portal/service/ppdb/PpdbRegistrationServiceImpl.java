package com.example.portal.service.ppdb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.portal.repository.ppdb.PpdbRegistrationRepository;
import com.example.portal.service.auth.EmailService;
import com.example.portal.model.PpdbRegistration;
import com.example.portal.model.Siswa;
import com.example.portal.model.enums.StatusPembayaran;
import com.example.portal.model.enums.StatusValidasi;
import com.example.portal.dto.ppdb.CreatePpdbRegistrationRequest;
import com.example.portal.dto.ppdb.PpdbRegistrationResponse;
import com.example.portal.dto.ppdb.UpdatePpdbRegistrationRequest;
import com.example.portal.dto.siswa.SiswaDTO;
import com.example.portal.mapper.ppdb.PpdbRegistrationMapper;
import com.example.portal.mapper.siswa.SiswaMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.portal.config.helper.CustomUserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

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

        String lastNo = repository.findLastNoPendaftaranByYear(LocalDate.now().getYear());

        int nextNumber = 1;
        if (lastNo != null && !lastNo.isEmpty()) {
            String[] parts = lastNo.split("-");
            if (parts.length == 3) {
                try {
                    nextNumber = Integer.parseInt(parts[2]) + 1;
                } catch (NumberFormatException e) {
                    nextNumber = 1; // fallback kalau format aneh
                }
            }
        }

        return String.format("PPDB-%s-%03d", tahun, nextNumber);
    }

    // via web by calon siswa
    @Override
    public PpdbRegistrationResponse register(CreatePpdbRegistrationRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Anda pernah mendaftar sebelumnya. harap hubungi admin sekolah");
        }
        PpdbRegistration entity = new PpdbRegistration();
        entity.setNama(request.getNama());
        entity.setTanggalLahir(request.getTanggalLahir());
        entity.setAlamat(request.getAlamat());
        entity.setNoHandphone(request.getNoHandphone());
        entity.setEmail(request.getEmail());

        // entity.setStatus(StatusValidasi.MENUNGGU_VALIDASI);
        // entity.setStatusPembayaran(StatusPembayaran.MENUNGGU_PEMBAYARAN);
        // entity.setJumlahDibayar(null);
        // entity.setCatatanValidasi(null);
        entity.setStatus(
                request.getStatus() != null ? request.getStatus() : StatusValidasi.MENUNGGU_VALIDASI);

        entity.setStatusPembayaran(
                request.getStatusPembayaran() != null ? request.getStatusPembayaran()
                        : StatusPembayaran.MENUNGGU_PEMBAYARAN);

        entity.setJumlahDibayar(
                request.getJumlahDibayar() != null ? request.getJumlahDibayar() : BigDecimal.ZERO);

        entity.setCatatanValidasi(
                request.getCatatanValidasi() != null ? request.getCatatanValidasi() : null);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // generate nomor pendaftaran otomatis
        entity.setNoPendaftaran(generateNoPendaftaran());
        int tahun = LocalDate.now().getYear();
        entity.setTahunPpdb(tahun);

        repository.save(entity);
        // kirim email bukti registrasi

        emailService.sendRegistrationEmail(entity.getEmail(), entity.getNoPendaftaran(), entity.getNama());

        return PpdbRegistrationMapper.toResponse(entity);

    }

    // by admin sekolah
    @Override
    public PpdbRegistrationResponse registerByAdmin(CreatePpdbRegistrationRequest request) {
        if (repository.existsByEmailAndStatus(request.getEmail(), StatusValidasi.DITERIMA)) {
            throw new IllegalArgumentException("Data sudah terdaftar sebagai calon siswa");
        }
        // PpdbRegistration entity = new PpdbRegistration();
        PpdbRegistration entity;

        if (request.getNoPendaftaran() != null) {
            // Update existing
            entity = repository.findByNoPendaftaran(request.getNoPendaftaran())
                    .orElseThrow(() -> new IllegalArgumentException("No pendaftaran tidak ditemukan"));
        } else {
            // Create new
            entity = new PpdbRegistration();
            entity.setNoPendaftaran(generateNoPendaftaran());
            entity.setCreatedAt(LocalDateTime.now());
        }

        entity.setNama(request.getNama());
        entity.setTanggalLahir(request.getTanggalLahir());
        entity.setAlamat(request.getAlamat());
        entity.setNoHandphone(request.getNoHandphone());
        entity.setEmail(request.getEmail());
        entity.setJenisKelamin(request.getJenisKelamin());
        entity.setNamaAyah(request.getNamaAyah());
        entity.setNamaIbu(request.getNamaIbu());
        entity.setTahunPpdb(LocalDate.now().getYear());

        // entity.setStatus(StatusValidasi.MENUNGGU_VALIDASI);
        // entity.setStatusPembayaran(StatusPembayaran.MENUNGGU_PEMBAYARAN);
        // entity.setJumlahDibayar(null);
        // entity.setCatatanValidasi(null);
        entity.setStatus(
                request.getStatus() != null ? request.getStatus() : StatusValidasi.MENUNGGU_VALIDASI);

        entity.setStatusPembayaran(
                request.getStatusPembayaran() != null ? request.getStatusPembayaran()
                        : StatusPembayaran.MENUNGGU_PEMBAYARAN);

        entity.setJumlahDibayar(
                request.getJumlahDibayar() != null ? request.getJumlahDibayar() : BigDecimal.ZERO);

        entity.setCatatanValidasi(
                request.getCatatanValidasi() != null ? request.getCatatanValidasi() : null);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setValidatedAt(LocalDateTime.now());

        // ✅ ambil ID admin dari context login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        entity.setValidatedByAdminId(userDetails.getId());

        // generate nomor pendaftaran otomatis
        // entity.setNoPendaftaran(generateNoPendaftaran());

        PpdbRegistration saved = repository.save(entity);
        // kirim email bukti registrasi

        emailService.sendAcceptanceEmailPpdb(saved);

        return PpdbRegistrationMapper.toResponse(entity);

    }

    @Override
    public PpdbRegistrationResponse findByNoPendaftaran(String noPendaftaran) {
        return repository.findByNoPendaftaran(noPendaftaran)
                .map(PpdbRegistrationMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("No pendaftaran tidak ditemukan"));
    }

    @Override
    public Map<String, Object> getPpdbPage(int tahun, int page, int size) {
        Page<PpdbRegistration> ppdbPage = repository.findByTahunPpdb(tahun, PageRequest.of(page, size));
        List<PpdbRegistrationResponse> ppdbList = ppdbPage.getContent().stream()
                .map(PpdbRegistrationMapper::toResponse)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("items", ppdbList);
        response.put("currentPage", ppdbPage.getNumber());
        response.put("totalItems", ppdbPage.getTotalElements());
        response.put("totalPages", ppdbPage.getTotalPages());
        return response;
    }

    @Override
    public Map<String, Object> search(String keyword, int tahun, int page, int size) {
        Page<PpdbRegistration> ppdbPage = repository.searchByKeywordAndYear(keyword, tahun, PageRequest.of(page, size));
        List<PpdbRegistrationResponse> ppdbList = ppdbPage.getContent().stream()
                .map(PpdbRegistrationMapper::toResponse)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("items", ppdbList);
        response.put("currentPage", ppdbPage.getNumber());
        response.put("totalItems", ppdbPage.getTotalElements());
        response.put("totalPages", ppdbPage.getTotalPages());
        return response;
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
        Optional<PpdbRegistration> existing = repository.findByEmail(request.getEmail());
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            throw new IllegalArgumentException("Email sudah terdaftar");
        }

        PpdbRegistration entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data tidak ditemukan"));

        // gunakan mapper untuk update field
        PpdbRegistrationMapper.updateEntity(entity, request);

        entity.setValidatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // ✅ ambil ID admin dari context login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        entity.setValidatedByAdminId(userDetails.getId());

        // repository.save(entity);
        PpdbRegistration saved = repository.save(entity);

        if (Boolean.TRUE.equals(request.getIsSendEmail())) {
            emailService.sendAcceptanceEmailPpdb(saved);
        }

        return PpdbRegistrationMapper.toResponse(saved);

    }

    @Override
    public void deleteById(Long id) {
        PpdbRegistration entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data tidak ditemukan"));
        repository.delete(entity);
    }

    @Override
    public List<PpdbRegistrationResponse> getAllByYear(int tahun) {
        return repository.findByTahunPpdb(tahun).stream()
                .map(PpdbRegistrationMapper::toResponse)
                .collect(Collectors.toList());
    }
}