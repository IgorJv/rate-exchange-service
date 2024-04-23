package com.example.rateexchangeservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private boolean success;
    private Date date;
    @Column(nullable = false)
    private Instant timestamp;
    private String base;
    @OneToMany(mappedBy = "exchangeRate", cascade = CascadeType.ALL,
        fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CurrencyRate> rates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void removeRate(CurrencyRate currencyRate) {
        this.rates.remove(currencyRate);
        currencyRate.setExchangeRate(null);
    }

    public void addRate(CurrencyRate currencyRate) {
        rates.add(currencyRate);
    }

    public Set<CurrencyRate> getRates() {
        return rates;
    }

    public void setRates(Set<CurrencyRate> rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return success == that.success && Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(timestamp, that.timestamp) && Objects.equals(base, that.base);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, success, date, timestamp, base);
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
            "id=" + id +
            ", success=" + success +
            ", date=" + date +
            ", timestamp=" + timestamp +
            ", base='" + base + '\'' +
            '}';
    }
}
