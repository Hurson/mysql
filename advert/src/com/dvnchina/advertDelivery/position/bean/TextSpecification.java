package com.dvnchina.advertDelivery.position.bean;

import java.io.Serializable;
/**
 * 文本规格信息
 *
 */
public class TextSpecification implements Serializable{
	
	private static final long serialVersionUID = -7560684770612428658L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 文本规格信息名称
	 */
	private String textDesc;
	/**
	 * 字段长度
	 */
	private String textLength;
	/**
	 * 是否有链接
	 */
	private Integer isLink;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getIsLink() {
		return isLink;
	}

	public void setIsLink(Integer isLink) {
		this.isLink = isLink;
	}

	public String getTextDesc() {
		return textDesc;
	}

	public void setTextDesc(String textDesc) {
		this.textDesc = textDesc;
	}

	public String getTextLength() {
		return textLength;
	}

	public void setTextLength(String textLength) {
		this.textLength = textLength;
	}
	
}
