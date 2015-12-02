package com.avit.dtmb.ploy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.ploy.dao.DPloyDao;
import com.avit.dtmb.ploy.service.DPloyService;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;

@Service("dPloyService")
public class DPloyServiceImpl implements DPloyService {
	
	@Resource
	private DPloyDao dPloyDao;

	@Override
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize) {
		
		return dPloyDao.queryDTMBPloyList(ploy, pageNo, pageSize);
	}

	@Override
	public void saveDTMBPloy(DPloy ploy) {
		dPloyDao.saveDTMBPloy(ploy);

	}
	
	@Override
	public List<DAdPosition> queryPositionList(){
		return null;
	}
	
	@Override
	public DAdPosition getPositionByCode(String positionCode){
		return null;
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

}
