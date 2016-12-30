package com.fithub.service.salesorder;

import com.fithub.domain.OrderDTO;
import com.fithub.domain.SalesOrder;

/**
 * Service that helps with operations pertaining to sales order
 * 
 *
 */
public interface SalesOrderHelperService {

	/**
	 * Method prepares a SalesOrder object based on the properties retrieved
	 * from a order transfer object
	 * 
	 * @param salesOrder
	 *            Reference to a newly created salesOrder object
	 * @param orderDTO
	 *            Order Data Transfer Object populated with properties retrieved
	 *            from the View
	 * @return salesOrder SalesOrder object to be stored in the database
	 */
	SalesOrder createSalesOrderFromOrderDTO(SalesOrder salesOrder, OrderDTO orderDTO);

	/**
	 * Method populates the Order DTO from sales Order object
	 * 
	 * @param salesOrder
	 * @return OrderDTO containing data to be displayed on UI
	 */
	OrderDTO populateOrderDTOFromOrder(SalesOrder salesOrder);

}
