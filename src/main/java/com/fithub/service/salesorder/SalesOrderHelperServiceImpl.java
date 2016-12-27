package com.fithub.service.salesorder;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fithub.domain.OrderDTO;
import com.fithub.domain.SalesOrder;
import com.fithub.domain.ShippingAddress;
import com.fithub.domain.User;
import com.fithub.service.product.ProductService;
import com.fithub.service.product.ProductTasksHelperService;
import com.fithub.service.salesorderitem.SalesOrderItemService;
import com.fithub.service.time.TimeHelperService;
import com.fithub.service.user.UserService;

/**
 * Implementation for SalesOrderHelperService
 *
 */
@Service
public class SalesOrderHelperServiceImpl implements SalesOrderHelperService {

	private final static Logger LOG = LoggerFactory.getLogger(SalesOrderHelperServiceImpl.class);
	private final UserService userService;
	private final TimeHelperService timeHelperService;
	private final ProductTasksHelperService productTasksHelperService;
	private final SalesOrderItemService salesOrderItemService;
	private final ProductService productService;

	@Autowired
	public SalesOrderHelperServiceImpl(UserService userService, TimeHelperService timeHelperService,
			ProductTasksHelperService productTasksHelperService, SalesOrderItemService salesOrderItemService,
			ProductService productService) {
		this.userService = userService;
		this.timeHelperService = timeHelperService;
		this.productTasksHelperService = productTasksHelperService;
		this.salesOrderItemService = salesOrderItemService;
		this.productService = productService;
	}

	@Override
	public SalesOrder createSalesOrderFromOrderDTO(SalesOrder salesOrder, OrderDTO orderDTO) {
		LOG.debug("Attempting to create a sales order from OrderDTO with email={}", orderDTO.getEmail());

		salesOrder.setShippingCharge(orderDTO.getShippingCharge());
		salesOrder.setTax(orderDTO.getTax());
		salesOrder.setSalesOrderTotalCost(orderDTO.getOrderTotalCost());
		salesOrder.setTrackingNumber(generateTrackingNumber());
		salesOrder.setStripeChargeId(orderDTO.getStripeChargeId());
		salesOrder.setPaymentStatus(orderDTO.getPaymentStatus());

		// Map the customer for the order
		User customer = userService.getUserByUsername(orderDTO.getCustomerUserNameForThisOrder());
		customer.addSalesOrder(salesOrder);
		salesOrder.setUser(customer);

		// ## Map shipping address by creating new for now
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setAddress(orderDTO.getAddress());
		shippingAddress.setCity(orderDTO.getCity());
		shippingAddress.setCountry(orderDTO.getCountry());
		shippingAddress.setEmail(orderDTO.getEmail());
		shippingAddress.setPhone(orderDTO.getPhone());
		shippingAddress.setProvince(orderDTO.getProvince());
		shippingAddress.setZip(orderDTO.getZipcode());
		salesOrder.setShippingAddress(shippingAddress);

		return salesOrder;
	}

	private String generateTrackingNumber() {

		// generate a unique random tracking number
		return timeHelperService.getCurrentTimeStamp().toString().replace("%:-%",
				RandomStringUtils.randomAlphanumeric(5));
	}

}
