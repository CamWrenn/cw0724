package com.camwrenn.rentaltool.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalAgreement {
    String toolCode;
    String toolType;
    String toolBrand;
    double dailyCharge;
    int rentalDays;
    int discountPercent;
    LocalDate checkoutDate;
    LocalDate dueDate;
    int chargeDays;
    double preDiscountCharge;
    double discountAmount;
    double finalCharge;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

    public RentalAgreement(Tool tool, int rentalDays, int discountPercent, LocalDate checkoutDate, int chargeDays) {
        this.toolCode = tool.code;
        this.toolType = tool.type.code.display;
        this.toolBrand = tool.brand;
        this.dailyCharge = tool.type.dailyCharge;
        this.rentalDays = rentalDays;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;
        this.dueDate = checkoutDate.plusDays(rentalDays);
        this.chargeDays = chargeDays;
        this.preDiscountCharge = round(tool.type.dailyCharge * chargeDays);
        this.discountAmount = round(this.discountPercent / 100d * this.preDiscountCharge);
        this.finalCharge = this.preDiscountCharge - this.discountAmount;
    }

    public String toString() {
        return """
                Tool code: %s
                Tool type: %s
                Tool brand: %s
                Rental days: %s
                Check out date: %s
                Due date: %s
                Daily Rental Charge: $%.2f
                Charge days: %s
                Pre-discount charge: $%.2f
                Discount percent: %s%%
                Discount amount: $%.2f
                __________________________
                Final charge: $%.2f
                """.formatted(toolCode, toolType, toolBrand, this.rentalDays,
                dateFormatter.format(checkoutDate), dateFormatter.format(dueDate), dailyCharge,
                chargeDays, preDiscountCharge, discountPercent, discountAmount, this.finalCharge);
    }

    public static double round(double value) {
        BigDecimal decimal = BigDecimal.valueOf(value);
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);
        return decimal.doubleValue();
    }
}