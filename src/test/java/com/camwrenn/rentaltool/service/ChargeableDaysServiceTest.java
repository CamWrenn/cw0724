package com.camwrenn.rentaltool.service;

import com.camwrenn.rentaltool.domain.ToolType;
import com.camwrenn.rentaltool.domain.ToolTypeCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ChargeableDaysServiceTest {

    ChargeableDaysService service = new ChargeableDaysService(true);

    private static Stream<Arguments> getChargeableDays() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 7, 8),5,true,true,true,5),
                Arguments.of(LocalDate.of(2024, 7, 7),7,true,true,true,7),
                Arguments.of(LocalDate.of(2024, 7, 7),7,true,false,true,5),
                Arguments.of(LocalDate.of(2024, 7, 1),5,true,true,true,5),
                Arguments.of(LocalDate.of(2024, 7, 1),5,true,true,false,4),

                Arguments.of(LocalDate.of(2024, 7, 8),20,true,true,true,20),
                Arguments.of(LocalDate.of(2024, 7, 7),20,true,true,true,20),
                Arguments.of(LocalDate.of(2024, 7, 7),20,true,false,true,15),
                Arguments.of(LocalDate.of(2024, 7, 1),20,true,true,true,20),
                Arguments.of(LocalDate.of(2024, 7, 1),20,true,true,false,19),

                //July 4 2020 is Saturday
                Arguments.of(LocalDate.of(2020, 6, 29),5,true,true,true,5),
                Arguments.of(LocalDate.of(2020, 6, 28),7,true,true,true,7),
                Arguments.of(LocalDate.of(2020, 6, 29),5,true,true,false,4),
                Arguments.of(LocalDate.of(2020, 6, 28),7,true,true,false,5),

                //July 4 2021 is Sunday
                Arguments.of(LocalDate.of(2021, 7, 2),2,true,true,true,2),
                Arguments.of(LocalDate.of(2021, 7, 2),3,true,true,false,2),
                Arguments.of(LocalDate.of(2021, 7, 2),4,true,true,false,2),
                Arguments.of(LocalDate.of(2021, 7, 2),5,true,true,false,3),

                //September 2 2024 is Labor Day
                Arguments.of(LocalDate.of(2024, 9, 1),3,true,true,true,3),
                Arguments.of(LocalDate.of(2024, 9, 1),3,true,true,false,2),
                //September 4 2023 is Labor Day
                Arguments.of(LocalDate.of(2023, 9, 3),3,true,true,false,2),

                Arguments.of(LocalDate.of(2024, 7, 1),10,false,false,true,0)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void getChargeableDays(LocalDate checkoutDate, int rentalDayCount, boolean weekdayCharge,
                                                       boolean weekendCharge, boolean holidayCharge, int expected) {
        ToolType type = new ToolType(ToolTypeCode.JACKHAMMER, 0, weekdayCharge, weekendCharge, holidayCharge);

        assertEquals(expected, service.getChargeableDays(type, checkoutDate, rentalDayCount));
    }

    @ParameterizedTest
    @CsvSource({"13,12", "14,15", "12,12"})
    public void observedDate(int day, int expected) {
        LocalDate date1 = LocalDate.of(2024, 7, day);
        LocalDate date2 = service.getHolidayObserved(date1);

        assertEquals(expected, date2.getDayOfMonth());
    }

    @ParameterizedTest
    @CsvSource({
            "3,3,6,true,true,0", // Holiday after rental
            "3,3,2,true,true,0", // Holiday before rental
            "3,3,3,true,true,1", // Holiday day of rental
            "3,3,5,true,true,1", // Weekday free holiday in range
            "3,3,5,false,true,0",// Weekday charged holiday in range
            "3,4,6,true,true,1", // Saturday free holiday in range
            "3,4,6,true,false,0",// Saturday charged holiday in range
            "3,8,7,true,true,1", // Sunday free holiday in range
            "3,8,7,true,false,0" // Sunday charged holiday in range
    })
    public void getHolidayDiscount(int startDay, int rentalDayCount, int holidayDay, boolean weekdayCharge, boolean weekendCharge, int expected) {
        LocalDate dateStart = LocalDate.of(2024, 7, startDay);
        LocalDate holiday = LocalDate.of(2024, 7, holidayDay);

        ToolType type = new ToolType(ToolTypeCode.JACKHAMMER, 0, weekdayCharge, weekendCharge, false);

        assertEquals(expected, service.getHolidayDiscount(dateStart, rentalDayCount, holiday, type));
    }
}