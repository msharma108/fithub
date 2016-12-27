package com.fithub.service.salesorder;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fithub.domain.OrderDTO;
import com.fithub.domain.SalesOrder;
import com.fithub.repository.salesorder.SalesOrderRepository;
import com.fithub.service.time.TimeHelperService;

/**
 * An implementation of the SalesOrder Service
 * 
 *
 */
@Service
public class SalesOrderServiceImpl implements SalesOrderService {

	private static final Logger LOG = LoggerFactory.getLogger(SalesOrderServiceImpl.class);
	private final SalesOrderRepository salesOrderRepository;
	private final TimeHelperService timeHelperService;
	private final SalesOrderHelperService salesOrderHelperService;

	@Autowired
	public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository, TimeHelperService timeHelperService,
			SalesOrderHelperService salesOrderHelperService) {

		this.salesOrderRepository = salesOrderRepository;
		this.timeHelperService = timeHelperService;
		this.salesOrderHelperService = salesOrderHelperService;
	}

	@Override
	public SalesOrder getSalesOrderById(Integer salesOrderId) throws IllegalArgumentException {
		LOG.debug("Retreive sales order having salesOrderId={}", salesOrderId);
		SalesOrder salesOrder = salesOrderRepository.findOne(salesOrderId);

		if (salesOrder != null)
			return salesOrder;
		else
			throw new NoSuchElementException(String.format("salesOrderId=%d not found", salesOrderId));
	}

	@Override
	public List<SalesOrder> getSalesOrderListByUserName(String userName) {
		LOG.debug("Retrieving list of salesOrders associated with userName={}", userName);
		List<SalesOrder> salesOrderList = salesOrderRepository.getSalesOrderByUserName(userName);
		if (salesOrderList != null)
			return salesOrderList;
		else
			throw new NoSuchElementException(String.format("No sales order found for user=%s", userName));
	}

	@Override
	public SalesOrder createSalesOrder(OrderDTO orderDTO) {
		LOG.debug("Attempting to save the order of email={} into database", orderDTO.getEmail());
		SalesOrder salesOrder = new SalesOrder();
		salesOrder= 
		return null;
	}

	@Override
	public List<SalesOrder> getAllSalesOrder() {
		LOG.debug("Retrieving the list of all the sales order");
		List<SalesOrder> salesOrderList = salesOrderRepository.findAll(new Sort("salesOrderCreationDate"));
		if (salesOrderList != null)
			return salesOrderList;
		else
			throw new NoSuchElementException("No sales order found in the database");
	}

}
