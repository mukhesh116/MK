package com.mufg.us.amh.vln_ced_401.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mufg.us.amh.vln_ced_401.processer.CANLOANProcessor;


@Component
public class CANLOANRoute extends RouteBuilder {

	@Autowired
	private CANLOANProcessor processor;
	
	@Override
	public void configure() throws Exception {
		from("quartz://scheduler_name?cron=0 0/1 * 1/1 * ? *")
	    		.routeId("cron-scheduler")
				.doTry()
				  .process(processor)
	    .to("file://target/output")
				  .doCatch(Exception.class)
		.end();
	}

}
