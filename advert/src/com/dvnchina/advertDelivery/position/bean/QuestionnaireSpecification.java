package com.dvnchina.advertDelivery.position.bean;

import java.io.Serializable;

public class QuestionnaireSpecification implements Serializable{
	
	private static final long serialVersionUID = -5012396794056550227L;

	private Integer id;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 问题个数
	 */
	private String fileSize;
	/**
	 * 每个问题选项个数
	 */				
	private Integer optionNumber;
	/**
	 * 问题最大字数
	 */				
	private Integer maxLength;
	/**
	 * 过滤内容
	 */			   
	private String excludeContent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public Integer getOptionNumber() {
		return optionNumber;
	}

	public void setOptionNumber(Integer optionNumber) {
		this.optionNumber = optionNumber;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getExcludeContent() {
		return excludeContent;
	}

	public void setExcludeContent(String excludeContent) {
		this.excludeContent = excludeContent;
	}
	
}
