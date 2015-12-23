package com.avit.dtmb.position.dao;

import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.TextSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;

public interface DPositionDao extends BaseDao{
	public PageBeanDB queryDPositionList(int pageNo,int pageSize);
	public DAdPosition GetDPositionById(Integer id);
	/**
	 * 修改子广告位信息（坐标、宽高、描述、背景图片）
	 * @param advertPosition
	 * @return 
	 */
	public void updateAdvertPosition(DAdPosition ad);
	public ImageSpecification getImageSpeById(Integer id);
	public TextSpecification getTextSpeById(Integer id);
	public VideoSpecification getVideoSpeById(Integer id);
	public DAdPosition getAdvertPosition(Integer advertPositionId);
}
