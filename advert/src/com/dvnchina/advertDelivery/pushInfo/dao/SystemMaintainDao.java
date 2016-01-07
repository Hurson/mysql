package com.dvnchina.advertDelivery.pushInfo.dao;

import java.util.List;

import com.dvnchina.advertDelivery.dao.BaseDao;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.pushInfo.bean.SystemMaintain;

public interface SystemMaintainDao extends BaseDao {
	/**
	 * 查询记录
	 * @return
	 */
	public SystemMaintain getAllMaintain();
	/**
	 * 指定时间更新unt待机数据
	 */
	public void sendSystemMainToUnt();
	public SystemMaintain fin();
	
	public List<ReleaseArea> getUserRelaArea(Integer userId);

}
