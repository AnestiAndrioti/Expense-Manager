package com.anesti.expensemanagement;

import java.util.Objects;

/**
 * Created by Anesti Andrioti on 22/03/2020.
 */
public class Money {

    private final Currency currency;
    private final double amount;

    public Money(Currency currency, double amount) {
        this.amount = checkAmount(amount);
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(money.amount, amount) == 0 &&
                currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    private double checkAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount cannot be smaller or equal to 0. Entered amount is " + amount);
        }
        return amount;
    }

    @Override
    public String toString() {
        return "Money: " + this.amount + " " + this.getCurrency().toString();
    }
}
