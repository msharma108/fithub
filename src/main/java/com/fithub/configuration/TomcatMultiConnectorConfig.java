package com.fithub.configuration;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Web Container configuration class for Tomcat
 *
 */
@Configuration
@Profile("!cloud")
public class TomcatMultiConnectorConfig {

	// Reference:-
	// https://www.drissamri.be/blog/java/enable-https-in-spring-boot/

	/**
	 * Method returns Servlet Container Bean with configuration that supports
	 * both http and https
	 * 
	 * @return Servlet container Bean
	 */
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcatContainer = new TomcatEmbeddedServletContainerFactory() {

			@Override
			protected void postProcessContext(Context context) {
				// Configure servlet container cache size
				int cacheMaxSize = 50 * 1024;
				StandardRoot standardRoot = new StandardRoot(context);
				standardRoot.setCacheMaxSize(cacheMaxSize);

				// Security constraints for the servlet container
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				// redirecting to Https when site accessed over Http
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);

				// context update
				context.setResources(standardRoot);
				context.addConstraint(securityConstraint);
			}
		};

		tomcatContainer.addAdditionalTomcatConnectors(createBasicConnector());
		return tomcatContainer;
	}

	/**
	 * Method creates a tomcat connector configured to redirect requests to
	 * https
	 * 
	 * @return connector with http and https support
	 */
	private Connector createBasicConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setPort(8080);
		connector.setScheme("http");
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}

}
