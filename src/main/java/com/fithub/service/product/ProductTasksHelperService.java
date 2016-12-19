package com.fithub.service.product;

import com.fithub.domain.Product;
import com.fithub.domain.ProductDTO;

public interface ProductTasksHelperService {

	/**
	 * Method prepares a product object based on the properties retrieved from a
	 * product transfer object
	 * 
	 * @param product
	 *            Reference to a newly created product object
	 * @param productDTO
	 *            Product Data Transfer Object populated with properties
	 *            retrieved from the View
	 * @return product Product object to be stored in the database
	 */
	Product createProductFromProductDTO(Product product, ProductDTO productDTO);

	/**
	 * Method populates a productDTO object extracting out details from the
	 * passed in product parameter
	 * 
	 * @param product
	 * @return productDTO populated with information from the passed product
	 */
	ProductDTO populateProductDTOfromProduct(Product product);

}
