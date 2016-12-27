package com.fithub.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the sales_order_detail database table.
 * 
 */
@Entity
@Table(name = "sales_order_detail")
@NamedQuery(name = "SalesOrderDetail.findAll", query = "SELECT s FROM SalesOrderDetail s")
public class SalesOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sales_order_detail_id", unique = true, nullable = false)
	private int salesOrderDetailId;

	@Column(name = "product_name", nullable = false, length = 45)
	private String productName;

	@Column(name = "product_quantity_sold")
	private int productQuantitySold;

	// bi-directional many-to-one association to SalesOrder
	@OneToMany(mappedBy = "salesOrderDetail")
	private List<SalesOrder> salesOrders;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	public SalesOrderDetail() {
	}

	public int getSalesOrderDetailId() {
		return this.salesOrderDetailId;
	}

	public void setSalesOrderDetailId(int salesOrderDetailId) {
		this.salesOrderDetailId = salesOrderDetailId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductQuantitySold() {
		return this.productQuantitySold;
	}

	public void setProductQuantitySold(int productQuantitySold) {
		this.productQuantitySold = productQuantitySold;
	}

	public List<SalesOrder> getSalesOrders() {
		return this.salesOrders;
	}

	public void setSalesOrders(List<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}

	public SalesOrder addSalesOrder(SalesOrder salesOrder) {
		getSalesOrders().add(salesOrder);
		salesOrder.setSalesOrderDetail(this);

		return salesOrder;
	}

	public SalesOrder removeSalesOrder(SalesOrder salesOrder) {
		getSalesOrders().remove(salesOrder);
		salesOrder.setSalesOrderDetail(null);

		return salesOrder;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}