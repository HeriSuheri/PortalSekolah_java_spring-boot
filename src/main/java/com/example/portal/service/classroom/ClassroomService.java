package com.example.portal.service.classroom;

import com.example.portal.dto.classroom.ClassroomDTO;
import com.example.portal.dto.classroom.ClassroomDetailDTO;
import com.example.portal.dto.classroom.CreateClassroomRequest;
import com.example.portal.dto.classroom.UpdateClassroomRequest;

import org.springframework.data.domain.Page;

public interface ClassroomService {
    ClassroomDTO create(CreateClassroomRequest request);

    ClassroomDTO update(Long id, UpdateClassroomRequest request);

    void delete(Long id);

    ClassroomDTO getById(Long id);

    Page<ClassroomDTO> search(String keyword, int page, int size);

    // ClassroomDetailDTO getClassroomDetail(Long id);
    ClassroomDetailDTO getClassroomDetail(Long id, String keyword, int page, int size);

}