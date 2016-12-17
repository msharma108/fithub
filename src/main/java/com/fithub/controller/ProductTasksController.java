package com.fithub.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fithub.domain.Product;
import com.fithub.domain.ProductDTO;
import com.fithub.service.product.ProductService;

/**
 * Controller to handle product related tasks other than registration and
 * details update
 *
 */
@Controller
public class ProductTasksController {

	private final ProductService productService;
	private static final Logger LOG = LoggerFactory.getLogger(ProductTasksController.class);

	public ProductTasksController(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping(value = { "/viewProducts" })
	public String getAllProductsListPage(
			@RequestParam(value = "productCategory", required = false) String productCategory, Model model) {
		LOG.debug("Attempting to list all the products");

		List<ProductDTO> ListProductDTO = new ArrayList<ProductDTO>();
		List<Product> productList = new ArrayList<Product>();
		if (productCategory == null)
			// Display all products
			productList = productService.getAllProducts();
		else
			// Display products based on provided category
			productList = productService.getProductsByCategory(productCategory);

		// Encoding byte array image received from DB and encoding it for
		// browser display
		for (Product productFromDB : productList) {

			ProductDTO productDTO = new ProductDTO();
			LOG.debug("Encoding image retreieved from database");
			productDTO.setBase64imageFile(
					"data:image/jpg;base64," + Base64.getEncoder().encodeToString(productFromDB.getThumbImage()));
			productDTO.setThumbImageAsByteArray(productFromDB.getThumbImage());
			ListProductDTO.add(productDTO);
		}
		model.addAttribute("allProducts", productList);
		// product DTO holding encoded image URL
		model.addAttribute("ListProductDTO", ListProductDTO);

		return "product/productList";
	}

}
