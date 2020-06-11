package com.anesti.expensemanagement;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTest {

    @Test
    void canAddExpenseOfSameCurrency() throws IOException, InterruptedException {
        var spotifyExpense = new Expense.ExpenseBuilder("Spotify", new Money(Currency.USD, 5.0))
                .build();

        var zwiftExpense = new Expense.ExpenseBuilder("Zwift", new Money(Currency.USD, 15.0))
                .withCountry("USA")
                .withDescription("Monthly Subscription Rate to Zwift")
                .withType("Sports")
                .withSubType("Indoor Biking")
                .build();

        Account account = new Account(1, Currency.USD);
        account.addExpense(zwiftExpense);
        account.addExpense(spotifyExpense);

        var accountExpenses = account.getExpenses();
        assertEquals(2, accountExpenses.size());

        assertTrue(accountExpenses.contains(spotifyExpense));
        assertTrue(accountExpenses.contains(zwiftExpense));

        for(var expense : accountExpenses) {
            assertEquals(Currency.USD, expense.getMoney().getCurrency());
            assertTrue(expense.getConvertedMoney().isEmpty());
        }
    }

    @Test
    void canAddExpenseOfDifferentCurrencies() throws IOException, InterruptedException {
        var spotifyExpense = new Expense.ExpenseBuilder("Spotify", new Money(Currency.USD, 5.0))
                .build();

        var tvExpense = new Expense.ExpenseBuilder("tv", new Money(Currency.EUR, 15.0))
                .build();

        assertTrue(tvExpense.getConvertedMoney().isEmpty());

        Account account = new Account(1, Currency.USD);
        account.addExpense(tvExpense);
        account.addExpense(spotifyExpense);

        assertTrue(tvExpense.getConvertedMoney().isPresent());

        var accountExpenses = account.getExpenses();
        assertEquals(2, accountExpenses.size());

        assertTrue(accountExpenses.contains(spotifyExpense));
        assertTrue(accountExpenses.contains(tvExpense));

        var tvExpenseFromAccount = getExpenseWithName(accountExpenses, "tv");
        assertEquals(Currency.EUR, tvExpense.getMoney().getCurrency());
        assertEquals(Currency.USD, tvExpense.getConvertedMoney().get().getCurrency());

        var spotifyExpenseFromAccount = getExpenseWithName(accountExpenses, "Spotify");
        assertEquals(Currency.USD, spotifyExpenseFromAccount.getMoney().getCurrency());
        assertTrue(spotifyExpenseFromAccount.getConvertedMoney().isEmpty());
    }

    private Expense getExpenseWithName(List<Expense> expenses, String name) {
        var expenseWithName = expenses
                .stream()
                .filter(expense -> name.equals(expense.getName()))
                .findFirst();

        if(expenseWithName.isPresent()) {
            return expenseWithName.get();
        }
        else {
            throw new IllegalArgumentException("Expense with name not found in List.");
        }
    }
}
