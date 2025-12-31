package com.example.portal.mapper.classroom;

import com.example.portal.dto.classroom.ClassroomDTO;
import com.example.portal.model.Classroom;

public class ClassroomMapper {
    public static ClassroomDTO toDTO(Classroom entity) {
        ClassroomDTO dto = new ClassroomDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setGradeLevelId(entity.getGradeLevel().getId());
        dto.setGradeLevelName(entity.getGradeLevel().getName());
        return dto;
    }
}
