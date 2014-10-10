package com.dvnchina.advertDelivery.model;

public class RoleColumn  extends CommonObject{

	private static final long serialVersionUID = 1L;

	/**
	 * 角色的ID
	 */
	private Integer  roleId;
	
	/**
	 * 栏目的ID
	 */
	private Integer  columnId;
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	@Override
	public String toString() {
		return "RoleColumn [columnId=" + columnId + ", roleId=" + roleId + "]";
	}
	
}
