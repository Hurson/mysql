package com.dvnchina.advertDelivery.warn.service;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.warn.bean.WarnInfo;

public interface WarnService {
	public List<WarnInfo> getEntityList();
	public void deleteWarnInfo(Integer id);
	public PageBeanDB queryWarning(int pageNo, int pageSize);
	public void deleteWarnInfo(String ids);
}
