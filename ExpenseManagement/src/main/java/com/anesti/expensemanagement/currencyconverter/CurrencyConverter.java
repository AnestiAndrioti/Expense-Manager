package com.anesti.expensemanagement.currencyconverter;

import com.anesti.expensemanagement.Currency;
import com.anesti.expensemanagement.Money;

import java.io.IOException;

public class CurrencyConverter {

    private static final String ACCESS_KEY = "74997eb32f34f1741ea1";
    private static final String BASE_URL = "https://free.currconv.com/api/v7/";

    private final static HTTPRateRequester httpRateRequester = new HTTPRateRequester();
    private final static JSONResponseParser jsonParser = new JSONResponseParser();

    public static Money convertMoney(Money moneyFromOriginalCurrency, Currency toCurrency) throws IOException, InterruptedException {

        String queryConversion = moneyFromOriginalCurrency.getCurrency().toString() + "_" + toCurrency.toString();

        String jsonResponse = httpRateRequester.queryExchangeRate(queryConversion).body();

        double factor = jsonParser.extractFromJsonAsDouble(queryConversion, jsonResponse);

        return new Money(toCurrency, moneyFromOriginalCurrency.getAmount() * factor);
    }
}
