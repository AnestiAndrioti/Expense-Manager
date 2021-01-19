package com.anesti.expensemanagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Account {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private final long id;
    private final List<Expense> expenses = Collections.synchronizedList(new ArrayList<>());
    private final Currency currency;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Account(long id, Currency currency) {
        this.id = id;
        this.currency = currency;
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public synchronized List<Expense> getExpenses() {
        return expenses;
    }

    synchronized void addExpense(Expense expense) {
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
