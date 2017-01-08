package com.fithub.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import com.fithub.domain.ProductCategory;
import com.fithub.service.productcategory.ProductCategoryService;

/**
 * Class for testing ProductCategory Service class integration with the database
 *
 */

@SqlGroup({
		@Sql(scripts = "/integration_test_scripts/product_category_service-test-data-creation.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "/integration_test_scripts/product_category_service-test-data-deletion.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) })
public class ProductCategoryServiceIntegrationTest extends AbstractFithubApplicationIntegrationTest {

	@Autowired
	private ProductCategoryService productCategoryService;

	@Test
	public void productCategoryServiceGetsAllProductCategoriesInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();
		int expectedProductCategories = 2;
		List<ProductCategory> productCategoryList = productCategoryService.getAllProductCategories();

		assertEquals("Problems retrieving the list of all products", expectedProductCategories,
				productCategoryList.size());
	}

	@Test
	public void productCategoryServiceCountsNumberOfProductCategoriesInDatabase() {

		assertDatabaseStateConsistencyBeforeTest();
	}

	@Test
	public void productCategoryServiceGetsProductCategoryByCategoryIfCategoryExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String categoryInDatabase = "Protein";
		ProductCategory productCategory = productCategoryService.getProductCategoryByCategory(categoryInDatabase);
		assertNotNull(String.format("productCategory=%s not found", categoryInDatabase), productCategory);
	}

	@Test
	public void productCategoryServiceReturnsNullOnGetProductCategoryByCategoryIfCategoryNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String categoryNotInDatabase = "Jazz";
		ProductCategory productCategory = productCategoryService.getProductCategoryByCategory(categoryNotInDatabase);
		assertNull(String.format("Product Category=%s shouldn't exist in database", categoryNotInDatabase),
				productCategory);
	}

	private void assertDatabaseStateConsistencyBeforeTest() {
		assertEquals("Database in an inconsistent state before test", 2,
				productCategoryService.countNumberOfProductCategoriesInDatabase());

	}

}
