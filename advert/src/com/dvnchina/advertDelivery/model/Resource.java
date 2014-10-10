package com.dvnchina.advertDelivery.model;

import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.Transform;

public class Resource extends CommonObject{
	/**
	 * 资产名称
	 */
	private String    resourceName;
	/**
	 * 资产类型
	 */
	private Integer   resourceType;
	
	private Integer   resourceTypeTemp;//修改时临时变量
	/**
	 * 资产ID
	 */
	private Integer   resourceId;
	/**
	 * 资产描述
	 */
	private String    resourceDesc;
	/**
	 * 所属广告商
	 */
	private Integer   customerId;
	
	private String   customerName;
	/**
	 * 所属内容分类
	 */
	private Integer   categoryId;
	/**
	 * 所属合同号
	 */
	private Integer   contractId;
	
	private String   contractName;
	
	/**
	 * 有效期开始时间
	 */
	private Date      startTime;
	/**
	 * 有效期结束时间
	 */
	private Date      endTime;
	/**
	 * 广告位ID
	 */
	private Integer   advertPositionId;
	/**
	 * 审核意见 
	 */
	private String    examinationOpintions;
	/**
	 * 状态
	 */
	private Character state;
	
	/**
	 * 状态 用于页面展现 ，不入库
	 * 
	 */
	private String stateStr;
	
	/**
	 * 创建时间
	 */
	private Date      createTime;
	/**
	 * 修改时间
	 */
	private Date      modifyTime;
	/**
	 * 操作人员ID
	 */
	private Integer   operationId;
	
	/**
	 * 审核时间
	 */
	private Date auditDate;
	
	
	/**
	 * 审核人
	 */
	private String auditTaff;
	
	/**
	 * 广告位名称 用于页面展示 不入库
	 * 
	 */
	private String advertPositionName;
	
	//创建时间 前
	private Date  createTimeA;
	//创建时间 后
	private Date  createTimeB;
	
	private String metaState;
	
	
	private Integer userId;
	
	
	private List<Integer> cIds;
	
	private String keyWords;
	
	/** 订单ID*/
	private Integer used;
	
	/** 是否默认素材*/
	private Integer isDefault;
	/**
     * 子广告位ID集合,素材查询使用
     */
    private String positionIds;
    
	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	public Resource(){}

	public Resource(String resourceName, Integer resourceType,
			Integer resourceId, String resourceDesc, Integer customerId,
			Integer categoryId, Integer contractId, Date startTime,
			Date endTime, Integer advertPositionId,
			String examinationOpintions, Character state, Date createTime,
			Date modifyTime, Integer operationId, Date auditDate,
			String auditTaff, Date createTimeA, Date createTimeB,
			String metaState) {
		super();
		this.resourceName = resourceName;
		this.resourceType = resourceType;
		this.resourceId = resourceId;
		this.resourceDesc = resourceDesc;
		this.customerId = customerId;
		this.categoryId = categoryId;
		this.contractId = contractId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.advertPositionId = advertPositionId;
		this.examinationOpintions = examinationOpintions;
		this.state = state;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.operationId = operationId;
		this.auditDate = auditDate;
		this.auditTaff = auditTaff;
		this.createTimeA = createTimeA;
		this.createTimeB = createTimeB;
		this.metaState = metaState;
	}
	public Resource(
	        Integer id,
	        Integer resourceId,
	        String resourceName, 
	        Integer resourceType,
	        Integer categoryId,
	        Date startTime,
	        Date endTime,
	        Character state, 
	        Date createTime,
	        String resourceDesc,
	        String keyWords,
	        String examinationOpintions,
	        Integer contractId,
	        String contractName,
	        Integer advertPositionId,
	        String advertPositionName) {
	    this.setId(id);
	    this.resourceId=resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.categoryId = categoryId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.createTime = createTime;
        this.resourceDesc=resourceDesc;
        this.keyWords=keyWords;
        this.examinationOpintions=examinationOpintions;
        this.contractId=contractId;
        this.contractName = contractName;      
        this.advertPositionId=advertPositionId;
        this.advertPositionName = advertPositionName;
    }
	public Resource(
	        Integer id,
	        Integer resourceId,
	        String resourceName, 
	        Integer resourceType,
	        Integer categoryId,
	        Date startTime,
	        Date endTime,
	        Character state, 
	        Date createTime,
	        String resourceDesc,
	        String keyWords,
	        String examinationOpintions,
	        //Integer contractId,
	        //String contractName,
	        Integer advertPositionId,
	        String advertPositionName,
	        Integer isDefault,
	        Integer customerId,
	        Date modifyTime,
	        String customerName) {
	    this.setId(id);
	    this.resourceId=resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.categoryId = categoryId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.createTime = createTime;
        this.resourceDesc=resourceDesc;
        this.keyWords=keyWords;
        this.examinationOpintions=examinationOpintions;
        //this.contractId=contractId;
        //this.contractName = contractName;      
        this.advertPositionId=advertPositionId;
        this.advertPositionName = advertPositionName;
        this.isDefault=isDefault;
        this.customerId=customerId;
        this.modifyTime=modifyTime;
        this.customerName=customerName;
    }
	
