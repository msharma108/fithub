package com.fithub.repository.shippingaddress;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fithub.domain.ShippingAddress;

/**
 * Repository for handling CRUD operations for shipping address
 *
 */
@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer> {

	/**
	 * Method retrieves a record form shipping address table based on the
	 * shipping address parameters provided
	 * 
	 * @param address
	 * @param city
	 * @param province
	 * @param zip
	 * @param country
	 * @param phone
	 * @param email
	 * @return shipping address record
	 */
	@Query("select shippingAddress from ShippingAddress shippingAddress where shippingAddress.address =:address AND shippingAddress.city =:city AND shippingAddress.province =:province AND shippingAddress.zip =:zip AND shippingAddress.country =:country AND shippingAddress.phone =:phone AND shippingAddress.email =:email")
	List<ShippingAddress> getShippingAddress(@Param("address") String address, @Param("city") String city,
			@Param("province") String province, @Param("zip") String zip, @Param("country") String country,
			@Param("phone") String phone, @Param("email") String email);

}
