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
	User getUserById(Long userId);

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
	 * Method deletes a user record from the database based on the provided
	 * userName
	 * 
	 * @param userName
	 * @return boolean
	 */
	boolean deleteUserByUsername(String userName);

	/**
	 * Method checks the existing role of the user and switches it to the other
	 * alternative role
	 * 
	 * @param user
	 * @param userName
	 * @return boolean
	 */
	boolean changeRole(User user, String userName);

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

}
