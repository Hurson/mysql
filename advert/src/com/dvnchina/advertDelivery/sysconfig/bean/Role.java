package com.dvnchina.advertDelivery.sysconfig.bean;

import java.io.Serializable;
import java.util.Date;

import com.dvnchina.advertDelivery.utils.StringUtil;

public class Role implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色ID
	 */
	private Integer roleId;

	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色描述
	 */
	private String description;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 角色类型：0 超级管理员 ，1运营商管理员，2广告商管理员
	 */
	private Integer type;
	
	/**
	 * 角色绑定的栏目ID
	 */
	private String columnIds;
	
	/**
	 * 角色绑定的栏目名称
	 */
	private String columnNames;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getColumnIds() {
		return columnIds;
	}

	public void setColumnIds(String columnIds) {
		this.columnIds = columnIds;
	}

	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}
	/**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(roleId)) ? "roleId:" + roleId + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(name)) ? "角色名称:" + name + "," : "");
        if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }
}
