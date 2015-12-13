package com.avit.dtmb.ploy.service;

import java.util.List;

import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;

public interface DPloyService {

	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize);
	
	public void saveDTMBPloy(DPloy ploy);
	
	public DPloy getDTMBPloy(Integer id);
	
	public void deleteDTMBPloy(List<String> ids);
	
	public List<DAdPosition> queryPositionList();	
	
	public DAdPosition getPositionByCode(String positionCode);
	
	public List<ReleaseArea> listReleaseArea();
	
	public String checkDPloyName(DPloy ploy);
	
	public PageBeanDB queryUserIndustryList(UserIndustryCategory userIndustryCategory, int pageNo, int pageSize);
}
