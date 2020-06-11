package com.anesti.expensemanagement.currencyconverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

class JSONResponseRateParser {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    // @VisibleForTesting
    double extractFromJsonAsDouble(String query, InputStream json) throws IOException {
        JsonNode node = MAPPER.readTree(json);
        return node.get(query).asDouble();
    }
}
