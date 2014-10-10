package com.dvnchina.advertDelivery.model;

import java.util.Date;

import com.dvnchina.advertDelivery.sysconfig.bean.User;
import com.dvnchina.advertDelivery.utils.StringUtil;

/**
 * 调查问卷模板实体
 * */
public class QuestionnaireTemplate {

	/**
	 * 主键
	 * */
	private Integer id;

	/**
	 * 模板名称
	 * */
	private String templateName;

	/**
	 * 模板包名
	 * */
	private String templatePackageName;

	/**
	 * html路径
	 * */
	private String htmlPath;

	/**
	 * 预览图路径
	 * */
	private String showImagePath;

	/**
	 * 状态
	 * */
	private Character state;

	/**
	 * 创建时间
	 * */
	private Date createTime;

	/**
	 * 修改时间
	 * */
	private Date modifyTime;

	/**
	 * 操作员
	 * */
	private User user;
	
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplatePackageName() {
		return templatePackageName;
	}

	public void setTemplatePackageName(String templatePackageName) {
		this.templatePackageName = templatePackageName;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public String getShowImagePath() {
		return showImagePath;
	}

	public void setShowImagePath(String showImagePath) {
		this.showImagePath = showImagePath;
	}

	public Character getState() {
		return state;
	}

	public void setState(Character state) {
		this.state = state;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
	

    /**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(id)) ? "ID:" + id + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(templateName)) ? "模板名称:" + templateName + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(htmlPath)) ? "模板路径:" + htmlPath + "," : "");
        if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }
}
