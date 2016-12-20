package com.fithub.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fithub.domain.Product;
import com.fithub.domain.ProductDTO;
import com.fithub.service.product.ProductService;
import com.fithub.service.product.ProductTasksHelperService;

/**
 * Controller to handle product related tasks other than registration and
 * details update
 *
 */
@Controller
public class ProductTasksController {

	private final ProductService productService;
	private final ProductTasksHelperService productTasksHelperService;
	private static final Logger LOG = LoggerFactory.getLogger(ProductTasksController.class);

	public ProductTasksController(ProductService productService, ProductTasksHelperService productTasksHelperService) {
		this.productService = productService;
		this.productTasksHelperService = productTasksHelperService;
	}

	@RequestMapping(value = { "/viewProducts", "/viewProducts/{category}" })
	public String getAllProductsListPage(@PathVariable Map<String, String> pathVariables, Model model) {
		LOG.debug("Attempting to list all the products");

		List<ProductDTO> ListProductDTO = new ArrayList<ProductDTO>();
		List<Product> productList = new ArrayList<Product>();

		// Reference:
		// http://stackoverflow.com/questions/4904092/with-spring-3-0-can-i-make-an-optional-path-variable

		if (pathVariables.containsKey("category"))

			// Display products based on provided category
			productList = productService.getProductsByCategory(pathVariables.get("category"));
		else
			// Display all products
			productList = productService.getAllProducts();

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

	// Use the product image as a button image source and pass the url different
	// for admin and user
	// For admin form action will be :
	// /admin/constructUrlForAdminProductOperations/{productName}
	// For user action will be:
	// /constructUrlForProductOperations/{productName}--user will see only add
	// to cart button and product view
	@PostMapping(value = { "/admin/constructUrlForAdminProductOperations/{productName}",
			"/constructUrlForProductOperations/{productName}" })
	public String constructUrlForProductTasks(@RequestParam(value = "viewProduct", required = false) String viewProduct,
			@RequestParam(value = "addToCart", required = false) String addToCart,
			@RequestParam(value = "removeFromCart", required = false) String removeFromCart,
			@RequestParam(value = "editProduct", required = false) String editProduct,
			@RequestParam(value = "deleteProduct", required = false) String deleteProduct,
			@PathVariable("productName") String productName, HttpServletRequest request, Authentication authentication,
			Model model) {
		LOG.debug("Reconstructing URL for product operations");

		String productBase64ImageUrl = request.getParameter("productBase64ImageUrl");
		String reconstructedUrl = "";

		if (productName != null) {

			// Retrieve product by productName and add it to model
			Product product = productService.getProductByProductName(productName);
			if (product != null) {
				ProductDTO productDTO = productTasksHelperService.populateProductDTOfromProduct(product);
				productDTO.setBase64imageFile(productBase64ImageUrl);

				if (viewProduct != null)
					reconstructedUrl = "/viewProduct/" + productName;
				if (addToCart != null)
					reconstructedUrl = "/shoppingCart/addToCart/" + productName;
				if (removeFromCart != null)
					reconstructedUrl = "/shoppingCart/removeFromCart/" + productName;
				if (editProduct != null)
					reconstructedUrl = "/admin/editProduct/" + productName;
				if (deleteProduct != null)
					reconstructedUrl = "/admin/deleteProduct/" + productName;

				LOG.debug("Reconstructed URL={}", reconstructedUrl);
				model.addAttribute("productDTO", productDTO);

				return "forward:" + reconstructedUrl;
			} else
				throw new NoSuchElementException((String.format("Product with productName=%s not found", productName)));
		} else
			throw new NoSuchElementException("ProductName not supplied,please recheck");

	}

	@RequestMapping(value = "/viewProduct/{productName}")
	public String getProductDetailsPage(@PathVariable("productName") String productName, Model model,
			@ModelAttribute("productDTO") ProductDTO productDTO) {
		LOG.debug("Retreiving product details of product={}", productDTO.getProductName());

		model.addAttribute("productDTO", productDTO);
		return "product/productDetails";
	}

}
