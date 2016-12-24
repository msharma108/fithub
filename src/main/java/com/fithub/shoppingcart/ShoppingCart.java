package com.fithub.shoppingcart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fithub.domain.ProductDTO;

/**
 * Shopping Cart class for the application
 *
 */
public class ShoppingCart {

	private List<ProductDTO> cartProductList = new ArrayList<ProductDTO>();
	private BigDecimal cartCost = BigDecimal.ZERO;
	private BigDecimal cartTaxRate = BigDecimal.valueOf(0.13);
	private BigDecimal cartTax = BigDecimal.ZERO;
	private BigDecimal cartTotalCost = BigDecimal.ZERO;

	public List<ProductDTO> getCartProductList() {
		return cartProductList;
	}

	public void setCartProductList(List<ProductDTO> cartProductList) {
		this.cartProductList = cartProductList;
	}

	public BigDecimal getCartCost() {
		return cartCost;
	}

	public void setCartCost(BigDecimal cartCost) {
		this.cartCost = cartCost;
	}

	public BigDecimal getCartTax() {
		return cartTax;
	}

	public void setCartTax(BigDecimal cartTax) {
		this.cartTax = cartTax;
	}

	public BigDecimal getCartTotalCost() {
		return cartTotalCost;
	}

	public void setCartTotalCost(BigDecimal cartTotalCost) {
		this.cartTotalCost = cartTotalCost;
	}

	public BigDecimal getCartTaxRate() {
		return cartTaxRate;
	}

	public void setCartTaxRate(BigDecimal cartTaxRate) {
		this.cartTaxRate = cartTaxRate;
	}

}
