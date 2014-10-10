package com.dvnchina.advertDelivery.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.dao.ChannelInfoDao;
import com.dvnchina.advertDelivery.model.ChannelInfo;
import com.dvnchina.advertDelivery.utils.HibernateSQLTemplete;


public class ChannelInfoDaoImpl  extends HibernateSQLTemplete implements ChannelInfoDao {

	private Logger logger = Logger.getLogger(ChannelInfoDaoImpl.class);

	
	@Override
	public ChannelInfo getChannelInfoById(Integer id) {
		logger.debug("------getChannelInfoById()-----start----");
		return this.getHibernateTemplate().get(ChannelInfo.class, id);
		 
	}
	
	@Override
	public int deleteChannelInfo(ChannelInfo channelInfo) {
		logger.debug("------getChannelInfoById()-----start----");
		int count = 0;
		if(channelInfo != null && !"".equals(channelInfo)){
			this.getHibernateTemplate().delete(channelInfo);
			count = 1;
		}
		return count;
	}
	
	
	@Override
	public int getChannelInfoCount(ChannelInfo channelInfo, String state) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		int count = 0;
		
		String hql = "select count(*) from ChannelInfo where 1=1";
		hql += fillCond(map,channelInfo,state);
		count = this.getTotalByHQL(hql, map);
		
		if(count != 0){
			System.out.println(count);
		}
		return count;
		
	}

	@Override
	public List<ChannelInfo> listChannelInfoMgr(ChannelInfo channelInfo, int x,int y, String state) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		List<ChannelInfo> listChannelInfo = null;
		String hql = "from ChannelInfo where 1=1"; 
		hql += fillCond(map,channelInfo,state);
		listChannelInfo =(List<ChannelInfo>)this.findByHQL(hql, map,x,y);
		
		return listChannelInfo;
	}

	private String fillCond(Map<String, Object> map, ChannelInfo channelInfo,String state) {
		
		logger.debug("------fillCond()-----start----");
		
		StringBuffer sb = new StringBuffer("");
		
		//主键
		if(channelInfo.getChannelId() != null){
			map.put("channelId",channelInfo.getChannelId());
			sb.append(" AND channelId =:channelId");
		}
		
		
		//频道名称
		if(StringUtils.isNotBlank(channelInfo.getChannelName())){
			map.put("channelName","%" +channelInfo.getChannelName()+ "%");
			sb.append(" AND channelName like :channelName");
			
		}
		
		if(StringUtils.isNotBlank(channelInfo.getChannelType()) && !"10".equals(channelInfo.getChannelType())){
			
			if("0".equals(channelInfo.getChannelType())){
				map.put("channelType","视频直播类业务");
				sb.append(" AND channelType =:channelType");
				
			}
			
			if("1".equals(channelInfo.getChannelType())){
				map.put("channelType","视频直播类业务");
				sb.append(" AND channelType =:channelType");
			}
		}
		//区域名称
		if(StringUtils.isNotBlank(channelInfo.getLocationName())){
			map.put("locationName","%" +channelInfo.getLocationName()+ "%");
			sb.append(" AND locationName like :locationName");
	
		}
		
		if(StringUtils.isNotBlank(channelInfo.getLocationCode())){
			map.put("locationCode",channelInfo.getLocationCode());
			sb.append(" AND locationCode =:locationCode");
	
		}
		
		if(StringUtils.isNotBlank(channelInfo.getInfoState()) && !"10".equals(channelInfo.getInfoState())){
			map.put("state",channelInfo.getInfoState().charAt(0));
			sb.append(" AND state =:state");
		}
		
		//创建时间 前
		if(channelInfo.getCreateTimeA() != null && !"".equals(channelInfo.getCreateTimeA()) ){
			map.put("createTimeA",channelInfo.getCreateTimeA());
			sb.append(" AND createTime >=:createTimeA");
		}
		//创建时间 后
		if(channelInfo.getCreateTimeB() != null && !"".equals(channelInfo.getCreateTimeB()) ){
			map.put("createTimeB",channelInfo.getCreateTimeB());
			sb.append(" AND createTime <=:createTimeB");
		}
		
		sb.append(" order by createTime desc ");
		
		return sb.toString();
	}

}
