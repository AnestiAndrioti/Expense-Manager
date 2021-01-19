package com.anesti.expensemanagement;

public enum Currency {
    USD("USD"), EUR("EUR"), JPY("JPY"), GBP("GBP"), CAD("CAD"), CHF("CHF"), LBP("LBP");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
