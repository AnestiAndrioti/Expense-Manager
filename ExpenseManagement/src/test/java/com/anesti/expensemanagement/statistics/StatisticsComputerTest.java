package com.anesti.expensemanagement.statistics;

import java.io.IOException;

import java.time.LocalDateTime;

import java.util.List;

import com.anesti.expensemanagement.Account;
import com.anesti.expensemanagement.Currency;
import com.anesti.expensemanagement.Expense;
import com.anesti.expensemanagement.ExpenseManager;
import com.anesti.expensemanagement.Money;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class StatisticsComputerTest {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final String SPORT = "Sport";
    private static final String MEDICAL = "Medical";
    private static final String TAXES = "Taxes";
    private static final String LEISURE = "Leisure";
    private static final String LEBANON = "Lebanon";

    private static final Account ACCOUNT = new Account(1, Currency.USD);
    private static final LocalDateTime REF_DATE_TIME = LocalDateTime.of(2020, 11, 17, 8, 0);

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @BeforeAll
    static void setup() throws IOException, InterruptedException {

        for (int i = 1; i <= 3; ++i) {
            var expenseSport = new Expense(i * 10, "expense-USD-sport-" + i, new Money(Currency.USD, 5.0), REF_DATE_TIME, SPORT, LEBANON);
            var expenseMedical = new Expense((i * 10) + 1, "expense-EUR-medical-" + i, new Money(Currency.USD, 5.0 + 1), REF_DATE_TIME, MEDICAL, LEBANON);
            var expenseTaxes = new Expense((i * 10) + 2, "expense-USD-taxes-" + i, new Money(Currency.USD, 5.0 + 2), REF_DATE_TIME, TAXES, LEBANON);
            var expenseLeisure = new Expense((i * 10) + 3, "expense-EUR-leisure-" + i, new Money(Currency.USD, 5.0 + i), REF_DATE_TIME, LEISURE, LEBANON);

            ExpenseManager.addExpenseToAccount(ACCOUNT, expenseSport);
            ExpenseManager.addExpenseToAccount(ACCOUNT, expenseMedical);
            ExpenseManager.addExpenseToAccount(ACCOUNT, expenseTaxes);
            ExpenseManager.addExpenseToAccount(ACCOUNT, expenseLeisure);
        }
    }

    @Test
    void computeSumOfExpensesOfMonth() {
        var totalExpensesOfMonth = StatisticsComputer.computeSumOfExpensesOfMonth(ACCOUNT, REF_DATE_TIME.getYear(), REF_DATE_TIME.getMonth());
        assertEquals(75.00, totalExpensesOfMonth, 0.01);
    }

    @Test
    void computeRatiosOfCategoriesForExpensesOfMonth() {
        var mapOfCategoriesRatios = StatisticsComputer.computeRatiosOfCategoriesForExpensesOfMonth(ACCOUNT, REF_DATE_TIME.getYear(), REF_DATE_TIME.getMonth());

        assertEquals(4, mapOfCategoriesRatios.size());

        assertEquals(0.2, mapOfCategoriesRatios.get(SPORT), 0.001);
        assertEquals(0.28, mapOfCategoriesRatios.get(TAXES), 0.001);
        assertEquals(0.28, mapOfCategoriesRatios.get(LEISURE), 0.001);
        assertEquals(0.24, mapOfCategoriesRatios.get(MEDICAL), 0.001);

        var sumOfRatios = (Double) mapOfCategoriesRatios.values().stream().mapToDouble(Double::doubleValue).sum();

        assertEquals(1.0, sumOfRatios, 0.001);
    }

    @Test
    void sortExpensesOfMonthDescending() {
        var expenses = StatisticsComputer.sortExpensesOfMonthDescending(ACCOUNT, REF_DATE_TIME.getYear(), REF_DATE_TIME.getMonth());
        assertTrue(isSorted(expenses));
    }

    @Test
    void getPriceWithAccountCurrency() throws IOException, InterruptedException {
        Account accountInUSD = new Account(1, Currency.USD);

        var expenseUSD = new Expense(1, "expense-USD", new Money(Currency.USD, 5.0), REF_DATE_TIME, SPORT, LEBANON);
        var expenseEUR = new Expense(2, "expense-EUR", new Money(Currency.EUR, 5.0), REF_DATE_TIME, MEDICAL, LEBANON);

        assertEquals(expenseUSD.getMoney(), StatisticsComputer.getMoneyWithAccountCurrency(expenseUSD));

        assertTrue(expenseEUR.getConvertedMoney().isEmpty());
        assertEquals(expenseEUR.getMoney(), StatisticsComputer.getMoneyWithAccountCurrency(expenseEUR));

        ExpenseManager.addExpenseToAccount(accountInUSD, expenseUSD);
        ExpenseManager.addExpenseToAccount(accountInUSD, expenseEUR);

        assertTrue(expenseUSD.getConvertedMoney().isEmpty());
        assertEquals(expenseUSD.getMoney(), StatisticsComputer.getMoneyWithAccountCurrency(expenseUSD));

        assertTrue(expenseEUR.getConvertedMoney().isPresent());
        assertEquals(expenseEUR.getConvertedMoney().get(), StatisticsComputer.getMoneyWithAccountCurrency(expenseEUR));
    }

    @Test
    void expenseIsOnSameMonthOfSameYear() {
        Money money = new Money(Currency.USD, 5.0);

        LocalDateTime ref_time_jan_01_2021 = LocalDateTime.of(2021, 1, 1, 8, 0);

        LocalDateTime jan_31_2021 = LocalDateTime.of(2021, 1, 31, 8, 0);
        LocalDateTime feb_01_2021 = LocalDateTime.of(2021, 2, 1, 8, 0);
        LocalDateTime jan_31_2022 = LocalDateTime.of(2022, 1, 31, 8, 0);

        var expenseMedical = new Expense(1, "expense-Jan-31-2021", money, jan_31_2021, MEDICAL, LEBANON);
        var expenseTaxes = new Expense(2, "expense-Feb-01-2021", money, feb_01_2021, TAXES, LEBANON);
        var expenseLeisure = new Expense(3, "expense-Jan-31-2021", money, jan_31_2022, LEISURE, LEBANON);

        assertTrue(StatisticsComputer.expenseIsOnSameMonthOfSameYear(expenseMedical, ref_time_jan_01_2021.getYear(), ref_time_jan_01_2021.getMonth()));

        assertFalse(StatisticsComputer.expenseIsOnSameMonthOfSameYear(expenseTaxes, ref_time_jan_01_2021.getYear(), ref_time_jan_01_2021.getMonth()));
        assertFalse(StatisticsComputer.expenseIsOnSameMonthOfSameYear(expenseLeisure, ref_time_jan_01_2021.getYear(), ref_time_jan_01_2021.getMonth()));

    }

    private boolean isSorted(List<Expense> expenses) {
        if (expenses.isEmpty()) {
            return true;
        }

        var expenseIterator = expenses.iterator();
        Expense previousExpense = expenseIterator.next();
        while (expenseIterator.hasNext()) {
            Expense currentExpense = expenseIterator.next();
            if (StatisticsComputer.getMoneyWithAccountCurrency(previousExpense).getAmount() < StatisticsComputer.getMoneyWithAccountCurrency(currentExpense).getAmount()) {
                return false;
            }
            previousExpense = currentExpense;
        }
        return true;
    }
}
