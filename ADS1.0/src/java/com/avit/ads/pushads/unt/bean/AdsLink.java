package com.avit.ads.pushads.unt.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 推荐链接信息
 *
 */
public class AdsLink implements Serializable{

	private static final long serialVersionUID = 2980930364406298528L;
	private Long id;//主键
	private Integer serviceId;//频道ID
	private Integer tvn;//用户号
	private String url;//推荐链接
	private String userIndustry;//用户行业组
	private String userlevel;//用户级别组
	private Date   startTime;//开始时间
	private Date   endTime;//结束时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUserIndustry(String userIndustry) {
		this.userIndustry = userIndustry;
	}
	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUserIndustry() {
		return userIndustry;
	}
	public String getUserlevel() {
		return userlevel;
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
