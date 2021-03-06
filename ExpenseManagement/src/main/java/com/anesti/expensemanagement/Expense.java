package com.anesti.expensemanagement;

import java.time.LocalDateTime;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Expense {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @Embedded
    @AttributeOverrides(
        {
            @AttributeOverride(name = "currency", column = @Column(name = "original_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "original_amount"))
        }
    )
    private Money money;

    private LocalDateTime dateTime;
    private String category;
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Account account;

    @Column(nullable = true)
    @Embedded
    @AttributeOverrides(
        {
            @AttributeOverride(name = "currency", column = @Column(name = "converted_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "converted_amount"))
        }
    )
    private Money convertedMoney = null;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    // @VisibleForTesting
    public Expense(long id, String name, Money money, LocalDateTime dateTime, String category, String country) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.category = category;
        this.country = country;
        this.dateTime = dateTime;
    }

    // for Spring
    protected Expense() {
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public long getId() {
        return id;
    }

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
        return Optional.ofNullable(convertedMoney);
    }

    public void setConvertedMoney(Money convertedMoney) {
        this.convertedMoney = convertedMoney;
    }

    public Account getAccount() {
        return account;
    }

    public void deleteAccount() {
        this.account = null;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if ((o == null) || (getClass() != o.getClass()))
            return false;
        Expense expense = (Expense) o;
        return (id == expense.id) && Objects.equals(name, expense.name) && Objects.equals(money, expense.money) && Objects.equals(dateTime, expense.dateTime) && Objects.equals(category, expense.category) &&
            Objects.equals(country, expense.country) && Objects.equals(account, expense.account) && Objects.equals(convertedMoney, expense.convertedMoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, money, dateTime, category, country, account, convertedMoney);
    }
}
