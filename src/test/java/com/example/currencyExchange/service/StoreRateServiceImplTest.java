package com.example.currencyExchange.service;

import com.example.currencyExchange.DefaultEntityUtils;
import com.example.currencyExchange.data.entity.ExchangeRate;
import com.example.currencyExchange.data.repository.ExchangeRateRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class StoreRateServiceImplTest {

	@Mock
	ExchangeRateRepository exchangeRateRepository;

	StoreRateServiceImpl exchangeRateService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		this.exchangeRateService = new StoreRateServiceImpl(this.exchangeRateRepository);
	}

	@Test
	public void getLastestRate() {
		//given
		ExchangeRate exchangeRate = DefaultEntityUtils.sampleRate();
		when(exchangeRateRepository.findTopByOrderByDateDesc()).thenReturn(Optional.of(exchangeRate));

		//when
		final Optional<ExchangeRate> rateRet = exchangeRateService.getLastestRate();

		//then
		assertTrue(rateRet.isPresent());
		assertThat(rateRet.get(), is(exchangeRate));
		verify(exchangeRateRepository, Mockito.times(1)).findTopByOrderByDateDesc();
		verifyNoMoreInteractions(exchangeRateRepository);
	}

	@Test
	public void getRatesInRange() {
		//given
		List<ExchangeRate> exchangeRate = DefaultEntityUtils.sampleRateList();
		when(exchangeRateRepository.findByDateBetweenOrderByDateAsc(exchangeRate.get(0).getDate(), exchangeRate.get(1).getDate())).thenReturn(exchangeRate);

		//when
		final List<ExchangeRate> rateRet = exchangeRateService.getRatesInRange(exchangeRate.get(0).getDate(), exchangeRate.get(1).getDate());

		//then
		assertThat(rateRet, notNullValue());
		assertThat(rateRet, is(exchangeRate));
		verify(exchangeRateRepository, Mockito.times(1)).findByDateBetweenOrderByDateAsc(any(), any());
		verifyNoMoreInteractions(exchangeRateRepository);
	}

	@Test
	public void saveRate() {
		//given
		ExchangeRate exchangeRate = DefaultEntityUtils.sampleRate();

		//when
		exchangeRateService.saveRate(exchangeRate);

		//then
		verify(exchangeRateRepository, Mockito.times(1)).saveAndFlush(exchangeRate);
		verifyNoMoreInteractions(exchangeRateRepository);
	}
}