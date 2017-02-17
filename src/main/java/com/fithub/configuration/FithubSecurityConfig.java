package com.fithub.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring Security Configuration class that governs access to resources based on
 * role
 *
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FithubSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(FithubSecurityConfig.class);
	private UserDetailsService userDetailsService;

	@Autowired
	public FithubSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		LOG.debug("Role based URL filtering");
		http.authorizeRequests()
				.antMatchers("/", "/product/**", "/*/login", "/logout", "/userTaskSuccess", "/viewProducts/**",
						"/userSave", "/home", "/shoppingCart/**", "/constructUrlForProductOperations/**",
						"/searchProduct/**", "/contactUs")
				.permitAll().antMatchers("/admin/***").hasAuthority("ADMIN").antMatchers("/userRegister").anonymous()
				.antMatchers("/passwordRetrieval").anonymous().antMatchers("/admin/userRegister").hasRole("ADMIN")
				.anyRequest().fullyAuthenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/")
				.failureUrl("/login?failure").usernameParameter("userName").passwordParameter("password").permitAll()
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccess").permitAll();
		;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	public void configure(AuthenticationManagerBuilder authentication) throws Exception {
		LOG.debug("Authentication about to begin");
		authentication.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
