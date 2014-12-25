package com.dvnchina.advertDelivery.subtitle.dao;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.subtitle.bean.SubtitleBean;

public interface SubtitleDao {
	
	public PageBeanDB findSubtitleList(SubtitleBean subtitle, int pageNo, int pageSize);
	
	public void delSubtitles(String ids);
	
	public void saveSubtitle(SubtitleBean entity);
	
	public SubtitleBean getSubtitleById(Integer id);
	
	public List getAreaList();
	
}
