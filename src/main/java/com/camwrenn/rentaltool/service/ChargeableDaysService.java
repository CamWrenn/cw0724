package com.camwrenn.rentaltool.service;

import com.camwrenn.rentaltool.domain.ToolType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.IntStream;

public class ChargeableDaysService {

    boolean doubleHoliday;

    public ChargeableDaysService(boolean doubleHoliday) {
        this.doubleHoliday = doubleHoliday;
    }

    public int getChargeableDays(ToolType type, LocalDate checkoutDate, int rentalDayCount) {
        int fullWeeks = rentalDayCount / 7;

        int dayOfWeekValue = checkoutDate.getDayOfWeek().getValue();
        int remainingWeekendDays = (int) IntStream.range(dayOfWeekValue, dayOfWeekValue + rentalDayCount % 7)
                .filter(day -> (day % 7) == 6 || (day % 7) == 0)
                .count();
        int remainingWeekdays = Math.max(rentalDayCount % 7 - remainingWeekendDays, 0);

        int chargableDays = 0;
        if (type.weekdayCharge) {
            chargableDays += fullWeeks * 5 + remainingWeekdays;
        }
        if (type.weekendCharge) {
            chargableDays += fullWeeks * 2 + remainingWeekendDays;
        }
        if (!type.holidayCharge) {
            LocalDate independenceDay = LocalDate.of(checkoutDate.getYear(), 7, 4);
            LocalDate independenceDayObserved =  getHolidayObserved(independenceDay);

            chargableDays -= getHolidayDiscount(checkoutDate, rentalDayCount, independenceDayObserved, type);
            if (doubleHoliday && independenceDay != independenceDayObserved) {
                chargableDays -= getHolidayDiscount(checkoutDate, rentalDayCount, independenceDay, type);
            }
            chargableDays -= getHolidayDiscount(checkoutDate, rentalDayCount, getLaborDay(checkoutDate), type);
        }

        return chargableDays;
    }

    public LocalDate getHolidayObserved(LocalDate holiday) {
        LocalDate observed;
        if (holiday.getDayOfWeek() == DayOfWeek.SATURDAY) {
            observed = holiday.minusDays(1);
        } else if (holiday.getDayOfWeek() == DayOfWeek.SUNDAY) {
            observed = holiday.plusDays(1);
        } else {
            observed = holiday;
        }

        return observed;
    }

    public LocalDate getLaborDay(LocalDate checkoutDate) {
        LocalDate firstOfSeptember = LocalDate.of(checkoutDate.getYear(), 9, 1);

        return firstOfSeptember.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    }

    public int getHolidayDiscount(LocalDate dateStart, int rentalDayCount, LocalDate holiday, ToolType type) {
        LocalDate dateEnd = dateStart.plusDays(rentalDayCount);
        if (holiday.isAfter(dateStart.minusDays(1)) && holiday.isBefore(dateEnd)) {
            DayOfWeek day = holiday.getDayOfWeek();
            boolean isWeekend = day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
            if ((type.weekendCharge && isWeekend) || (type.weekdayCharge && !isWeekend)) {
                return 1;
            }
        }

        return 0;
    }
}
