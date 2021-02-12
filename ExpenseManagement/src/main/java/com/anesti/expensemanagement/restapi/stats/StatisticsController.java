package com.anesti.expensemanagement.restapi.stats;

import com.anesti.expensemanagement.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.List;
import java.util.Map;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping("/accounts/{accountId}/stats/sumOfExpenses/{year}/{month}")
    public double getSumOfExpensesOfMonth(@PathVariable long accountId, @PathVariable int year, @PathVariable Month month) {
        return statisticsService.getSumOfExpensesOfMonth(accountId, year, month);
    }

    @RequestMapping("/accounts/{accountId}/stats/ratiosOfCategories/{year}/{month}")
    public Map<String, Double> getRatiosOfCategoriesForExpensesOfMonth(@PathVariable long accountId, @PathVariable int year, @PathVariable Month month) {
        return statisticsService.getRatiosOfCategoriesForExpensesOfMonth(accountId, year, month);
    }

    @RequestMapping("/accounts/{accountId}/stats/sortedExpenses/{year}/{month}")
    public List<Expense> getSortedExpensesOfMonthDescending(@PathVariable long accountId, @PathVariable int year, @PathVariable Month month) {
        return statisticsService.getSortedExpensesOfMonthDescending(accountId, year, month);
    }
}
