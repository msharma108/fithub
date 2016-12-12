package com.fithub.service.user;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
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
		user.setRole(userDTO.getRole().getRoleAsString());
		user.setSex(userDTO.getSex());
		user.setUserName(userDTO.getUserName());
		user.setZipcode(userDTO.getZipcode());
		user.setRegistrationDate(userDTO.getRegistrationDate());

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

	@Override
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

	@Override
	public String getLoggedInUserName(Authentication authentication) {

		CustomUser loggedInUser = (CustomUser) authentication.getPrincipal();
		return loggedInUser.getUserName();
	}

	@Override
	public String getLoggedInUserUserRole(Authentication authentication) {
		CustomUser loggedInUser = (CustomUser) authentication.getPrincipal();
		return loggedInUser.getRole();
	}

	@Override
	public Timestamp getCurrentTimeStamp() {

		Timestamp timeStamp = new Timestamp(new Date().getTime());
		return timeStamp;
	}

	@Override
	public UserDTO populateUserDTOFromUser(User user) {
		LOG.debug("Preparing userDTO={} from user object", user.getUserName());

		UserDTO userDTO = new UserDTO();
		userDTO.setAddress(user.getAddress());
		userDTO.setCity(user.getCity());
		userDTO.setCountry(user.getCountry());
		userDTO.setDateOfBirth(user.getDateOfBirth().toString());
		userDTO.setEmail(user.getEmail());
		userDTO.setFamilyName(user.getFamilyName());
		userDTO.setGivenName(user.getGivenName());
		userDTO.setPassword(user.getPassword());
		userDTO.setRepeatPassword(user.getPassword());
		userDTO.setPaymentMode(user.getPaymentMode());
		userDTO.setPhone(user.getPhone());
		userDTO.setProvince(user.getProvince());
		userDTO.setRole(UserRole.valueOf(user.getRole()));
		userDTO.setSex(user.getSex());
		userDTO.setUserName(user.getUserName());
		userDTO.setZipcode(user.getZipcode());
		userDTO.setRegistrationDate(user.getRegistrationDate());

		return userDTO;

	}

}
