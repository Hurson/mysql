package com.avit.dtmb.ploy.dao;

import java.util.List;

import com.avit.dtmb.channelGroup.bean.DChannelGroup;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;

public interface DPloyDao extends BaseDao {
	/**
	 * 
	 * @return
	 */
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize);
	
	public void saveDTMBPloy(DPloy ploy);
	
	public DPloy getDTMBPloy(Integer id);
	
	public List<DAdPosition> queryPositionList();	
	
	public DAdPosition getPositionByCode(String positionCode);
	
	public List<ReleaseArea> listReleaseArea();
	
	public DPloy getDPloyByName(String ployName);
	
	public List<ReleaseArea> getUserAccessArea(Integer userId);
	
	public PageBeanDB queryUserIndustryList(UserIndustryCategory userIndustryCategory, int pageNo, int pageSize);
	
	public List<Integer> checkOrderRelPloy(Integer ployId);
	
	public PageBeanDB queryChanelGroupList(DChannelGroup channelGroup, int pageNo, int pageSize);

}
