package com.fithub.service.shoppingcart;

import java.math.BigDecimal;

import com.fithub.domain.ProductDTO;
import com.fithub.shoppingcart.ShoppingCart;

/**
 * Service for helping with shopping cart related operations
 *
 */
public interface ShoppingCartService {

	/**
	 * Method performs operations on the shopping cart based on the provided
	 * shoppingCartOperationType parameter
	 * 
	 * @param shoppingCart
	 *            Existing ShoppingCart associated with the session
	 * @param productDTO
	 *            representing the product that is affecting the present state
	 *            of the shoppingCart
	 * @param shoppingCartOperationType
	 *            The type of shoppingCartOperation to be performed
	 * @param productSubTotalInCart
	 *            Subtotal of the product in Cart product in cart
	 * @param productQuantityInCart
	 *            Quantity of product in cart
	 * @return the updated shoppingCart
	 */
	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart, ProductDTO productDTO, String shoppingCartOperationType,
			BigDecimal productSubTotalInCart, BigDecimal productQuantityInCart);

}
