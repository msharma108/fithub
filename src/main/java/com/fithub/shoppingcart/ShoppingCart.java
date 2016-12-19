package com.fithub.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import com.fithub.domain.ProductDTO;

/**
 * Shopping Cart class for the application
 *
 */
public class ShoppingCart {

	private List<ProductDTO> cartProductList = new ArrayList<ProductDTO>();
	private float cartCost;
	private float cartTax = (float) 0.13;
	private float cartTotalCost;

	public List<ProductDTO> getCartProductList() {
		return cartProductList;
	}

	public void setCartProductList(List<ProductDTO> cartProductList) {
		this.cartProductList = cartProductList;
	}

	public float getCartCost() {
		return cartCost;
	}

	public void setCartCost(float cartCost) {
		this.cartCost = cartCost;
	}

	public float getCartTax() {
		return cartTax;
	}

	public void setCartTax(float cartTax) {
		this.cartTax = cartTax;
	}

	public float getCartTotalCost() {
		return cartTotalCost;
	}

	public void setCartTotalCost(float cartTotalCost) {
		this.cartTotalCost = cartTotalCost;
	}

}
