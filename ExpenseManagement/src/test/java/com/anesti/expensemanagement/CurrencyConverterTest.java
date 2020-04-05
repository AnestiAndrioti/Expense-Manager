package com.anesti.expensemanagement;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyConverterTest {

    @Test
    public void canSendRequestForConversion() throws IOException, InterruptedException {

        HttpResponse<String> conversionHttpResponse = CurrencyConverter.queryExchangeRate("USD_LBP");
        assertEquals(200, conversionHttpResponse.statusCode());
    }

    @Test
    public void convertToSameCurrencyYieldsSameAmount() throws IOException, InterruptedException {

        Money originalMoney = new Money(Currency.LBP, 100000);
        assertEquals(originalMoney, CurrencyConverter.convertMoney(originalMoney, Currency.LBP));
    }

//    As of 05/04/2020
//    Mockito does not mock static methods...
//    PowerMock does not support Junit5...
//    @Test
//    public void convertCurrencies() {
//    }

}
