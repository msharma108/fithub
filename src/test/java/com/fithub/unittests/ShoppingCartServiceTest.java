package com.fithub.unittests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.fithub.domain.ProductDTO;
import com.fithub.service.shoppingcart.ShoppingCartService;
import com.fithub.service.shoppingcart.ShoppingCartServiceImpl;
import com.fithub.shoppingcart.ShoppingCart;

public class ShoppingCartServiceTest {

	private ShoppingCart shoppingCart;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void beforeTest() {
		this.shoppingCart = new ShoppingCart();
	}

	@Test
	public void updateShoppingCartAddsProductToCartAndUpdatesCartCostIfProductInStockAndNotInCart() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 2;
		BigDecimal productPrice = BigDecimal.TEN;
		BigDecimal expectedCartCost = (productPrice.multiply(shoppingCart.getCartTaxRate())).add(productPrice);
		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity, productPrice);
		String shoppingCartOperationType = "addToCart";

		shoppingCartService.updateShoppingCart(shoppingCart, productDTO, shoppingCartOperationType, BigDecimal.ZERO,
				BigDecimal.ZERO);
		// Assert shopping cart has 1 item
		assertEquals("Shopping cart empty, product not added", 1, shoppingCart.getCartProductList().size());
		assertEquals("Shopping cart cost not updated", expectedCartCost, shoppingCart.getCartTotalCost());
	}

	@Test
	public void updateShoppingCartDoesNotAddProductToCartIfProductNotInStock() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 0;
		BigDecimal productPrice = BigDecimal.TEN;
		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity, productPrice);
		String shoppingCartOperationType = "addToCart";

		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(
				String.format("Quantity in cart: %s for product :%s cannot exceed the quantity in stock: %s",
						productDTO.getQuantityInCart().toPlainString(), productDTO.getProductName(),
						productDTO.getStockQuantity()));
		shoppingCartService.updateShoppingCart(shoppingCart, productDTO, shoppingCartOperationType, BigDecimal.ZERO,
				BigDecimal.ZERO);

	}

	@Test
	public void updateShoppingCartRemovesProductFromCartAndUpdatesCartCostIfProductInCart() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 2;
		String shoppingCartOperationType = "removeFromCart";
		BigDecimal productPrice = BigDecimal.TEN;
		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity, productPrice);
		// setup test data by adding product to cart
		shoppingCart.getCartProductList().add(productDTO);
		shoppingCart.setCartCost(productPrice);
		shoppingCart.setCartTax(shoppingCart.getCartCost().multiply(shoppingCart.getCartTaxRate()));
		shoppingCart.setCartTotalCost(shoppingCart.getCartTax().add(shoppingCart.getCartCost()));

		shoppingCartService.updateShoppingCart(shoppingCart, productDTO, shoppingCartOperationType,
				productDTO.getPrice(), BigDecimal.ONE);

		// Assert shopping cart has 1 item
		assertEquals("Product not removed from shopping cart", 0, shoppingCart.getCartProductList().size());
		assertEquals("Shopping cart cost not updated", BigDecimal.ZERO.setScale(2, 0), shoppingCart.getCartTotalCost());

	}

	public ProductDTO prepareProductDTOForTest(int stockQuantity, BigDecimal productPrice) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setStockQuantity(stockQuantity);
		productDTO.setQuantityInCart(BigDecimal.ZERO);
		productDTO.setPrice(productPrice);
		productDTO.setProductName("productBeingAddedFirstTimeToCart");
		return productDTO;
	}

}
