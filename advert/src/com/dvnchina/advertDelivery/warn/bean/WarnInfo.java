package com.dvnchina.advertDelivery.warn.bean;

import java.util.Date;
/*
 * 告警信息
 */
public class WarnInfo {
	/** 主键 */
	private Integer id;
	/** 告警时间 */
	private Date time;
	/** 告警内容 */
	private String content;
	/** 是否已经处理 （1: 已处理， 0： 未处理）*/
	private Integer isProcessed;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsProcessed() {
		return isProcessed;
	}
	public void setIsProcessed(Integer isProcessed) {
		this.isProcessed = isProcessed;
	}

	
}
