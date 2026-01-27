package com.example.portal.service.siswa;

import com.example.portal.dto.siswa.CreateSiswaRequest;
import com.example.portal.dto.siswa.UpdateSiswaRequest;
import com.example.portal.dto.siswa.SiswaDTO;
import com.example.portal.mapper.siswa.SiswaMapper;
import com.example.portal.model.Siswa;
import com.example.portal.repository.siswa.SiswaRepository;
import com.example.portal.service.auth.EmailService;
import com.example.portal.model.Classroom;
import com.example.portal.model.PpdbRegistration;
import com.example.portal.repository.classroom.ClassroomRepository;
import com.example.portal.repository.ppdb.PpdbRegistrationRepository;
import com.example.portal.model.User;
import com.example.portal.model.enums.StatusPembayaran;
import com.example.portal.model.enums.StatusValidasi;
import com.example.portal.repository.UserRepository;
import com.example.portal.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SiswaServiceImpl implements SiswaService {

    private final SiswaRepository siswaRepo;
    private final ClassroomRepository classroomRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final PpdbRegistrationRepository ppdbRegistrationRepo;
    private final EmailService emailService;

    public SiswaServiceImpl(SiswaRepository siswaRepo,
            ClassroomRepository classroomRepo,
            UserRepository userRepo,
            PasswordEncoder passwordEncoder, PpdbRegistrationRepository ppdbRegistrationRepo,
            EmailService emailService) {
        this.siswaRepo = siswaRepo;
        this.classroomRepo = classroomRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.ppdbRegistrationRepo = ppdbRegistrationRepo;
        this.emailService = emailService;
    }

    // private String generateNis() {
    // // contoh: ambil tahun sekarang + counter dari DB
    // int tahun = LocalDate.now().getYear();
    // long count = siswaRepo.count() + 1; // atau sequence khusus
    // return tahun + String.format("%04d", count);
    // }

    private String generateNis() {
        int tahun = LocalDate.now().getYear();
        String tahunStr = String.valueOf(tahun);

        String lastNis = siswaRepo.findLastNisByYear(tahunStr);

        long nextNumber;
        if (lastNis == null) {
            // belum ada siswa tahun ini
            nextNumber = 1;
        } else {
            // ambil 4 digit terakhir dari NIS
            String lastCounterStr = lastNis.substring(4);
            nextNumber = Long.parseLong(lastCounterStr) + 1;
        }

        return tahunStr + String.format("%04d", nextNumber);
    }

    @Override
    public SiswaDTO create(CreateSiswaRequest request) {
        // ✅ generate NIS otomatis
        String nisBaru = generateNis();

        // Validasi unik
        if (siswaRepo.existsByNis(nisBaru)) {
            throw new IllegalArgumentException("NIS sudah digunakan");
        }
        if (userRepo.existsByNomorInduk(nisBaru)) {
            throw new IllegalArgumentException("Nomor Induk sudah digunakan di User");
        }
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email sudah digunakan di User");
        }

        // Buat akun User untuk siswa
        User user = new User();
        user.setNomorInduk(nisBaru);
        user.setTanggalLahir(request.getTanggalLahir());
        user.setPassword(null); // first login pakai tanggal lahir
        user.setRole(Role.SISWA);
        user.setNama(request.getNama());
        user.setEmail(request.getEmail());
        userRepo.save(user);

        // Ambil classroom
        Classroom classroom = classroomRepo.findById(request.getClassroomId())
                .orElseThrow(() -> new IllegalArgumentException("Classroom tidak ditemukan"));

        // Buat entity Siswa
        Siswa siswa = new Siswa();
        siswa.setNis(nisBaru);
        siswa.setNama(request.getNama());
        siswa.setTanggalLahir(request.getTanggalLahir());
        siswa.setAlamat(request.getAlamat());
        siswa.setNamaAyah(request.getNamaAyah());
        siswa.setNamaIbu(request.getNamaIbu());
        siswa.setNoHandphone(request.getNoHandphone());
        siswa.setClassroom(classroom);
        siswa.setUser(user);

        // ✅ Tambahan: relasi ke PPDB Registration kalau ada noPendaftaran
        if (request.getNoPendaftaran() != null) {
            PpdbRegistration reg = ppdbRegistrationRepo.findByNoPendaftaran(request.getNoPendaftaran())
                    .orElseThrow(() -> new IllegalArgumentException("PPDB Registration tidak ditemukan"));

            siswa.setPpdbRegistration(reg);

            // ✅ ambil dari request kalau ada, kalau tidak fallback ke PPDB
            siswa.setStatus(request.getStatus() != null ? request.getStatus() : reg.getStatus());
            siswa.setStatusPembayaran(
                    request.getStatusPembayaran() != null ? request.getStatusPembayaran() : reg.getStatusPembayaran());
            siswa.setJumlahBayar(
                    request.getJumlahBayar() != null ? request.getJumlahBayar() : reg.getJumlahDibayar());

            // kalau admin override, update PPDB juga biar sinkron
            reg.setStatus(siswa.getStatus());
            reg.setStatusPembayaran(siswa.getStatusPembayaran());
            reg.setJumlahDibayar(siswa.getJumlahBayar());
            reg.setValidatedAt(LocalDateTime.now());
            reg.setValidatedByAdminId(1L); // ambil dari context admin login
            ppdbRegistrationRepo.save(reg);

        } else {
            // default untuk siswa offline/pindahan
            if (request.getStatus() != null) {
                siswa.setStatus(request.getStatus());
            } else {
                siswa.setStatus(StatusValidasi.MENUNGGU_VALIDASI);
            }

            if (request.getStatusPembayaran() != null) {
                siswa.setStatusPembayaran(request.getStatusPembayaran());
            } else {
                siswa.setStatusPembayaran(StatusPembayaran.MENUNGGU_PEMBAYARAN);
            }

            if (request.getJumlahBayar() != null) {
                siswa.setJumlahBayar(request.getJumlahBayar());
            } else {
                siswa.setJumlahBayar(BigDecimal.ZERO);
            }

        }

        // siswaRepo.save(siswa);

        Siswa saved = siswaRepo.save(siswa);

        // ✅ kirim email hanya saat create & status DITERIMA
        if (saved.getStatus() == StatusValidasi.DITERIMA) {
            emailService.sendAcceptanceEmail(saved);
        }

        return SiswaMapper.toDTO(siswa);
    }

    @Override
    public SiswaDTO update(Long id, UpdateSiswaRequest request) {
        Siswa siswa = siswaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Siswa tidak ditemukan"));

        // update field dasar
        siswa.setNama(request.getNama());
        siswa.setTanggalLahir(request.getTanggalLahir());
        siswa.setAlamat(request.getAlamat());
        siswa.setNamaAyah(request.getNamaAyah());
        siswa.setNamaIbu(request.getNamaIbu());
        siswa.setNoHandphone(request.getNoHandphone());

        // classroom
        if (request.getClassroomId() != null) {
            Classroom classroom = classroomRepo.findById(request.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom tidak ditemukan"));
            siswa.setClassroom(classroom);
        }

        // validasi & update NIS
        if (request.getNis() != null && !request.getNis().equals(siswa.getNis())) {
            if (siswaRepo.existsByNis(request.getNis())) {
                throw new IllegalArgumentException("NIS sudah digunakan oleh siswa lain");
            }
            if (userRepo.existsByNomorInduk(request.getNis())) {
                throw new IllegalArgumentException("Nomor Induk sudah digunakan di User lain");
            }
            siswa.setNis(request.getNis());
            siswa.getUser().setNomorInduk(request.getNis());
        }

        // update user
        User user = siswa.getUser();
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (userRepo.existsByEmail(request.getEmail()) &&
                    !user.getEmail().equals(request.getEmail())) {
                throw new IllegalArgumentException("Email sudah digunakan di User");
            }
            user.setEmail(request.getEmail());
        }
        user.setNama(request.getNama());
        user.setTanggalLahir(request.getTanggalLahir());

        // ✅ update kolom sinkron di siswa (pakai fallback)
        if (request.getStatus() != null) {
            siswa.setStatus(request.getStatus());
        }
        if (request.getStatusPembayaran() != null) {
            siswa.setStatusPembayaran(request.getStatusPembayaran());
        }
        if (request.getJumlahBayar() != null) {
            siswa.setJumlahBayar(request.getJumlahBayar());
        }

        // ✅ update PPDB Registration kalau siswa punya relasi
        if (siswa.getPpdbRegistration() != null) {
            PpdbRegistration reg = siswa.getPpdbRegistration();

            reg.setStatus(request.getStatus() != null ? request.getStatus() : reg.getStatus());
            reg.setStatusPembayaran(
                    request.getStatusPembayaran() != null ? request.getStatusPembayaran() : reg.getStatusPembayaran());
            reg.setJumlahDibayar(request.getJumlahBayar() != null ? request.getJumlahBayar() : reg.getJumlahDibayar());

            reg.setValidatedAt(LocalDateTime.now());
            reg.setValidatedByAdminId(1L); // TODO: ambil dari context admin login

            ppdbRegistrationRepo.save(reg);
        }

        return SiswaMapper.toDTO(siswaRepo.save(siswa));
    }

    @Override
    public void delete(Long id) {
        Siswa siswa = siswaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Siswa tidak ditemukan"));

        if (siswa.getPpdbRegistration() != null) {
            ppdbRegistrationRepo.delete(siswa.getPpdbRegistration());
        }

        siswaRepo.delete(siswa);
    }

    @Override
    public SiswaDTO getById(Long id) {
        return siswaRepo.findById(id)
                .map(SiswaMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Siswa tidak ditemukan"));
    }

    @Override
    public List<SiswaDTO> getAll() {
        return siswaRepo.findAll().stream()
                .map(SiswaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getSiswaPage(int page, int size) {
        Page<Siswa> siswaPage = siswaRepo.findAll(PageRequest.of(page, size));
        List<SiswaDTO> siswaList = siswaPage.getContent().stream()
                .map(SiswaMapper::toDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("items", siswaList);
        response.put("currentPage", siswaPage.getNumber());
        response.put("totalItems", siswaPage.getTotalElements());
        response.put("totalPages", siswaPage.getTotalPages());
        return response;
    }

    @Override
    public Map<String, Object> search(String keyword, int page, int size) {
        Page<Siswa> siswaPage = siswaRepo.searchByKeyword(keyword, PageRequest.of(page, size));
        List<SiswaDTO> siswaList = siswaPage.getContent().stream()
                .map(SiswaMapper::toDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("items", siswaList);
        response.put("currentPage", siswaPage.getNumber());
        response.put("totalItems", siswaPage.getTotalElements());
        response.put("totalPages", siswaPage.getTotalPages());
        return response;
    }

    @Override
    public List<SiswaDTO> getByClassroom(Long classroomId) {
        return siswaRepo.findByClassroomId(classroomId).stream()
                .map(SiswaMapper::toDTO)
                .collect(Collectors.toList());
    }
}