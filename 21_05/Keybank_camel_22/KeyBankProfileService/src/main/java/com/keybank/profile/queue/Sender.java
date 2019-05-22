package com.keybank.profile.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Sender {

	@Autowired
	JmsTemplate jmsTemplate;
	
	public void processSendingQueue(String content,String inQueue){
       log.info("Sending a message.");
       jmsTemplate.convertAndSend(inQueue,content);
	}

}


