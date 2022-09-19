package com.cts.kst.config.manager;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.MessageProcessingHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.cts.kst.controller.FlowComponent;
import com.cts.kst.entity.KeystoneParam;
import com.cts.kst.handler.GenericHandler;


@Component
public class TemplateConfigManager {

	private static final Logger log = LoggerFactory.getLogger(TemplateConfigManager.class);
	
	@Autowired
	private IntegrationFlowContext integrationFlowContext;
	
	@Autowired
	private KafkaTemplate<String,Object> kafkaTemplate;
	
	@Autowired
	private ConsumerFactory<String,Object> consumerFactory;
	
	@Autowired
	GenericHandler genericHandler;
	
	private KeystoneParam keystoneParam;

	@Bean
	@Lazy
	public HeaderEnricher keystoneParam() {
		return new HeaderEnricher(
				Collections.singletonMap("KEYSTONE_PARAM",
						new StaticHeaderValueMessageProcessor<>(keystoneParam))
		);
	}
	
	@Bean
	@Lazy
	public HeaderEnricher sorHeaderEnricher() {
		return new HeaderEnricher(
				Collections.singletonMap("ROUTER_TOPIC", 
						new MessageProcessingHeaderValueMessageProcessor(sorHeader())
				));
	}

	private Object sorHeader() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void configure(KeystoneParam param) {
		keystoneParam = param;
		log.info("test {}",integrationFlowContext.getRegistry().keySet());
		IntegrationFlowBuilder flowBuilder = IntegrationFlows.from(
				Kafka.messageDrivenChannelAdapter(consumerFactory,getTopic(param)).id("keystone-kafka-consumer"))
				.transform(keystoneParam())
				.log(LoggingHandler.Level.INFO,getClass().getName(), m-> String.format("Received Request: %s", m ));
		
		param.getFlows().stream()
		.filter(f->f.getEndpoint().equalsIgnoreCase("Component"));
		
		if(!param.getRouters().isEmpty()) {
			flowBuilder.transform(sorHeaderEnricher()).handle(genericHandler)
			.log(LoggingHandler.Level.INFO, getClass().getName(), m-> String.format("Processed Successfully"));
		}
		integrationFlowContext.registration(flowBuilder.get()).register();
	}

	private String getTopic(KeystoneParam param) {
		FlowComponent flowComponent = param.getFlows().stream()
				.filter(f-> f.getEndpoint().equalsIgnoreCase("Topic"))
				.findFirst().orElseThrow(RuntimeException::new);
		
		return flowComponent.getName();
	}
}
