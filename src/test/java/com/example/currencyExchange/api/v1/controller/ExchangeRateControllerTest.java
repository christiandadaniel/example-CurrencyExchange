package com.example.currencyExchange.api.v1.controller;

import com.example.currencyExchange.DefaultEntityUtils;
import com.example.currencyExchange.data.entity.ExchangeRate;
import com.example.currencyExchange.service.StoreRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ExchangeRateControllerTest {

	public static final String URL_TEMPLATE = "/api/v1/exchangeRate/";
	public static final String URL_TEMPLATE_RANGE = URL_TEMPLATE + "range";

	private ExchangeRateController exchangeRateController;

	@Mock
	private StoreRateService storeRateService;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.exchangeRateController = new ExchangeRateController(this.storeRateService);
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.exchangeRateController).build();
	}


	@Test
	public void getLatestOK() throws Exception {
		//given
		final ExchangeRate rateRet = DefaultEntityUtils.sampleRate();
		final String rateRetJson = new ObjectMapper().writeValueAsString(rateRet);

		when(this.storeRateService.getLastestRate()).thenReturn(Optional.of(rateRet));

		//when
		final ResultActions perform = mockMvc.perform(get(URL_TEMPLATE));

		//then
		perform.andExpect(status().isOk());
		perform.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		perform.andExpect(content().json(rateRetJson));

		verify(storeRateService, Mockito.times(1)).getLastestRate();
		verifyNoMoreInteractions(storeRateService);
	}

	@Test
	public void getLatestEmpty() throws Exception {
		//given
		when(this.storeRateService.getLastestRate()).thenReturn(Optional.empty());

		//when
		final ResultActions perform = mockMvc.perform(get(URL_TEMPLATE));

		//then
		perform.andExpect(status().isNoContent());
		perform.andExpect(content().string(""));

		verify(storeRateService, Mockito.times(1)).getLastestRate();
		verifyNoMoreInteractions(storeRateService);
	}

	@Test
	public void getRangeOK() throws Exception {
		//given
		final List<ExchangeRate> rateRet = DefaultEntityUtils.sampleRateList();
		final String rateRetJson = new ObjectMapper().writeValueAsString(rateRet);

		Calendar begin = rateRet.get(0).getDate();
		Calendar end = rateRet.get(rateRet.size() - 1).getDate();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		String beginStr = sdf.format(begin.getTime());
		String endStr = sdf.format(end.getTime());

		when(this.storeRateService.getRatesInRange(begin, end)).thenReturn(rateRet);

		//when
		final ResultActions perform = mockMvc.perform(
				get(URL_TEMPLATE_RANGE)
						.param("begin", beginStr)
						.param("end", endStr));

		//then
		perform.andExpect(status().isOk());
		perform.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		perform.andExpect(content().json(rateRetJson));

		verify(storeRateService, Mockito.times(1)).getRatesInRange(any(), any());
		verifyNoMoreInteractions(storeRateService);
	}

	@Test
	public void getRangeNotADate() throws Exception {
		//given
		final List<ExchangeRate> rateRet = DefaultEntityUtils.sampleRateList();

		Calendar begin = rateRet.get(0).getDate();
		Calendar end = rateRet.get(rateRet.size() - 1).getDate();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		String beginStr = sdf.format(begin.getTime());
		String endStr = sdf.format(end.getTime());

		//when
		final ResultActions perform1 = mockMvc.perform(
				get(URL_TEMPLATE_RANGE)
						.param("begin", beginStr));

		final ResultActions perform2 = mockMvc.perform(
				get(URL_TEMPLATE_RANGE)
						.param("end", endStr));

		final ResultActions perform3 = mockMvc.perform(
				get(URL_TEMPLATE_RANGE)
						.param("begin", "")
						.param("end", endStr));

		final ResultActions perform4 = mockMvc.perform(
				get(URL_TEMPLATE_RANGE)
						.param("begin", "NotADate")
						.param("end", endStr));

		//then
		perform1.andExpect(status().isBadRequest());
		perform2.andExpect(status().isBadRequest());
		perform3.andExpect(status().isBadRequest());
		perform4.andExpect(status().isBadRequest());

		verifyZeroInteractions(storeRateService);
	}
}