package com.avit.ads.pushads.uix.dao;

public interface UixDao {
	public int getUiVersion(String areaCode, Integer type);
	public void updateVersion(String areaCode, Integer type);
}
