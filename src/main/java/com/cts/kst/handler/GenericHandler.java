package com.cts.kst.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.cts.kst.controller.FlowComponent;
import com.cts.kst.controller.RouterComponent;
import com.cts.kst.entity.KeystoneParam;

@Component
public class GenericHandler implements org.springframework.integration.handler.GenericHandler<Message<?>>{

	
	private static final Logger log = LoggerFactory.getLogger(GenericHandler.class);

	@Autowired
	private KafkaTemplate<Object,Object> kafkaTemplate;
	
	
	@Override
	public Object handle(Message<?> payload, MessageHeaders headers) {
		log.info("Inside Generic Handler handle method");
		
		String sourceSystemCode = headers.get("ROUTER_TOPIC").toString();
		KeystoneParam param = (KeystoneParam) headers.get("KEYSTONE_PARAM");
		FlowComponent fc =  param.getFlows().stream()
				.filter(f -> f.getEndpoint().equalsIgnoreCase("router"))
				.findFirst().orElseThrow(RuntimeException::new);
		
		  RouterComponent routerComponent =fc.getRouters().stream() .filter(f ->
		  f.getAttributeValue().equalsIgnoreCase(sourceSystemCode))
		  .findFirst().orElseThrow(RuntimeException::new);
		 
		
		String topic = routerComponent.getName();
		kafkaTemplate.send(topic,"random string", payload.getPayload());
		
		log.info("Message publised Successfully to topic {}",topic);
		
		return new Message<Object>() {
			@Override
			public Object getPayload() {return payload.getPayload(); };
			
			@Override
			public MessageHeaders getHeaders() { return payload.getHeaders(); };
		};
	}
	
	
}
