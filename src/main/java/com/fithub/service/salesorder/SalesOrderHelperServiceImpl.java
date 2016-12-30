package com.fithub.service.salesorder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fithub.domain.OrderDTO;
import com.fithub.domain.SalesOrder;
import com.fithub.domain.ShippingAddress;
import com.fithub.domain.User;
import com.fithub.service.shippingaddress.ShippingAddressService;
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
	private final ShippingAddressService shippingAddressService;

	@Autowired
	public SalesOrderHelperServiceImpl(UserService userService, TimeHelperService timeHelperService,
			ShippingAddressService shippingAddressService) {
		this.userService = userService;
		this.timeHelperService = timeHelperService;
		this.shippingAddressService = shippingAddressService;
	}

	@Override
	public SalesOrder createSalesOrderFromOrderDTO(SalesOrder salesOrder, OrderDTO orderDTO) {
		LOG.debug("Attempting to create a sales order from OrderDTO with email={}", orderDTO.getEmail());

		// populate salesOrder from orderDTO
		salesOrder.setShippingCharge(orderDTO.getShippingCharge());
		salesOrder.setTax(orderDTO.getTax());
		salesOrder.setSalesOrderTotalCost(orderDTO.getOrderTotalCost());
		salesOrder.setTrackingNumber(generateTrackingNumber());
		salesOrder.setStripeChargeId(orderDTO.getStripeChargeId());
		salesOrder.setPaymentStatus(orderDTO.getPaymentStatus());
		salesOrder.setStatus(orderDTO.getOrderStatus());

		// Map the customer for the order
		User customer = userService.getUserByUsername(orderDTO.getCustomerUserNameForThisOrder());
		customer.addSalesOrder(salesOrder);
		salesOrder.setUser(customer);

		// ## Map shipping address by creating new for now
		ShippingAddress shippingAddress = new ShippingAddress();

		List<ShippingAddress> shippingAddressList = new ArrayList<ShippingAddress>();

		// Checking if the shipping address already exists in DB
		shippingAddressList = shippingAddressService.getShippingAddress(orderDTO.getAddress(), orderDTO.getCity(),
				orderDTO.getProvince(), orderDTO.getZipcode(), orderDTO.getCountry(), orderDTO.getPhone(),
				orderDTO.getEmail());

		// List can contain only 1 element based on parameters

		if (!shippingAddressList.isEmpty())
			// mapping an existing shipping address with the sales order
			salesOrder.setShippingAddress(shippingAddressList.get(0));
		else {
			// create a new shipping order
			shippingAddress.setAddress(orderDTO.getAddress());
			shippingAddress.setCity(orderDTO.getCity());
			shippingAddress.setCountry(orderDTO.getCountry());
			shippingAddress.setEmail(orderDTO.getEmail());
			shippingAddress.setPhone(orderDTO.getPhone());
			shippingAddress.setProvince(orderDTO.getProvince());
			shippingAddress.setZip(orderDTO.getZipcode());
			salesOrder.setShippingAddress(shippingAddress);
		}

		return salesOrder;
	}

	@Override
	public OrderDTO populateOrderDTOFromOrder(SalesOrder salesOrder) {

		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setOrderId(salesOrder.getSalesOrderId());
		orderDTO.setShippingCharge(salesOrder.getShippingCharge());
		orderDTO.setTax(salesOrder.getTax());
		orderDTO.setOrderTotalCost(salesOrder.getSalesOrderTotalCost());
		orderDTO.setOrderCreationDate(salesOrder.getSalesOrderCreationDate());
		orderDTO.setOrderEditDate(salesOrder.getSalesOrderEditDate());
		orderDTO.setOrderRefundAmount(salesOrder.getSalesOrderRefundAmount());
		orderDTO.setStripeRefundId(salesOrder.getStripeRefundId());
		orderDTO.setTrackingNumber(salesOrder.getTrackingNumber());
		orderDTO.setStripeChargeId(salesOrder.getStripeChargeId());
		orderDTO.setPaymentStatus(salesOrder.getPaymentStatus());
		orderDTO.setOrderStatus(salesOrder.getStatus());
		orderDTO.setPaymentStatus(salesOrder.getPaymentStatus());

		orderDTO.setUser(salesOrder.getUser());
		orderDTO.setShippingAddress(salesOrder.getShippingAddress());
		orderDTO.setSalesOrderItems(salesOrder.getSalesOrderItems());

		return orderDTO;
	}

	/**
	 * Method generates a unique random tracking number by replacing : & - in
	 * the current date time
	 * 
	 * @return unique random tracking number as string
	 */
	private String generateTrackingNumber() {

		// generate a unique random tracking number based on current time
		int loopControl = 0;
		StringBuffer randomStringBuffer = new StringBuffer(timeHelperService.getCurrentTimeStamp().toString());
		while (loopControl < 2) {

			randomStringBuffer = randomStringBuffer.replace(randomStringBuffer.indexOf(":"),
					randomStringBuffer.indexOf(":") + 1, RandomStringUtils.randomAlphanumeric(1));

			randomStringBuffer = randomStringBuffer.replace(randomStringBuffer.indexOf("-"),
					randomStringBuffer.indexOf("-") + 1, RandomStringUtils.randomAlphanumeric(1));

			loopControl++;
		}

		randomStringBuffer = randomStringBuffer.replace(randomStringBuffer.indexOf(" "),
				randomStringBuffer.indexOf(" ") + 1, "");

		randomStringBuffer = randomStringBuffer.replace(randomStringBuffer.indexOf("."),
				randomStringBuffer.indexOf(".") + 1, "");

		return randomStringBuffer.toString();
	}

}
