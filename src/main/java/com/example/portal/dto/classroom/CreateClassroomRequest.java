package com.example.portal.dto.classroom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateClassroomRequest {
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    private Long gradeLevelId;

    private Long waliGuruId;

    private Boolean isActive = true;

    public void setWaliGuruId(Long waliGuruId) {
        this.waliGuruId = waliGuruId;
    }

    public Long getWaliGuruId() {
        return waliGuruId;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
