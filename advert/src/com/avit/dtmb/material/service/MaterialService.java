package com.avit.dtmb.material.service;

import com.avit.dtmb.material.bean.DResource;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;

public interface MaterialService {
	public PageBeanDB queryDMaterialList(DResource meterialQuery,int pageNo,int pageSize);
	public PageBeanDB queryPositonList(int pageNo,int pageSize);
	public VideoSpecification getVideoSpc(Integer advertPositionId);
	public ImageSpecification getImageMateSpeci(Integer advertPositionId);
}
