package com.example.portal.service.siswa;

import com.example.portal.dto.siswa.CreateSiswaRequest;
import com.example.portal.dto.siswa.UpdateSiswaRequest;
import com.example.portal.dto.siswa.SiswaDTO;
import com.example.portal.mapper.siswa.SiswaMapper;
import com.example.portal.model.Siswa;
import com.example.portal.repository.siswa.SiswaRepository;
import com.example.portal.model.Classroom;
import com.example.portal.repository.classroom.ClassroomRepository;
import com.example.portal.model.User;
import com.example.portal.repository.UserRepository;
import com.example.portal.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SiswaServiceImpl implements SiswaService {

    private final SiswaRepository siswaRepo;
    private final ClassroomRepository classroomRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public SiswaServiceImpl(SiswaRepository siswaRepo,
            ClassroomRepository classroomRepo,
            UserRepository userRepo,
            PasswordEncoder passwordEncoder) {
        this.siswaRepo = siswaRepo;
        this.classroomRepo = classroomRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SiswaDTO create(CreateSiswaRequest request) {
        // Validasi unik
        if (siswaRepo.existsByNis(request.getNis())) {
            throw new IllegalArgumentException("NIS sudah digunakan");
        }

        if (userRepo.existsByNomorInduk(request.getNis())) {
            throw new IllegalArgumentException("Nomor Induk sudah digunakan di User");
        }

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email sudah digunakan di User");
        }

        // Buat akun User untuk siswa
        User user = new User();
        user.setNomorInduk(request.getNis());
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
        siswa.setNis(request.getNis());
        siswa.setNama(request.getNama());
        siswa.setTanggalLahir(request.getTanggalLahir());
        siswa.setAlamat(request.getAlamat());
        siswa.setNamaAyah(request.getNamaAyah());
        siswa.setNamaIbu(request.getNamaIbu());
        siswa.setNoHandphone(request.getNoHandphone());
        siswa.setClassroom(classroom);
        siswa.setUser(user);

        siswaRepo.save(siswa);

        return SiswaMapper.toDTO(siswa);
    }

    @Override
    public SiswaDTO update(Long id, UpdateSiswaRequest request) {
        Siswa siswa = siswaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Siswa tidak ditemukan"));

        siswa.setNama(request.getNama());
        siswa.setTanggalLahir(request.getTanggalLahir());
        siswa.setAlamat(request.getAlamat());
        siswa.setNamaAyah(request.getNamaAyah());
        siswa.setNamaIbu(request.getNamaIbu());
        siswa.setNoHandphone(request.getNoHandphone());

        if (request.getClassroomId() != null) {
            Classroom classroom = classroomRepo.findById(request.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom tidak ditemukan"));
            siswa.setClassroom(classroom);
        }

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

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            User user = siswa.getUser();
            if (userRepo.existsByEmail(request.getEmail()) &&
                    !user.getEmail().equals(request.getEmail())) {
                throw new IllegalArgumentException("Email sudah digunakan di User");
            }
            user.setEmail(request.getEmail());
        }

        // sinkron nama di user juga
        siswa.getUser().setNama(request.getNama());

        return SiswaMapper.toDTO(siswaRepo.save(siswa));
    }

    @Override
    public void delete(Long id) {
        Siswa siswa = siswaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Siswa tidak ditemukan"));
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