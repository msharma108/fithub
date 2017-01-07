package com.fithub.integrationtests;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fithub.service.product.ProductService;
import com.fithub.service.time.TimeHelperService;

/**
 * Class for testing Product Service class integration with the database
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("integration_testing")
@Transactional
@SqlGroup({
		@Sql(scripts = "/integration_test_scripts/product_service-test-data-creation.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "/integration_test_scripts/product_service-test-data-deletion.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) })
public class ProductServiceIntegrationTest {

	@Autowired
	private ProductService productService;
	@Autowired
	private TimeHelperService timeHelperService;

	// @Test
	// public void productServiceRegistersProductIfProductName() {
	// assertDatabaseStateConsistencyBeforeTest();
	//
	// String expectedUserName = "testUserName";
	// UserDTO userDTO = prepareUserDTO(expectedUserName);
	// User user = userService.createUser(userDTO);
	// assertEquals("User registration failure", expectedUserName,
	// user.getUserName());
	//
	// }

	protected void assertDatabaseStateConsistencyBeforeTest() {
		assertEquals("Database in an inconsistent state before test", 2,
				productService.countNumberOfProductsInDatabase());

	}

}
