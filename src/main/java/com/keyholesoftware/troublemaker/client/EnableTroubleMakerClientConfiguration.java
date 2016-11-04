package com.keyholesoftware.troublemaker.client;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableConfigurationProperties(TroubleMakerClientProperties.class)
public class EnableTroubleMakerClientConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	public TroubleMakerClientController troubleMakerClientController() {
		return new TroubleMakerClientController();
	}

	@Bean
	public TroubleMakerClientService troubleMakerClientService() {
		return new TroubleMakerClientService();
	}
}