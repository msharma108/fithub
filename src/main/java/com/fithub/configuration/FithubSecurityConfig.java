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
 * Spring Security Configuration class
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

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		LOG.debug("Role based URL filtering");
        http.authorizeRequests()
                .antMatchers("/", "/product/**", "/*/login").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/register_user").anonymous()
                .antMatchers("/admin/register_user").hasRole("ADMIN")
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?failure")
                .usernameParameter("userName")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();
    }
	
	@Override
	public void configure(AuthenticationManagerBuilder authentication) throws Exception {
		LOG.debug("Authentication about to begin");
		authentication.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
