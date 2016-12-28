package com.fithub.service.shippingaddress;

import java.util.List;

import com.fithub.domain.ShippingAddress;

/**
 * Service for shipping address operations
 *
 */
public interface ShippingAddressService {

	/**
	 * Method gets shipping address record from the shipping address table in db
	 * based on the provided shipping address creation date.
	 * 
	 * @param shippingAddressCreationDate
	 * @return ShippingAddress based on the provided shipping address creation
	 *         date
	 */
	List<ShippingAddress> getShippingAddress(String address, String city, String province, String zip, String country,
			String phone, String email);

}
