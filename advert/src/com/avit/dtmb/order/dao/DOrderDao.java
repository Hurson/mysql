package com.avit.dtmb.order.dao;

import java.util.List;

import com.avit.dtmb.order.bean.DOrder;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.BaseDao;

public interface DOrderDao extends BaseDao {
	
	public PageBeanDB queryDTMBOrderList(DOrder order, int pageNo, int pageSize);
	public DOrder getDTMBOrderById(Integer id);
	public void saveDOrder(DOrder order);
	public void deleteOrder(List<String> ids);
	public List<DAdPosition> queryPositionList();
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize);
}