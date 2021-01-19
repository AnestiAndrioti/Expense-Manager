package com.anesti.expensemanagement;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTest {

    @Test
    void canAddExpenseOfSameCurrency() throws IOException, InterruptedException {
        var spotifyExpense = new Expense("Spotify", new Money(Currency.USD, 5.0), "Music", "Lebanon");
        var zwiftExpense = new Expense("Zwift", new Money(Currency.USD, 15.0), "Sport", "USA");

        Account account = new Account(1, Currency.USD);

        ExpenseManager.addExpenseToAccount(account, zwiftExpense);
        ExpenseManager.addExpenseToAccount(account, spotifyExpense);

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
        var spotifyExpense = new Expense("Spotify", new Money(Currency.USD, 5.0), "Music", "Lebanon");
        var tvExpense = new Expense("tv", new Money(Currency.EUR, 15.0), "Entertainment", "Greece");

        assertTrue(tvExpense.getConvertedMoney().isEmpty());

        Account account = new Account(1, Currency.USD);

        ExpenseManager.addExpenseToAccount(account, tvExpense);
        ExpenseManager.addExpenseToAccount(account, spotifyExpense);

        assertTrue(tvExpense.getConvertedMoney().isPresent());

        var accountExpenses = account.getExpenses();
        assertEquals(2, accountExpenses.size());

        assertTrue(accountExpenses.contains(spotifyExpense));
        assertTrue(accountExpenses.contains(tvExpense));

        var tvExpenseFromAccount = getExpenseWithName(accountExpenses, "tv");
        assertEquals(Currency.EUR, tvExpenseFromAccount.getMoney().getCurrency());
        assertTrue(tvExpenseFromAccount.getConvertedMoney().isPresent());
        assertEquals(Currency.USD, tvExpenseFromAccount.getConvertedMoney().get().getCurrency());

        var spotifyExpenseFromAccount = getExpenseWithName(accountExpenses, "Spotify");
        assertEquals(Currency.USD, spotifyExpenseFromAccount.getMoney().getCurrency());
        assertTrue(spotifyExpenseFromAccount.getConvertedMoney().isEmpty());
    }

    private Expense getExpenseWithName(List<Expense> expenses, String name) {
        return expenses
                .stream()
                .filter(expense -> name.equals(expense.getName()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
