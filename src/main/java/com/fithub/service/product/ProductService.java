package com.fithub.service.product;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.fithub.domain.Product;
import com.fithub.domain.ProductDTO;

/**
 * An interface for the services pertinent to product domain objects
 *
 */
public interface ProductService {

	/**
	 * Method gets a product based on the automatically generated productId
	 * Primary Key
	 * 
	 * @param productId
	 * @return Product
	 */
	Product getProductById(Integer productId);

	/**
	 * Method gets a product based on the provided product name
	 * 
	 * @param productName
	 *            Name of the product
	 * @return Product
	 */
	Product getProductByProductName(String productName);

	/**
	 * Method registers a product based on the information filled on product
	 * form
	 * 
	 * @param userDTO
	 * @return User
	 */
	Product registerProduct(ProductDTO productDTO);

	/**
	 * Method marks a product record as deleted in the database based on the
	 * provided productName and return true as boolean result on success
	 * 
	 * @param productDTO
	 * @param authentication
	 *            Spring Security Authentication object to acquire admin details
	 * @return boolean
	 */
	boolean deleteProduct(ProductDTO productDTO, Authentication authentication);

	/**
	 * Method gets a list of all the products in the database
	 * 
	 * @return List of all the products ordered by the productName
	 */
	List<Product> getAllProducts();

	/**
	 * Method gets a list of all the products in the database belonging to the
	 * category provided
	 * 
	 * @param category
	 *            Category of the product
	 * @return List of all the products based on provided category
	 */
	List<Product> getProductsByCategory(String category);

	/**
	 * Method updates a product based on the information filled on product
	 * update form
	 * 
	 * @param productDTO
	 * @param authentication
	 *            Spring Security Authentication object to acquire admin details
	 * @return updated product
	 */

	Product updateProductDetails(ProductDTO productDTO, Authentication authentication);

	/**
	 * Method finds top 5 products sorted in a descending order indicating which
	 * products have the highest quantity sold
	 * 
	 * @return List of products based sorted in a descending order based on the
	 *         quantity sold
	 */
	List<Product> getTopProductsByQuantitySold();

	/**
	 * Method gets a list of products whose productName or short description
	 * containing the provided productName and short description
	 * 
	 * @param searchProductName
	 *            The searched short productName
	 * @param searchShortDescription
	 *            The searched short description
	 * @return The list of products containing the supplied search string
	 */
	List<Product> getProductsContaingNameOrShortDescription(String searchProductName, String searchShortDescription);
}
