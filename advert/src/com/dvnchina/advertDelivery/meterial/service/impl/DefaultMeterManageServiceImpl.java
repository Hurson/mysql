package com.dvnchina.advertDelivery.meterial.service.impl;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.meterial.dao.DefaultMeterManageDao;
import com.dvnchina.advertDelivery.meterial.service.DefaultMeterialManageService;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Resource;

public class DefaultMeterManageServiceImpl implements DefaultMeterialManageService{

	private DefaultMeterManageDao defaultMeterManageDao;
	public DefaultMeterManageDao getDefaultMeterManageDao() {
		return defaultMeterManageDao;
	}
	public void setDefaultMeterManageDao(DefaultMeterManageDao defaultMeterManageDao) {
		this.defaultMeterManageDao = defaultMeterManageDao;
	}
	@Override
	public PageBeanDB queryMeterialList(Resource meterialQuery, int pageSize,
			int pageNo) {
		PageBeanDB page = defaultMeterManageDao.queryMeterialList(meterialQuery, pageSize, pageNo);
		return page;
	}
	
	@Override
	public Resource getMaterialByID(int materialId) {
		Resource r = defaultMeterManageDao.getMaterialByID(materialId);
		return r;
	}
	
	/**
	 * 删除素材
	 */
	public boolean deleteMaterialByIds(String ids) {
		ids = ids.substring(0, ids.length()-1);
		String id = "(" + ids + ")";
		return defaultMeterManageDao.deleteMaterialByIds(id);
	}
	@Override
	public void saveTextMaterialReal(MessageReal textMetaReal) {
//		defaultMeterManageDao.saveTextMaterialReal(textMetaReal);
		defaultMeterManageDao.updateId(textMetaReal);
		
	}
	@Override
	public void updateTextMaterialReal(MessageReal textMetaReal) {
		defaultMeterManageDao.updateTextMaterialReal(textMetaReal);
		
	}

}
