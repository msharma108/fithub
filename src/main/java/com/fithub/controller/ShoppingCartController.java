package com.fithub.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fithub.domain.ProductDTO;
import com.fithub.service.shoppingcart.ShoppingCartService;
import com.fithub.shoppingcart.ShoppingCart;

/**
 * Controller for handling shopping cart related operations
 * 
 *
 */
@Controller
@SessionAttributes("productDTO")
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

	private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartController.class);
	private final ShoppingCartService shoppingCartService;

	@Autowired
	public ShoppingCartController(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

	@RequestMapping(value = "/addToCart/{productName}")
	public String handleAddToCart(@ModelAttribute("productDTO") ProductDTO productDTO,
			@PathVariable("productName") String productName, HttpSession session,
			RedirectAttributes redirectAttributes) {

		LOG.debug("Attempting to add product={} to the cart", productName);
		String cartOperationTypeAddProduct = "addToCart";
		// Get shoppingCart from session
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		shoppingCart = shoppingCartService.updateShoppingCart(shoppingCart, productDTO, cartOperationTypeAddProduct,
				BigDecimal.ONE);
		session.setAttribute("shoppingCart", shoppingCart);

		// used to check if the product was successfully added to the cart
		redirectAttributes.addFlashAttribute("shoppingCartTaskTypeCompleted", 1);

		LOG.debug("Product added to the shopping cart with cart price={}", shoppingCart.getCartCost());
		return "redirect:/shoppingCart/viewCart";

	}

	// On clicking minus button in cart, have action
	// :/constructUrlForProductOperations/{productName} with button name
	// This is for refresh quantity in cart
	// Write URL Reconstruction for Refresh or think of logic in Cart itself
	@RequestMapping(value = "/refreshCart/{productName}")
	public String handleRefreshProductQuantityInCart(@ModelAttribute("productDTO") ProductDTO productDTO,
			@PathVariable("productName") String productName, HttpSession session, RedirectAttributes redirectAttributes,
			HttpServletRequest request, @ModelAttribute("quantityInCart") BigDecimal quantityInCart) {

		// ## If there is any issues remove the sessionAttribute and using
		// service retrieve the product from db here

		LOG.debug("Attempting to refreshCart product={} quantity in the cart", productName);
		String cartOperationTypeRefreshProductQuantity = "refreshQuantityInCart";
		// Get refreshed quantity from request object
		// Get it from flash attributes
		BigDecimal productQuantityInCartAfterRefresh = quantityInCart;

		// Get shoppingCart from session
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		shoppingCart = shoppingCartService.updateShoppingCart(shoppingCart, productDTO,
				cartOperationTypeRefreshProductQuantity, productQuantityInCartAfterRefresh);
		session.setAttribute("shoppingCart", shoppingCart);

		// used to check if the product quantity was successfully refreshed in
		// the cart
		redirectAttributes.addFlashAttribute("shoppingCartTaskTypeCompleted", 3);

		return "redirect:/shoppingCart/viewCart";

	}

	// On clicking minus button in cart, have action
	// :/constructUrlForProductOperations/{productName} with button name
	// This is for remove
	@RequestMapping(value = "/removeFromCart/{productName}")
	public String handleRemoveFromCart(@ModelAttribute("productDTO") ProductDTO productDTO,
			@PathVariable("productName") String productName, HttpSession session, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {

		LOG.debug("Attempting to reduce product={} quantity in the cart", productName);
		String cartOperationTypeRemoveProduct = "removeFromCart";
		// Get refreshed quantity from request object
		int productQuantityInCartAfterRefresh = 0;

		// Get shoppingCart from session
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		shoppingCart = shoppingCartService.updateShoppingCart(shoppingCart, productDTO, cartOperationTypeRemoveProduct,
				BigDecimal.ZERO);
		session.setAttribute("shoppingCart", shoppingCart);

		// used to check if the product was successfully removed from the cart
		redirectAttributes.addFlashAttribute("shoppingCartTaskTypeCompleted", 2);

		return "redirect:/shoppingCart/viewCart";

	}

	@RequestMapping(value = "/viewCart", method = { RequestMethod.GET, RequestMethod.POST })
	public String getShoppingCartPage() {
		LOG.debug("Getting ShoppingCart Page");
		return "product/shoppingCart";
	}

}
