package com.avit.ads.pushads.unt.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 滚动字幕信息
 *
 */
public class AdsSubtitle implements Serializable{
	
	private static final long serialVersionUID = 3348837885247965731L;
	private Long    id ;//主键ID
	private Integer serviceId;//频道ID 
	private Integer tvn;//用户号 0为所有 
	private String action;//控制显示动作
	private Integer durationTime;//字幕持续时间
	private Integer fontSize;//字体大小
	private String fontColor;//字体颜色
	private Integer regionLeft;//区域-左
	private Integer regionAbove;//区域-上
	private Integer regionWidth;//区域-宽
	private Integer regionHeight;//区域-高
	private String regionColor;//区域-颜色
	private Integer speed;//滚动速度
	private String content;//字幕内容
	private String userIndustry;//用户行业组 ","分隔
	private String userlevel;//用户级别组 ","分隔
	private Date   startTime;//开始时间
	private Date   endTime;//结束时间
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getTvn() {
		return tvn;
	}
	public void setTvn(Integer tvn) {
		this.tvn = tvn;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getDurationTime() {
		return durationTime;
	}
	public void setDurationTime(Integer durationTime) {
		this.durationTime = durationTime;
	}
	public Integer getFontSize() {
		return fontSize;
	}
	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public Integer getRegionLeft() {
		return regionLeft;
	}
	public void setRegionLeft(Integer regionLeft) {
		this.regionLeft = regionLeft;
	}
	public Integer getRegionAbove() {
		return regionAbove;
	}
	public void setRegionAbove(Integer regionAbove) {
		this.regionAbove = regionAbove;
	}
	public Integer getRegionWidth() {
		return regionWidth;
	}
	public void setRegionWidth(Integer regionWidth) {
		this.regionWidth = regionWidth;
	}
	public Integer getRegionHeight() {
		return regionHeight;
	}
	public void setRegionHeight(Integer regionHeight) {
		this.regionHeight = regionHeight;
	}
	public String getRegionColor() {
		return regionColor;
	}
	public void setRegionColor(String regionColor) {
		this.regionColor = regionColor;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserIndustry() {
		return userIndustry;
	}
	public void setUserIndustry(String userIndustry) {
		this.userIndustry = userIndustry;
	}
	public String getUserlevel() {
		return userlevel;
	}
	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
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
