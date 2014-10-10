package com.dvnchina.advertDelivery.action;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.dvnchina.advertDelivery.bean.contract.ContractContractADRelation;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.constant.LoginConstant;
import com.dvnchina.advertDelivery.constant.PageConstant;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.service.ContractBackupService;
import com.dvnchina.advertDelivery.service.ContractRunService;
import com.dvnchina.advertDelivery.utils.CookieUtils;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.utils.page.PageUtils;

public class ContractAction extends BaseActionSupport<Object>{
	
	private static final long serialVersionUID = -3666982468062423696L;
	
	private Logger logger = Logger.getLogger(ContractAction.class);
	
	private ContractBackup contract;
	/**
	 * 接受来自页面中的ajax请求参数
	 */
	private String contractParam;
	/**
	 * 修改前，更新记录时用于修改前后数据比对
	 */
	private String contractParamBefore;
	/**
	 * 用于收集比较结果
	 */
	private String comparedFormResult;
	/**
	 * 合同和中间表主键
	 */
	private String contractAd;
	
	private ContractBackupService contractBackupService;
	
	private ContractRunService contractRunService;
	
	private ContractQueryBean object;
	
	/**
	 * 获取用户id
	 * */
	private Integer getOpId() {
		String userId = CookieUtils.getCookieValueByKey(getRequest(),
				LoginConstant.COOKIE_USER_ID);
		return new Integer(userId);
	}

	/**
	 * 获取用户名
	 * */
	private String getUserName() {
		String userName = CookieUtils.getCookieValueByKey(getRequest(),
				LoginConstant.COOKIE_USER_NAME);
		return userName;
	}

	public ContractQueryBean getObject() {
		return object;
	}

	public void setObject(ContractQueryBean object) {
		this.object = object;
	}

	public ContractBackup getContract() {
		return contract;
	}

	public void setContract(ContractBackup contract) {
		this.contract = contract;
	}

	public ContractBackupService getContractBackupService() {
		return contractBackupService;
	}

	public void setContractBackupService(ContractBackupService contractBackupService) {
		this.contractBackupService = contractBackupService;
	}

	public ContractRunService getContractRunService() {
		return contractRunService;
	}

	public void setContractRunService(ContractRunService contractRunService) {
		this.contractRunService = contractRunService;
	}

	/**
	 * 保存或更新合同信息
	 * @return
	 */
	public String save(){
		boolean flag = false;
		String message = "";
		StringBuffer url = new StringBuffer();
		ContractBackup contractBackup = null;
		Map<String,String> resultMap = null;
		Map responseResult = new HashMap();
		
		try {
			contractParam = URLDecoder.decode(contractParam, "UTF-8");
			contractBackup = parseJson(contractParam);
			if (contractBackup!=null) {
				contractBackup.setOperatorId(getUserId());
				resultMap = contractBackupService.saveContractBackup(contractBackup);
			}
		} catch (Exception e) {
			resultMap.put("flag","error");
			logger.error("参数解析出现异常",e);
		}
		
		responseResult.put("originalData",contractParam);
		responseResult.put("handleResult",resultMap);
		String json = JSON.toJSONString(responseResult);
		this.renderJson(json);
		return null;
	}
	
	/**
	 * 保存或更新合同信息
	 * @return
	 */
	public String update(){
		boolean flag = false;
		String message = "";
		StringBuffer url = new StringBuffer();
		
		Map<String,String> resultMap = null;
		Map responseResult = new HashMap();
		
		try {
			contractParam = URLDecoder.decode(contractParam, "UTF-8");
			contractParamBefore = URLDecoder.decode(contractParamBefore, "UTF-8");
			comparedFormResult = URLDecoder.decode(comparedFormResult, "UTF-8");
			contractAd = URLDecoder.decode(contractAd, "UTF-8");
			resultMap=contractBackupService.updateContractBackup(contractParam, contractParamBefore, comparedFormResult,contractAd,this.getUserId());
			//resultMap = contractBackupService.saveContractBackup(contractBackup);
		} catch (Exception e) {
			resultMap.put("flag","error");
			logger.error("参数解析出现异常",e);
		}
		
		/*responseResult.put("originalData",contractParam);*/
		responseResult.put("handleResult",resultMap);
		String json = JSON.toJSONString(responseResult);
		logger.info(json);
		this.renderJson(json);
		return null;
	}
	
