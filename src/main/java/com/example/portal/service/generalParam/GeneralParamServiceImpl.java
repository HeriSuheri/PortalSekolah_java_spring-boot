package com.example.portal.service.generalParam;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.portal.dto.generalParam.GeneralParamDTO;
import com.example.portal.mapper.generalParam.GeneralParamMapper;
import com.example.portal.model.GeneralParam;
import com.example.portal.repository.UserRepository;
import com.example.portal.repository.generalParam.GeneralParamRepository;
import com.example.portal.repository.guru.GuruRepository;
import com.example.portal.dto.generalParam.GeneralParamDTO;

@Service
public class GeneralParamServiceImpl implements GeneralParamService {
    // private final GuruRepository guruRepository;
    // private final UserRepository userRepository;

    // public GuruServiceImpl(GuruRepository guruRepository, UserRepository
    // userRepository) {
    // this.guruRepository = guruRepository;
    // this.userRepository = userRepository;
    // }

    @Autowired
    private GeneralParamRepository repo;

    @Override
    public List<GeneralParamDTO> getAllParams() {
        return repo.findAll()
                .stream()
                .map(GeneralParamMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GeneralParamDTO updateParam(Long id, GeneralParamDTO dto) {
        GeneralParam param = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Parameter tidak ditemukan"));
        GeneralParamMapper.updateEntityFromDTO(dto, param);
        return GeneralParamMapper.toDTO(repo.save(param));
    }

    @Override
    public GeneralParamDTO updateByKey(String paramKey, GeneralParamDTO dto) {
        GeneralParam param = repo.findByParamKey(paramKey)
                .orElseThrow(() -> new RuntimeException("Parameter tidak ditemukan"));
        GeneralParamMapper.updateEntityFromDTO(dto, param);
        return GeneralParamMapper.toDTO(repo.save(param));
    }
}