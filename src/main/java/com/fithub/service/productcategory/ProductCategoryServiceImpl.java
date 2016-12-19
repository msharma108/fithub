package com.fithub.service.productcategory;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fithub.domain.ProductCategory;
import com.fithub.repository.productcategory.ProductCategoryRepository;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	private static final Logger LOG = LoggerFactory.getLogger(ProductCategoryService.class);
	private final ProductCategoryRepository productCategoryRepository;

	public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}

	@Override
	public List<ProductCategory> getAllProductCategories() {
		LOG.debug("Retrieving the list of all the product categories");
		return productCategoryRepository.findAll(new Sort("category"));
	}

	@Override
	public ProductCategory getProductCategoryByCategory(String category) {
		if (category != null)
			return productCategoryRepository.findOneByCategory(category);
		else
			throw new NoSuchElementException(String.format("product category=%d not found", category));
	}

}
