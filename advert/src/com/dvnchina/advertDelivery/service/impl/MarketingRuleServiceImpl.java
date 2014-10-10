package com.dvnchina.advertDelivery.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dvnchina.advertDelivery.bean.marketingRule.MarketingRuleBean;
import com.dvnchina.advertDelivery.dao.MarketingRuleDao;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.service.MarketingRuleService;

public class MarketingRuleServiceImpl implements MarketingRuleService{

	private MarketingRuleDao marketingRuleDao;
	
	@Override
	public Map<String, String> insertMarketingRule(MarketingRule marketingRule) {
		Map<String,String> resultMap = new HashMap<String,String>();
		String handleFlag = "";
		try {
			marketingRuleDao.insertMarketingRule(marketingRule);
			handleFlag = "true";
		} catch (RuntimeException e) {
			handleFlag = "false";
			e.printStackTrace();
		}
		resultMap.put("flag", handleFlag);
		return resultMap;
	}

	@Override
	public Map<String, String> updateMarketingRule(MarketingRule marketingRule) {
		Map<String,String> resultMap = new HashMap<String,String>();
		String handleFlag = "";
		try {
			marketingRuleDao.updateMarketingRule(marketingRule);
			handleFlag = "true";
		} catch (RuntimeException e) {
			handleFlag = "false";
			e.printStackTrace();
		}
		resultMap.put("flag", handleFlag);
		return resultMap;
	}

	@Override
	public void deleteMarketingRuleById(String marketingRuleId) {
		marketingRuleDao.deleteMarketingRuleById(marketingRuleId);
	}
	
	@Override
	public void upLineMarketingRule(String marketingRuleId) {
		marketingRuleDao.upLineMarketingRule(marketingRuleId);
	}
	
	@Override
	public void downLineMarketingRule(String marketingRuleId) {
		marketingRuleDao.downLineMarketingRule(marketingRuleId);
	}

	@Override
	public List<MarketingRuleBean> getAllMarketingRule(int begin, int end) {
		return marketingRuleDao.getAllMarketingRule(begin,end);
	} 

	@Override
	public List<MarketingRuleBean> getMarketingRuleById(Integer marketingRuleId) {
		return marketingRuleDao.getMarketingRuleById(marketingRuleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getMarketingRuleCount(Map condition) {
		
		StringBuffer query = new StringBuffer();
		String conditionStr = "";
		query.append("SELECT COUNT(*) FROM (");
		query.append("SELECT * from (SELECT tmp.*, row_number() over(partition by tmp.ruleid order by tmp.ruleid desc) mm FROM (SELECT t.Rule_Id RULEID,t.RULE_NAME RULENAME, t.START_TIME STARTTIME, t.END_TIME ENDTIME, t.position_id, t.create_time createTime, t.state FROM t_marketing_rule t");
		//query.append("  SELECT marketRuleAdpC.*,releaseArea.Area_Name areaName FROM (SELECT marketRuleAdp.*,channel.channel_name channelName FROM (SELECT marketRule.id id,marketRule.rule_name ruleName,marketRule.start_time startTime, marketRule.end_time endTime,marketRule.create_time createTime,marketRule.state state,advertPosition.position_name positionName,marketRule.channel_id,marketRule.location_id FROM t_marketing_rule marketRule INNER JOIN t_advertposition advertPosition ON ");
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				Object object = entry.getValue();
				
				
				if(conditionStr.contains("WHERE")){
					conditionStr+=" AND ";
				}else{
					conditionStr+=" WHERE ";
				}
				
				if ("java.lang.String".equals(object.getClass().getName())) {
					if ("RULE_NAME".equals(columnName)) {
						conditionStr+=columnName;
						conditionStr+=" like '%";
						conditionStr+=object;
						conditionStr+="%' ";
					}else{
						conditionStr+=columnName;
						conditionStr+="='";
						conditionStr+=object;
						conditionStr+="' ";
					}
				}else{
					conditionStr+=columnName;
					conditionStr+="=";
					conditionStr+=object;
					conditionStr+=" ";
				}
				
			}
			
		}
		//query.append(" marketRule.Position_Id=advertPosition.id) marketRuleAdp INNER JOIN t_channelinfo channel ON marketRuleAdp.channel_id=channel.CHANNEL_ID) marketRuleAdpC INNER JOIN t_release_area releaseArea ON marketRuleAdpC.location_id=releaseArea.id");
		query.append(conditionStr);
		query.append(") tmp) WHERE mm = 1");
		query.append(" )");
		return marketingRuleDao.getMarketingRuleCount(query.toString());
	}

