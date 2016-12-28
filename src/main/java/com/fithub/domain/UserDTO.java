package com.fithub.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Data Transfer Object class for User
 * 
 */
public class UserDTO {

	@NotEmpty
	private String userName;

	private String userNameBeforeEdit;

	@NotEmpty
	@Size(min = 5, max = 45)
	private String address;

	@NotEmpty
	private String city;

	@NotEmpty
	private String country;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	@Email
	@NotEmpty
	private String email;

	@NotEmpty
	@Size(min = 3, max = 45)
	private String givenName;

	@NotEmpty
	@Size(min = 3, max = 45)
	private String familyName;

	@NotEmpty
	private String password;

	@NotEmpty
	private String repeatPassword;

	@NotEmpty
	private String paymentMode = "CREDIT";

	@Size(min = 10, max = 10)
	private String phone;

	@NotEmpty
	private String province;

	@NotNull
	private UserRole role = UserRole.CUSTOMER;

	private String sex = "UNDISCLOSED";

	private Date registrationDate;

	@Size(min = 6, max = 6)
	@NotEmpty
	private String zipcode;

	private boolean isEditable = false;

	private boolean isUserDeleted = false;

	// Represents currently logged in User's userName
	private String loggedInUserName;

	// Represents currently logged in User's role set to default CUSTOMER
	private String loggedInUserUserRole = "CUSTOMER";

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public boolean getIsEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public String getLoggedInUserName() {
		return loggedInUserName;
	}

	public void setLoggedInUserName(String loggedInUserName) {
		this.loggedInUserName = loggedInUserName;
	}

	public String getLoggedInUserUserRole() {
		return loggedInUserUserRole;
	}

	public void setLoggedInUserUserRole(String loggedInUserUserRole) {
		this.loggedInUserUserRole = loggedInUserUserRole;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public boolean getIsUserDeleted() {
		return isUserDeleted;
	}

	public void setIsUserDeleted(boolean isUserDeleted) {
		this.isUserDeleted = isUserDeleted;
	}

	public String getUserNameBeforeEdit() {
		return userNameBeforeEdit;
	}

	public void setUserNameBeforeEdit(String userNameBeforeEdit) {
		this.userNameBeforeEdit = userNameBeforeEdit;
	}

}
