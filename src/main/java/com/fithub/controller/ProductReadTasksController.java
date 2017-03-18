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

/**
 * Controller to handle product related tasks that involve read operations on
 * the product data
 *
 */
@Controller
@SessionAttributes({ "productDTO", "cartUpdateParams" })
public class ProductReadTasksController {

	private final ProductService productService;
	private final ProductTasksHelperService productTasksHelperService;
	private static final Logger LOG = LoggerFactory.getLogger(ProductReadTasksController.class);

	/**
	 * Constructor for ProductReadTasksController
	 * 
	 * @param productService
	 * @param productTasksHelperService
	 */
	public ProductReadTasksController(ProductService productService,
			ProductTasksHelperService productTasksHelperService) {
		this.productService = productService;
		this.productTasksHelperService = productTasksHelperService;
	}

	/**
	 * Method returns the product List page
	 * 
	 * @param pathVariables
	 *            Spring path variables the are visible in the URL
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @return the product List page
	 */
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

	/**
	 * Method prepares the Model object for displaying information of products
	 * on the product list page
	 * 
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @param productList
	 *            List of products
	 * @return Spring model object containing information about the list of
	 *         products
	 */
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

	/**
	 * Method acts as a routing method that fetches product information and
	 * passes that information to respective methods based on the operation
	 * being requested. The operation is identified based on the trigger point
	 * for the operation eg: the type of button clicked. The method masks the
	 * URL therefore disallowing accessing and performing operations by copying
	 * operation URL
	 * 
	 * @param viewProduct
	 *            Spring request param for the viewProduct operation triggered
	 *            by View product button
	 * @param addToCart
	 *            Spring request param for the addToCart operation triggered by
	 *            addToCart button
	 * @param removeFromCart
	 *            Spring request param for the removeFromCart operation
	 *            triggered by removeFromCart button
	 * @param refreshCart
	 *            Spring request param for the refreshCart operation triggered
	 *            by refreshCart button
	 * @param editProduct
	 *            Spring request param for the editProduct operation triggered
	 *            by editProduct button
	 * @param deleteProduct
	 *            Spring request param for the deleteProduct operation triggered
	 *            by deleteProduct button
	 * @param productName
	 *            Spring request param for the productName operation triggered
	 *            by productName button
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 * @param authentication
	 *            Spring Security core Authentication instance that comprises of
	 *            the authenticated user's security details
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @return mappings for the different product tasks
	 */
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

	/**
	 * Method returns the product details page for display
	 * 
	 * @param productName
	 *            Product name of the product whose details are to be viewed
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @param productDTO
	 *            Data Transfer Object(DTO) for product that captures product
	 *            related data from the UI and also presents it on the UI.
	 * @return the product details page for display
	 */
	@RequestMapping(value = "/viewProduct/{productName:.+}")
	public String getProductDetailsPage(@PathVariable("productName") String productName, Model model,
			@ModelAttribute("productDTO") ProductDTO productDTO) {
		LOG.debug("Retreiving product details of product={}", productDTO.getPrice());

		model.addAttribute("productDTO", productDTO);
		return "product/productDetails";
	}

	/**
	 * Method handles product search
	 * 
	 * @param model
	 *            Spring Model object that can encompass request data
	 * @param productSearchString
	 *            search string for product search operation
	 * @return product list page with products matching the search string
	 */
	@RequestMapping(value = "/searchProduct")
	public String handleSearchProduct(Model model, @RequestParam("productSearchString") String productSearchString) {
		LOG.debug("Attempting to search product");
		List<Product> productList = new ArrayList<Product>();
		productList = productService.getProductsContainingProductNameOrShortDescription(productSearchString);
		model = prepareProductsForDisplay(model, productList);

		return "product/productList";
	}

	/**
	 * Method returns product task success page corresponding to a product
	 * operation
	 * 
	 * @param request
	 *            HttpServlet request object encapsulating hidden form
	 * @return product task success page corresponding to a product operation
	 */
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