	public MarketingRuleDao getMarketingRuleDao() {
		return marketingRuleDao;
	}

	public void setMarketingRuleDao(MarketingRuleDao marketingRuleDao) {
		this.marketingRuleDao = marketingRuleDao;
	}

	@Override
	public int getAreaCount(String positionId) {
		return marketingRuleDao.getAreaCount(positionId);
	}
	
	@Override
	public int getAreaCount() {
		return marketingRuleDao.getAreaCount();
	}
	
	@Override
	public List<MarketingRuleBean> getAreaList(String positionId,final int begin,final int end) {
		return marketingRuleDao.getAreaList(positionId, begin, end);
	} 
	
	@Override
	public List<MarketingRuleBean> getAreaList(final int begin,final int end) {
		return marketingRuleDao.getAreaList(begin, end);
	}
	
	@Override
	public int getRuleCount(String positionId) {
		return marketingRuleDao.getRuleCount(positionId);
	}
	
	@Override
	public List<MarketingRuleBean> getRuleList(String positionId,final int begin,final int end) {
		return marketingRuleDao.getRuleList(positionId, begin, end);
	} 
	
	@Override
	public int getChannelCount(String positionId, String areaId) {
		return marketingRuleDao.getChannelCount(positionId,areaId);
	}

	@Override
	public int getChannelCount(String positionId) {
		return marketingRuleDao.getChannelCount(positionId);
	}

	@Override
	public int getChannelCount() {
		return marketingRuleDao.getChannelCount();
	}

	@Override
	public List<MarketingRuleBean> getChannelList(String positionId,
			String areaId, int begin, int end) {
		return marketingRuleDao.getChannelList(positionId, areaId, begin, end);
	}

	@Override
	public List<MarketingRuleBean> getChannelList(String positionId, int begin,
			int end) {
		return marketingRuleDao.getChannelList(positionId, begin, end);
	}

	@Override
	public List<MarketingRuleBean> getChannelList(int begin, int end) {
		return marketingRuleDao.getChannelList(begin, end);
	} 

	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingRuleBean> page(Map condition, int start, int end) {
		StringBuffer query = new StringBuffer();
		String conditionStr = "";
		query.append("SELECT * from (SELECT tmp.*, row_number() over(partition by tmp.ruleid order by tmp.ruleid desc) mm FROM (SELECT t.Rule_Id RULEID,t.RULE_NAME RULENAME, t.START_TIME STARTTIME, t.END_TIME ENDTIME, t.position_id, t.create_time createTime, t.state FROM t_marketing_rule t");
		//query.append("  SELECT marketRuleAdpC.*,releaseArea.Area_Name areaName FROM (SELECT marketRuleAdp.*,channel.channel_name channelName FROM (SELECT marketRule.id id,marketRule.rule_name ruleName,marketRule.start_time startTime, marketRule.end_time endTime,marketRule.create_time createTime,marketRule.state state,advertPosition.position_name positionName,marketRule.channel_id,marketRule.location_id FROM t_marketing_rule marketRule INNER JOIN t_advertposition advertPosition ON ");
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				Object object = entry.getValue();
				
				if(conditionStr.contains("WHERE")){
					conditionStr+=" AND ";
				}else{
					conditionStr+=" WHERE ";
				}
				
				if ("java.lang.String".equals(object.getClass().getName())) {
					if ("RULE_NAME".equals(columnName)) {
						conditionStr+=columnName;
						conditionStr+=" like '%";
						conditionStr+=object;
						conditionStr+="%' ";
					}else{
						conditionStr+=columnName;
						conditionStr+="='";
						conditionStr+=object;
						conditionStr+="' ";
					}
				}else{
					conditionStr+=columnName;
					conditionStr+="=";
					conditionStr+=object;
					conditionStr+=" ";
				}
			}
		}
		query.append(conditionStr);
		query.append(") tmp) WHERE mm = 1");
		//query.append(" marketRule.Position_Id=advertPosition.id) marketRuleAdp LEFT JOIN t_channelinfo channel ON marketRuleAdp.channel_id=channel.CHANNEL_ID) marketRuleAdpC LEFT JOIN t_release_area releaseArea ON marketRuleAdpC.location_id=releaseArea.id");
		return marketingRuleDao.page(query.toString(), start, end);
	}
	
}