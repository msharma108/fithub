package com.fithub.service.captcha;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fithub.domain.RecaptchaResponse;

@Service
public class CaptchaServiceImpl implements CaptchaService {

	private final RestTemplate restTemplate;
	private final String recaptchaServiceEndpoint;
	private final String recaptchaServiceKey;
	
	@Autowired
	public CaptchaServiceImpl(RestTemplate restTemplate,@Value("${recaptcha.captchaServiceEndpoint}") String recaptchaServiceEndpoint,@Value("${recaptcha.apiKey}") String recaptchaKey ){
		this.recaptchaServiceEndpoint =recaptchaServiceEndpoint;
		this.restTemplate =restTemplate;
		this.recaptchaServiceKey= recaptchaKey;
		
	}
	
	
	@Override
	public boolean isCaptchaResponseValid(String response) {
		RecaptchaResponse recaptchaResponse;
		try{
		recaptchaResponse= restTemplate.postForEntity(recaptchaServiceEndpoint, createRequestBody(response,recaptchaServiceKey), RecaptchaResponse.class).getBody();
		}
		catch (RestClientException e) {
			throw new RestClientException("Issues validating human user through Google Recaptcha service");
		}
		 return recaptchaResponse.isSuccess();
	}
	
	/** Method creates request for google recaptcha rest api
	 * @param response User's response to repatcha form field
	 * @param recaptchaServiceKey domain's api key
	 * @return requestMap that is sent as a HTTP Post request to google recaptcha api
	 */
	private Map<String,String> createRequestBody(String response,String recaptchaServiceKey){
		Map<String,String> recaptchaRequestMap = new HashMap<String,String>();
		recaptchaRequestMap.put("secret", recaptchaServiceKey);
		recaptchaRequestMap.put("response", response);
		return recaptchaRequestMap;	
	}

}
