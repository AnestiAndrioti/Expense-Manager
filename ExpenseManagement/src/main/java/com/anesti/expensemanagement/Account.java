package com.anesti.expensemanagement;

import com.anesti.expensemanagement.currencyconverter.CurrencyConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Account {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private final long id;
    private final List<Expense> expenses;
    private final Currency currency;
    private final CurrencyConverter currencyConverter;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Account(long id, Currency currency) {
        this.id = id;
        this.currency = currency;
        this.currencyConverter = new CurrencyConverter(currency);
        expenses = new ArrayList<>();
    }

    public Account(long id, CurrencyConverter currencyConverter) {
        this.id = id;
        this.currencyConverter = currencyConverter;
        this.currency = currencyConverter.getToCurrency();
        expenses = new ArrayList<>();
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) throws IOException, InterruptedException {
        if (!currency.equals(expense.getMoney().getCurrency())) {
            expense.setConvertedMoney(currencyConverter.convertMoney(expense.getMoney()));
        }
        expenses.add(expense);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if ((o == null) || (getClass() != o.getClass()))
            return false;
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
