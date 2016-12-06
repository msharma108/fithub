/**
 * 
 */
package com.fithub.service.authenticateduser;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.fithub.domain.CustomUser;
import com.fithub.domain.User;
import com.fithub.service.user.UserService;

/**
 * This service implements Spring Security's UserDetailsService and provides
 * mechanism to authenticate a user based on userName
 */
@Service
public class CustomUserService implements UserDetailsService {

	private final static Logger LOG = LoggerFactory.getLogger(CustomUserService.class);
	private final UserService userService;

	@Autowired
	public CustomUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Method loads the user based on userName and performs authentication
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws NoSuchElementException {

		LOG.debug("User with userName={} is being authenticated", userName);
		User user = userService.getUserByUsername(userName);
		return new CustomUser(user);
	}

}
