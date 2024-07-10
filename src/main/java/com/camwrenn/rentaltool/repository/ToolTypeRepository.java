package com.camwrenn.rentaltool.repository;

import com.camwrenn.rentaltool.domain.ToolType;
import com.camwrenn.rentaltool.domain.ToolTypeCode;

public interface ToolTypeRepository {

    ToolType getByCode(ToolTypeCode code);
}
