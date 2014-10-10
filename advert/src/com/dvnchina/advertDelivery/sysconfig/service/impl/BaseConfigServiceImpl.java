package com.dvnchina.advertDelivery.sysconfig.service.impl;

import java.util.List;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.sysconfig.bean.BaseConfig;
import com.dvnchina.advertDelivery.sysconfig.dao.BaseConfigDao;
import com.dvnchina.advertDelivery.sysconfig.service.BaseConfigService;

public class BaseConfigServiceImpl implements BaseConfigService{

	private BaseConfigDao baseConfigDao;
	public BaseConfigDao getBaseConfigDao() {
		return baseConfigDao;
	}

	public void setBaseConfigDao(BaseConfigDao baseConfigDao) {
		this.baseConfigDao = baseConfigDao;
	}

	/**
	 * 分页查信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryConfigList(int pageNo, int pageSize, String queryKey){
		PageBeanDB pageBean = null;
		try {
			pageBean = baseConfigDao.queryConfigList(pageNo, pageSize, queryKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageBean;
	}
	
	/**
	 * 根据ID获取配置项信息
	 * @param id
	 * @return BaseConfig
	 */
	public BaseConfig getBaseConfigById(int id){
		BaseConfig baseConfig = null;
		try {
			baseConfig = baseConfigDao.getBaseConfigById(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return baseConfig;
	}
	
	/**
	 * 保存
	 * @param BaseConfig
	 * @return int
	 */
	public int saveBaseConfig(BaseConfig obj){
		int sussess = -1;
		try {
			sussess = baseConfigDao.saveBaseConfig(obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sussess;
		
	}
	
	/**
	 * 修改
	 * @param BaseConfig
	 * 
	 */
	public void updateBaseConfig(BaseConfig obj){
		try {
			baseConfigDao.updateBaseConfig(obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	/**
	 * 保存
	 * @param List<BaseConfig>
	 * 
	 */
	public void deleteBaseConfig(List<BaseConfig> obj){
		try {
			baseConfigDao.deleteBaseConfig(obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 根据编码获取配置值
	 * @param List<BaseConfig>
	 * 
	 */
	public String getBaseConfigByCode(String configCode){
		return baseConfigDao.getBaseConfigByCode(configCode);
	}
}
