package com.spring.currencyconversion;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}/")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, 
														  @PathVariable String to,
														  @PathVariable BigDecimal quantity) {
		
		HashMap<String, String> uriVaribles=new HashMap<>();
		uriVaribles.put("from", from);
		uriVaribles.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000//currency-exchange/from/{from}/to/{to}", 
				CurrencyConversion.class, uriVaribles);
		
		CurrencyConversion currencyConversion= responseEntity.getBody();
		
		return new CurrencyConversion(currencyConversion.getId(), 
				from, to,currencyConversion.getConversionMultiple(),currencyConversion.getEnvironment(),
				quantity,quantity.multiply(currencyConversion.getConversionMultiple()));
		
	}
	
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}/")
	public CurrencyConversion calculateCurrencyConversionfeign(@PathVariable String from, 
														  @PathVariable String to,
														  @PathVariable BigDecimal quantity) {
		
		CurrencyConversion currencyConversion = proxy.getCurrencyexchange(from, to);
		
		
		return new CurrencyConversion(currencyConversion.getId(), 
				from, to,currencyConversion.getConversionMultiple(),currencyConversion.getEnvironment()+"  feign",
				quantity,quantity.multiply(currencyConversion.getConversionMultiple()));
		
	}
}
