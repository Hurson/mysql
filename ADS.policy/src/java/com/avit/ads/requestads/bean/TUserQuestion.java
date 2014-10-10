package com.avit.ads.requestads.bean;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TUserQuestion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user_question", catalog = "ads")
public class TUserQuestion implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer user_questionnaire_id;
	private String usersn;
	private Long questionnaireId;
	private Long questionId;
	private String questionname;
	private Long optionsId;
	private String optionsName;
	private Short flag;
	private Date createTime;

	// Constructors

	/** default constructor */
	public TUserQuestion() {
	}

	/** full constructor */
	public TUserQuestion(String usersn, Long questionnaireId, Long questionId,
			String questionname, Long optionsId, String optionsName,
			Short flag, Timestamp createTime) {
		this.usersn = usersn;
		this.questionnaireId = questionnaireId;
		this.questionId = questionId;
		this.questionname = questionname;
		this.optionsId = optionsId;
		this.optionsName = optionsName;
		this.flag = flag;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_questionnaire_id")
	public Integer getUser_questionnaire_id() {
		return user_questionnaire_id;
	}

	public void setUser_questionnaire_id(Integer user_questionnaire_id) {
		this.user_questionnaire_id = user_questionnaire_id;
	}

	@Column(name = "USERSN", length = 20)
	public String getUsersn() {
		return this.usersn;
	}

	public void setUsersn(String usersn) {
		this.usersn = usersn;
	}

	@Column(name = "QUESTIONNAIRE_ID", precision = 14, scale = 0)
	public Long getQuestionnaireId() {
		return this.questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	@Column(name = "QUESTION_ID", precision = 11, scale = 0)
	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	@Column(name = "QUESTIONNAME")
	public String getQuestionname() {
		return this.questionname;
	}

	public void setQuestionname(String questionname) {
		this.questionname = questionname;
	}

	@Column(name = "OPTIONS_ID", precision = 11, scale = 0)
	public Long getOptionsId() {
		return this.optionsId;
	}

	public void setOptionsId(Long optionsId) {
		this.optionsId = optionsId;
	}

	@Column(name = "OPTIONS_NAME", length = 20)
	public String getOptionsName() {
		return this.optionsName;
	}

	public void setOptionsName(String optionsName) {
		this.optionsName = optionsName;
	}

	@Column(name = "FLAG")
	public Short getFlag() {
		return this.flag;
	}

	public void setFlag(Short flag) {
		this.flag = flag;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}