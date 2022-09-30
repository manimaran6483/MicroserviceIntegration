package com.cts.kst;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cts.kst.config.manager.TemplateConfigManager;
import com.cts.kst.entity.KeystoneParam;
import com.cts.kst.service.KeystoneService;

@SpringBootApplication
public class KafkaDemoApplication {

	@Autowired
	private TemplateConfigManager templateManager;
	
	@Autowired
	private KeystoneService keystoneService;
	
	@PostConstruct
	public void configureRecentConfig() {
		KeystoneParam input = keystoneService.getRecentConfig();
		LoggerFactory.getLogger(KafkaDemoApplication.class).info(input.toString());
		//templateManager.configure(input);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoApplication.class, args);
	}

}
