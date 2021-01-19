package com.anesti.expensemanagement.currencyconverter;

import com.anesti.expensemanagement.Currency;
import com.anesti.expensemanagement.Money;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CurrencyConverterTest {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    void convertToSameCurrencyYieldsSameAmount() throws IOException, InterruptedException {
        Money originalMoney = new Money(Currency.LBP, 100000);
        assertEquals(originalMoney, CurrencyConverter.convertMoney(originalMoney, Currency.LBP));
    }

    @Test
    void convertMoneyKeepsOriginal() throws IOException, InterruptedException {
        final double originalAmount = 10;
        Money originalMoney = new Money(Currency.USD, originalAmount);
        Money resultMoney = CurrencyConverter.convertMoney(originalMoney, Currency.LBP);

        assertEquals(Currency.LBP, resultMoney.getCurrency());
        assertEquals(Currency.USD, originalMoney.getCurrency());
        assertEquals(originalAmount, originalMoney.getAmount(), 0.0000001);

    }

    @Test
    @Disabled("Mockito has a bug while mocking static methods")
    void canConvertCurrencies() throws IOException, InterruptedException {
        Money oneDollar = new Money(Currency.USD, 1);
        Money hundredDollars = new Money(Currency.USD, 100);

        try (MockedStatic<CurrencyConverter> currencyConverterMock = Mockito.mockStatic(CurrencyConverter.class)) {
            currencyConverterMock.when(() -> CurrencyConverter.getRateFactor(Currency.USD, Currency.EUR)).thenCallRealMethod().thenReturn(0.92);

            var convertDollar = CurrencyConverter.convertMoney(oneDollar, Currency.EUR);
            var convertedHundredDollars = CurrencyConverter.convertMoney(hundredDollars, Currency.EUR);

            assertEquals(0.92, convertDollar.getAmount());
            assertEquals(92, convertedHundredDollars.getAmount());
            assertEquals(Currency.EUR, convertDollar.getCurrency());
            assertEquals(Currency.EUR, convertedHundredDollars.getCurrency());
        }
    }
}
