package com.spring.currencyconversion;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange-service" , url = "localhost:8000/")
//To load balance just remove the url 
@FeignClient(name = "currency-exchange-service")
public interface CurrencyExchangeProxy  {

	

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion getCurrencyexchange(@PathVariable String from, 
														  @PathVariable String to);
		
}
