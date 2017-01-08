package com.fithub.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

import com.fithub.domain.OrderDTO;
import com.fithub.domain.ProductDTO;
import com.fithub.domain.SalesOrder;
import com.fithub.service.salesorder.SalesOrderService;

/**
 * Class for testing Sales Order Service class integration with the database
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("integration_testing")
@Transactional
@SqlGroup({
		@Sql(scripts = "/integration_test_scripts/sales_order_service-test-data-creation.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "/integration_test_scripts/sales_order_service-test-data-deletion.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) })
public class SalesOrderServiceIntegrationTest {

	@Autowired
	private SalesOrderService salesOrderService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void salesOrderServiceGetsSalesOrderIfSalesOrderIdInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		int salesOrderId = 1;
		SalesOrder salesOrder = salesOrderService.getSalesOrderById(salesOrderId);
		assertNotNull(String.format("salesOrderId=%d not found", salesOrderId), salesOrder);
	}

	@Test
	public void salesOrderServiceDoesNotGetSalesOrderIfSalesOrderIdNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		int salesOrderId = 1001;
		expectedException.expect(NoSuchElementException.class);
		expectedException.expectMessage(String.format("salesOrderId=%d not found", salesOrderId));
		salesOrderService.getSalesOrderById(salesOrderId);
		fail("NoSuchElementException expected");
	}

	@Test
	public void salesOrderServiceGetsSalesOrderListByUserNameIfUserNameInDatabaseHasSalesOrder() {
		assertDatabaseStateConsistencyBeforeTest();

		String userNameInDatabase = "mohitshsh";
		List<SalesOrder> salesOrderList = salesOrderService.getSalesOrderListByUserName(userNameInDatabase);
		boolean expecedSalesOrderListForUserNameIsEmpty = false;

		assertEquals(String.format("No sales order found for user=%s", userNameInDatabase),
				expecedSalesOrderListForUserNameIsEmpty, salesOrderList.isEmpty());
	}

	@Test
	public void salesOrderServiceThrowsNoSuchElementExceptionOnGetSalesOrderListByUserNameIfUserNameInDatabaseHasNoSalesOrder() {
		assertDatabaseStateConsistencyBeforeTest();

		String userNameInDatabase = "actualUser";

		expectedException.expect(NoSuchElementException.class);
		expectedException.expectMessage(String.format("No sales order found for user=%s", userNameInDatabase));

		salesOrderService.getSalesOrderListByUserName(userNameInDatabase);
		fail("NoSuchElementException expected");
	}

	@Test
	public void salesOrderServiceGetsAllSalesOrdersFromDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		List<SalesOrder> salesOrderList = salesOrderService.getAllSalesOrder();
		int expectedSalesOrderListSize = 2;

		assertEquals("Problems retrieving the list of all sales orders", expectedSalesOrderListSize,
				salesOrderList.size());
	}

	@Test
	public void salesOrderServiceCreatesSalesOrderIfSalesOrderNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userNameOfOrderCreator = "mohitshsh";
		OrderDTO orderDTO = prepareOrderDTO(userNameOfOrderCreator);

		SalesOrder salesOrder = salesOrderService.createSalesOrder(orderDTO);

		assertNotNull("Sales Order creation failed", salesOrder);
	}

	@Test
	public void salesOrderServiceCountsNumberOfSalesOrdersInDatabase() {

		assertDatabaseStateConsistencyBeforeTest();
	}

	private OrderDTO prepareOrderDTO(String userNameOfOrderCreator) {
		OrderDTO orderDTO = new OrderDTO();

		orderDTO.setOrderProductList(prepareProductDTOList());

		orderDTO.setStripeChargeId("chargeID");
		orderDTO.setPaymentStatus("succeeded");
		orderDTO.setAddress("address");
		orderDTO.setCity("city");
		orderDTO.setCountry("country");
		orderDTO.setPhone("1243123424");
		orderDTO.setEmail("test@test.test");
		orderDTO.setProvince("province");
		orderDTO.setZipcode("1k11k11");

		orderDTO.setCustomerUserNameForThisOrder(userNameOfOrderCreator);

		return orderDTO;
	}

	private List<ProductDTO> prepareProductDTOList() {
		List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
		productDTOList.add(prepareProductDTO("ProteinProduct1"));
		productDTOList.add(prepareProductDTO("VitaminProduct1"));
		return productDTOList;
	}

	private ProductDTO prepareProductDTO(String productName) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductDisplayName("testProductName");
		productDTO.setQuantityInCart(BigDecimal.TEN);
		productDTO.setProductName(productName);
		productDTO.setProductCategory("Protein");
		productDTO.setProductNameBeforeEdit(productName);
		return productDTO;
	}

	private void assertDatabaseStateConsistencyBeforeTest() {
		assertEquals("Database in an inconsistent state before test", 2,
				salesOrderService.countNumberOfSalesOrderInDatabase());

	}

}
