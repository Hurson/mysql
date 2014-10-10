package com.avit.ads.requestads.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TQuestionPlaylistv entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_question_playlistv", catalog = "ads")
public class TQuestionPlaylistv implements java.io.Serializable {

	// Fields

	private String id;
	private String questionnaireId;
	private String contentPath;
	private BigDecimal orderId;
	private Integer maxPeruser;
	private Integer maxPerquestion;
	private Integer scoreScale;
    private Date     beginDate;
    private Date     endDate;
    private int retvalue;
	// Constructors

	/** default constructor */
	public TQuestionPlaylistv() {
	}

	/** full constructor */
	public TQuestionPlaylistv(String id, String questionnaireId,
			String contentPath, BigDecimal orderId, Integer maxPeruser,
			Integer maxPerquestion, Integer scoreScale) {
		this.id = id;
		this.questionnaireId = questionnaireId;
		this.contentPath = contentPath;
		this.orderId = orderId;
		this.maxPeruser = maxPeruser;
		this.maxPerquestion = maxPerquestion;
		this.scoreScale = scoreScale;
	}

	// Property accessors

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "QUESTIONNAIRE_ID")
	public String getQuestionnaireId() {
		return this.questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	@Column(name = "CONTENT_PATH", length = 555)
	public String getContentPath() {
		return this.contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	@Column(name = "ORDER_ID", precision = 38, scale = 0)
	public BigDecimal getOrderId() {
		return this.orderId;
	}

	public void setOrderId(BigDecimal orderId) {
		this.orderId = orderId;
	}

	@Column(name = "max_peruser")
	public Integer getMaxPeruser() {
		return this.maxPeruser;
	}

	public void setMaxPeruser(Integer maxPeruser) {
		this.maxPeruser = maxPeruser;
	}

	@Column(name = "max_perquestion")
	public Integer getMaxPerquestion() {
		return this.maxPerquestion;
	}

	public void setMaxPerquestion(Integer maxPerquestion) {
		this.maxPerquestion = maxPerquestion;
	}

	@Column(name = "score_scale")
	public Integer getScoreScale() {
		return this.scoreScale;
	}

	public void setScoreScale(Integer scoreScale) {
		this.scoreScale = scoreScale;
	}

	@Column(name = "BEGIN_DATE")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name = "END_DATE")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Transient
	public int getRetvalue() {
		return retvalue;
	}

	public void setRetvalue(int retvalue) {
		this.retvalue = retvalue;
	}
	
	
}