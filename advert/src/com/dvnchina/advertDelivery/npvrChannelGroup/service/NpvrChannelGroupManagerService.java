package com.dvnchina.advertDelivery.npvrChannelGroup.service;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.NpvrChannelGroupRef;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.NpvrChannelInfo;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.TNpvrChannelGroup;

public interface NpvrChannelGroupManagerService {
    
   
    /**
     * 查询频道组列表
     * @param channelGroup
     * @param pageSize
     * @param pageNumber
     * @return
     */
	public PageBeanDB queryChanelGroupList(TNpvrChannelGroup channelGroup,Integer pageSize,Integer pageNumber);
    
	/**
	 * 
	 * @description: 保存频道组表
	 * @param channelGroup
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 下午06:04:50
	 */
    public boolean saveChannelGroup(TNpvrChannelGroup channelGroup);
    
    /**
	 * 
	 * @description: 修改频道组表
	 * @param channelGroup
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 下午06:04:50
	 */
	public boolean updateChannelGroup(TNpvrChannelGroup channelGroup);
	
	/**
	 * 
	 * @description: 根据ID获取频道组信息
	 * @param id
	 * @return 
	 * TChannelGroup
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 上午11:29:00
	 */
	public TNpvrChannelGroup getChannelGroupByID(int id);
	
	public Integer getEntityCountByCode(String code);
	
	/**
	 * 查询频道列表
	 * @param channel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB queryChannelGroupRefList(NpvrChannelInfo channel,Integer pageSize,Integer pageNumber);
    
	/**
	 * 查询频道选择列表
	 * @param channel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB selectChannelList(NpvrChannelInfo channel,Integer pageSize,Integer pageNumber);
	
	
	/**
	 * 
	 * @description: 批量保存频道组频道关联表
	 * @param contractADList
	 * @return 
	 * boolean
	 *
	 * @author: wangfei@avit.com.cn
	 * @date: 2013-5-6 上午10:17:47
	 */
	public boolean saveChannelGroupRefList(List<NpvrChannelGroupRef> channelGroupRefList);
	
	/**
	 * 删除频道组频道关联表
	 * @param id
	 * @return
	 */
	public boolean deleteChannelGroupRef(String id);
	
	/**
	 * 删除频道组
	 * @param ids
	 * @return
	 */
	public boolean deleteChannelGroup(String ids);
	
}
