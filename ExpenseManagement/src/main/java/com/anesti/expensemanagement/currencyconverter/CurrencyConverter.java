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

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private final Currency toCurrency;
    private final HTTPRateRequester httpRateRequester;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public CurrencyConverter(Currency toCurrency) {
        this.toCurrency = toCurrency;
        httpRateRequester = new HTTPRateRequester();
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Currency getToCurrency() {
        return toCurrency;
    }

    public Money convertMoney(Money originalMoney) throws IOException, InterruptedException {
        double factor = getRateFactor(originalMoney);
        return new Money(toCurrency, originalMoney.getAmount() * factor);
    }

    // @VisibleForTesting
    double getRateFactor(Money originalMoney) throws IOException, InterruptedException {
        String queryConversion = originalMoney.getCurrency().toString() + "_" + toCurrency.toString();

        InputStream jsonResponse = httpRateRequester.queryExchangeRate(queryConversion).body();
        return JSON_RESPONSE_RATE_PARSER.extractFromJsonAsDouble(queryConversion, jsonResponse);
    }
}
