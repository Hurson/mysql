package com.dvnchina.advertDelivery.bean;


/**
 * 用于页面展示
 * @author Administrator
 *
 */
public class UserRoleBean {
	
	/**
	 * 用户的ID
	 */
	private String userId;
	
	/**
	 * 用户的ID
	 */
	private String roleId;
	
	/**
	 *用户名称 
	 */
	private String username;
	
	/**
	 *用户的登录名称 
	 */
	private String loginname;
	
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
	private String createTime;
	
	/**
	 * 修改时间
	 */
	private String modifyTime;
	
	/**
	 * 用户状态  1登陆，0未登录，2禁用
	 */
	private String status;
	
	/**
	 * 用户类型编码
	 */
	private String userTypeCode;
	
	/**
	 * 用户类型值
	 */
	private String userTypeValue;
	
	/**
	 * 角色名称
	 */
	private String rolename;
	/**
	 * 角色描述
	 */
	private String description;
	
	/**
	 * 用户状态  1启用，0不启用
	 */
	private String rolestatus;
	
	/**
	 * 角色类型：0 超级管理员 ，1运营商管理员，2广告商管理员
	 */
	private String type;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public String getUserTypeValue() {
		return userTypeValue;
	}

	public void setUserTypeValue(String userTypeValue) {
		this.userTypeValue = userTypeValue;
	}

	public String getRoleName() {
		return rolename;
	}

	public void setRoleName(String name) {
		this.rolename = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRolestatus() {
		return rolestatus;
	}

	public void setRolestatus(String rolestatus) {
		this.rolestatus = rolestatus;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "UserRoleBean [createTime=" + createTime + ", description=" + description + ", email=" + email + ", loginname="
				+ loginname + ", modifyTime=" + modifyTime + ", password=" + password + ", roleId=" + roleId + ", rolename="
				+ rolename + ", rolestatus=" + rolestatus + ", status=" + status + ", type=" + type + ", userId=" + userId
				+ ", userTypeCode=" + userTypeCode + ", userTypeValue=" + userTypeValue + ", username=" + username + "]";
	}



}
