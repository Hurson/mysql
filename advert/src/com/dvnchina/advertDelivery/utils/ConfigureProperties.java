package com.dvnchina.advertDelivery.utils;

import java.io.*;
import java.util.Properties;

public class ConfigureProperties {

	private static ConfigureProperties cp=new ConfigureProperties();
	private Properties p=null;
	private File f;

	private ConfigureProperties(){
		f=new File(this.getClass().getClassLoader().getResource("configuration.properties").getFile());
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			p=new Properties();
			p.load(fis);
		}
		catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != fis){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static ConfigureProperties getInstance(){
		return cp;
	}
	public void put(Object key,Object value){
		FileOutputStream outs = null;
		try {
			outs = new FileOutputStream(f);
			p.put(key, value);
			p.store(outs, "configuration.properties");
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != outs){
					outs.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	
	public Object get(Object key){
		return p.get(key);
	}

	public String get(String key){
		return (String)p.get(key);
	}
}
