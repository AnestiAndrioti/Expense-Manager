package com.anesti.expensemanagement;

import java.util.Objects;

import javax.persistence.Embeddable;


@Embeddable
public class Money {

    private Currency currency;
    private double amount;
    
    public Money(Currency currency, double amount) {
        this.amount = checkAmount(amount);
        this.currency = currency;
    }

    // For Spring
    protected Money() {
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if ((o == null) || (getClass() != o.getClass()))
            return false;
        Money money = (Money) o;
        return (Double.compare(money.amount, amount) == 0) && (currency == money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return "Money: " + this.amount + " " + this.getCurrency().getCode();
    }

    private double checkAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount cannot be smaller or equal to 0. Entered amount is " + amount);
        }
        return amount;
    }
}
