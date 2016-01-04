package com.avit.dtmb.material.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
	public VideoSpecification getVideoSpc(Integer id) {
		return (VideoSpecification)materialDao.get(VideoSpecification.class, id);
	}

	@Override
	public ImageSpecification getImageMateSpeci(Integer id) {
		return (ImageSpecification)materialDao.get(ImageSpecification.class, id);
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

	@Override
	public String checkMaterialExist(DResource resource) {
		DResource res = materialDao.getDRsourceByName(resource.getResourceName());
		if(res == null){
			return "0";
		}else if(res.getId().equals(resource.getId())){
			return "0";
		}
		return "1";
	}

	@Override
	public void deleteMaterial(String ids) {
		if(StringUtils.isNotBlank(ids)){
			for(String id : ids.split(",")){
				materialDao.deleteObj(DResource.class.getName(), Integer.valueOf(id));
			}
		}
		
	}
}
