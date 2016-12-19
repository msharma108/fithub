package com.fithub.service.shoppingcart;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fithub.domain.ProductDTO;
import com.fithub.shoppingcart.ShoppingCart;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private final static Logger LOG = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

	@Override
	public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart, ProductDTO productDTO,
			String shoppingCartOperationType) {
		LOG.debug("Inside updateShoppingCart method");

		String CartOperationTypeAddProduct = "addProduct";
		List<ProductDTO> cartProductList = shoppingCart.getCartProductList();

		// If the cart is not empty
		if (!cartProductList.isEmpty()) {

			for (ProductDTO cartProduct : cartProductList) {
				// Increase/Decrease the product's quantity in cart if the
				// product being added/removed already exists in the cart
				if (cartProduct.getProductName().equals(productDTO.getProductName())) {
					if (shoppingCartOperationType.equals(CartOperationTypeAddProduct)) {

						// Perform update cart for addition of product to cart
						LOG.debug("Existing Product={} being added in the cart", cartProduct.getProductName());
						cartProduct.setQuantityInCart(cartProduct.getQuantityInCart() + 1);
						shoppingCart.setCartCost(shoppingCart.getCartCost() + cartProduct.getPrice());
						shoppingCart.setCartTotalCost(
								shoppingCart.getCartCost() + shoppingCart.getCartCost() * shoppingCart.getCartTax());
					} else {

						// Perform update cart for removal of product from cart
						LOG.debug("Existing Product={} being removed from the cart", cartProduct.getProductName());
						cartProduct.setQuantityInCart(cartProduct.getQuantityInCart() - 1);
						shoppingCart.setCartCost(shoppingCart.getCartCost() - cartProduct.getPrice());
						shoppingCart.setCartTotalCost(
								shoppingCart.getCartCost() - shoppingCart.getCartCost() * shoppingCart.getCartTax());
					}

				}
			}

		}
		if (shoppingCartOperationType.equals(CartOperationTypeAddProduct)) {
			// Add product into an empty shoppingCart
			LOG.debug("Adding product={} to an empty shoppingCart", productDTO.getProductName());
			cartProductList.add(productDTO);
			shoppingCart.setCartCost(shoppingCart.getCartCost() + productDTO.getPrice());
			shoppingCart.setCartTotalCost(
					shoppingCart.getCartCost() + shoppingCart.getCartCost() * shoppingCart.getCartTax());
		}

		return shoppingCart;
	}

}
