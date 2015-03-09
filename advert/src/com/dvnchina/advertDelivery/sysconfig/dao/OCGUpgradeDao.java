package com.dvnchina.advertDelivery.sysconfig.dao;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;
import com.dvnchina.advertDelivery.sysconfig.bean.AreaOCG;

public interface OCGUpgradeDao extends BaseDao{
	
	/**
	 * 分页查询OCG区域版本信息
	 * @param version
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryAreaOCGList(AreaOCG ocg,int pageNo, int pageSize);
	
	
	/**
	 * 更新OCGVersion版本号
	 * @param areaCodes
	 */
	public void updateAreaOCG(String version, String ip);

	
}
