package com.keybank.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class KeybankTransform implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("Processer starts");
		String myString = exchange.getIn().getBody(String.class);
		log.info("Transformed data :: " +myString);
		log.info("Processer Ends");
	}

}


