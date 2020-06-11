package com.anesti.expensemanagement.currencyconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class JSONResponseRateParser {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    // @VisibleForTesting
    double extractFromJsonAsDouble(String query, String json) throws JsonProcessingException {
        if(!json.contains(query)) {
            throw new IllegalArgumentException("The key " + query + " is not present in the following JSON" + json);
        }
        JsonNode node = MAPPER.readTree(json);
        return node.get(query).asDouble();
    }
}
