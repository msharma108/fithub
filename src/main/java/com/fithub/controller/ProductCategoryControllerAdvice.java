package com.fithub.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOG = LoggerFactory.getLogger(ProductCategoryControllerAdvice.class);
	private final ProductCategoryService productCategoryService;

	@Autowired
	public ProductCategoryControllerAdvice(ProductCategoryService productCategoryService) {
		this.productCategoryService = productCategoryService;
	}

	@ModelAttribute("categoryList")
	public List<ProductCategory> getCategoryList() {
		LOG.debug("Getting the list of categories");
		return (productCategoryService.getAllProductCategories());
	}

}
