package com.dvnchina.advertDelivery.model;

import java.util.Date;

import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.Transform;

public class CustomerBackUp {
	
	private Integer id;
	
	/**
	 * 广告商名称
	 */
	private String  advertisersName;

	/**客户代码
	 * 
	 */
	private String  clientCode;

	/**
	 * 描述
	 */
	private String  remark;

	/**
	 * 公司地址
	 */
	private String  conpanyAddress;

	/**
	 * 公司主页
	 */
	private String  conpanySheet;

	/**
	 * 联系人
	 */
	private String  communicator;

	/**
	 * 电话
	 */
	private String  tel;

	/**
	 * 手机
	 */
	private String  mobileTel;

	/**
	 * 传真
	 */
	private String  fax;

	/**
	 * 通讯地址
	 */
	private String  contacts;

	/**
	 * 合作期限
	 */
	private String  cooperationTime;

	/**
	 * 信用等级
	 */
	private String  creditRating;

	/**
	 * 合同扫描件
	 */
	private String contract;

	/**
	 * 营业执照
	 */
	private String businessLicence;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 创建时间
	 */
	private Date    createTime;

	/**
	 * 操作人ID
	 */
	private Integer operator;

	/**
	 * 
	 */
	private String auditTaff;

	/**
	 * 审核意见
	 */
	private String examinationOpinions;

	/**
	 * 审核时间
	 */
	private Date auditDate;
	
	//创建时间前
	private Date createTimeA;
	//创建时间后
	private Date createTimeB;
	//评论置空项
	private String remarkAudit;
	//广告商级别
	private Integer customerLevel;
	//注册资金
	private Integer registerFinancing;
	//注册地
	private String registerAddress;
	//营业面积 
	private Integer businessArea;
	

	private Integer delflag;
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
	public String getAuditTaff() {
		return auditTaff;
	}
	public void setAuditTaff(String auditTaff) {
		this.auditTaff = auditTaff;
	}
	public String getExaminationOpinions() {
		return examinationOpinions;
	}
	public void setExaminationOpinions(String examinationOpinions) {
		this.examinationOpinions = examinationOpinions;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
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
	public String getRemarkAudit() {
		return remarkAudit;
	}
	public void setRemarkAudit(String remarkAudit) {
		this.remarkAudit = remarkAudit;
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
	public Integer getDelflag() {
		return delflag;
	}
	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}
	/**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(id)) ? "ID:" + id + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(advertisersName)) ? "广告商名称:" + advertisersName + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(clientCode)) ? "广告商编码:" + clientCode + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(cooperationTime)) ? "合作期限:" + cooperationTime + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(customerLevel)) ? "级别:" + customerLevel + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(registerFinancing)) ? "注册资金:" + registerFinancing + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(businessArea)) ? "营业面积:" + businessArea + "," : "");
        if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }
}
