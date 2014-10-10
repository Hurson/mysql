package com.avit.ads.requestads.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TQuestionnaireReal entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_questionnaire_real", catalog = "ads")
public class TQuestionnaireReal implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String description;
	private String questionnaireId;
	private String summary;
	private String questionnaireType;
	private String picturePath;
	private String videoPath;
	private String formalFilePath;
	private String filePath;
	private Long   integral;
	// Constructors

	/** default constructor */
	public TQuestionnaireReal() {
	}

	/** full constructor */
	public TQuestionnaireReal(String name, String description,
			String questionnaireId, String summary, String questionnaireType,
			String picturePath, String videoPath, String formalFilePath,
			String filePath) {
		this.name = name;
		this.description = description;
		this.questionnaireId = questionnaireId;
		this.summary = summary;
		this.questionnaireType = questionnaireType;
		this.picturePath = picturePath;
		this.videoPath = videoPath;
		this.formalFilePath = formalFilePath;
		this.filePath = filePath;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "QUESTIONNAIRE_ID", length = 100)
	public String getQuestionnaireId() {
		return this.questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	@Column(name = "SUMMARY", length = 600)
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "QUESTIONNAIRE_TYPE", length = 1)
	public String getQuestionnaireType() {
		return this.questionnaireType;
	}

	public void setQuestionnaireType(String questionnaireType) {
		this.questionnaireType = questionnaireType;
	}

	@Column(name = "PICTURE_PATH", length = 500)
	public String getPicturePath() {
		return this.picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	@Column(name = "VIDEO_PATH", length = 500)
	public String getVideoPath() {
		return this.videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	@Column(name = "FORMAL_FILE_PATH")
	public String getFormalFilePath() {
		return this.formalFilePath;
	}

	public void setFormalFilePath(String formalFilePath) {
		this.formalFilePath = formalFilePath;
	}

	@Column(name = "FILE_PATH", length = 500)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "INTEGRAL")
	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

}