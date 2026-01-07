package com.example.portal.service.classroom;

import org.springframework.stereotype.Service;
import com.example.portal.dto.classroom.ClassroomDTO;
import com.example.portal.dto.classroom.ClassroomDetailDTO;
import com.example.portal.dto.classroom.CreateClassroomRequest;
import com.example.portal.dto.classroom.UpdateClassroomRequest;
import com.example.portal.dto.siswa.SiswaDTO;
import com.example.portal.exception.ResourceNotFoundException;
import com.example.portal.mapper.classroom.ClassroomMapper;
import com.example.portal.mapper.siswa.SiswaMapper;
import com.example.portal.model.Classroom;
import com.example.portal.model.GradeLevel;
import com.example.portal.model.Guru;
import com.example.portal.repository.classroom.ClassroomRepository;
import com.example.portal.repository.gradelevel.GradeLevelRepository;
import com.example.portal.repository.guru.GuruRepository;
import com.example.portal.repository.siswa.SiswaRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
@Transactional
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepo;
    private final GradeLevelRepository gradeLevelRepo;
    private final GuruRepository guruRepo;
    private final SiswaRepository siswaRepo;

    public ClassroomServiceImpl(ClassroomRepository classroomRepo,
            GradeLevelRepository gradeLevelRepo, GuruRepository guruRepo, SiswaRepository siswaRepo) {
        this.classroomRepo = classroomRepo;
        this.gradeLevelRepo = gradeLevelRepo;
        this.guruRepo = guruRepo;
        this.siswaRepo = siswaRepo;
    }

    @Override
    public ClassroomDTO create(CreateClassroomRequest request) {
        String name = request.getName().trim();
        if (classroomRepo.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Nama kelas sudah ada");
        }
        GradeLevel gradeLevel = gradeLevelRepo.findById(request.getGradeLevelId())
                .orElseThrow(() -> new ResourceNotFoundException("Grade level tidak ditemukan"));

        Classroom entity = new Classroom();
        entity.setName(name);
        entity.setGradeLevel(gradeLevel);
        // ✅ assign wali guru kalau ada
        if (request.getWaliGuruId() != null) {
            Guru waliGuru = guruRepo.findById(request.getWaliGuruId())
                    .orElseThrow(() -> new ResourceNotFoundException("Guru tidak ditemukan"));
            entity.setWaliGuru(waliGuru);
        }

        return ClassroomMapper.toDTO(classroomRepo.save(entity));
    }

    @Override
    public ClassroomDTO update(Long id, UpdateClassroomRequest request) {
        Classroom entity = classroomRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom tidak ditemukan"));

        String name = request.getName().trim();
        if (classroomRepo.existsByNameIgnoreCase(name) && !entity.getName().equalsIgnoreCase(name)) {
            throw new IllegalArgumentException("Nama kelas sudah ada");
        }

        GradeLevel gradeLevel = gradeLevelRepo.findById(request.getGradeLevelId())
                .orElseThrow(() -> new ResourceNotFoundException("Grade level tidak ditemukan"));

        entity.setName(name);
        entity.setGradeLevel(gradeLevel);
        // ✅ update wali guru kalau ada
        if (request.getWaliGuruId() != null) {
            Guru waliGuru = guruRepo.findById(request.getWaliGuruId())
                    .orElseThrow(() -> new ResourceNotFoundException("Guru tidak ditemukan"));
            entity.setWaliGuru(waliGuru);
        } else {
            entity.setWaliGuru(null); // kalau mau reset wali guru
        }

        return ClassroomMapper.toDTO(classroomRepo.save(entity));
    }

    @Override
    public void delete(Long id) {
        Classroom entity = classroomRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom tidak ditemukan"));
        classroomRepo.delete(entity);
    }

    @Override
    public ClassroomDTO getById(Long id) {
        return classroomRepo.findById(id)
                .map(ClassroomMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom tidak ditemukan"));
    }

    // get all classroom
    @Override
    public List<ClassroomDTO> getAll() {
        return classroomRepo.findAll().stream()
                .map(ClassroomMapper::toDTO)
                .collect(Collectors.toList());
    }

    // get classroom with paging
    @Override
    public Page<ClassroomDTO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Classroom> result = (keyword == null || keyword.isBlank())
                ? classroomRepo.findAll(pageable)
                : classroomRepo.findByNameContainingIgnoreCase(keyword.trim(), pageable);
        return result.map(ClassroomMapper::toDTO);
    }

    // @Override
    // public ClassroomDetailDTO getClassroomDetail(Long id) {
    // Classroom classroom = classroomRepo.findById(id)
    // .orElseThrow(() -> new RuntimeException("Classroom tidak ditemukan"));

    // List<SiswaDTO> siswaList = siswaRepo.findByClassroomId(id).stream()
    // .map(SiswaMapper::toDTO)
    // .collect(Collectors.toList());

    // return ClassroomMapper.toDetailDTO(classroom, siswaList);
    // }

    // detail classromm: CRUD siswa, info siswa - wali kelas, dll
    @Override
    public ClassroomDetailDTO getClassroomDetail(Long id, String keyword, int page, int size) {
        Classroom classroom = classroomRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom tidak ditemukan"));

        Pageable pageable = PageRequest.of(page, size, Sort.by("nama").ascending());
        Page<SiswaDTO> siswaPage = (keyword == null || keyword.isBlank())
                ? siswaRepo.findByClassroomId(id, pageable).map(SiswaMapper::toDTO)
                : siswaRepo.searchByClassroomAndNama(id, keyword.trim(), pageable).map(SiswaMapper::toDTO);

        return ClassroomMapper.toDetailDTO(classroom, siswaPage);
    }
}