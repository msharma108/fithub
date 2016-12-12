/**
 * 
 */
package com.fithub.service.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fithub.domain.User;
import com.fithub.domain.UserDTO;
import com.fithub.domain.UserRole;
import com.fithub.repository.user.UserRepository;

/**
 * Implementation of UserService Interface
 *
 */

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	private final UserRepository userRepository;
	private final UserTasksHelperService userTasksHelperService;

	// Constructor dependency Injection for UserRepository
	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserTasksHelperService userTasksHelperService) {
		this.userRepository = userRepository;
		this.userTasksHelperService = userTasksHelperService;
	}

	@Override
	public User getUserById(Long userId) throws IllegalArgumentException {
		LOG.debug("Retreive user having userId={}", userId);
		User user = userRepository.findOne(userId);
		if (user != null)
			return user;
		else
			throw new NoSuchElementException(String.format("UserId=%d not found", userId));
	}

	@Override
	public User getUserByUsername(String userName) {
		LOG.debug("Retreive user having userName={}", userName);
		User user = userRepository.findOneByUserName(userName);
		return user;

	}

	@Override
	public User createUser(UserDTO userDTO) {

		LOG.debug("Saving user having userName={}", userDTO.getUserName());
		User user = new User();
		user = userTasksHelperService.createUserFromUserDTO(user, userDTO);
		user.setRegistrationDate(userTasksHelperService.getCurrentTimeStamp());
		return userRepository.save(user);
	}

	@Override
	public boolean deleteUserByUsername(String userName) {

		boolean isUserDeleted = true;
		LOG.debug("Attempting to delete user having userName={}", userName);
		try {
			userRepository.deleteByUserName(userName);
			return isUserDeleted;
		} catch (IllegalArgumentException exception) {
			LOG.debug("User with userName={} can't be deleted as they dont exist in the database", userName);
			isUserDeleted = false;
			return isUserDeleted;
		}
	}

	@Override
	public boolean changeRole(User user, String userName) throws NoSuchElementException {

		boolean isRoleChanged = false;

		if (user != null) {
			// Checking for users current role and switching the role
			if (user.getRole().equals(UserRole.ADMIN.getRoleAsString())) {
				user.setRole(UserRole.CUSTOMER.getRoleAsString());
			} else {
				user.setRole(UserRole.ADMIN.getRoleAsString());
			}
			userRepository.saveAndFlush(user);
			LOG.debug("User with userName={} now has the role={}", userName, user.getRole());
			isRoleChanged = true;
		}
		return isRoleChanged;
	}

	@Override
	public List<User> getAllUsers() {

		LOG.debug("Retrieving the list of all the users");
		return userRepository.findAll(new Sort("userName"));
	}

	@Override
	public User updateUserProfile(UserDTO userDTO) {

		LOG.debug("Attempting to edit user profile of user={} by user={}", userDTO.getUserName(),
				userDTO.getLoggedInUserName());
		User user = new User();
		user = userTasksHelperService.createUserFromUserDTO(user, userDTO);
		user.setProfileEditDate(userTasksHelperService.getCurrentTimeStamp());
		user.setProfileEditedByUser(userDTO.getLoggedInUserName());
		return userRepository.save(getUserByUsername(user.getUserName()));
	}

}
