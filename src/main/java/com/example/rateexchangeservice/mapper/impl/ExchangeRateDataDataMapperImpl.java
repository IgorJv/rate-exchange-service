package com.example.rateexchangeservice.mapper.impl;

import com.example.rateexchangeservice.entity.CurrencyRate;
import com.example.rateexchangeservice.entity.ExchangeRate;
import com.example.rateexchangeservice.exceptions.ExchangeRateResponseNotFoundException;
import com.example.rateexchangeservice.mapper.ExchangeRateDataMapper;
import com.example.rateexchangeservice.model.response.CurrencyRateResponse;
import com.example.rateexchangeservice.model.response.ExchangeRateResponse;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class ExchangeRateDataDataMapperImpl implements ExchangeRateDataMapper {
    @Override
    public ExchangeRate mapToEntity(final ExchangeRateResponse exchangeRateResponse) {
        Optional.of(exchangeRateResponse)
            .orElseThrow(() -> new ExchangeRateResponseNotFoundException("The ExchangeRateResponse is null"));
        var entity = new ExchangeRate();
        entity.setSuccess(exchangeRateResponse.isSuccess());
        entity.setBase(exchangeRateResponse.getBase());
        entity.setDate(exchangeRateResponse.getDate());
        entity.setTimestamp(exchangeRateResponse.getTimestamp());
        Set<CurrencyRateResponse> currencyRateResponse = exchangeRateResponse.getRatesValue();
        Set<CurrencyRate> currencyEntity = new HashSet<>();
        for (CurrencyRateResponse rate : currencyRateResponse) {
            var currencyRate = new CurrencyRate();
            currencyRate.setCurrencyCode(rate.getCurrencyCode());
            currencyRate.setExchangeRates(rate.getExchangeRate());
            currencyRate.setExchangeRate(entity);
            currencyEntity.add(currencyRate);
        }
        entity.setRates(currencyEntity);
        return entity;
    }

    @Override
    public ExchangeRateResponse mapToDomain(final ExchangeRate exchangeRate) {
        Optional.of(exchangeRate)
            .orElseThrow(() -> new ExchangeRateResponseNotFoundException("The ExchangeRate entity is null"));
        var exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setSuccess(Objects.requireNonNull(exchangeRate).isSuccess());
        exchangeRateResponse.setBase(exchangeRate.getBase());
        exchangeRateResponse.setDate(exchangeRate.getDate());
        exchangeRateResponse.setTimestamp(exchangeRate.getTimestamp());
        Set<CurrencyRate> currencyEntity = exchangeRate.getRates();
        Set<CurrencyRateResponse> currencyRateResponse = new HashSet<>();
        for (CurrencyRate rate : currencyEntity) {
            var rateResponse = new CurrencyRateResponse();
            rateResponse.setCurrencyCode(rate.getCurrencyCode());
            rateResponse.setExchangeRate(rate.getExchangeRates());
            currencyRateResponse.add(rateResponse);
        }
        exchangeRateResponse.setRates(currencyRateResponse);
        return exchangeRateResponse;
    }
}
