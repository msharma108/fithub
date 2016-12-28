package com.fithub.validator.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fithub.domain.ProductDTO;
import com.fithub.service.product.ProductService;

@Component
public class ProductDTOValidator implements Validator {

	private static final Logger LOG = LoggerFactory.getLogger(ProductDTOValidator.class);
	private final ProductService productService;

	@Autowired
	public ProductDTOValidator(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * Method returns true if the passed object can be validated by the given
	 * validator
	 * 
	 * @param classToBeValidated
	 *            Class that this validator is being asked if it can validate
	 *
	 * @return boolean result
	 */
	@Override
	public boolean supports(Class<?> validator) {
		LOG.debug("Attempting to check if {}.class is supported", validator.getSimpleName());
		return validator.isAssignableFrom(ProductDTO.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOG.debug("Initiating validation of productDTO supplied by ProductRegisterValidator");
		ProductDTO productDTO = (ProductDTO) target;
		if (!productDTO.getIsEditable())
			// Run following validation only for new registration
			validateProductDoesNotExist(productDTO, errors);

	}

	/**
	 * Method checks and populates the errors if a product with the details
	 * entered in the registration form already exists in the database
	 * 
	 * @param productDTO
	 * @param errors
	 */
	private void validateProductDoesNotExist(ProductDTO productDTO, Errors errors) {
		LOG.debug("Validating the entered productName={} is not already in use", productDTO.getProductName());
		if ((productService.getProductByProductName(productDTO.getProductName()) != null)) {
			errors.rejectValue("productName", "productName.exists");
		}

	}

}
