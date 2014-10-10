package com.dvnchina.advertDelivery.ploy.bean;

import java.util.Date;
import java.util.List;

/**
 * 策略实体
 * */
public class Ploy {
	public Ploy(){
		
	}
	private Integer id;
	private Integer ployId;
	private String ployName;
	private Integer customerId;
	private Integer contractId;
	private String contractName;
	private Integer positionId;
	private String positionName;
	private Integer ruleId;
	private String ruleName;
	private String startTime;
	private String endTime;
	private Integer priority;
	private Long areaId;
	private Integer channelId;
	
	private Integer groupId;
	private String groupName;//组名称
	private Integer operationId;
	private Date createTime;
	private Date modifyTime;
	private String state;
	private String description;
	private Integer ployNumber;
	private String defaultstart;
	
	private String action;
	private Integer durationTime;
	private Integer fontSize;
	private String fontColor;
	private String backgroundColor;
	private Integer rollSpeed;
	private Long operatorId;
	private Long auditID;
	private String auditOption;
	private Date   auditDate;
	private List<Ploy> subPloyList;//分策略列表
	public Ploy(Integer ployId,String ployName,Integer contractId,Integer positionId,Integer ruleId,String startTime,String endTime,Integer operationId,Date createTime,Date modifyTime,String state,String description,Integer ployNumber,String action,Integer durationTime,Integer fontSize,String fontColor,String backgroundColor,Integer rollSpeed)
	{
		this.ployId=ployId;
		this.ployName=ployName;
		this.contractId=contractId;
		this.positionId=positionId;
		this.ruleId=ruleId;
		this.startTime=startTime;
		this.endTime=endTime;
		this.operationId=operationId;
		this.createTime=createTime;
		this.modifyTime=modifyTime;
		this.state=state;
		this.description=description;
		this.ployNumber=ployNumber;
		this.action=action;
		this.durationTime=durationTime;
		this.fontSize=fontSize;
		this.fontColor=fontColor;
		this.backgroundColor=backgroundColor;
		this.rollSpeed=rollSpeed;
	}
	public Ploy(Integer ployId,String ployName,Integer contractId,Integer positionId,Integer ruleId,String startTime,String endTime,Integer operationId,Date createTime,Date modifyTime,String state,String description)
	{
		this.ployId=ployId;
		this.ployName=ployName;
		this.contractId=contractId;
		this.positionId=positionId;
		this.ruleId=ruleId;
		this.startTime=startTime;
		this.endTime=endTime;
		this.operationId=operationId;
		this.createTime=createTime;
		this.modifyTime=modifyTime;
		this.state=state;
		this.description=description;
	
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getContractId() {
		if(contractId == null){
			return 0;
		}
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getPositionId() {
		if(positionId == null){
			return 0;
		}
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getRuleId() {
		if(ruleId == null){
			return 0;
		}
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPloyNumber() {
		return ployNumber;
	}

	public void setPloyNumber(Integer ployNumber) {
		this.ployNumber = ployNumber;
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

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Integer getRollSpeed() {
		return rollSpeed;
	}

	public void setRollSpeed(Integer rollSpeed) {
		this.rollSpeed = rollSpeed;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public Long getAuditID() {
		return auditID;
	}
	public void setAuditID(Long auditID) {
		this.auditID = auditID;
	}
	public String getAuditOption() {
		return auditOption;
	}
	public void setAuditOption(String auditOption) {
		this.auditOption = auditOption;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public List<Ploy> getSubPloyList() {
		return subPloyList;
	}
	public void setSubPloyList(List<Ploy> subPloyList) {
		this.subPloyList = subPloyList;
	}
	public String getDefaultstart() {
		return defaultstart;
	}
	public void setDefaultstart(String defaultstart) {
		this.defaultstart = defaultstart;
	}

}
