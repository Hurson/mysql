package com.avit.dtmb.material.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.material.dao.MaterialDao;
import com.avit.dtmb.material.service.MaterialService;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
@Service("MaterialService")
public class MaterialServiceImpl implements MaterialService{
	@Resource
	private MaterialDao materialDao;

	@Override
	public PageBeanDB queryDMaterialList(DResource meterialQuery,int pageNo, int pageSize) {
		return materialDao.queryDMaterialList(meterialQuery,pageNo, pageSize);
	}

	@Override
	public PageBeanDB queryPositonList(int pageNo, int pageSize) {
		return materialDao.queryPosisonList(pageNo, pageSize);
	}

	@Override
	public VideoSpecification getVideoSpc(Integer advertPositionId) {
		return materialDao.getVideoSpc(advertPositionId);
	}

	@Override
	public ImageSpecification getImageMateSpeci(Integer advertPositionId) {
		return materialDao.getImageMateSpeci(advertPositionId);
	}

	@Override
	public void saveDResource(DResource materialTemp) {
		materialDao.saveDResource(materialTemp);
	}

	@Override
	public VideoMeta getVideoMetaByID(Integer id) {
		return materialDao.getVideoMetaByID(id);
	}

	@Override
	public DResource getMaterialByID(int materialId) {
		return materialDao.getMaterialByID(materialId);
	}

	@Override
	public ImageMeta getImageMetaByID(Integer resourceId) {
		return materialDao.getImageMetaByID(resourceId);
	}
}
