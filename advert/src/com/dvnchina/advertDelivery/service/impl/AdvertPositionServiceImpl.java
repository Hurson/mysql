package com.dvnchina.advertDelivery.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.dao.AdvertPositionDao;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.PositionOccupyStatesInfo;
import com.dvnchina.advertDelivery.service.AdvertPositionService;

public class AdvertPositionServiceImpl implements AdvertPositionService {

	private AdvertPositionDao advertPositionDao;

	@Override
	public String savePosition(AdvertPosition advertPosition) {
		advertPosition.setCreateTime(new Timestamp(System.currentTimeMillis()));
		advertPosition.setOperationId("123");
		//advertPosition.setState("SUCC");
		advertPositionDao.saveAdvertPosition(advertPosition);
		return null;
	}

	public AdvertPositionDao getAdvertPositionDao() {
		return advertPositionDao;
	}

	public void setAdvertPositionDao(AdvertPositionDao advertPositionDao) {
		this.advertPositionDao = advertPositionDao;
	}
	//select * from (SELECT id,t.position_type_id FROM t_advertposition t WHERE t.POSITION_TYPE_ID=90) tmp,t_position_type pt where tmp.position_type_id=pt.id
	//SELECT t.id,t.position_type_id FROM t_advertposition t,t_position_type tp WHERE t.POSITION_TYPE_ID=90 and t.position_type_id=tp.id
	@Override
	public List<AdvertPosition> page(Map condition,int start, int end) {
		
		StringBuffer queryPosition = new StringBuffer();
		queryPosition.append("SELECT tmp.*,pt.position_type_name,pt.position_type_code,pt.IS_CHARACTERISTIC,pt.is_alltime isAllTime,pt.is_channel isChannel FROM (");
		queryPosition.append("SELECT * FROM t_advertposition t");
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 0;
			queryPosition.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				count++;
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				Object object = entry.getValue();
				if ("java.lang.String".equals(object.getClass().getName())) {
					queryPosition.append(columnName).append(" LIKE '%").append(object).append("%'");
				}else{
					queryPosition.append(columnName).append("=").append(object);
				}
				
				if(count<mapSize){
					queryPosition.append(" AND ");
				}
			}
		}
		queryPosition.append(") tmp left join t_position_type pt on tmp.position_type_id=pt.id");
		return advertPositionDao.page(queryPosition.toString(), start, end);
	}

	@Override
	public int queryCount(Map condition) {
		StringBuffer queryPosition = new StringBuffer();
		queryPosition.append("SELECT COUNT(*) FROM t_advertposition");
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 0;
			queryPosition.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				count++;
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				Object object = entry.getValue();
				if ("java.lang.String".equals(object.getClass().getName())) {
					queryPosition.append(columnName).append(" like '%").append(
							object).append("%'");
				}else{
					queryPosition.append(columnName).append("=").append(
							object);
				}
				
				if(count<mapSize){
					queryPosition.append(" AND ");
				}
			}
		}
		return advertPositionDao.queryTotalCount(queryPosition.toString());
	}

	@Override
	public List<AdvertPosition> find(Map condition) {
		StringBuffer queryPosition = new StringBuffer();
		queryPosition.append("SELECT * FROM t_advertposition ");
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 0;
			queryPosition.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				count++;
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				Object object = entry.getValue();
				if ("java.lang.String".equals(object.getClass().getName())) {
					queryPosition.append(columnName).append("='").append(
							entry.getValue()).append("'");
				}else{
					queryPosition.append(columnName).append("=").append(
							entry.getValue());
				}
				
				if(count<mapSize){
					queryPosition.append(" AND ");
				}
			}
		}
		return advertPositionDao.find(queryPosition.toString());
	}

	@Override
	public int remove(int advertPositionId) {
		return advertPositionDao.removeAdvertPosition(advertPositionId);
	}

	@Override
	public int update(AdvertPosition advertPositionAfter) {
		return advertPositionDao.updateAdvertPosition(advertPositionAfter);
	}

	@Override
	public int[] saveBatchPosition(List<AdvertPosition> advertPositionList) {
		return advertPositionDao.saveBatchAdvertPosition(advertPositionList);
	}

	@Override
	public int[] updateBatch(List<AdvertPosition> advertPositionList) {
		return advertPositionDao.updateBatchAdvertPosition(advertPositionList);
	}

	@Override
	public List<AdvertPosition> findPositionByEign(List<String> eignValueList) {
		StringBuffer queryPosition = new StringBuffer();
		List<AdvertPosition>  advertPositionList = null;
		
		if(eignValueList!=null&&eignValueList.size()>0){
			for (int i = 0; i < eignValueList.size(); i++) {
				if((i!=0)&&(i!=eignValueList.size()-2)){
					queryPosition.append("UNION SELECT * FROM t_advertposition");
					queryPosition.append(" WHERE 1=1 AND CHARACTERISTIC_IDENTIFICATION='");
					queryPosition.append(eignValueList.get(i));
					queryPosition.append("' ");
				}else {
					queryPosition.append("SELECT * FROM t_advertposition");
					queryPosition.append(" WHERE 1=1 AND CHARACTERISTIC_IDENTIFICATION='");
					queryPosition.append(eignValueList.get(i));
					queryPosition.append("' ");
				}
			}
			advertPositionList = advertPositionDao.find(queryPosition.toString());
		}
		
		return advertPositionList;
	}
	
	public static void main(String[] args) {
		AdvertPositionServiceImpl advertPositionServiceImpl = new AdvertPositionServiceImpl();
		
		List eignValueList1 = new ArrayList();
		advertPositionServiceImpl.findPositionByEign(eignValueList1);
		
		List eignValueList2 = new ArrayList();
		eignValueList2.add("sd_123");
		advertPositionServiceImpl.findPositionByEign(eignValueList2);
		
		List eignValueList3 = new ArrayList();
		eignValueList3.add("sd_123");
		eignValueList3.add("hd_123");
		advertPositionServiceImpl.findPositionByEign(eignValueList3);
		
	}

	@Override
	public List<AdvertPosition> getAdvertPositionById(Integer advertPositionId) {
		return advertPositionDao.getAdvertPositionById(advertPositionId);
	}

	@Override
	public List<PositionOccupyStatesInfo> page4PositionOccupyStatus(
			Integer status,Integer positionId, int start, int end,String startDate,String endDate) {
		StringBuffer querySql = new StringBuffer();
		String sql = "";
		if(status.intValue()==1){
			//查询已销售记录
			//querySql.append("SELECT * FROM (SELECT * FROM (SELECT * FROM (  SELECT contractAd.Id contractAdId,contractAd.Contract_Id contractId,contractAd.Ad_Id contractAdPositionId,TO_CHAR(contractAd.Valid_Start,'yyyy-mm-dd') validStartDate,TO_CHAR(contractAd.Valid_End,'yyyy-mm-dd') validEndDate,contractAd.Rule_Id contractAdRuleId,contract.contract_number contractNumber,contract.contract_code contractCode,contract.custom_id customerId,contract.contract_name contractName,contract.effective_start_date effectiveStartDate,contract.effective_end_date effectiveEndDate,contract.status contractStatus FROM t_contract contract INNER JOIN t_contract_ad contractAd ON contractAd.Contract_Id=contract.id AND contractAd.ad_id=?) INNER JOIN (  SELECT mrule.id ruleIdP,mrule.rule_id ruleId,mrule.position_id positionId,mrule.rule_name ruleName,TO_CHAR(mrule.start_time,'hh24:mi:ss') startTime ,TO_CHAR(mrule.end_time,'hh24:mi:ss') endTime,mrule.location_id releaseAreaId,mrule.channel_id channelId,mrule.state marketingRuleStates,area.area_code releaseAreaCode,area.area_name areaName,area.parent_code parentCode FROM t_marketing_rule mrule INNER JOIN t_release_area area ON mrule.location_id=area.id and mrule.position_id=?) ON contractAdRuleId=ruleIdP) contractRule inner join t_customer customer on contractRule.customerId=customer.id) t inner join T_ADVERTPOSITION a on t.positionid=a.id ");

			sql = "SELECT CONTRACTAD.ID                 CONTRACTADID,\n" +
			"       CONTRACTAD.CONTRACT_ID        CONTRACTID,\n" + 
			"       CONTRACTAD.AD_ID              CONTRACTADPOSITIONID,\n" + 
			"       CONTRACTAD.VALID_START        VALIDSTARTDATE,\n" + 
			"       CONTRACTAD.VALID_END          VALIDENDDATE,\n" + 
			"       CONTRACTAD.RULE_ID            CONTRACTADRULEID,\n" + 
			"       CONTRACT.CONTRACT_NUMBER      CONTRACTNUMBER,\n" + 
			"       CONTRACT.CONTRACT_CODE        CONTRACTCODE,\n" +
			"       CONTRACT.CONTRACT_NAME        CONTRACTNAME,\n" + 
			"       CONTRACT.EFFECTIVE_START_DATE,\n" + 
			"       CONTRACT.EFFECTIVE_END_DATE,\n" + 
			"       CONTRACT.STATUS               CONTRACTSTATUS,\n" + 
			"\t\t\t CONTRACT.CUSTOM_ID            CUSTOMERID,\n" + 
			"\t\t\t MRULE.ID RULEIDP,\n" + 
			"       MRULE.RULE_ID RULEID,\n" + 
			"       MRULE.POSITION_ID POSITIONID,\n" + 
			"       MRULE.RULE_NAME RULENAME,\n" + 
			"       MRULE.START_TIME STARTTIME,\n" + 
			"       MRULE.END_TIME ENDTIME,\n" + 
			"       MRULE.LOCATION_ID RELEASEAREAID,\n" + 
			"       MRULE.CHANNEL_ID CHANNELID,\n" + 
			"       MRULE.STATE MARKETINGRULESTATES,\n" + 
			"       AREA.AREA_CODE RELEASEAREACODE,\n" + 
			"       AREA.AREA_NAME AREANAME,\n" + 
			"       AREA.PARENT_CODE PARENTCODE,\n" + 
			"       CUSTOMER.ADVERTISERS_NAME,\n" + 
			"\t\t\t A.*\n" + 
			"  FROM T_CONTRACT CONTRACT\n" + 
			"  INNER JOIN T_CONTRACT_AD CONTRACTAD ON CONTRACTAD.CONTRACT_ID =\n" + 
			"                                         CONTRACT.ID\n";
			if(positionId!=null){
				sql += "       AND CONTRACTAD.AD_ID = ? ";
			}
			
			if(StringUtils.isNotBlank(startDate)){
				sql += "       AND CONTRACT.Effective_Start_Date<=? " ;
			}
			
			if(StringUtils.isNotBlank(endDate)){
				sql += "       AND CONTRACT.Effective_End_Date>=? " ;
			}
			
			sql +="       LEFT JOIN T_MARKETING_RULE MRULE ON  CONTRACTAD.RULE_ID=MRULE.id LEFT JOIN  T_RELEASE_AREA AREA ON MRULE.LOCATION_ID = AREA.ID LEFT JOIN T_CUSTOMER CUSTOMER ON CONTRACT.CUSTOM_ID=CUSTOMER.ID LEFT JOIN T_ADVERTPOSITION A ON CONTRACTAD.AD_ID = A.ID";
			querySql.append(sql);
		}else if(status.intValue()==2){
			//查询待销售记录 营销规则表中存在，但合同-广告位运行期表中不存在
			//querySql.append("  SELECT '' contractAdId,'' contractId,'' contractAdPositionId,'' validStartDate,'' validEndDate,'' contractAdRuleId,'' contractNumber,'' contractCode,'' customerId,''  contractName,'' effectiveStartDate,'' effectiveEndDate,''  contractStatus,mrule.id ruleIdP,mrule.rule_id as ruleId,mrule.position_id as positionId,mrule.rule_name as ruleName, 	to_char(mrule.start_time, 'hh24:mi:ss') as startTime,	to_char(mrule.end_time, 'hh24:mi:ss') as endTime, 	mrule.location_id as releaseAreaId,	mrule.channel_id as channelId,	mrule.state marketingRuleStates,	area.area_code as releaseAreaCode,	area.area_name as areaName,	area.parent_code as parentCode,'' advertisersName	from (SELECT *	FROM t_marketing_rule mruleInner	WHERE NOT EXISTS (SELECT *	FROM t_contract_ad ad	WHERE ad.rule_id = mruleInner.id)) mrule	inner join t_release_area area on mrule.location_id = area.id and mrule.position_id=?");
			querySql.append("  SELECT  A.*,AP.POSITION_NAME FROM (SELECT mrule.id ruleIdP,mrule.rule_id as ruleId,mrule.position_id as positionId,mrule.rule_name as ruleName,mrule.start_time as startTime,mrule.end_time as endTime,   mrule.location_id as releaseAreaId,  mrule.channel_id as channelId,  mrule.state marketingRuleStates,  area.area_code as releaseAreaCode,  area.area_name as areaName,  area.parent_code as parentCode,'' advertisersName  from (SELECT *  FROM t_marketing_rule mruleInner  WHERE NOT EXISTS (SELECT *  FROM t_contract_ad ad  WHERE ad.rule_id = mruleInner.id)) mrule  LEFT join t_release_area area on mrule.location_id = area.id and mrule.position_id=?) a INNER JOIN T_ADVERTPOSITION ap ON a.positionid=ap.id");

		}else if(status.intValue()==3){
			//查询其他
			//querySql.append("  SELECT * FROM (SELECT mrule.id ruleIdP,mrule.rule_id as ruleId,mrule.position_id as positionId,mrule.rule_name as ruleName,   to_char(mrule.start_time, 'hh24:mi:ss') as startTime,  to_char(mrule.end_time, 'hh24:mi:ss') as endTime,   mrule.location_id as releaseAreaId,  mrule.channel_id as channelId,  mrule.state marketingRuleStates,  area.area_code as releaseAreaCode,  area.area_name as areaName,  area.parent_code as parentCode,'' advertisersName  from (SELECT *  FROM t_marketing_rule mruleInner  WHERE NOT EXISTS (SELECT *  FROM t_contract_ad ad  WHERE ad.rule_id = mruleInner.id)) mrule  inner join t_release_area area on mrule.location_id = area.id and mrule.position_id=?) a INNER JOIN T_ADVERTPOSITION ap ON a.positionid=ap.id");
		}
		System.out.println(querySql.toString());
		return advertPositionDao.page4OccupyStates(querySql.toString(),status,positionId, start, end,startDate,endDate);
	}

	@Override
	public int queryCount4PositionOccupyStatus(Integer status,Integer positionId,String startDate,String endDate) {
		StringBuffer querySql = new StringBuffer();
		String sql = "";
		if(status.intValue()==1){
			//查询已销售记录
			querySql.append("SELECT COUNT(*) FROM (");
			
			//查询已销售记录
			//querySql.append("SELECT * FROM (SELECT * FROM (SELECT * FROM (  SELECT contractAd.Id contractAdId,contractAd.Contract_Id contractId,contractAd.Ad_Id contractAdPositionId,TO_CHAR(contractAd.Valid_Start,'yyyy-mm-dd') validStartDate,TO_CHAR(contractAd.Valid_End,'yyyy-mm-dd') validEndDate,contractAd.Rule_Id contractAdRuleId,contract.contract_number contractNumber,contract.contract_code contractCode,contract.custom_id customerId,contract.contract_name contractName,contract.effective_start_date effectiveStartDate,contract.effective_end_date effectiveEndDate,contract.status contractStatus FROM t_contract contract INNER JOIN t_contract_ad contractAd ON contractAd.Contract_Id=contract.id AND contractAd.ad_id=?) INNER JOIN (  SELECT mrule.id ruleIdP,mrule.rule_id ruleId,mrule.position_id positionId,mrule.rule_name ruleName,TO_CHAR(mrule.start_time,'hh24:mi:ss') startTime ,TO_CHAR(mrule.end_time,'hh24:mi:ss') endTime,mrule.location_id releaseAreaId,mrule.channel_id channelId,mrule.state marketingRuleStates,area.area_code releaseAreaCode,area.area_name areaName,area.parent_code parentCode FROM t_marketing_rule mrule INNER JOIN t_release_area area ON mrule.location_id=area.id and mrule.position_id=?) ON contractAdRuleId=ruleIdP) contractRule inner join t_customer customer on contractRule.customerId=customer.id) t inner join T_ADVERTPOSITION a on t.positionid=a.id ");

			sql = "SELECT CONTRACTAD.ID                 CONTRACTADID,\n" +
			"       CONTRACTAD.CONTRACT_ID        CONTRACTID,\n" + 
			"       CONTRACTAD.AD_ID              CONTRACTADPOSITIONID,\n" + 
			"       CONTRACTAD.VALID_START        VALIDSTARTDATE,\n" + 
			"       CONTRACTAD.VALID_END          VALIDENDDATE,\n" + 
			"       CONTRACTAD.RULE_ID            CONTRACTADRULEID,\n" + 
			"       CONTRACT.CONTRACT_NUMBER      CONTRACTNUMBER,\n" + 
			"       CONTRACT.CONTRACT_CODE        CONTRACTCODE,\n" + 
			"       CONTRACT.CUSTOM_ID            CUSTOMERID,\n" + 
			"       CONTRACT.CONTRACT_NAME        CONTRACTNAME,\n" + 
			"       CONTRACT.EFFECTIVE_START_DATE EFFECTIVESTARTDATE,\n" + 
			"       CONTRACT.EFFECTIVE_END_DATE   EFFECTIVEENDDATE,\n" + 
			"       CONTRACT.STATUS               CONTRACTSTATUS,\n" +
			"\t\t\t MRULE.ID RULEIDP,\n" + 
			"       MRULE.RULE_ID RULEID,\n" + 
			"       MRULE.POSITION_ID POSITIONID,\n" + 
			"       MRULE.RULE_NAME RULENAME,\n" + 
			"       MRULE.START_TIME STARTTIME,\n" + 
			"       MRULE.END_TIME ENDTIME,\n" + 
			"       MRULE.LOCATION_ID RELEASEAREAID,\n" + 
			"       MRULE.CHANNEL_ID CHANNELID,\n" + 
			"       MRULE.STATE MARKETINGRULESTATES,\n" + 
			"       AREA.AREA_CODE RELEASEAREACODE,\n" + 
			"       AREA.AREA_NAME AREANAME,\n" + 
			"       AREA.PARENT_CODE PARENTCODE\n" + 
			"  FROM T_CONTRACT CONTRACT\n" + 
			"  INNER JOIN T_CONTRACT_AD CONTRACTAD ON CONTRACTAD.CONTRACT_ID =\n" + 
			"                                         CONTRACT.ID\n" ; 
			if(positionId!=null){
				sql +="       AND CONTRACTAD.AD_ID = ";
				sql +=positionId;
			}
			
			if(StringUtils.isNotBlank(startDate)){
				sql +="  AND CONTRACT.Effective_Start_Date<=";
				sql +="str_to_date('"+startDate+"','%Y-%m-%d')";
			}
			
			if(StringUtils.isNotBlank(endDate)){
				sql +="  AND CONTRACT.Effective_End_Date<= " ;
				sql +="str_to_date('"+endDate+"','%Y-%m-%d')";
			}
			sql +="       LEFT JOIN T_MARKETING_RULE MRULE ON  CONTRACTAD.RULE_ID=MRULE.id LEFT JOIN  T_RELEASE_AREA AREA ON MRULE.LOCATION_ID = AREA.ID LEFT JOIN T_CUSTOMER CUSTOMER ON CONTRACT.CUSTOM_ID=CUSTOMER.ID LEFT JOIN T_ADVERTPOSITION A ON CONTRACTAD.AD_ID = A.ID";
			querySql.append(sql);
			//querySql.append("SELECT * FROM (SELECT * FROM (SELECT * FROM (  SELECT contractAd.Id contractAdId,contractAd.Contract_Id contractId,contractAd.Ad_Id contractAdPositionId,TO_CHAR(contractAd.Valid_Start,'yyyy-mm-dd') validStartDate,TO_CHAR(contractAd.Valid_End,'yyyy-mm-dd') validEndDate,contractAd.Rule_Id contractAdRuleId,contract.contract_number contractNumber,contract.contract_code contractCode,contract.custom_id customerId,contract.contract_name contractName,contract.effective_start_date effectiveStartDate,contract.effective_end_date effectiveEndDate,contract.status contractStatus FROM t_contract contract INNER JOIN t_contract_ad contractAd ON contractAd.Contract_Id=contract.id AND contractAd.ad_id=").append(positionId).append(") INNER JOIN (  SELECT mrule.id ruleIdP,mrule.rule_id ruleId,mrule.position_id positionId,mrule.rule_name ruleName,TO_CHAR(mrule.start_time,'hh24:mi:ss') startTime ,TO_CHAR(mrule.end_time,'hh24:mi:ss') endTime,mrule.location_id releaseAreaId,mrule.channel_id channelId,mrule.state marketingRuleStates,area.area_code releaseAreaCode,area.area_name areaName,area.parent_code parentCode FROM t_marketing_rule mrule INNER JOIN t_release_area area ON mrule.location_id=area.id and mrule.position_id=").append(positionId).append(") ON contractAdRuleId=ruleIdP) contractRule inner join t_customer customer on contractRule.customerId=customer.id) t inner join T_ADVERTPOSITION a on t.positionid=a.id");
			querySql.append(")");
		}else if(status.intValue()==2){
			//查询待销售记录
			querySql.append("SELECT COUNT(*) FROM (");
			//querySql.append("  SELECT '' contractAdId,'' contractId,'' contractAdPositionId,'' validStartDate,'' validEndDate,'' contractAdRuleId,'' contractNumber,'' contractCode,'' customerId,''  contractName,'' effectiveStartDate,'' effectiveEndDate,''  contractStatus,mrule.id ruleIdP,mrule.rule_id as ruleId,mrule.position_id as positionId,mrule.rule_name as ruleName, 	to_char(mrule.start_time, 'hh24:mi:ss') as startTime,	to_char(mrule.end_time, 'hh24:mi:ss') as endTime, 	mrule.location_id as releaseAreaId,	mrule.channel_id as channelId,	mrule.state marketingRuleStates,	area.area_code as releaseAreaCode,	area.area_name as areaName,	area.parent_code as parentCode,'' advertisersName	from (SELECT *	FROM t_marketing_rule mruleInner	WHERE NOT EXISTS (SELECT *	FROM t_contract_ad ad	WHERE ad.rule_id = mruleInner.id)) mrule	inner join t_release_area area on mrule.location_id = area.id and mrule.position_id=").append(positionId);
			querySql.append("  SELECT  A.*,AP.POSITION_NAME FROM (SELECT mrule.id ruleIdP,mrule.rule_id as ruleId,mrule.position_id as positionId,mrule.rule_name as ruleName, mrule.start_time as startTime,  mrule.end_time as endTime,   mrule.location_id as releaseAreaId,  mrule.channel_id as channelId,  mrule.state marketingRuleStates,  area.area_code as releaseAreaCode,  area.area_name as areaName,  area.parent_code as parentCode,'' advertisersName  from (SELECT *  FROM t_marketing_rule mruleInner  WHERE NOT EXISTS (SELECT *  FROM t_contract_ad ad  WHERE ad.rule_id = mruleInner.id)) mrule  inner join t_release_area area on mrule.location_id = area.id and mrule.position_id=").append(positionId).append(") a INNER JOIN T_ADVERTPOSITION ap ON a.positionid=ap.id");
			querySql.append(")");
		}else if(status.intValue()==3){
			//查询其他
			querySql.append("SELECT COUNT(*) FROM (");
			//querySql.append("  SELECT '' contractAdId,'' contractId,'' contractAdPositionId,'' validStartDate,'' validEndDate,'' contractAdRuleId,'' contractNumber,'' contractCode,'' customerId,''  contractName,'' effectiveStartDate,'' effectiveEndDate,''  contractStatus,mrule.id ruleIdP,mrule.rule_id as ruleId,mrule.position_id as positionId,mrule.rule_name as ruleName, 	to_char(mrule.start_time, 'hh24:mi:ss') as startTime,	to_char(mrule.end_time, 'hh24:mi:ss') as endTime, 	mrule.location_id as releaseAreaId,	mrule.channel_id as channelId,	mrule.state marketingRuleStates,	area.area_code as releaseAreaCode,	area.area_name as areaName,	area.parent_code as parentCode,'' advertisersName	from (SELECT *	FROM t_marketing_rule mruleInner	WHERE NOT EXISTS (SELECT *	FROM t_contract_ad ad	WHERE ad.rule_id = mruleInner.id)) mrule	inner join t_release_area area on mrule.location_id = area.id and mrule.position_id=").append(positionId);
			querySql.append("  SELECT * FROM (SELECT mrule.id ruleIdP,mrule.rule_id as ruleId,mrule.position_id as positionId,mrule.rule_name as ruleName,   mrule.start_time as startTime,  mrule.end_time as endTime,   mrule.location_id as releaseAreaId,  mrule.channel_id as channelId,  mrule.state marketingRuleStates,  area.area_code as releaseAreaCode,  area.area_name as areaName,  area.parent_code as parentCode,'' advertisersName  from (SELECT *  FROM t_marketing_rule mruleInner  WHERE NOT EXISTS (SELECT *  FROM t_contract_ad ad  WHERE ad.rule_id = mruleInner.id)) mrule  inner join t_release_area area on mrule.location_id = area.id and mrule.position_id=").append(positionId).append(") a INNER JOIN T_ADVERTPOSITION ap ON a.positionid=ap.id");
			querySql.append(")");
		}
		System.out.println(querySql.toString());
		return advertPositionDao.queryTotalCount(querySql.toString());
	}
	
	public List<String> generateCharacteristicIdentification(int type,String typeCode){
		List<String> eignValueList = new ArrayList<String>();
		eignValueList = new ArrayList<String>();
		if (0 == type) {
				eignValueList.add("sd_" + typeCode);
		}else if (1 == type) {
				eignValueList.add("hd_" + typeCode);
		}
		
		return eignValueList;
	}

	@Override
	public List<Integer> getAdvertPositionOccupyStatus(
			List<AdvertPosition> advertPositionList) {
		return advertPositionDao.getAdvertPositionOccupyStatus(advertPositionList);
	}

	@Override
	public int[] removeBatchAdvertPosition(String[] ids) {
		return advertPositionDao.removeBatchAdvertPosition(ids);
	}
}
