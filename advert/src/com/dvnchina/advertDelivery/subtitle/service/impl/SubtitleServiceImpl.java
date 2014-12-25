package com.dvnchina.advertDelivery.subtitle.service.impl;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.subtitle.bean.SubtitleBean;
import com.dvnchina.advertDelivery.subtitle.dao.SubtitleDao;
import com.dvnchina.advertDelivery.subtitle.service.SubtitleService;

public class SubtitleServiceImpl implements SubtitleService {
	
	private SubtitleDao subtitleDao;

	public SubtitleDao getSubtitleDao() {
		return subtitleDao;
	}

	public void setSubtitleDao(SubtitleDao subtitleDao) {
		this.subtitleDao = subtitleDao;
	}

	@Override
	public PageBeanDB findSubtitleList(SubtitleBean subtitle, int pageNo,int pageSize) {
		return subtitleDao.findSubtitleList(subtitle, pageNo, pageSize);
	}

	@Override
	public void delSubtitles(String ids) {
		subtitleDao.delSubtitles(ids);
	}

	@Override
	public void saveSubtitle(SubtitleBean entity) {
		subtitleDao.saveSubtitle(entity);
		
	}

	@Override
	public SubtitleBean getSubtitleById(Integer id) {
		return subtitleDao.getSubtitleById(id);
	}

	@Override
	public List getAreaList() {
		return subtitleDao.getAreaList();
	}	
	
	

}
