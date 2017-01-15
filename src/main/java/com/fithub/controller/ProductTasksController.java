package com.fithub.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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

import com.fithub.domain.Product;
import com.fithub.domain.ProductDTO;
import com.fithub.service.product.ProductService;
import com.fithub.service.product.ProductTasksHelperService;
import com.fithub.validator.product.ProductDTOValidator;

/**
 * Controller to handle product related tasks other than registration and
 * details update
 *
 */
@Controller
@SessionAttributes({ "productDTO", "cartUpdateParams" })
public class ProductTasksController {

	private final ProductService productService;
	private final ProductTasksHelperService productTasksHelperService;
	private final ProductDTOValidator productDTOValidator;
	private static final Logger LOG = LoggerFactory.getLogger(ProductTasksController.class);

	public ProductTasksController(ProductService productService, ProductTasksHelperService productTasksHelperService,
			ProductDTOValidator productDTOValidator) {
		this.productService = productService;
		this.productTasksHelperService = productTasksHelperService;
		this.productDTOValidator = productDTOValidator;
	}

	@RequestMapping(value = { "/viewProducts", "/viewProducts/{category}", "/viewProducts/topProducts/{topProducts}" })
	public String getAllProductsListPage(@PathVariable Map<String, String> pathVariables, Model model) {
		LOG.debug("Attempting to list all the products");

		List<Product> productList = new ArrayList<Product>();

		// Reference:
		// http://stackoverflow.com/questions/4904092/with-spring-3-0-can-i-make-an-optional-path-variable

		if (pathVariables.containsKey("category"))

			// Display products based on provided category
			productList = productService.getProductsByCategory(pathVariables.get("category"));

		else if (pathVariables.containsKey("topProducts")) {
			// Display top products based on quantity sold
			productList = productService.getTop5ProductsByQuantitySold();
			model.addAttribute("displayTopProductsHeading", true);
		}

		else
			// Display all products
			productList = productService.getAllProducts();

		model = prepareProductsForDisplay(model, productList);

		return "product/productList";
	}

	private Model prepareProductsForDisplay(Model model, List<Product> productList) {
		List<ProductDTO> listProductDTOForImageDisplay = new ArrayList<ProductDTO>();
		// quantity of a product that has been marked as deleted in db
		int deletedProductQuantity = -999;

		// Remove products marked as deleted from the list
		productList.removeIf(
				(Product productMarkedDeleted) -> productMarkedDeleted.getStockQuantity() == deletedProductQuantity);

		// Encoding byte array image received from DB
		for (Product productFromDB : productList) {

			// Display only the products that have not been marked as deleted
			// Product marked for deletion have a negative stock quantity

			ProductDTO productDTO = new ProductDTO();
			LOG.debug("Encoding image retreieved from database");
			productDTO.setBase64imageFile(
					"data:image/jpg;base64," + Base64.getEncoder().encodeToString(productFromDB.getThumbImage()));
			listProductDTOForImageDisplay.add(productDTO);

		}

		model.addAttribute("allProducts", productList);
		// product DTO holding encoded image URL
		model.addAttribute("ListProductDTO", listProductDTOForImageDisplay);

		return model;
	}

	@PostMapping(value = { "/admin/constructUrlForAdminProductOperations/{productName:.+}",
			"/constructUrlForProductOperations/{productName:.+}" })
	public String constructUrlForProductTasks(@RequestParam(value = "viewProduct", required = false) String viewProduct,
			@RequestParam(value = "addToCart", required = false) String addToCart,
			@RequestParam(value = "removeFromCart", required = false) String removeFromCart,
			@RequestParam(value = "refreshCart", required = false) String refreshCart,
			@RequestParam(value = "editProduct", required = false) String editProduct,
			@RequestParam(value = "deleteProduct", required = false) String deleteProduct,
			@PathVariable("productName") String productName, HttpServletRequest request, Authentication authentication,
			Model model) {
		LOG.debug("Reconstructing URL for product operations");

		String base64imageFile = request.getParameter("base64imageFile");
		String reconstructedUrl = "";
		Map<String, BigDecimal> cartUpdateParams = new HashMap<String, BigDecimal>();

		if (productName != null) {

			// Retrieve product by productName and add it to model
			Product product = productService.getProductByProductName(productName);
			if (product != null) {
				ProductDTO productDTO = productTasksHelperService.populateProductDTOfromProduct(product);
				productDTO.setBase64imageFile(base64imageFile);

				if (viewProduct != null)
					reconstructedUrl = "/viewProduct/" + productName;
				if (addToCart != null)
					reconstructedUrl = "/shoppingCart/addToCart/" + productName;

				// request routing for Cart refresh or removal operations
				if (removeFromCart != null || refreshCart != null) {
					BigDecimal productSubTotalInCart = new BigDecimal(request.getParameter("subTotal"));
					BigDecimal productQuantityInCartAfterRefresh = new BigDecimal(
							request.getParameter("quantityInCart"));

					cartUpdateParams.put("productSubTotalInCart", productSubTotalInCart);
					cartUpdateParams.put("productQuantityInCart", productQuantityInCartAfterRefresh);
					model.addAttribute("cartUpdateParams", cartUpdateParams);

					if (removeFromCart != null)
						reconstructedUrl = "/shoppingCart/removeFromCart/" + productName;
					if (refreshCart != null)
						reconstructedUrl = "/shoppingCart/refreshCart/" + productName;
				}
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

	@RequestMapping(value = "/viewProduct/{productName:.+}")
	public String getProductDetailsPage(@PathVariable("productName") String productName, Model model,
			@ModelAttribute("productDTO") ProductDTO productDTO) {
		LOG.debug("Retreiving product details of product={}", productDTO.getPrice());

		model.addAttribute("productDTO", productDTO);
		return "product/productDetails";
	}

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

		return "redirect:/product/productTaskSuccess";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value = { "/admin/editProduct/{productName:.+}" }, params = "editProduct")
	public String getProductEditPage(@PathVariable("productName") String productName,
			@ModelAttribute("productDTO") ProductDTO productDTO, Model model) {
		LOG.debug("Getting editProductPage for product={}", productName);

		productDTO.setEditable(true);

		return "product/productRegister";
	}

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
		return "redirect:/product/productTaskSuccess";
	}

	@RequestMapping(value = "/searchProduct")
	public String searchProduct(Model model, @RequestParam("productSearchString") String productSearchString) {
		LOG.debug("Attempting to search product");
		List<Product> productList = new ArrayList<Product>();
		productList = productService.getProductsContaingProductNameOrShortDescription(productSearchString);
		model = prepareProductsForDisplay(model, productList);

		return "product/productList";
	}

}
