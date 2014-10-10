package com.dvnchina.advertDelivery.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dvnchina.advertDelivery.dao.PlayCategoryDao;
import com.dvnchina.advertDelivery.model.PlayCategory;
import com.dvnchina.advertDelivery.service.PlayCategoryService;

public class PlayCategoryServiceImpl implements PlayCategoryService {
	
	private PlayCategoryDao playCategoryDao;
	
	@Override
	public List<PlayCategory> page(Map condition, int start, int end) {
		StringBuffer queryPlayColumn = new StringBuffer();
		queryPlayColumn.append("SELECT * FROM T_PLAT_CATEGORY");
		
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryPlayColumn.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				queryPlayColumn.append(columnName).append("=").append(entry.getValue());
				count++;
				if(count<mapSize){
					queryPlayColumn.append(" AND ");
				}
			}
		}
		
		return playCategoryDao.page(queryPlayColumn.toString(), start, end);
	}

	@Override
	public int queryCount(Map condition) {
		StringBuffer queryPlayColumn = new StringBuffer();
		queryPlayColumn.append("SELECT COUNT(*) FROM T_PLAT_CATEGORY");
		
		if(condition!=null&&condition.size()>0){
			Set conditionSet = condition.entrySet();
			int mapSize = conditionSet.size();
			int count = 1;
			queryPlayColumn.append(" WHERE ");
			for (Iterator iterator = conditionSet.iterator(); iterator
					.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String columnName = (String)entry.getKey();
				queryPlayColumn.append(columnName).append("=").append(entry.getValue());
				count++;
				if(count<mapSize){
					queryPlayColumn.append(" AND ");
				}
			}
		}
		return playCategoryDao.queryTotalCount(queryPlayColumn.toString());
	}

	public PlayCategoryDao getPlayCategoryDao() {
		return playCategoryDao;
	}

	public void setPlayCategoryDao(PlayCategoryDao playCategoryDao) {
		this.playCategoryDao = playCategoryDao;
	}

	@Override
	public String savePlayCategory(PlayCategory playCategory) {
		playCategoryDao.savePlayCategory(playCategory);
		return null;
	}
	
	
	
}
