package com.example.portal.mapper.generalParam;

import java.time.LocalDateTime;

import com.example.portal.dto.generalParam.GeneralParamDTO;
import com.example.portal.model.GeneralParam;

public class GeneralParamMapper {
    public static GeneralParamDTO toDTO(GeneralParam entity) {
        GeneralParamDTO dto = new GeneralParamDTO();
        dto.setId(entity.getId());
        dto.setParamKey(entity.getParamKey());

        // paramValue JSONB → simpan sebagai Object (String atau List)
        dto.setParamValue(entity.getParamValue());

        dto.setIsActive(entity.getIsActive());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public static void updateEntityFromDTO(GeneralParamDTO dto, GeneralParam entity) {
        if (dto.getParamValue() != null) {
            entity.setParamValue(dto.getParamValue()); // dto.getParamValue() sudah JsonNode
        }
        if (dto.getIsActive() != null) {
            entity.setIsActive(dto.getIsActive());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        entity.setUpdatedAt(LocalDateTime.now());
    }
}
