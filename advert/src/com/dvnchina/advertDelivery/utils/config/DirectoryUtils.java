package com.dvnchina.advertDelivery.utils.config;

import java.io.File;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p> 
 * @author zhangfeng
 * @version 1.0
 * @since 2012-12-17
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
	
	
}

