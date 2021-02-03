package com.anesti.expensemanagement.currencyconverter;

import java.io.IOException;
import java.io.InputStream;

import com.anesti.expensemanagement.Currency;
import com.anesti.expensemanagement.Money;


public class CurrencyConverter {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final JSONResponseRateParser JSON_RESPONSE_RATE_PARSER = new JSONResponseRateParser();
    private static final HTTPRateRequester HTTP_RATE_REQUESTER = new HTTPRateRequester();

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static Money convertMoney(Money originalMoney, Currency toCurrency) throws IOException, InterruptedException {
        double factor = getRateFactor(originalMoney.getCurrency(), toCurrency);
        return new Money(toCurrency, originalMoney.getAmount() * factor);
    }

    // @VisibleForTesting
    public static double getRateFactor(Currency fromCurrency, Currency toCurrency) throws IOException, InterruptedException {
        String queryConversion = fromCurrency.getCode() + "_" + toCurrency.getCode();

        InputStream jsonResponse = HTTP_RATE_REQUESTER.queryExchangeRate(queryConversion).body();
        return JSON_RESPONSE_RATE_PARSER.extractFromJsonAsDouble(queryConversion, jsonResponse);
    }
}
