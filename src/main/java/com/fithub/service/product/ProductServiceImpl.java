package com.fithub.service.product;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fithub.domain.Product;
import com.fithub.domain.ProductDTO;
import com.fithub.repository.product.ProductRepository;
import com.fithub.service.time.TimeHelperService;
import com.fithub.service.user.UserTasksHelperService;

/**
 * An implementation of the ProductService
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
	private final ProductRepository productRepository;
	private final ProductTasksHelperService productTasksHelperService;
	private final TimeHelperService timeHelperService;
	private final UserTasksHelperService userTasksHelperService;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, ProductTasksHelperService productTasksHelperService,
			TimeHelperService timeHelperService, UserTasksHelperService userTasksHelperService) {
		this.productRepository = productRepository;
		this.productTasksHelperService = productTasksHelperService;
		this.timeHelperService = timeHelperService;
		this.userTasksHelperService = userTasksHelperService;

	}

	@Override
	public Product getProductById(Integer productId) throws IllegalArgumentException {
		LOG.debug("Retreive product having productId={}", productId);
		Product product = productRepository.findOne(productId);

		// Handling null in service layer
		// if a future controller uses this then we don't need to care about
		// null checks in controller
		if (product != null)
			return product;
		else
			throw new NoSuchElementException(String.format("productId=%d not found", productId));
	}

	@Override
	public Product getProductByProductName(String productName) {
		LOG.debug("Retreive product having productName={}", productName);
		Product product = productRepository.findOneByProductName(productName);
		return product;

	}

	@Override
	public Product registerProduct(ProductDTO productDTO) {
		LOG.debug("Registering product with productName={}", productDTO.getProductName());
		Product product = new Product();
		product = productTasksHelperService.createProductFromProductDTO(product, productDTO);
		product.setRegistrationDate(timeHelperService.getCurrentTimeStamp());
		return productRepository.save(product);
	}

	@Override
	@Transactional
	public boolean deleteProductByProductName(String productName) {
		boolean isProductDeleted = true;
		LOG.debug("Attempting to delete product having productName={}", productName);
		try {
			productRepository.deleteByProductName(productName);
			return isProductDeleted;
		} catch (IllegalArgumentException exception) {
			LOG.debug("Product with productName={} can't be deleted as it doesn't exist in the database", productName);
			return (!isProductDeleted);
		}
	}

	@Override
	public List<Product> getAllProducts() {
		LOG.debug("Retrieving the list of all the products");
		return productRepository.findAll(new Sort("productName"));
	}

	@Override
	public List<Product> getProductsByCategory(String category) {

		if (category != null) {
			LOG.debug("Retrieving the list of products belonging to category={}", category);

			return productRepository.getProductsByCategory(category);
		} else
			throw new NoSuchElementException(String.format("productCategory=%d not found", category));
	}

	@Override
	public Product updateProductDetails(ProductDTO productDTO, Authentication authentication) {

		LOG.debug("Attempting to update product={} by user={}", productDTO.getProductName(),
				userTasksHelperService.getLoggedInUserName(authentication));
		Product product = new Product();
		// Get product using DTO to intimate JPA about update operation as a
		// part of the transaction
		product = getProductByProductName(productDTO.getProductName());
		product = productTasksHelperService.createProductFromProductDTO(product, productDTO);
		product.setProductUpdateDate((timeHelperService.getCurrentTimeStamp()));
		product.setProductEditedByUser((userTasksHelperService.getLoggedInUserName(authentication)));
		return productRepository.save(product);
	}

}
