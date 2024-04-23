package com.example.rateexchangeservice.service.impl;

import com.example.rateexchangeservice.exceptions.ExchangeRateResponseNotFoundException;
import com.example.rateexchangeservice.exceptions.InvalidCurrencyException;
import com.example.rateexchangeservice.mapper.impl.ExchangeRateDataDataMapperImpl;
import com.example.rateexchangeservice.model.response.CurrencyResponse;
import com.example.rateexchangeservice.model.request.CurrencyRequest;
import com.example.rateexchangeservice.model.response.ExchangeRateResponse;
import com.example.rateexchangeservice.repository.ExchangeRateRepository;
import com.example.rateexchangeservice.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    @Value("${fixer.api.url}")
    private String apiUrl;
    @Value("${fixer.api.access_key}")
    private String accessKey;
    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;
    private static final ConcurrentMap<String, ExchangeRateResponse> cache = new ConcurrentHashMap<>();
    private static final List<String> currencies = new CopyOnWriteArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    public ExchangeRateServiceImpl(RestTemplate restTemplate, ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.restTemplate = restTemplate;
    }

    @Async
    @Transactional
    @Scheduled(fixedRate = 3600000)
    public synchronized void getLatestExchangeRates() {
        final List<String> currencies = new CopyOnWriteArrayList<>(ExchangeRateServiceImpl.currencies);
        for (String code : currencies) {
            var url = apiUrl + "?access_key=" + accessKey + "&cbase=" + code + "&format=1";
            logger.info("Request URL: {}", url);
            var exchangeRateResponse = restTemplate.getForObject(url, ExchangeRateResponse.class);
            logger.info("Response: {}", exchangeRateResponse);

            saveToCache(code, exchangeRateResponse);
            saveToDatabase(exchangeRateResponse);
        }
    }

    private synchronized void saveToCache(String code, ExchangeRateResponse exchangeRateResponse) {
        logger.info("Saving to map: {}", code);
        cache.put(code, exchangeRateResponse);
    }

    private synchronized void saveToDatabase(ExchangeRateResponse exchangeRateResponse) {
        logger.info("Saving to database: {}", exchangeRateResponse);
        final var mapper = new ExchangeRateDataDataMapperImpl();
        final var entity = mapper.mapToEntity(exchangeRateResponse);
        exchangeRateRepository.save(entity);
    }

    @Override
    public ExchangeRateResponse getExchangeRatesByCurrency(final String currency) {
        logger.info("Retrieving currency with code: {}", currency);
        final Optional<ExchangeRateResponse> optionalExchangeRateResponse = Optional.ofNullable(cache.get(currency));
        //Free account on fixer.io supports only EUR currency, here it's replaced with created
        optionalExchangeRateResponse.ifPresent(response -> response.setBase(currency));
        return optionalExchangeRateResponse.orElseThrow(() ->
            new ExchangeRateResponseNotFoundException("Exchange rate response not found for currency: " + currency));
    }

    @Override
    public CurrencyResponse getCurrency() {
        final var currencyResponse  = new CurrencyResponse();
        logger.info("Retrieving currencies: {}", currencyResponse.getCurrencies());
        Optional<Set<String>> optionalCurrencies = Optional.of(cache.keySet());
        currencyResponse.setCurrencies(optionalCurrencies.orElseThrow(() ->
            new ExchangeRateResponseNotFoundException("Currency cache is empty")));
        return currencyResponse;
    }

    @Override
    public void addCurrency(final CurrencyRequest currency) {
        logger.info("Adding currency: {}", currency.getCurrency());
        Optional<String> optionalCurrency = Optional.ofNullable(currency.getCurrency());
        currencies.add(optionalCurrency.orElseThrow(() ->
            new InvalidCurrencyException("Currency cannot be null or empty")));
    }
}
