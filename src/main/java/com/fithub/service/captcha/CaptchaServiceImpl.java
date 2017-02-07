package com.fithub.service.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fithub.domain.RecaptchaResponse;

@Service
public class CaptchaServiceImpl implements CaptchaService {

	private final RestTemplate restTemplate;
	private final String recaptchaServiceEndpoint;
	private final String recaptchaServiceKey;
	private final String recaptchaServiceTestKey;
	private final Environment environment;

	@Autowired
	public CaptchaServiceImpl(RestTemplate restTemplate,
			@Value("${recaptcha.captchaServiceEndpoint}") String recaptchaServiceEndpoint,
			@Value("${recaptcha.apiKey}") String recaptchaServiceKey, Environment environment,
			@Value("${recaptcha.testApiKey}") String recaptchaServiceTestKey) {
		this.recaptchaServiceEndpoint = recaptchaServiceEndpoint;
		this.restTemplate = restTemplate;
		this.environment = environment;
		this.recaptchaServiceKey = recaptchaServiceKey;
		this.recaptchaServiceTestKey = recaptchaServiceTestKey;

	}

	@Override
	public boolean isCaptchaResponseValid(String response) {
		RecaptchaResponse recaptchaResponse;
		try {
			recaptchaResponse = restTemplate.postForEntity(recaptchaServiceEndpoint,
					createRequestBody(response, recaptchaServiceKey), RecaptchaResponse.class).getBody();
		} catch (RestClientException e) {
			throw new RestClientException("Issues validating human user through Google Recaptcha service");
		}
		return recaptchaResponse.isSuccess();
	}

	/**
	 * Method creates request for google recaptcha rest api
	 * 
	 * @param response
	 *            User's response to repatcha form field
	 * @param recaptchaServiceKey
	 *            domain's api key
	 * @return requestMap that is sent as a HTTP Post request to google
	 *         recaptcha api
	 */
	private MultiValueMap<String, String> createRequestBody(String response, String recaptchaServiceKey) {
		MultiValueMap<String, String> recaptchaRequestMap = new LinkedMultiValueMap<String, String>();
		recaptchaRequestMap.add("response", response);

		// Add non-testing profile recaptcha key for user validation
		if (environment.getProperty("spring.profiles.active") != null
				&& !environment.getProperty("spring.profiles.active").contains("testing"))
			recaptchaRequestMap.add("secret", recaptchaServiceKey);
		else
			recaptchaRequestMap.add("secret", recaptchaServiceTestKey);

		return recaptchaRequestMap;
	}

}
