package com.camwrenn.rentaltool.repository;

import com.camwrenn.rentaltool.domain.ToolType;
import com.camwrenn.rentaltool.domain.ToolTypeCode;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class HardCodedToolTypeRepository implements ToolTypeRepository{

    Map<ToolTypeCode, ToolType> toolTypes;

    HardCodedToolTypeRepository() {
        toolTypes = Stream.of(
            new ToolType(ToolTypeCode.LADDER, 1.99, true, true, false),
            new ToolType(ToolTypeCode.CHAINSAW, 1.49, true, false, true),
            new ToolType(ToolTypeCode.JACKHAMMER, 2.99, true, false, false)
        ).collect(Collectors.toMap(ToolType::getCode, type -> type));
    }

    @Override
    public ToolType getByCode(ToolTypeCode code) {
        return toolTypes.get(code);
    }
}
