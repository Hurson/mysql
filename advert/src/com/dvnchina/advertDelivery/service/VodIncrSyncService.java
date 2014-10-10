package com.dvnchina.advertDelivery.service;

import java.util.HashMap;

/**
 * 增量方式，从BSMP获取vod数据,并将解析的数据保存到数据库
 */

public interface VodIncrSyncService {

	/**
	 * 增量修改portal的数据
	 * 
	 * @param infos
	 */
	public boolean updateDataIncrement(HashMap<String, Object> infos);
	
	
}
