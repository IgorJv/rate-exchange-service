package com.example.rateexchangeservice;

import com.example.rateexchangeservice.model.response.CurrencyResponse;
import com.example.rateexchangeservice.model.request.CurrencyRequest;
import com.example.rateexchangeservice.model.response.ExchangeRateResponse;
import com.example.rateexchangeservice.repository.ExchangeRateRepository;
import com.example.rateexchangeservice.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpMethod.PUT;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class RateExchangeIntegrationTests {
    @Autowired
    private ExchangeRateRepository repository;
    @Autowired
    private ExchangeRateServiceImpl service;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${local.server.port}")
    int port;

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres");

    @Test
    public void getAllCurrenciesTest() {
        var currencyResponse = restTemplate.getForObject("http://localhost:"+ port +"/api/v1/currency", CurrencyResponse.class);

        assertNotNull(currencyResponse);
        assertTrue(currencyResponse.getCurrencies().contains("USD"));
    }

    @Test
    public void getExchangeRateByCurrency() {
        var exchangeRateResponse = restTemplate.getForObject("http://localhost:"+ port +"/api/v1/currency/USD", ExchangeRateResponse.class);

        assertNotNull(exchangeRateResponse);
        assertEquals("USD", exchangeRateResponse.getBase());
    }

    @Test
    public void addCurrencyTest() {
        var currency = new CurrencyResponse();
        currency.setCurrencies(Set.of("USD"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>("{\"currency\": \"USD\"}", headers);
        var response = restTemplate.exchange("http://localhost:"+ port +"/api/v1/currency", PUT, requestEntity, String.class);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
    }

    @BeforeEach
    void contextLoads() {
        com.example.rateexchangeservice.entity.ExchangeRate mockExchangeRate = new com.example.rateexchangeservice.entity.ExchangeRate();
        mockExchangeRate.setSuccess(true);
        mockExchangeRate.setTimestamp(Instant.ofEpochSecond(1713768364L));
        mockExchangeRate.setBase("EUR");
        mockExchangeRate.setDate(new Date());

        var rate = new com.example.rateexchangeservice.entity.CurrencyRate();
        rate.setExchangeRates(12.5);
        rate.setCurrencyCode("USD");
        rate.setExchangeRate(mockExchangeRate);
        mockExchangeRate.setRates(Set.of(rate));

        repository.save(mockExchangeRate);

        System.out.println("Loads");

        var currency = new CurrencyRequest();
        currency.setCurrency("USD");
        service.addCurrency(currency);
        service.getLatestExchangeRates();
    }

    @Test
    public void connectionTest() {
        assertTrue(container.isCreated());
        assertTrue(container.isRunning());
    }
}
