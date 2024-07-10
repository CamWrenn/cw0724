package com.camwrenn.rentaltool.repository;

import com.camwrenn.rentaltool.domain.Tool;

import java.util.List;
import java.util.Optional;

public interface ToolRepository {
    Optional<Tool> getByCode(String code);

    List<String> getAllCodes();
}
