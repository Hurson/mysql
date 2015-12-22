package com.dvnchina.advertDelivery.sysconfig.bean;

import java.io.Serializable;
import java.util.Date;

import com.dvnchina.advertDelivery.utils.StringUtil;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 用户的ID
	 */
	private Integer userId;
	
	/**
	 *用户名称 
	 */
	private String userName;
	
	/**
	 *用户的登录名称 
	 */
	private String loginName;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 用户状态  1可用，0禁用
	 */
	private String state;
	
	/**
	 * 是否删除0未删除  1删除无效
	 */
	private Integer delFlag ;
	
	/** 角色ID */
	private Integer roleId;
	/** 角色名称 */
	private String roleName;
	/** 角色类型 */
	private Integer roleType;
	/** 绑定的广告商IDS */
	private String customerIds;
	/** 绑定的广告商名称 */
	private String customerNames;
	/** 指定广告位id*/
	private String positionIds;
	/** 指定广告位名*/
	private String positionNames;
	/**选择区域areaCode*/
	private String areaCodes;
	/**选择区域名称areaName*/
	private String areaNames;
	/** 指定无线广告位id*/
	private String dtmbPositionIds;
	/** 指定无线广告位名*/
	private String dtmbPositionNames;
	

	public String getPositionIds() {
		return positionIds;
	}

	public void setPositionIds(String positionIds) {
		this.positionIds = positionIds;
	}

	public String getPositionNames() {
		return positionNames;
	}

	public void setPositionNames(String positionNames) {
		this.positionNames = positionNames;
	}

	public String getAreaCodes() {
		return areaCodes;
	}

	public void setAreaCodes(String areaCodes) {
		this.areaCodes = areaCodes;
	}

	public String getAreaNames() {
		return areaNames;
	}

	public void setAreaNames(String areaNames) {
		this.areaNames = areaNames;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getCustomerNames() {
		return customerNames;
	}

	public void setCustomerNames(String customerNames) {
		this.customerNames = customerNames;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
	
	public String getDtmbPositionIds() {
		return dtmbPositionIds;
	}

	public void setDtmbPositionIds(String dtmbPositionIds) {
		this.dtmbPositionIds = dtmbPositionIds;
	}

	public String getDtmbPositionNames() {
		return dtmbPositionNames;
	}

	public void setDtmbPositionNames(String dtmbPositionNames) {
		this.dtmbPositionNames = dtmbPositionNames;
	}

	/**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(userId)) ? "userId:" + userId + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(userName)) ? "用户名称:" + userName + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(loginName)) ? "登录名:" + loginName + "," : "");
        if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }

}
