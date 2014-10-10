package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.Transform;

/**
 * 合同运行期表相关 -->	T_CONTRACT
 * @author lester
 *
 */
public class ContractRun implements Serializable{
	
	private static final long serialVersionUID = 5164866647343217822L;
	/**
	 * 合同主键  ID
	 */
	private Integer id;
	/**
	 * 合同编号 255  CONTRACT_NUMBER
	 */
	private String contractNumber ;
	/**
	 * 合同代码 255 CONTRACT_CODE 
	 */
	private String contractCode;
	/**
	 * 广告商ID 外健 number 10 CUSTOMER_ID
	 */
	private Integer customerId;
	/**
	 * 合同名称 255 CONTRACT_NAME 
	 */
	private String contractName;
	/**
	 * 合同有效开始时间 date EFFECTIVE_START_DATE
	 */
	private Date effectiveStartDate;
	/**
	 * 合同有效结束时间 date EFFECTIVE_END_DATE
	 */
	private Date   effectiveEndDate;
	/**
	 * 送审单位 255 SUBMIT_UNITS
	 */
	private String submitUnits;
	/**
	 * 合同金额 255 FINANCIAL_INFORMATION
	 */
	private String financialInformation;
	/**
	 * 审批文号  255 APPROVAL_CODE
	 */
	private String approvalCode;
	/**
	 * 审批文号有效期的开始时间 date  APPROVAL_START_DATE
	 */
	private Date   approvalStartDate;
	/**
	 * 审批文号有效期的结束时间 date APPROVAL_END_DATE
	 */
	private Date   approvalEndDate;
	/**
	 * 系统生成 素材存储路径 255  METARIAL_PATH
	 */
	private String metarialPath;
	/**
	 * 状态 number 2   0 待审核   1 审核    2 下线状态（定时任务自动检测） STATUS
	 */
	private Integer status;
	/**
	 * 创建时间 date  CREATE_TIME
	 */
	private Date createTime;
	/**
	 * 操作人ID number 10 OPERATOR_ID
	 */
	private Integer operatorId;	
	/**
	 * 其他内容 255 OTHER_CONTENT
	 */
	private String otherContent;
	/**
	 * 合同描述 255 DESC
	 */
	private String contractDesc;
	/**
	 * 广告商名称 只作为前台查询条件，不入库
	 */
	private String customerName;
	/**
	 * 广告位类型 只作为前台查询条件，不入库
	 */
	private String positionType;
	/**
	 * 已绑定广告位【合同信息维护时使用】
	 */
	private List<AdvertPosition> bindingPosition;
	
	private String effectiveStartDateShow;
	
	private String effectiveEndDateShow;
	
	private String approvalStartDateShow;
	
	private String approvalEndDateShow;
	/**
	 * 是否修改标记  0 默认 1 新增 2 删除 3 修改
	 */
	private String modify;
	

    public ContractRun(){}
	
	public ContractRun(Integer id,String contractName,String contractNumber,String contractCode,Integer customerId,String submitUnits,String financialInformation,String approvalCode,Date effectiveStartDate,Date effectiveEndDate,Integer status)
	{
		
		this.id=id;
		this.contractName=contractName;
		this.contractNumber=contractNumber;
		this.contractCode=contractCode;
		this.customerId=customerId;
		this.submitUnits=submitUnits;
		this.financialInformation=financialInformation;
		this.approvalCode=approvalCode;
		this.effectiveStartDate=effectiveStartDate;
		this.effectiveEndDate=effectiveEndDate;
		this.status=status;

	}
	

	
	//广告商级别
	private Integer customerLevel;
	//注册资金
	private Integer registerFinancing;
	//注册地
	private String registerAddress;
	//营业面积 
	private Integer businessArea;
	

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public String getEffectiveStartDateShow() {
		return effectiveStartDateShow;
	}

	public void setEffectiveStartDateShow(String effectiveStartDateShow) {
		this.effectiveStartDateShow = effectiveStartDateShow;
	}

	public String getEffectiveEndDateShow() {
		return effectiveEndDateShow;
	}

	public void setEffectiveEndDateShow(String effectiveEndDateShow) {
		this.effectiveEndDateShow = effectiveEndDateShow;
	}

	public String getApprovalStartDateShow() {
		return approvalStartDateShow;
	}

	public void setApprovalStartDateShow(String approvalStartDateShow) {
		this.approvalStartDateShow = approvalStartDateShow;
	}

	public String getApprovalEndDateShow() {
		return approvalEndDateShow;
	}

	public void setApprovalEndDateShow(String approvalEndDateShow) {
		this.approvalEndDateShow = approvalEndDateShow;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
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

	public String getSubmitUnits() {
		return submitUnits;
	}

	public void setSubmitUnits(String submitUnits) {
		this.submitUnits = submitUnits;
	}

	public String getFinancialInformation() {
		return financialInformation;
	}

	public void setFinancialInformation(String financialInformation) {
		this.financialInformation = financialInformation;
	}

	public String getApprovalCode() {
		return approvalCode;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	public Date getApprovalStartDate() {
		return approvalStartDate;
	}

	public void setApprovalStartDate(Date approvalStartDate) {
		this.approvalStartDate = approvalStartDate;
	}

	public Date getApprovalEndDate() {
		return approvalEndDate;
	}

	public void setApprovalEndDate(Date approvalEndDate) {
		this.approvalEndDate = approvalEndDate;
	}

	public String getMetarialPath() {
		return metarialPath;
	}

	public void setMetarialPath(String metarialPath) {
		this.metarialPath = metarialPath;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getOtherContent() {
		return otherContent;
	}

	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}

	public String getContractDesc() {
		return contractDesc;
	}

	public void setContractDesc(String contractDesc) {
		this.contractDesc = contractDesc;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<AdvertPosition> getBindingPosition() {
		return bindingPosition;
	}

	public void setBindingPosition(List<AdvertPosition> bindingPosition) {
		this.bindingPosition = bindingPosition;
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
	/**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(id)) ? "ID:" + id + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(contractCode)) ? "合同号:" + contractCode + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(contractNumber)) ? "合同编码:" + contractNumber + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(financialInformation)) ? "合同金额:" + financialInformation + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(approvalCode)) ? "审批文号:" + approvalCode + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(effectiveStartDate)) ? "开始日期:" + Transform.date2String(effectiveStartDate, "yyyy-MM-dd")+ "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(effectiveEndDate)) ? "结束日期:" + Transform.date2String(effectiveEndDate, "yyyy-MM-dd")+ "," : "");
        if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }
}
