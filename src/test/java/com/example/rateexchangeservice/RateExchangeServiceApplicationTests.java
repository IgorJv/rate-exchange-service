package com.example.rateexchangeservice;

import com.example.rateexchangeservice.entity.CurrencyRate;
import com.example.rateexchangeservice.entity.ExchangeRate;
import com.example.rateexchangeservice.model.response.CurrencyResponse;
import com.example.rateexchangeservice.model.request.CurrencyRequest;
import com.example.rateexchangeservice.model.response.ExchangeRateResponse;
import com.example.rateexchangeservice.repository.ExchangeRateRepository;
import com.example.rateexchangeservice.service.ExchangeRateService;
import com.example.rateexchangeservice.service.impl.ExchangeRateServiceImpl;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;


import java.time.Instant;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class RateExchangeServiceApplicationTests {

	@Mock
	private ExchangeRateRepository exchangeRateRepository;

	@InjectMocks
	private ExchangeRateServiceImpl currencyService;

	@Test
	void contextLoads() {
	}

	@Test
	public void getListOfCurrenciesTest() {
		var currency = new CurrencyResponse();
		currency.setCurrencies(Set.of("EUR", "USD", "GBP"));
		ExchangeRateService mockExchangeRateService = mock(ExchangeRateService.class);
		when(mockExchangeRateService.getCurrency()).thenReturn(currency);

		mockExchangeRateService.getCurrency();

		assertEquals(3, currency.getCurrencies().size());
		assertTrue(currency.getCurrencies().contains("EUR"));
		assertTrue(currency.getCurrencies().contains("USD"));
		assertTrue(currency.getCurrencies().contains("GBP"));
	}

	@Test
	public void getExchangeRatesByCurrencyTest() {
		ExchangeRateResponse mockExchangeRateResponse = new ExchangeRateResponse();
		mockExchangeRateResponse.setBase("EUR");
		mockExchangeRateResponse.setTimestamp(Instant.ofEpochSecond(1713768364L));

		ExchangeRateService mockExchangeRateService = mock(ExchangeRateService.class);
		when(mockExchangeRateService.getExchangeRatesByCurrency("EUR")).thenReturn(mockExchangeRateResponse);

		ExchangeRateResponse exchangeRateResponse = mockExchangeRateService.getExchangeRatesByCurrency("EUR");

		assertNotNull(exchangeRateResponse);
		assertEquals("EUR", exchangeRateResponse.getBase());
		assertEquals("2024-04-22T06:46:04Z", exchangeRateResponse.getTimestamp().toString());
	}

	@Test
	public void testAddCurrency() {
		ExchangeRateService mockExchangeRateService = Mockito.spy(new ExchangeRateServiceImpl(new RestTemplate(), exchangeRateRepository));
		var currencyRequest = new CurrencyRequest("JPY");
		currencyRequest.setCurrency("JPY");

		CurrencyResponse currencyResponse = new CurrencyResponse();
		currencyResponse.setCurrencies(Set.of("JPY"));

		Mockito.when(mockExchangeRateService.getCurrency()).thenReturn(currencyResponse);

		mockExchangeRateService.addCurrency(currencyRequest);
		Mockito.verify(mockExchangeRateService).addCurrency(currencyRequest);

		assertTrue(mockExchangeRateService.getCurrency().getCurrencies().contains("JPY"));

	}

	@Test
	public void currencyRepositoryTest() {
		var mockExchangeRate = new ExchangeRate();
		mockExchangeRate.setSuccess(true);
		mockExchangeRate.setTimestamp(Instant.ofEpochSecond(1713768364L));
		mockExchangeRate.setBase("EUR");
		mockExchangeRate.setDate(new Date());

		var rate = new CurrencyRate();
		rate.setCurrencyCode("USD");
		rate.setExchangeRates(12.5);
		rate.setExchangeRate(mockExchangeRate);

		mockExchangeRate.setRates(Set.of(rate));

		when(exchangeRateRepository.save(any(ExchangeRate.class))).thenReturn(mockExchangeRate);

		var savedEntity = exchangeRateRepository.save(mockExchangeRate);

		verify(exchangeRateRepository).save(mockExchangeRate);
		assertEquals(mockExchangeRate, savedEntity);
	}

}
