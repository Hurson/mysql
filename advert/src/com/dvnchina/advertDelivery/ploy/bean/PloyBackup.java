package com.dvnchina.advertDelivery.ploy.bean;

import java.util.Date;

import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.Transform;

/**
 * 维护期策略实体
 * */
public class PloyBackup {
	public PloyBackup(){
		
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
	private Integer channelGroupType;	//add by lwp
	private String  groupName;
	private Integer operationId;
	private Date createTime;
	private Date modifyTime;
	private String state;
	private String description;
	private Integer ployNumber;
	
	private String userIndustrys;
	private String userLevels;
	private String tvnNumber;
	
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
	private Integer delflag;
	private boolean auditFlag;
	public PloyBackup(Integer ployId,String ployName,Integer contractId,Integer positionId,Integer ruleId,String startTime,String endTime,Integer operationId,Date createTime,Date modifyTime,String state,String description,Integer ployNumber,String action,Integer durationTime,Integer fontSize,String fontColor,String backgroundColor,Integer rollSpeed)
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
	
	public PloyBackup(Integer ployId,String ployName,Integer contractId,Integer positionId,Integer ruleId,String startTime,String endTime,Integer operationId,Date createTime,Date modifyTime,String state,String description)
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
	public PloyBackup(Integer ployId,String ployName,Integer customerId,Integer positionId,Integer ruleId,Integer operationId,Date createTime,Date modifyTime,String state,String description,Integer ployNumber,String defaultstart)
	{
		this.ployId=ployId;
		this.ployName=ployName;
		this.customerId=customerId;
		this.positionId=positionId;
		this.ruleId=ruleId;
		this.operationId=operationId;
		this.createTime=createTime;
		this.modifyTime=modifyTime;
		this.state=state;
		this.description=description;
		
		this.ployNumber=ployNumber;
		this.defaultstart=defaultstart;
	}
	
	public PloyBackup(Integer ployId,String ployName,Integer customerId,Integer positionId,Integer ruleId,Integer operationId,Date createTime,Date modifyTime,String state,String description)
	{
		this.ployId=ployId;
		this.ployName=ployName;
		this.customerId=customerId;
		this.positionId=positionId;
		this.ruleId=ruleId;
		this.operationId=operationId;
		this.createTime=createTime;
		this.modifyTime=modifyTime;
		this.state=state;
		this.description=description;
	}
	
	public PloyBackup(Integer ployId,Integer channelGroupType,String ployName,Integer customerId,Integer positionId,Integer ruleId,Integer operationId,Date createTime,Date modifyTime,String state,String description,Integer ployNumber,String defaultstart)
	{
		this.ployId=ployId;
		this.ployName=ployName;
		this.channelGroupType = channelGroupType;
		this.customerId=customerId;
		this.positionId=positionId;
		this.ruleId=ruleId;
		this.operationId=operationId;
		this.createTime=createTime;
		this.modifyTime=modifyTime;
		this.state=state;
		this.description=description;
		
		this.ployNumber=ployNumber;
		this.defaultstart=defaultstart;
	}
	
	public PloyBackup(Integer ployId,String ployName,Integer customerId,Integer positionId,Integer ruleId,Integer operationId,Long operatorId, Date createTime,Date modifyTime,String state,String description,Integer ployNumber,String defaultstart)
	{
		this.ployId=ployId;
		this.ployName=ployName;
		this.customerId=customerId;
		this.positionId=positionId;
		this.ruleId=ruleId;
		this.operationId=operationId;
		this.operatorId = operatorId;
		this.createTime=createTime;
		this.modifyTime=modifyTime;
		this.state=state;
		this.description=description;
		
		this.ployNumber=ployNumber;
		this.defaultstart=defaultstart;
	}
	
	public PloyBackup(String ployName)
	{
		this.ployName=ployName;
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

	public Integer getDelflag() {
		return delflag;
	}

	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDefaultstart() {
		return defaultstart;
	}

	public void setDefaultstart(String defaultstart) {
		this.defaultstart = defaultstart;
	}
	
	

	public Integer getChannelGroupType() {
		return channelGroupType;
	}

	public void setChannelGroupType(Integer channelGroupType) {
		this.channelGroupType = channelGroupType;
	}

	public String getUserIndustrys() {
		return userIndustrys;
	}

	public void setUserIndustrys(String userIndustrys) {
		this.userIndustrys = userIndustrys;
	}

	public String getUserLevels() {
		return userLevels;
	}

	public void setUserLevels(String userLevels) {
		this.userLevels = userLevels;
	}

	public String getTvnNumber() {
		return tvnNumber;
	}

	public void setTvnNumber(String tvnNumber) {
		this.tvnNumber = tvnNumber;
	}
	

	public boolean isAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(boolean auditFlag) {
		this.auditFlag = auditFlag;
	}

	/**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(id)) ? "ID:" + id + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(ployName)) ? "策略名称:" + ployName + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(ployId)) ? "策略ID:" + ployId + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(positionId)) ? "广告位ID:" + positionId + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(startTime)) ? "开始日期:" + startTime+ "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(endTime)) ? "结束日期:" + endTime+ "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(auditOption)) ? "审核意见:" + auditOption+ "," : "");
        
        if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }
}
