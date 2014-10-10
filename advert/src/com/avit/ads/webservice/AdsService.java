package com.avit.ads.webservice;

import javax.jws.WebService;

@WebService
public interface AdsService {
	
	/**
	 * 更新UI中的dataDefine-a.dat和 htmlData-a.dat描述符
	 * @param areaCode
	 * @param dataDefine（FTP对应全路径）
	 * @param htmlData（FTP对应全路径）  
	 * @return
	 */
	public String sendUI(String areaCode, String dataDefine, String htmlData);

}
