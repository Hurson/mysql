package com.dvnchina.advertDelivery.ploy.bean;

import java.util.Date;

import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.Transform;

/**
 * TNoAdPloy entity. @author MyEclipse Persistence Tools
 */

public class TNoAdPloy implements java.io.Serializable {

	// Fields

	private Long id;
	private String ployname;
	private Long positionid;
	private String tvn;
	private Date startDate;
	private Date endDate;

	// Constructors

	/** default constructor */
	public TNoAdPloy() {
	}

	/** minimal constructor */
	public TNoAdPloy(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TNoAdPloy(Long id, String ployname, Long positionid, String tvn,
			Date startDate, Date endDate) {
		this.id = id;
		this.ployname = ployname;
		this.positionid = positionid;
		this.tvn = tvn;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPloyname() {
		return this.ployname;
	}

	public void setPloyname(String ployname) {
		this.ployname = ployname;
	}

	public Long getPositionid() {
		return this.positionid;
	}

	public void setPositionid(Long positionid) {
		this.positionid = positionid;
	}

	public String getTvn() {
		return this.tvn;
	}

	public void setTvn(String tvn) {
		this.tvn = tvn;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(id)) ? "ID:" + id + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(ployname)) ? "禁播名称:" + ployname + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(positionid)) ? "广告位ID:" + positionid + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(tvn)) ? "TVN号:" + tvn + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(startDate)) ? "开始日期:" + Transform.date2String(startDate, "yyyy-MM-dd")+ "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(endDate)) ? "结束日期:" + Transform.date2String(endDate, "yyyy-MM-dd")+ "," : "");
       if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }
	

}