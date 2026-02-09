package com.example.portal.repository.generalParam;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portal.model.GeneralParam;

public interface GeneralParamRepository extends JpaRepository<GeneralParam, Long> {
    Optional<GeneralParam> findByParamKey(String paramKey);
}
