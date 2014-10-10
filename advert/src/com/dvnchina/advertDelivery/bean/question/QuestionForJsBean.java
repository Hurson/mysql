package com.dvnchina.advertDelivery.bean.question;

import java.util.List;

public class QuestionForJsBean {
	private String questionDiv;
	private String questionId;
	private int optionNum;
	private List<OptionForJsBean> option;

	public String getQuestionDiv() {
		return questionDiv;
	}

	public void setQuestionDiv(String questionDiv) {
		this.questionDiv = questionDiv;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public int getOptionNum() {
		return optionNum;
	}

	public void setOptionNum(int optionNum) {
		this.optionNum = optionNum;
	}

	public List<OptionForJsBean> getOption() {
		return option;
	}

	public void setOption(List<OptionForJsBean> option) {
		this.option = option;
	}

	

}
