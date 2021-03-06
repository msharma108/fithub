package com.fithub.service.productcategory;

import java.util.List;

import com.fithub.domain.ProductCategory;

public interface ProductCategoryService {

	/**
	 * Method gets a list of all the product categories in the database
	 * 
	 * @return List of all the product categories
	 */
	List<ProductCategory> getAllProductCategories();

	/**
	 * Method gets ProductCategory record based on the product category passed
	 * 
	 * @param category
	 * @return ProductCategory record based on the product category passed
	 */
	ProductCategory getProductCategoryByCategory(String category);

	/**
	 * Method counts the number of product categories in the system\database
	 * 
	 * @return the number of product categories in the system
	 */
	long countNumberOfProductCategoriesInDatabase();

}