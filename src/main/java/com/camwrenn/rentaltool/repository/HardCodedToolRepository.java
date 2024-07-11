package com.camwrenn.rentaltool.repository;

import com.camwrenn.rentaltool.domain.Tool;
import com.camwrenn.rentaltool.domain.ToolTypeCode;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HardCodedToolRepository implements ToolRepository {

    Map<String, Tool> tools;

    public HardCodedToolRepository(ToolTypeRepository toolTypeRepository) {
        tools = Stream.of(
                new Tool("CHNS", toolTypeRepository.getByCode(ToolTypeCode.CHAINSAW), "Stihl"),
                new Tool("LADW", toolTypeRepository.getByCode(ToolTypeCode.LADDER), "Werner"),
                new Tool("JAKD", toolTypeRepository.getByCode(ToolTypeCode.JACKHAMMER), "DeWalt"),
                new Tool("JAKR", toolTypeRepository.getByCode(ToolTypeCode.JACKHAMMER), "Rigid")
        ).collect(Collectors.toMap(Tool::getCode, tool -> tool));
    }

    @Override
    public Optional<Tool> getByCode(String code) {
        return Optional.ofNullable(tools.get(code));
    }

    @Override
    public List<String> getAllCodes() {
        return tools.keySet().stream().toList();
    }
}
