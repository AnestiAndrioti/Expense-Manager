package com.anesti.expensemanagement.currencyconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JSONResponseParserTest {

    @Test
    public void canExtractRateFromJson() throws JsonProcessingException {
        String json = "{\"USD_LBP\":1512.767904}";
        JSONResponseParser jsonResponseParser = new JSONResponseParser();

        double rate = jsonResponseParser.extractFromJsonAsDouble("USD_LBP", json);

        assertEquals(1512.767904, rate, 0.000001);
    }

    @Test
    public void ThrowsExceptionIfKeyNotInJson() {
        String json = "{\"USD_LBP\":1512.767904}";
        JSONResponseParser jsonResponseParser = new JSONResponseParser();

        assertThrows(IllegalArgumentException.class, () -> jsonResponseParser.extractFromJsonAsDouble("USD_EUR", json));
    }
}
