package com.camwrenn.rentaltool.domain;

public class Tool {

    public String code;
    public ToolType type;
    public String brand;

    public Tool(String code, ToolType type, String brand) {
        this.code = code;
        this.type = type;
        this.brand = brand;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return """
                Tool:
                  type: %s
                  brand: %s
                  type:
                    %s
                """.formatted(code, brand, type);
    }
}
