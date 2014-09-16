package com.avit.ads.pushads.ui.service;

import java.util.List;

public interface UiService {

	public boolean addUiDesc(List<String> types,List<String> files,String defaultstartflag );
	
	/**
	 * 往区域发送NID描述符插入信息
	 * @param bodyContent
	 * @param areaCode
	 * @return
	 */
	String sendUiDesc(String bodyContent,String areaCode);
}
