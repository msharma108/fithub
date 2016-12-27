package com.fithub.service.salesorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fithub.domain.OrderDTO;
import com.fithub.domain.SalesOrder;
import com.fithub.domain.SalesOrderDetail;
import com.fithub.domain.User;
import com.fithub.service.user.UserService;

/**
 * Implementation for SalesOrderHelperService
 *
 */
@Service
public class SalesOrderHelperServiceImpl implements SalesOrderHelperService {

	private final static Logger LOG = LoggerFactory.getLogger(SalesOrderHelperServiceImpl.class);
	private final UserService userService;

	@Autowired
	public SalesOrderHelperServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public SalesOrder createSalesOrderFromOrderDTO(SalesOrder salesOrder, OrderDTO orderDTO) {
		LOG.debug("Attempting to create a sales order from OrderDTO with email={}", orderDTO.getEmail());

		salesOrder.setAddress(orderDTO.getAddress());
		salesOrder.setCity(orderDTO.getCity());
		salesOrder.setCountry(orderDTO.getCountry());
		salesOrder.setEmail(orderDTO.getEmail());
		salesOrder.setPhone(orderDTO.getPhone());
		salesOrder.setProvince(orderDTO.getProvince());
		salesOrder.setShippingCharge(orderDTO.getShippingCharge());
		salesOrder.setTax(orderDTO.getTax());
		salesOrder.setZip(orderDTO.getZipcode());
		salesOrder.setStripeChargeId(orderDTO.getStripeChargeId());
		salesOrder.setPaymentStatus(orderDTO.getPaymentStatus());

		// Map the customer for the order
		User customer = userService.getUserByUsername(orderDTO.getCustomerUserNameForThisOrder());
		salesOrder.setUser(customer);

		// Order Detail for the order
		SalesOrderDetail salesOrderDetail = new SalesOrderDetail();

		salesOrder.setSalesOrderDetail(salesOrderDetail);

		return null;
	}

}
