package com.fithub.domain;

/**
 * Enumeration for the type of roles in the application
 * 
 */
public enum UserRole {
	
	CUSTOMER("Customer"),
	ADMIN("Admin");
	
	private final String role;
	
	 UserRole(String aRole)
	{
		this.role = aRole;
	}

	public String getRole() {
		return role;
	}
 

}
