package com.fithub.configuration;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Enables support for both http and https
 *
 */
@Configuration
public class TomcatMultiConnectorConfig {

	// Reference:-
	// https://www.drissamri.be/blog/java/enable-https-in-spring-boot/

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcatContainer = new TomcatEmbeddedServletContainerFactory();
		tomcatContainer.addAdditionalTomcatConnectors(createBasicConnector());
		return tomcatContainer;
	}

	private Connector createBasicConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setPort(8080);
		connector.setScheme("http");
		connector.setSecure(false);
		return connector;
	}

}
