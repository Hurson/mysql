package com.dvnchina.advertDelivery.order.bean;

import java.io.Serializable;
import java.util.Date;

import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.Transform;

public class DOrder implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Integer id;
	/** 订单编码 */
	private String orderCode;
	/** 合同ID */
	private Integer contractId;
	/** 合同名称 */
	private String contractName;
	/** 广告位ID */
	private Integer positionId;
	/** 广告位名称 */
	private String positionName;
	/** 策略ID */
	private Integer ployId;
	/** 策略名称 */
	private String ployName;
	/** 订单开始时间 */
	private Date startTime;
	/** 订单结束时间 */
	private Date endTime;
	/** 状态 */
	private String state;
	/** ORDER_TYPE */
	private Integer orderType;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date modifyTime;
	/** 操作员ID */
	private Integer operatorId;
	/** 描述 */
	private String description;
	/** 总投放次数 */
	private Integer playNumber;
	/** 已投放次数 */
	private Integer playedNumber;
	
	/** 订单开始日期（字符） */
	private String startDateStr;
	/** 订单结束日期（字符） */
	private String endDateStr;
	/** 订单关联的素材 */
	private String selResource;
	
	/** 投放开始日期 */
	private Date validStart;
	/** 投放结束日期 */
	private Date validEnd;
	/** 策略开始时间 */
	private String ployStartTime;
	/** 策略结束时间 */
	private String ployEndTime;
	/** 广告商ID */
	private Integer customerId;
	/** 广告商名称 */
	private String customerName;
	/** 审核意见 */
	private String opinion;
	/** 修改标识，0-全部修改，1-修改结束时间 */
	private Integer updateFlag = 0;
	
	/** 用户总次数 */
	private Integer userNumber = 0;
	/** 通知门限值 */
	private Integer thresholdNumber = 0;
	/** 问卷总次数 */
	private Integer questionnaireNumber = 0;
	/** 积分兑换人民币比率 */
	private String integralRatio ;
	/** 问卷已请求数 */
	private Integer questionnaireCount = 0;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public Integer getPloyId() {
		return ployId;
	}
	public void setPloyId(Integer ployId) {
		this.ployId = ployId;
	}
	public String getPloyName() {
		return ployName;
	}
	public void setPloyName(String ployName) {
		this.ployName = ployName;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPlayNumber() {
		return playNumber;
	}
	public void setPlayNumber(Integer playNumber) {
		this.playNumber = playNumber;
	}
	public Integer getPlayedNumber() {
		return playedNumber;
	}
	public void setPlayedNumber(Integer playedNumber) {
		this.playedNumber = playedNumber;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public String getSelResource() {
		return selResource;
	}
	public void setSelResource(String selResource) {
		this.selResource = selResource;
	}
	public Date getValidStart() {
		return validStart;
	}
	public void setValidStart(Date validStart) {
		this.validStart = validStart;
	}
	public Date getValidEnd() {
		return validEnd;
	}
	public void setValidEnd(Date validEnd) {
		this.validEnd = validEnd;
	}
	public String getPloyStartTime() {
		return ployStartTime;
	}
	public void setPloyStartTime(String ployStartTime) {
		this.ployStartTime = ployStartTime;
	}
	public String getPloyEndTime() {
		return ployEndTime;
	}
	public void setPloyEndTime(String ployEndTime) {
		this.ployEndTime = ployEndTime;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public Integer getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(Integer updateFlag) {
		this.updateFlag = updateFlag;
	}
	
	public Integer getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(Integer userNumber) {
		this.userNumber = userNumber;
	}
	public Integer getQuestionnaireNumber() {
		return questionnaireNumber;
	}
	public void setQuestionnaireNumber(Integer questionnaireNumber) {
		this.questionnaireNumber = questionnaireNumber;
	}
	public Integer getThresholdNumber() {
		return thresholdNumber;
	}
	public void setThresholdNumber(Integer thresholdNumber) {
		this.thresholdNumber = thresholdNumber;
	}
	public String getIntegralRatio() {
		return integralRatio;
	}
	public void setIntegralRatio(String integralRatio) {
		this.integralRatio = integralRatio;
	}
	public Integer getQuestionnaireCount() {
		return questionnaireCount;
	}
	public void setQuestionnaireCount(Integer questionnaireCount) {
		this.questionnaireCount = questionnaireCount;
	}
	/**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(id)) ? "ID:" + id + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(orderCode)) ? "订单编号:" + orderCode + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(contractId)) ? "合同ID:" + contractId + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(positionId)) ? "广告位ID:" + positionId + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(ployId)) ? "策略ID:" + ployId + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(startTime)) ? "开始日期:" + Transform.date2String(startTime, "yyyy-MM-dd")+ "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(endTime)) ? "结束日期:" + Transform.date2String(endTime, "yyyy-MM-dd")+ "," : "");
        if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }
}
