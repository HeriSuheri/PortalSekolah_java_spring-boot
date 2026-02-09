package com.example.portal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;

@Entity
@Table(name = "general_param")
public class GeneralParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "param_key", unique = true, nullable = false, length = 100)
    private String paramKey;

    // param_value sekarang JSONB → di JPA bisa pakai String dulu, atau langsung map
    // ke List<String> dengan converter
    // @Column(name = "param_value", columnDefinition = "jsonb")
    // private String paramValue;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "param_value", columnDefinition = "jsonb")
    private JsonNode paramValue;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public JsonNode getParamValue() {
        return paramValue;
    }

    public void setParamValue(JsonNode paramValue) {
        this.paramValue = paramValue;
    }

}