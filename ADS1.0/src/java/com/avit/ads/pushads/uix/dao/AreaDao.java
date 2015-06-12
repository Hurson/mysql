package com.avit.ads.pushads.uix.dao;

import java.util.List;

import com.avit.ads.pushads.task.bean.TReleaseArea;

public interface AreaDao {
	public TReleaseArea getAreaByAreaCode(String areaCode);
	public List<TReleaseArea> getAllArea();
	public void updateAdCtrlByAreaCode(String areaCode, String adCtrl);
}
