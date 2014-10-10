package com.dvnchina.advertDelivery.model;

import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.order.bean.CommonParentBean;

/**
 * 策略实体
 * */
public class Ploy {
	public Ploy(){
		
	}
	private Integer id;
	private Integer ployId;
	private String ployName;
	private Integer contractId;
	private String contractName;
	private Integer positionId;
	private String positionName;
	private Integer ruleId;
	private String ruleName;
	private String startTime;
	private String endTime;
	private Integer areaId;
	private Integer channelId;
	private Integer operationId;
	private Date createTime;
	private Date modifyTime;
	private Character state;
	private String description;
	private Integer ployNumber;
	private String action;
	private Integer durationTime;
	private Integer fontSize;
	private String fontColor;
	private String backgroundColor;
	private Integer rollSpeed;
	/**
	 * 策略绑定的区域集合
	 * */
	private List<CommonParentBean> areas;
	public Ploy(Integer ployId,String ployName,Integer contractId,Integer positionId,Integer ruleId,String startTime,String endTime,Integer operationId,Date createTime,Date modifyTime,Character state,String description,Integer ployNumber,String action,Integer durationTime,Integer fontSize,String fontColor,String backgroundColor,Integer rollSpeed)
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
	public Ploy(Integer ployId,String ployName,Integer contractId,Integer positionId,Integer ruleId,String startTime,String endTime,Integer operationId,Date createTime,Date modifyTime,Character state,String description)
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

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Character getState() {
		return state;
	}

	public void setState(Character state) {
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
	public List<CommonParentBean> getAreas() {
		return areas;
	}
	public void setAreas(List<CommonParentBean> areas) {
		this.areas = areas;
	}

}
