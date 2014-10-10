package com.dvnchina.advertDelivery.model;

import java.util.Date;
import java.util.Set;

/**
 * 调查问卷实体
 * */
public class QuestionnaireReal {
	
	/**
	 * 主键
	 * */
	private Integer id;
	
	/**
	 * 问卷索引
	 * */
	private String QuestionnaireId;
	
	/**
	 * 问卷摘要
	 * */
	private String summary;
	
	/**
	 * 问卷类型
	 * */
	private Character questionnaireType;
	
	/**
	 * 图片路径
	 * */
	private String picturePath;
	
	/**
	 * 视频路径
	 * */
	private String videoPath;
	
	/**
	 * 状态
	 * */
	private Character state;
	
	/**
	 * 创建时间
	 * */
	private Date createTime;
	
	/**
	 * 修改时间
	 * */
	private Date modifyTime;
	
	/**
	 * 操作员
	 * */
	private Integer userId;
	
	/**
	 * 模板编号
	 * */
	private Integer templateId;
	
	/**
	 * 名称
	 * */
	private String name;
	
	/**
	 * 类别
	 * */
	private int type;
	
	/**
	 * 描述
	 * */
	private String description;
	
	/**
	 * 广告商编号
	 * */
	private Integer businessId;
	
	/**
	 * 广告商名称
	 * */
	private String businessName;
	
	/**
	 * 合同号
	 * */
	private Integer contractCode;
	
	/**
	 * 调查问卷问题集合
	 * */
	private Set<QuestionReal> questions;
	
	/**
	 * 问卷访问地址
	 */
	private String filePath;
	
	private Integer integral;
	
	

	public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestionnaireId() {
		return QuestionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		QuestionnaireId = questionnaireId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Character getQuestionnaireType() {
		return questionnaireType;
	}

	public void setQuestionnaireType(Character questionnaireType) {
		this.questionnaireType = questionnaireType;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public Character getState() {
		return state;
	}

	public void setState(Character state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getContractCode() {
		return contractCode;
	}

	public void setContractCode(Integer contractCode) {
		this.contractCode = contractCode;
	}

	public Set<QuestionReal> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<QuestionReal> questions) {
		this.questions = questions;
	}

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
	

}
