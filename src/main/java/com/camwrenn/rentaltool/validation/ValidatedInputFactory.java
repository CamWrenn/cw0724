package com.camwrenn.rentaltool.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/*
    There appears to be a bug in Spring Shell... I can't get messages from Java validation to display correctly.
    So this handles validation manually.
 */
public class ValidatedInputFactory {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yy");

    public static ValidatedInput getValidatedInput(String code, String rentalDayCount, String discountPercent,
                                            String checkoutDate)
            throws RentalValidationException {

        ValidatedInput input = new ValidatedInput();
        if (code == null || code.isBlank()) {
            throw new RentalValidationException("Tool code is required");
        }
        input.code = code;

        try {
            input.rentalDays = Integer.parseInt(rentalDayCount);
        } catch (NumberFormatException e) {
            throw new RentalValidationException("Rental Day Count is not a valid number");
        }
        if (input.rentalDays < 1) {
            throw new RentalValidationException("Rental Day Count must be 1 or greater");
        }

        try {
            input.discountPercent = Integer.parseInt(discountPercent);
        } catch (NumberFormatException e) {
            throw new RentalValidationException("Discount percent is not a valid number");
        }
        if (input.discountPercent < 0 || input.discountPercent > 100) {
            throw new RentalValidationException("Discount percent must be between 0 and 100");
        }

        try {
            input.checkoutDate = LocalDate.parse(checkoutDate, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new RentalValidationException(
                    "Check out Date is not a valid date. It should be in the format MM/DD/YY"
            );
        }

        return input;
    }
}