	public Resource(
	        Integer id,
	        Integer resourceId,
	        String resourceName, 
	        Integer resourceType,
	        Integer categoryId,
	        Date createTime,
	        String resourceDesc,
	        String keyWords,
	        Integer advertPositionId,
	        String advertPositionName,
	        Integer used) {
	    this.setId(id);
	    this.resourceId=resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.categoryId = categoryId;
        this.createTime = createTime;
        this.resourceDesc=resourceDesc;
        this.keyWords=keyWords;  
        this.advertPositionId=advertPositionId;
        this.advertPositionName = advertPositionName;
        this.used = used;
    }

	public Resource(
	        Integer id,
	        Integer resourceId,
	        String resourceName, 
	        Integer resourceType,
	        Integer categoryId,
	        Date createTime,
	        String resourceDesc,
	        String keyWords,
	        Integer advertPositionId,
	        String advertPositionName) {
	    this.setId(id);
	    this.resourceId=resourceId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.categoryId = categoryId;
        this.createTime = createTime;
        this.resourceDesc=resourceDesc;
        this.keyWords=keyWords;  
        this.advertPositionId=advertPositionId;
        this.advertPositionName = advertPositionName;
    }



	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	

	public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
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

	public Integer getAdvertPositionId() {
		return advertPositionId;
	}

	public void setAdvertPositionId(Integer advertPositionId) {
		this.advertPositionId = advertPositionId;
	}

	public String getExaminationOpintions() {
		return examinationOpintions;
	}

	public void setExaminationOpintions(String examinationOpintions) {
		this.examinationOpintions = examinationOpintions;
	}

	public Character getState() {
		return state;
	}

	public void setState(Character state) {
		this.state = state;
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

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
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

	public String getMetaState() {
		return metaState;
	}

	public void setMetaState(String metaState) {
		this.metaState = metaState;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditTaff() {
		return auditTaff;
	}

	public void setAuditTaff(String auditTaff) {
		this.auditTaff = auditTaff;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getAdvertPositionName() {
		return advertPositionName;
	}

	public void setAdvertPositionName(String advertPositionName) {
		this.advertPositionName = advertPositionName;
	}

	public String getStateStr() {
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Integer> getCIds() {
		return cIds;
	}

	public void setCIds(List<Integer> ids) {
		cIds = ids;
	}

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public Integer getResourceTypeTemp() {
        return resourceTypeTemp;
    }

    public void setResourceTypeTemp(Integer resourceTypeTemp) {
        this.resourceTypeTemp = resourceTypeTemp;
    }

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(String positionIds) {
        this.positionIds = positionIds;
    }

    /**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(resourceId)) ? "ID:" + resourceId + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(resourceName)) ? "素材名:" + resourceName + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(resourceType)) ? "素材类型:" + resourceType + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(resourceId)) ? "资产ID:" + resourceId + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(customerId)) ? "广告商:" + customerId + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(contractId)) ? "合同ID:" + contractId+ "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(advertPositionId)) ? "广告位ID:" + advertPositionId+ "," : "");
        if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }
}


