/**
 * Copyright (c) AVIT LTD (2012). All Rights Reserved.
 * Welcome to <a href="www.avit.com.cn">www.avit.com.cn</a>
 */
package com.avit.common.util;

import java.io.InputStream;
import java.util.Properties;

import com.avit.common.util.SystemProperties;

/**
 * @Description:
 * @author lizhiwei
 * @Date: 2012-6-28
 * @Version: 1.0
 */
public class ActionConfigProperties {
	private static final String FILE_NAME = "actionConfig.properties";
	private static Properties props;
	/**
	 * 
	 * @param property    key值
	 * @param def   如果没有的默认值
	 * @return
	 */
	public static String getProperty(String property, String def) {
		String retVal = null;
		getProperties();
		if (props != null){
			retVal = props.getProperty(property, def).trim();
		}else{
			retVal = def;
		}
		return retVal;
	}

	public static void setProperty(String strKey, String strValue) {
		if (props != null) {
			props.setProperty(strKey, strValue);
		}
	}

	/**
	 * This method loads the properties object and returns it.
	 * 
	 * @return Properties the loaded property object, else null
	 */
	public static Properties getProperties() {
		if (props == null) {
			try {
				loadProperties(FILE_NAME);
			} catch (Exception exc) {
				System.err.println(exc.getMessage());
				props = null;
			}
		}
		return props;
	}

	public static void loadProperties(String file) throws Exception {
		props = loadPropertiesFile(file);
	}

	public static Properties loadPropertiesFile(String file) throws Exception {
		Properties retVal = new Properties();
		InputStream in = SystemProperties.class.getClassLoader()
				.getResourceAsStream(file);
		if (in != null){
			retVal.load(in);
			in.close();
		}
		return retVal;
	}
}
