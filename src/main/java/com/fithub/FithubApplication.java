package com.fithub;

/**
 * Entry point for the Spring boot application
 *
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class FithubApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FithubApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FithubApplication.class);
	}

}