	/**
	 * 查询合同信息
	 * @return
	 */
	public String queryPage(){
		
		HttpServletRequest request = this.getRequest();
		//清空request中原有记录
		request.setAttribute("advertPositionType",null);
		
		String currentPage=request.getParameter("currentPage");
		
		Map conditionMap = new HashMap();
		if (object!=null) {
			//广告商名称
			String customerName = object.getCustomerName();
			if (StringUtils.isNotBlank(customerName)) {
				request.setAttribute("customerName", customerName);
				conditionMap.put("Advertisers_Name", customerName);
			}
			
			String contractName = object.getContractName();
			if (StringUtils.isNotBlank(contractName)) {
				request.setAttribute("contractName", contractName);
				conditionMap.put("CONTRACT_NAME", contractName);
			}
			
			//开始时间
			Date effectiveStartDate = object.getEffectiveStartDate();
			if (effectiveStartDate!=null) {
				request.setAttribute("effectiveStartDate", effectiveStartDate);
				conditionMap.put("EFFECTIVE_START_DATE", effectiveStartDate);
			}
			//结束时间
			Date effectiveEndDate = object.getEffectiveEndDate();
			if (effectiveEndDate!=null) {
				request.setAttribute("effectiveEndDate", effectiveEndDate);
				conditionMap.put("EFFECTIVE_END_DATE", effectiveEndDate);
			}
			request.setAttribute("object", object);
		}
		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;
		
		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}

