package com.dvnchina.advertDelivery.order.bean;

import java.util.List;

/**
 * 封装订单绑定的资产信息
 * */
public class OrderMaterialBean {
	/**
	 * 精准id
	 * */
	private Integer pId;
	
	/**
	 * 精准名称
	 * */
	private String preciseName;
	
	/**
	 * 素材集合
	 * */
	private List<MaterialBean> material;
	
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getPreciseName() {
		return preciseName;
	}
	public void setPreciseName(String preciseName) {
		this.preciseName = preciseName;
	}
	public List<MaterialBean> getMaterial() {
		return material;
	}
	public void setMaterial(List<MaterialBean> material) {
		this.material = material;
	}
}


