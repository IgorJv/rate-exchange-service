package com.example.rateexchangeservice.controller;

import com.example.rateexchangeservice.model.response.CurrencyResponse;
import com.example.rateexchangeservice.model.request.CurrencyRequest;
import com.example.rateexchangeservice.model.response.ExchangeRateResponse;
import com.example.rateexchangeservice.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "Bad request"),
    @ApiResponse(responseCode = "404", description = "Not found")
})
@RequestMapping("api/v1")
@RestController
public class CurrencyController {
    private final ExchangeRateService exchangeRateService;

    public CurrencyController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Operation(summary = "Get exchange rates for a currency")
    @GetMapping("/currency/{currency}")
    @ResponseStatus(HttpStatus.OK)
    public ExchangeRateResponse getExchangeRateForCurrency(@PathVariable String currency) {
        return exchangeRateService.getExchangeRatesByCurrency(currency);
    }

    @Operation(summary = "Get a list of currencies used in the project")
    @GetMapping("/currency")
    @ResponseStatus(HttpStatus.OK)
    public CurrencyResponse getCurrency() {
        return exchangeRateService.getCurrency();
    }

    @Operation(summary = "Add new currency for getting exchange rates")
    @PutMapping("/currency")
    @ResponseStatus(HttpStatus.OK)
    public void addCurrency(@RequestBody CurrencyRequest currency) {
        exchangeRateService.addCurrency(currency);
    }
}
