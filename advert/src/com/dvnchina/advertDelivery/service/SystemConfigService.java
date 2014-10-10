package com.dvnchina.advertDelivery.service;

import java.util.List;

import com.dvnchina.advertDelivery.bean.config.AdvertPositionBean;
import com.dvnchina.advertDelivery.bean.config.CommonConfigBean;
import com.dvnchina.advertDelivery.bean.config.InterfaceConfigBean;
import com.dvnchina.advertDelivery.bean.config.PlatformConfigBean;
import com.dvnchina.advertDelivery.model.AdvertPosition;

/**
 * 系统配置对外统一接口
 * 
 * @author Administrator
 *
 */
public interface SystemConfigService {
	
	/**
	 * 添加平台地址配置
	 * 
	 * @param bean
	 */
	public void addOrUpdatePlatformUrl(PlatformConfigBean bean);
	
	/**
	 * 删除
	 * @param urlId
	 * @return
	 */
	public boolean deletePlatformUrl(String urlId);
	
	/**
	 * 获取平台配置信息
	 * @return
	 */
	public List<PlatformConfigBean> getPlatformUrl();
	
	/*********************************************************************/
	
	/**
	 * 添加和修改接口
	 * 
	 *@param bean
	 */
	public void addOrUpdateInterface(InterfaceConfigBean bean);
	/**
	 * 删除接口的配置
	 * @param urlId
	 * @return
	 */
	public boolean deleteInterface(String urlId);
	
	/**
	 * 获取数据同步接口的配置列表
	 * @return
	 */
	public List<InterfaceConfigBean> getInterfaceConfigList();
	
	/**
	 * 添加和修改插播次数
	 * @param bean
	 * @return
	 */
	public boolean addOrUpdataPlayCount(CommonConfigBean bean);
	
	/**
	 * 字幕显示位置配置列表
	 * @return
	 */
	public List<CommonConfigBean> getShowPlayCountList();
	
	/******************************************************************************************/
	
	
	/**
	 * 保存和修改显示位置
	 * @param bean
	 */
	public void addOrUpdateShowPosition(CommonConfigBean bean);
	
	/**
	 * 删除字幕显示位置的配置
	 * @param urlId
	 * @return
	 */
	public boolean deleteShowPosition(String urlId);
	
	/**
	 * 获取显示位置配置
	 * @return
	 */
	public List<CommonConfigBean> getShowPositionList();
	
	
	/**
	 * 添加广告位的默认素材
	 * @return
	 */
	public boolean addPositionDefaultMaterial(AdvertPosition bean ,String m_type,String m_path);
	
	
	/**
	 * 删除广告位的默认素材
	 * 
	 * @param positionId
	 * @param m_id
	 * @return
	 */
	public boolean deletePositionDefaultMaterial(String positionId,String m_id);
	
	/**
	 * 查询默认素材
	 * @param id
	 * @return
	 */
	public List<AdvertPositionBean> queryDefaultMaterial(String id);

}
