package com.dvnchina.advertDelivery.model;


import java.io.Serializable;
import java.util.List;

public class Template implements Serializable{
	
	private static final long serialVersionUID = 5333256483615872082L;
	
	private Integer id;
	
	private String templateName;
	
	private String effectPicture;
	
	private List<Position> positionList;
	
	/**
	 * 0 ���� 1 ����
	 */
	private Integer type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getEffectPicture() {
		return effectPicture;
	}

	public void setEffectPicture(String effectPicture) {
		this.effectPicture = effectPicture;
	}

	public List<Position> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<Position> positionList) {
		this.positionList = positionList;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
