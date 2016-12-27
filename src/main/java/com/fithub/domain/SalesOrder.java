package com.fithub.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the sales_order database table.
 * 
 */
@Entity
@Table(name = "sales_order")
@NamedQuery(name = "SalesOrder.findAll", query = "SELECT s FROM SalesOrder s")
public class SalesOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sales_order_id", unique = true, nullable = false)
	private int salesOrderId;

	@Column(nullable = false, length = 100)
	private String address;

	@Column(nullable = false, length = 50)
	private String city;

	@Column(nullable = false, length = 30)
	private String country;

	@Column(nullable = false, length = 100)
	private String email;

	@Column(nullable = false, length = 20)
	private String phone;

	@Column(nullable = false, length = 20)
	private String province;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sales_order_creation_date", nullable = false)
	private Date salesOrderCreationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sales_order_edit_date")
	private Date salesOrderEditDate;

	@Column(name = "sales_order_edited_by_user", length = 45)
	private String salesOrderEditedByUser;

	@Column(name = "stripe_charge_id", length = 60)
	private String stripeChargeId;

	@Column(name = "payment_status", length = 45)
	private String paymentStatus;

	@Column(length = 20)
	private String status;

	@Column(name = "sales_order_total_cost", precision = 10)
	private BigDecimal salesOrderTotalCost;

	@Column(name = "shipping_charge", precision = 10)
	private BigDecimal shippingCharge;

	@Column(precision = 10)
	private BigDecimal tax;

	@Column(name = "tracking_number", length = 80)
	private String trackingNumber;

	@Column(nullable = false, length = 20)
	private String zip;

	// bi-directional many-to-one association to SalesOrderDetail
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sales_order_detail_id", nullable = false)
	private SalesOrderDetail salesOrderDetail;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_name", nullable = false)
	private User user;

	public SalesOrder() {
	}

	public int getSalesOrderId() {
		return this.salesOrderId;
	}

	public void setSalesOrderId(int salesOrderId) {
		this.salesOrderId = salesOrderId;
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

	public BigDecimal getSalesOrderTotalCost() {
		return this.salesOrderTotalCost;
	}

	public void setSalesOrderTotalCost(BigDecimal salesOrderTotalCost) {
		this.salesOrderTotalCost = salesOrderTotalCost;
	}

	public BigDecimal getShippingCharge() {
		return this.shippingCharge;
	}

	public void setShippingCharge(BigDecimal shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	public BigDecimal getTax() {
		return this.tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public String getTrackingNumber() {
		return this.trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public SalesOrderDetail getSalesOrderDetail() {
		return this.salesOrderDetail;
	}

	public void setSalesOrderDetail(SalesOrderDetail salesOrderDetail) {
		this.salesOrderDetail = salesOrderDetail;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getSalesOrderCreationDate() {
		return salesOrderCreationDate;
	}

	public void setSalesOrderCreationDate(Date salesOrderCreationDate) {
		this.salesOrderCreationDate = salesOrderCreationDate;
	}

	public Date getSalesOrderEditDate() {
		return salesOrderEditDate;
	}

	public void setSalesOrderEditDate(Date salesOrderEditDate) {
		this.salesOrderEditDate = salesOrderEditDate;
	}

	public String getSalesOrderEditedByUser() {
		return salesOrderEditedByUser;
	}

	public void setSalesOrderEditedByUser(String salesOrderEditedByUser) {
		this.salesOrderEditedByUser = salesOrderEditedByUser;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}