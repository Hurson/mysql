package com.dvnchina.advertDelivery.utils.config;

import java.io.*;
import java.util.Properties;

/**
 * 配置信息类
 * 
 * @author Administrator
 *
 */
public class ConfigureProperties {

	private static ConfigureProperties cp=new ConfigureProperties();
	private Properties p=null;
	private File f;

	/**
	 * 私有化构造方法
	 */
	private ConfigureProperties(){
		f=new File(this.getClass().getClassLoader().getResource("configuration.properties").getFile());
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			p=new Properties();
			p.load(fis);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 全局访问点
	 * @return
	 */
	public static ConfigureProperties getInstance(){
		return cp;
	}
	
	/**
	 * 写入配置信息
	 * @param key
	 * @param value
	 */
	public void put(Object key,Object value){
		FileOutputStream outs;
		try {
			outs = new FileOutputStream(f);
			p.put(key, value);
			p.store(outs, "configuration.properties");
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 按键取值
	 * @param key
	 * @return String
	 */
	public  String getValueByKey(String key){
		return (String) ConfigureProperties.getInstance().p.get(key);
	}
	
	/**
	 * 取得键值
	 * @param key
	 * @return Object
	 */
	public Object get(Object key){
		return p.get(key);
	}

	/**
	 * 取得键值
	 * @param key
	 * @return String
	 */ 
	public String get(String key){
		return (String)p.get(key);
	}
	

}
