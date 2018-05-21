package com.example.currencyExchange.jobs;

import com.example.currencyExchange.data.entity.ExchangeRate;
import com.example.currencyExchange.service.CurrentRateService;
import com.example.currencyExchange.service.StoreRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;

@Component
@Slf4j
public class ScheduledRateRetrieval {

	private CurrentRateService currentRateService;
	private StoreRateService storeRateService;

	@Autowired
	public ScheduledRateRetrieval(CurrentRateService currentRateService, StoreRateService storeRateService) {
		this.currentRateService = currentRateService;
		this.storeRateService = storeRateService;

		log.info("Job Construido");
	}

	@Scheduled(cron = "${currencyExchange.retrieval.cron}")
	public void retrieveAndStore() {
		BigDecimal currentRate = currentRateService.retrieveCurrentRate();

		storeRateService.saveRate(ExchangeRate.builder()
				.date(Calendar.getInstance())
				.rate(currentRate)
				.build());

		log.info("Salvo: {}", currentRate);

	}

}
