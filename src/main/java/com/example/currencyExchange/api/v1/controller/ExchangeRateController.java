package com.example.currencyExchange.api.v1.controller;

import com.example.currencyExchange.api.v1.exception.BadRequestException;
import com.example.currencyExchange.api.v1.exception.NoContentException;
import com.example.currencyExchange.data.entity.ExchangeRate;
import com.example.currencyExchange.service.StoreRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/api/v1/exchangeRate",
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExchangeRateController {

	private StoreRateService storeRateService;

	@Autowired
	public ExchangeRateController(StoreRateService storeRateService) {
		this.storeRateService = storeRateService;
	}

	@GetMapping(path = "/")
	public ExchangeRate getLatest() throws NoContentException {
		return storeRateService.getLastestRate()
				.orElseThrow(NoContentException::new);
	}

	@GetMapping(path = "/range")
	public List<ExchangeRate> getRange(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Calendar begin,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Calendar end) throws BadRequestException {

		if (begin == null || end == null) {
			throw new BadRequestException();
		}

		List<ExchangeRate> ret = storeRateService.getRatesInRange(begin, end);
		return ret;
	}
}
