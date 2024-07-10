package com.camwrenn.rentaltool.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidatedInputFactoryTest {
    @Test
    public void validatedInput () throws RentalValidationException {
        ValidatedInput input = ValidatedInputFactory.getValidatedInput(
                "code", "1", "5", "1/2/24");

        assertEquals("code", input.code);
        assertEquals(1, input.rentalDays);
        assertEquals(5, input.discountPercent);
        assertEquals(LocalDate.of(2024, 1, 2), input.checkoutDate);
    }

    @Test
    public void nullCode() {
        RentalValidationException e = assertThrows(
                RentalValidationException.class,
                () -> ValidatedInputFactory.getValidatedInput(
                        null, "1", "5", "1/2/24")
        );

        assertEquals("Tool code is required", e.getMessage());
    }

    @Test
    public void emptyCode() {
        RentalValidationException e = assertThrows(
                RentalValidationException.class,
                () -> ValidatedInputFactory.getValidatedInput(
                        " ", "1", "5", "1/2/24")
        );

        assertEquals("Tool code is required", e.getMessage());
    }

    @Test
    public void dayCount() {
        RentalValidationException e = assertThrows(
                RentalValidationException.class,
                () -> ValidatedInputFactory.getValidatedInput(
                        "code", "0", "5", "1/2/24")
        );

        assertEquals("Rental Day Count must be 1 or greater", e.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"101", "-1"})
    public void discountPercent(String percent) {
        RentalValidationException e = assertThrows(
                RentalValidationException.class,
                () -> ValidatedInputFactory.getValidatedInput(
                        "code", "1", percent, "1/2/24")
        );

        assertEquals("Discount percent must be between 0 and 100", e.getMessage());
    }

    @Test
    public void discountPercent() {
        RentalValidationException e = assertThrows(
                RentalValidationException.class,
                () -> ValidatedInputFactory.getValidatedInput(
                        "code", "1", "percent", "1/2/24")
        );

        assertEquals("Discount percent is not a valid number", e.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"date", "2/3", "2-3-24"})
    public void checkoutDate(String date) {
        RentalValidationException e = assertThrows(
                RentalValidationException.class,
                () -> ValidatedInputFactory.getValidatedInput(
                        "code", "1", "0", date)
        );

        assertEquals("Check out Date is not a valid date. It should be in the format MM/DD/YY", e.getMessage());
    }
}