package com.fithub.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
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

	@Column(name = "payment_status", length = 45)
	private String paymentStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sales_order_creation_date")
	private Date salesOrderCreationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sales_order_edit_date")
	private Date salesOrderEditDate;

	@Column(name = "sales_order_edited_by_user", length = 45)
	private String salesOrderEditedByUser;

	@Column(name = "sales_order_total_cost", precision = 10, scale = 2)
	private BigDecimal salesOrderTotalCost;

	@Column(name = "shipping_charge", precision = 10)
	private BigDecimal shippingCharge;

	@Column(length = 20)
	private String status;

	@Column(name = "stripe_charge_id", length = 60)
	private String stripeChargeId;

	@Column(precision = 10, scale = 2)
	private BigDecimal tax;

	@Column(name = "tracking_number", length = 80)
	private String trackingNumber;

	// bi-directional many-to-one association to User
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// bi-directional many-to-one association to ShippingAddress
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "shipping_address_id", nullable = false)
	private ShippingAddress shippingAddress;

	// bi-directional many-to-one association to SalesOrderItem
	@OneToMany(mappedBy = "salesOrder")
	private List<SalesOrderItem> salesOrderItems;

	public SalesOrder() {
	}

	public int getSalesOrderId() {
		return this.salesOrderId;
	}

	public void setSalesOrderId(int salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getSalesOrderCreationDate() {
		return this.salesOrderCreationDate;
	}

	public void setSalesOrderCreationDate(Date salesOrderCreationDate) {
		this.salesOrderCreationDate = salesOrderCreationDate;
	}

	public Date getSalesOrderEditDate() {
		return this.salesOrderEditDate;
	}

	public void setSalesOrderEditDate(Date salesOrderEditDate) {
		this.salesOrderEditDate = salesOrderEditDate;
	}

	public String getSalesOrderEditedByUser() {
		return this.salesOrderEditedByUser;
	}

	public void setSalesOrderEditedByUser(String salesOrderEditedByUser) {
		this.salesOrderEditedByUser = salesOrderEditedByUser;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStripeChargeId() {
		return this.stripeChargeId;
	}

	public void setStripeChargeId(String stripeChargeId) {
		this.stripeChargeId = stripeChargeId;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ShippingAddress getShippingAddress() {
		return this.shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public List<SalesOrderItem> getSalesOrderItems() {
		return this.salesOrderItems;
	}

	public void setSalesOrderItems(List<SalesOrderItem> salesOrderItems) {
		this.salesOrderItems = salesOrderItems;
	}

	public SalesOrderItem addSalesOrderItem(SalesOrderItem salesOrderItem) {
		getSalesOrderItems().add(salesOrderItem);
		salesOrderItem.setSalesOrder(this);

		return salesOrderItem;
	}

	public SalesOrderItem removeSalesOrderItem(SalesOrderItem salesOrderItem) {
		getSalesOrderItems().remove(salesOrderItem);
		salesOrderItem.setSalesOrder(null);

		return salesOrderItem;
	}

}