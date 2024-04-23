package com.example.rateexchangeservice.mapper;

import com.example.rateexchangeservice.entity.ExchangeRate;
import com.example.rateexchangeservice.model.response.ExchangeRateResponse;

public interface ExchangeRateDataMapper {
    ExchangeRate mapToEntity(final ExchangeRateResponse exchangeRateResponse);
    ExchangeRateResponse mapToDomain(final ExchangeRate exchangeRate);
}
