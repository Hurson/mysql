package com.dvnchina.advertDelivery.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.dvnchina.advertDelivery.bean.contract.ContractContractADRelation;
import com.dvnchina.advertDelivery.bean.contract.ContractModifyFlag;
import com.dvnchina.advertDelivery.bean.contract.MarketRuleModifyFlag;
import com.dvnchina.advertDelivery.bean.contract.PositionModifyFlag;
import com.dvnchina.advertDelivery.bean.marketingRule.MarketingRuleBean;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.dao.ContractBackupDao;
import com.dvnchina.advertDelivery.dao.ContractRunDao;
import com.dvnchina.advertDelivery.dao.CustomerDao;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.CustomerBackUp;
import com.dvnchina.advertDelivery.model.TempContract;
import com.dvnchina.advertDelivery.service.ContractBackupService;
import com.dvnchina.advertDelivery.utils.Transform;
import com.dvnchina.advertDelivery.utils.config.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;

public class ContractBackupServiceImpl implements ContractBackupService{
	
	private static Logger logger = Logger.getLogger(ContractBackupServiceImpl.class);
	
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	// 2048
	private static final String maxFileSize = config.get("advert.file.maxsize");
	// /advert/
	private static final String advertDirectory = config.get("ftp.advertDirectory");
	//  temp/
	private static final String ftpTempPath = config.get("resource.ftpTempPath");
	
	private static final String SEP = System.getProperty("file.separator");
	
	private ContractBackupDao contractBackupDao;
	
	private ContractRunDao contractRunDao;
	
	private CustomerDao customerDao;
	
	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public ContractBackupDao getContractBackupDao() {
		return contractBackupDao;
	}

	public void setContractBackupDao(ContractBackupDao contractBackupDao) {
		this.contractBackupDao = contractBackupDao;
	}

	/**
	 * SELECT * FROM t_contract_backup cb_out WHERE EXISTS (SELECT * FROM ( SELECT CB.id
	   FROM T_CONTRACT_BACKUP CB LEFT JOIN (
         SELECT TA.POSITION_NAME,
				 CAB.ID AS CABID,
				 CAB.CONTRACT_ID,  
         CAB.AD_ID AS POSITIONID,
         CAB.RULE_ID,
				 CAB.Ad_Type AS Position_Type_Id,
				 CAB.AD_TYPE_NAME POSITION_TYPE_NAME,
				 CAB.ADVERTISERS_NAME,
         TMR.ID AS mrId, 
         TMR.RULE_ID AS MARKETINGRULEID      
 		FROM T_CONTRACT_AD_BACKUP CAB INNER  JOIN T_MARKETING_RULE TMR ON CAB.RULE_ID = TMR.ID LEFT  JOIN T_ADVERTPOSITION TA ON CAB.AD_ID = TA.ID) ADM ON CB.ID=ADM.CONTRACT_ID WHERE ADM.ADVERTISERS_NAME='方法' AND ADM.POSITION_NAME='名称' AND adm.position_type_id=90 GROUP BY CB.ID) cb_in WHERE cb_in.id=cb_out.id)
	 */
	@Override
	public List<ContractBackup> page(Map condition, int start, int end) {
		StringBuffer queryContractBackup = new StringBuffer();
		List list = new ArrayList();
		String conditionStr = "";
		queryContractBackup.append("	SELECT CB_OUT.*,CB.ADVERTISERS_NAME AS customer_Name FROM T_CONTRACT_BACKUP CB_OUT,T_CUSTOMER_BACKUP CB WHERE EXISTS (SELECT * FROM ( SELECT CB.id");
		queryContractBackup.append("	FROM T_CONTRACT_BACKUP CB LEFT JOIN (");
		queryContractBackup.append("		SELECT TA.POSITION_NAME,");
		queryContractBackup.append("			CAB.ID AS CABID,");
		queryContractBackup.append("			CAB.CONTRACT_ID,  ");
		queryContractBackup.append("			CAB.AD_ID AS POSITIONID,");
		queryContractBackup.append("			CAB.RULE_ID,");
		queryContractBackup.append("			CAB.Ad_Type AS Position_Type_Id,");
		queryContractBackup.append("		    CAB.AD_TYPE_NAME POSITION_TYPE_NAME,");
		queryContractBackup.append("			CAB.ADVERTISERS_NAME,");
		queryContractBackup.append("			TMR.ID AS mrId, ");
		queryContractBackup.append("			TMR.RULE_ID AS MARKETINGRULEID");      
		queryContractBackup.append("		FROM T_CONTRACT_AD_BACKUP CAB INNER  JOIN T_MARKETING_RULE TMR ON CAB.RULE_ID = TMR.ID LEFT  JOIN T_ADVERTPOSITION TA ON CAB.AD_ID = TA.ID) ADM ON CB.ID=ADM.CONTRACT_ID");
		
		if(condition!=null&&condition.size()>0){
			if(condition.get("Advertisers_Name")!=null){
				conditionStr = " WHERE ";
				queryContractBackup.append(conditionStr).append(" Advertisers_Name ").append(" like ").append("?").append(" ");
				list.add("%"+condition.get("Advertisers_Name")+"%");
			}
			
			if(condition.get("CONTRACT_NAME")!=null){
				if(StringUtils.isBlank(conditionStr)){
					conditionStr = " WHERE ";
				}else if(conditionStr==" WHERE "){
					conditionStr = " AND ";
				}
				queryContractBackup.append(conditionStr).append(" CONTRACT_NAME ").append(" like ").append("?").append(" ");
				list.add("%"+condition.get("CONTRACT_NAME")+"%");
			}
			
		}
		
		queryContractBackup.append("		GROUP BY CB.ID) cb_in WHERE cb_in.id=cb_out.id) AND CB_OUT.CUSTOMER_ID=CB.ID ");
		conditionStr = "";
		if (condition!=null) {
			if (condition.get("STATUS") != null) {
				conditionStr = " AND ";
				queryContractBackup.append(conditionStr).append(" CB_OUT.STATUS ")
						.append("=").append("?");
				list.add(condition.get("STATUS"));
			}
			if (condition.get("EFFECTIVE_START_DATE") != null) {
				if (StringUtils.isBlank(conditionStr)) {
					conditionStr = " AND ";
				} else {
					conditionStr = " AND ";
				}
				queryContractBackup.append(conditionStr).append(
						" EFFECTIVE_START_DATE ").append(">=").append("?");
				list.add(condition.get("EFFECTIVE_START_DATE"));
			}
			if (condition.get("EFFECTIVE_END_DATE") != null) {
				if (StringUtils.isBlank(conditionStr)) {
					conditionStr = " AND ";
				} else {
					conditionStr = " AND ";
				}
				queryContractBackup.append(conditionStr).append(
						" EFFECTIVE_END_DATE ").append("<=").append("?");
				list.add(condition.get("EFFECTIVE_END_DATE"));
			}
		}
		return contractBackupDao.page(queryContractBackup.toString(),list, start, end);
	}

