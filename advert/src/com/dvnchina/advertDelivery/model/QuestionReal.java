package com.dvnchina.advertDelivery.model;

/**
 * 调查问卷问题（审核后）实体
 * */
public class QuestionReal {

	/**
	 * 主键
	 * */
	private Integer id;

	/**
	 * 问题题目
	 * */
	private String question;

	/**
	 * 问题选项
	 * */
	private String options;

	/**
	 * 问题索引
	 * */
	private int questionnaireIndex;

	/**
	 * 调查问卷对象
	 * */
	private Integer questionnaireId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public int getQuestionnaireIndex() {
		return questionnaireIndex;
	}

	public void setQuestionnaireIndex(int questionnaireIndex) {
		this.questionnaireIndex = questionnaireIndex;
	}

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

}
