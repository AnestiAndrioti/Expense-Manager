package com.anesti.expensemanagement.currencyconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JSONResponseParserTest {

    @Test
    void canExtractRateFromJson() throws JsonProcessingException {
        String json = "{\"USD_LBP\":1512.767904}";
        JSONResponseRateParser jsonResponseParser = new JSONResponseRateParser();

        double rate = jsonResponseParser.extractFromJsonAsDouble("USD_LBP", json);

        assertEquals(1512.767904, rate, 0.000001);
    }

    @Test
    void ThrowsExceptionIfKeyNotInJson() {
        String json = "{\"USD_LBP\":1512.767904}";
        JSONResponseRateParser jsonResponseParser = new JSONResponseRateParser();

        assertThrows(IllegalArgumentException.class, () -> jsonResponseParser.extractFromJsonAsDouble("USD_EUR", json));
    }
}
