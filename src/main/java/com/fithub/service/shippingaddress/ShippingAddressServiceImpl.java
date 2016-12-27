package com.fithub.service.shippingaddress;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fithub.domain.ShippingAddress;
import com.fithub.repository.shippingaddress.ShippingAddressRepository;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

	private final ShippingAddressRepository shippingAddressRepository;
	private static final Logger LOG = LoggerFactory.getLogger(ShippingAddressServiceImpl.class);

	public ShippingAddressServiceImpl(ShippingAddressRepository shippingAddressRepository) {
		this.shippingAddressRepository = shippingAddressRepository;

	}

	@Override
	public List<ShippingAddress> getShippingAddress(String address, String city, String province, String zip,
			String country, String phone, String email) {
		LOG.debug("Attempting to retrieve shipping address based on provided parameters");
		List<ShippingAddress> shippingAddressList = new ArrayList<ShippingAddress>();
		shippingAddressList = shippingAddressRepository.getShippingAddress(address, city, province, zip, country, phone,
				email);

		return shippingAddressList;

	}

}
