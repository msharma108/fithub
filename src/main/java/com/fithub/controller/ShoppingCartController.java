package com.fithub.controller;

import java.math.BigDecimal;
import java.util.HashMap;

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
@SessionAttributes({ "productDTO", "cartUpdateParams" })
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

	private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartController.class);
	private final ShoppingCartService shoppingCartService;

	/**
	 * Constructor for ShoppingCartController
	 * 
	 * @param shoppingCartService
	 */
	@Autowired
	public ShoppingCartController(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

	/**
	 * Method handles operation of type Add to cart
	 * 
	 * @param productDTO
	 *            Data Transfer Object(DTO) for product that captures product
	 *            related data from the UI and also presents it on the UI
	 * @param productName
	 *            Product name of the product being added to the cart
	 * @param session
	 *            Http session object
	 * @param redirectAttributes
	 *            Spring MVC RedirectAttribute instance which stores flash
	 *            attribute for redirect requests. The flash attributes within
	 *            the request attribute will have life span of just one redirect
	 *            request
	 * @return the shopping cart view displaying the updated cart
	 */
	@RequestMapping(value = "/addToCart/{productName:.+}")
	public String handleAddToCart(@ModelAttribute("productDTO") ProductDTO productDTO,
			@PathVariable("productName") String productName, HttpSession session,
			RedirectAttributes redirectAttributes) {

		LOG.debug("Attempting to add product={} to the cart", productName);
		String cartOperationTypeAddProduct = "addToCart";
		BigDecimal productQuantityIncrease = BigDecimal.ONE;
		// Get shoppingCart from session
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		shoppingCart = shoppingCartService.updateShoppingCart(shoppingCart, productDTO, cartOperationTypeAddProduct,
				BigDecimal.ZERO, productQuantityIncrease);
		session.setAttribute("shoppingCart", shoppingCart);

		// used to check if the product was successfully added to the cart
		redirectAttributes.addFlashAttribute("shoppingCartTaskTypeCompleted", 1);

		LOG.debug("Product added to the shopping cart with cart price={}", shoppingCart.getCartCost());
		return "redirect:/shoppingCart/viewCart";

	}

	/**
	 * Method handles operation of type refresh cart which is triggered by the
	 * refresh button in the cart on the UI
	 * 
	 * @param productDTO
	 *            Data Transfer Object(DTO) for product that captures product
	 *            related data from the UI and also presents it on the UI
	 * @param productName
	 *            Product name of the product being added to the cart
	 * @param session
	 *            Http session object
	 * @param redirectAttributes
	 *            Spring MVC RedirectAttribute instance which stores flash
	 *            attribute for redirect requests. The flash attributes within
	 *            the request attribute will have life span of just one redirect
	 *            request
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 * @param cartUpdateParams
	 *            Hashmap containing the updated product quantity and subtotal
	 *            in cart
	 * @return the shopping cart view displaying the updated cart
	 */
	@RequestMapping(value = "/refreshCart/{productName:.+}")
	public String handleRefreshProductQuantityInCart(@ModelAttribute("productDTO") ProductDTO productDTO,
			@PathVariable("productName") String productName, HttpSession session, RedirectAttributes redirectAttributes,
			HttpServletRequest request,
			@ModelAttribute("cartUpdateParams") HashMap<String, BigDecimal> cartUpdateParams) {

		LOG.debug("Attempting to refreshCart product={} quantity in the cart", productName);
		String cartOperationTypeRefreshProductQuantity = "refreshQuantityInCart";

		// Get shoppingCart from session
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		shoppingCart = shoppingCartService.updateShoppingCart(shoppingCart, productDTO,
				cartOperationTypeRefreshProductQuantity, cartUpdateParams.get("productSubTotalInCart"),
				cartUpdateParams.get("productQuantityInCart"));
		session.setAttribute("shoppingCart", shoppingCart);

		// used to check if the product quantity was successfully refreshed in
		// the cart
		redirectAttributes.addFlashAttribute("shoppingCartTaskTypeCompleted", 3);

		return "redirect:/shoppingCart/viewCart";

	}

	/**
	 * Method handles operation of type remove from cart which is triggered by
	 * the remove button in the cart on the UI
	 * 
	 * @param productDTO
	 *            productDTO Data Transfer Object(DTO) for product that captures
	 *            product related data from the UI and also presents it on the
	 *            UI
	 * @param productName
	 *            Product name of the product being added to the cart
	 * @param session
	 *            Http session object
	 * @param redirectAttributes
	 *            Spring MVC RedirectAttribute instance which stores flash
	 *            attribute for redirect requests. The flash attributes within
	 *            the request attribute will have life span of just one redirect
	 *            request
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 * @param cartUpdateParams
	 *            cartUpdateParams Hashmap containing the product quantity and
	 *            subtotal in cart
	 * @return the shopping cart view displaying the updated cart
	 */
	@RequestMapping(value = "/removeFromCart/{productName:.+}")
	public String handleRemoveFromCart(@ModelAttribute("productDTO") ProductDTO productDTO,
			@PathVariable("productName") String productName, HttpSession session, RedirectAttributes redirectAttributes,
			HttpServletRequest request,
			@ModelAttribute("cartUpdateParams") HashMap<String, BigDecimal> cartUpdateParams) {

		LOG.debug("Attempting to reduce product={} quantity in the cart", productName);
		String cartOperationTypeRemoveProduct = "removeFromCart";

		// Get shoppingCart from session
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		shoppingCart = shoppingCartService.updateShoppingCart(shoppingCart, productDTO, cartOperationTypeRemoveProduct,
				cartUpdateParams.get("productSubTotalInCart"), cartUpdateParams.get("productQuantityInCart"));
		session.setAttribute("shoppingCart", shoppingCart);

		// used to check if the product was successfully removed from the cart
		redirectAttributes.addFlashAttribute("shoppingCartTaskTypeCompleted", 2);

		return "redirect:/shoppingCart/viewCart";

	}

	/**
	 * Method returns the view displaying the current state of the shopping cart
	 * 
	 * @return view displaying the current state of the shopping cart
	 */
	@RequestMapping(value = "/viewCart", method = { RequestMethod.GET, RequestMethod.POST })
	public String getShoppingCartPage() {
		LOG.debug("Getting ShoppingCart Page");
		return "product/shoppingCart";
	}

}
