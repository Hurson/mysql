package com.avit.ads.dtmb.dao;


public interface DUixDao {
	public int getUiVersion(String areaCode, Integer type);
	
	public void updateVersion(String areaCode, Integer type);
	
	public int getAvailableVersion(String areaCode, Integer type);
	
	public void abolishVersion(String areaCode, Integer type);
}
