package com.anesti.expensemanagement.currencyconverter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPRateRequesterTest {

    @Test
    void canSendRequestForConversion() throws IOException, InterruptedException {
        HTTPRateRequester httpRateRequester = new HTTPRateRequester();
        HttpResponse<InputStream> conversionHttpResponse = httpRateRequester.queryExchangeRate("USD_LBP");
        assertEquals(200, conversionHttpResponse.statusCode());
    }
}
