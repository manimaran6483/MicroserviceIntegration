package com.cts.kst.handler;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.buf.StringUtils;
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
import com.cts.kst.entity.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GenericHandler implements org.springframework.integration.handler.GenericHandler<Message<?>> {

	private static final Logger log = LoggerFactory.getLogger(GenericHandler.class);

	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public Object handle(Message<?> payload, MessageHeaders headers) {
		log.info("Inside Generic Handler handle method");
		
		String topic = null;
		ObjectMapper mapper = new ObjectMapper();
		
		Request request= mapper.convertValue(payload.getPayload(), Request.class);
		
		String requestType = request.getRequestType();
		//String requestType = "membertopic";
		
		if(headers.get("ROUTER") !=null) {
			log.info("inside router");
			List<RouterComponent> routers = (List<RouterComponent>) headers.get("ROUTER");
			RouterComponent router = routers.stream().filter(f -> f.getAttributeValue().equalsIgnoreCase(requestType)).findFirst().orElse(null);
			topic = router.getName();
		}else {
			log.info("inside flow");
			KeystoneParam param = (KeystoneParam) headers.get("KEYSTONE_PARAM");
			FlowComponent destinationFlow = param.getFlows().stream()
					.filter(f -> f.getType().equalsIgnoreCase("destination")).findFirst()
					.orElseThrow(RuntimeException::new);
			topic = destinationFlow.getName();
		}
		
		log.info(payload.toString());
		
		if (topic != null) {
			
			kafkaTemplate.send(topic, RandomStringUtils.random(32), payload.getPayload());
			
			log.info("Message publised Successfully to topic {}", topic);
		
		}

		return new Message<Object>() {
			@Override
			public Object getPayload() {
				return payload.getPayload();
			};

			@Override
			public MessageHeaders getHeaders() {
				return payload.getHeaders();
			};
		};
	}

}
