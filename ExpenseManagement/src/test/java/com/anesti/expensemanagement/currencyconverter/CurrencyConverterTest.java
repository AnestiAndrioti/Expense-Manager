package com.anesti.expensemanagement.currencyconverter;

import com.anesti.expensemanagement.Currency;
import com.anesti.expensemanagement.Money;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyConverterTest {

    @Test
    public void convertToSameCurrencyYieldsSameAmount() throws IOException, InterruptedException {

        Money originalMoney = new Money(Currency.LBP, 100000);
        assertEquals(originalMoney, CurrencyConverter.convertMoney(originalMoney, Currency.LBP));
    }

    @Test
    public void convertMoneyKeepsOriginal() throws IOException, InterruptedException {

        final double originalAmount = 10;
        Money originalMoney = new Money(Currency.USD, originalAmount);
        Money resultMoney = CurrencyConverter.convertMoney(originalMoney, Currency.LBP);

        assertEquals(Currency.LBP, resultMoney.getCurrency());
        assertEquals(Currency.USD, originalMoney.getCurrency());
        assertEquals(originalAmount, originalMoney.getAmount(), 0.0000001);

    }

//    Wanted to test convertCurrency. But to do so
//    without the randomness due to an unfixed exchange rate
//    I have to mock the response of the httpRequest to give a fix rate
//    However, as of 05/04/2020
//    Mockito does not mock static methods...
//    and PowerMock does not support Junit5...
//    @Test
//    public void convertCurrencies() {
//    }

}
