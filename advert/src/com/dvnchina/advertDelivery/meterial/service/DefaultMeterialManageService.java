package com.dvnchina.advertDelivery.meterial.service;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.MessageReal;
import com.dvnchina.advertDelivery.model.Resource;

public interface DefaultMeterialManageService {

	PageBeanDB queryMeterialList(Resource meterialQuery, int pageSize, int pageNo);

	Resource getMaterialByID(int materialId);

	boolean deleteMaterialByIds(String ids);

	void saveTextMaterialReal(MessageReal textMetaReal);

	void updateTextMaterialReal(MessageReal textMetaReal);

}
