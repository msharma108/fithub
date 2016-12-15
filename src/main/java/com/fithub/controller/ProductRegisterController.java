package com.fithub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fithub.domain.ProductDTO;
import com.fithub.service.product.ProductService;

/**
 * Controller for handling product registrations
 *
 */
@Controller
public class ProductRegisterController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductRegisterController.class);
	private final ProductService productService;

	@Autowired
	public ProductRegisterController(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * Method for displaying the Product Registration form
	 * 
	 * @param model
	 * @return registration view
	 */

	@GetMapping(value = "/admin/productRegister")
	public String getProductRegisterPage(Model model) {
		LOG.debug("Displaying Product Registration page");
		ProductDTO productDTO = new ProductDTO();
		model.addAttribute("productDTO", productDTO);

		return "product/productRegister";
	}

}
