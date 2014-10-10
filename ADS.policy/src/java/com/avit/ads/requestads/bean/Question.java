package com.avit.ads.requestads.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 调查问卷问题实体
 * */
@Entity
@Table(name = "AD_QUESTIONNAIRE_INFO")
public class Question {

	/**
	 * 主键
	 * */
	@Id
	@Column(name = "ID")
	private Integer id;

	/**
	 * 问题题目
	 * */
	@Column(name = "QUESTION")
	private String question;

	/**
	 * 问题选项
	 * */
	@Column(name = "OPTIONS")
	private String options;

	/**
	 * 问题索引
	 * */
	@Column(name = "QUESTIONNAIRE_INDEX")
	private int questionnaireIndex;

	/**
	 * 调查问卷对象
	 * */
	@Column(name = "QUESTIONNAIRE_ID")
	private Integer questionnaireId;
	
	/** 外键*/
	@Column(name = "PARENT_ID")
	private Long parentId;
	
	public Question(){}
	
	public Question(String question){
		this.question=question;
	}

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
