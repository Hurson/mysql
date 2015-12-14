package com.avit.dtmb.order.service.impl;

import java.util.Date;
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
import com.google.gson.Gson;
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
		DOrder order = (DOrder)dOrderDao.get(DOrder.class, id);
		dOrderDao.deleteDOrderMateRelTmp(order);
		dOrderDao.copyDOrderMateRelTmp(order);
		return order;
	}

	@Override
	public void saveDOrder(DOrder order) {
		dOrderDao.saveDOrder(order);
		this.saveOrderMateRel(order);

	}

	@Override
	public void deleteDOrder(List<String> ids) {
		for(String id : ids){
			DOrder order = (DOrder)dOrderDao.get(DOrder.class, Integer.valueOf(id.trim()));
			if("0".equals(order.getState())){
				dOrderDao.delete(order);
			}else{
				order.setState("2");
				dOrderDao.saveOrUpdate(order);
			}
		}

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
		dOrderDao.deleteDOrderMateRelTmp(order);
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

	@Override
	public String getOrderResourceJson(DOrderMateRelTmp omrTmp) {
		List<DResource> resourceList = dOrderDao.getOrderResourceJson(omrTmp);
		Gson gson = new Gson();
		return gson.toJson(resourceList);
	}

	@Override
	public String auditDTMBPloy(DOrder order, String flag) {
		String result = "-1";
		
		DOrder dorder = (DOrder)dOrderDao.get(DOrder.class, order.getId());
		if("1".equals(flag)){
			dorder.setEndDate(order.getEndDate());
			
			if("0".equals(dorder.getState()) && insertPlayList(dorder) > 0){
				dorder.setState("6");
				result = "0";
			}else if("1".equals(dorder.getState()) && updatePlayListEndDate(dorder) > 0){
				dorder.setState("6");
				result = "0";
			}else if("2".equals(dorder.getState())){
				dorder.setEndDate(new Date());
				if(updatePlayListEndDate(dorder) > 0){
					dorder.setState("7");
					result = "0";
				}else{
					result = "-1";
				}
				
			}else{
				result = "-1";
			}
		
		}else if("-1".equals(flag)){
			int state = Integer.parseInt(dorder.getState()) + 3;
			dorder.setState(state + "");
		}else{
			result = "-1";
		}
		
		if("0".equals(result)){
			dorder.setAuditAdvice(order.getAuditAdvice());
			dOrderDao.saveDOrder(dorder);
		}
		
		return result;
		
		
	}
	private void saveOrderMateRel(DOrder order){
		dOrderDao.deleteDOrderMateRel(order);
		dOrderDao.saveDOrderMateRel(order);
		dOrderDao.deleteDOrderMateRelTmp(order);
		
	}

	@Override
	public PageBeanDB queryAuditDOrderList(DOrder order, int pageNo, int pageSize) {
		
		return dOrderDao.queryAuditDOrderList(order, pageNo, pageSize);
	}
	private int insertPlayList(DOrder order){
		int result = dOrderDao.insertPlayList(order);
		return result;
	}
	private int updatePlayListEndDate(DOrder order){
		int result = dOrderDao.updatePlayListEndDate(order);
		return result;
	}

	@Override
	public String checkDOrderRule(DOrder order) {
		List<Integer> list = dOrderDao.checkDOrderRule(order);
		if(list != null && list.size() > 0){
			return "1";
		}
		return "0";
	}

	@Override
	public void delDOrderMateRelTmp(String ids) {
		dOrderDao.delDOrderMateRelTmp(ids);
		
	}

}
