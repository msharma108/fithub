package com.fithub.service.product;

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
	Product getProductById(Long productId);

	/**
	 * Method gets a product based on the provided product name
	 * 
	 * @param name
	 *            Name of the product
	 * @return Product
	 */
	Product getProductByProductName(String name);

	/**
	 * Method creates a product based on the information filled on registration
	 * form
	 * 
	 * @param userDTO
	 * @return User
	 */
	Product createProduct(ProductDTO productDTO);

}
