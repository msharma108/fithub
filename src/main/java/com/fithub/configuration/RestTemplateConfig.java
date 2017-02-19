package com.fithub.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for Spring's rest template used for REST Api consumption
 *
 */
@Configuration
public class RestTemplateConfig {

	/**
	 * Method builds a Rest Template Bean
	 * 
	 * @param builder
	 *            Builder object for building rest template and can be
	 *            configured as per needs
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
