package com.dvnchina.advertDelivery.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.dao.AdvertPositionTypeDao;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.service.AdvertPositionTypeService;

public class AdvertPositionTypeServiceImpl implements AdvertPositionTypeService{
	
	private AdvertPositionTypeDao advertPositionTypeDao;
	
	@Override
	public List<AdvertPositionType> page(Map condition, int start, int end) {
		StringBuffer queryAdvertPositionType = new StringBuffer();
		queryAdvertPositionType.append("SELECT * FROM T_POSITION_TYPE");
		
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryAdvertPositionType.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				if ("java.lang.String".equals(columnName.getClass().getName())) {
					queryAdvertPositionType.append(columnName).append(" like")
							.append("'%").append(entry.getValue()).append("%'");
				}else{
					queryAdvertPositionType.append(columnName).append("=").append(entry.getValue());
				}				
				count++;
				if(count<=mapSize){
					queryAdvertPositionType.append(" AND ");
				}
			}
		}
		
		return advertPositionTypeDao.page(queryAdvertPositionType.toString(), start, end);
	}

	@Override
	public int queryCount(Map condition) {
		StringBuffer queryAdvertPositionType = new StringBuffer();
		queryAdvertPositionType.append("SELECT COUNT(*) FROM T_POSITION_TYPE");
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryAdvertPositionType.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				if ("java.lang.String".equals(columnName.getClass().getName())) {
					queryAdvertPositionType.append(columnName).append(" like")
							.append("'%").append(entry.getValue()).append("%'");
				}else{
					queryAdvertPositionType.append(columnName).append("=").append(entry.getValue());
				}
				count++;
				if(count<=mapSize){
					queryAdvertPositionType.append(" AND ");
				}
			}
		}
		return advertPositionTypeDao.queryTotalCount(queryAdvertPositionType.toString());
	}

	@Override
	public boolean save(AdvertPositionType object) {
		boolean flag = false;
		int operationCount = advertPositionTypeDao.save(object);
		if(operationCount>0){
			flag = true;
		}
		return flag;
	}

	public AdvertPositionTypeDao getAdvertPositionTypeDao() {
		return advertPositionTypeDao;
	}

	public void setAdvertPositionTypeDao(AdvertPositionTypeDao advertPositionTypeDao) {
		this.advertPositionTypeDao = advertPositionTypeDao;
	}

	@Override
	public boolean checkAdvertPositionType(String typeCode,Integer id) {
		boolean flag = false;
		List<AdvertPositionType> advertPositionTypeList = null;
		
		StringBuffer querySql = new StringBuffer();
		querySql.append("SELECT * FROM T_POSITION_TYPE");
		
		if(StringUtils.isNotBlank(typeCode)){
			querySql.append(" WHERE POSITION_TYPE_CODE=");
			querySql.append("'");
			querySql.append(typeCode);
			querySql.append("'");
		}
		
		advertPositionTypeList = advertPositionTypeDao.query(querySql.toString());
		
		if(advertPositionTypeList!=null&&advertPositionTypeList.size()>0){
			
			for (AdvertPositionType advertPositionType : advertPositionTypeList) {
				if (id!=null) {
					if (advertPositionType.getPositionTypeCode().equals(typeCode)
							&& advertPositionType.getId() != id) {
						flag = true;
					}
				}else{
					if (advertPositionType.getPositionTypeCode().equals(typeCode)) {
						flag = true;
					}
				}
			}
			
		}
		
		return flag;
	}

	@Override
	public boolean remove(int objectId) {
		boolean flag = false;
		int result = advertPositionTypeDao.remove(objectId);
		if(result>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean update(
			AdvertPositionType object) {
		boolean flag = false;
		int result = advertPositionTypeDao.update(object);
		if(result>0){
			flag = true;
		}
		return flag;
	}

	@Override
	public AdvertPositionType get(Integer id) {
		return advertPositionTypeDao.get(id);
	}

	@Override
	public List<AdvertPositionType> getAll() {
		return advertPositionTypeDao.query("SELECT * FROM T_POSITION_TYPE");		
	}
}
