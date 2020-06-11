package com.anesti.expensemanagement.currencyconverter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class HTTPRateRequester {

    private static final String ACCESS_KEY = "74997eb32f34f1741ea1";
    private static final String BASE_URL = "https://free.currconv.com/api/v7/";
    private static final String ENDPOINT = "convert";

    private final HttpClient httpClient;

    HTTPRateRequester() {
        httpClient = HttpClient.newHttpClient();
    }

    // @VisibleForTesting
    HttpResponse<String> queryExchangeRate(String queryConversion) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + ENDPOINT + "?q=" + queryConversion + "&compact=ultra&apiKey=" + ACCESS_KEY))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
