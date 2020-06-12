package com.anesti.expensemanagement.currencyconverter;

import java.io.IOException;
import java.io.InputStream;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


class HTTPRateRequesterTest {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    void canSendRequestForConversion() throws IOException, InterruptedException {
        HTTPRateRequester httpRateRequester = new HTTPRateRequester();
        HttpResponse<InputStream> conversionHttpResponse = httpRateRequester.queryExchangeRate("USD_LBP");
        assertEquals(200, conversionHttpResponse.statusCode());
    }
}
