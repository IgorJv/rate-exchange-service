package com.example.rateexchangeservice.model.response;

import java.util.Objects;

public class CurrencyRateResponse {
    private String currencyCode;
    private Double exchangeRate;

    public CurrencyRateResponse() {
    }

    public CurrencyRateResponse(String currencyCode, Double exchangeRate) {
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRateResponse that = (CurrencyRateResponse) o;
        return Objects.equals(currencyCode, that.currencyCode) && Objects.equals(exchangeRate, that.exchangeRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyCode, exchangeRate);
    }

    @Override
    public String toString() {
        return "CurrencyRateResponse{" +
            "currencyCode='" + currencyCode + '\'' +
            ", exchangeRate=" + exchangeRate +
            '}';
    }
}
