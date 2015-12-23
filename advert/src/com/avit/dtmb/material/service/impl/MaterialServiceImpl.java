package com.avit.dtmb.material.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.material.dao.MaterialDao;
import com.avit.dtmb.material.service.MaterialService;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.TextSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
import com.google.gson.Gson;
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

	@Override
	public String getMaterialSpecification(DAdPosition position) {
		String result = "";
		String resourceType = position.getResourceType();
		Object spec = null;
		if("0".equals(resourceType)){
			spec = materialDao.get(ImageSpecification.class, position.getSpecificationId());
			
		}else if("1".equals(resourceType)){
			spec = materialDao.get(VideoSpecification.class, position.getSpecificationId());
			
		}else if("2".equals(resourceType)){
			spec = materialDao.get(TextSpecification.class, position.getSpecificationId());
			
		}
		Gson gson = new Gson();
		result = gson.toJson(spec);
		return result;
	}
}
