package com.camwrenn.rentaltool.commands;

import com.camwrenn.rentaltool.domain.RentalAgreement;
import com.camwrenn.rentaltool.domain.Tool;
import com.camwrenn.rentaltool.repository.ToolRepository;
import com.camwrenn.rentaltool.service.RentalAgreementService;
import com.camwrenn.rentaltool.validation.RentalValidationException;
import com.camwrenn.rentaltool.validation.ValidatedInput;
import com.camwrenn.rentaltool.validation.ValidatedInputFactory;

import java.util.Optional;

public class RentalCommands {
    RentalAgreementService rentalAgreementService;
    ToolRepository toolRepository;

    public RentalCommands(RentalAgreementService rentalAgreementService, ToolRepository toolRepository) {
        this.rentalAgreementService = rentalAgreementService;
        this.toolRepository = toolRepository;
    }


    public String checkout(
            String code,
            String rentalDayCount,
            String discountPercent,
            String checkoutDate
    ) {

        RentalAgreement agreement;
        try {
            ValidatedInput input = ValidatedInputFactory.getValidatedInput(
                    code, rentalDayCount,discountPercent,checkoutDate);
            agreement = rentalAgreementService.createAgreement(
                    input.code, input.rentalDays, input.discountPercent, input.checkoutDate);
        } catch (RentalValidationException e) {
            return e.getMessage();
        }

        return agreement.toString();
    }

    public String list() {

        return toolRepository.getAllCodes().toString();
    }

    public String tool(String code) {
        Optional<Tool> tool = toolRepository.getByCode(code);
        return tool.isPresent() ? tool.get().toString() : "Tool not found";
    }

    public String help() {
        return """
                Rental Commands:
                  checkout: Create Rental Agreement with product code, length of rental in days, discount percent, and checkout date
                  list: List valid tool codes
                  tool: Get a single tool by code
                """;
    }
}
