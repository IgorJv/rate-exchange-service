package com.example.rateexchangeservice.service;

import com.example.rateexchangeservice.model.response.CurrencyResponse;
import com.example.rateexchangeservice.model.request.CurrencyRequest;
import com.example.rateexchangeservice.model.response.ExchangeRateResponse;

public interface ExchangeRateService {
    void getLatestExchangeRates();
    ExchangeRateResponse getExchangeRatesByCurrency(String currency);
    CurrencyResponse getCurrency();
    void addCurrency(CurrencyRequest currency);
}
