package com.example.rateexchangeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RateExchangeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RateExchangeServiceApplication.class, args);
	}

}
