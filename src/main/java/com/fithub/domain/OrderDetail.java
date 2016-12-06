package com.fithub.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the order_detail database table.
 * 
 */
@Entity
@Table(name = "order_detail")
@NamedQuery(name = "OrderDetail.findAll", query = "SELECT o FROM OrderDetail o")
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_detail_id", unique = true, nullable = false)
	private int orderDetailId;

	@Column(name = "product_quantity")
	private int productQuantity;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	// bi-directional many-to-one association to SalesOrder
	@ManyToOne
	@JoinColumn(name = "sales_order_id", nullable = false)
	private SalesOrder salesOrder;

	public OrderDetail() {
	}

	public int getOrderDetailId() {
		return this.orderDetailId;
	}

	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public int getProductQuantity() {
		return this.productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
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