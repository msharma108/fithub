package com.fithub.service.salesorderitem;

import java.util.List;

import com.fithub.domain.SalesOrderItem;

public interface SalesOrderItemService {

	SalesOrderItem saveSalesOrderItem(SalesOrderItem salesOrderItem);

	List<SalesOrderItem> saveSalesOrderItemList(List<SalesOrderItem> salesOrderItemList);

}
