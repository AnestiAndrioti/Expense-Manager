package com.anesti.expensemanagement;

import java.time.LocalDateTime;

import java.util.Optional;


/**
 * Created by Anesti Andrioti on 22/03/2020.
 */
public class Expense {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private final String name;
    private final Money money;
    private final LocalDateTime dateTime;
    private final String category;
    private final String country;
    private Optional<Money> convertedMoney;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Expense(String name, Money money, String category, String country) {
        this.name = name;
        this.money = money;
        this.category = category;
        this.country = country;
        this.dateTime = LocalDateTime.now();
        this.convertedMoney = Optional.empty();
    }

    // Mainly used for testing to avoid randomness
    public Expense(String name, Money money, LocalDateTime dateTime, String category, String country) {
        this.name = name;
        this.money = money;
        this.category = category;
        this.country = country;
        this.dateTime = dateTime;
        this.convertedMoney = Optional.empty();
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Money getMoney() {
        return money;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getCountry() {
        return country;
    }

    public Optional<Money> getConvertedMoney() {
        return convertedMoney;
    }

    public void setConvertedMoney(Money convertedMoney) {
        this.convertedMoney = Optional.of(convertedMoney);
    }
}
