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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
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

    // @Override
    // public GeneralParamDTO updateByKey(String paramKey, GeneralParamDTO dto) {
    // GeneralParam param = repo.findByParamKey(paramKey)
    // .orElseThrow(() -> new RuntimeException("Parameter tidak ditemukan"));
    // GeneralParamMapper.updateEntityFromDTO(dto, param);
    // return GeneralParamMapper.toDTO(repo.save(param));
    // }

    @Override
    public GeneralParamDTO updateByKey(String paramKey, GeneralParamDTO dto) {
        GeneralParam param = repo.findByParamKey(paramKey)
                .orElseThrow(() -> new RuntimeException("Parameter tidak ditemukan"));

        // Validasi khusus untuk paramKey = "acara"
        if ("kegiatan".equalsIgnoreCase(paramKey) && dto.getParamValue() != null) {
            ArrayNode arrayNode = (ArrayNode) dto.getParamValue();

            // 1. Validasi jumlah slider max 10
            if (arrayNode.size() > 10) {
                throw new RuntimeException("Maksimal 10 slider kegiatan");
            }

            // 2. Validasi ukuran file base64 (opsional)
            for (JsonNode node : arrayNode) {
                String base64Image = node.get("image").asText();
                // hitung size base64 (approx)
                int sizeInBytes = (base64Image.length() * 3) / 4;
                if (sizeInBytes > 2 * 1024 * 1024) {
                    throw new RuntimeException("Ukuran gambar maksimal 2MB");
                }
            }
        }

        GeneralParamMapper.updateEntityFromDTO(dto, param);
        return GeneralParamMapper.toDTO(repo.save(param));
    }
}