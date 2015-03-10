package com.dvnchina.advertDelivery.awaitDoing.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.awaitDoing.dao.AwaitDoingDao;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.order.bean.Order;

public class AwaitDoingDaoImpl extends BaseDaoImpl implements AwaitDoingDao{

	/**
	 * 根据广告IDS获取问卷代办订单
	 * @param positionIds
	 * @param contractIds
	 * @return
	 */
	public List<Order> getQuestionnaireOrderList(String positionIds,String contractIds){
		String sql = " select o.id,o.order_code from t_order o where o.threshold_number is not NULL "
			+ " and o.threshold_number <= (select count(1) from t_user_questionnaire uq where uq.order_id=o.id) " 
			+ " and not EXISTS (select o.order_code from t_order_real re where re.order_id=o.id)";
		
		if(StringUtils.isNotBlank(positionIds)){
			sql += "and o.position_id in ("+positionIds+")";
		}
		if(StringUtils.isNotBlank(contractIds)){
			sql += "and o.contract_id in ("+contractIds+")";
		}
		List list = this.getDataBySql(sql, null);
		List<Order> orderList = new ArrayList<Order>();
		if(list != null && list.size()>0){
			int size = list.size();
			Order order = null;
			for(int i=0;i<size;i++){
				Object[] obj = (Object[])list.get(i);
				order = new Order();
				order.setId(toInteger(obj[0]));
				order.setOrderCode((String)obj[1]);
				orderList.add(order);
			}
		}
		return orderList;
	}
	
	/**
	 * 根据广告商ID获取子广告位ID和合同ID
	 * @param customerId
	 * @return
	 */
	public List<Object[]> getPositionContractByCustomer(Integer customerId){
		String sql = " select ap.id, ca.contract_id from t_contract c, t_contract_ad ca,t_advertposition ap "
			+ " where c.id=ca.contract_id and ca.ad_id=ap.position_package_id " 
			+ " and c.custom_id="+customerId.intValue();
		List list = this.getDataBySql(sql, null);
		return list;
	}

	@Override
	public List<String> getFreePositionRemind(String packageIds,String areaCodes, String startDateStr, String endDateStr) {
		String sql = "select a.position_name from t_advertposition a where a.position_package_id in(" + packageIds + ")"
				+ " and not exists (select 1 from t_order o, t_ploy p where a.id = o.position_id and o.ploy_id = p.ploy_id"
				+ " and p.area_id in(" + areaCodes + ") and o.start_time <= '" + startDateStr + "' and o.end_time >= '" + endDateStr + "')";
		List list = this.getDataBySql(sql, null);
		List<String> resultList = new ArrayList<String>();
		for(Object obj : list){
			resultList.add((String)obj);
		}
		return resultList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
