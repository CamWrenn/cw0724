package com.camwrenn.rentaltool.service;

import com.camwrenn.rentaltool.domain.RentalAgreement;
import com.camwrenn.rentaltool.domain.Tool;
import com.camwrenn.rentaltool.repository.ToolRepository;
import com.camwrenn.rentaltool.validation.RentalValidationException;

import java.time.LocalDate;

public class RentalAgreementService {
    ToolRepository toolRepository;
    ChargeableDaysService chargeableDaysService;

    public RentalAgreementService(ToolRepository toolRepository, ChargeableDaysService chargeableDaysService) {
        this.toolRepository = toolRepository;
        this.chargeableDaysService = chargeableDaysService;
    }

    public RentalAgreement createAgreement(String toolCode,
                                           int rentalDayCount,
                                           int discountPercent,
                                           LocalDate checkoutDate)
            throws RentalValidationException {

        Tool tool = toolRepository.getByCode(toolCode)
                .orElseThrow(() -> new RentalValidationException(
                        String.format("Tool with code %s not found", toolCode))
                );

        int chargeDays = chargeableDaysService.getChargeableDays(tool.type, checkoutDate, rentalDayCount);

        return new RentalAgreement(tool, rentalDayCount, discountPercent, checkoutDate, chargeDays);
    }
}
