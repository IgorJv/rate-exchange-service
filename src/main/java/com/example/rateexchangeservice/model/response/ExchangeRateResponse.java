package com.example.rateexchangeservice.model.response;

import com.example.rateexchangeservice.utils.CurrencyRateSetDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ExchangeRateResponse {
    private boolean success;
    private Instant timestamp;
    private String base;
    private Date date;
    @JsonDeserialize(using = CurrencyRateSetDeserializer.class)
    private Set<CurrencyRateResponse> rates;

    @JsonDeserialize(using = CurrencyRateSetDeserializer.class)
    public Map<String, Double> getRates() {
        Map<String, Double> ratesMap = new HashMap<>();
        for (CurrencyRateResponse rate : rates) {
            ratesMap.put(rate.getCurrencyCode(), rate.getExchangeRate());
        }
        return ratesMap;
    }

    @JsonIgnore
    public Set<CurrencyRateResponse> getRatesValue() {
        return rates;
    }

    public void setRates(Set<CurrencyRateResponse> rates) {
        this.rates = rates;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateResponse that = (ExchangeRateResponse) o;
        return success == that.success && Objects.equals(timestamp, that.timestamp) && Objects.equals(base, that.base) && Objects.equals(date, that.date) && Objects.equals(rates, that.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, timestamp, base, date, rates);
    }

    @Override
    public String toString() {
        return "ExchangeRateResponse{" +
            "success=" + success +
            ", date=" + date +
            ", base='" + base + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
