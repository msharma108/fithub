package com.fithub.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

	private String paymentStatus;

	private BigDecimal shippingCharge;

	private BigDecimal tax;

	private String stripeChargeId;

	private String customerUserNameForThisOrder;

	private BigDecimal orderTotalCost;

	private String trackingNumber;

	private String orderStatus = "ORDERED";

	private List<ProductDTO> orderProductList = new ArrayList<ProductDTO>();

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public String getStripeChargeId() {
		return stripeChargeId;
	}

	public void setStripeChargeId(String stripeChargeId) {
		this.stripeChargeId = stripeChargeId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public BigDecimal getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(BigDecimal shippingCharge) {
		this.shippingCharge = shippingCharge;
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

	public String getCustomerUserNameForThisOrder() {
		return customerUserNameForThisOrder;
	}

	public void setCustomerUserNameForThisOrder(String customerNameForThisOrder) {
		this.customerUserNameForThisOrder = customerNameForThisOrder;
	}

	public BigDecimal getOrderTotalCost() {
		return orderTotalCost;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setOrderTotalCost(BigDecimal orderTotalCost) {
		this.orderTotalCost = orderTotalCost;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public List<ProductDTO> getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(List<ProductDTO> orderProductList) {
		this.orderProductList = orderProductList;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
