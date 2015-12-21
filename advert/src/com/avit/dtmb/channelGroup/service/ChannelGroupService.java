package com.avit.dtmb.channelGroup.service;

import com.avit.dtmb.channelGroup.bean.DChannelGroup;
import com.avit.dtmb.channelGroup.bean.DChannelInfoSync;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;

public interface ChannelGroupService {

	public PageBeanDB queryChanelGroupList(DChannelGroup channelGroupQuery,
			int pageSize, int pageNo);
	
	public Integer getEntityCountByCode(String code);

	public boolean updateChannelGroup(DChannelGroup channelGroup);

	public boolean saveChannelGroup(DChannelGroup channelGroup);

	/**
	 * 删除频道组
	 * @param ids
	 * @return
	 */
	public boolean deleteChannelGroup(String ids);

	public PageBeanDB queryChannelGroupRefList(DChannelInfoSync channelQuery,
			int pageSize, int pageNo);

	public PageBeanDB selectChannelList(DChannelInfoSync selectChannelQuery,
			int pageSize, int pageNo, String channelGroupType);

}
