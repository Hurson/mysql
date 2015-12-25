package com.avit.ads.dtmb.dao;

import java.util.List;

import com.avit.ads.dtmb.bean.DOcgInfo;

public interface DOcgInfoDao {

	public List<DOcgInfo> getOcgInfoList();
	public List<DOcgInfo> getOcgMulticastInfoList(String areaCode, String tsId);
}
