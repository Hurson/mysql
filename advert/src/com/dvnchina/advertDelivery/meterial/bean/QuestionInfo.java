package com.dvnchina.advertDelivery.meterial.bean;

import java.util.List;

import com.dvnchina.advertDelivery.model.Question;



public class QuestionInfo {

	private static final long serialVersionUID = -4879127837142232709L;

	private String question;
	private List<Question> answerList;

	
	public QuestionInfo(){}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public List<Question> getAnswerList() {
		return answerList;
	}


	public void setAnswerList(List<Question> answerList) {
		this.answerList = answerList;
	}
	
	
	
	
}
