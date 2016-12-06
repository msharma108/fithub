package com.fithub.domain;

import org.springframework.security.core.authority.AuthorityUtils;

/**
 * CustomUser implements Spring Security's implementation of UserDetails
 * interface
 *
 */
public class CustomUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	private String role;

	public CustomUser(User user) {
		super(user.getUserName(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole()));
		this.role = user.getRole();
	}

	public String getRole() {
		return role;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	public String getUserName() {
		return super.getUsername();
	}

}
