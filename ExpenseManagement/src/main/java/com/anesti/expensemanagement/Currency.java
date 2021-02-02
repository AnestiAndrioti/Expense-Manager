package com.anesti.expensemanagement;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Currency {
    USD("USD"), EUR("EUR"), JPY("JPY"), GBP("GBP"), CAD("CAD"), CHF("CHF"), LBP("LBP");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
