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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fithub.domain.User;
import com.fithub.domain.UserDTO;
import com.fithub.domain.UserRole;
import com.fithub.repository.user.UserRepository;
import com.fithub.service.time.TimeHelperService;

/**
 * Implementation of UserService Interface
 *
 */

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	private final UserRepository userRepository;
	private final UserTasksHelperService userTasksHelperService;
	private final TimeHelperService timeHelperService;

	// Constructor dependency Injection for UserRepository
	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserTasksHelperService userTasksHelperService,
			TimeHelperService timeHelperService) {
		this.userRepository = userRepository;
		this.userTasksHelperService = userTasksHelperService;
		this.timeHelperService = timeHelperService;
	}

	@Override
	public User getUserById(Integer userId) {
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
		user.setRegistrationDate(timeHelperService.getCurrentTimeStamp());
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public boolean deleteUser(String userNameOfUserBeingDeleted) {

		boolean isUserDeleted = true;
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(userNameOfUserBeingDeleted);
		LOG.debug("Attempting to delete user having userName={}", userNameOfUserBeingDeleted);
		try {
			User user = getUserByUsername(userNameOfUserBeingDeleted);
			userDTO = userTasksHelperService.destroyUserDataForDeletion(userDTO);
			user = userTasksHelperService.createUserFromUserDTO(user, userDTO);
			user.setProfileEditDate(timeHelperService.getCurrentTimeStamp());
			user.setProfileEditedByUser(userDTO.getLoggedInUserName());
			user = userRepository.save(user);
			return isUserDeleted;
		} catch (NoSuchElementException exception) {
			LOG.debug("User with userName={} can't be deleted as they dont exist in the database");
			isUserDeleted = false;
			return isUserDeleted;
		}
	}

	@Override
	public User changeUserRole(UserDTO userDTO) throws NoSuchElementException {

		User user = new User();
		if (userDTO.getRole() != null) {
			// Checking for users current role and switching the role
			if (userDTO.getRole().equals(UserRole.ADMIN)) {
				userDTO.setRole(UserRole.CUSTOMER);
			} else {
				userDTO.setRole(UserRole.ADMIN);
			}
			user = updateUserProfile(userDTO);
			LOG.debug("User with userName={} now has the role={}", userDTO.getUserName(), userDTO.getRole());
		}
		return user;
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
		// Get user using DTO to intimate JPA about update operation as a part
		// of the transaction
		user = getUserByUsername(userDTO.getUserNameBeforeEdit());
		user = userTasksHelperService.createUserFromUserDTO(user, userDTO);
		user.setProfileEditDate(timeHelperService.getCurrentTimeStamp());
		user.setProfileEditedByUser(userDTO.getLoggedInUserName());
		return userRepository.save(user);
	}

	@Override
	public boolean resetPassword(User user, String resetPassword) {
		LOG.debug("Attempting to reset user password of user={} by user={}", user.getUserName(), user.getUserName());

		user.setPassword((new BCryptPasswordEncoder().encode(resetPassword)));
		user.setProfileEditDate(timeHelperService.getCurrentTimeStamp());
		user.setProfileEditedByUser(user.getUserName());
		userRepository.save(user);

		return true;

	}

	@Override
	public long countNumberOfUsersInDatabase() {
		return userRepository.count();
	}
}
