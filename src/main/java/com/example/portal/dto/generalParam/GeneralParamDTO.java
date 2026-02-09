package com.example.portal.dto.generalParam;

import com.fasterxml.jackson.databind.JsonNode;

public class GeneralParamDTO {
    private Long id;
    private String paramKey;
    private JsonNode paramValue;
    private Boolean isActive;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JsonNode getParamValue() {
        return paramValue;
    }

    public void setParamValue(JsonNode paramValue) {
        this.paramValue = paramValue;
    }

}
