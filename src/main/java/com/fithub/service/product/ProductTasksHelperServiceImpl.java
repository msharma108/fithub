package com.fithub.service.product;

import org.springframework.stereotype.Service;

import com.fithub.domain.Product;
import com.fithub.domain.ProductCategory;
import com.fithub.domain.ProductDTO;

@Service

public class ProductTasksHelperServiceImpl implements ProductTasksHelperService {

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
		productCategory.setCategory(productDTO.getProductCategory());
		product.setProductCategory(productCategory);

		return product;
	}

}
