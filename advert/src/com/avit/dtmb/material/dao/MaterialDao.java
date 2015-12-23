package com.avit.dtmb.material.dao;

import com.avit.dtmb.material.bean.DResource;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.VideoMeta;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;

public interface MaterialDao {
	public PageBeanDB queryDMaterialList(DResource meterialQuery,int pageNo,int pageSize);
	public PageBeanDB queryPosisonList(int pageNo,int pageSize);
	public VideoSpecification getVideoSpc(Integer advertPositionId);
	public ImageSpecification getImageMateSpeci(Integer advertPositionId);
	public void saveDResource(DResource materialTemp);
	public VideoMeta getVideoMetaByID(Integer id);
	public DResource getMaterialByID(int materialId);
	public ImageMeta getImageMetaByID(Integer resourceId);
}
