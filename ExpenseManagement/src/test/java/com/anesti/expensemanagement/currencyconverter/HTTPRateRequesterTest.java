package com.anesti.expensemanagement.currencyconverter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTTPRateRequesterTest {

    @Test
    public void canSendRequestForConversion() throws IOException, InterruptedException {

        HTTPRateRequester httpRateRequester = new HTTPRateRequester();
        HttpResponse<String> conversionHttpResponse = httpRateRequester.queryExchangeRate("USD_LBP");
        assertEquals(200, conversionHttpResponse.statusCode());
    }
}
