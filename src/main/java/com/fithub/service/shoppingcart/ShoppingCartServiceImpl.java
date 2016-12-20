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
			String shoppingCartOperationType, int productQuantityInCartAfterRefresh) {
		LOG.debug("Inside updateShoppingCart method");

		String cartOperationTypeAddProduct = "addToCart";
		String cartOperationTypeRemoveProduct = "removeFromCart";
		String cartOperationTypeRefreshProductQuantity = "refreshQuantityInCart";
		List<ProductDTO> cartProductList = shoppingCart.getCartProductList();

		// If the cart is not empty
		if (!cartProductList.isEmpty()) {

			if (shoppingCartOperationType.equals(cartOperationTypeAddProduct)) {

				for (ProductDTO cartProduct : cartProductList) {
					// Increase the product's quantity in cart if the
					// product being added already exists in the cart
					if (cartProduct.getProductName().equals(productDTO.getProductName())) {

						// Perform update cart for addition of product to
						// cart
						LOG.debug("Existing Product={} being added in the cart", cartProduct.getProductName());
						cartProduct
								.setQuantityInCart(cartProduct.getQuantityInCart() + productQuantityInCartAfterRefresh);
						shoppingCart.setCartCost(shoppingCart.getCartCost()
								+ (cartProduct.getPrice() * productQuantityInCartAfterRefresh));
						shoppingCart.setCartTotalCost(
								shoppingCart.getCartCost() + (shoppingCart.getCartCost() * shoppingCart.getCartTax()));
					}
					// if the cart is not empty but the product being added is
					// the first of its kind
					else {
						LOG.debug("Adding the first occurance of product={} into cart", productDTO.getProductName());
						cartProductList.add(productDTO);
						shoppingCart.setCartCost(shoppingCart.getCartCost() + productDTO.getPrice());
						shoppingCart.setCartTotalCost(
								shoppingCart.getCartCost() + shoppingCart.getCartCost() * shoppingCart.getCartTax());
					}

				}
			} else if (shoppingCartOperationType.equals(cartOperationTypeRemoveProduct)) {
				cartProductList.removeIf(
						(ProductDTO cartProduct) -> cartProduct.getProductName().equals(productDTO.getProductName()));
			} else if (shoppingCartOperationType.equals(cartOperationTypeRefreshProductQuantity)) {
				// Handling reducing product quantity in cart
				if (productQuantityInCartAfterRefresh < 1) {
					// remove the product from the cart if the entered quantity
					// is less than 1
					cartProductList.removeIf((ProductDTO cartProduct) -> cartProduct.getProductName()
							.equals(productDTO.getProductName()));

				} else {

					for (ProductDTO cartProduct : cartProductList) {
						// Reduce the product's quantity in cart if the
						// product already exists in the cart and quantity >=1
						if (cartProduct.getProductName().equals(productDTO.getProductName())) {

							LOG.debug("Existing Product={}'s quantity being refreshed in the cart",
									cartProduct.getProductName());
							int productQuantityBeforeRefresh = cartProduct.getQuantityInCart();
							cartProduct.setQuantityInCart(productQuantityInCartAfterRefresh);
							// Difference in cart quantity of a product
							int differenceInCartQuantityAfterRefresh = (productQuantityInCartAfterRefresh
									- productQuantityBeforeRefresh);

							// ### if and else if are redundant
							// Shopping Cart cost computation based on the
							// change in quantity of product in cart
							if (differenceInCartQuantityAfterRefresh > 0) {
								// Update cart price based on difference in
								// quantity
								LOG.debug("Product Quantity increased in cart");
								shoppingCart.setCartCost(shoppingCart.getCartCost()
										+ (cartProduct.getPrice() * differenceInCartQuantityAfterRefresh));
								shoppingCart.setCartTotalCost(shoppingCart.getCartCost()
										+ (shoppingCart.getCartCost() * shoppingCart.getCartTax()));

							} else if (differenceInCartQuantityAfterRefresh < 0) {

								// Update cart price based on difference in
								// quantity
								LOG.debug("Product Quantity decreased in cart");
								shoppingCart.setCartCost(shoppingCart.getCartCost()
										+ (cartProduct.getPrice() * differenceInCartQuantityAfterRefresh));
								shoppingCart.setCartTotalCost(shoppingCart.getCartCost()
										+ (shoppingCart.getCartCost() * shoppingCart.getCartTax()));
							} else
								return shoppingCart;

						}

					}

				}
			}

		}
		// if the cart is empty and a product is being added to the cart
		if (shoppingCartOperationType.equals(cartOperationTypeAddProduct)) {
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
