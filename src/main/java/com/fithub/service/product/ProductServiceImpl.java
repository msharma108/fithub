package com.fithub.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fithub.domain.CustomUser;
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
	public Product getProductById(Integer productId) {
		LOG.debug("Retreive product having productId={}", productId);
		Product product = productRepository.findOne(productId);

		if (product != null)
			return product;
		else
			throw new NoSuchElementException(String.format("productId=%s not found", productId));
	}

	@Override
	public Product getProductByProductName(String productName) throws IllegalArgumentException {
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
	public boolean deleteProduct(ProductDTO productDTO, Authentication authentication) {
		boolean isProductDeleted = true;
		LOG.debug("Attempting to delete product having productName={}", productDTO.getProductName());
		try {
			Product product = getProductByProductName(productDTO.getProductName());
			productDTO = productTasksHelperService.destroyProductDataForDeletion(productDTO);
			product = productTasksHelperService.createProductFromProductDTO(product, productDTO);
			product.setProductUpdateDate(timeHelperService.getCurrentTimeStamp());
			product.setIsProductDeleted(true);

			// Get the name of admin who deleted the product
			CustomUser loggedInAdmin = (CustomUser) authentication.getPrincipal();
			product.setProductEditedByUser(loggedInAdmin.getUserName());
			product = productRepository.save(product);
			return isProductDeleted;
		} catch (NoSuchElementException exception) {
			LOG.debug("Product with productName={} can't be deleted as it doesn't exist in the database",
					productDTO.getProductName());
			isProductDeleted = false;
			return isProductDeleted;
		}
	}

	@Override
	public List<Product> getAllProducts() {
		LOG.debug("Retrieving the list of all the products");
		return productRepository.findAll(new Sort("productName"));
	}

	@Override
	public List<Product> getTop5ProductsByQuantitySold() {
		LOG.debug("Retrieving the list of top products based on quantity sold");
		return productRepository.findTop5ByOrderByQuantitySoldDesc();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {

		LOG.debug("Retrieving the list of products belonging to category={}", category);

		List<Product> productList = productRepository.getProductsByCategory(category);
		if (!productList.isEmpty())
			return productList;
		else
			throw new IllegalStateException(String.format("productCategory=%s has no associated products", category));
	}

	@Override
	public Product updateProductDetails(ProductDTO productDTO, Authentication authentication) {

		LOG.debug("Attempting to update product={} by user={}", productDTO.getProductName(),
				userTasksHelperService.getLoggedInUserName(authentication));
		Product product = new Product();
		// Get product using DTO to intimate JPA about update operation as a
		// part of the transaction
		product = getProductByProductName(productDTO.getProductNameBeforeEdit());
		product = productTasksHelperService.createProductFromProductDTO(product, productDTO);
		product.setProductUpdateDate((timeHelperService.getCurrentTimeStamp()));
		product.setProductEditedByUser((userTasksHelperService.getLoggedInUserName(authentication)));
		return productRepository.save(product);
	}

	@Override
	public List<Product> getProductsContaingProductNameOrShortDescription(String productSearchString) {
		LOG.debug("Attempting to find products matching searchString={}", productSearchString);
		List<Product> productList = new ArrayList<Product>();

		// Repository invocation based on passed in search strings
		if (productSearchString.equals(""))
			throw new IllegalArgumentException("Please provide search values for searching the product");

		else
			productList = productRepository.findByProductNameContainingIgnoreCaseOrSdescIgnoreCaseContaining(
					productSearchString, productSearchString);

		if (!productList.isEmpty())
			return productList;
		else
			throw new NoSuchElementException(
					String.format("Product matching the searchString=%s not found", productSearchString));

	}

	@Override
	public long countNumberOfProductsInDatabase() {
		return productRepository.count();
	}

}
