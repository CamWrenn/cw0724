package com.camwrenn.rentaltool.domain;

public enum ToolTypeCode {
    CHAINSAW("Chainsaw"),
    LADDER("Ladder"),
    JACKHAMMER("Jackhammer");

    public final String display;

    ToolTypeCode(String display) {
        this.display = display;
    }
}
