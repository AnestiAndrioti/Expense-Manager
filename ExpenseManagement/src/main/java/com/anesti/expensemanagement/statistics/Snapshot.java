package com.anesti.expensemanagement.statistics;

import java.time.LocalDateTime;
import java.time.Month;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.anesti.expensemanagement.Account;
import com.anesti.expensemanagement.Expense;
import com.anesti.expensemanagement.util.ExpenseUtils;


public class Snapshot {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private final List<Expense> expenses;
    private final LocalDateTime creationDateTime;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Snapshot(Account account) {
        this.expenses = account.getExpenses();
        creationDateTime = LocalDateTime.now();
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public double computeSumOfExpensesOfMonth(int year, Month month) {
        return expenses.stream()
                .filter(expense -> ExpenseUtils.expenseIsOnSameMonthOfSameYear(expense, year, month))
                .mapToDouble(ExpenseUtils::getPriceWithAccountCurrency)
                .sum();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public Map<String, Double> computeRatiosOfCategoriesForExpensesOfMonth(int year, Month month) {
        var expensesOfTheMonth = expenses.stream()
                .filter(expense -> ExpenseUtils.expenseIsOnSameMonthOfSameYear(expense, year, month))
                .collect(Collectors.toList());

        Map<String, Double> categoryTotalMap = new HashMap<>();
        double totalSum = 0;

        for (var expense : expensesOfTheMonth) {
            var category = expense.getCategory();
            var expenseAmount = ExpenseUtils.getPriceWithAccountCurrency(expense);
            insertInCategoryMap(categoryTotalMap, category, expenseAmount);
            totalSum += expenseAmount;
        }

        Map<String, Double> categoryRatiosMap = new HashMap<>();
        for(var category : categoryTotalMap.keySet()) {
            categoryRatiosMap.put(category, categoryTotalMap.get(category) / totalSum);
        }
        return categoryRatiosMap;
    }

    public List<Expense> sortExpensesOfMonth(int year, Month month) {
        return expenses.stream()
                .filter(expense -> ExpenseUtils.expenseIsOnSameMonthOfSameYear(expense, year, month))
                .sorted(Comparator.comparingDouble(ExpenseUtils::getPriceWithAccountCurrency))
                .collect(Collectors.toList());
    }

    public LocalDateTime getSnapshotCreationDateTime() {
        return creationDateTime;
    }

    private void insertInCategoryMap(Map<String, Double> categoryTotalMap, String category, double expenseAmount) {
        if(categoryTotalMap.containsKey(category)) {
            categoryTotalMap.put(category, categoryTotalMap.get(category) + expenseAmount);
        } else {
            categoryTotalMap.put(category, expenseAmount);
        }
    }
}
