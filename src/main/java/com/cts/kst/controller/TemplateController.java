package com.cts.kst.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.http.config.EnableIntegrationGraphController;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.kst.config.manager.TemplateConfigManager;
import com.cts.kst.entity.KeystoneParam;
import com.cts.kst.entity.Request;
import com.cts.kst.service.KeystoneService;

@RestController
@CrossOrigin("*")
@EnableIntegrationGraphController
public class TemplateController {

	private static final Logger log = LoggerFactory.getLogger(TemplateController.class);
	
	@Autowired
	private TemplateConfigManager templateManager;
	
	@Autowired
	private KafkaTemplate<Object, Object> kafkaTemplate;
	
	
	@Autowired
	private KeystoneService keystoneService;
	
	@PostMapping("build-flow")
	public ResponseEntity<String> buildFlow(@RequestBody KeystoneParam input){
		log.info("request : {}",input.toString());
		String errorMessage = null;
		errorMessage= keystoneService.validateRequest(input);
		if(errorMessage==null) {
			log.debug("Request is valid");
			input.setCreatedDate(new Date());
			keystoneService.saveKeystoneParam(input);
			templateManager.configure(input);
			return new ResponseEntity<>("Request Published Successfully " ,HttpStatus.OK);
		}
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("get-attributes/{requestName}")
	public ResponseEntity<String> getAttributes(@PathVariable String requestName){
		List<String> fields = new ArrayList<>();
		log.info("request : {}",requestName);
		try {
			/*Object record = getInstance(requestName);
			if(record!=null) {
				Field[] fs = record.getClass().getDeclaredFields();
				for(Field f : fs) {
					log.info("element {}, {}",f.getType(),f.getName());
				}
			}*/
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Invalid Request",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Attributes Retrieved",HttpStatus.OK);
	}
	
	
	@GetMapping("getAllJson")
	public ResponseEntity<List<KeystoneParam>> getAllJson(){
		List<KeystoneParam> list = keystoneService.getAllKeystoneParams();
		return new ResponseEntity<>(list ,HttpStatus.OK);
	}
	
	@PostMapping("produceMessage/{topic}")
	public ResponseEntity<String> produce(@RequestBody Request input,@PathVariable String topic){
		log.info(input.toString());
		kafkaTemplate.send(topic,input);
		return new ResponseEntity<>("Json pushed to topic "+ topic ,HttpStatus.OK);
	}
}
