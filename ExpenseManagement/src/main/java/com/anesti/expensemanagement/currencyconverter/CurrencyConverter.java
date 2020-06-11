package com.anesti.expensemanagement.currencyconverter;

import com.anesti.expensemanagement.Currency;
import com.anesti.expensemanagement.Money;

import java.io.IOException;

public class CurrencyConverter {

    private final Currency toCurrency;
    private final HTTPRateRequester httpRateRequester;
    private final static JSONResponseRateParser JSON_RESPONSE_RATE_PARSER = new JSONResponseRateParser();


    public CurrencyConverter(Currency toCurrency) {
        this.toCurrency = toCurrency;
        httpRateRequester = new HTTPRateRequester();
    }

    public Money convertMoney(Money originalMoney) throws IOException, InterruptedException {
        double factor = getRateFactor(originalMoney);
        return new Money(toCurrency, originalMoney.getAmount() * factor);
    }

    // @VisibleForTesting
    double getRateFactor(Money originalMoney) throws IOException, InterruptedException {
        String queryConversion = originalMoney.getCurrency().toString() + "_" + toCurrency.toString();

        String jsonResponse = httpRateRequester.queryExchangeRate(queryConversion).body();
        return JSON_RESPONSE_RATE_PARSER.extractFromJsonAsDouble(queryConversion, jsonResponse);
    }
}
