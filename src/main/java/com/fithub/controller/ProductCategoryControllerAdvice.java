package com.fithub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fithub.domain.ProductCategory;
import com.fithub.service.productcategory.ProductCategoryService;

/**
 * Controller makes all available product categories available across the
 * application views
 *
 */
@ControllerAdvice
public class ProductCategoryControllerAdvice {

	private final ProductCategoryService productCategoryService;

	/**
	 * Constructor for ProductCategoryControllerAdvice
	 * 
	 * @param productCategoryService
	 */
	@Autowired
	public ProductCategoryControllerAdvice(ProductCategoryService productCategoryService) {
		this.productCategoryService = productCategoryService;
	}

	/**
	 * Method populates the model object with a category list that exists in the
	 * product_category table in the database
	 * 
	 * @return Spring model object containing information about the list of
	 *         product categories existent in the application's product_category
	 *         table
	 */
	@ModelAttribute("categoryList")
	public List<ProductCategory> getCategoryList() {

		final String categoryOfDeletedProducts = "Product_Deleted";
		List<ProductCategory> productCategoryList = productCategoryService.getAllProductCategories();

		productCategoryList.removeIf((ProductCategory categoryNotShownOnUI) -> categoryNotShownOnUI.getCategory()
				.equals(categoryOfDeletedProducts));

		return (productCategoryList);
	}

}
