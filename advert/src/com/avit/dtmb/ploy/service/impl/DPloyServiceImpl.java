package com.avit.dtmb.ploy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.ploy.bean.DPloyDetail;
import com.avit.dtmb.ploy.dao.DPloyDao;
import com.avit.dtmb.ploy.service.DPloyService;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.sysconfig.bean.UserIndustryCategory;

@Service("dPloyService")
public class DPloyServiceImpl implements DPloyService {
	
	@Resource
	private DPloyDao dPloyDao;

	@SuppressWarnings("unchecked")
	@Override
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize) {
		
		PageBeanDB page = dPloyDao.queryDTMBPloyList(ploy, pageNo, pageSize);
		List<DPloy> dataList = (List<DPloy>)page.getDataList();
		if(dataList != null && dataList.size() > 0){
			for(DPloy dploy : dataList){
				if("2".equals(dploy.getStatus()) && checkOrderRel(dploy.getId())){
					dploy.setStatus("4");
				}
				if("4".equals(dploy.getStatus()) && !checkOrderRel(dploy.getId())){
					dploy.setStatus("2");
				}
			}
		}
		return page;
	}

	@Override
	public void saveDTMBPloy(DPloy ploy) {
		List<DPloyDetail> detailList = ploy.getPloyDetailList();
		if(detailList == null){
			detailList = new ArrayList<DPloyDetail>();
			ploy.setPloyDetailList(detailList);
		}
		
		boolean timeFlag = false;
		boolean areaFlag = false;
		
		for(DPloyDetail detail : detailList){
			if(detail == null){
				continue;
			}
			if("1".equals(detail.getPloyType())){
				areaFlag = true;
			}
			if("2".equals(detail.getPloyType())){
				timeFlag = true;
			}
		}
		if(!areaFlag){
			List<ReleaseArea> accessAreas = dPloyDao.getUserAccessArea(ploy.getOperatorId());
			for(ReleaseArea area : accessAreas){
				DPloyDetail detail = new DPloyDetail();
				detail.setPloyType("1");
				detail.setTypeValue(area.getAreaCode());
				detail.setValueName(area.getAreaName());
				detailList.add(detail);
			}
			
		}
		if(!timeFlag){
			DPloyDetail detail = new DPloyDetail();
			detail.setPloyType("2");
			detail.setTypeValue("00:00:00");
			detail.setValueName("23:59:59");
			detailList.add(detail);
		}
		
		dPloyDao.saveDTMBPloy(ploy);

	}
	
	@Override
	public List<DAdPosition> queryPositionList(){
		return dPloyDao.queryPositionList();
	}
	
	@Override
	public DAdPosition getPositionByCode(String positionCode){
		return dPloyDao.getPositionByCode(positionCode);
	}

	@Override
	public DPloy getDTMBPloy(Integer id) {
		
		return dPloyDao.getDTMBPloy(id);
	}

	@Override
	public void deleteDTMBPloy(List<String> ids) {
		if(ids != null){
			for(String id: ids){
				dPloyDao.deleteDTMBPloy(Integer.valueOf(id));
			}
		}

	}
	@Override
	public List<ReleaseArea> listReleaseArea(){
		return dPloyDao.listReleaseArea();
	}

	@Override
	public String checkDPloyName(DPloy ploy) {
		DPloy dploy = dPloyDao.getDPloyByName(ploy.getPloyName());
		if(dploy != null && !dploy.getId().equals(ploy.getId())){
			return "1";
		}
		return "0";
	}

	@Override
	public PageBeanDB queryUserIndustryList(UserIndustryCategory userIndustryCategory, int pageNo, int pageSize) {
		return dPloyDao.queryUserIndustryList(userIndustryCategory, pageNo, pageSize);
	}
	private boolean checkOrderRel(Integer ployId){
		List<Integer> list = dPloyDao.checkOrderRelPloy(ployId);
		return (list!= null && list.size() > 0);
	}

	@Override
	public PageBeanDB queryChanelGroupList(TChannelGroup channelGroup, int pageNo, int pageSize) {
		return dPloyDao.queryChanelGroupList(channelGroup, pageNo, pageSize);
	}

}
