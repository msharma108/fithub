package com.fithub.service.user;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fithub.domain.CustomUser;
import com.fithub.domain.User;
import com.fithub.domain.UserDTO;
import com.fithub.domain.UserRole;
import com.fithub.service.time.TimeHelperService;

@Service
public class UserTasksHelperServiceImpl implements UserTasksHelperService {

	private static final Logger LOG = LoggerFactory.getLogger(UserTasksHelperServiceImpl.class);
	private final TimeHelperService timeHelperService;

	@Autowired
	public UserTasksHelperServiceImpl(TimeHelperService timeHelperService) {
		this.timeHelperService = timeHelperService;
	}

	@Override
	public User createUserFromUserDTO(User user, UserDTO userDTO) {
		LOG.debug("Preparing user={} from user transfer object", userDTO.getUserName());

		if (user == null)
			throw new NoSuchElementException((String.format("Username=%s not found", userDTO.getUserName())));
		user.setAddress(userDTO.getAddress());
		user.setIsUserDeleted(userDTO.getIsUserDeleted());
		user.setCity(userDTO.getCity());
		user.setCountry(userDTO.getCountry());
		user.setDateOfBirth((userDTO.getDateOfBirth()));
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
		user.setSecurityQuestion(userDTO.getSecurityQuestion());
		user.setSecurityQuestionAnswer(userDTO.getSecurityQuestionAnswer());

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
	public UserDTO populateUserDTOFromUser(User user) {
		LOG.debug("Preparing userDTO={} from user object", user.getUserName());

		UserDTO userDTO = new UserDTO();
		userDTO.setAddress(user.getAddress());
		userDTO.setCity(user.getCity());
		userDTO.setCountry(user.getCountry());
		userDTO.setDateOfBirth(user.getDateOfBirth());
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
		userDTO.setUserNameBeforeEdit(user.getUserName());
		userDTO.setZipcode(user.getZipcode());
		userDTO.setRegistrationDate(user.getRegistrationDate());
		userDTO.setSecurityQuestion(user.getSecurityQuestion());
		userDTO.setSecurityQuestionAnswer(user.getSecurityQuestionAnswer());

		return userDTO;

	}

	@Override
	public boolean isLoggedInUserAdmin(Authentication authentication) {
		CustomUser loggedInUser = (CustomUser) authentication.getPrincipal();
		if (loggedInUser.getRole().equals(UserRole.ADMIN.getRoleAsString()))

			return true;
		else
			return false;
	}

	@Override
	public UserDTO destroyUserDataForDeletion(UserDTO userDTO) {

		final String userDeleted = "User_Deleted";
		final String userDeletedDummyDate = "1900/10/11";
		final String userDeletedDummy = "UNDISCLOSED";

		userDTO.setIsUserDeleted(true);
		userDTO.setAddress(userDeleted);
		userDTO.setCity(userDeleted);
		userDTO.setCountry(userDeleted);
		userDTO.setDateOfBirth(timeHelperService.dateFormatter(userDeletedDummyDate));
		userDTO.setEmail(userDeleted);
		userDTO.setFamilyName(userDeleted);
		userDTO.setGivenName(userDeleted);
		userDTO.setPassword(userDeleted);
		userDTO.setRepeatPassword(userDeleted);
		userDTO.setPaymentMode(userDeletedDummy);
		userDTO.setPhone(userDeleted);
		userDTO.setProvince(userDeleted);
		userDTO.setSex(userDeletedDummy);
		userDTO.setUserName(userDTO.getUserName().concat(userDeleted));
		userDTO.setZipcode(userDeleted);
		userDTO.setRegistrationDate(timeHelperService.dateFormatter(userDeletedDummyDate));
		userDTO.setSecurityQuestion(userDeleted);
		userDTO.setSecurityQuestionAnswer(userDeleted);

		return userDTO;
	}
}
