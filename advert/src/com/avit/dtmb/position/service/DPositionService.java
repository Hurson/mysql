package com.avit.dtmb.position.service;

import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.TextSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;

public interface DPositionService {
	public PageBeanDB queryDPositionList(int pageNo,int pagesize);
	public DAdPosition GetDPositionById(Integer id);
	public void updateAdvertPosition(DAdPosition adposition);
	public ImageSpecification getImageSpeById(Integer id);
	public void saveImageSpecification(ImageSpecification imageSpe);
	public TextSpecification getTextSpeById(Integer id);
	public VideoSpecification getVideoSpeById(Integer id);
	public void saveVideoSpecification(VideoSpecification videoSpe);
	public void saveTextSpecification(TextSpecification textSpe);
	public DAdPosition getAdvertPosition(String positionCode);
}
