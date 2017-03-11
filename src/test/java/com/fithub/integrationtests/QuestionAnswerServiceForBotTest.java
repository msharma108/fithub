package com.fithub.integrationtests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fithub.domain.BotQuestionAnswerResponse;

/**
 * Class tests the availability and sample response for Microsoft Cognitive
 * Service QnA Maker
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class QuestionAnswerServiceForBotTest {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${qna.questionAnswerServiceForBotEndpoint}")
	private String questionAnswerServiceForBotEndpoint;

	@Test
	public void questionAnswerServiceForBotProvidesAnswerToQuestion() {

		// Arrange
		// Data setup
		BotQuestionAnswerResponse botQuestionAnswerResponse;
		String apiKey = System.getenv("QNA_MAKER_API_KEY");
		String questionForBot = "Hello";
		String expectedBotAnswer = "hello!";

		// Request Header setup
		HttpHeaders requestHeader = new HttpHeaders();
		requestHeader.add("Ocp-Apim-Subscription-Key", apiKey);
		requestHeader.add("Accept", "application/json");

		// Request Body setup
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("question", questionForBot);

		// Act
		// Post request to Rest API
		HttpEntity<?> entity = new HttpEntity<Object>(requestBody, requestHeader);
		botQuestionAnswerResponse = restTemplate
				.exchange(questionAnswerServiceForBotEndpoint, HttpMethod.POST, entity, BotQuestionAnswerResponse.class)
				.getBody();

		// Assert
		assertEquals("", expectedBotAnswer, botQuestionAnswerResponse.getBotAnswer());
	}

}
