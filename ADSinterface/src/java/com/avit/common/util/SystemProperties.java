package com.avit.common.util;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Weimmy
 * 
 * @date:2011-7-26
 * @version :1.0
 * 
 */

public class SystemProperties {
	//模块代码
	public static final String PROPERTY_SUFIX_ISCG = "_iscg";
	public static final String PROPERTY_SUFIX_ODRM = "_odrm";
	public static final String PROPERTY_SUFIX_ERM = "_erm";

	private static Properties props;
	private static Properties iscgProps;
	private static long iscgLastModified = 0;
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
	
	/**
	 * 从指定模块的获取属性配置
	 * @param module      模块代码
	 * @param property
	 * @param def
	 * @return
	 */
	public static String getProperty(String module, String property, String def) {
		String retVal = null;
		String file = "system"+module+".properties";
		if (iscgProps == null || isFileUpdated(file)) {
			try {
				iscgProps = loadPropertiesFile(file);
			} catch (Exception exc) {
				return retVal;
			}
		}
		if (iscgProps != null){
			retVal = iscgProps.getProperty(property, def).trim();
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
				loadProperties("system.properties");
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

	/**
	 * 判断文件是否已修改
	 * @param file
	 * @return
	 */
	public static boolean isFileUpdated(String file){
		String realPath = SystemProperties.class.getClassLoader().getResource(file).getPath();
		File systemFile = new File(realPath);
		long newLastModified = systemFile.lastModified();
		if(iscgLastModified == 0 || iscgLastModified < newLastModified) {
			iscgLastModified = newLastModified;
		} else {
			return false;
		}
		return true;
	}
	
}
