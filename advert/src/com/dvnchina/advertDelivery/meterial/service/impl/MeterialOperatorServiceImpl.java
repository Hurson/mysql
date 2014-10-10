package com.dvnchina.advertDelivery.meterial.service.impl;

import java.util.Map;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.meterial.dao.MeterialOperatorDao;
import com.dvnchina.advertDelivery.meterial.service.MeterialOperatorService;
import com.dvnchina.advertDelivery.model.Resource;

public class MeterialOperatorServiceImpl implements MeterialOperatorService{

	private MeterialOperatorDao meterialOperatorDao;
	public PageBeanDB queryMeterialUponLineList(int pageNo, int pageSize, Resource object){
		return meterialOperatorDao.queryMeterialOperatorList(pageNo, pageSize, object);
		
	}

	public Resource getMeterialUponLineById(int lid){
		return null;
		
	}

	
	public MeterialOperatorDao getMeterialOperatorDao() {
		return meterialOperatorDao;
	}

	public void setMeterialOperatorDao(MeterialOperatorDao dao) {
		this.meterialOperatorDao = dao;
	}

	/**
	 * 更新运行期表上下线状态
	 * @param state
	 * @return
	 */
	public boolean modifyResourceState(char state, String ids){
		boolean flag = true;
		try {
			// 先修改运行表
			meterialOperatorDao.modifyRunningResourceState(state, ids);
		
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 上线，下线操作
	 * @param state
	 * @param resource
	 */
	public void writeVerifyOpinion(char state, Resource resource, String ids) {
		
		boolean flag = true;
		try {
			// 修改维护表
			resource.setState(state);
			// 因为列表更新的时候不会在列表中放入所有字段的隐藏域，所以此方法使用SQL更新
			ids = "("+ids+")";
			meterialOperatorDao.writeVerifyOpinion(resource, ids);
			
			// 修改运行表
			this.modifyResourceState(state,  ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 删除下线状态的素材，在页面端会做判断，上线状态的不允许删除，所以在后台不做判断了。
	 * @param ids
	 */
	public void deleteMeterialOffline(String ids) {
		try {
			ids = "("+ids+")";
			meterialOperatorDao.deleteMeterialOffline(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public int hasBindedOrder(String ids) {
		int count = meterialOperatorDao.hasBindedOrder(ids);
		return count;
	}

}
