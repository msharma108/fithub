package com.fithub.service.product;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fithub.domain.Product;
import com.fithub.domain.ProductCategory;
import com.fithub.domain.ProductDTO;
import com.fithub.service.productcategory.ProductCategoryService;
import com.fithub.service.time.TimeHelperService;

@Service

public class ProductTasksHelperServiceImpl implements ProductTasksHelperService {

	private final ProductCategoryService productCategoryService;
	private final TimeHelperService timeHelperService;
	private final static Logger LOG = LoggerFactory.getLogger(ProductTasksHelperServiceImpl.class);

	public ProductTasksHelperServiceImpl(ProductCategoryService productCategoryService,
			TimeHelperService timeHelperService) {
		this.productCategoryService = productCategoryService;
		this.timeHelperService = timeHelperService;
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
		productDTO.setProductNameBeforeEdit(product.getProductName());
		productDTO.setQuantitySold(product.getQuantitySold());
		productDTO.setRating(product.getRating());
		productDTO.setRegistrationDate(product.getRegistrationDate());
		productDTO.setWeight(product.getWeight());
		productDTO.setProductCategory(product.getProductCategory().getCategory());
		productDTO.setSdesc(product.getSdesc());
		productDTO.setStockQuantity(product.getStockQuantity());
		productDTO.setThumbImageAsByteArray(product.getThumbImage());

		return productDTO;
	}

	@Override
	public ProductDTO destroyProductDataForDeletion(ProductDTO productDTO) {

		final String productDeleted = "Product_Deleted";
		final String productDeletedDummyDate = "1900/10/11";
		final String productDeletedRating = "Not Applicable";
		BigDecimal productDeletedDummyNumber = new BigDecimal(-999);

		productDTO.setBase64imageFile(productDeleted);

		productDTO.setExpiryDate(timeHelperService.dateFormatter(productDeletedDummyDate));
		productDTO.setFlavor(productDeleted);
		productDTO.setLdesc(productDeleted);
		productDTO.setManufactureDate((timeHelperService.dateFormatter(productDeletedDummyDate)));
		productDTO.setPrice(productDeletedDummyNumber);
		productDTO.setProductCategory(productDeleted);
		productDTO.setProductDeleted(true);
		productDTO.setProductName(productDTO.getProductName().concat(productDeleted));
		productDTO.setQuantityInCart(BigDecimal.ZERO);
		productDTO.setQuantitySold(-999);
		productDTO.setRating(productDeletedRating);
		productDTO.setRegistrationDate(timeHelperService.dateFormatter(productDeletedDummyDate));
		productDTO.setSdesc(productDeleted);
		productDTO.setStockQuantity(-999);
		productDTO.setThumbImageAsByteArray(null);
		productDTO.setWeight(productDeletedDummyNumber);

		return productDTO;
	}

}
