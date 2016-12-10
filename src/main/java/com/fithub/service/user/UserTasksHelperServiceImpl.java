package com.fithub.service.user;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fithub.domain.CustomUser;
import com.fithub.domain.User;
import com.fithub.domain.UserDTO;
import com.fithub.domain.UserRole;

@Service
public class UserTasksHelperServiceImpl implements UserTasksHelperService {

	private static final Logger LOG = LoggerFactory.getLogger(UserTasksHelperServiceImpl.class);

	@Override
	public User createUserFromUserDTO(User user, UserDTO userDTO) {
		LOG.debug("Preparing user={} from user transfer object", userDTO.getUserName());

		Timestamp registrationDateTime = new Timestamp(new Date().getTime());

		user.setAddress(userDTO.getAddress());
		user.setCity(userDTO.getCity());
		user.setCountry(userDTO.getCountry());
		user.setDateOfBirth(dateFormatter(userDTO.getDateOfBirth()));
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

	@Override
	public boolean canAccessUser(CustomUser customUser, String userName) {
		LOG.debug("Checking if the logged in user={} has access to={}", customUser.getUserName(), userName);

		if (customUser != null && (customUser.getUsername().equals(userName)
				|| (customUser.getRole().equals(UserRole.ADMIN.getRoleAsString())))) {
			LOG.debug("User={} can access user={}", customUser.getUsername(), userName);
			return true;
		}

		return false;
	}

	/**
	 * Method formats provided date in yy/mm/dd format
	 * 
	 * @param dateToBeFormatted
	 * @return date
	 */
	public Date dateFormatter(String dateToBeFormatted) {
		// Reference :
		// http://www.mkyong.com/java/java-date-and-calendar-examples/
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");

		try {
			date = dateFormat.parse(dateToBeFormatted);
		} catch (ParseException exception) {
			LOG.debug("Error parsing date={}", dateToBeFormatted);
		}
		return date;
	}

}
