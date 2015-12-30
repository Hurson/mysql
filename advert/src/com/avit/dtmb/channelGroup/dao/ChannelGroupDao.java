package com.avit.dtmb.channelGroup.dao;

import java.util.List;

import com.avit.dtmb.channelGroup.bean.DChannelGroup;
import com.avit.dtmb.channelGroup.bean.DChannelGroupRef;
import com.avit.dtmb.channelGroup.bean.DChannelInfoSync;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.ChannelGroupRef;

public interface ChannelGroupDao {

	PageBeanDB queryChanelGroupList(DChannelGroup channelGroupQuery,
			int pageSize, int pageNo);
	
	public Integer getEntityCountByCode(String code);

	boolean updateChannelGroup(DChannelGroup channelGroup);

	boolean saveChannelGroup(DChannelGroup channelGroup);

	boolean deleteChannelGroup(String ids);

	PageBeanDB queryChannelGroupRefList(DChannelInfoSync channelQuery,
			int pageSize, int pageNo);

	PageBeanDB selectChannelList(DChannelInfoSync selectChannelQuery,
			int pageSize, int pageNo, String channelGroupType);

	boolean saveChannelGroupRefList(List<DChannelGroupRef> channelGroupRefList);

	boolean deleteChannelGroupRef(String ids);

	DChannelGroup getChannelGroupByID(Long channelGroupId);

}
