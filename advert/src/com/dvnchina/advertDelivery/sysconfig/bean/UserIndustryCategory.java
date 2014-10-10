package com.dvnchina.advertDelivery.sysconfig.bean;

import java.io.Serializable;

import com.dvnchina.advertDelivery.utils.StringUtil;

public class UserIndustryCategory implements Serializable {

	private static final long serialVersionUID = -3207135679920030191L;
	
	private Integer id; 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 行业类别编码
	 */
	
	private String userIndustryCategoryCode;
	/**
	 * 行业类型值
	 */
	private String userIndustryCategoryValue;

	
	public String getUserIndustryCategoryCode() {
		return userIndustryCategoryCode;
	}

	public void setUserIndustryCategoryCode(String userIndustryCategoryCode) {
		this.userIndustryCategoryCode = userIndustryCategoryCode;
	}

	public String getUserIndustryCategoryValue() {
		return userIndustryCategoryValue;
	}

	public void setUserIndustryCategoryValue(String userIndustryCategoryValue) {
		this.userIndustryCategoryValue = userIndustryCategoryValue;
	}
	
	/**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(id)) ? "ID:" + id + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(userIndustryCategoryCode)) ? "用户行业编码:" + userIndustryCategoryCode + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(userIndustryCategoryValue)) ? "用户行业名称:" + userIndustryCategoryValue + "," : "");
        if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }

	
}
