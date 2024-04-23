package com.example.rateexchangeservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "currency_rates")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "currency_code", nullable = false)
    private String currencyCode;
    @Column(name = "exchange_rate", nullable = false)
    private Double exchangeRates;
    @ManyToOne
    @JoinColumn(name = "exchange_rates_id", nullable = false)
    private ExchangeRate exchangeRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(Double exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRate that = (CurrencyRate) o;
        return Objects.equals(id, that.id) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(exchangeRates, that.exchangeRates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyCode, exchangeRates);
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
            "id=" + id +
            ", exchangeRates=" + exchangeRates +
            ", currencyCode='" + currencyCode + '\'' +
            '}';
    }
}
