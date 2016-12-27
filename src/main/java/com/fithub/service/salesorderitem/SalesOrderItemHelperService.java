package com.fithub.service.salesorderitem;

import java.util.List;

import com.fithub.domain.OrderDTO;
import com.fithub.domain.SalesOrder;
import com.fithub.domain.SalesOrderItem;

public interface SalesOrderItemHelperService {

	List<SalesOrderItem> synchronizeSalesOrderItem(OrderDTO orderDTO, SalesOrder salesOrder);

}
