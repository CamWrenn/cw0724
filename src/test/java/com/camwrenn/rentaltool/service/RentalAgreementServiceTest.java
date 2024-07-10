package com.camwrenn.rentaltool.service;

import com.camwrenn.rentaltool.domain.RentalAgreement;
import com.camwrenn.rentaltool.domain.Tool;
import com.camwrenn.rentaltool.domain.ToolType;
import com.camwrenn.rentaltool.domain.ToolTypeCode;
import com.camwrenn.rentaltool.repository.ToolRepository;
import com.camwrenn.rentaltool.validation.RentalValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalAgreementServiceTest {

    @Mock
    ToolRepository toolRepository;

    @Mock
    ChargeableDaysService chargeableDaysService;

    @InjectMocks
    RentalAgreementService rentalAgreementService;

    Tool tool = new Tool("code",
            new ToolType(ToolTypeCode.JACKHAMMER, 1, true, true, true),
            "brand");

    @Test
    void createAgreement() throws RentalValidationException {
        when(toolRepository.getByCode("code")).thenReturn(Optional.ofNullable(tool));
        when(chargeableDaysService.getChargeableDays(tool.type, LocalDate.of(2024, 1, 2), 1)).thenReturn(1);

        RentalAgreement agreement = rentalAgreementService.createAgreement("code", 1, 0, LocalDate.of(2024, 1, 2));

        assertEquals("""
                Tool code: code
                Tool type: Jackhammer
                Tool brand: brand
                Rental days: 1
                Check out date: 01/02/24
                Due date: 01/03/24
                Daily Rental Charge: $1.00
                Charge days: 1
                Pre-discount charge: $1.00
                Discount percent: 0%
                Discount amount: $0.00
                __________________________
                Final charge: $1.00
                """, agreement.toString());
    }

    @Test
    void createAgreementToolNotFound()  {
        RentalValidationException e = assertThrows(
                RentalValidationException.class,
                () -> rentalAgreementService.createAgreement("code", 1, 0, LocalDate.of(2024, 1, 2))
        );

        assertEquals("Tool with code code not found", e.getMessage());
    }
}