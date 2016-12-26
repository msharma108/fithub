package com.fithub.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * Data Transfer Object class for Product
 * 
 */
public class ProductDTO {

	@NotEmpty
	@Size(min = 10)
	private String ldesc;

	@NotNull
	private Date manufactureDate;

	@NotNull
	private Date expiryDate;

	private String flavor;

	private BigDecimal price;

	private byte[] mainImage;

	@NotEmpty
	private String productCategory;

	@NotEmpty
	@Size(min = 3, max = 45)
	private String productName;

	private String rating;

	private Date registrationDate;

	@NotEmpty
	@Size(min = 3, max = 300)
	private String sdesc;

	@NotNull
	private int stockQuantity;

	@NotNull
	private int quantitySold;

	private MultipartFile thumbImage;

	private byte[] thumbImageAsByteArray;

	private transient String base64imageFile;

	private BigDecimal quantityInCart = BigDecimal.ZERO;

	private BigDecimal weight;

	private boolean isEditable = false;
	private boolean isProductDeleted = false;

	private String productNameBeforeEdit;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLdesc() {
		return ldesc;
	}

	public void setLdesc(String ldesc) {
		this.ldesc = ldesc;
	}

	public Date getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public byte[] getMainImage() {
		return mainImage;
	}

	public void setMainImage(byte[] mainImage) {
		this.mainImage = mainImage;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getSdesc() {
		return sdesc;
	}

	public void setSdesc(String sdesc) {
		this.sdesc = sdesc;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public int getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(int quantitySold) {
		this.quantitySold = quantitySold;
	}

	public MultipartFile getThumbImage() {
		return thumbImage;
	}

	public void setThumbImage(MultipartFile thumbImage) {
		this.thumbImage = thumbImage;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public boolean getIsEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public String getBase64imageFile() {
		return base64imageFile;
	}

	public void setBase64imageFile(String base64imageFile) {
		this.base64imageFile = base64imageFile;
	}

	public byte[] getThumbImageAsByteArray() {
		return thumbImageAsByteArray;
	}

	public void setThumbImageAsByteArray(byte[] thumbImageAsByteArray) {
		this.thumbImageAsByteArray = thumbImageAsByteArray;
	}

	public BigDecimal getQuantityInCart() {
		return quantityInCart;
	}

	public void setQuantityInCart(BigDecimal quantityInCart) {
		this.quantityInCart = quantityInCart;
	}

	public boolean isProductDeleted() {
		return isProductDeleted;
	}

	public void setProductDeleted(boolean isProductDeleted) {
		this.isProductDeleted = isProductDeleted;
	}

	public String getProductNameBeforeEdit() {
		return productNameBeforeEdit;
	}

	public void setProductNameBeforeEdit(String productNameBeforeEdit) {
		this.productNameBeforeEdit = productNameBeforeEdit;
	}

}
