package com.example.portal.mapper.classroom;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.portal.dto.classroom.ClassroomDTO;
import com.example.portal.dto.classroom.ClassroomDetailDTO;
import com.example.portal.dto.classroom.ClassroomDetailSiswaKelasDTO;
import com.example.portal.dto.siswa.SiswaDTO;
import com.example.portal.model.Classroom;

public class ClassroomMapper {
    public static ClassroomDTO toDTO(Classroom entity) {
        ClassroomDTO dto = new ClassroomDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setGradeLevelId(entity.getGradeLevel().getId());
        dto.setGradeLevelName(entity.getGradeLevel().getName());
        if (entity.getWaliGuru() != null) {
            dto.setWaliGuruId(entity.getWaliGuru().getId());
            dto.setWaliGuruName(entity.getWaliGuru().getNama());
            dto.setWaliGuruNip(entity.getWaliGuru().getNip());
        }

        return dto;
    }

    public static ClassroomDTO toDTO(Classroom entity, long jumlahSiswa) {
        ClassroomDTO dto = toDTO(entity); // panggil versi lama
        dto.setJumlahSiswa(jumlahSiswa);
        return dto;
    }

    // public static ClassroomDetailDTO toDetailDTO(Classroom classroom,
    // List<SiswaDTO> siswaList) {
    // ClassroomDetailDTO dto = new ClassroomDetailDTO();
    // dto.setId(classroom.getId());
    // dto.setName(classroom.getName());

    // if (classroom.getGradeLevel() != null) {
    // dto.setGradeLevelId(classroom.getGradeLevel().getId());
    // dto.setGradeLevelName(classroom.getGradeLevel().getName());
    // }

    // if (classroom.getWaliGuru() != null) {
    // dto.setWaliGuruId(classroom.getWaliGuru().getId());
    // dto.setWaliGuruName(classroom.getWaliGuru().getNama());
    // }

    // dto.setSiswa(siswaList);
    // return dto;
    // }

    public static ClassroomDetailDTO toDetailDTO(Classroom classroom, Page<SiswaDTO> siswaPage) {
        ClassroomDetailDTO dto = new ClassroomDetailDTO();
        dto.setId(classroom.getId());
        dto.setName(classroom.getName());

        if (classroom.getGradeLevel() != null) {
            dto.setGradeLevelId(classroom.getGradeLevel().getId());
            dto.setGradeLevelName(classroom.getGradeLevel().getName());
        }

        if (classroom.getWaliGuru() != null) {
            dto.setWaliGuruId(classroom.getWaliGuru().getId());
            dto.setWaliGuruName(classroom.getWaliGuru().getNama());
            dto.setWaliGuruNip(classroom.getWaliGuru().getNip());
        }

        dto.setSiswa(siswaPage); // field di DTO harus Page<SiswaDTO>
        return dto;
    }

    public static ClassroomDetailSiswaKelasDTO toDetailNoPagingDTO(Classroom classroom, List<SiswaDTO> siswaList) {
        ClassroomDetailSiswaKelasDTO dto = new ClassroomDetailSiswaKelasDTO();
        dto.setId(classroom.getId());
        dto.setName(classroom.getName());

        if (classroom.getGradeLevel() != null) {
            dto.setGradeLevelId(classroom.getGradeLevel().getId());
            dto.setGradeLevelName(classroom.getGradeLevel().getName());
        }

        if (classroom.getWaliGuru() != null) {
            dto.setWaliGuruId(classroom.getWaliGuru().getId());
            dto.setWaliGuruName(classroom.getWaliGuru().getNama());
            dto.setWaliGuruNip(classroom.getWaliGuru().getNip());
        }

        dto.setSiswa(siswaList); // ✅ langsung list
        return dto;
    }

}
