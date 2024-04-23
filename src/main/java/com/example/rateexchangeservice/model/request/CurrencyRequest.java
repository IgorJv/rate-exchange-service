package com.example.rateexchangeservice.model.request;

public class CurrencyRequest {
    private String currency;

    public CurrencyRequest(String currency) {
        this.currency = currency;
    }

    public CurrencyRequest() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "CurrencyRequest{" +
            "currency='" + currency + '\'' +
            '}';
    }
}
