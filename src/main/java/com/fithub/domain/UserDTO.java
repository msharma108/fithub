package com.fithub.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;




/**
 * Data Transfer Object class for user registrations
 * 
 */
public class UserDTO {
	
	
	@NotEmpty
	private String userName = "";
	
	@NotEmpty
	private String address = "";
	
	@NotEmpty
	private String city = "";
	
	@NotEmpty
	private String country = "";

	@Past
	//@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dateOfBirth;

	@Email
	private String email;

	@NotEmpty
	private String fullName = "";

	@NotEmpty
	private String password = "";
	
	@NotEmpty
	private String repeatPassword = "";

	@NotEmpty
	private String paymentMode = "";

	@Size(min=10,max=10)
	private String phone;

	@NotEmpty
	private String province = "";

	@NotNull
	private UserRole role = UserRole.CUSTOMER ;

	
	private String sex;

	
	@Size(min=6,max=6)
	@NotEmpty
	private String zipcode = "";
	
	
	

}
