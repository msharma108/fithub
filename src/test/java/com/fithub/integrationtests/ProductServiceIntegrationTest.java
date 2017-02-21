package com.fithub.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import com.fithub.domain.CustomUser;
import com.fithub.domain.Product;
import com.fithub.domain.ProductDTO;
import com.fithub.service.product.ProductService;

/**
 * Class for testing Product Service class integration with the database
 *
 */

@SqlGroup({
		@Sql(scripts = "/integration_test_scripts/product_service-test-data-creation.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "/integration_test_scripts/product_service-test-data-deletion.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) })
public class ProductServiceIntegrationTest extends AbstractFithubApplicationIntegrationTest {

	@Autowired
	private ProductService productService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void productServiceRegistersProductIfProductNameNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String expectedProductName = "testProductNameRustColor5Lbs";
		ProductDTO productDTO = prepareProductDTO(expectedProductName);
		Product product = productService.registerProduct(productDTO);
		assertEquals("Product registration failure", expectedProductName, product.getProductName());

	}

	@Test
	public void productServiceDoesNotRegisterProductIfProductAlreadyInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String productName = "ProteinProduct1";
		ProductDTO productDTO = prepareProductDTO(productName);
		expectedException.expect(DataIntegrityViolationException.class);
		productService.registerProduct(productDTO);
		fail("DataIntegrityViolationException expected");

	}

	@Test
	public void productServiceGetsProductByIdIfIdExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		int productId = 1;
		Product product = productService.getProductById(productId);
		assertNotNull(String.format("productId=%d not found", productId), product);
	}

	@Test
	public void productServiceThrowsNoSuchElementExceptionOnGetProductBasedOnIdNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		int productId = 1001;
		expectedException.expect(NoSuchElementException.class);
		expectedException.expectMessage(String.format("productId=%s not found", productId));
		productService.getProductById(productId);
		fail("NoSuchElementException expected");
	}

	@Test
	public void productServiceGetsProductByProductNameIfProductExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String expectedProductName = "ProteinProduct1";
		Product product = productService.getProductByProductName(expectedProductName);
		assertNotNull(String.format("expectedProductName=%s not found", expectedProductName), product.getProductName());
	}

	@Test
	public void productServiceThrowsNoSuchElementExceptionIfProductWithProductNameDoesNotExistInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String productNameNotInDatabase = "testProductNameRustColor5Lbs";
		Product product = productService.getProductByProductName(productNameNotInDatabase);
		assertNull("Null product provided", product);
	}

	@Test
	public void productServiceMarksProductAsDeletedWhenProductNameExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String productNameOfProductBeingDeleted = "ProteinProduct1";
		ProductDTO productDTO = prepareProductDTO(productNameOfProductBeingDeleted);

		// Mock dependencies for Product Deletion
		Authentication mockAuthentication = mock(Authentication.class);
		CustomUser mockCustomUser = mock(CustomUser.class);
		when(mockAuthentication.getPrincipal()).thenReturn(mockCustomUser);
		when(mockCustomUser.getUserName()).thenReturn("admin");

		boolean actualIsProductDeleted = productService.deleteProduct(productDTO, mockAuthentication);
		boolean expectedIsProductDeleted = true;
		assertEquals(String.format("Product with productName=%s not deleted as it doesnt exist in database",
				productNameOfProductBeingDeleted), expectedIsProductDeleted, actualIsProductDeleted);
	}

	@Test
	public void productServiceDoesNotMarkProductAsDeletedWhenProductNameNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String productNameOfProductBeingDeleted = "ProductNotInDatabase";
		ProductDTO productDTO = prepareProductDTO(productNameOfProductBeingDeleted);

		// Mock dependencies for Product Deletion
		Authentication mockAuthentication = mock(Authentication.class);
		CustomUser mockCustomUser = mock(CustomUser.class);
		when(mockAuthentication.getPrincipal()).thenReturn(mockCustomUser);
		when(mockCustomUser.getUserName()).thenReturn("admin");

		boolean actualIsProductDeleted = productService.deleteProduct(productDTO, mockAuthentication);
		boolean expectedIsProductDeleted = false;
		assertEquals(String.format("Product with productName=%s deleted,problems with deleteProduct Method",
				productNameOfProductBeingDeleted), expectedIsProductDeleted, actualIsProductDeleted);
	}

	@Test
	public void productServiceGetsAllProductsFromDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		List<Product> productList = productService.getAllProducts();
		int expectedProductListSize = 6;

		assertEquals("Problems retrieving the list of all products", expectedProductListSize, productList.size());
	}

	@Test
	public void productServiceGetsListOfTop5ProductsBasedOnQuantitySold() {
		assertDatabaseStateConsistencyBeforeTest();

		List<Product> productList = productService.getTop5ProductsByQuantitySold();
		int expectedProductListSize = 5;
		assertEquals("Possibly less than 5 products in the database", expectedProductListSize, productList.size());
	}

	@Test
	public void productServiceGetsListOfProductsByCategoryIfCategoryExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String category = "Protein";
		List<Product> productList = productService.getProductsByCategory(category);
		assertNotNull(String.format("productCategory=%s not found", category), productList);
	}

	@Test
	public void productServiceReturnsIllegalStateExceptionOnGetListOfProductsBasedOnCategoryNotExistentInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String category = "Jazz";
		expectedException.expect(IllegalStateException.class);
		expectedException.expectMessage(String.format("productCategory=%s has no associated products", category));
		productService.getProductsByCategory(category);
		fail("IllegalStateException expected");
	}

	@Test
	public void productServiceCountsNumberOfProductsInDatabase() {

		assertDatabaseStateConsistencyBeforeTest();
	}

	@Test
	public void productServiceUpdatesProductDetailsIfTheProductExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String productName = "ProteinProduct1";
		ProductDTO productDTO = prepareProductDTO(productName);
		Product product = productService.getProductByProductName(productName);
		Date productUpdateDatePriorToUpdate = product.getProductUpdateDate();

		// Mock dependencies for updating Product Details
		Authentication mockAuthentication = mock(Authentication.class);
		CustomUser mockCustomUser = mock(CustomUser.class);
		when(mockAuthentication.getPrincipal()).thenReturn(mockCustomUser);
		when(mockCustomUser.getUserName()).thenReturn("admin");

		Date productUpdateDateAfterUpdate = productService.updateProductDetails(productDTO, mockAuthentication)
				.getProductUpdateDate();

		assertNotEquals("Problems with updating Product Details", productUpdateDatePriorToUpdate,
				productUpdateDateAfterUpdate);

	}

	@Test
	public void productServiceDoesNotUpdateProductDetailsIfTheProductNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String productName = "ProductNotInDatabase";
		ProductDTO productDTO = prepareProductDTO(productName);

		// Mock dependencies for updating Product Details
		Authentication mockAuthentication = mock(Authentication.class);
		CustomUser mockCustomUser = mock(CustomUser.class);
		when(mockAuthentication.getPrincipal()).thenReturn(mockCustomUser);
		when(mockCustomUser.getUserName()).thenReturn("admin");

		expectedException.expect(NoSuchElementException.class);
		expectedException.expectMessage(String.format("ProductName=%s not found", productDTO.getProductName()));
		productService.updateProductDetails(productDTO, mockAuthentication);
		fail("NoSuchElementException expected");

	}

	@Test
	public void productServiceGetsListOfProductsContainingProductNameOrShortDescriptionIfSearchStringMatchesProductNameOrSDescInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String searchStringLikeProductName = "Protein";
		int expectedProductListSize = 3;

		List<Product> productSearchResultList = productService
				.getProductsContaingProductNameOrShortDescription(searchStringLikeProductName);
		assertEquals("Problems with getProductsContaingProductNameOrShortDescription method", expectedProductListSize,
				productSearchResultList.size());
	}

	@Test
	public void productServiceThrowsIllegalArgumentExceptionIfSearchStringIsBlankForGetProductsContainingProductNameOrShortDescription() {
		assertDatabaseStateConsistencyBeforeTest();

		String searchStringLikeProductName = "";
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Please provide search values for searching the product");
		productService.getProductsContaingProductNameOrShortDescription(searchStringLikeProductName);
		fail("IllegalArgumentException expected");
	}

	@Test
	public void productServiceThrowsNoSuchElementExceptionWhenSearchStringForGetProductsContainingProductNameOrShortDescriptionDoesNotMatchProductsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String searchStringLikeProductName = "Jazz";
		expectedException.expect(NoSuchElementException.class);
		expectedException.expectMessage(
				String.format("Product matching the searchString=%s not found", searchStringLikeProductName));
		productService.getProductsContaingProductNameOrShortDescription(searchStringLikeProductName);
		fail("NoSuchElementException expected");
	}

	private ProductDTO prepareProductDTO(String productName) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductDisplayName("testProductName");
		productDTO.setProductName(productName);
		productDTO.setProductCategory("Protein");
		productDTO.setProductNameBeforeEdit(productName);
		return productDTO;
	}

	private void assertDatabaseStateConsistencyBeforeTest() {
		assertEquals("Database in an inconsistent state before test", 6,
				productService.countNumberOfProductsInDatabase());

	}

}
