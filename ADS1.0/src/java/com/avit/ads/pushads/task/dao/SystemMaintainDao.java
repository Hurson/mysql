package com.avit.ads.pushads.task.dao;

import com.avit.ads.pushads.task.bean.SystemMaintainBean;


public interface SystemMaintainDao{
	/**
	 * 查询记录
	 * @return
	 */
	public SystemMaintainBean getAllMaintain();
	/**
	 * 指定时间更新unt待机数据
	 */
	public void sendSystemMainToUnt();
	public SystemMaintainBean fin();
	
	public int saveOrUpdate(SystemMaintainBean o);

}
