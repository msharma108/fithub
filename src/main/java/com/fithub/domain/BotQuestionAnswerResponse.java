package com.fithub.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to model Microsoft Cognitive Services QnA maker service's response
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BotQuestionAnswerResponse {

	@JsonProperty("answer")
	private String botAnswer;

	@JsonProperty("score")
	private BigDecimal answerRelevanceScore;

	public String getBotAnswer() {
		return botAnswer;
	}

	public BigDecimal getAnswerRelevanceScore() {
		return answerRelevanceScore;
	}

	public void setBotAnswer(String botAnswer) {
		this.botAnswer = botAnswer;
	}

	public void setAnswerRelevanceScore(BigDecimal answerRelevanceScore) {
		this.answerRelevanceScore = answerRelevanceScore;
	}

}
