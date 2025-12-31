package com.example.portal.dto.classroom;

public class ClassroomDTO {
    private Long id;
    private String name;
    private Long gradeLevelId;
    private String gradeLevelName;

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

}
