package com.avit.dtmb.material.dao;

import com.avit.dtmb.material.bean.DResource;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.VideoMeta;

public interface MaterialDao extends BaseDao{
	public PageBeanDB queryDMaterialList(DResource meterialQuery,int pageNo,int pageSize);
	public PageBeanDB queryPosisonList(int pageNo,int pageSize);
	public void saveDResource(DResource materialTemp);
	public VideoMeta getVideoMetaByID(Integer id);
	public DResource getMaterialByID(int materialId);
	public ImageMeta getImageMetaByID(Integer resourceId);
	public DResource getDRsourceByName(String resourceName);
}
