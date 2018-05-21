package com.example.currencyExchange.data.repository;

import com.example.currencyExchange.data.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

	List<ExchangeRate> findByDateBetweenOrderByDateAsc(Calendar begin, Calendar end);

	Optional<ExchangeRate> findTopByOrderByDateDesc();
}
