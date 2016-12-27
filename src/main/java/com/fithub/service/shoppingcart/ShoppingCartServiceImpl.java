package com.fithub.service.shoppingcart;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;

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
			String shoppingCartOperationType, BigDecimal productSubTotalInCart, BigDecimal productQuantityCart) {
		LOG.debug("Inside updateShoppingCart method");

		String cartOperationTypeAddProduct = "addToCart";
		String cartOperationTypeRemoveProduct = "removeFromCart";
		String cartOperationTypeRefreshProductQuantity = "refreshQuantityInCart";
		List<ProductDTO> cartProductList = shoppingCart.getCartProductList();
		boolean productFoundInCartDuringIteration = false;
		boolean isProductRemovedFromCart = false;

		ListIterator<ProductDTO> listIterator = cartProductList.listIterator();
		ProductDTO cartProduct;

		// If the cart is not empty
		if (!cartProductList.isEmpty()) {

			if (shoppingCartOperationType.equals(cartOperationTypeAddProduct)) {

				while (listIterator.hasNext()) {

					cartProduct = listIterator.next();

					if (cartProduct.getProductName().equals(productDTO.getProductName())) {

						// Increase the product's quantity in cart if the
						// product being added already exists in the cart & in
						// stock
						if (cartProduct.getStockQuantity() >= (cartProduct.getQuantityInCart().add(BigDecimal.ONE))
								.intValue()) {

							// Perform update cart for addition of product to
							// cart
							LOG.debug("Existing Product={} being added in the cart", cartProduct.getProductName());
							cartProduct.setQuantityInCart(cartProduct.getQuantityInCart().add(productQuantityCart));
							// Synchronize shopping cart parameters
							shoppingCart = synchShoppingCart(shoppingCart, productDTO, cartOperationTypeAddProduct,
									null);
							productFoundInCartDuringIteration = true;
						} else
							throw new IllegalStateException((String.format(
									"Quantity in cart: %s for product :%s cannot exceed the quantity in stock: %s",
									cartProduct.getQuantityInCart().toPlainString(), cartProduct.getProductName(),
									cartProduct.getStockQuantity())));

					}

				}

				// if the cart is not empty but the product being added is
				// the first of its kind being added to the cart
				if (productFoundInCartDuringIteration == false) {
					LOG.debug("Attemping to add the first occurance of product={} into cart",
							productDTO.getProductName());

					if (productDTO.getStockQuantity() >= (productDTO.getQuantityInCart().add(BigDecimal.ONE))
							.intValue()) {
						productDTO.setQuantityInCart(productQuantityCart);
						cartProductList.add(productDTO);
						// Synchronize shopping cart parameters
						shoppingCart = synchShoppingCart(shoppingCart, productDTO, cartOperationTypeAddProduct, null);
					} else {
						throw new IllegalStateException((String.format(
								"Quantity in cart: %s for product :%s cannot exceed the quantity in stock: %s",
								productDTO.getQuantityInCart().toPlainString(), productDTO.getProductName(),
								productDTO.getStockQuantity())));
					}
				}

			} else if (shoppingCartOperationType.equals(cartOperationTypeRemoveProduct)) {
				LOG.debug("Attempting to delete product from cart");

				isProductRemovedFromCart = cartProductList
						.removeIf((ProductDTO cartProductToBeDeleted) -> cartProductToBeDeleted.getProductName()
								.equals(productDTO.getProductName()));
				if (isProductRemovedFromCart) {
					// Synchronize shopping cart parameters
					shoppingCart = synchShoppingCart(shoppingCart, productDTO, cartOperationTypeRemoveProduct,
							productSubTotalInCart);
				}
			} else if (shoppingCartOperationType.equals(cartOperationTypeRefreshProductQuantity)) {
				// Handling reducing product quantity in cart
				LOG.debug("Attempting to refresh product quantity in cart");
				BigDecimal productQuantityInCart = productQuantityCart;

				if (productQuantityInCart.compareTo(BigDecimal.ONE) < 0) {
					// remove the product from the cart if the entered quantity
					// is less than 1
					isProductRemovedFromCart = cartProductList
							.removeIf((ProductDTO cartProductToBeDeleted) -> cartProductToBeDeleted.getProductName()
									.equals(productDTO.getProductName()));
					if (isProductRemovedFromCart) {
						// Synchronize shopping cart parameters
						shoppingCart = synchShoppingCart(shoppingCart, productDTO, cartOperationTypeRemoveProduct,
								productSubTotalInCart);
					}

				} else {

					while (listIterator.hasNext()) {

						cartProduct = listIterator.next();
						// Refresh the product's quantity in cart if the
						// product already exists in the cart and quantity >=1
						if (cartProduct.getProductName().equals(productDTO.getProductName())) {

							LOG.debug("Existing Product={}'s quantity being refreshed in the cart",
									cartProduct.getProductName());
							BigDecimal productQuantityBeforeRefresh = cartProduct.getQuantityInCart();
							cartProduct.setQuantityInCart(productQuantityInCart);

							if (cartProduct.getQuantityInCart()
									.compareTo((new BigDecimal(cartProduct.getStockQuantity()))) > 0) {

								throw new IllegalStateException((String.format(
										"Quantity in cart: %s for product :%s cannot exceed the quantity in stock: %s",
										cartProduct.getQuantityInCart().toPlainString(), cartProduct.getProductName(),
										cartProduct.getStockQuantity())));
							} else {

								// Difference in cart quantity of a product
								BigDecimal differenceInCartQuantityAfterRefresh = (productQuantityInCart
										.subtract(productQuantityBeforeRefresh));

								// ### if and else if are redundant
								// Shopping Cart cost computation based on the
								// change in quantity of product in cart
								if (differenceInCartQuantityAfterRefresh.compareTo(BigDecimal.ZERO) == 0) {

									return shoppingCart;

								} else {

									// Update cart price based on difference in
									// quantity
									LOG.debug("Product Quantity increased in cart");
									shoppingCart.setCartCost(shoppingCart.getCartCost().add(
											(cartProduct.getPrice().multiply(differenceInCartQuantityAfterRefresh))));
									shoppingCart.setCartTax(
											shoppingCart.getCartTaxRate().multiply(shoppingCart.getCartCost()));
									shoppingCart.setCartTotalCost(
											shoppingCart.getCartCost().add(shoppingCart.getCartTax()));
								}
							}
						}

					}

				}
			}
		}

		// if the cart is empty and a product is being added to the cart
		if (cartProductList.isEmpty() && shoppingCartOperationType.equals(cartOperationTypeAddProduct)) {
			// Add product into an empty shoppingCart
			if (productDTO.getStockQuantity() >= (productDTO.getQuantityInCart().add(BigDecimal.ONE)).intValue()) {
				LOG.debug("Adding product={} to an empty shoppingCart", productDTO.getProductName());
				productDTO.setQuantityInCart(productQuantityCart);
				cartProductList.add(productDTO);
				// Synchronize shopping cart parameters
				shoppingCart = synchShoppingCart(shoppingCart, productDTO, cartOperationTypeAddProduct, null);
			} else {
				throw new IllegalStateException(
						(String.format("Quantity in cart: %s for product :%s cannot exceed the quantity in stock: %s",
								productDTO.getQuantityInCart().toPlainString(), productDTO.getProductName(),
								productDTO.getStockQuantity())));
			}
		}

		return shoppingCart;
	}

	private ShoppingCart synchShoppingCart(ShoppingCart shoppingCart, ProductDTO productDTO, String cartOperationType,
			BigDecimal productSubTotalInCart) {

		String cartOperationTypeAddProduct = "addToCart";
		String cartOperationTypeRemoveProduct = "removeFromCart";

		if (cartOperationType.equals(cartOperationTypeAddProduct)) {

			shoppingCart.setCartCost(shoppingCart.getCartCost().add(productDTO.getPrice()));
			shoppingCart.setCartTax(shoppingCart.getCartTaxRate().multiply(shoppingCart.getCartCost()));
			shoppingCart.setCartTotalCost(shoppingCart.getCartCost().add(shoppingCart.getCartTax()));
		}
		if (cartOperationType.equals(cartOperationTypeRemoveProduct)) {

			shoppingCart.setCartCost(shoppingCart.getCartCost().subtract(productSubTotalInCart));
			shoppingCart.setCartTax(shoppingCart.getCartTaxRate().multiply(shoppingCart.getCartCost()));
			shoppingCart.setCartTotalCost(shoppingCart.getCartCost().add(shoppingCart.getCartTax()));

		}

		return shoppingCart;
	}

}
