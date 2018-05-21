package com.example.currencyExchange.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrentRateServiceMock implements CurrentRateService {

	@Override
	public BigDecimal retrieveCurrentRate() {

		double random = Math.random();
		random = (random/2)+1;

		return new BigDecimal(random);
	}
}
