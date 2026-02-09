package com.example.portal.controller.generalParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.portal.dto.generalParam.GeneralParamDTO;
import com.example.portal.service.generalParam.GeneralParamService;

@RestController
@RequestMapping("/api/general-param")
public class GeneralParamController {

    @Autowired
    private GeneralParamService service;

    @GetMapping
    public ResponseEntity<List<GeneralParamDTO>> getAllParams() {
        return ResponseEntity.ok(service.getAllParams());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralParamDTO> updateParam(
            @PathVariable Long id,
            @RequestBody GeneralParamDTO dto) {
        return ResponseEntity.ok(service.updateParam(id, dto));
    }

    @PutMapping("/key/{paramKey}")
    public ResponseEntity<GeneralParamDTO> updateByKey(
            @PathVariable String paramKey,
            @RequestBody GeneralParamDTO dto) {
        return ResponseEntity.ok(service.updateByKey(paramKey, dto));
    }
}
