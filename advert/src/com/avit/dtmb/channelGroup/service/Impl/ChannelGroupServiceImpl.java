package com.avit.dtmb.channelGroup.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.avit.dtmb.channelGroup.bean.DChannelGroup;
import com.avit.dtmb.channelGroup.bean.DChannelGroupRef;
import com.avit.dtmb.channelGroup.bean.DChannelInfoSync;
import com.avit.dtmb.channelGroup.dao.ChannelGroupDao;
import com.avit.dtmb.channelGroup.service.ChannelGroupService;
import com.avit.dtmb.material.dao.MaterialDao;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.ChannelGroupRef;
@Service("ChannelGroupService")
public class ChannelGroupServiceImpl implements ChannelGroupService {
	@Resource
	private ChannelGroupDao channelGroupDao;
	@Override
	public PageBeanDB queryChanelGroupList(DChannelGroup channelGroupQuery,
			int pageSize, int pageNo) {
		return channelGroupDao.queryChanelGroupList(channelGroupQuery, pageSize, pageNo);
	}
	@Override
	public Integer getEntityCountByCode(String code) {
		// TODO Auto-generated method stub
		return channelGroupDao.getEntityCountByCode(code);
	}
	@Override
	public boolean updateChannelGroup(DChannelGroup channelGroup) {
		return channelGroupDao.updateChannelGroup(channelGroup);
	}
	@Override
	public boolean saveChannelGroup(DChannelGroup channelGroup) {
		return channelGroupDao.saveChannelGroup(channelGroup);
	}
	@Override
	public boolean deleteChannelGroup(String ids) {
		boolean flag = channelGroupDao.deleteChannelGroup("("+ids+")");
        return flag;
	}
	@Override
	public PageBeanDB queryChannelGroupRefList(DChannelInfoSync channelQuery,
			int pageSize, int pageNo) {
		return channelGroupDao.queryChannelGroupRefList(channelQuery, pageSize, pageNo);
	}
	@Override
	public PageBeanDB selectChannelList(DChannelInfoSync selectChannelQuery,
			int pageSize, int pageNo, String channelGroupType) {
		return channelGroupDao.selectChannelList(selectChannelQuery, pageSize, pageNo,channelGroupType);
	}
	@Override
	public boolean saveChannelGroupRefList(
			List<DChannelGroupRef> channelGroupRefList) {
		return channelGroupDao.saveChannelGroupRefList(channelGroupRefList);
	}
	@Override
	public boolean deleteChannelGroupRef(String dataIds) {
		boolean flag = channelGroupDao.deleteChannelGroupRef("("+dataIds+")");
        return flag;
	}
	@Override
	public DChannelGroup getChannelGroupByID(Long channelGroupId) {
		return channelGroupDao.getChannelGroupByID(channelGroupId);
	}

}
