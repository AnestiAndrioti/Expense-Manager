package com.anesti.expensemanagement.statistics;

import com.anesti.expensemanagement.Account;
import com.anesti.expensemanagement.Expense;
import com.anesti.expensemanagement.ExpenseManager;
import com.anesti.expensemanagement.Money;

import java.time.Month;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsComputer {

    public static double computeSumOfExpensesOfMonth(Account account, int year, Month month) {
        return ExpenseManager.getExpensesFromAccount(account).stream()
                .filter(expense -> expenseIsOnSameMonthOfSameYear(expense, year, month))
                .map(StatisticsComputer::getMoneyWithAccountCurrency)
                .mapToDouble(Money::getAmount)
                .sum();
    }

    public static Map<String, Double> computeRatiosOfCategoriesForExpensesOfMonth(Account account, int year, Month month) {
        var expensesOfTheMonth = ExpenseManager.getExpensesFromAccount(account).stream()
                .filter(expense -> expenseIsOnSameMonthOfSameYear(expense, year, month))
                .collect(Collectors.toList());

        Map<String, Double> categoryTotalMap = new HashMap<>();
        double totalSum = 0.0;

        for (var expense : expensesOfTheMonth) {
            var category = expense.getCategory();
            var expenseAmount = getMoneyWithAccountCurrency(expense).getAmount();
            insertInCategoryMap(categoryTotalMap, category, expenseAmount);
            totalSum += expenseAmount;
        }

        Map<String, Double> categoryRatiosMap = new HashMap<>();
        for(var category : categoryTotalMap.keySet()) {
            categoryRatiosMap.put(category, categoryTotalMap.get(category) / totalSum);
        }
        return categoryRatiosMap;
    }

    public static List<Expense> sortExpensesOfMonthDescending(Account account, int year, Month month) {
        return ExpenseManager.getExpensesFromAccount(account).stream()
                .filter(expense -> expenseIsOnSameMonthOfSameYear(expense, year, month))
                .sorted(Comparator.comparingDouble((Expense expense) ->
                        getMoneyWithAccountCurrency(expense).getAmount()
                ).reversed())
                .collect(Collectors.toList());
    }

    // @VisibleForTesting
    static Money getMoneyWithAccountCurrency(Expense expense) {
        if(expense.getConvertedMoney().isPresent()) {
            return expense.getConvertedMoney().get();
        }
        return expense.getMoney();
    }

    // @VisibleForTesting
    static boolean expenseIsOnSameMonthOfSameYear(Expense expense, int year, Month month) {
        if(expense.getDateTime().getYear() != year) {
            return false;
        }
        return expense.getDateTime().getMonth().equals(month);
    }

    private static void insertInCategoryMap(Map<String, Double> categoryTotalMap, String category, double expenseAmount) {
        if(categoryTotalMap.containsKey(category)) {
            categoryTotalMap.put(category, categoryTotalMap.get(category) + expenseAmount);
        } else {
            categoryTotalMap.put(category, expenseAmount);
        }
    }
}
