package com.keybank.profile.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Listener {

	public static String queueResponse = null;
	
	@JmsListener(destination = "KB_ID_OUT")
	public void receiveIdMessage(final Message jsonMessage)throws JMSException {
	 	String messageData = null;
		log.info("Received message " + jsonMessage);
		if(jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)jsonMessage;
			messageData = textMessage.getText();
			queueResponse = messageData;
		}
	}

	@JmsListener(destination = "KB_NM_OUT")
	public void receiveNameMessage(final Message jsonMessage)throws JMSException {
	 	String messageData = null;
		log.info("Received message " + jsonMessage);
		if(jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)jsonMessage;
			messageData = textMessage.getText();
			queueResponse = messageData;
		}
	} 
	
}
