package com.anesti.expensemanagement.restapi.stats;

import java.time.Month;

import java.util.List;
import java.util.Map;

import com.anesti.expensemanagement.Account;
import com.anesti.expensemanagement.Expense;
import com.anesti.expensemanagement.restapi.account.AccountService;
import com.anesti.expensemanagement.statistics.StatisticsComputer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
class StatisticsService {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Autowired
    private AccountService accountService;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    double getSumOfExpensesOfMonth(long accountId, int year, Month month) {
        Account accountFromRepository = accountService.getAccountFromRepository(accountId);
        return StatisticsComputer.computeSumOfExpensesOfMonth(accountFromRepository, year, month);
    }

    Map<String, Double> getRatiosOfCategoriesForExpensesOfMonth(long accountId, int year, Month month) {
        Account accountFromRepository = accountService.getAccountFromRepository(accountId);
        return StatisticsComputer.computeRatiosOfCategoriesForExpensesOfMonth(accountFromRepository, year, month);
    }

    List<Expense> getSortedExpensesOfMonthDescending(long accountId, int year, Month month) {
        Account accountFromRepository = accountService.getAccountFromRepository(accountId);
        return StatisticsComputer.sortExpensesOfMonthDescending(accountFromRepository, year, month);
    }
}
