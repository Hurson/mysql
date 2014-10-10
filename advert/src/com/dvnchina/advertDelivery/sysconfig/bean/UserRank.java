package com.dvnchina.advertDelivery.sysconfig.bean;

import java.io.Serializable;

import com.dvnchina.advertDelivery.utils.StringUtil;

public class UserRank implements Serializable {

	private static final long serialVersionUID = -7349495412127145259L;
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 用户级别编码 
	 */
	private String userRankCode;
	/**
	 * 用户级别名称
	 */
	private String userRankName;
	
	/**
	 * 描述
	 */
	private String description;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserRankCode() {
		return userRankCode;
	}
	public void setUserRankCode(String userRankCode) {
		this.userRankCode = userRankCode;
	}
	public String getUserRankName() {
		return userRankName;
	}
	public void setUserRankName(String userRankName) {
		this.userRankName = userRankName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(id)) ? "ID:" + id + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(userRankName)) ? "用户级别名称:" + userRankName + "," : "");
        if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }
}
