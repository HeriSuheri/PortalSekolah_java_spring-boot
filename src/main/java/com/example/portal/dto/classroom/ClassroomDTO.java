package com.example.portal.dto.classroom;

public class ClassroomDTO {
    private Long id;
    private String name;
    private Long gradeLevelId;
    private String gradeLevelName;

    private Long waliGuruId;
    private String waliGuruName;
    private String waliGuruNip;
    private Long jumlahSiswa;
    private Boolean isActive = true;

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

    public String getGradeLevelName() {
        return gradeLevelName;
    }

    public void setWaliGuruId(Long waliGuruId) {
        this.waliGuruId = waliGuruId;
    }

    public Long getWaliGuruId() {
        return waliGuruId;
    }

    public void setWaliGuruName(String waliGuruName) {
        this.waliGuruName = waliGuruName;
    }

    public String getWaliGuruName() {
        return waliGuruName;
    }

    public void setWaliGuruNip(String waliGuruNip) {
        this.waliGuruNip = waliGuruNip;
    }

    public String getWaliGuruNip() {
        return waliGuruNip;
    }

    public void setJumlahSiswa(Long jumlahSiswa) {
        this.jumlahSiswa = jumlahSiswa;
    }

    public Long getJumlahSiswa() {
        return jumlahSiswa;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
