package com.dvnchina.advertDelivery.model;

import java.util.Date;

public class Customer implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String  advertisersName;
	private String  clientCode;
	private String  remark;
	private String  conpanyAddress;
	private String  conpanySheet;
	private String  communicator;
	private String  tel;
	private String  mobileTel;
	private String  fax;
	private String  contacts;
	private String  cooperationTime;
	private String  creditRating;
	private String contract;
	private String businessLicence;
	private String status;
	private Date    createTime;
	private Integer operator;
	
	//创建时间前
	private Date createTimeA;
	//创建时间后
	private Date createTimeB;
	
	
	//广告商级别
	private Integer customerLevel;
	//注册资金
	private Integer registerFinancing;
	//注册地
	private String registerAddress;
	//营业面积 
	private Integer businessArea;
	
	
	
	public Customer(){
	}

	public Customer(Integer id, String advertisersName, String clientCode,
			String remark, String conpanyAddress, String conpanySheet,
			String communicator, String tel, String mobileTel, String fax,
			String contacts, String cooperationTime, String creditRating,
			String contract, String businessLicence, String status,
			Date createTime, Integer operator, Date createTimeA,
			Date createTimeB) {
		super();
		this.id = id;
		this.advertisersName = advertisersName;
		this.clientCode = clientCode;
		this.remark = remark;
		this.conpanyAddress = conpanyAddress;
		this.conpanySheet = conpanySheet;
		this.communicator = communicator;
		this.tel = tel;
		this.mobileTel = mobileTel;
		this.fax = fax;
		this.contacts = contacts;
		this.cooperationTime = cooperationTime;
		this.creditRating = creditRating;
		this.contract = contract;
		this.businessLicence = businessLicence;
		this.status = status;
		this.createTime = createTime;
		this.operator = operator;
		this.createTimeA = createTimeA;
		this.createTimeB = createTimeB;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdvertisersName() {
		return advertisersName;
	}

	public void setAdvertisersName(String advertisersName) {
		this.advertisersName = advertisersName;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConpanyAddress() {
		return conpanyAddress;
	}

	public void setConpanyAddress(String conpanyAddress) {
		this.conpanyAddress = conpanyAddress;
	}

	public String getConpanySheet() {
		return conpanySheet;
	}

	public void setConpanySheet(String conpanySheet) {
		this.conpanySheet = conpanySheet;
	}

	public String getCommunicator() {
		return communicator;
	}

	public void setCommunicator(String communicator) {
		this.communicator = communicator;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobileTel() {
		return mobileTel;
	}

	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getCooperationTime() {
		return cooperationTime;
	}

	public void setCooperationTime(String cooperationTime) {
		this.cooperationTime = cooperationTime;
	}

	public String getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
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

	@Override
	public String toString() {
		return "Customer [advertisersName=" + advertisersName + ", businessLicence=" + businessLicence + ", clientCode="
				+ clientCode + ", communicator=" + communicator + ", conpanyAddress=" + conpanyAddress + ", conpanySheet="
				+ conpanySheet + ", contacts=" + contacts + ", contract=" + contract + ", cooperationTime=" + cooperationTime
				+ ", createTime=" + createTime + ", createTimeA=" + createTimeA + ", createTimeB=" + createTimeB
				+ ", creditRating=" + creditRating + ", fax=" + fax + ", id=" + id + ", mobileTel=" + mobileTel + ", operator="
				+ operator + ", remark=" + remark + ", status=" + status + ", tel=" + tel + ",customerLevel="
				+ customerLevel + ", registerFinancing=" + registerFinancing + ", registerAddress=" + registerAddress + ", businessArea=" + businessArea + "]";
		
		
	}

	public Integer getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(Integer customerLevel) {
		this.customerLevel = customerLevel;
	}

	public Integer getRegisterFinancing() {
		return registerFinancing;
	}

	public void setRegisterFinancing(Integer registerFinancing) {
		this.registerFinancing = registerFinancing;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public Integer getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(Integer businessArea) {
		this.businessArea = businessArea;
	}

}


