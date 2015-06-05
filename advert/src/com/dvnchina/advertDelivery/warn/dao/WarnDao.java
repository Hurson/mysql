package com.dvnchina.advertDelivery.warn.dao;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.warn.bean.WarnInfo;

public interface WarnDao {
	/*
	 * 查询尚未处理的告警信息
	 */
	public List<WarnInfo> getEntityList(String areaCodes);
	
	/*
	 * 标记删除
	 */
	public void deleteWarnInfo(Integer id);
	
	public PageBeanDB queryWarning(String areaCodes, int pageNo, int pageSize);
	
	public void deleteWarnInfo(String ids);
}
