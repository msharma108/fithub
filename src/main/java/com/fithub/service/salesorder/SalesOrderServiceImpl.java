package com.fithub.service.salesorder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fithub.domain.OrderDTO;
import com.fithub.domain.Product;
import com.fithub.domain.SalesOrder;
import com.fithub.domain.SalesOrderItem;
import com.fithub.repository.salesorder.SalesOrderRepository;
import com.fithub.service.product.ProductService;
import com.fithub.service.salesorderitem.SalesOrderItemHelperService;
import com.fithub.service.time.TimeHelperService;
import com.stripe.model.Refund;

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
	private final SalesOrderItemHelperService salesOrderItemHelperService;
	private final ProductService productService;

	@Autowired
	public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository, TimeHelperService timeHelperService,
			SalesOrderHelperService salesOrderHelperService, SalesOrderItemHelperService salesOrderItemHelperService,
			ProductService productService) {

		this.salesOrderRepository = salesOrderRepository;
		this.timeHelperService = timeHelperService;
		this.salesOrderHelperService = salesOrderHelperService;
		this.salesOrderItemHelperService = salesOrderItemHelperService;
		this.productService = productService;
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
		if (!salesOrderList.isEmpty())
			return salesOrderList;
		else
			throw new NoSuchElementException(String.format("No sales order found for user=%s", userName));
	}

	@Override
	public SalesOrder createSalesOrder(OrderDTO orderDTO) {
		LOG.debug("Attempting to save the order of email={} into database", orderDTO.getEmail());
		SalesOrder salesOrder = new SalesOrder();
		salesOrder = salesOrderHelperService.createSalesOrderFromOrderDTO(salesOrder, orderDTO);
		salesOrder.setSalesOrderCreationDate(timeHelperService.getCurrentTimeStamp());
		salesOrder = salesOrderRepository.save(salesOrder);
		// Update sales order item table as part of the order creation
		salesOrderItemHelperService.synchronizeSalesOrderItem(orderDTO, salesOrder);
		return salesOrder;
	}

	@Override
	public List<SalesOrder> getAllSalesOrder() {
		LOG.debug("Retrieving the list of all the sales order");
		List<SalesOrder> salesOrderList = salesOrderRepository.findAll(new Sort("salesOrderCreationDate"));
		if (!salesOrderList.isEmpty())
			return salesOrderList;
		else
			throw new NoSuchElementException("No sales order found in the database");
	}

	@Override
	public SalesOrder cancelSalesOrder(SalesOrder salesOrder, Refund refund) {
		LOG.debug("Attempting to process cancellation of sales order with id={}", salesOrder.getSalesOrderId());

		String paymentStatusRefunded = "refunded";
		String orderStatusCancelled = "CANCELED";
		int centsToDollar = 100;

		// Parameters to be updated for cancellation of order
		salesOrder.setStripeRefundId(refund.getId());
		// refund amount is in cents
		salesOrder.setSalesOrderRefundAmount(new BigDecimal(refund.getAmount() / centsToDollar));
		salesOrder.setPaymentStatus(paymentStatusRefunded);
		salesOrder.setStatus(orderStatusCancelled);
		salesOrder.setSalesOrderEditDate(timeHelperService.getCurrentTimeStamp());

		// Get Sales Order Item associated with the sales order
		List<SalesOrderItem> salesOrderItemList = new ArrayList<SalesOrderItem>();
		salesOrderItemList = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItemMappedToSalesOrder : salesOrderItemList) {
			Product product = salesOrderItemMappedToSalesOrder.getProduct();
			int productQuantitySold = salesOrderItemMappedToSalesOrder.getSalesOrderItemQuantitySold();

			// Update the product quantity in product table for canceled product
			product.setQuantitySold(product.getQuantitySold() - productQuantitySold);
			product.setStockQuantity(product.getStockQuantity() + productQuantitySold);

		}

		return salesOrderRepository.save(salesOrder);
	}

}
