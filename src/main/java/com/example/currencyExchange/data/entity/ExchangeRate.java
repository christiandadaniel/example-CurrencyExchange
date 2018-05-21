package com.example.currencyExchange.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;

	private BigDecimal rate;

}
