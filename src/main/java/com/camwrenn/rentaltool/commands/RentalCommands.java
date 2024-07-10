package com.camwrenn.rentaltool.commands;

import com.camwrenn.rentaltool.domain.RentalAgreement;
import com.camwrenn.rentaltool.domain.Tool;
import com.camwrenn.rentaltool.repository.ToolRepository;
import com.camwrenn.rentaltool.service.RentalAgreementService;
import com.camwrenn.rentaltool.validation.RentalValidationException;
import com.camwrenn.rentaltool.validation.ValidatedInput;
import com.camwrenn.rentaltool.validation.ValidatedInputFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Optional;

@ShellComponent
public class RentalCommands {
    RentalAgreementService rentalAgreementService;
    ToolRepository toolRepository;

    public RentalCommands(RentalAgreementService rentalAgreementService, ToolRepository toolRepository) {
        this.rentalAgreementService = rentalAgreementService;
        this.toolRepository = toolRepository;
    }

    @ShellMethod(
            "Create Rental Agreement with product code, length of rental in days, discount percent, and checkout date")
    public String checkout(
            @ShellOption(help="Valid code for a tool") String code,
            @ShellOption(help="Integer above 0") String rentalDayCount,
            @ShellOption(help="Integer between 0 and 100") String discountPercent,
            @ShellOption(help="String with the format MM/DD/YY") String checkoutDate
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

    @ShellMethod("List valid tool codes")
    public String list() {

        return toolRepository.getAllCodes().toString();
    }

    @ShellMethod("Get a single tool by code")
    public String tool(String code) {
        Optional<Tool> tool = toolRepository.getByCode(code);
        return tool.isPresent() ? tool.get().toString() : "Tool not found";
    }
}
