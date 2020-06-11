package com.anesti.expensemanagement.currencyconverter;

import com.anesti.expensemanagement.Currency;
import com.anesti.expensemanagement.Money;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class CurrencyConverterTest {

    @Test
    void convertToSameCurrencyYieldsSameAmount() throws IOException, InterruptedException {
        CurrencyConverter currencyConverter = new CurrencyConverter(Currency.LBP);
        Money originalMoney = new Money(Currency.LBP, 100000);
        assertEquals(originalMoney, currencyConverter.convertMoney(originalMoney));
    }

    @Test
    void convertMoneyKeepsOriginal() throws IOException, InterruptedException {
        CurrencyConverter currencyConverter = new CurrencyConverter(Currency.LBP);
        final double originalAmount = 10;
        Money originalMoney = new Money(Currency.USD, originalAmount);
        Money resultMoney = currencyConverter.convertMoney(originalMoney);

        assertEquals(Currency.LBP, resultMoney.getCurrency());
        assertEquals(Currency.USD, originalMoney.getCurrency());
        assertEquals(originalAmount, originalMoney.getAmount(), 0.0000001);

    }

    @Test
    void canConvertCurrencies() throws IOException, InterruptedException {
        Money oneDollar = new Money(Currency.USD, 1);
        Money hundredDollars = new Money(Currency.USD, 100);

        CurrencyConverter currencyConverterSpy = Mockito.spy(new CurrencyConverter(Currency.EUR));
        Mockito.doReturn(0.92).when(currencyConverterSpy).getRateFactor(any());

        var convertDollar = currencyConverterSpy.convertMoney(oneDollar);
        var convertedHundredDollars = currencyConverterSpy.convertMoney(hundredDollars);

        assertEquals(0.92, convertDollar.getAmount());
        assertEquals(92, convertedHundredDollars.getAmount());
        assertEquals(Currency.EUR, convertDollar.getCurrency());
        assertEquals(Currency.EUR, convertedHundredDollars.getCurrency());
    }
}
