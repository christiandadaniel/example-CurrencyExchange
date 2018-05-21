package com.example.currencyExchange.service;

import com.example.currencyExchange.data.entity.ExchangeRate;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface StoreRateService {

	Optional<ExchangeRate> getLastestRate();
	List<ExchangeRate> getRatesInRange(Calendar begin, Calendar end);
	void saveRate(ExchangeRate exchangeRate);
}
