package com.fithub.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fithub.domain.ProductDTO;
import com.fithub.service.product.ProductService;
import com.fithub.validator.product.ProductDTOValidator;

/**
 * Controller for handling product registrations
 *
 */
@Controller
public class ProductRegisterController {

	private static final Logger LOG = LoggerFactory.getLogger(ProductRegisterController.class);
	private final ProductService productService;
	private final ProductDTOValidator productDTOValidator;

	/**
	 * Constructor for ProductRegisterController
	 * 
	 * @param productService
	 * @param productDTOValidator
	 */
	@Autowired
	public ProductRegisterController(ProductService productService, ProductDTOValidator productDTOValidator) {
		this.productService = productService;
		this.productDTOValidator = productDTOValidator;
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

	/**
	 * Method handling submissions of product registration forms, in case of
	 * errors, it displays the errors to the admins on the view In case of no
	 * errors in the form validation , it saves the product to the database.
	 * Redirects to product registration success page
	 * 
	 * @param productDTO
	 * @param result
	 *            Holding results of validation against the supplied productDTO
	 * @return view based on results
	 */
	@PostMapping(value = "/admin/productSave", params = "productRegister")
	public String submitProductRegisterPage(@Valid @ModelAttribute("productDTO") ProductDTO productDTO,
			BindingResult result, RedirectAttributes redirectAttributes,
			@RequestParam("thumbImage") MultipartFile thumbImage) {
		LOG.debug("Attempting to register product", productDTO.getProductName());

		productDTOValidator.validate(productDTO, result);
		if (result.hasErrors()) {
			LOG.debug("Errors in the submitted form");
			// return = forward him to the registration form page
			return "product/productRegister";
		}

		try {
			productDTO.setThumbImageAsByteArray(thumbImage.getBytes());
		} catch (IOException e) {
			LOG.debug("Problems saving product images");
			e.printStackTrace();
		}

		productService.registerProduct(productDTO);
		LOG.debug("Registration successful, heading to the jsp");

		// used to check if the product was successfully registered into DB
		redirectAttributes.addFlashAttribute("productTaskTypeCompleted", 1);

		// Redirect based on logged in user's role
		return "redirect:/admin/productTaskSuccess";

	}

}
