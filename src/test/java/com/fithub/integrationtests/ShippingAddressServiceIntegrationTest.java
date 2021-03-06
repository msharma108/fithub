package com.fithub.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import com.fithub.domain.ShippingAddress;
import com.fithub.service.shippingaddress.ShippingAddressService;

/**
 * Class for testing ShippingAddressService class integration with the database
 *
 */

@SqlGroup({
		@Sql(scripts = "/integration_test_scripts/shipping_address_service-test-data-creation.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "/integration_test_scripts/shipping_address_service-test-data-deletion.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) })
public class ShippingAddressServiceIntegrationTest extends AbstractFithubApplicationIntegrationTest {

	@Autowired
	ShippingAddressService shippingAddressService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void shippingAddressServiceCountsNumberOfShippingAddressRecordsInDatabase() {

		assertDatabaseStateConsistencyBeforeTest();
	}

	@Test
	public void shippingAddressServiceGetsShippingAddressByIdIfIdInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		int shippingAddressIdInDatabase = 1;
		assertNotNull("Shipping address not found",
				shippingAddressService.getShippingAddressById(shippingAddressIdInDatabase));
	}

	@Test
	public void shippingAddressServiceThrowsNoSuchElementExceptionOnGetShippingAddressByIdIfIdNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		int shippingAddressIdNotInDatabase = 101;
		expectedException.expect(NoSuchElementException.class);
		expectedException
				.expectMessage(String.format("ShippingAddress for id=%s not found", shippingAddressIdNotInDatabase));
		shippingAddressService.getShippingAddressById(shippingAddressIdNotInDatabase);
		fail("NoSuchElement exception expected");

	}

	@Test
	public void shippingAddressServiceGetsShippingAddressByShippingAddressParametersIfShippingAddressExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();
		int shippingAddressIdInDatabase = 1;

		Map<String, String> shippingAddressMap = prepareShippingAddress(shippingAddressIdInDatabase);

		// Call shippingAddressService based on existing shippingAddress
		// parameters. It returns 1 unique record in a list
		List<ShippingAddress> shippingAddressList = shippingAddressService
				.getShippingAddressByShippingAddressParameters(shippingAddressMap.get("address"),
						shippingAddressMap.get("city"), shippingAddressMap.get("province"),
						shippingAddressMap.get("zip"), shippingAddressMap.get("country"),
						shippingAddressMap.get("phone"), shippingAddressMap.get("email"));

		assertNotNull("Shipping address with the specified shipping address parameters not found",
				shippingAddressList.get(0));
	}

	@Test
	public void shippingAddressServiceReturnsEmptyListOfShippingAddressOnGetShippingAddressByShippingAddressParametersIfShippingAddressWithTheParametersNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();
		int shippingAddressIdInDatabaseToPopulateHashMap = 1;
		boolean expectedShippingAddressListIsEmpty = true;
		Map<String, String> shippingAddressMap = prepareShippingAddress(shippingAddressIdInDatabaseToPopulateHashMap);

		// Set non-existent zip to make shipping address non-existent in
		// database
		shippingAddressMap.put("zip", "NotInDatabase");

		List<ShippingAddress> shippingAddressList = shippingAddressService
				.getShippingAddressByShippingAddressParameters(shippingAddressMap.get("address"),
						shippingAddressMap.get("city"), shippingAddressMap.get("province"),
						shippingAddressMap.get("zip"), shippingAddressMap.get("country"),
						shippingAddressMap.get("phone"), shippingAddressMap.get("email"));
		assertEquals("Shipping Address found when it should not be found in database",
				expectedShippingAddressListIsEmpty, shippingAddressList.isEmpty());
	}

	private Map<String, String> prepareShippingAddress(int shippingAddressIdInDatabase) {
		Map<String, String> shippingAddressMap = new HashMap<String, String>();
		ShippingAddress shippingAddress = shippingAddressService.getShippingAddressById(shippingAddressIdInDatabase);

		shippingAddressMap.put("address", shippingAddress.getAddress());
		shippingAddressMap.put("city", shippingAddress.getCity());
		shippingAddressMap.put("province", shippingAddress.getProvince());
		shippingAddressMap.put("zip", shippingAddress.getZip());
		shippingAddressMap.put("country", shippingAddress.getCountry());
		shippingAddressMap.put("phone", shippingAddress.getPhone());
		shippingAddressMap.put("email", shippingAddress.getEmail());
		return shippingAddressMap;

	}

	private void assertDatabaseStateConsistencyBeforeTest() {
		assertEquals("Database in an inconsistent state before test", 1,
				shippingAddressService.countNumberOfShippingAddressInDatabase());

	}

}
