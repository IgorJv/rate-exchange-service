package com.example.rateexchangeservice.utils;

import com.example.rateexchangeservice.model.response.CurrencyRateResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CurrencyRateSetDeserializer extends JsonDeserializer<Set<CurrencyRateResponse>> {
    @Override
    public Set<CurrencyRateResponse> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Set<CurrencyRateResponse> currencyRateResponses = new HashSet<>();
        if (jsonParser.currentToken() != JsonToken.START_OBJECT) {
            throw new IOException("Expected start of object");
        }
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String currencyCode = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if (jsonParser.currentToken() == JsonToken.VALUE_NUMBER_FLOAT || jsonParser.currentToken() == JsonToken.VALUE_NUMBER_INT) {
                double rate = jsonParser.getDoubleValue();
                currencyRateResponses.add(new CurrencyRateResponse(currencyCode, rate));
            } else {
                jsonParser.skipChildren();
            }
        }
        return currencyRateResponses;
    }
}
