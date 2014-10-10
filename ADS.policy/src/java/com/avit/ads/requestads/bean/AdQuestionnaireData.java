package com.avit.ads.requestads.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AdQuestionnaireData entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_QUESTIONNAIRE_DATA")
//@SequenceGenerator(name = "questionnaire_seq", sequenceName = "AD_QUESTIONNAIRE_SEQ", allocationSize = 25)
public class AdQuestionnaireData implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String surveyType;
	private String surveyId;
	private String surveyTheme;
	private Date writeTime;
	private String tvnId;

	// Constructors

	/** default constructor */
	public AdQuestionnaireData() {
	}

	/** minimal constructor */
	public AdQuestionnaireData(long id) {
		this.id = id;
	}

	/** full constructor */
	public AdQuestionnaireData(long id, 
			String surveyType, String surveyId, String surveyTheme,
			Date writeTime, String tvnId) {
		this.id = id;
		this.surveyType = surveyType;
		this.surveyId = surveyId;
		this.surveyTheme = surveyTheme;
		this.writeTime = writeTime;
		this.tvnId = tvnId;
	}

	// Property accessors
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "questionnaire_seq")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "SURVEY_TYPE", length = 2)
	public String getSurveyType() {
		return this.surveyType;
	}

	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}

	@Column(name = "SURVEY_ID", length = 10)
	public String getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	@Column(name = "SURVEY_THEME", length = 100)
	public String getSurveyTheme() {
		return this.surveyTheme;
	}

	public void setSurveyTheme(String surveyTheme) {
		this.surveyTheme = surveyTheme;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "WRITE_TIME", length = 7)
	public Date getWriteTime() {
		return this.writeTime;
	}

	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}

	@Column(name = "TVN_ID", length = 50)
	public String getTvnId() {
		return this.tvnId;
	}

	public void setTvnId(String tvnId) {
		this.tvnId = tvnId;
	}

}