package com.anesti.expensemanagement.currencyconverter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


class JSONResponseParserTest {

    @Test
    void canExtractRateFromJson() throws IOException {
        String json = "{\"USD_LBP\":1512.767904}";
        InputStream jsonStream = new ByteArrayInputStream(json.getBytes());
        JSONResponseRateParser jsonResponseParser = new JSONResponseRateParser();

        double rate = jsonResponseParser.extractFromJsonAsDouble("USD_LBP", jsonStream);

        assertEquals(1512.767904, rate, 0.000001);
    }
}
