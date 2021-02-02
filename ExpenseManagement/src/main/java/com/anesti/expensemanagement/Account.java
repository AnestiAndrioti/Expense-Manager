package com.anesti.expensemanagement;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Account {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Expense> expenses = Collections.synchronizedList(new ArrayList<>());

    private Currency currency;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------


    // Mainly used for Spring
    protected Account() {
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

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
        expense.setAccount(this);
    }

    synchronized void deleteExpense(Expense expense) {
        expenses.remove(expense);
        expense.deleteAccount();
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
