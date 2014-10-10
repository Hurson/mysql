package com.dvnchina.advertDelivery.channelGroup.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.channelGroup.bean.ChannelGroupRef;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.channelGroup.dao.ChannelGroupManagerDao;
import com.dvnchina.advertDelivery.channelGroup.service.ChannelGroupManagerService;
import com.dvnchina.advertDelivery.contract.bean.AdvertPositionPackage;
import com.dvnchina.advertDelivery.contract.bean.PositionAD;
import com.dvnchina.advertDelivery.contract.dao.ContractManagerDao;
import com.dvnchina.advertDelivery.contract.service.ContractManagerService;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;

/**
 * 合同维护相关操作
 * @author lester
 *
 */
public class ChannelGroupManagerServiceImpl implements ChannelGroupManagerService{
	private ChannelGroupManagerDao channelGroupManagerDao;
	
	
	public ChannelGroupManagerDao getChannelGroupManagerDao() {
		return channelGroupManagerDao;
	}

	public void setChannelGroupManagerDao(
			ChannelGroupManagerDao channelGroupManagerDao) {
		this.channelGroupManagerDao = channelGroupManagerDao;
	}

	
	/**
     * 查询频道组列表
     * @param channelGroup
     * @param pageSize
     * @param pageNumber
     * @return
     */
	public PageBeanDB queryChanelGroupList(TChannelGroup channelGroup,Integer pageSize,Integer pageNumber){
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
    public boolean saveChannelGroup(TChannelGroup channelGroup){
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
	public boolean updateChannelGroup(TChannelGroup channelGroup){
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
	public TChannelGroup getChannelGroupByID(int id){
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
	public PageBeanDB queryChannelGroupRefList(ChannelInfo channel,Integer pageSize,Integer pageNumber){
		return channelGroupManagerDao.queryChannelGroupRefList(channel, pageSize, pageNumber);
	}
	
	/**
	 * 查询频道选择列表
	 * @param channel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB selectChannelList(ChannelInfo channel,Integer pageSize,Integer pageNumber){
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
	public boolean saveChannelGroupRefList(List<ChannelGroupRef> channelGroupRefList){
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
