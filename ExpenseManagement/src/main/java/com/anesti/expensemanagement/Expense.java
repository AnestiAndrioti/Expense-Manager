package com.anesti.expensemanagement;

import java.time.LocalDateTime;

/**
 * Created by Anesti Andrioti on 22/03/2020.
 */
public class Expense {

    private final String name;
    private final Money amount;
    private final LocalDateTime dateTime;
    private final String type;
    private final String subType;
    private final String description;
    private final String country;

    private Expense(String name, Money amount, LocalDateTime dateTime, String type, String subType, String description, String country) {
        this.name = name;
        this.amount = amount;
        this.dateTime = dateTime;
        this.type = type;
        this.subType = subType;
        this.description = description;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getCountry() {
        return country;
    }

    public static class ExpenseBuilder {

        private final String name;
        private final Money amount;
        private final LocalDateTime dateTime;
        private String type;
        private String subType;
        private String description;
        private String country;

        public ExpenseBuilder(String name, Money amount) {
            this.name = name;
            this.amount = amount;
            this.dateTime = LocalDateTime.now();
        }

        public ExpenseBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public ExpenseBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ExpenseBuilder withSubType(String subType) {
            this.subType = subType;
            return this;
        }

        public ExpenseBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Expense build() {
            return new Expense(name, amount, dateTime, type, subType, description, country);
        }
    }
}
