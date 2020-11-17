package com.anesti.expensemanagement.statistics;

import java.io.IOException;

import java.time.LocalDateTime;

import java.util.List;

import com.anesti.expensemanagement.Account;
import com.anesti.expensemanagement.Currency;
import com.anesti.expensemanagement.Expense;
import com.anesti.expensemanagement.Money;
import com.anesti.expensemanagement.currencyconverter.CurrencyConverter;
import com.anesti.expensemanagement.util.ExpenseUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;


class SnapshotTest {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static Account account;
    private static LocalDateTime refDateTime = LocalDateTime.of(2020, 11, 17, 8, 0);

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @BeforeAll
    static void setup() throws IOException, InterruptedException {
        CurrencyConverter currencyConverterSpy = Mockito.spy(new CurrencyConverter(Currency.USD));
        Mockito.doAnswer((Answer<Money>) invocationOnMock -> {
                Money originalMoney = (Money) invocationOnMock.getArguments()[0];
                return new Money(currencyConverterSpy.getToCurrency(), originalMoney.getAmount() * 1.19);
            }).when(currencyConverterSpy).convertMoney(any());

        account = new Account(1, currencyConverterSpy);

        for (int i = 1; i <= 3; ++i) {
            var expenseUSDSport = new Expense.ExpenseBuilder("expense-USD-sport-" + i, new Money(Currency.USD, 5.0 + i), refDateTime).withType("Sport").build();
            var expenseEURMedical = new Expense.ExpenseBuilder("expense-EUR-medical-" + i, new Money(Currency.EUR, 5.0 + i), refDateTime).withType("Medical").build();
            var expenseUSDTaxes = new Expense.ExpenseBuilder("expense-USD-taxes-" + i, new Money(Currency.USD, 2.0 + i), refDateTime).withType("Taxes").build();
            var expenseEURLeisure = new Expense.ExpenseBuilder("expense-EUR-leisure-" + i, new Money(Currency.EUR, 8.0 + i), refDateTime).withType("Leisure").build();
            account.addExpense(expenseUSDSport);
            account.addExpense(expenseEURMedical);
            account.addExpense(expenseUSDTaxes);
            account.addExpense(expenseEURLeisure);
        }
    }

    @Test
    void computeSumOfExpensesOfMonth() {
        Snapshot snapshot = new Snapshot(account);
        var totalExpensesOfMonth = snapshot.computeSumOfExpensesOfMonth(refDateTime.getYear(), refDateTime.getMonth());
        assertEquals(93.69, totalExpensesOfMonth);
    }

    @Test
    void computeRatiosOfCategoriesForExpensesOfMonth() {
        Snapshot snapshot = new Snapshot(account);
        var mapOfCategoriesRatios = snapshot.computeRatiosOfCategoriesForExpensesOfMonth(refDateTime.getYear(), refDateTime.getMonth());

        assertEquals(4, mapOfCategoriesRatios.size());

        assertEquals(0.224, mapOfCategoriesRatios.get("Sport"), 0.001);
        assertEquals(0.128, mapOfCategoriesRatios.get("Taxes"), 0.001);
        assertEquals(0.381, mapOfCategoriesRatios.get("Leisure"), 0.001);
        assertEquals(0.2667, mapOfCategoriesRatios.get("Medical"), 0.001);

        var sumOfRatios = (Double) mapOfCategoriesRatios.values().stream().mapToDouble(Double::doubleValue).sum();

        assertEquals(1.0, sumOfRatios, 0.001);
    }

    @Test
    void sortExpensesOfMonth() {
        Snapshot snapshot = new Snapshot(account);
        var expenses = snapshot.sortExpensesOfMonth(refDateTime.getYear(), refDateTime.getMonth());
        assertTrue(isSorted(expenses));
    }

    private boolean isSorted(List<Expense> expenses) {
        var expenseIterator = expenses.iterator();
        if (!expenseIterator.hasNext()) {
            return true;
        }
        Expense expense = expenseIterator.next();
        while (expenseIterator.hasNext()) {
            Expense expense2 = expenseIterator.next();
            if (ExpenseUtils.getPriceWithAccountCurrency(expense) > ExpenseUtils.getPriceWithAccountCurrency(expense2)) {
                return false;
            }
            expense = expense2;
        }
        return true;
    }
}
