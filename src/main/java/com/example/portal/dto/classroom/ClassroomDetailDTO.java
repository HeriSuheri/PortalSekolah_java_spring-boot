package com.example.portal.dto.classroom;

import com.example.portal.dto.siswa.SiswaDTO;
import java.util.List;

import org.springframework.data.domain.Page;

public class ClassroomDetailDTO {
    private Long id;
    private String name;
    private Long gradeLevelId;
    private String gradeLevelName;
    private Long waliGuruId;
    private String waliGuruName;
    // private List<SiswaDTO> siswa;
    private Page<SiswaDTO> siswa;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGradeLevelId(Long gradeLevelId) {
        this.gradeLevelId = gradeLevelId;
    }

    public Long getGradeLevelId() {
        return gradeLevelId;
    }

    public void setGradeLevelName(String gradeLevelName) {
        this.gradeLevelName = gradeLevelName;
    }

    public void setWaliGuruId(Long waliGuruId) {
        this.waliGuruId = waliGuruId;
    }

    public Long getWaliGuruId() {
        return waliGuruId;
    }

    public String getGradeLevelName() {
        return gradeLevelName;
    }

    public void setWaliGuruName(String waliGuruName) {
        this.waliGuruName = waliGuruName;
    }

    public String getWaliGuruName() {
        return waliGuruName;
    }

    // public void setSiswa(List<SiswaDTO> siswa) {
    // this.siswa = siswa;
    // }
    // public void setSiswa(Page<SiswaDTO> siswa) {
    // this.siswa = siswa;
    // }

    // public List<SiswaDTO> getSiswa() {
    // return siswa;
    // }
    public Page<SiswaDTO> getSiswa() {
        return siswa;
    }

    public void setSiswa(Page<SiswaDTO> siswa) {
        this.siswa = siswa;
    }

}