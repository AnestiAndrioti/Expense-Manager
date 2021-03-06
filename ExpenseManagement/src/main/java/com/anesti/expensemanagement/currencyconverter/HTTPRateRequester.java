package com.anesti.expensemanagement.currencyconverter;

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


class HTTPRateRequester {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final String ACCESS_KEY = "74997eb32f34f1741ea1";
    private static final String BASE_URL = "https://free.currconv.com/api/v7/";
    private static final String ENDPOINT = "convert";

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private final HttpClient httpClient;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    HTTPRateRequester() {
        httpClient = HttpClient.newHttpClient();
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    // @VisibleForTesting
    HttpResponse<InputStream> queryExchangeRate(String queryConversion) throws IOException, InterruptedException {
        final var conversionURI = URI.create(BASE_URL + ENDPOINT + "?q=" + queryConversion + "&compact=ultra&apiKey=" + ACCESS_KEY);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(conversionURI).build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
    }
}
