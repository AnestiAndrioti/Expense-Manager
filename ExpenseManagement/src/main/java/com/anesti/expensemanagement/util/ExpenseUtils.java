package com.anesti.expensemanagement.util;

import com.anesti.expensemanagement.Expense;

import java.time.Month;


public class ExpenseUtils {

    public static double getPriceWithAccountCurrency(Expense expense) {
        if(expense.getConvertedMoney().isPresent()) {
            return expense.getConvertedMoney().get().getAmount();
        }
        return expense.getMoney().getAmount();
    }

    public static boolean expenseIsOnSameMonthOfSameYear(Expense expense, int year, Month month) {
        if(expense.getDateTime().getYear() != year) {
            return false;
        }
        return expense.getDateTime().getMonth().equals(month);
    }
}
