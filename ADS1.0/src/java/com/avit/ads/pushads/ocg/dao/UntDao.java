package com.avit.ads.pushads.ocg.dao;

public interface UntDao {
	
	public int getUntVersion(String areaCode, Integer type);
	
	public void updateVersion(String areaCode, Integer type);
}
