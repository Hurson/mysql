package com.dvnchina.advertDelivery.order.bean;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;

/**
 * 封装资产的基本信息
 * */
public class MaterialBean extends CommonBean {
	/**
	 * 订单素材关系主键
	 * */
	private Integer relId;
	/**
	 * 素材的轮询索引
	 * */
	private Integer loopNo;
	/**
	 * 素材的插播位置
	 * */
	private String instream;
	/**
	 * 素材的广告商名称
	 * */
	private String businessName;
	/**
	 * 素材类型
	 * */
	private Integer type;
	/**
	 * 素材路径
	 * */
	private String path;
	/**
	 * 文字类型素材的内容
	 * */
	private String content;
	/**
	 * 文字类型素材的链接地址
	 * */
	private String url;
	/**
	 * 素材描述
	 * */
	private String description;

	/** 生效时间 */
	private Date startDate;

	/** 失效时间 */
	private Date endDate;

	public Integer getRelId() {
		return relId;
	}

	public void setRelId(Integer relId) {
		this.relId = relId;
	}

	public Integer getLoopNo() {
		return loopNo;
	}

	public void setLoopNo(Integer loopNo) {
		this.loopNo = loopNo;
	}

	public String getInstream() {
		return instream;
	}

	public void setInstream(String instream) {
		this.instream = instream;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(Blob content) {
		InputStream in;
		StringBuffer buf = new StringBuffer();
		try {
			in = content.getBinaryStream();
			byte[] buff = new byte[(int) content.length()];
			while (in.read(buff) > 0) {
				buf.append(new String(buff, "gbk"));
			}
			this.content = buf.toString();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
