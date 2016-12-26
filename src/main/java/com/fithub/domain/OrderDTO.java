package com.fithub.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * DTO for order checkout
 *
 */
public class OrderDTO {

	@NotEmpty
	@Size(min = 5, max = 45)
	private String address;

	@NotEmpty
	private String city;

	@NotEmpty
	private String country;

	@NotEmpty
	@Size(min = 3, max = 45)
	private String givenName;

	@NotEmpty
	@Size(min = 3, max = 45)
	private String familyName;

	@Size(min = 10, max = 10)
	private String phone;

	@NotEmpty
	private String province;

	@Size(min = 6, max = 6)
	@NotEmpty
	private String zipcode;

	@Email
	@NotEmpty
	private String email;

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

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
