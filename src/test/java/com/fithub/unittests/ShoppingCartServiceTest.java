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
	private final BigDecimal TWO = new BigDecimal(2);
	BigDecimal productPrice = BigDecimal.TEN;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void beforeTest() {
		this.shoppingCart = new ShoppingCart();
	}

	@Test
	public void updateShoppingCartAddsProductToCartAndUpdatesCartCostIfProductInStockAndNotInCartAndCartOperationTypeIsAddToCart() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 2;
		BigDecimal expectedCartCost = (productPrice.multiply(shoppingCart.getCartTaxRate())).add(productPrice);
		BigDecimal quantityInCart = BigDecimal.ZERO;
		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity, productPrice, quantityInCart);
		String shoppingCartOperationType = "addToCart";

		shoppingCartService.updateShoppingCart(shoppingCart, productDTO, shoppingCartOperationType, BigDecimal.ZERO,
				BigDecimal.ZERO);
		// Assert shopping cart has 1 item
		assertEquals("Shopping cart empty, product not added", 1, shoppingCart.getCartProductList().size());
		assertEquals("Shopping cart cost not updated", expectedCartCost, shoppingCart.getCartTotalCost());
	}

	@Test
	public void updateShoppingCartUpdatesProductQuantityInCartAndUpdatesCartCostIfProductInStockAndInCartAndCartOperationTypeIsAddToCart() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 2;

		BigDecimal expectedCartTotalCost = (productPrice.add(productPrice)).multiply(shoppingCart.getCartTaxRate())
				.add(productPrice.add(productPrice));

		BigDecimal quantityInCart = BigDecimal.ONE;
		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity, productPrice, quantityInCart);
		shoppingCart.getCartProductList().add(productDTO);
		shoppingCart.setCartCost(productPrice);
		String shoppingCartOperationType = "addToCart";

		shoppingCartService.updateShoppingCart(shoppingCart, productDTO, shoppingCartOperationType,
				productDTO.getPrice(), shoppingCart.getCartProductList().get(0).getQuantityInCart());

		// Assert shopping cart has 2 item
		assertEquals("Shopping cart not updated", new BigDecimal(2),
				shoppingCart.getCartProductList().get(0).getQuantityInCart());
		assertEquals("Shopping cart cost not updated", expectedCartTotalCost, shoppingCart.getCartTotalCost());
	}

	@Test
	public void updateShoppingCartIncrementsProductQuantityInCartAndUpdatesCartCostIfProductInStockAndInCartAndCartOperationTypeIsRefreshCartToIncrementCartQuantity() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 2;

		BigDecimal expectedCartTotalCost = (productPrice.add(productPrice)).multiply(shoppingCart.getCartTaxRate())
				.add(productPrice.add(productPrice));

		BigDecimal quantityInCart = BigDecimal.ONE;

		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity, productPrice, quantityInCart);
		shoppingCart.getCartProductList().add(productDTO);
		shoppingCart.setCartCost(productPrice);
		String shoppingCartOperationType = "refreshQuantityInCart";
		BigDecimal quantityInCartEnteredByUser = shoppingCart.getCartProductList().get(0).getQuantityInCart()
				.add(BigDecimal.ONE);

		shoppingCartService.updateShoppingCart(shoppingCart, productDTO, shoppingCartOperationType,
				productDTO.getPrice(), quantityInCartEnteredByUser);

		// Assert shopping cart item's quantity is refreshed to 2
		assertEquals("Shopping cart not updated", new BigDecimal(2),
				shoppingCart.getCartProductList().get(0).getQuantityInCart());
		assertEquals("Shopping cart cost not updated", expectedCartTotalCost, shoppingCart.getCartTotalCost());
	}

	@Test
	public void updateShoppingCartDecrementsProductQuantityInCartAndUpdatesCartCostIfProductInCartAndCartOperationTypeIsRefreshCartToDecrementCartQuantity() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 2;

		BigDecimal expectedCartTotalCost = (productPrice).multiply(shoppingCart.getCartTaxRate()).add(productPrice);

		BigDecimal quantityInCart = new BigDecimal(2);

		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity, productPrice, quantityInCart);
		shoppingCart.getCartProductList().add(productDTO);

		shoppingCart.setCartCost(productPrice.add(productPrice));
		String shoppingCartOperationType = "refreshQuantityInCart";
		BigDecimal quantityInCartEnteredByUser = shoppingCart.getCartProductList().get(0).getQuantityInCart()
				.subtract(BigDecimal.ONE);

		shoppingCartService.updateShoppingCart(shoppingCart, productDTO, shoppingCartOperationType,
				productDTO.getPrice(), quantityInCartEnteredByUser);

		// Assert shopping cart item's quantity is refreshed to 1
		assertEquals("Shopping cart not updated", BigDecimal.ONE,
				shoppingCart.getCartProductList().get(0).getQuantityInCart());
		assertEquals("Shopping cart cost not updated", expectedCartTotalCost, shoppingCart.getCartTotalCost());
	}

	@Test
	public void updateShoppingCartRemovesProductFromCartAndUpdatesCartCostIfProductInCartAndCartOperationTypeIsRefreshCartToDecrementCartQuantityToZero() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 1;

		boolean expectedCartEmpty = true;
		BigDecimal quantityInCart = BigDecimal.ONE;

		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity, productPrice, quantityInCart);
		shoppingCart.getCartProductList().add(productDTO);

		shoppingCart.setCartCost(productPrice);
		String shoppingCartOperationType = "refreshQuantityInCart";
		BigDecimal quantityInCartEnteredByUser = shoppingCart.getCartProductList().get(0).getQuantityInCart()
				.subtract(BigDecimal.ONE);

		shoppingCartService.updateShoppingCart(shoppingCart, productDTO, shoppingCartOperationType,
				productDTO.getPrice(), quantityInCartEnteredByUser);

		// Assert shopping cart item has been removed from cart
		assertEquals("Shopping cart not updated", expectedCartEmpty, shoppingCart.getCartProductList().isEmpty());
		assertEquals("Shopping cart cost not updated", BigDecimal.ZERO.setScale(2, 0), shoppingCart.getCartTotalCost());
	}

	@Test
	public void updateShoppingCartDoesNotAddOrUpdateProductToCartIfProductNotInStockAndCartOperationTypeIsAddOrRefresh() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 0;
		BigDecimal quantityInCart = BigDecimal.ZERO;
		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity, productPrice, quantityInCart);
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
	public void updateShoppingCartRemovesProductFromCartAndUpdatesCartCostIfProductInCartAndCartOperationTypeIsRemove() {
		ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
		int stockQuantity = 2;
		String shoppingCartOperationType = "removeFromCart";
		BigDecimal quantityInCart = BigDecimal.ONE;
		ProductDTO productDTO = prepareProductDTOForTest(stockQuantity, productPrice, quantityInCart);
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

	public ProductDTO prepareProductDTOForTest(int stockQuantity, BigDecimal productPrice, BigDecimal quantityInCart) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setStockQuantity(stockQuantity);
		productDTO.setQuantityInCart(quantityInCart);
		productDTO.setPrice(productPrice);
		productDTO.setProductName("productName");
		return productDTO;
	}

}
