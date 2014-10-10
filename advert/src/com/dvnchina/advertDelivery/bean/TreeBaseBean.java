package com.dvnchina.advertDelivery.bean;

import java.io.Serializable;
import java.util.List;

public class TreeBaseBean implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键的ID
	 */
	private String id;
	
	/**
	 * 显示名称
	 */
	private String text;
	
	/**
	 * 0 、1、 2
	 */
	private String checked;
	
	/**
	 * 父ID
	 */
	private String pid;
	
	/**
	 * 子节点集合
	 */
	private Object children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Object getChildren() {
		return children;
	}

	public void setChildren(Object children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "TreeBaseBean [checked=" + checked + ", children=" + children + ", id=" + id + ", pid=" + pid + ", text=" + text
				+ "]";
	}
}
