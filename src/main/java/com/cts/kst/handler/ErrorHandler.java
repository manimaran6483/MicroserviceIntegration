package com.cts.kst.handler;

import org.apache.avro.specific.SpecificRecordBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandler implements GenericHandler<Message<MessagingException>>{

	
	private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

	@Override
	public Object handle(Message<MessagingException> message, MessageHeaders headers) {
		MessagingException messageException = message.getPayload();
		SpecificRecordBase request = (SpecificRecordBase) messageException.getFailedMessage().getPayload();
		
		log.info("Caught Exception {}", messageException.getFailedMessage().getPayload().toString());
		return "Message Processing Failed";
	}
}
