package com.avit.ads.requestads.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TUserQuestionnaire entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user_questionnaire", catalog = "ads")
public class TUserQuestionnaire implements java.io.Serializable {

	// Fields

	private Integer id;
	private String usersn;
	private Integer questionnaireId;
	private Integer orderId;
	private Date createTime;
	private String idnumber;
	private String tel;
	// Constructors

	/** default constructor */
	public TUserQuestionnaire() {
	}

	/** full constructor */
	public TUserQuestionnaire(String usersn, Integer questionnaireId,
			Date createTime) {
		this.usersn = usersn;
		this.questionnaireId = questionnaireId;
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

	@Column(name = "USERSN", length = 20)
	public String getUsersn() {
		return this.usersn;
	}

	public void setUsersn(String usersn) {
		this.usersn = usersn;
	}

	@Column(name = "QUESTIONNAIRE_id")
	public Integer getQuestionnaireId() {
		return this.questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	@Column(name = "ORDER_ID")
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "idnumber")
	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	@Column(name = "tel")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}