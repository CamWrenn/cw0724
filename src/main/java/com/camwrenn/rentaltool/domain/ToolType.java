package com.camwrenn.rentaltool.domain;

public class ToolType {
    public ToolTypeCode code;
    public double dailyCharge;
    public boolean weekdayCharge;
    public boolean weekendCharge;
    public boolean holidayCharge;

    public ToolType(ToolTypeCode code,
                    double dailyCharge,
                    boolean weekdayCharge,
                    boolean weekendCharge,
                    boolean holidayCharge) {
        this.code = code;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public ToolTypeCode getCode() {
        return code;
    }

    @Override
    public String toString() {
        return """
                ToolType:
                  code: %s
                  dailyCharge: %s
                  weekdayCharge: %s
                  weekendCharge: %s
                  holidayCharge: %s
                """.formatted(code, dailyCharge, weekdayCharge, weekendCharge, holidayCharge);
    }
}
