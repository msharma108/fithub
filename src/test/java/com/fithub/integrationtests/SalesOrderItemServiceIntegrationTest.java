package com.fithub.integrationtests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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

import com.fithub.domain.SalesOrderItem;
import com.fithub.service.product.ProductService;
import com.fithub.service.salesorder.SalesOrderService;
import com.fithub.service.salesorderitem.SalesOrderItemService;

/**
 * Class for testing SalesOrderItemService class integration with the database
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
public class SalesOrderItemServiceIntegrationTest {

	@Autowired
	SalesOrderItemService salesOrderItemService;

	@Autowired
	ProductService productService;

	@Autowired
	SalesOrderService salesOrderService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void salesOrderItemServiceSavesSalesOrderItemListIfListNotEmpty() {
		assertDatabaseStateConsistencyBeforeTest();
		int expectedSalesOrderItemListSize = 2;
		List<SalesOrderItem> salesOrderItemList = prepareSalesOrderItemList();
		salesOrderItemList = salesOrderItemService.saveSalesOrderItemList(salesOrderItemList);
		assertEquals("Sales Order Item List not saved", expectedSalesOrderItemListSize, salesOrderItemList.size());
	}

	private List<SalesOrderItem> prepareSalesOrderItemList() {
		List<SalesOrderItem> salesOrderItemList = new ArrayList<SalesOrderItem>();

		// Add two products to sales order item
		for (int i = 1; i < 3; i++) {
			SalesOrderItem salesOrderItem = new SalesOrderItem();
			salesOrderItem.setProduct(productService.getProductById(i));
			salesOrderItem.setSalesOrderItemQuantitySold(1);
			salesOrderItem.setSalesOrder(salesOrderService.getSalesOrderById(1));
			salesOrderItemList.add(salesOrderItem);
		}
		return salesOrderItemList;

	}

	@Test
	public void salesOrderItemServiceThrowsIllegalArgumentExceptionOnSaveSalesOrderItemListIfListIsEmpty() {

		List<SalesOrderItem> salesOrderItemList = new ArrayList<SalesOrderItem>();
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("SalesOrderItem list is empty");
		salesOrderItemService.saveSalesOrderItemList(salesOrderItemList);
	}

	@Test
	public void salesOrderItemServiceCountsNumberOfSalesOrderItemInDatabase() {

		assertDatabaseStateConsistencyBeforeTest();
	}

	private void assertDatabaseStateConsistencyBeforeTest() {
		assertEquals("Database in an inconsistent state before test", 3,
				salesOrderItemService.countNumberOfSalesOrderItemInDatabase());

	}

}
