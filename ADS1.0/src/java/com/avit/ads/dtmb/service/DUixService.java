package com.avit.ads.dtmb.service;

public interface DUixService {
	public boolean sendUiUpdateMsg(String mod, String areaCode, Integer type, String updatePath);
	
	public boolean sendUiUpdateMsg(String mod, String areaCode, Integer type, String updatePath, boolean isDefault);
	
	public boolean delUiUpdateMsg(String areaCode, Integer type, String updatePath, boolean isDefault);
}
