package com.avit.ads.pushads.ocg.dao;

import java.util.List;

import com.avit.ads.pushads.task.bean.OcgInfo;

public interface OcgInfoDao {

	public List<OcgInfo> getOcgInfoList();
	public List<OcgInfo> getOcgMulticastInfoList(String areaCode, String tsId);
}
