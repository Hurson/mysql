package com.dvnchina.advertDelivery.subtitle.service;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.subtitle.bean.SubtitleBean;

public interface SubtitleService {
	
	public PageBeanDB findSubtitleList(SubtitleBean subtitle, int pageNo, int pageSize);
	public void delSubtitles(String ids);
	public void saveSubtitle(SubtitleBean entity);
	public SubtitleBean getSubtitleById(Integer id);
	public List getAreaList();
}
