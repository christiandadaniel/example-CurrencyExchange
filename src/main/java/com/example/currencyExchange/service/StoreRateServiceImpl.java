package com.example.currencyExchange.service;

import com.example.currencyExchange.data.entity.ExchangeRate;
import com.example.currencyExchange.data.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class StoreRateServiceImpl implements StoreRateService {

	private ExchangeRateRepository exchangeRateRepository;

	@Autowired
	public StoreRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
		this.exchangeRateRepository = exchangeRateRepository;
	}

	@Override
	public Optional<ExchangeRate> getLastestRate() {
		return exchangeRateRepository.findTopByOrderByDateDesc();
	}

	@Override
	public List<ExchangeRate> getRatesInRange(Calendar begin, Calendar end) {
		return exchangeRateRepository.findByDateBetweenOrderByDateAsc(begin, end);
	}

	@Override
	public void saveRate(ExchangeRate exchangeRate) {
		exchangeRateRepository.saveAndFlush(exchangeRate);
	}
}
