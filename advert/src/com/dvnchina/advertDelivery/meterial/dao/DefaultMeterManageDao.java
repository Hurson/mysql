package com.dvnchina.advertDelivery.meterial.dao;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Resource;

public interface DefaultMeterManageDao {

	PageBeanDB queryMeterialList(Resource meterialQuery, int pageSize, int pageNo);

	Resource getMaterialByID(int materialId);

	boolean deleteMaterialByIds(String id);

	void saveTextMaterialReal(MessageReal textMetaReal);

	void updateTextMaterialReal(MessageReal textMetaReal);
	
	public void updateId(MessageReal textMetaReal);

}
