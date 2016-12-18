package com.fithub.service.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fithub.domain.Product;
import com.fithub.domain.ProductCategory;
import com.fithub.domain.ProductDTO;
import com.fithub.service.productcategory.ProductCategoryService;

@Service

public class ProductTasksHelperServiceImpl implements ProductTasksHelperService {

	private final ProductCategoryService productCategoryService;
	private final static Logger LOG = LoggerFactory.getLogger(ProductTasksHelperServiceImpl.class);

	public ProductTasksHelperServiceImpl(ProductCategoryService productCategoryService) {
		this.productCategoryService = productCategoryService;
	}

	@Override
	public Product createProductFromProductDTO(Product product, ProductDTO productDTO) {

		product.setThumbImage(productDTO.getThumbImageAsByteArray());
		product.setProductName(productDTO.getProductName());
		product.setExpiryDate(productDTO.getExpiryDate());
		product.setFlavor(productDTO.getFlavor());
		product.setLdesc(productDTO.getLdesc());
		product.setManufactureDate(productDTO.getManufactureDate());
		product.setPrice(productDTO.getPrice());
		product.setRating(productDTO.getRating());
		product.setSdesc(productDTO.getSdesc());
		product.setStockQuantity(productDTO.getStockQuantity());
		product.setWeight(productDTO.getWeight());

		// Setting Product Category for the product based on the user input
		ProductCategory productCategory = new ProductCategory();
		ProductCategory existingProductCategory = productCategoryService
				.getProductCategoryByCategory(productDTO.getProductCategory());

		if (existingProductCategory != null)
			productCategory = existingProductCategory;

		productCategory.setCategory(productDTO.getProductCategory());
		product.setProductCategory(productCategory);

		return product;
	}

	@Override
	public ProductDTO populateProductDTOfromProduct(Product product) {
		LOG.debug("Attempting to populate productDTO from product with productName={}", product.getProductName());
		ProductDTO productDTO = new ProductDTO();

		productDTO.setExpiryDate(product.getExpiryDate());
		productDTO.setFlavor(product.getFlavor());
		productDTO.setLdesc(product.getLdesc());
		productDTO.setManufactureDate(product.getManufactureDate());
		productDTO.setPrice(product.getPrice());
		productDTO.setProductName(product.getProductName());
		productDTO.setQuantitySold(product.getQuantitySold());
		productDTO.setRating(product.getRating());
		productDTO.setRegistrationDate(product.getRegistrationDate());
		productDTO.setWeight(product.getWeight());
		productDTO.setProductCategory(product.getProductCategory().getCategory());
		productDTO.setSdesc(product.getSdesc());
		productDTO.setStockQuantity(product.getStockQuantity());

		return productDTO;
	}

}
