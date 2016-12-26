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

	@Autowired
	public ProductCategoryControllerAdvice(ProductCategoryService productCategoryService) {
		this.productCategoryService = productCategoryService;
	}

	@ModelAttribute("categoryList")
	public List<ProductCategory> getCategoryList() {

		final String categoryOfDeletedProducts = "Product_Deleted";
		List<ProductCategory> productCategoryList = productCategoryService.getAllProductCategories();

		productCategoryList.removeIf((ProductCategory categoryNotShownOnUI) -> categoryNotShownOnUI.getCategory()
				.equals(categoryOfDeletedProducts));

		return (productCategoryList);
	}

}
