package com.fithub.service.customuser;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * An interface for implementing security mechanism or framework for the
 * application
 *
 */
public interface CustomUserService {

	UserDetails loadUserByUsername(String userName);

}
