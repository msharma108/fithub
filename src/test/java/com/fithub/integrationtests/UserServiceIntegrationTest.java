/**
 * 
 */
package com.fithub.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import com.fithub.domain.PasswordRetrievalDTO;
import com.fithub.domain.User;
import com.fithub.domain.UserDTO;
import com.fithub.domain.UserRole;
import com.fithub.service.time.TimeHelperService;
import com.fithub.service.user.UserService;

/**
 * Class for testing User Service class integration with the database
 *
 */

@SqlGroup({
		@Sql(scripts = "/integration_test_scripts/user_service-test-data-creation.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
		@Sql(scripts = "/integration_test_scripts/user_service-test-data-deletion.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) })
public class UserServiceIntegrationTest extends AbstractFithubApplicationIntegrationTest {

	@Autowired
	private UserService userService;
	@Autowired
	private TimeHelperService timeHelperService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void userServiceCreatesUserIfTheUserNameNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String expectedUserName = "testUserName";
		UserDTO userDTO = prepareUserDTO(expectedUserName);
		User user = userService.createUser(userDTO);
		assertEquals("User registration failure", expectedUserName, user.getUserName());

	}

	@Test
	public void userServiceDoesNotCreateUserAndThrowsDataIntegrityViolationExceptionIfTheUserNameAlreadyInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String expectedUserName = "mohitshsh";
		UserDTO userDTO = prepareUserDTO(expectedUserName);
		expectedException.expect(DataIntegrityViolationException.class);
		userService.createUser(userDTO);
		fail("DataIntegrityViolationException expected");

	}

	@Test
	public void userServiceGetsUserByUserIdIfIdExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		int userId = 1;
		User user = userService.getUserById(userId);
		assertNotNull(String.format("UserId=%d not found", userId), user);
	}

	@Test
	public void userServiceThrowsNoSuchElementExceptionOnGetUserBasedOnIdIfUserIdNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		int userId = 1001;
		expectedException.expect(NoSuchElementException.class);
		expectedException.expectMessage(String.format("UserId=%s not found", userId));
		userService.getUserById(userId);
		fail("NoSuchElementException expected");
	}

	@Test
	public void userServiceGetsUserByUserNameIfUserNameExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userName = "mohitshsh";
		User user = userService.getUserByUsername(userName);
		assertNotNull(String.format("UserName=%s not found", userName), user);
	}

	@Test
	public void userServiceReturnsNullIfGetUserByUserNameCalledWithUserNameNotExistentInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userName = "testUserNameNotInDatabase";
		User user = userService.getUserByUsername(userName);
		assertNull("Null userName provided", user);
	}

	@Test
	public void userServiceMarksUserAsDeletedWhenUserNameExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userNameOfUserBeingDeleted = "mohitshsh";
		boolean actualIsUserDeleted = userService.deleteUser(userNameOfUserBeingDeleted);
		boolean expectedUserDeleted = true;
		assertEquals(String.format("User with userName=%s not deleted as it doesnt exist in database",
				userNameOfUserBeingDeleted), expectedUserDeleted, actualIsUserDeleted);
	}

	@Test
	public void userServiceDoesNotMarkUserAsDeletedWhenUserNameDoesNotExistInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userNameOfUserBeingDeleted = "testUserToBeDeleted";
		boolean actualIsUserDeleted = userService.deleteUser(userNameOfUserBeingDeleted);
		boolean expectedIsUserDeleted = false;
		assertEquals(String.format("User with userName=%s deleted", userNameOfUserBeingDeleted), expectedIsUserDeleted,
				actualIsUserDeleted);
	}

	@Test
	public void userServiceChangesUserRoleFromCurrentRoleToSwitchedRoleWhenUserExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String expectedChangedUserRole = UserRole.ADMIN.toString();
		String userName = "mohitshsh";
		UserDTO userDTO = prepareUserDTO(userName);

		User user = userService.changeUserRole(userDTO);
		assertEquals(String.format("User with userName=%s role is unchanged", userName), expectedChangedUserRole,
				user.getRole());
	}

	@Test
	public void userServiceDoesNotChangeUserRoleFromCurrentRoleToSwitchedRoleWhenUserDoesNotExistInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userName = "testUserNameRoleToBeChanged";
		UserDTO userDTO = prepareUserDTO(userName);

		expectedException.expect(NoSuchElementException.class);
		expectedException.expectMessage(String.format("Username=%s not found", userDTO.getUserName()));
		userService.changeUserRole(userDTO);
		fail("NoSuchElementException expected");
	}

	@Test
	public void userServiceGetsAllUsersFromDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		List<User> userList = userService.getAllUsers();
		int expectedUserListSize = 2;

		assertEquals("Problems retrieving the list of all users", expectedUserListSize, userList.size());
	}

	@Test
	public void userServiceUpdatesUserProfileIfTheUserNameExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userName = "mohitshsh";

		User user = userService.getUserByUsername(userName);
		Date profileEditDatePrirToEdit = user.getProfileEditDate();

		UserDTO userDTO = prepareUserDTO(userName);
		Date profileEditDateAfterEdit = userService.updateUserProfile(userDTO).getProfileEditDate();

		assertNotEquals("Problems with editing user profile", profileEditDatePrirToEdit, profileEditDateAfterEdit);

	}

	@Test
	public void userServiceDoesNotUpdateUserProfileIfTheUserNameNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userName = "testUserProfileUpdate";

		UserDTO userDTO = prepareUserDTO(userName);
		expectedException.expect(NoSuchElementException.class);
		expectedException.expectMessage(String.format("Username=%s not found", userDTO.getUserName()));
		userService.updateUserProfile(userDTO);
		fail("NoSuchElementException expected");
	}

	@Test
	public void userServiceResetsPasswordIfUserExistsInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userName = "mohitshsh";
		boolean expectedPasswordResetResult = true;
		PasswordRetrievalDTO passwordRetrievalDTO = new PasswordRetrievalDTO();
		passwordRetrievalDTO.setSecurityAnswer("Delhi");
		passwordRetrievalDTO.setZip("K1G3R4");
		User user = userService.getUserByUsername(userName);
		boolean actualPasswordResetResult = userService.resetPassword(user, passwordRetrievalDTO);

		assertEquals("Password reset not successful", expectedPasswordResetResult, actualPasswordResetResult);

	}

	@Test
	public void userServiceDoesNotResetPasswordIfUserNotInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userName = "userNameNotInDatabase";
		PasswordRetrievalDTO passwordRetrievalDTO = new PasswordRetrievalDTO();
		User user = userService.getUserByUsername(userName);
		expectedException.expect(NoSuchElementException.class);
		expectedException.expectMessage("User not found");
		userService.resetPassword(user, passwordRetrievalDTO);

		fail("NoSuchElementException expected");

	}

	@Test
	public void userServiceCountsNumberOfUsersInDatabase() {

		assertDatabaseStateConsistencyBeforeTest();
	}

	@Test
	public void userServiceGetsUsersWithNameContainingUserSearchStringIfUsersWithMatchingStringExistInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userSearchString = "mohit";
		String expectedSearchResult = "mohitshsh";
		List<User> searchResultList = userService.getUsersWithNameContainingUserSearchString(userSearchString);
		String actualSearchResult = searchResultList.get(0).getUserName();

		assertEquals("User matching search string not found", expectedSearchResult, actualSearchResult);

	}

	@Test
	public void userServiceThrowsNoSuchElementExceptionWhenSearchStringForGetUsersWithNameContainingUserSearchStringDoesNotMatchUsersInDatabase() {
		assertDatabaseStateConsistencyBeforeTest();

		String userSearchString = "userNotInDatabase";
		expectedException.expect(NoSuchElementException.class);
		expectedException
				.expectMessage(String.format("Users matching the searchString=%s not found", userSearchString));
		userService.getUsersWithNameContainingUserSearchString(userSearchString);
		fail("NoSuchElementException expected");

	}

	private UserDTO prepareUserDTO(String userName) {

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

	private void assertDatabaseStateConsistencyBeforeTest() {
		assertEquals("Database in an inconsistent state before test", 2, userService.countNumberOfUsersInDatabase());
	}

}
