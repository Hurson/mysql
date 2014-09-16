package com.avit.ads.pushads.unt.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 单向广告图片信息
 *
 */
public class AdsPicture implements Serializable{
	
	private static final long serialVersionUID = -5788675871520704164L;
	private String pictureURL;//图片链接
	private String configURL;//图片配置链接
	private Date   startTime;//开始时间
	private Date   endTime;//结束时间
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public String getConfigURL() {
		return configURL;
	}
	public void setConfigURL(String configURL) {
		this.configURL = configURL;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
