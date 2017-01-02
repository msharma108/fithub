package com.fithub.service.salesorder;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.fithub.domain.OrderDTO;
import com.fithub.domain.SalesOrder;
import com.stripe.model.Refund;

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

	/**
	 * Method updates the provided sales order's status to cancelled in database
	 * along with updating refunded amount and product quantity table
	 * 
	 * @param salesOrder
	 * @param refund
	 *            Stripe Refund object to update the cancelled order's refund
	 *            details
	 * @param authentication
	 *            Spring security authentication object to get logged in
	 *            username
	 * 
	 * @return cancelled sales order
	 */
	SalesOrder cancelSalesOrder(SalesOrder salesOrder, Refund refund, Authentication authentication);

}
