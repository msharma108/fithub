package com.fithub.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fithub.domain.ProductDTO;
import com.fithub.service.product.ProductService;
import com.fithub.validator.product.ProductDTOValidator;

/**
 * Controller to handle product related tasks that involve modification to the
 * product data
 *
 */
@Controller
@SessionAttributes({ "productDTO", "cartUpdateParams" })
public class ProductModifyTasksController {

	private final ProductService productService;
	private final ProductDTOValidator productDTOValidator;
	private static final Logger LOG = LoggerFactory.getLogger(ProductModifyTasksController.class);

	/**
	 * Constructor for ProductModifyTasksController
	 * 
	 * @param productService
	 * @param productDTOValidator
	 */
	public ProductModifyTasksController(ProductService productService, ProductDTOValidator productDTOValidator) {
		this.productService = productService;
		this.productDTOValidator = productDTOValidator;
	}

	/**
	 * Method to handle deletion of product which involves marking the product
	 * as deleted in the database rather than actually deleting it. As the
	 * product details might be required for audit
	 * 
	 * @param productName
	 *            The product being marked for deletion
	 * @param productDTO
	 *            Data Transfer Object(DTO) for product that captures product
	 *            related data from the UI and also presents it on the UI.
	 * @param redirectAttributes
	 *            Spring MVC RedirectAttribute instance which stores flash
	 *            attribute for redirect requests. The flash attributes within
	 *            the request attribute will have life span of just one redirect
	 *            request
	 * @param authentication
	 *            Spring Security core Authentication instance that comprises of
	 *            the authenticated user's security details
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 * @return product task success view for display in case of success
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/admin/deleteProduct/{productName:.+}")
	public String handleProductDelete(@PathVariable("productName") String productName,
			@ModelAttribute("productDTO") ProductDTO productDTO, RedirectAttributes redirectAttributes,
			Authentication authentication, Model model, HttpServletRequest request) {
		LOG.debug("Attempting to delete product={}", productDTO.getProductName());

		boolean isProductDeleted = productService.deleteProduct(productDTO, authentication);

		LOG.debug("Product was deleted successfuly ?={}", isProductDeleted);

		if (!isProductDeleted) {
			model.addAttribute("exception", String.format("ProductName=%s not found", productDTO.getProductName()));
			model.addAttribute("errorUrl", request.getRequestURI());
			return "customErrorPage";
		}
		redirectAttributes.addFlashAttribute("productTaskTypeCompleted", 2);

		return "redirect:/admin/productTaskSuccess";
	}

	/**
	 * Method returns the product edit page for display
	 * 
	 * @param productName
	 *            Product name of the product being edited
	 * @param productDTO
	 *            Data Transfer Object(DTO) for product that captures product
	 *            related data from the UI and also presents it on the UI.
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @return product edit page for editing product details
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value = { "/admin/editProduct/{productName:.+}" }, params = "editProduct")
	public String getProductEditPage(@PathVariable("productName") String productName,
			@ModelAttribute("productDTO") ProductDTO productDTO, Model model) {
		LOG.debug("Getting editProductPage for product={}", productName);

		productDTO.setEditable(true);

		return "product/productRegister";
	}

	/**
	 * Method handles submission of product update details on the product edit
	 * page
	 * 
	 * @param productDTO
	 *            Data Transfer Object(DTO) for product that captures product
	 *            related data from the UI and also presents it on the UI.
	 * @param result
	 *            product update details validation object that validates the
	 *            updated data according to business logic
	 * @param redirectAttributes
	 *            Spring MVC RedirectAttribute instance which stores flash
	 *            attribute for redirect requests. The flash attributes within
	 *            the request attribute will have life span of just one redirect
	 *            request
	 * @param authentication
	 *            Spring Security core Authentication instance that comprises of
	 *            the authenticated user's security details
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 * @param thumbImage
	 *            thumbnail of the product
	 * @return product task success in case of successful updates or redirect to
	 *         product edit page in case of validation failure
	 */
	@PostMapping(value = { "/admin/productSave" }, params = "editProduct")
	public String submitProductEditPage(@Valid @ModelAttribute("productDTO") ProductDTO productDTO,
			BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication,
			HttpServletRequest request, @RequestParam("thumbImage") MultipartFile thumbImage) {
		LOG.debug("Attempting to update product={}", productDTO.getProductName());

		// Product DTO validation
		productDTOValidator.validate(productDTO, result);

		if (result.hasErrors()) {
			LOG.debug("Errors in the submitted form");
			return "product/productRegister";
		}

		try {
			// If a new image is being uploaded for the product
			if (!thumbImage.isEmpty()) {
				productDTO.setThumbImageAsByteArray(thumbImage.getBytes());
			}

		} catch (IOException e) {
			LOG.debug("Problems saving product images");
			e.printStackTrace();
		}

		productService.updateProductDetails(productDTO, authentication);
		LOG.debug("Product={} information update successful", productDTO.getProductName());

		// used to check update success on the canvas page
		redirectAttributes.addFlashAttribute("productTaskTypeCompleted", 3);
		return "redirect:/admin/productTaskSuccess";
	}

}
