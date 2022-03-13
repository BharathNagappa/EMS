package com.ems.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class EMSConfiguration {
	
	/*
	 * @Value("${rest.baseUrl.userService}") String baseUrl;
	 * 
	 * public String getBaseUrl() { return baseUrl; }
	 */
	@LoadBalanced
	@Bean
	public RestTemplate initializeWebClient()
	{
		return  new RestTemplate();
	}

}
