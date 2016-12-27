package com.fithub.service.salesorderitem;

import com.fithub.domain.OrderDTO;
import com.fithub.domain.SalesOrder;

public interface SalesOrderItemHelperService {

	/**
	 * Method synchronizes the sales_order_item table during the creation of a
	 * sales order. Updates the stock quantity of product in Product table for
	 * the product which has been sold
	 * 
	 * @param orderDTO
	 *            Holding details pertaining to the sales order
	 * @param salesOrder
	 *            Sales order being persisted into the database
	 */
	void synchronizeSalesOrderItem(OrderDTO orderDTO, SalesOrder salesOrder);

}