	@Override
	public int queryCount(Map condition) {
		StringBuffer queryContractBackup = new StringBuffer();
		List list = new ArrayList();
		String conditionStr = "";
		queryContractBackup.append("SELECT COUNT(*) FROM t_contract_backup cb_out,T_CUSTOMER_BACKUP CB WHERE EXISTS (SELECT * FROM ( SELECT CB.id");
		queryContractBackup.append("	FROM T_CONTRACT_BACKUP CB LEFT JOIN (");
		queryContractBackup.append("		SELECT TA.POSITION_NAME,");
		queryContractBackup.append("			CAB.ID AS CABID,");
		queryContractBackup.append("			CAB.CONTRACT_ID,  ");
		queryContractBackup.append("			CAB.AD_ID AS POSITIONID,");
		queryContractBackup.append("			CAB.RULE_ID,");
		queryContractBackup.append("			CAB.Ad_Type AS Position_Type_Id,");
		queryContractBackup.append("			 CAB.AD_TYPE_NAME POSITION_TYPE_NAME,");
		
		queryContractBackup.append("			CAB.ADVERTISERS_NAME,");
		queryContractBackup.append("			TMR.ID AS mrId, ");
		queryContractBackup.append("			TMR.RULE_ID AS MARKETINGRULEID");      
		queryContractBackup.append("		FROM T_CONTRACT_AD_BACKUP CAB INNER  JOIN T_MARKETING_RULE TMR ON CAB.RULE_ID = TMR.ID LEFT  JOIN T_ADVERTPOSITION TA ON CAB.AD_ID = TA.ID) ADM ON CB.ID=ADM.CONTRACT_ID");
		
		if(condition!=null&&condition.size()>0){
			if(condition.get("Advertisers_Name")!=null){
				conditionStr = " WHERE ";
				queryContractBackup.append(conditionStr).append(" Advertisers_Name ").append(" like ").append("?").append(" ");
				list.add("%"+condition.get("Advertisers_Name")+"%");
			} 
			
			if(condition.get("CONTRACT_NAME")!=null){
				
				if(StringUtils.isBlank(conditionStr)){
					conditionStr = " WHERE ";
				}else if(conditionStr==" WHERE "){
					conditionStr = " AND ";
				}
				queryContractBackup.append(conditionStr).append(" POSITION_NAME ").append(" like ").append("?").append(" ");
				list.add("%"+condition.get("CONTRACT_NAME")+"%");
			}
			
		}
		
		queryContractBackup.append("		GROUP BY CB.ID) cb_in WHERE cb_in.id=cb_out.id) AND CB_OUT.CUSTOMER_ID=CB.ID");
		
		conditionStr = "";
		if (condition!=null) {
			if (condition.get("STATUS") != null) {
				conditionStr = " AND ";
				queryContractBackup.append(conditionStr).append(" CB_OUT.STATUS ")
						.append("=").append("?");
				list.add(condition.get("STATUS"));
			}
			if (condition.get("EFFECTIVE_START_DATE") != null) {
				if (StringUtils.isBlank(conditionStr)) {
					conditionStr = " AND ";
				} else {
					conditionStr = " AND ";
				}
				queryContractBackup.append(conditionStr).append(
						" EFFECTIVE_START_DATE ").append(">=").append("?");
				list.add(condition.get("EFFECTIVE_START_DATE"));
			}
			if (condition.get("EFFECTIVE_END_DATE") != null) {
				if (StringUtils.isBlank(conditionStr)) {
					conditionStr = " AND ";
				} else {
					conditionStr = " AND ";
				}
				queryContractBackup.append(conditionStr).append(
						" EFFECTIVE_END_DATE ").append("<=").append("?");
				list.add(condition.get("EFFECTIVE_END_DATE"));
			}
		}
		System.out.println("sql-->"+queryContractBackup);
		return contractBackupDao.queryTotalCount(queryContractBackup.toString(),list);
	}

	@Override
	public Map<String,String> saveContractBackup(ContractBackup contractBackup) {
		boolean flag = false;
		List<AdvertPosition> bindingPositionList = null;
		List<MarketingRuleBean> marketingRuleList = null;
		List<ContractAD> contractADList = new ArrayList<ContractAD>();
		ContractAD contractAD = null;
		Map<String,String> resultMap = new HashMap<String,String>();
		String handleFlag = "";
		String cause = "";
		String dataType="";
		Integer dateId = null;
		Integer currentId = null;
		List<ContractBackup> contractBackupDBList = null;
		if (contractBackup!=null) {
			//1、查看该合同编号是否为空
			
			contractBackupDBList=contractBackupDao.queryContractByContractNumber(contractBackup.getContractNumber());
			if (contractBackupDBList!=null&&contractBackupDBList.size()>0) {
				handleFlag = "false";
				cause = "contractNumberExist";
				resultMap.put("flag", handleFlag);
				resultMap.put("cause", cause);
				return resultMap;
			}else{
				//2、查询当前序列
				currentId = contractBackupDao.queryCurrentSequece();
				contractBackup.setId(currentId);
				//默认为待审核状态
				contractBackup.setStatus(Constant.CONTRACT_AUDIT_STATUS_WAIT);
				//创建时间
				contractBackup.setCreateTime(new Date());
				
				//3、保存合同至合同维护表中
				setDate(contractBackup.getEffectiveEndDate());
				setDate(contractBackup.getEffectiveStartDate());
				setDate(contractBackup.getApprovalStartDate());
				setDate(contractBackup.getApprovalEndDate());
				
				Integer operationCount = contractBackupDao.saveContractBackup(contractBackup);
				if (operationCount <= 0) {
					handleFlag = "false";
					cause = "saveContractFailure";
					resultMap.put("flag", handleFlag);
					resultMap.put("cause", cause);
					return resultMap;
				}
			}
		}else{
			handleFlag = "false";
			cause = "ParamNotEnough";
			resultMap.put("flag", handleFlag);
			resultMap.put("cause", cause);
			return resultMap;
		}
		
		//4、筛选待保存记录
		if (contractBackup!=null) {
			bindingPositionList=contractBackup.getBindingPosition();
			if(bindingPositionList!=null&&bindingPositionList.size()>0){
				for (AdvertPosition advertPosition : bindingPositionList) {
					marketingRuleList = advertPosition.getMarketRules();
					
					if(marketingRuleList!=null&&marketingRuleList.size()>0){
						for (MarketingRuleBean marketingRuleInner : marketingRuleList) {
							contractAD = new ContractAD();
							contractAD.setContractId(currentId);
							contractAD.setContractCode(contractBackup.getContractCode());
							contractAD.setContractName(contractBackup.getContractName());
							contractAD.setEffectiveEndDate(contractBackup.getEffectiveEndDate());
							contractAD.setEffectiveStartDate(contractBackup.getEffectiveStartDate());
							contractAD.setCustomerId(contractBackup.getCustomerId());
							contractAD.setCustomerName(contractBackup.getCustomerName());
							
							setDate(contractAD.getEffectiveEndDate());
							setDate(contractAD.getEffectiveStartDate());
							
							contractAD.setPositionId(advertPosition.getId());
							contractAD.setPositionName(advertPosition.getPositionName());
							
							contractAD.setStartDate(advertPosition.getValidStartDate());
							contractAD.setEndDate(advertPosition.getValidEndDate());
							
							setDate(contractAD.getStartDate());
							setDate(contractAD.getEndDate());
							
							contractAD.setPositionTypeName(advertPosition.getPositionTypeName());
							contractAD.setPositionTypeId(advertPosition.getPositionTypeId());
							
							contractAD.setMarketingRuleId(marketingRuleInner.getId());
							contractAD.setMarketingRuleName(marketingRuleInner.getRuleName());
							contractADList.add(contractAD);
						}
					}else{
						logger.info("ID为"+advertPosition.getId()+"的广告位未绑定策略");
						handleFlag = "false";
						cause = "noBindingMarketingRule";
						dataType="marketingRule";
						dateId=advertPosition.getId();
						resultMap.put("flag", handleFlag);
						resultMap.put("cause", cause);
						resultMap.put("dataType", dataType);
						resultMap.put("dateId", dateId+"");
						return resultMap;
					}
				}
			}else{
				logger.info("ID为"+currentId+"的合同未绑定广告位");
				handleFlag = "false";
				cause = "noBindingAdvertPosition";
				dataType="advertPosition";
				dateId=contractBackup.getId();
				resultMap.put("flag", handleFlag);
				resultMap.put("cause", cause);
				resultMap.put("dataType", dataType);
				resultMap.put("dateId", dateId+"");
				return resultMap;
			}
		}else{
			handleFlag = "false";
			cause = "ParamNotEnough";
			resultMap.put("flag", handleFlag);
			resultMap.put("cause", cause);
			return resultMap;
		}
		
		//5、将记录保存入 合同-广告位维护表
		int[] count = contractBackupDao.saveBatchContractAD(contractADList);
		
		if(count.length>0){
			handleFlag = "true";
			cause = "saveSuccess";
			resultMap.put("flag", handleFlag);
			resultMap.put("cause", cause);
		}else{
			handleFlag = "true";
			cause = "saveFailure";
			resultMap.put("flag", handleFlag);
			resultMap.put("cause", cause);
		}
		return resultMap;
	}

