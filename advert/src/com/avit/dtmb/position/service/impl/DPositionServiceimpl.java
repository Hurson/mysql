package com.avit.dtmb.position.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.avit.dtmb.position.bean.DAdPosition;
import com.avit.dtmb.position.dao.DPositionDao;
import com.avit.dtmb.position.service.DPositionService;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.PositionPackage;
import com.dvnchina.advertDelivery.position.bean.TextSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
@Service("dPositionService")
public class DPositionServiceimpl implements DPositionService {
	@Resource
	private DPositionDao dPositionDao;
	@Override
	public PageBeanDB queryDPositionList(int pageNo,
			int pagesize) {
		
		return dPositionDao.queryDPositionList(pageNo, pagesize);
	}
	@Override
	public DAdPosition GetDPositionById(Integer id) {
		return dPositionDao.GetDPositionById(id);
	}
	@Override
	public void updateAdvertPosition(DAdPosition adposition) {
		dPositionDao.updateAdvertPosition(adposition);
		
	}
	@Override
	public ImageSpecification getImageSpeById(Integer id) {
		return dPositionDao.getImageSpeById(id);
	}
	@Override
	public void saveImageSpecification(ImageSpecification imageSpe) {
		dPositionDao.update(imageSpe);
		
	}
	@Override
	public TextSpecification getTextSpeById(Integer id) {
		return dPositionDao.getTextSpeById(id);
	}
	@Override
	public VideoSpecification getVideoSpeById(Integer id) {
		return dPositionDao.getVideoSpeById(id);
	}
	@Override
	public void saveVideoSpecification(VideoSpecification videoSpe) {
		dPositionDao.update(videoSpe);
		
	}
	@Override
	public void saveTextSpecification(TextSpecification textSpe) {
		dPositionDao.update(textSpe);
		
	}
	@Override
	public DAdPosition getAdvertPosition(String positionCode) {
		DAdPosition dAdPosition = dPositionDao.getAdvertPosition(positionCode);
		return dAdPosition;		
	}

}
