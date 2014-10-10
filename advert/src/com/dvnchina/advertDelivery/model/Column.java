package com.dvnchina.advertDelivery.model;

/**
 * 栏目的实体
 * @author Administrator
 *
 */
public class Column  extends CommonObject{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 栏目名称
	 */
	private String name; 
	
	/**
	 * 栏目说明
	 */
private String description;
	
	/**
	 * 栏目编号
	 */
	private String columnCode;
	
	/**
	 * 父级栏目的ID
	 */
	private Integer parentId;
	
	private String parentName;
	
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}


	/**
	 * 栏目状态
	 */
	private char state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
	}
	
	

	@Override
	public String toString() {
		return "Column [columnCode=" + columnCode + ", description=" + description + ", name=" + name + ", parentId=" + parentId
				+ ", state=" + state + "]";
	}
}
