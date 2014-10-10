package com.avit.ads.requestads.bean.cache;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AD_QUESTIONNAIRE_DATA")
//@SequenceGenerator(name = "questionnaire_seq", sequenceName = "AD_QUESTIONNAIRE_SEQ", allocationSize = 25)
public class SurveyReport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 问卷的问题 */
	@Column(name = "QUESTIONS")
	private String questions;

	/** 问卷的答案 */
	@Column(name = "ANSWERS")
	private String answers;

	/** 问卷类型 */
	@Column(name = "SURVEY_TYPE")
	private String surveyType;

	/** 问卷主题 */
	@Column(name = "SURVEY_THEME")
	private String surveyTheme;

	/** 问卷ID */
	@Column(name = "SURVEY_ID")
	private String surveyId;

	/** 问卷填写时间*/
	@Column(name = "WRITE_TIME")
	private Date writeTime;

	/** 用户机顶盒唯一标识符*/
	@Column(name = "TVN_ID")
	private String tvnId;
	
	/** 主键 */
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "questionnaire_seq")
	@Column(name = "ID")
	private Integer id;

	public String getSurveyType() {
		return surveyType;
	}

	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}

	public String getSurveyTheme() {
		return surveyTheme;
	}

	public void setSurveyTheme(String surveyTheme) {
		this.surveyTheme = surveyTheme;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public String getQuestions() {
		return questions;
	}

	public String getAnswers() {
		return answers;
	}

	public Date getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}

	public String getTvnId() {
		return tvnId;
	}

	public void setTvnId(String tvnId) {
		this.tvnId = tvnId;
	}

}
