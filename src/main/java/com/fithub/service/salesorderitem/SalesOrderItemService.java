package com.fithub.service.salesorderitem;

import java.util.List;

import com.fithub.domain.SalesOrderItem;

public interface SalesOrderItemService {

	/**
	 * Method saves the SalesOrderItem for a sales order
	 * 
	 * @param salesOrderItem
	 * @return SalesOrderItem: The saved salesOrderItem
	 */
	SalesOrderItem saveSalesOrderItem(SalesOrderItem salesOrderItem);

	/**
	 * Method saves the list of SalesOrderItem for a sales order if there are
	 * multiple products in the order. Each SalesOrderItem will correspond to a
	 * Product Item
	 * 
	 * @param salesOrderItemList
	 * @return list of SalesOrderItem for a sales order
	 */
	List<SalesOrderItem> saveSalesOrderItemList(List<SalesOrderItem> salesOrderItemList);

}
