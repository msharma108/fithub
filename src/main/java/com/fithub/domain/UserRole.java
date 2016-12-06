package com.fithub.domain;

/**
 * Enumeration for the type of roles in the application
 * 
 */
public enum UserRole {

	CUSTOMER("Customer"), ADMIN("Admin");

	private final String role;

	UserRole(String role) {
		this.role = role;
	}

	public String getRoleAsString() {
		return role;
	}

}
