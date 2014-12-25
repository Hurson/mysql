package com.avit.ads.webservice;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.dvnchina.advertDelivery.subtitle.bean.SubtitleBean;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;

public class AdsClient {
	
	/** 投放webservice服务地址 */
	private static String adsServerUrl=ConfigureProperties.getInstance().get("adsServerUrl");
	
	/**
	 * 更新UI中的dataDefine-a.dat和 htmlData-a.dat描述符
	 * @param areaCode
	 * @param dataDefine（FTP对应全路径）
	 * @param htmlData（FTP对应全路径）  
	 * @return
	 */
	public String sendUI(String areaCode, String dataDefine, String htmlData){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(AdsService.class);
		factory.setAddress(adsServerUrl);
		String ret="1" ;
		try
		{
			AdsService service = (AdsService) factory.create();
			ret= service.sendUI(areaCode, dataDefine, htmlData);
		}
		catch(Exception e)
		{
			ret="0";
		}
		return ret;
	}
	
	public boolean pushSubtitle(SubtitleBean subtitle){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(AdsService.class);
		factory.setAddress(adsServerUrl);
		try{
			AdsService service = (AdsService) factory.create();
			service.pushSubtitle(subtitle);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void testEasyBean(EasyBean easyBean){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(AdsService.class);
		factory.setAddress(adsServerUrl);
		try{
			AdsService service = (AdsService) factory.create();
			service.test(easyBean);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
