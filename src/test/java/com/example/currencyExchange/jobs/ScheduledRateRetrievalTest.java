package com.example.currencyExchange.jobs;

import com.example.currencyExchange.data.entity.ExchangeRate;
import com.example.currencyExchange.service.CurrentRateService;
import com.example.currencyExchange.service.StoreRateService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ScheduledRateRetrievalTest {

	@Mock
	StoreRateService storeRateService;
	@Mock
	CurrentRateService currentRateService;

	ScheduledRateRetrieval scheduledRateRetrieval;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		this.scheduledRateRetrieval = new ScheduledRateRetrieval(this.currentRateService, this.storeRateService);
	}

	@Test
	public void retrieveAndStore() {
		//given
		BigDecimal currentRate = new BigDecimal(1.3);
		when(currentRateService.retrieveCurrentRate()).thenReturn(currentRate);

		//when
		scheduledRateRetrieval.retrieveAndStore();

		//then
		verify(currentRateService, Mockito.times(1)).retrieveCurrentRate();
		verifyNoMoreInteractions(currentRateService);

		ArgumentCaptor<ExchangeRate> captor = ArgumentCaptor.forClass(ExchangeRate.class);
		verify(storeRateService, Mockito.times(1)).saveRate(captor.capture());
		ExchangeRate rate = captor.getValue();
		assertThat(rate, notNullValue());
		assertThat(rate.getRate(), is(currentRate));
		verifyNoMoreInteractions(storeRateService);
	}
}