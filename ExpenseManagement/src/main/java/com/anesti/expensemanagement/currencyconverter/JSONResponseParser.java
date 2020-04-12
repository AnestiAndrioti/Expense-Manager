package com.anesti.expensemanagement.currencyconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONResponseParser {

    // @VisibleForTesting
    double extractFromJsonAsDouble(String query, String json) throws JsonProcessingException {
        if(!json.contains(query)) {
            throw new IllegalArgumentException("The key " + query + " is not present in the following JSON" + json);
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        return node.get(query).asDouble();
    }
}
