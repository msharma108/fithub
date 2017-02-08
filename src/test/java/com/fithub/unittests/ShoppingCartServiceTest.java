package com.fithub.unittests;

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
	public void updateShoppingCartAddsProductToCartIfProductInStockAndNotInCart() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 2;
		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity);
		String shoppingCartOperationType = "addToCart";

		shoppingCartService.updateShoppingCart(shoppingCart, productDTO, shoppingCartOperationType, BigDecimal.ZERO,
				BigDecimal.ZERO);

	}

	@Test
	public void updateShoppingCartDoesNotAddProductToCartIfProductNotInStock() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 0;
		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity);
		String shoppingCartOperationType = "addToCart";

		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(
				String.format("Quantity in cart: %s for product :%s cannot exceed the quantity in stock: %s",
						productDTO.getQuantityInCart().toPlainString(), productDTO.getProductName(),
						productDTO.getStockQuantity()));
		shoppingCartService.updateShoppingCart(shoppingCart, productDTO, shoppingCartOperationType, BigDecimal.ZERO,
				BigDecimal.ZERO);

	}

	public ProductDTO prepareProductDTOForTest(int stockQuantity) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setStockQuantity(stockQuantity);
		productDTO.setQuantityInCart(BigDecimal.ZERO);
		productDTO.setPrice(BigDecimal.TEN);
		productDTO.setProductName("productBeingAddedFirstTimeToCart");
		return productDTO;
	}

}
