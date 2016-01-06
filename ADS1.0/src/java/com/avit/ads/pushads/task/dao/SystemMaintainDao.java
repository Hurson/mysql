package com.avit.ads.pushads.task.dao;

import com.avit.ads.pushads.task.bean.SystemMaintain;


public interface SystemMaintainDao{
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
	
	public int saveOrUpdate(SystemMaintain o);

}
