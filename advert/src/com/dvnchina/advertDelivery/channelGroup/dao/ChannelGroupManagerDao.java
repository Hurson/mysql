package com.dvnchina.advertDelivery.channelGroup.dao;

import java.util.Date;
import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.bean.contract.ContractQueryBean;
import com.dvnchina.advertDelivery.channelGroup.bean.ChannelGroupRef;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.contract.bean.AdvertPositionPackage;
import com.dvnchina.advertDelivery.contract.bean.PositionAD;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.MarketingRule;
import com.dvnchina.advertDelivery.model.Ploy;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.ploy.bean.PloyAreaChannel;
import com.dvnchina.advertDelivery.utils.page.PageBean;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;

// TODO: Auto-generated Javadoc
/**
 * The Interface ContractManagerDao.
 */
public interface ChannelGroupManagerDao {
    
   
	/**
	 * 查询频道组列表.
	 *
	 * @param contract the contract
	 * @param pageSize the page size
	 * @param pageNumber the page number
	 * @return the page bean db
	 */
	public PageBeanDB queryChanelGroupList(TChannelGroup channelGroup,Integer pageSize,Integer pageNumber);
	
	
   
    
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
    public boolean saveChannelGroup(TChannelGroup channelGroup);
    
    
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
	public boolean updateChannelGroup(TChannelGroup channelGroup);
    

    

    
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
	public TChannelGroup getChannelGroupByID(int id);
    
	
	public Integer getEntityCountByCode(String code);
  
	/**
	 * 查询频道列表
	 * @param channel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB queryChannelGroupRefList(ChannelInfo channel,Integer pageSize,Integer pageNumber);
	
	/**
	 * 查询频道选择列表
	 * @param channel
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public PageBeanDB selectChannelList(ChannelInfo channel,Integer pageSize,Integer pageNumber);
	
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
	public boolean saveChannelGroupRefList(List<ChannelGroupRef> channelGroupRefList);
	
	/**
	 * 删除频道组频道关联表
	 * @param ids
	 * @return
	 */
	public boolean deleteChannelGroupRef(String ids);
	
	/**
	 * 删除频道组
	 * @param ids
	 * @return
	 */
	public boolean deleteChannelGroup(String ids);
}
