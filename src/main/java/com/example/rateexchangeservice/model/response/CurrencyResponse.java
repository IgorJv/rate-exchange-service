package com.example.rateexchangeservice.model.response;

import java.util.Objects;
import java.util.Set;

public class CurrencyResponse {
    private Set<String> currencies;

    public Set<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Set<String> currencies) {
        this.currencies = currencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyResponse currencyResponse = (CurrencyResponse) o;
        return Objects.equals(currencies, currencyResponse.currencies);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(currencies);
    }

    @Override
    public String toString() {
        return "Currency{" +
            "currencies=" + currencies +
            '}';
    }
}
