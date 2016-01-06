package com.avit.ads.pushads.task.dao;

import com.avit.ads.pushads.task.bean.UntSystemMaintain;


public interface UntSystemMaintainDao  {
	public void saveOrUpdate(Object obj);
	public UntSystemMaintain find();

}
