package com.example.currencyExchange.data.repository;

import com.example.currencyExchange.DefaultEntityUtils;
import com.example.currencyExchange.data.entity.ExchangeRate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ExchangeRateRepositoryTest {

	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	@Test
	@DirtiesContext
	public void findByDateBetweenOrderByDateAsc() {
		//given
		List<ExchangeRate> sampleRates = DefaultEntityUtils.sampleRateList();

		sampleRates.forEach((rate) -> exchangeRateRepository.saveAndFlush(rate));

		//when
		ExchangeRate middle1 = sampleRates.get(1);
		ExchangeRate middle2 = sampleRates.get(2);
		List<ExchangeRate> rateList = exchangeRateRepository.findByDateBetweenOrderByDateAsc(middle1.getDate(), middle2.getDate());

		//then
		assertThat(rateList, notNullValue());
		assertThat(rateList, hasSize(2));
		assertThat(rateList.get(0).getDate(), equalTo(middle1.getDate()));
		assertThat(rateList.get(0).getRate(), equalTo(middle1.getRate()));
		assertThat(rateList.get(1).getDate(), equalTo(middle2.getDate()));
		assertThat(rateList.get(1).getRate(), equalTo(middle2.getRate()));
	}

	@Test
	@DirtiesContext
	public void findTopByOrderByDateDesc() {
		//given
		List<ExchangeRate> sampleRates = DefaultEntityUtils.sampleRateList();

		sampleRates.forEach((rate) -> exchangeRateRepository.saveAndFlush(rate));

		//when
		ExchangeRate last = sampleRates.get(3);
		Optional<ExchangeRate> rate = exchangeRateRepository.findTopByOrderByDateDesc();

		//then
		assertTrue(rate.isPresent());
		assertThat(rate.get().getDate(), equalTo(last.getDate()));
		assertThat(rate.get().getRate(), equalTo(last.getRate()));
	}
}