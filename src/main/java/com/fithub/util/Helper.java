/**
 * 
 */
package com.fithub.util;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fithub.domain.User;
import com.fithub.domain.UserDTO;

/**
 * Helper class containing helper methods
 *
 */
public class Helper {

	public static User createUserFromUserDTO(User user, UserDTO userDTO) {

		Timestamp registrationDateTime = new Timestamp(new Date().getTime());

		user.setAddress(userDTO.getAddress());
		user.setCity(userDTO.getCity());
		user.setCountry(userDTO.getCountry());
		user.setDateOfBirth(userDTO.getDateOfBirth());
		user.setEmail(userDTO.getEmail());
		user.setFamilyName(userDTO.getFamilyName());
		user.setGivenName(userDTO.getGivenName());
		user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
		user.setPaymentMode(userDTO.getPaymentMode());
		user.setPhone(userDTO.getPhone());
		user.setProvince(userDTO.getProvince());
		user.setRegistrationDate(registrationDateTime);
		user.setRole(userDTO.getRole().getRoleAsString());
		user.setSex(userDTO.getSex());
		user.setUserName(userDTO.getUserName());
		user.setZipcode(userDTO.getZipcode());

		return user;

	}

}
