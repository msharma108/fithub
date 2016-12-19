package com.fithub.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name = "product")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", unique = true, nullable = false)
	private int productId;

	@Temporal(TemporalType.DATE)
	@Column(name = "expiry_date")
	private Date expiryDate;

	@Column(length = 45)
	private String flavor;

	@Column(name = "is_product_deleted")
	private boolean isProductDeleted;

	@Lob
	private String ldesc;

	@Lob
	@Column(name = "main_image")
	private byte[] mainImage;

	@Temporal(TemporalType.DATE)
	@Column(name = "manufacture_date")
	private Date manufactureDate;

	private float price;

	@Column(name = "product_edited_by_user", length = 45)
	private String productEditedByUser;

	@Column(name = "product_name", nullable = false, length = 100)
	private String productName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "product_update_date")
	private Date productUpdateDate;

	@Column(name = "quantity_sold")
	private int quantitySold;

	@Column(length = 20)
	private String rating;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registration_date", nullable = false)
	private Date registrationDate;

	@Column(length = 300)
	private String sdesc;

	@Column(name = "stock_quantity")
	private int stockQuantity;

	@Lob
	@Column(name = "thumb_image")
	private byte[] thumbImage;

	private float weight;

	// bi-directional many-to-one association to OrderDetail
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails;

	// bi-directional many-to-one association to ProductCategory
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id", nullable = false)
	private ProductCategory productCategory;

	public Product() {
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getFlavor() {
		return this.flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public boolean getIsProductDeleted() {
		return this.isProductDeleted;
	}

	public void setIsProductDeleted(boolean isProductDeleted) {
		this.isProductDeleted = isProductDeleted;
	}

	public String getLdesc() {
		return this.ldesc;
	}

	public void setLdesc(String ldesc) {
		this.ldesc = ldesc;
	}

	public byte[] getMainImage() {
		return this.mainImage;
	}

	public void setMainImage(byte[] mainImage) {
		this.mainImage = mainImage;
	}

	public Date getManufactureDate() {
		return this.manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getProductEditedByUser() {
		return this.productEditedByUser;
	}

	public void setProductEditedByUser(String productEditedByUser) {
		this.productEditedByUser = productEditedByUser;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getProductUpdateDate() {
		return this.productUpdateDate;
	}

	public void setProductUpdateDate(Date productUpdateDate) {
		this.productUpdateDate = productUpdateDate;
	}

	public int getQuantitySold() {
		return this.quantitySold;
	}

	public void setQuantitySold(int quantitySold) {
		this.quantitySold = quantitySold;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getSdesc() {
		return this.sdesc;
	}

	public void setSdesc(String sdesc) {
		this.sdesc = sdesc;
	}

	public int getStockQuantity() {
		return this.stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public byte[] getThumbImage() {
		return this.thumbImage;
	}

	public void setThumbImage(byte[] thumbImage) {
		this.thumbImage = thumbImage;
	}

	public float getWeight() {
		return this.weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public List<OrderDetail> getOrderDetails() {
		return this.orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderDetail addOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().add(orderDetail);
		orderDetail.setProduct(this);

		return orderDetail;
	}

	public OrderDetail removeOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().remove(orderDetail);
		orderDetail.setProduct(null);

		return orderDetail;
	}

	public ProductCategory getProductCategory() {
		return this.productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

}