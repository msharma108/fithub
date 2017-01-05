/**
 * 
 */
package com.fithub.integrationtests;

import static org.junit.Assert.assertEquals;

import java.util.Date;

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

		assertEquals("Database in an inconsistent state before test", 1, userService.countNumberOfUsersInSystem());

		String expectedUserName = "testUserName";
		UserDTO userDTO = prepareUserDTO(expectedUserName);
		User user = userService.createUser(userDTO);

		assertEquals("User registration failure", expectedUserName, user.getUserName());

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void userServiceDoesNotCreateUserIfTheUserNameAlreadyInDatabase() {

		assertEquals("Database in an inconsistent state before test", 1, userService.countNumberOfUsersInSystem());

		String expectedUserName = "mohitshsh";
		UserDTO userDTO = prepareUserDTO(expectedUserName);
		userService.createUser(userDTO);

	}

	protected UserDTO prepareUserDTO(String userName) {

		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(userName);
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

}
