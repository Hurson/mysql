package com.dvnchina.advertDelivery.order.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 保存DTV发送FTP需要的信息
 * @author mengjt
 *
 */
public class DtvFtpInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/** 精准ID  0代表策略*/
	private Integer preciseId;
	/** 区域ID，多个以逗号，分割*/
	private String areaIds;
	/** 文件路径*/
	private String filePath;
	/** 0代表精准  1代表策略*/
	private Integer type;
	private String starttime;
	private String endtime;
	private String   startdate;
	private String   enddate;
	
	public Integer getPreciseId() {
		return preciseId;
	}
	public void setPreciseId(Integer preciseId) {
		this.preciseId = preciseId;
	}
	public String getAreaIds() {
		return areaIds;
	}
	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	
}
