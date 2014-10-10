package com.dvnchina.advertDelivery.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.dao.ReleaseAreaDao;
import com.dvnchina.advertDelivery.model.CustomerBackUp;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;

public class ReleaseAreaDaoImpl extends HibernateSQLTemplete implements ReleaseAreaDao {

	private Logger logger = Logger.getLogger(ReleaseAreaDaoImpl.class);
	
	
	@Override
	public ReleaseArea getReleaseAreaById(Integer id) {
		logger.debug("------getCustomerBackUpById()-----start----");
		return getHibernateTemplate().get(ReleaseArea.class, id);
	}
	
	
	@Override
	public int deleteReleaseArea(ReleaseArea releaseArea) {
		logger.debug("------deleteReleaseArea()-----start----");
		int count = 0;
		if( releaseArea != null && !"".equals(releaseArea)){
			this.getHibernateTemplate().delete(releaseArea);
			count = 1;
		}
		return count;
	}
	
	
	@Override
	public List<ReleaseArea> listReleaseAreaMgr(ReleaseArea releaseArea, int x,int y, String state) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		List<ReleaseArea> listReleaseArea = null;
		
		String hql = "from ReleaseArea where 1=1 "; 
		
		hql += fillCond(releaseArea,map,state);
		
		listReleaseArea = (List<ReleaseArea>)this.findByHQL(hql, map, x, y);
		
		return listReleaseArea;
	}
	
	@Override
	public int getReleaseAreaCount(ReleaseArea releaseArea, String state) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int count = 0;
		
		String hql ="select count(*) from ReleaseArea where 1=1";
		
		hql += this.fillCond(releaseArea, map, state);
		
		count = this.getTotalByHQL(hql,map);
		
		
		return count;
	}
	
	
	
	
	private String fillCond(ReleaseArea releaseArea, Map<String, Object> map,String state) {
		
		StringBuffer sb = new StringBuffer();
		
		if(StringUtils.isNotBlank(releaseArea.getAreaCode())){
			map.put("areaCode", "%" +releaseArea.getAreaCode()+ "%");
			sb.append("AND areaCode like:areaCode");
		}
		
		if(StringUtils.isNotBlank(releaseArea.getAreaName())){
			map.put("areaName", "%" +releaseArea.getAreaName()+ "%");
			sb.append("AND areaName like:areaName");
		}
		
		return sb.toString();
		
	}

	@Override
	public ReleaseArea getReleaseAreaByreleaseAreaCode(String releaseAreaCode) {
		
		ReleaseArea releaseArea = null;
		List<ReleaseArea> listReleaseArea = null;
		String hql = "from ReleaseArea as tu where tu.areaCode='"+releaseAreaCode+"'";
		listReleaseArea = this.getHibernateTemplate().find(hql);
		
		if(listReleaseArea != null && listReleaseArea.size()>0){
			releaseArea = listReleaseArea.get(0);
		}else{
			releaseArea = null;
		}
		return releaseArea;
	}

}
