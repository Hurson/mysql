package com.avit.dtmb.material.service;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;

public interface MaterialService {
	public PageBeanDB queryDMaterialList(DResource meterialQuery,int pageNo,int pageSize);
	public PageBeanDB queryPositonList(int pageNo,int pageSize);
	public VideoSpecification getVideoSpc(Integer advertPositionId);
	public ImageSpecification getImageMateSpeci(Integer id);
	public void saveDResource(DResource materialTemp);
	public VideoMeta getVideoMetaByID(Integer id);
	public DResource getMaterialByID(int materialId);
	public ImageMeta getImageMetaByID(Integer resourceId);
	public String getMaterialSpecification(DAdPosition position);
	public String checkMaterialExist(DResource resource);
}
