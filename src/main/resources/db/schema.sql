CREATE DATABASE currency_exchange_db;

CREATE TABLE IF NOT EXISTS exchange_rates (
    id SERIAL PRIMARY KEY,
    success BOOLEAN NOT NULL,
    date DATE NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    base VARCHAR(3) NOT NULL
);

CREATE TABLE IF NOT EXISTS currency_rates (
    id SERIAL PRIMARY KEY,
    currency_code VARCHAR(3),
    exchange_rate DECIMAL(10, 6),
    exchange_rates_id INT REFERENCES exchange_rates(id)
);