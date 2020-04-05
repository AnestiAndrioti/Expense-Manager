package com.anesti.expensemanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConverter {

    private static final String ACCESS_KEY = "74997eb32f34f1741ea1";
    private static final String BASE_URL = "https://free.currconv.com/api/v7/";
    private static final String ENDPOINT = "convert";

    public static Money convertMoney(Money moneyFromOriginalCurrency, Currency toCurrency) throws IOException, InterruptedException {

        String queryConversion = moneyFromOriginalCurrency.getCurrency().toString() + "_" + toCurrency.toString();

        String jsonResponse = queryExchangeRate(queryConversion).body();

        double factor = extractFactorFromJson(queryConversion, jsonResponse);

        return new Money(toCurrency, moneyFromOriginalCurrency.getAmount() * factor);
    }

    private static double extractFactorFromJson(String queryConversion, String jsonResponse) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonResponse);
        return node.get(queryConversion).asDouble();
    }

    // @VisibleForTesting
    static HttpResponse<String> queryExchangeRate(String queryConversion) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + ENDPOINT + "?q=" + queryConversion + "&compact=ultra&apiKey=" + ACCESS_KEY))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
