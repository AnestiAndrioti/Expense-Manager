package com.anesti.expensemanagement;

import com.anesti.expensemanagement.currencyconverter.CurrencyConverter;

import java.io.IOException;
import java.util.List;

public class ExpenseManager {

        public static void addExpenseToAccount(Account account, Expense expense) throws IOException, InterruptedException {
        if (!account.getCurrency().equals(expense.getMoney().getCurrency())) {
            expense.setConvertedMoney(CurrencyConverter.convertMoney(expense.getMoney(), account.getCurrency()));
        }
        account.addExpense(expense);
    }

    public static List<Expense> getExpensesFromAccount(Account account) {
        return account.getExpenses();
    }
}
