package com.dvnchina.advertDelivery.sysconfig.dao;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.BaseConfig;

public interface BaseConfigDao {

	/**
	 * 分页查信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryConfigList(int pageNo, int pageSize, String queryKey);
	
	/**
	 * 根据ID获取配置项信息
	 * @param id
	 * @return BaseConfig
	 */
	public BaseConfig getBaseConfigById(int id);
	
	/**
	 * 保存
	 * @param BaseConfig
	 * @return int
	 */
	public int saveBaseConfig(BaseConfig obj);
	
	/**
	 * 修改
	 * @param BaseConfig
	 * 
	 */
	public void updateBaseConfig(BaseConfig obj);
	
	/**
	 * 保存
	 * @param List<BaseConfig>
	 * 
	 */
	public void deleteBaseConfig(List<BaseConfig> obj);
	
	/**
	 * 根据编码获取配置值
	 * @param List<BaseConfig>
	 * 
	 */
	public String getBaseConfigByCode(String configCode);
}
