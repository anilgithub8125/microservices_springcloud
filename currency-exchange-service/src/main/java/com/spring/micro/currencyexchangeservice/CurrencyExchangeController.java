package com.spring.micro.currencyexchangeservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private CurrencyExchangeRepo repository;

	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange getCurrencyexchange(@PathVariable String from, @PathVariable String to) {
		//CurrencyExchange currencyExchange= new CurrencyExchange(10000L, "USD", "INR", BigDecimal.valueOf(50));
		CurrencyExchange currencyExchange= repository.findByFromAndTo(from, to);
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		
		if(currencyExchange==null) {
			throw new RuntimeException("Unable to find the data for "+from+" "+to);
		}
		
		return currencyExchange;
	}
}
