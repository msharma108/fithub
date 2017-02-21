package com.fithub.service.user;

import java.util.List;

import com.fithub.domain.User;
import com.fithub.domain.UserDTO;

/**
 * An interface for the services pertinent to user domain objects
 *
 */
public interface UserService {

	/**
	 * Method gets a user based on the automatically generated userID Primary
	 * Key
	 * 
	 * @param userId
	 * @return User
	 */
	User getUserById(Integer userId);

	/**
	 * Method gets a user based on the provided userName
	 * 
	 * @param userName
	 * @return User
	 */
	User getUserByUsername(String userName);

	/**
	 * Method creates a user based on the information filled on registration
	 * form
	 * 
	 * @param userDTO
	 * @return User
	 */
	User createUser(UserDTO userDTO);

	/**
	 * Method simulates a user record deletion in the database based on the
	 * provided userName. The user record is kept for audit purposes but his
	 * information is overwritten with dummy data
	 * 
	 * @param userNameOfUserBeingDeleted
	 *            userName of the user to be marked as deleted
	 * @return boolean returns true if the user is marked as deleted
	 */
	boolean deleteUser(String userNameOfUserBeingDeleted);

	/**
	 * Method checks the existing role of the user and switches it to the other
	 * alternative role
	 * 
	 * @param userDTO
	 * @return User The user object with the changed role
	 */
	User changeUserRole(UserDTO userDTO);

	/**
	 * Method gets a list of all the users in the database
	 * 
	 * @return List of all the users ordered by the userName
	 */
	List<User> getAllUsers();

	/**
	 * Method updates a user based on the information filled on user update form
	 * 
	 * @param userDTO
	 * @return updated user
	 */
	User updateUserProfile(UserDTO userDTO);

	/**
	 * Method resets the password of the provided user
	 * 
	 * @param user
	 *            User whose password is being reset
	 * @param resetPassword
	 *            : The new random string sent to user as password
	 * @return boolean true if the password reset was successful
	 * 
	 */
	boolean resetPassword(User user, String resetPassword);

	/**
	 * Method counts the number of users in the system\database
	 * 
	 * @return the number of users in the system
	 */
	long countNumberOfUsersInDatabase();

	/**
	 * Method gets the list of users matching the provided user search string
	 * 
	 * @param userSearchString
	 *            The entered search string for user
	 * @return The list of users containing the supplied search string
	 */
	List<User> getUsersWithNameContainingUserSearchString(String userSearchString);

}
