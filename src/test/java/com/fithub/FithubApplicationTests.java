package com.fithub;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("integration_testing")
public class FithubApplicationTests {

	@Autowired
	Environment env;

	@Test
	public void contextLoads() {

		String databaseConnectionURL = "jdbc:mysql://localhost:3306/fithub_integ_testing";
		assertEquals("Database configuration mismatch", databaseConnectionURL,
				env.getProperty("spring.datasource.url"));

	}

}
