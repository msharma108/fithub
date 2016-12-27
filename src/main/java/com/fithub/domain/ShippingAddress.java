package com.fithub.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the shipping_address database table.
 * 
 */
@Entity
@Table(name = "shipping_address")
@NamedQuery(name = "ShippingAddress.findAll", query = "SELECT s FROM ShippingAddress s")
public class ShippingAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shipping_address_id", unique = true, nullable = false)
	private int shippingAddressId;

	@Column(length = 100)
	private String address;

	@Column(length = 50)
	private String city;

	@Column(length = 30)
	private String country;

	@Column(length = 100)
	private String email;

	@Column(length = 20)
	private String phone;

	@Column(length = 20)
	private String province;

	@Column(length = 20)
	private String zip;

	// bi-directional many-to-one association to SalesOrder
	@OneToMany(mappedBy = "shippingAddress")
	private List<SalesOrder> salesOrders;

	public ShippingAddress() {
	}

	public int getShippingAddressId() {
		return this.shippingAddressId;
	}

	public void setShippingAddressId(int shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<SalesOrder> getSalesOrders() {
		return this.salesOrders;
	}

	public void setSalesOrders(List<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}

	public SalesOrder addSalesOrder(SalesOrder salesOrder) {
		getSalesOrders().add(salesOrder);
		salesOrder.setShippingAddress(this);

		return salesOrder;
	}

	public SalesOrder removeSalesOrder(SalesOrder salesOrder) {
		getSalesOrders().remove(salesOrder);
		salesOrder.setShippingAddress(null);

		return salesOrder;
	}

}