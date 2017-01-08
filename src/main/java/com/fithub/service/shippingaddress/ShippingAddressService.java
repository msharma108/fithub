package com.fithub.service.shippingaddress;

import java.util.List;

import com.fithub.domain.ShippingAddress;

/**
 * Service for shipping address operations
 *
 */
public interface ShippingAddressService {

	/**
	 * Method gets shipping address record from the shipping address table in
	 * database based on the provided shipping address information.
	 * 
	 * @param shippingAddressCreationDate
	 * @return ShippingAddress based on the provided shipping address
	 *         information
	 */
	List<ShippingAddress> getShippingAddressByShippingAddressParameters(String address, String city, String province,
			String zip, String country, String phone, String email);

	/**
	 * Method counts the number of shipping address records in the database
	 * 
	 * @return the number of shipping address records in the shipping address
	 *         table
	 */
	long countNumberOfShippingAddressInDatabase();

	/**
	 * Method gets shipping address record based on the shipping address id
	 * provided
	 * 
	 * @param shippingAddressId
	 *            primary key for shippingAddress table
	 * 
	 * @return shippingAddress record for the id provided
	 */
	ShippingAddress getShippingAddressById(int shippingAddressId);

}
