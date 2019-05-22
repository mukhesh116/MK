package com.keybank.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KeybankCamelRouter extends RouteBuilder {
	
	
	final String KB_ID_IN = "jms:queue:IN_ID";
	final String KB_ID_OUT = "jms:queue:OUT_ID";
	
	final String KB_NM_IN = "jms:queue:IN_NM";
	final String KB_NM_OUT = "jms:queue:OUT_NM";
	
    @Override
    public void configure() throws Exception {
    	log.info("CamelRouter Starts");
        try {
        	//Queue receives data for BY_ID data and send back to queue
        	from(KB_ID_IN)
        	.process(new KeybankTransform())
        	.to("jolt:/spec_id.json?inputType=JsonString&outputType=JsonString")
        	.to("file://transformed_by_id")
        	.to(KB_ID_OUT);
        	//Queue receives data for BY_ID data and send back to queue
        	from(KB_NM_IN)
        	.process(new KeybankTransform())
        	.to("jolt:/spec_nm.json?inputType=JsonString&outputType=JsonString")
        	.to("file://transformed_by_name")
        	.to(KB_NM_OUT);
        	log.info("Transformed routing input json");
        }catch(Exception e){
        	log.error("Error occured while transforming data and the exception is ",e.getMessage());
        } 
        log.info("CamelRouter Ends");
    }
}