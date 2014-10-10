package com.dvnchina.advertDelivery.service;

import java.io.InputStream;
import java.util.HashMap;

/**
 * 用于解析从BSMP获取到的数据信息
 */

public interface ParseVodService{
	
	/**
	 * 获取远程文件,并调用解析方法
	 * 
	 * @param url  服务提供的地址
	 * return 
	 */
	public HashMap<String, Object> parseInfoByHttp(String url);
	
	/**
	 * 以读取本地路径的方式解析VOD数据
	 * @param url 文件存放位置
	 * @return 解析后的对象
	 */
	public HashMap<String, Object> parseInfoByLocal(String url); 
	
	public HashMap<String, Object> convertStreamToObject(InputStream intpuStream);
	
}
