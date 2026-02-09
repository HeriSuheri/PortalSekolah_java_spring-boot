package com.example.portal.service.generalParam;

import com.example.portal.dto.generalParam.GeneralParamDTO;
import java.util.List;

public interface GeneralParamService {
    List<GeneralParamDTO> getAllParams();

    GeneralParamDTO updateParam(Long id, GeneralParamDTO dto);

    GeneralParamDTO updateByKey(String paramKey, GeneralParamDTO dto);
}