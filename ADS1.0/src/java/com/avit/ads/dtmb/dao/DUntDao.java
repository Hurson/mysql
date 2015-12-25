package com.avit.ads.dtmb.dao;

public interface DUntDao {
	
	public int getUntVersion(String areaCode, Integer type);
	
	public void updateVersion(String areaCode, Integer type);
}
