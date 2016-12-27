package com.fithub.service.salesorder;

import java.util.List;

import com.fithub.domain.OrderDTO;
import com.fithub.domain.SalesOrder;

/**
 * An interface for the services pertinent to sales order
 *
 */
public interface SalesOrderService {

	/**
	 * Method gets a sales order based on the provided sales order id
	 * 
	 * @param salesOrderId
	 * @return record from the sales order table
	 */
	SalesOrder getSalesOrderById(Integer salesOrderId);

	/**
	 * Method gets list of sales order against a userName
	 * 
	 * @param userName
	 * @return list of sales order table rows
	 */
	List<SalesOrder> getSalesOrderListByUserName(String userName);

	/**
	 * Method creates a sales order based on the provided orderDTO form
	 * 
	 * @param userDTO
	 * @return User
	 */
	SalesOrder createSalesOrder(OrderDTO orderDTO);

	/**
	 * Method gets a list of all the sales order from the database
	 * 
	 * @return List of all the sales order ordered by the salesOrderCreationDate
	 */
	List<SalesOrder> getAllSalesOrder();

}
