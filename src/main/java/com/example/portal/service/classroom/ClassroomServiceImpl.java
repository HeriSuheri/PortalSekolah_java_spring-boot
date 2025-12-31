package com.example.portal.service.classroom;

import org.springframework.stereotype.Service;
import com.example.portal.dto.classroom.ClassroomDTO;
import com.example.portal.dto.classroom.CreateClassroomRequest;
import com.example.portal.dto.classroom.UpdateClassroomRequest;
import com.example.portal.exception.ResourceNotFoundException;
import com.example.portal.mapper.classroom.ClassroomMapper;
import com.example.portal.model.Classroom;
import com.example.portal.model.GradeLevel;
import com.example.portal.repository.classroom.ClassroomRepository;
import com.example.portal.repository.gradelevel.GradeLevelRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
@Transactional
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepo;
    private final GradeLevelRepository gradeLevelRepo;

    public ClassroomServiceImpl(ClassroomRepository classroomRepo,
            GradeLevelRepository gradeLevelRepo) {
        this.classroomRepo = classroomRepo;
        this.gradeLevelRepo = gradeLevelRepo;
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

    @Override
    public Page<ClassroomDTO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Classroom> result = (keyword == null || keyword.isBlank())
                ? classroomRepo.findAll(pageable)
                : classroomRepo.findByNameContainingIgnoreCase(keyword.trim(), pageable);
        return result.map(ClassroomMapper::toDTO);
    }
}