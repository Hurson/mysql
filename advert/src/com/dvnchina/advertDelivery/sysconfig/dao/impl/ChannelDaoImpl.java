package com.dvnchina.advertDelivery.sysconfig.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;
import com.dvnchina.advertDelivery.sysconfig.dao.ChannelDao;

public class ChannelDaoImpl extends BaseDaoImpl implements ChannelDao {
	
	/**
	 * 分页查询频道信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryChannelList(ChannelInfo channel,int pageNo, int pageSize){
		StringBuffer hql = new StringBuffer("from ChannelInfo where 1=1");
		if(channel != null){
			if(!StringUtils.isEmpty(channel.getChannelName())){
				hql.append(" and channelName like '%").append(channel.getChannelName()).append("%' ");
			}
			if(!StringUtils.isEmpty(channel.getChannelCode())){
				hql.append(" and channelCode like '%").append(channel.getChannelCode()).append("%' ");
			}
		}
		
		int rowcount = this.getTotalCountHQL(hql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}

		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		hql.append("order by channelType,channelId");
		List<ChannelInfo> list = (List<ChannelInfo>)this.getListForPage(hql.toString(), null, pageNo, pageSize);
		page.setDataList(list);
		return page;
	}
	
	/**
	 * 根据频道ID获取频道信息
	 * @param channelId
	 * @return
	 */
	public ChannelInfo getChannelById(Integer channelId){
		return this.getHibernateTemplate().get(ChannelInfo.class, channelId);
	}
	
	/**
	 * 修改频道信息
	 * @param channel
	 */
	public void updateChannel(ChannelInfo channel){
		String updateSql = "update t_channelinfo set is_playback = ?,summaryshort = ? where channel_id = ?";
		this.executeBySQL(updateSql, new Object[]{channel.getIsPlayBack(),channel.getSummaryShort(),channel.getChannelId()});
	}

}