		try {
			count = contractBackupService.queryCount(conditionMap);
			List<ContractBackup> contractBackupList = contractBackupService.page(
					conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE, pageNB
							* PageConstant.PAGE_SIZE);
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,contractBackupList);
			request.setAttribute("contractBackupList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询合同信息
	 * @return
	 */
	public String approvalListPage(){
		
		HttpServletRequest request = this.getRequest();
		//清空request中原有记录
		request.setAttribute("advertPositionType",null);
		
		String currentPage=request.getParameter("currentPage");
		
		String saveAgainFlag = request.getParameter("flag");
		
		Map conditionMap = new HashMap();
		
		int count = 0;
		int pageNB = 1;
		PageBean pageBean = null;
		int pageSize = PageConstant.PAGE_SIZE;
		
		if (object!=null) {
			//广告商名称
			String customerName = object.getCustomerName();
			if (StringUtils.isNotBlank(customerName)) {
				request.setAttribute("customerName", customerName);
				conditionMap.put("Advertisers_Name", customerName);
			}
			//广告位名称
			String positionName = object.getPositionName();
			if (StringUtils.isNotBlank(positionName)) {
				request.setAttribute("positionName", positionName);
				conditionMap.put("POSITION_NAME", positionName);
			}
			//开始时间
			Date effectiveStartDate = object.getEffectiveStartDate();
			if (effectiveStartDate!=null) {
				request.setAttribute("effectiveStartDate", effectiveStartDate);
				conditionMap.put("EFFECTIVE_START_DATE", effectiveStartDate);
			}
			//结束时间
			Date effectiveEndDate = object.getEffectiveEndDate();
			if (effectiveEndDate!=null) {
				request.setAttribute("effectiveEndDate", effectiveEndDate);
				conditionMap.put("EFFECTIVE_END_DATE", effectiveEndDate);
			}
			//广告位类型
			String positionTypeName = object.getPositionTypeName();
			if (StringUtils.isNotBlank(positionTypeName)) {
				conditionMap.put("POSITION_TYPE_NAME", positionTypeName);
			}
			//广告位状态
			Integer status = object.getStatus();
			if (status!=null&&status.intValue()!=-1) {
				conditionMap.put("STATUS", status);
			}else{
				conditionMap.put("STATUS",Constant.CONTRACT_AUDIT_STATUS_WAIT);
			}
			request.setAttribute("object", object);
		}else{
			conditionMap.put("STATUS",Constant.CONTRACT_AUDIT_STATUS_WAIT);
		}
		
		if (StringUtils.isNotBlank(currentPage)) {
			pageNB = Integer.valueOf(currentPage);
		}

		try {
			
			count = contractBackupService.queryCount(conditionMap);
			List<ContractBackup> contractBackupList = contractBackupService.page(
					conditionMap, (pageNB - 1) * PageConstant.PAGE_SIZE, pageNB
							* PageConstant.PAGE_SIZE);
			pageBean = PageUtils.getPageBean2(pageNB, pageSize, count,contractBackupList);
			request.setAttribute("contractBackupList", pageBean.getList());
			request.setAttribute("page", pageBean);
		} catch (Exception e) {
			logger.error("获取数据列表时出现异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 保存或更新合同信息
	 * @return
	 */
	public String approvalContract(){
		boolean flag = false;
		String message = "";
		StringBuffer url = new StringBuffer();
		
		Map<String,String> resultMap = new HashMap();
		Map responseResult = new HashMap();
		
		try {
			contractParam = URLDecoder.decode(contractParam, "UTF-8");
			resultMap=contractBackupService.updateApprovalContractContractBackup(contractParam,this.getUserId(),this.getUserName());
			//resultMap = contractBackupService.saveContractBackup(contractBackup);
		} catch (Exception e) {
			resultMap.put("flag","error");
			logger.error("参数解析出现异常",e);
		}
		
		responseResult.put("handleResult",resultMap);
		String json = JSON.toJSONString(responseResult);
		logger.info(json);
		this.renderJson(json);
		return null;
	}
	
	/**
	 * 删除合同
	 * @return
	 */
	public String remove(){
		boolean flag = false;
		HttpServletRequest request = this.getRequest();
		String ids = "";
		String[] idArray = null; 
		
		try {
			//flag = contractBackupService.removeContractBackup(contract.getId());
			ids = request.getParameter("ids");
			if(StringUtils.isNotBlank(ids)){
				idArray = ids.split(",");
				if(idArray!=null&&idArray.length>0){
					for (String id : idArray) {
						if(StringUtils.isNotBlank(id)){
							flag = contractBackupService.removeContractBackup(Integer.valueOf(id));
							logger.info("删除主键id："+id+"结果："+flag);
						}
					}
				}
			}
			logger.info("删除结果"+flag);
		} catch (Exception e) {
			logger.error("删除数据时出现异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 跳转到更新合同页面
	 * @return
	 */
	public String updatePage(){
		
		HttpServletRequest request = this.getRequest();
		int contractStatus ;
		ContractBackup contractInDB = null;
		String positionList = "";
		String contractContractADRelationListStr="";
		List<ContractContractADRelation> contractContractADRelationList = null;
		try {
			//获取表单中的相关数据
			if(contract!=null){
				contractStatus=contract.getStatus().intValue();
				//待审核状态 从 备份表中获取数据
				if(Constant.CONTRACT_AUDIT_STATUS_WAIT==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
					//审核通过状态	 从 运行期表中获取数据
				}else if(Constant.CONTRACT_AUDIT_STATUS_PASS==contractStatus){
					//contractInDB=contractRunService.getContractByContractId(contract.getId());
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
				//下线状态
				}else if(Constant.CONTRACT_AUDIT_STATUS_NO_PASS==contractStatus){
					//contractInDB=contractRunService.getContractByContractId(contract.getId());
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
				//下线状态
				}else if(Constant.CONTRACT_AUDIT_STATUS_OFFLINE==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
				}
				request.setAttribute("contractAd",contractContractADRelationListStr);
				request.setAttribute("contract",contractInDB);
				request.setAttribute("positionList",positionList);
				request.setAttribute("updateFlag","update");
				request.setAttribute("saveOrUpdateFlag","update");
			}else{
				log.info("获取信息失败");
			}
		} catch (Exception e) {
			request.setAttribute("formEnable","false");
			logger.error("获取合同信息时出现异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 跳转到审核合同进入页面
	 * @return
	 */
	public String approvalContractPage(){
		
		HttpServletRequest request = this.getRequest();
		int contractStatus ;
		ContractBackup contractInDB = null;
		String positionList = "";
		String contractContractADRelationListStr="";
		List<ContractContractADRelation> contractContractADRelationList = null;
		try {
			//获取表单中的相关数据
			if(contract!=null){
				contractStatus=contract.getStatus().intValue();
				//待审核状态 从 备份表中获取数据
				if(Constant.CONTRACT_AUDIT_STATUS_WAIT==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
					//审核通过状态	 从 运行期表中获取数据
				}else if(Constant.CONTRACT_AUDIT_STATUS_PASS==contractStatus){
					//contractInDB=contractRunService.getContractByContractId(contract.getId());
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
				//下线状态
				}else if(Constant.CONTRACT_AUDIT_STATUS_OFFLINE==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					contractContractADRelationList = contractBackupService.getContractContractADRelation(contract.getId());
					positionList=JSON.toJSONString(contractInDB);
					contractContractADRelationListStr=JSON.toJSONString(contractContractADRelationList);
				}
				request.setAttribute("contractAd",contractContractADRelationListStr);
				request.setAttribute("contract",contractInDB);
				request.setAttribute("positionList",positionList);
				request.setAttribute("updateFlag","update");
				request.setAttribute("formEnable", "false");
				
			}else{
				log.info("获取信息失败");
			}
		} catch (Exception e) {
			logger.error("获取合同信息时出现异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 跳转入增加合同页面中
	 * @return
	 */
	public String addPage(){
		this.getRequest().setAttribute("saveOrUpdateFlag","save");
		return SUCCESS;
	}
	
	/**
	 * 查看合同详情
	 * @return
	 */
	public String viewPage(){
		HttpServletRequest request = this.getRequest();
		int contractStatus ;
		ContractBackup contractInDB = null;
		String positionList = "";
		try {
			//获取表单中的相关数据
			if(contract!=null){
				contractStatus=contract.getStatus().intValue();
				//待审核状态 从 备份表中获取数据
				if(Constant.CONTRACT_AUDIT_STATUS_WAIT==contractStatus){
					
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					positionList=JSON.toJSONString(contractInDB);
				//审核通过状态	 从 运行期表中获取数据
				}else if(Constant.CONTRACT_AUDIT_STATUS_PASS==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					positionList=JSON.toJSONString(contractInDB);
				//下线状态
				}else if(Constant.CONTRACT_AUDIT_STATUS_NO_PASS==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					positionList=JSON.toJSONString(contractInDB);
				//下线状态
				}else if(Constant.CONTRACT_AUDIT_STATUS_OFFLINE==contractStatus){
					contractInDB=contractBackupService.getContractByContractId(contract.getId());
					if(contractInDB!=null){
						contractInDB.setId(contract.getId());
					}
					positionList=JSON.toJSONString(contractInDB);
				}
				request.setAttribute("formEnable","false");
				request.setAttribute("contract",contractInDB);
				request.setAttribute("positionList",positionList);
				request.setAttribute("updateFlag","update");
			}else{
				log.info("获取信息失败");
			}
		} catch (Exception e) {
			logger.error("获取合同信息时出现异常",e);
		}
		return SUCCESS;
	}
	
	public static ContractBackup  parseJson(String jsonString){
		ContractBackup contract = null;
		
		if(StringUtils.isNotBlank(jsonString)){
			contract=JSON.toJavaObject(JSON.parseObject(jsonString), ContractBackup.class);
		}
		return contract;
	}
	
	public static void main(String[] args) {
		String a = "{\"id\":\"\",\"contractNumber\":\"00003\",\"contractCode\":\"C00003\",\"customerId\":\"88\",\"customerName\":\"方法\",\"contractName\":\"C00003名称\",\"submitUnits\":\"C00003送审单位\",\"financialInformation\":\"C00003合同金额\",\"approvalCode\":\"C00003审批文号\",\"metarialPath\":\"\",\"effectiveStartDate\":1363589846000,\"effectiveEndDate\":1363589849000,\"approvalStartDate\":1363589851000,\"approvalEndDate\":1363589854000,\"effectiveStartDateShow\":\"2013-03-18 14:57:26\",\"effectiveEndDateShow\":\"2013-03-18 14:57:29\",\"approvalStartDateShow\":\"2013-03-18 14:57:31\",\"approvalEndDateShow\":\"2013-03-18 14:57:34\",\"otherContent\":\"C00003其他内容\",\"status\":\"\",\"contractDesc\":\"C00003备注描述\",\"bindingPosition\":[{\"id\":\"99\",\"characteristicIdentification\":\"hd_0001\",\"positionName\":\"名称\",\"categoryId\":\"\",\"positionTypeId\":90,\"positionTypeName\":\"类型一\",\"deliveryPlatform\":\"UNT\",\"textRuleId\":\"134\",\"videoRuleId\":\"61\",\"imageRuleId\":\"81\",\"questionRuleId\":\"134\",\"isLoop\":\"1\",\"deliveryMode\":\"0\",\"isHd\":\"1\",\"isAdd\":\"0\",\"materialNumber\":\"0\",\"coordinate\":\"220,350\",\"price\":\"0001价格\",\"discount\":\"0.1元\",\"backgroundPath\":\"images/position/1361090090735.jpg\",\"startTime\":\"01:00:00\",\"endTime\":\"23:59:59\",\"validStartDateShow\":\"2013-03-18 14:56:29\",\"validEndDateShow\":\"2013-03-18 14:56:31\",\"validStartDate\":1363589789000,\"validEndDate\":1363589791000,\"chooseMarketRules\":\"12,\",\"marketRules\":[{\"id\":12,\"ruleName\":\"a12\",\"startTime\":0,\"endTime\":0,\"positionName\":\"名称\",\"areaName\":\"安阳\",\"channelName\":\"摄影\",\"state\":1,\"chooseState\":\"false\"}],\"chooseMarketRulesElement\":\"#alreadyChooseRule0\",\"currentIndex\":0,\"chooseState\":\"false\"},{\"id\":\"1\",\"characteristicIdentification\":\"sd_0001\",\"positionName\":\"名称1\",\"categoryId\":\"\",\"positionTypeId\":90,\"positionTypeName\":\"类型一\",\"deliveryPlatform\":\"UNT\",\"textRuleId\":\"134\",\"videoRuleId\":\"61\",\"imageRuleId\":\"81\",\"questionRuleId\":\"134\",\"isLoop\":\"1\",\"deliveryMode\":\"0\",\"isHd\":\"1\",\"isAdd\":\"0\",\"materialNumber\":\"0\",\"coordinate\":\"220,350\",\"price\":\"0001价格\",\"discount\":\"0.1元\",\"backgroundPath\":\"images/position/1361090090735.jpg\",\"startTime\":\"01:00:00\",\"endTime\":\"23:59:59\",\"validStartDateShow\":\"2013-03-18 14:56:33\",\"validEndDateShow\":\"2013-03-18 14:56:35\",\"validStartDate\":1363589793000,\"validEndDate\":1363589795000,\"chooseMarketRules\":\"7,\",\"marketRules\":[{\"id\":7,\"ruleName\":\"a7\",\"startTime\":0,\"endTime\":0,\"positionName\":\"名称1\",\"areaName\":\"安阳\",\"channelName\":\"摄影\",\"state\":1,\"chooseState\":\"false\"}],\"chooseMarketRulesElement\":\"#alreadyChooseRule1\",\"currentIndex\":1,\"chooseState\":\"false\"}]}";
		
	}

	public String getContractParam() {
		return contractParam;
	}

	public void setContractParam(String contractParam) {
		this.contractParam = contractParam;
	}

	public String getContractParamBefore() {
		return contractParamBefore;
	}

	public void setContractParamBefore(String contractParamBefore) {
		this.contractParamBefore = contractParamBefore;
	}

	public String getComparedFormResult() {
		return comparedFormResult;
	}

	public void setComparedFormResult(String comparedFormResult) {
		this.comparedFormResult = comparedFormResult;
	}

	public String getContractAd() {
		return contractAd;
	}

	public void setContractAd(String contractAd) {
		this.contractAd = contractAd;
	}
	
	
}
