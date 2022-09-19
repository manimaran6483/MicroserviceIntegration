package com.cts.kst.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationFlowConfig {

	
	@Autowired
	private KeystoneConfig config;
}
