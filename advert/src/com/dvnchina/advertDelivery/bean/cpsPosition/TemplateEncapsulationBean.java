package com.dvnchina.advertDelivery.bean.cpsPosition;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TemplateEncapsulationBean implements Serializable{

	private static final long serialVersionUID = -6392261385180562913L;
	
private Integer id;
	
	private String templateName;
	
	private String effectPicture;
	/**
	 * 解析XML文件时使用
	 */
	private List<PositionEncapsulationBean> positionList;
	/**
	 * 缓存中使用
	 */
	private Map<Integer,PositionEncapsulationBean> positionMap;
	
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

	public List<PositionEncapsulationBean> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<PositionEncapsulationBean> positionList) {
		this.positionList = positionList;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Map<Integer,PositionEncapsulationBean> getPositionMap() {
		return positionMap;
	}

	public void setPositionMap(Map<Integer,PositionEncapsulationBean> positionMap) {
		this.positionMap = positionMap;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TemplateEncapsulationBean)) return false;
		else {
			TemplateEncapsulationBean templateEncapsulationBean  = (TemplateEncapsulationBean) obj;
			if (null == this.getId() || null == templateEncapsulationBean.getId()) return false;
			else return (this.getId().equals(templateEncapsulationBean.getId()));
		}
	}

}
