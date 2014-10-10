package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
import java.util.List;

public class CpsTemplate implements Serializable {

	private static final long serialVersionUID = 8409139636041631560L;

	private Integer id;
	
	private String templateName;
	
	private String effectPicture;
	
	private List<CpsPosition> positionList;
	
	/**
	 * 0 标清 1 高清
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
	
	public List<CpsPosition> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<CpsPosition> positionList) {
		this.positionList = positionList;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