	@Override
	public boolean removeContractBackup(int contractBackupId) {
		boolean flag = false;
		/*int result = contractBackupDao.removeContractBackup(contractBackupId);
		if(result>0){
			flag = true;
		}
		return flag;*/
		int result = contractBackupDao.removeContractAllInfoByContractId(contractBackupId);
		if(result==Constant.CALLABLE_EXECUTE_SUCCESS){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}

	@Override
	public Map<String,String> updateContractBackup(String contractParam,String contractParamBefore,String comparedFormResult,String contractAd,Integer userId) {
		Map<String,String> result = new HashMap<String,String>();
		ContractBackup contractBackupAfter = null;
		ContractBackup contractBackupBefore = null;
		ContractModifyFlag contractModifyFlag = null;
		List<ContractContractADRelation> contractContractADRelationList = null;
		String flag = "init";
		String resultFlag="false";
		Map<String,List> resultMap = null;
		List<Integer> adBackupRemove = null;
		List<ContractAD> contractADListAdd = null;
		List<ContractAD> contractADListUpdate = null;
		
		if(StringUtils.isNotBlank(contractParam)){
			contractBackupAfter=JSON.toJavaObject(JSON.parseObject(contractParam), ContractBackup.class);
		}
		
		if(StringUtils.isNotBlank(contractParamBefore)){
			contractBackupBefore=JSON.toJavaObject(JSON.parseObject(contractParamBefore), ContractBackup.class);
		}
		
		if(StringUtils.isNotBlank(comparedFormResult)){
			try {
				contractModifyFlag = JSON.toJavaObject(JSON.parseObject(comparedFormResult),ContractModifyFlag.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
			if(StringUtils.isNotBlank(contractAd)){
				try {
					flag="parseContractADRelation";
					contractContractADRelationList = JSON.parseArray(contractAd, ContractContractADRelation.class);
					resultFlag = "true";
				} catch (Exception e) {
					resultFlag="error";
					logger.error("解析Json时出现异常",e);
					result.put("flag", resultFlag);
					return result;
				} 
			}
			
			if (contractModifyFlag != null) {
				
				//2、contractModifyFlag中bindingPosition不为空，更新属性值的同时，更新备份表信息
				List<PositionModifyFlag> positionModifyFlagList = contractModifyFlag.getBindingPosition();

				try {
					//1、contractModifyFlag中bindingPosition为空直接更新合同属性值
					flag = "updateContractBaseInfo";
					contractBackupAfter.setOperatorId(userId);
					contractBackupAfter.setStatus(Constant.CONTRACT_AUDIT_STATUS_WAIT);
					
					contractBackupDao.updateContractBackup(contractBackupAfter);
					
					ContractAD contractAD = new ContractAD();
					contractAD.setContractCode(contractBackupAfter.getContractCode());
					contractAD.setContractName(contractBackupAfter.getContractName());
					contractAD.setCustomerId(contractBackupAfter.getCustomerId());
					contractAD.setCustomerName(contractBackupAfter.getCustomerName());
					contractAD.setEffectiveStartDate(contractBackupAfter.getEffectiveStartDate());
					contractAD.setEffectiveEndDate(contractBackupAfter.getEffectiveEndDate());
					contractAD.setContractId(contractBackupAfter.getId());
					
					contractBackupDao.updateContractADBackup4ModifyContractBackup(contractAD);
					
					
					resultFlag="true";
					//2、修改原始信息后，需要同时修改关系表中的
					// 合同编号 合同名称 广告商ID 广告商名称 合同有效开始时间 合同有效结束时间
				} catch (Exception e) {
					resultFlag="error";
					logger.error("更新合同基本信息时出现异常",e);
					result.put("flag", resultFlag);
					return result;
					
				}
				
				if ((positionModifyFlagList != null)&& (positionModifyFlagList.size() > 0)) {
					
					try {
						resultMap = renderData(contractBackupAfter,contractBackupBefore, contractModifyFlag,contractContractADRelationList);
						resultFlag="true";
					} catch (Exception e) {
						resultFlag="error";
						logger.error("组装Json时出现异常", e);
						result.put("flag", resultFlag);
						return result;
					}
					
					try {
						if (resultMap != null && resultMap.size() > 0) {
							flag = "updateContractMarketRuleInfo";
							adBackupRemove = resultMap.get("remove");
							contractADListAdd = resultMap.get("add");
							contractADListUpdate = resultMap.get("update");
							if (adBackupRemove != null&& adBackupRemove.size() > 0) {
								//删除多余策略
								contractBackupDao.removeContractBackupAd(adBackupRemove);
								resultFlag="true";
							}
							//保存新增记录
							if (contractADListAdd != null
									&& contractADListAdd.size() > 0) {
								contractBackupDao.saveBatchContractAD(contractADListAdd);
								resultFlag="true";
							}
							//批量更新开始结束时间
							if (contractADListUpdate != null
									&& contractADListUpdate.size() > 0) {
								contractBackupDao.updateContractADBackupBatch(contractADListUpdate);
								resultFlag="true";
							}
						}
					} catch (Exception e) {
						resultFlag="error";
						logger.error("数据入库时出现异常", e);
						result.put("flag", resultFlag);
						return result;
					}
				} 
			}
			result.put("flag", resultFlag);
		
		return result;
	}
	
	private Map<String,List> renderData(ContractBackup contractBackupAfter,ContractBackup contractBackupBefore,ContractModifyFlag contractModifyFlag,List<ContractContractADRelation> contractContractADRelationList){
		
		Map<String,List> map = new HashMap<String,List>();
		List<Integer> adBackupRemove = new ArrayList<Integer>();
		List<ContractAD> contractADListAdd = new ArrayList<ContractAD>();
		List<ContractAD> contractADListUpdate = new ArrayList<ContractAD>();
		
		List<PositionModifyFlag> positionModifyFlagList = contractModifyFlag.getBindingPosition();
		
		for (PositionModifyFlag positionModifyFlag : positionModifyFlagList) {
			//将操作分类
			//将执行删除操作 dbFlag=0代表为库中原始数据 flag=1代表该数据为新增数据，一般不会存在，除非表单录入不全
			if((positionModifyFlag!=null)&&(positionModifyFlag.getDbFlag()==0)&&(positionModifyFlag.getFlag().intValue()==0)){
				//positionModifyFlagListAdd.add(positionModifyFlag);
				continue;
				//将执行删除操作 dbFlag=0代表为库中原始数据 flag=2代表该数据被删除
			}else if((positionModifyFlag!=null)&&(positionModifyFlag.getDbFlag()==0)&&(positionModifyFlag.getFlag().intValue()==1)){
				//positionModifyFlagListAdd.add(positionModifyFlag);
				
				//将执行删除操作 dbFlag=0代表为库中原始数据 flag=2代表该数据被删除
			}else if((positionModifyFlag!=null)&&(positionModifyFlag.getDbFlag()==0)&&(positionModifyFlag.getFlag().intValue()==2)){
				
				adBackupRemove.addAll(positionModifyFlag.getTabIdList());
				//dbFlag=0代表为库中原始数据 flag=3代表该数据被更新
			}else if((positionModifyFlag!=null)&&(positionModifyFlag.getDbFlag()==0)&&(positionModifyFlag.getFlag().intValue()==3)){
				
				//比较规则差异，对数据进行更新
				for (MarketRuleModifyFlag marketRuleModifyFlag : positionModifyFlag.getMarketRules()) {
					//数据库中存在 增加 此种情况页面进行处理 后台暂时不存在
					if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()==0)&&marketRuleModifyFlag.getFlag().intValue()==0){
						continue;
						//数据库中存在 删除
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()==0)&&marketRuleModifyFlag.getFlag().intValue()==1){
						
					//数据库中存在 删除
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()==0)&&marketRuleModifyFlag.getFlag().intValue()==2){
						if (marketRuleModifyFlag.getTabId()!=null) {
							adBackupRemove.add(marketRuleModifyFlag.getTabId());
							//数据库中存在 更新	 规则节点更新状态不存在
						}
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()==0)&&marketRuleModifyFlag.getFlag().intValue()==3){
						
					//数据库中不存在 增加 
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()!=0)&&marketRuleModifyFlag.getFlag().intValue()==1){
						ContractAD contractAD = null;
						//从后操作的结果中找到对应记录
						List<AdvertPosition> positionList = contractBackupAfter.getBindingPosition();
						addContarct2AddCollection(positionList,contractADListAdd,contractBackupAfter,positionModifyFlag,marketRuleModifyFlag);
						//contractADListAdd.add(contractAD);
					//数据库中不存在 删除 此种情况已在页面中进行过滤	此处不进行处理
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()!=0)&&marketRuleModifyFlag.getFlag().intValue()==2){
					
					//数据库中不存在 更新 此种情况规则不存在
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()!=0)&&marketRuleModifyFlag.getFlag().intValue()==3){
						
					}
				}
				addContractAD2UpdateCollection(positionModifyFlag,contractADListUpdate,contractBackupBefore);

				//代表数据库中不存在，新增加记录 设置时间和绑定规则会改变状态值，故此状态在前台屏蔽，后台暂不处理
			}else if((positionModifyFlag!=null)&&(positionModifyFlag.getDbFlag()!=0)&&(positionModifyFlag.getFlag().intValue()==1)){
				
				//一般不会出现以下情况，此状态在页面中直接被删除
			}else if((positionModifyFlag!=null)&&(positionModifyFlag.getDbFlag()!=0)&&(positionModifyFlag.getFlag().intValue()==2)){
				
				//代表数据库中不存在，新增记录
			}else if((positionModifyFlag!=null)&&(positionModifyFlag.getDbFlag()!=0)&&(positionModifyFlag.getFlag().intValue()==3)){
				
				//比较规则差异，对数据进行更新
				for (MarketRuleModifyFlag marketRuleModifyFlag : positionModifyFlag.getMarketRules()) {
					
					//数据库中存在 增加 此种情况页面进行处理 后台暂时不存在
					if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()==0)&&marketRuleModifyFlag.getFlag().intValue()==0){
						continue;
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()==0)&&marketRuleModifyFlag.getFlag().intValue()==1){
						
					
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()==0)&&marketRuleModifyFlag.getFlag().intValue()==2){
						//adBackupRemove.add(marketRuleModifyFlag.getTabId());		
						//数据库中存在 更新	 规则节点更新状态不存在
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()==0)&&marketRuleModifyFlag.getFlag().intValue()==3){
						
					//数据库中不存在 增加 因设置时间和绑定规则会影响此状态值，故此状态值暂不进行处理，前台通过js校验过滤
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()!=0)&&marketRuleModifyFlag.getFlag().intValue()==1){
						ContractAD contractAD = null;
						//从后操作的结果中找到对应记录
						List<AdvertPosition> positionList = contractBackupAfter.getBindingPosition();
						addContarct2AddCollection(positionList,contractADListAdd,contractBackupAfter,positionModifyFlag,marketRuleModifyFlag);
						
					//数据库中存在 删除 如果dbFlag为0 flag 为 2时 在前台直接进行数据的删除操作，此处不进行处理
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()!=0)&&marketRuleModifyFlag.getFlag().intValue()==2){
					
					//数据库中不存在 更新 此种情况规则不存在
					}else if((marketRuleModifyFlag!=null)&&(marketRuleModifyFlag.getDbFlag()!=0)&&marketRuleModifyFlag.getFlag().intValue()==3){
						
					}
				}
				
				//比较时间差异
				String positionIndexFlag = positionModifyFlag.getPositionIndexFlag();
				ContractAD contractAD = new ContractAD();
				boolean dateModifyFlag = false;
				//从后操作的结果中找到对应记录
				List<AdvertPosition> position = contractBackupBefore.getBindingPosition();
				addContractAD2UpdateCollection(positionModifyFlag,contractADListUpdate,contractBackupBefore);	
			}
		}
		map.put("add", contractADListAdd);
		map.put("remove", adBackupRemove);
		map.put("update", contractADListUpdate);
		return map;
	}
	
	/**
	 * 判断是否修改了日期
	 */
	private void addContractAD2UpdateCollection(PositionModifyFlag positionModifyFlag,List<ContractAD> contractADListUpdate,ContractBackup contractBackupBefore){
		//比较时间差异
		String positionIndexFlag = positionModifyFlag.getPositionIndexFlag();
		ContractAD contractAD = new ContractAD();
		boolean dateModifyFlag = false;
		//从后操作的结果中找到对应记录
		List<AdvertPosition> position = contractBackupBefore.getBindingPosition();
		if(position!=null&&position.size()>0){
			for (AdvertPosition advertPosition : position) {
				
				if (StringUtils.isNotBlank(advertPosition.getPositionIndexFlag())) {
					if ((positionModifyFlag.getId().intValue()==advertPosition.getId())&&(positionIndexFlag.equals(advertPosition.getPositionIndexFlag()))) {

						long startTime = advertPosition.getValidStartDate().getTime();

						long endTime = advertPosition.getValidEndDate().getTime();

						if (positionModifyFlag.getValidStartDate() != startTime) {
							contractAD.setStartDate(new Date(positionModifyFlag.getValidStartDate()));
							dateModifyFlag = true;
						}

						if (positionModifyFlag.getValidEndDate() != endTime) {
							contractAD.setEndDate(new Date(positionModifyFlag.getValidEndDate()));
							dateModifyFlag = true;
						}

					}
				
				}else if((positionModifyFlag.getId().intValue()==advertPosition.getId())&&(advertPosition.getTabIdList()!=null)&&(positionModifyFlag.getTabIdList()!=null)){
					
					if (compare(advertPosition.getTabIdList(),positionModifyFlag.getTabIdList())) {
						long startTime = advertPosition
								.getValidStartDate().getTime();
						long endTime = advertPosition.getValidEndDate()
								.getTime();
						if (positionModifyFlag.getValidStartDate() != startTime) {
							dateModifyFlag = true;
						}
						if (positionModifyFlag.getValidEndDate() != endTime) {
							dateModifyFlag = true;
						}
						if (dateModifyFlag == true) {
							for (Integer i : positionModifyFlag
									.getTabIdList()) {
								contractAD = new ContractAD();
								contractAD.setId(i);
								contractAD.setStartDate(new Date(
										positionModifyFlag
												.getValidStartDate()));
								contractAD.setEndDate(new Date(
										positionModifyFlag
												.getValidEndDate()));
								contractADListUpdate.add(contractAD);
							}
							;
						}
					}
				}
				
			}
		}
	}
	/**
	 * 判断是否需要将此记录加入集合中
	 * @param positionList
	 * @param contractADListAdd
	 */
	private void addContarct2AddCollection(List<AdvertPosition> positionList,List<ContractAD> contractADListAdd,ContractBackup contractBackupAfter,PositionModifyFlag positionModifyFlag,MarketRuleModifyFlag marketRuleModifyFlag){
		ContractAD contractAD = null;
		if(positionList!=null&&positionList.size()>0){
			
			for (AdvertPosition advertPosition : positionList) {
				//找到对应规则信息
				if(positionModifyFlag.getPositionIndexFlag().equals(advertPosition.getPositionIndexFlag())){
					
					contractAD = new ContractAD();
					contractAD.setPositionId(advertPosition.getId());
					contractAD.setContractId(contractBackupAfter.getId());
					contractAD.setStartDate(advertPosition.getValidStartDate());
					contractAD.setEndDate(advertPosition.getValidEndDate());
					
					contractAD.setContractCode(contractBackupAfter.getContractCode());
					contractAD.setContractName(contractBackupAfter.getContractName());
					contractAD.setPositionName(advertPosition.getPositionName());
					contractAD.setPositionTypeId(advertPosition.getPositionTypeId());
					contractAD.setPositionTypeName(advertPosition.getPositionTypeName());
					
					contractAD.setEffectiveStartDate(contractBackupAfter.getEffectiveStartDate());
					contractAD.setEffectiveEndDate(contractBackupAfter.getEffectiveEndDate());
					contractAD.setCustomerId(contractBackupAfter.getCustomerId());
					contractAD.setCustomerName(contractBackupAfter.getCustomerName());

					List<MarketingRuleBean> marketingRuleBeanList = advertPosition.getMarketRules();
					
					for (MarketingRuleBean marketingRuleBean : marketingRuleBeanList) {
						
						if(marketingRuleBean.getId()==marketRuleModifyFlag.getId()){
							ContractAD contractADMarket = new ContractAD();
							try {
								BeanUtils.copyProperties(
										contractADMarket,
										contractAD);
							} catch (Exception e) {
								e.printStackTrace();
							}
							contractADMarket.setMarketingRuleId(marketingRuleBean.getId());
							contractADMarket.setMarketingRuleName(marketingRuleBean.getRuleName());
							contractADListAdd.add(contractADMarket);
						}
					}
				}
			}
		}
	}
	
	
	
	
	@Override
	public List<ContractBackup> getContractBackupById(Integer CustomerId) {
		return contractBackupDao.queryCustomerBackupById(CustomerId);
	}

	@Override
	public ContractBackup getContractByContractId(Integer contractId) throws Exception{
		
		ContractBackup contractBackup = null;
		TempContract tempContract = null;
		
		Map<Integer,List<AdvertPosition>> advertPositionCache = null;
		
		List<AdvertPosition> advertPositonList = null;
		
		List<Integer> adPrimaryList = null;
		
		List<MarketingRuleBean> marketingRuleList = null;
		
		AdvertPosition advertPosition = null;
		
		MarketingRuleBean marketingRuleBean = null;
		
		List<Integer> positionListCondition= new ArrayList<Integer>();
		
		int arrayListLength;
		//同一时段
		boolean timerIntervalFlag = false;
		
		List<TempContract> contractBackupList = contractBackupDao.queryContractByContractIdFromBackup(contractId);
		
		if(contractBackupList!=null&&contractBackupList.size()>0){
			
			advertPositionCache = new HashMap<Integer,List<AdvertPosition>>();
			arrayListLength = contractBackupList.size();
			contractBackup = new ContractBackup();
			//设置合同原始数据
			tempContract = contractBackupList.get(0);
			
			if(contractBackup!=null){
				BeanUtils.copyProperties(contractBackup, tempContract);
				contractBackup.setCustomerId(tempContract.getAdvertisersId());
				
				contractBackup.setApprovalStartDateShow(Transform.CalendartoString(tempContract.getApprovalStartDate()));
				contractBackup.setApprovalEndDateShow(Transform.CalendartoString(tempContract.getApprovalEndDate()));
				contractBackup.setCustomerId(tempContract.getCustomerId());
				contractBackup.setEffectiveStartDateShow(Transform.CalendartoString(tempContract.getEffectiveStartDate()));
				contractBackup.setEffectiveEndDateShow(Transform.CalendartoString(tempContract.getEffectiveEndDate()));
				
				contractBackup.setCustomerName(tempContract.getAdvertisersName());
				contractBackup.setCustomerId(tempContract.getAdvertisersId());
				contractBackup.setId(tempContract.getContractId());
				contractBackup.setPositionType(tempContract.getAdType());
				
				contractBackup.setModify("0");
			}
			
			for (int index = 0;index<arrayListLength;index++) {
				
				TempContract tempContractFirst = (TempContract) contractBackupList.get(index);
				advertPositonList = advertPositionCache.get(tempContractFirst.getPositionId());
				
				if(advertPositonList==null){
					
					advertPositonList = new ArrayList<AdvertPosition>();
					
					adPrimaryList = new ArrayList<Integer>();
					adPrimaryList.add(tempContractFirst.getTabId());
					advertPosition = new AdvertPosition();
					advertPosition.setTabIdList(adPrimaryList);
					
					copyPositionFromTempContract(advertPosition, tempContractFirst);
					advertPosition.setId(tempContractFirst.getPositionId());
					advertPosition.setPositionName(tempContractFirst.getPositionName());
					advertPosition.setValidStartDate(tempContractFirst.getValidStart());
					advertPosition.setValidEndDate(tempContractFirst.getValidEnd());
					advertPosition.setValidStartDateShow(Transform.CalendartoString(tempContractFirst.getValidStart()));
					advertPosition.setValidEndDateShow(Transform.CalendartoString(tempContractFirst.getValidEnd()));
					if (StringUtils.isNotBlank(tempContractFirst.getAdType())) {
						advertPosition.setPositionTypeId(Integer
								.valueOf(tempContractFirst.getAdType()));
					}
					advertPosition.setPositionTypeName(tempContractFirst.getAdTypeName());
					
					advertPosition.setChooseMarketRulesElement("#");
					advertPosition.setCurrentIndex("0");
					advertPosition.setChooseState("false");
					
					advertPosition.setModify("0");
					
					marketingRuleBean = new MarketingRuleBean();
					//if (tempContractFirst.getMrId()!=null) {
					if (tempContractFirst.getMarketingRuleId()!=null) {
						//marketingRuleBean.setId(tempContractFirst.getMrId());
						marketingRuleBean.setId(tempContractFirst.getMarketingRuleId());
						marketingRuleBean.setAreaName(tempContractFirst.getMrAreaName());
						marketingRuleBean.setChannelName(tempContractFirst
								.getMrChannelName());
						marketingRuleBean.setCreateTime(tempContractFirst
								.getMrCreateTime());
						marketingRuleBean.setEndTime(tempContractFirst.getMrEndTime());
						marketingRuleBean.setPositionName(tempContractFirst
								.getPositionName());
						marketingRuleBean.setRuleName(tempContractFirst
								.getMrRuleName());
						marketingRuleBean.setStartTime(tempContractFirst.getMrStartTime());
						
						Integer state = tempContractFirst.getMrState();
						if (state!=null) {
							marketingRuleBean.setState(tempContractFirst.getMrState());
						}else{
							marketingRuleBean.setState(0);
						}
						marketingRuleBean.setTabId(tempContractFirst.getTabId());
						
						marketingRuleBean.setModify("0");
						
						marketingRuleList = new ArrayList<MarketingRuleBean>();
						marketingRuleList.add(marketingRuleBean);
						advertPosition.setChooseMarketRules(tempContractFirst.getMarketingRuleId()+",");
						advertPosition.setMarketRules(marketingRuleList);
						advertPositonList.add(advertPosition);
						advertPositionCache.put(advertPosition.getId(),advertPositonList);
					}
				}else{
					
					//分两种情况
					
					for (int i = 0; i < advertPositonList.size(); i++) {
						
						AdvertPosition advertPositionInner = advertPositonList.get(i);
						
						if((advertPositionInner.getValidStartDate().compareTo(tempContractFirst.getValidStart())==0)&&(advertPositionInner.getValidEndDate().compareTo(tempContractFirst.getValidEnd())==0)){
							
							List<MarketingRuleBean> marketingRuleListInner = advertPositionInner.getMarketRules();
							
							for (MarketingRuleBean marketingRuleBeanInner : marketingRuleListInner) {
								//if(marketingRuleBeanInner.getId()!=tempContractFirst.getMrId()&&(!positionListCondition.contains(i))){
								if(marketingRuleBeanInner.getId()!=tempContractFirst.getMarketingRuleId()&&(!positionListCondition.contains(i))){
									timerIntervalFlag=true;
									positionListCondition.add(i);
									break;
								}
							}
						}
					}
					
					
					if((positionListCondition!=null)&&(positionListCondition.size()>0)){
							
							marketingRuleBean = new MarketingRuleBean();
							//marketingRuleBean.setId(tempContractFirst.getMrId());
							marketingRuleBean.setId(tempContractFirst.getMarketingRuleId());
							marketingRuleBean.setAreaName(tempContractFirst.getMrAreaName());
							marketingRuleBean.setChannelName(tempContractFirst.getMrChannelName());
							marketingRuleBean.setCreateTime(tempContractFirst.getMrCreateTime());
							marketingRuleBean.setEndTime(tempContractFirst.getMrEndTime());
							marketingRuleBean.setPositionName(tempContractFirst.getPositionName());
							marketingRuleBean.setRuleName(tempContractFirst.getMrRuleName());
							marketingRuleBean.setStartTime(tempContractFirst.getMrStartTime());
							
							Integer state = tempContractFirst.getMrState();
							if (state!=null) {
								marketingRuleBean.setState(tempContractFirst.getMrState());
							}else{
								marketingRuleBean.setState(0);
							}
							marketingRuleBean.setModify("0");
							marketingRuleBean.setTabId(tempContractFirst.getTabId());
							adPrimaryList = advertPosition.getTabIdList();
							adPrimaryList.add(tempContractFirst.getTabId());
							
							for (Integer indexInner : positionListCondition) {
								advertPositonList.get(indexInner).getMarketRules().add(marketingRuleBean);
								//advertPositonList.get(indexInner).setChooseMarketRules((advertPositonList.get(indexInner).getChooseMarketRules()!=null?advertPositonList.get(indexInner).getChooseMarketRules():"")+tempContractFirst.getMrId()+",");
								advertPositonList.get(indexInner).setChooseMarketRules((advertPositonList.get(indexInner).getChooseMarketRules()!=null?advertPositonList.get(indexInner).getChooseMarketRules():"")+tempContractFirst.getMarketingRuleId()+",");
							}
							
						}else{
							advertPosition = new AdvertPosition();
							adPrimaryList = new ArrayList<Integer>();
							adPrimaryList.add(tempContractFirst.getTabId());
							advertPosition = new AdvertPosition();
							advertPosition.setTabIdList(adPrimaryList);
							
							copyPositionFromTempContract(advertPosition, tempContractFirst);
							
							advertPosition.setId(tempContractFirst.getPositionId());
							advertPosition.setPositionName(tempContractFirst.getPositionName());
							advertPosition.setValidStartDate(tempContractFirst.getValidStart());
							advertPosition.setValidEndDate(tempContractFirst.getValidEnd());
							
							advertPosition.setValidStartDateShow(Transform.CalendartoString(tempContractFirst.getValidStart()));
							advertPosition.setValidEndDateShow(Transform.CalendartoString(tempContractFirst.getValidEnd()));
							
							advertPosition.setChooseMarketRulesElement("#");
							advertPosition.setCurrentIndex("0");
							advertPosition.setChooseState("false");
							
							advertPosition.setModify("0");
							
							marketingRuleBean = new MarketingRuleBean();
							//marketingRuleBean.setId(tempContractFirst.getMrId());
							marketingRuleBean.setId(tempContractFirst.getMarketingRuleId());
							marketingRuleBean.setAreaName(tempContractFirst.getMrAreaName());
							marketingRuleBean.setChannelName(tempContractFirst.getMrChannelName());
							marketingRuleBean.setCreateTime(tempContractFirst.getMrCreateTime());
							marketingRuleBean.setEndTime(tempContractFirst.getMrEndTime());
							marketingRuleBean.setPositionName(tempContractFirst.getPositionName());
							marketingRuleBean.setRuleName(tempContractFirst.getMrRuleName());
							marketingRuleBean.setStartTime(tempContractFirst.getMrStartTime());
							
							Integer state = tempContractFirst.getMrState();
							
							if (state!=null) {
								marketingRuleBean.setState(tempContractFirst.getMrState());
							}else{
								marketingRuleBean.setState(0);
							}
							marketingRuleBean.setTabId(tempContractFirst.getTabId());
							marketingRuleBean.setModify("0");
							
							//advertPosition.setChooseMarketRules((advertPosition.getChooseMarketRules()!=null?advertPosition.getChooseMarketRules():"")+tempContractFirst.getMrId()+",");
							advertPosition.setChooseMarketRules((advertPosition.getChooseMarketRules()!=null?advertPosition.getChooseMarketRules():"")+tempContractFirst.getMarketingRuleId()+",");
							//advertPosition.setChooseMarketRules(tempContractFirst.getMrId()+",");
							marketingRuleList = new ArrayList<MarketingRuleBean>();
							marketingRuleList.add(marketingRuleBean);
							advertPosition.setMarketRules(marketingRuleList);
							advertPositonList.add(advertPosition);
						}
					
					positionListCondition.clear();
				}
			}
			
			List<AdvertPosition> advertPositionListReturn = new ArrayList<AdvertPosition>();
			//将Map中的list取出，合并
			if(advertPositionCache!=null&&advertPositionCache.size()>0){
				Set advertPositionCacheSet = advertPositionCache.entrySet();
				for (Iterator iterator = advertPositionCacheSet.iterator(); iterator
						.hasNext();) {
					Map.Entry<Integer,List<AdvertPosition>> mapInner = (Map.Entry<Integer,List<AdvertPosition>>) iterator.next();
					advertPositionListReturn.addAll(mapInner.getValue());
				}
			}
			contractBackup.setBindingPosition(advertPositionListReturn);
		}
		
		return contractBackup;
	}

	@Override
	public List<ContractContractADRelation> getContractContractADRelation(
			Integer contractId) throws Exception {
		return contractBackupDao.getContractContractADRelation(contractId);
	}
	
	public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
		if(a.size() != b.size())
			return false;
			Collections.sort(a);
			Collections.sort(b);
			for(int i=0;i<a.size();i++){
				if(!a.get(i).equals(b.get(i)))
			            return false;
			    }
			    return true;		
	}

	@Override
	public Map<String, String> updateApprovalContractContractBackup(
			String contractParam,Integer userId,String username) {
		Map<String,String> result = new HashMap<String,String>();
		String resultFlag="false";
		String resultType = "";
		String causeCondition = "";
		String stage = "";
		ContractBackup contractBackup = null;
		String metarialPath = null;
		int flag;
		try {
			//先修改备份表中数据，更新状态为审核通过状态
			if (StringUtils.isNotBlank(contractParam)) {
				contractBackup = JSON.toJavaObject(JSON
						.parseObject(contractParam), ContractBackup.class);

				if (contractBackup != null) {
					//审核通过
					if (Constant.CONTRACT_AUDIT_STATUS_PASS == contractBackup
							.getStatus().intValue()) {
						stage = "saveData2DB";
						resultType = "pass";
						metarialPath = this.gengerateUserHomeDirectory(contractBackup);
						metarialPath = metarialPath.replace("\\","/");
						contractBackup.setAuditDate(new Date());
						
						if (StringUtils.isNotBlank(username)&&"admin".equals(username)) {
							contractBackup.setAuditTaff(username);
							contractBackup.setOperatorId(0);
						}
						contractBackup.setMetarialPath(metarialPath);
						contractBackupDao.approvalContractBackup(contractBackup);
						contractBackupDao.updateCotractBackupStatus(contractBackup.getId(),Constant.CONTRACT_AUDIT_STATUS_PASS);
						
						//将备份表中数据拷贝到运行期表
						flag = contractRunDao
								.copyContractBackup2ContractRun(contractBackup
										.getId());
						if(flag==Constant.CALLABLE_EXECUTE_SUCCESS){
							resultFlag = "true";
							
							//将对应关系表中备份表记录拷贝到主表中
							flag=contractRunDao.copyContractBackupAD2ContractADRun(contractBackup.getId());
							
							if(flag==Constant.CALLABLE_EXECUTE_SUCCESS){
								stage = "createReomoteDirectory";
								//生成用户主目录
								this.createReomoteDirectory(contractBackup.getMetarialPath());
								
								stage = "createDirectoryQuota";
								//创建磁盘配额
								this.createDirectoryQuota(contractBackup.getMetarialPath(),Integer.valueOf(maxFileSize));
							}else{
								resultFlag = "false";
							}
						}else{
							resultFlag = "false";
						}
						
						//审核不通过
					} else if (Constant.CONTRACT_AUDIT_STATUS_NO_PASS == contractBackup.getStatus().intValue()) {
						resultType = "nopass";
						metarialPath = this.gengerateUserHomeDirectory(contractBackup);
						metarialPath=metarialPath.replace("\\","/");
						contractBackup.setMetarialPath(metarialPath);
						contractBackup.setAuditDate(new Date());
						
						if (StringUtils.isNotBlank(username)&&"admin".equals(username)) {
							contractBackup.setAuditTaff(username);
							contractBackup.setOperatorId(0);
						}
						
						contractBackupDao.approvalContractBackup(contractBackup);
						contractBackupDao.updateCotractBackupStatus(contractBackup.getId(),Constant.CONTRACT_AUDIT_STATUS_NO_PASS);
						resultFlag = "true";
					}
				} else {
					causeCondition = "InsufficientParameters";
				}
			}
		} catch (Exception e) {
			logger.error("审核过程中出现异常",e);
		}
		
		result.put("resultFlag", resultFlag);
		result.put("resultType", resultType);
		result.put("causeCondition", resultFlag);
		
		return result;
	}

	public ContractRunDao getContractRunDao() {
		return contractRunDao;
	}

	public void setContractRunDao(ContractRunDao contractRunDao) {
		this.contractRunDao = contractRunDao;
	}
	
	/**
	 * 生成用户主目录
	 * advert/客户所属合同号(client_code)在customer_backup表单中/合同号(contract_number)在contract_backup中/素材类型（如果是图片，是image,如果是视频时video）/之后是素材本身。
	 * @return
	 */
	private String gengerateUserHomeDirectory(ContractBackup contractBackup){
		StringBuffer homeDirectory = new StringBuffer();
		List<CustomerBackUp>  customerBackUpList = customerDao.getClientCodeByCustomerId(contractBackup.getCustomerId());
		String clientCode = null;
		if(customerBackUpList!=null&&customerBackUpList.size()>0){
			clientCode = customerBackUpList.get(0).getClientCode();
			homeDirectory.append(advertDirectory).append(ftpTempPath).append(clientCode).append(SEP).append(contractBackup.getContractNumber());
		}
		
		return homeDirectory.toString();
	}
	/**
	 * 创建远程Ftp目录
	 * @param path
	 * @param username
	 * @param passwd
	 * @return
	 */
	private boolean createReomoteDirectory(String path){
		boolean flag = true;
		FtpUtils ftpUtils = new FtpUtils();
		try {
			ftpUtils.connectionFtp();
			ftpUtils.createDirecoty(path);
			ftpUtils.closedFtp();
		} catch (Exception e) {
			flag = false;
			logger.error("创建ftp目录失败", e);
		}
		return flag;
	}
	/**
	 * 为指定目录创建磁盘配合，限制操作的最大值
	 * @param path
	 * @param fileMaxSize
	 * @return
	 */
	private boolean createDirectoryQuota(String path,Integer fileMaxSize){
		boolean flag = true;
		return flag;
	}
	
	/**
	 * 因apache common 对 timestamp 空值 处理不好，故此处手动进行属性值拷贝
	 */
	private void copyPositionFromTempContract(AdvertPosition advertPosition,TempContract tempContract){
		if(advertPosition!=null&&tempContract!=null){
			advertPosition.setId(tempContract.getPositionId());
			advertPosition.setCharacteristicIdentification(tempContract.getCharacteristicIdentification());
			advertPosition.setPositionName(tempContract.getPositionName());
			advertPosition.setDescription(tempContract.getDescription());
			
			advertPosition.setImageRuleId(tempContract.getImageRuleId());
			advertPosition.setVideoRuleId(tempContract.getVideoRuleId());
			advertPosition.setTextRuleId(tempContract.getTextRuleId());
			advertPosition.setQuestionRuleId(tempContract.getQuestionRuleId());
			
			advertPosition.setIsHd(tempContract.getIsHd());
			advertPosition.setIsAdd(tempContract.getIsAdd());
			advertPosition.setIsLoop(tempContract.getIsLoop());
			advertPosition.setMaterialNumber(tempContract.getMaterialNumber());
			advertPosition.setDeliveryMode(tempContract.getDeliveryMode());
			advertPosition.setPrice(tempContract.getPrice());
			advertPosition.setDiscount(tempContract.getDiscount());
			advertPosition.setBackgroundPath(tempContract.getBackgroundPath());
			advertPosition.setOperationId(tempContract.getOperationId());
			advertPosition.setState(tempContract.getState());
			
			advertPosition.setPositionTypeId(tempContract.getPositionTypeId());
			advertPosition.setCoordinate(tempContract.getCoordinate());
			advertPosition.setDeliveryPlatform(tempContract.getDeliveryPlatform());
			
			advertPosition.setPositionTypeName(tempContract.getAdTypeName());
			if (tempContract.getCreateTime()!=null) {
				advertPosition.setCreateTime(tempContract.getCreateTime());
			}
			if (tempContract.getStartTime()!=null) {
				advertPosition.setStartTime(tempContract.getStartTime());
			}
			if (tempContract.getEndTime()!=null) {
				advertPosition.setEndTime(tempContract.getEndTime());
			}
			if (tempContract.getModifyTime()!=null) {
				advertPosition.setModifyTime(new Timestamp(tempContract.getModifyTime().getTime()));
			}
			advertPosition.setWidthHeight(tempContract.getWidthHeight());
			
		}
	}
	/**
	 * 暂时处理所选日期多8小时问题
	 * @param date
	 */
	private void setDate(Date date){
		if(date!=null){
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
		}
	}
	
	public static void main(String[] args) {
		String abc = "{\"id\":\"false\",\"contractNumber\":\"false\",\"contractCod\":\"false\",\"customerId\":\"false\",\"customerName\":\"false\",\"contractName\":\"false\",\"submitUnits\":\"false\",\"financialInformation\":\"false\",\"approvalCode\":\"false\",\"metarialPath\":\"false\",\"effectiveStartDate\":\"false\",\"effectiveEndDate\":\"false\",\"approvalStartDate\":\"false\",\"approvalEndDate\":\"false\",\"effectiveStartDateShow\":\"false\",\"effectiveEndDateShow\":\"false\",\"approvalStartDateShow\":\"false\",\"approvalEndDateShow\":\"false\",\"otherContent\":\"false\",\"status\":\"false\",\"contractDesc\":\"false\",\"bindingPosition\":[{\"id\":\"262\",\"validStartDate\":\"1364918400000\",\"validStartDateShow\":\"2013-4-3\",\"validEndDateShow\":\"2013-4-3\",\"validEndDate\":\"1367251200000\",\"dbFlag\":0,\"flag\":3,\"tabIdList\":[128],\"marketRules\":[{\"id\":\"46\",\"dbFlag\":0,\"flag\":0},{\"id\":\"47\",\"dbFlag\":1,\"flag\":1}],\"positionIndexFlag\":\"1366534445285_262\"}]}";
		try {
			ContractModifyFlag contractModifyFlag = JSON.toJavaObject(JSON.parseObject(abc),ContractModifyFlag.class);
			System.out.println(contractModifyFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
