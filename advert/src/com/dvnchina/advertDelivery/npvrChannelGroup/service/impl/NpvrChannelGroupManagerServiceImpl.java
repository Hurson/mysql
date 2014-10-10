package com.dvnchina.advertDelivery.npvrChannelGroup.service.impl;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.NpvrChannelGroupRef;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.NpvrChannelInfo;
import com.dvnchina.advertDelivery.npvrChannelGroup.bean.TNpvrChannelGroup;
import com.dvnchina.advertDelivery.npvrChannelGroup.dao.NpvrChannelGroupManagerDao;
import com.dvnchina.advertDelivery.npvrChannelGroup.service.NpvrChannelGroupManagerService;

/**
 * 合同维护相关操作
 * @author lester
 *
 */
public class NpvrChannelGroupManagerServiceImpl implements NpvrChannelGroupManagerService{
	private NpvrChannelGroupManagerDao channelGroupManagerDao;
	
	
	public NpvrChannelGroupManagerDao getChannelGroupManagerDao() {
		return channelGroupManagerDao;
	}

	public void setChannelGroupManagerDao(
			NpvrChannelGroupManagerDao channelGroupManagerDao) {
		this.channelGroupManagerDao = channelGroupManagerDao;
	}

	/**
     * 查询频道组列表
     * @param channelGroup
     * @param pageSize
     * @param pageNumber
     * @return
     */
	public PageBeanDB queryChanelGroupList(TNpvrChannelGroup channelGroup,Integer pageSize,Integer pageNumber){
		return channelGroupManagerDao.queryChanelGroupList(channelGroup, pageSize, pageNumber);
	}
	
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
    public boolean saveChannelGroup(TNpvrChannelGroup channelGroup){
    	return channelGroupManagerDao.saveChannelGroup(channelGroup);
    }
    
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
	public boolean updateChannelGroup(TNpvrChannelGroup channelGroup){
		return channelGroupManagerDao.updateChannelGroup(channelGroup);
	}
	
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
	public TNpvrChannelGroup getChannelGroupByID(int id){
		return channelGroupManagerDao.getChannelGroupByID(id);
	}	
	
	@Override
	public Integer getEntityCountByCode(String code) {
		return channelGroupManagerDao.getEntityCountByCode(code);
	}

	/**
	 * 查询频道列表
	 * @param channel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB queryChannelGroupRefList(NpvrChannelInfo channel,Integer pageSize,Integer pageNumber){
		return channelGroupManagerDao.queryChannelGroupRefList(channel, pageSize, pageNumber);
	}
	
	/**
	 * 查询频道选择列表
	 * @param channel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB selectChannelList(NpvrChannelInfo channel,Integer pageSize,Integer pageNumber){
		return channelGroupManagerDao.selectChannelList(channel, pageSize, pageNumber);
	}
	
	
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
	public boolean saveChannelGroupRefList(List<NpvrChannelGroupRef> channelGroupRefList){
		return channelGroupManagerDao.saveChannelGroupRefList(channelGroupRefList);
	}
	
	/**
	 * 删除频道组频道关联表
	 * @param id
	 * @return
	 */
	public boolean deleteChannelGroupRef(String id) {
        boolean flag = channelGroupManagerDao.deleteChannelGroupRef("("+id+")");
        return flag;
    }
	
	/**
	 * 删除频道组
	 * @param ids
	 * @return
	 */
	public boolean deleteChannelGroup(String ids){
		boolean flag = channelGroupManagerDao.deleteChannelGroup("("+ids+")");
        return flag;
	}
	
}
