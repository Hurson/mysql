package com.dvnchina.advertDelivery.bean.contract;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 合同查询时收集合同表达数据
 * @author lester
 *
 */
public class ContractQueryBean {
	private String customerName;
	private String positionName;
	private Date effectiveStartDate;
	private Date effectiveEndDate;
	private String effectiveStartDateStr;
    private String effectiveEndDateStr;
	private Integer positionTypeId;
	private String positionTypeName;
	private Integer status;
	private String contractName;
	private String customerids;

	public String getPositionTypeName() {
		return positionTypeName;
	}

	public void setPositionTypeName(String positionTypeName) {
		this.positionTypeName = positionTypeName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	public Integer getPositionTypeId() {
		return positionTypeId;
	}

	public void setPositionTypeId(Integer positionTypeId) {
		this.positionTypeId = positionTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

    public String getCustomerids() {
        return customerids;
    }

    public void setCustomerids(String customerids) {
        this.customerids = customerids;
    }

    public String getEffectiveStartDateStr() {
        
        if(effectiveStartDate!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            effectiveStartDateStr = sdf.format(effectiveStartDate);
        }
        return effectiveStartDateStr;
    }

    public void setEffectiveStartDateStr(String effectiveStartDateStr) {
        this.effectiveStartDateStr = effectiveStartDateStr;
    }

    public String getEffectiveEndDateStr() {
        if(effectiveEndDate!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            effectiveEndDateStr = sdf.format(effectiveEndDate);
        }
        
        return effectiveEndDateStr;
    }

    public void setEffectiveEndDateStr(String effectiveEndDateStr) {
        this.effectiveEndDateStr = effectiveEndDateStr;
    }
	

}
