package com.fithub.service.user;

import java.util.Date;

import org.springframework.security.core.Authentication;

import com.fithub.domain.CustomUser;
import com.fithub.domain.User;
import com.fithub.domain.UserDTO;

public interface UserTasksHelperService {

	/**
	 * Method prepares a user object based on the properties retrieved from a
	 * user transfer object
	 * 
	 * @param user
	 *            Reference to a newly created user object
	 * @param userDTO
	 *            User Data Transfer Object populated with properties retrieved
	 *            from the View
	 * @return User User object to be stored in the database
	 */
	User createUserFromUserDTO(User user, UserDTO userDTO);

	/**
	 * Method that checks if the passed in user has the necessary access for
	 * subsequent actions
	 * 
	 * @param customUser
	 *            Logged in user
	 * @param userName
	 *            userName of the user which needs to be accessed
	 * @return true if canAccessUser else false
	 */
	boolean canAccessUser(CustomUser customUser, String userName);

	/**
	 * Method formats provided date in "yy/mm/dd" format
	 * 
	 * @param dateToBeFormatted
	 * @return Formatted date
	 */
	Date dateFormatter(String dateToBeFormatted);

	/**
	 * Method returns the logged in user's userName
	 * 
	 * @param authentication
	 *            Spring Security object encapsulating the logged in user
	 * @return userName Logged in user's userName
	 */
	String getLoggedInUserName(Authentication authentication);

	/**
	 * Method returns the logged in user's authority
	 * 
	 * @param authentication
	 *            Spring Security object encapsulating the logged in user
	 * @return role Role of the logged in user
	 */
	String getLoggedInUserUserRole(Authentication authentication);

}
