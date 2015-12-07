package com.avit.dtmb.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.order.bean.DOrder;
import com.avit.dtmb.order.bean.DOrderMateRelTmp;
import com.avit.dtmb.order.dao.DOrderDao;
import com.avit.dtmb.order.service.DOrderService;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.ReleaseArea;
@Service
public class DOrderServiceImpl implements DOrderService {

	@Resource
	private DOrderDao dOrderDao;
	@Override
	public PageBeanDB queryDTMBOrderList(DOrder order, int pageNo, int pageSize) {
		return dOrderDao.queryDTMBOrderList(order, pageNo, pageSize);
	}

	@Override
	public DOrder getDTMBOrderById(Integer id) {
		
		return (DOrder)dOrderDao.get(DOrder.class, id);
	}

	@Override
	public void saveDOrder(DOrder order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteDOrder(List<String> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DAdPosition> queryPositionList() {
		
		return dOrderDao.queryPositionList();
	}

	@Override
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize) {
		return dOrderDao.queryDTMBPloyList(ploy, pageNo, pageSize);
	}

	@Override
	public void insertDOrderMateRelTmp(DOrder order) {
		//dOrderDao.deleteDOrderMateRelTmp(order);
		dOrderDao.insertDOrderMateRelTmp(order);
		
	}

	@Override
	public PageBeanDB queryDOrderMateRelTmpList(DOrderMateRelTmp omrTmp, int pageNo, int pageSize) {

		return dOrderDao.queryDOrderMateRelTmpList(omrTmp, pageNo, pageSize);
	}

	@Override
	public List<ReleaseArea> queryReleaseAreaList() {
		
		return dOrderDao.queryReleaseAreaList();
	}

	@Override
	public PageBeanDB queryDResourceList(DResource resource, int pageNo, int pageSize) {
		return dOrderDao.queryDResourceList(resource, pageNo, pageSize);
	}

	@Override
	public void saveOrderMateRelTmp(String ids, Integer id) {
		dOrderDao.saveOrderMateRelTmp(ids, id);
		
	}

}
