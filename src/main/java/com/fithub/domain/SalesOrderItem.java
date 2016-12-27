package com.fithub.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the sales_order_item database table.
 * 
 */
@Entity
@Table(name = "sales_order_item")
@NamedQuery(name = "SalesOrderItem.findAll", query = "SELECT s FROM SalesOrderItem s")
public class SalesOrderItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sales_order_item_id", unique = true, nullable = false)
	private int salesOrderItemId;

	// bi-directional many-to-one association to Product
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	// bi-directional many-to-one association to SalesOrder
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sales_order_id", nullable = false)
	private SalesOrder salesOrder;

	public SalesOrderItem() {
	}

	public int getSalesOrderItemId() {
		return this.salesOrderItemId;
	}

	public void setSalesOrderItemId(int salesOrderItemId) {
		this.salesOrderItemId = salesOrderItemId;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public SalesOrder getSalesOrder() {
		return this.salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

}