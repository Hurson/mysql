package com.dvnchina.advertDelivery.model;

import java.util.Date;

public class AdAssetsForm {
	
	private Integer id;
	private String name;
	private String  type;
	private String description;
	private String businessName;
	private Integer contractCode;
	
	private Date createTime;
	
	private Date createTimeA;
	private Date createTimeB;
	
	private String state;
	
	public AdAssetsForm(){}
	
	public AdAssetsForm(Integer id){
		this.id = id;
	}

	public AdAssetsForm(Integer id, String name, String type,
			String description, String businessName, Integer contractCode,
			Date createTime, Date createTimeA, Date createTimeB, String state) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.businessName = businessName;
		this.contractCode = contractCode;
		this.createTime = createTime;
		this.createTimeA = createTimeA;
		this.createTimeB = createTimeB;
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getContractCode() {
		return contractCode;
	}

	public void setContractCode(Integer contractCode) {
		this.contractCode = contractCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeA() {
		return createTimeA;
	}

	public void setCreateTimeA(Date createTimeA) {
		this.createTimeA = createTimeA;
	}

	public Date getCreateTimeB() {
		return createTimeB;
	}

	public void setCreateTimeB(Date createTimeB) {
		this.createTimeB = createTimeB;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}





