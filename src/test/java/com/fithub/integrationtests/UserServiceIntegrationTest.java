/**
 * 
 */
package com.fithub.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fithub.domain.User;
import com.fithub.domain.UserDTO;
import com.fithub.domain.UserRole;
import com.fithub.service.time.TimeHelperService;
import com.fithub.service.user.UserService;

/**
 * Class for testing User Service class integration with the database
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("integration_testing")
@Transactional
@SqlGroup({
		@Sql(scripts = "/integration_test_scripts/user_service-test-data-creation.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "/integration_test_scripts/user_service-test-data-deletion.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) })
public class UserServiceIntegrationTest {

	@Autowired
	private UserService userService;
	@Autowired
	private TimeHelperService timeHelperService;

	@Test
	public void userServiceCreatesUserIfTheUserNameNotInDatabase() {
		assertDatabaseStateConsistencyBeforetest();

		String expectedUserName = "testUserName";
		UserDTO userDTO = prepareUserDTO(expectedUserName);
		User user = userService.createUser(userDTO);
		assertEquals("User registration failure", expectedUserName, user.getUserName());

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void userServiceDoesNotCreateUserAndThrowsDataIntegrityViolationExceptionIfTheUserNameAlreadyInDatabase() {
		assertDatabaseStateConsistencyBeforetest();

		String expectedUserName = "mohitshsh";
		UserDTO userDTO = prepareUserDTO(expectedUserName);
		userService.createUser(userDTO);
		fail("DataIntegrityViolationException expected");

	}

	@Test
	public void userServiceGetsUserByUserIdIfIdExistsInDatabase() {
		assertDatabaseStateConsistencyBeforetest();

		int userId = 1;
		User user = userService.getUserById(userId);
		assertNotNull(String.format("UserId=%d not found", userId), user);
	}

	@Test(expected = NoSuchElementException.class)
	public void userServiceThrowsNoSuchElementFoundExceptionIfUserIdNotInDatabase() {
		assertDatabaseStateConsistencyBeforetest();

		int userId = 1001;
		userService.getUserById(userId);
		fail("NoSuchElementException expected");
	}

	@Test
	public void userServiceGetsUserByUserNameIfUserNameExistsInDatabase() {
		assertDatabaseStateConsistencyBeforetest();

		String userName = "mohitshsh";
		User user = userService.getUserByUsername(userName);
		assertNotNull(String.format("UserName=%s not found", userName), user);
	}

	@Test
	public void userServiceThrowsUserByUserNameIfUserNameExistsInDatabase() {
		assertDatabaseStateConsistencyBeforetest();

		String userName = "mohitshsh";
		User user = userService.getUserByUsername(userName);
		assertNotNull(String.format("UserName=%s not found", userName), user);
	}

	@Test
	public void userServiceMarksUserAsDeletedWhenUserNameExistsInDatabase() {
		assertDatabaseStateConsistencyBeforetest();

		String userNameOfUserBeingDeleted = "mohitshsh";
		boolean actualIsUserDeleted = userService.deleteUser(userNameOfUserBeingDeleted);
		boolean expectedUserDeleted = true;
		assertEquals(String.format("User with userName=%s not deleted as it doesnt exist in database",
				userNameOfUserBeingDeleted), expectedUserDeleted, actualIsUserDeleted);
	}

	@Test
	public void userServiceDoesNotMarkUserAsDeletedWhenUserNameDoesNotExistInDatabase() {
		assertDatabaseStateConsistencyBeforetest();

		String userNameOfUserBeingDeleted = "testUserToBeDeleted";
		boolean actualIsUserDeleted = userService.deleteUser(userNameOfUserBeingDeleted);
		boolean expectedIsUserDeleted = false;
		assertEquals(String.format("User with userName=%s deleted", userNameOfUserBeingDeleted), expectedIsUserDeleted,
				actualIsUserDeleted);
	}

	@Test
	public void userServiceChangesUserRoleFromCurrentRoleToSwitchedRoleWhenUserExistsInDatabase() {
		assertDatabaseStateConsistencyBeforetest();

		String expectedChangedUserRole = UserRole.ADMIN.toString();
		String userName = "mohitshsh";
		UserDTO userDTO = prepareUserDTO(userName);

		User user = userService.changeUserRole(userDTO);
		assertEquals(String.format("User with userName=%s role is unchanged", userName), expectedChangedUserRole,
				user.getRole());
	}

	@Test(expected = IllegalArgumentException.class)
	public void userServiceDoesNotChangeUserRoleFromCurrentRoleToSwitchedRoleWhenUserDoesNotExistInDatabase() {
		assertDatabaseStateConsistencyBeforetest();

		String userName = "testUserNameRoleToBeChanged";
		UserDTO userDTO = prepareUserDTO(userName);

		userService.changeUserRole(userDTO);
		fail("IllegalArgumentException expected");
	}

	@Test
	public void userServiceGetsAllUsersFromDatabase() {
		assertDatabaseStateConsistencyBeforetest();

		List<User> userList = userService.getAllUsers();
		int expectedUserListSize = 2;

		assertEquals("Problems retrieving the list of all users", expectedUserListSize, userList.size());
	}

	protected UserDTO prepareUserDTO(String userName) {

		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(userName);
		userDTO.setUserNameBeforeEdit(userName);
		userDTO.setPassword("password");
		userDTO.setGivenName("testGivenName");
		userDTO.setFamilyName("testFamilyName");
		userDTO.setDateOfBirth(new Date());
		userDTO.setRegistrationDate(timeHelperService.getCurrentTimeStamp());
		userDTO.setSecurityQuestion("testSecuriyQuestion");
		userDTO.setSecurityQuestionAnswer("testSecurityQuestionAnswer");
		userDTO.setEmail("test@test.com");

		return userDTO;

	}

	protected void assertDatabaseStateConsistencyBeforetest() {
		assertEquals("Database in an inconsistent state before test", 2, userService.countNumberOfUsersInSystem());
	}

}
