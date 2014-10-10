/* 
 * Copyright 2008 Digital Video Networks (Beijing) Co., Ltd. All rights reserved.
 */
package com.dvnchina.advertDelivery.utils;

import java.io.File;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p> 
 * @author fengxf
 * @version 1.0
 * @since 2008-10-20
 */
public class DirectoryUtils
{
	
	public static String getSerlvetContextPath()
	{
	
		String classPath = Thread.currentThread().getContextClassLoader()
			.getResource("").getPath();
		File file = new File(classPath);

		String path = file.getParentFile().getParentFile().getPath();
		
		return path;
	}
	
	
	public static String getGenerateHtmlPath()
	{
		//return getSerlvetContextPath() + "/generateHtml";
		return getSerlvetContextPath() + "/generateManage/generateTemplate";
	}
	
	
	public static String getGenerateHtmlRootPath()
	{
		//return getSerlvetContextPath() + "/generateHtml/root";
		return getSerlvetContextPath()+ "/generateManage/generateTemplate/root";
	}
	
	public static String getGenerateHtmlPosterPath()
	{
		//return getSerlvetContextPath() +"";
		//return getSerlvetContextPath() + "/generateHtml/poster";
		return getSerlvetContextPath() + "/generateManage/generateTemplate/poster";
	}
	
	public static String getBroadcastImagePath()
	{
		return getSerlvetContextPath() + "/generateManage/generateBroadcast/broadcastImages";
	}
	
	public static String getBroadcastJsPath()
	{
		return getSerlvetContextPath() + "/generateManage/generateBroadcast/broadcastScripts";
	}
	public static String getBroadcast()
	{
		return getSerlvetContextPath() + "/generateManage/generateBroadcast";
	}
	
	public static String getImgsPath()
	{
		return getSerlvetContextPath() + "/broadcastManage/images";
	}
	
	public static String getBroadcastManagerPath()
	{
		return getSerlvetContextPath() + "/broadcastManage/scripts";
	}
	
	public static String getBackupPath()
	{
		return getSerlvetContextPath() + "/backup";
	}	
	
	public static String getFtlDir()
	{
		return getSerlvetContextPath() + "/ftl/";
		//return getSerlvetContextPath() +"/loadTemplateManage/html";
	}
}

