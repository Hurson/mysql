/**
 * Copyright (c) AVIT LTD (2012). All Rights Reserved.
 * Welcome to <a href="www.avit.com.cn">www.avit.com.cn</a>
 */
package com.avit.ads.xml;

import javax.xml.bind.annotation.XmlRegistry;

import com.avit.ads.util.bean.AdsConfig;
import com.avit.ads.util.message.AdsConfigJs;
import com.avit.ads.util.message.AdsImage;
import com.avit.ads.util.message.Channelrecomendurl;
import com.avit.ads.util.message.MsubtitleInfo;
import com.avit.ads.util.message.OcgPlayMsg;
import com.avit.ads.util.message.RetMsg;
import com.avit.ads.util.message.SystemMaintain;
import com.avit.ads.util.message.UNTMessage;
import com.avit.ads.util.message.UiUpdateMsg;
import com.avit.ads.util.message.Weatherforecast;
import com.avit.ads.xml.beans.ServerResponse;





/**
 * @Description:
 * @author lizhiwei
 * @Date: 2012-5-22
 * @Version: 1.0
 */
@XmlRegistry
public class ObjectFactory {    
	public AdsConfig createAdsConfig()
	{
		return new AdsConfig();
	}
	
	public ServerResponse createServerResponse()
	{
		return new ServerResponse();
	}
	
	public XmlBeanConfig  createXmlBeanConfig()
	{
		return new XmlBeanConfig();
	}
	
//	public CusFaceIncServices createCusFaceIncServices(){
//		return new CusFaceIncServices();
//	}
//	
//	public AreaList createAreaList(){
//		return new AreaList();
//	}
	
	public OcgPlayMsg createOcgPlayMsg()
	{
		return new OcgPlayMsg();
	}
	public RetMsg createRetMsg()
	{
		return new RetMsg();
	}
	
	public UiUpdateMsg createUiUpdateMsg()
	{
		return new UiUpdateMsg();
	}
	
	
	public UNTMessage createUNTMessage()
	{
		return new UNTMessage();
	}
	
}
