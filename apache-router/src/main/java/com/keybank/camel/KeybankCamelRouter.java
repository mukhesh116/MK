package com.keybank.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KeybankCamelRouter extends RouteBuilder {
	
	
	String keybankQueueIn = "jms:queue:IN";
	String keybankQueueOUT = "jms:queue:OUT_2";
	
    @Override
    public void configure() throws Exception {
    	log.info("CamelRouter Starts");
        try {
        	from(keybankQueueIn)
        	.process(new KeybankTransform())
        	.to("jolt:/spec.json?inputType=JsonString&outputType=JsonString")
        	//.to("file://transformed") working fine. i just commented. If need , uncomment it.
        	.to(keybankQueueOUT);
        	log.info("Transformed routing input json");
        }catch(Exception e){
        	log.error("Error occured ",e.getMessage());
        } 
        log.info("CamelRouter Ends");
    }
}