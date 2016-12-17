package com.fithub.service.user;

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

	/**
	 * Method populates a UserDTO object with the Domain User object in order to
	 * prevent exposing the domain entity to the presentation tier
	 * 
	 * @param user
	 *            User retrieved from the database
	 * @return userDTO UserDO presented to the JSP layer
	 */
	UserDTO populateUserDTOFromUser(User user);

	/**
	 * Method returns boolean true if the logged in user's role is Admin
	 * 
	 * @param authentication
	 *            Spring Security authenticatio object
	 * @return boolean true if the logged in user's role is Admin
	 */
	boolean isLoggedInUserAdmin(Authentication authentication);

}
