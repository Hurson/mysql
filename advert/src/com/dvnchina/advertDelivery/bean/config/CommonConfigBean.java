package com.dvnchina.advertDelivery.bean.config;

import java.io.Serializable;

/**
 * 系统配置公共实体bean
 * 
 * @author Administrator
 *
 */
public class CommonConfigBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 值 -- value
	 */
	private String value;
	
	/**
	 * 名字 -- key
	 */
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CommonConfigBean [id=" + id + ", name=" + name + ", value=" + value + "]";
	}
	
	
}
