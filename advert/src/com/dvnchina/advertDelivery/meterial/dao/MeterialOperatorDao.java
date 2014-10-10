package com.dvnchina.advertDelivery.meterial.dao;

import java.util.Map;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.Resource;

public interface MeterialOperatorDao {
	/**
	 * 查询素材分页列表
	 * @param pageNo
	 * @param pageSize
	 * @param queryCondition
	 * @return
	 */
	public PageBeanDB queryMeterialOperatorList(int pageNo, int pageSize, Resource queryCondition);

	/**
	 * 根据素材的ID获取素材对象
	 * @param lid
	 * @return
	 */
	public Resource getMeterialOperatorById(int lid);

	/**
	 * 修改素材运行表的状态
	 * @param state
	 * @return
	 */
	public boolean modifyRunningResourceState(char state, String ids);

	/**
	 * 填写审核意见并修改审核后的状态
	 * @param resource
	 */
	public boolean writeVerifyOpinion(Resource resource, String ids);

	/**
	 * 批量删除下线的素材
	 * @param ids
	 * @return
	 */
	public boolean deleteMeterialOffline(String ids);

	public int hasBindedOrder(String ids);
}
