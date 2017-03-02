package com.fithub.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fithub.domain.Product;
import com.fithub.domain.ProductDTO;
import com.fithub.service.product.ProductService;
import com.fithub.service.product.ProductTasksHelperService;

@Controller
@SessionAttributes({ "productDTO", "cartUpdateParams" })
public class ProductReadTasksController {

	private final ProductService productService;
	private final ProductTasksHelperService productTasksHelperService;
	private static final Logger LOG = LoggerFactory.getLogger(ProductReadTasksController.class);

	public ProductReadTasksController(ProductService productService,
			ProductTasksHelperService productTasksHelperService) {
		this.productService = productService;
		this.productTasksHelperService = productTasksHelperService;
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

	@PostMapping(value = { "/admin/adminProductOperation/{productName:.+}", "/productOperation/{productName:.+}" })
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

	@RequestMapping(value = "/searchProduct")
	public String searchProduct(Model model, @RequestParam("productSearchString") String productSearchString) {
		LOG.debug("Attempting to search product");
		List<Product> productList = new ArrayList<Product>();
		productList = productService.getProductsContainingProductNameOrShortDescription(productSearchString);
		model = prepareProductsForDisplay(model, productList);

		return "product/productList";
	}

	@RequestMapping(value = { "/productTaskSuccess", "/admin/productTaskSuccess" })
	public String getProductTaskSuccessPage(HttpServletRequest request) {

		// Preventing problem with page refresh in case of flash attribute
		// Reference:
		// http://www.tikalk.com/redirectattributes-new-feature-spring-mvc-31/
		LOG.debug("Getting Success Page");
		Map<String, ?> checkMap = RequestContextUtils.getInputFlashMap(request);
		if (checkMap != null)

			// Success Page could be on registration itself
			// Handles RegisterSuccess and UpdateSuccess
			return "product/productTaskSuccess";
		else
			return "home";
	}

}
