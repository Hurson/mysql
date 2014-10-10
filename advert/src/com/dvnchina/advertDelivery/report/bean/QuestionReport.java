package com.dvnchina.advertDelivery.report.bean;

import java.io.Serializable;

/**
 * 子广告位信息
 * @author mengjt
 *
 */
public class QuestionReport implements Serializable{

	private static final long serialVersionUID = 3933082652318260190L;
	/** 主键 */
	private Integer id;
	/** 广告位编码 */
	private String positionCode;
	/** 广告位名称 */
	private String positionName;
	/** 素材名称 */
	private String resourceName;
	/** 素材分类 */
	private String categoryName;
	/** 广告商名称 */
	private String advertisersName;
	/** 素材投放次数 */
	private Long pushCount;
	/** 素材展示次数 */
	private Long receiveCount;
	/** 投放时间 */
	private String pushDate;
	/** 投放开始日期 */
	private String pushDateStart;
	/** 投放结束日期 */
	private String pushDateEnd;
	/** 生成报表类型 */
	private Integer reportType;
	
	/** 投放开始日期 */
	private String searchStart;
	/** 投放结束日期 */
	private String searchEnd;
	public QuestionReport(){}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getPositionCode() {
		return positionCode;
	}


	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}


	public String getPositionName() {
		return positionName;
	}


	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}


	public String getResourceName() {
		return resourceName;
	}


	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getAdvertisersName() {
		return advertisersName;
	}


	public void setAdvertisersName(String advertisersName) {
		this.advertisersName = advertisersName;
	}

	public Long getPushCount() {
		return pushCount;
	}


	public void setPushCount(Long pushCount) {
		this.pushCount = pushCount;
	}


	public Long getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(Long receiveCount) {
		this.receiveCount = receiveCount;
	}

	public String getPushDateStart() {
		return pushDateStart;
	}


	public void setPushDateStart(String pushDateStart) {
		this.pushDateStart = pushDateStart;
	}


	public String getPushDateEnd() {
		return pushDateEnd;
	}


	public void setPushDateEnd(String pushDateEnd) {
		this.pushDateEnd = pushDateEnd;
	}


	public String getPushDate() {
		return pushDate;
	}


	public void setPushDate(String pushDate) {
		this.pushDate = pushDate;
	}


	public Integer getReportType() {
		return reportType;
	}


	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}


	public String getSearchStart() {
		return searchStart;
	}


	public void setSearchStart(String searchStart) {
		this.searchStart = searchStart;
	}


	public String getSearchEnd() {
		return searchEnd;
	}


	public void setSearchEnd(String searchEnd) {
		this.searchEnd = searchEnd;
	}

	
}
