package com.fithub.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private int userId;

	@Column(length = 45)
	private String address;

	@Column(length = 45)
	private String city;

	@Column(length = 45)
	private String country;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth", nullable = false)
	private Date dateOfBirth;

	@Column(nullable = false, length = 45)
	private String email;

	@Column(name = "family_name", nullable = false, length = 45)
	private String familyName;

	@Column(name = "given_name", nullable = false, length = 45)
	private String givenName;

	@Column(nullable = false, length = 60)
	private String password;

	@Column(name = "payment_mode", length = 1)
	private String paymentMode;

	@Column(length = 45)
	private String phone;

	@Column(length = 45)
	private String province;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registration_date", nullable = false)
	private Date registrationDate;

	@Column(nullable = false, length = 1)
	private String role;

	@Column(length = 20)
	private String sex;

	@Column(name = "user_name", nullable = false, length = 45)
	private String userName;

	@Column(length = 7)
	private String zipcode;

	// bi-directional many-to-one association to SalesOrder
	@OneToMany(mappedBy = "user")
	private List<SalesOrder> salesOrders;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return this.givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPaymentMode() {
		return this.paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public List<SalesOrder> getSalesOrders() {
		return this.salesOrders;
	}

	public void setSalesOrders(List<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}

	public SalesOrder addSalesOrder(SalesOrder salesOrder) {
		getSalesOrders().add(salesOrder);
		salesOrder.setUser(this);

		return salesOrder;
	}

	public SalesOrder removeSalesOrder(SalesOrder salesOrder) {
		getSalesOrders().remove(salesOrder);
		salesOrder.setUser(null);

		return salesOrder;
	}

}