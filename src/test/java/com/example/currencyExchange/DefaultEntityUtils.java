package com.example.currencyExchange;

import com.example.currencyExchange.data.entity.ExchangeRate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DefaultEntityUtils {

	public static ExchangeRate sampleRate() {
		return sampleRateList().get(0);
	}

	public static List<ExchangeRate> sampleRateList() {
		Calendar dateBefore = new Calendar.Builder().setDate(2018, 1, 5).build();
		ExchangeRate before = ExchangeRate.builder()
				.date(dateBefore)
				.rate(new BigDecimal("1.2039"))
				.build();

		Calendar dateMiddle1 = new Calendar.Builder().setDate(2018, 3, 5).build();
		BigDecimal rate1 = new BigDecimal("1.2331");
		ExchangeRate middle1 = ExchangeRate.builder()
				.date(dateMiddle1)
				.rate(rate1)
				.build();

		Calendar dateMiddle2 = new Calendar.Builder().setDate(2018, 3, 20).build();
		BigDecimal rate2 = new BigDecimal("1.2274");
		ExchangeRate middle2 = ExchangeRate.builder()
				.date(dateMiddle2)
				.rate(rate2)
				.build();

		Calendar dateAfter = new Calendar.Builder().setDate(2018, 5, 20).build();
		ExchangeRate after = ExchangeRate.builder()
				.date(dateAfter)
				.rate(new BigDecimal("1.1751"))
				.build();


		return Arrays.asList(before, middle1, middle2, after);
	}
}
