package com.anesti.expensemanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {
    private final long id;
    private final List<Expense> expenses;

    public Account(long id) {
        this.id = id;
        this.expenses = new ArrayList<Expense>();
    }

    public Account(long id, List<Expense> expenses) {
        this.id = id;
        this.expenses = new ArrayList<Expense>(expenses);
    }

    public long getId() {
        return id;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
