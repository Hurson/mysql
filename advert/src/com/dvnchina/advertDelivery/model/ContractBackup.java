package com.dvnchina.advertDelivery.model;

import java.util.Date;

import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.Transform;

/**
 * 合同维护表相关 -- T_CONTRACT_BACKUP
 * 
 * @author lester
 * 
 */
public class ContractBackup extends ContractRun {

	private static final long serialVersionUID = 1834760527671600647L;
	/**
	 * 审核人 255 AUDIT_TAFF
	 */
	private String auditTaff;
	/**
	 * 审核意见 255 EXAMINATION_OPINIONS
	 */
	private String examinationOpinions;
	/**
	 * 审核时间 date AUDIT_DATE
	 */
	private Date auditDate;
	
    public ContractBackup(){}
	
	public ContractBackup(Integer id,String contractName,
			String contractNumber
	        ,String contractCode,
			Integer customerId,String submitUnits,
			String financialInformation,String approvalCode,
			Date effectiveStartDate,Date effectiveEndDate,Integer status,
			Date approvalStartDate,Date approvalEndDate,String advertisersName,String contractDesc,String examinationOpinions,Date createTime,Integer operatorId)
	{	
	
		this.setId(id);
		this.setContractName(contractName);
		this.setContractNumber(contractNumber);
		this.setContractCode(contractCode);
		this.setCustomerId(customerId);
		this.setSubmitUnits(submitUnits);
		this.setFinancialInformation(financialInformation);
		this.setApprovalCode(approvalCode);
		this.setEffectiveStartDate(effectiveStartDate);
		this.setEffectiveEndDate(effectiveEndDate);
		this.setEffectiveStartDateShow(effectiveStartDate.toString().substring(0,10));
		this.setEffectiveEndDateShow(effectiveEndDate.toString().substring(0, 10));
		this.setStatus(status);
		this.setApprovalStartDate(approvalStartDate);
		this.setApprovalEndDate(approvalEndDate);
		this.setCustomerName(advertisersName);
		this.setContractDesc(contractDesc);
		this.setExaminationOpinions(examinationOpinions);
	    this.setCreateTime(createTime);
	    this.setOperatorId(operatorId);
	}

	public ContractBackup(Integer id,String contractName,
            String contractNumber
            ,String contractCode,
            Integer customerId,String submitUnits,
            String financialInformation,String approvalCode,
            Date effectiveStartDate,Date effectiveEndDate,Integer status,String examinationOpinions)
    {   
    
        this.setId(id);
        this.setContractName(contractName);
        this.setContractNumber(contractNumber);
        this.setContractCode(contractCode);
        this.setCustomerId(customerId);
        this.setSubmitUnits(submitUnits);
        this.setFinancialInformation(financialInformation);
        this.setApprovalCode(approvalCode);
        this.setEffectiveStartDate(effectiveStartDate);
        this.setEffectiveEndDate(effectiveEndDate);
        this.setEffectiveStartDateShow(effectiveStartDate.toString().substring(0,10));
        this.setEffectiveEndDateShow(effectiveEndDate.toString().substring(0, 10));
        this.setStatus(status);
        this.setExaminationOpinions(examinationOpinions);
    
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
	
}
