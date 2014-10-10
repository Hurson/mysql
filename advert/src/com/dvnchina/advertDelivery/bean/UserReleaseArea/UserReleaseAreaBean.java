package com.dvnchina.advertDelivery.bean.UserReleaseArea;

/**
 * 用户投放区域 用于页面展示
 * @author Weicl
 *
 */

public class UserReleaseAreaBean {
	
	/**
	 * 用户的ID
	 */
	private String userId;
	
	/**
	 * 投放区域id
	 */
	private String releaseAreaId;
	
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
	 * 投放区域code
	 */
	private String areaCode;
	
	
	/**
	 * 投放区域名称
	 */
	private String areaName;
	

	/**
	 *投放区域编码 
	 */
	private String prentCode;
	

	/**
	 * 投放区域类型
	 */
	private String locationType;


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getReleaseAreaId() {
		return releaseAreaId;
	}


	public void setReleaseAreaId(String releaseAreaId) {
		this.releaseAreaId = releaseAreaId;
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


	public String getAreaCode() {
		return areaCode;
	}


	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}


	public String getAreaName() {
		return areaName;
	}


	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


	public String getPrentCode() {
		return prentCode;
	}


	public void setPrentCode(String prentCode) {
		this.prentCode = prentCode;
	}


	public String getLocationType() {
		return locationType;
	}


	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	
	
}
