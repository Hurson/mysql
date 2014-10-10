package com.avit.ads.webservice;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

// TODO: Auto-generated Javadoc
/**
 * 管理子系统 资源上载.
 */
public class UploadClient {
	
	/** The server url. */
	private static String serverUrl="http://192.168.102.104:8080/ads/";
	///http://192.168.102.104:8080/CXFwebservice/ws/uploadService
	/** The service. */
	private static String service="ws/uploadService";
	
	/**
	 * 获取上载服务器URL.
	 *
	 * @return 上载服务器URL
	 */
	public static String getServerUrl() {
		return serverUrl;
	}
	
	/**
	 * 设置上载服务器URL.上载文件前需要调用此函数.
	 *
	 * @param serverUrl 上载服务器URL 如http://192.168.102.104:8080/ads/
	 */
	public static void setServerUrl(String serverUrl) {
		UploadClient.serverUrl = serverUrl;
	}
	
	/**
	 * 上载文件.
	 *
	 * @param fileName 带完整路径的文件名
	 * @param uploadType 上传类型 1代表上传到投放子系统，2代表上传到HttpServer,3代表上传到VideoPump
	 * @return the string
	 */
	public String sendFile(String fileName,String adsTypeCode)
	{
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(UploadService.class);
		factory.setAddress(serverUrl + service);
		String retfileName="" ;
		try
		{
		UploadService client = (UploadService) factory.create();
		ResourceFile res=new ResourceFile();
		res.setFileName(fileName.substring(fileName.lastIndexOf(File.separator)+1));
		res.setAdsTypeCode(adsTypeCode);
		
		DataSource source = new FileDataSource(new File(fileName));
		res.setFile(new DataHandler(source));
		
		retfileName= client.sendFile(res);
		System.out.println(retfileName);
		}
		catch(Exception e)
		{
			
		}
		return retfileName;
	}
	
	/**
	 * 删除文件.
	 *
	 * @param fileName 带完整路径的文件名
	 * @param uploadType 上传类型 1代表上传到投放子系统，2代表上传到HttpServer,3代表上传到VideoPump
	 * @return 1 成功  0失败 
	 */
	public String deleteFile(String fileName,String adsTypeCode)
	{
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(UploadService.class);
		factory.setAddress(serverUrl + service);
		String ret="1" ;
		try
		{
			UploadService client = (UploadService) factory.create();
			ret= client.deleteFile(fileName,adsTypeCode);
			}
		catch(Exception e)
		{
			ret="0";
		}
		return ret;
	}
	/**
	 * 获取广告位投放提前量  秒.
	 *
	 * @param adsTypeCode 广告位编码 
	 * @return  
	 */
	public String getPreSecond(String adsTypeCode)
	{
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(UploadService.class);
		factory.setAddress(serverUrl + service);
		String ret="1" ;
		try
		{
			UploadService client = (UploadService) factory.create();
			ret= client.getPreSecond(adsTypeCode);
			}
		catch(Exception e)
		{
			ret="0";
		}
		return ret;
	}
	/**
	 * DTV 订单审核时调用. 
	 * @param areaCode 区域编码
	 * @param sourcefile 原文件
	 * @param targetpath 目标目录
	 * @return 1 成功  0失败 
	 */
	public String sendFileDTV(String areaCode,String sourcefile,String targetpath)
	{
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(UploadService.class);
		factory.setAddress(serverUrl + service);
		String ret="1" ;
		try
		{
			UploadService client = (UploadService) factory.create();
			ret= client.sendFileDTV(areaCode, sourcefile, targetpath);
			}
		catch(Exception e)
		{
			ret="0";
		}
		return ret;
	}
	/**
	 * 非开机，DTV视频素材审核时调用.
	 *
	 * @param sourcefile 原文件
	 * @param targetpath 目标目录
	 * @return 1 成功  0失败 
	 */
	public String sendFileVideoPump(String sourcefile,String targetpath)
	{
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(UploadService.class);
		factory.setAddress(serverUrl + service);
		String ret="1" ;
		try
		{
			UploadService client = (UploadService) factory.create();
			ret= client.sendFileVideoPump(sourcefile, targetpath);
			}
		catch(Exception e)
		{
			ret="0";
		}
		return ret;
	}
}
