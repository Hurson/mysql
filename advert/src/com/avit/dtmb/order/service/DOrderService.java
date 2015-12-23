package com.avit.dtmb.order.service;

import java.util.List;
import java.util.Map;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.order.bean.DOrder;
import com.avit.dtmb.order.bean.DOrderMateRelTmp;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.ReleaseArea;

public interface DOrderService {

	public PageBeanDB queryDTMBOrderList(DOrder order, int pageNo, int pageSize);
	public PageBeanDB queryAuditDOrderList(DOrder order, int pageNo, int pageSize);
	public DOrder getDTMBOrderById(Integer id);
	public void saveDOrder(DOrder order);
	public void deleteDOrder(List<String> ids);
	public List<DAdPosition> queryPositionList();
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize);
	public void insertDOrderMateRelTmp(DOrder order);
	public PageBeanDB queryDOrderMateRelTmpList(DOrderMateRelTmp omrTmp, int pageNo, int pageSize);
	public List<ReleaseArea> queryReleaseAreaList();
	public PageBeanDB queryDResourceList(DResource resource, int pageNo, int pageSize);
	public void saveOrderMateRelTmp(String ids, Integer id);
	public String getOrderResourceJson(DOrderMateRelTmp omrTmp);
	public String auditDTMBPloy(DOrder order, String flag);
	public String checkDOrderRule(DOrder order);
	public void delDOrderMateRelTmp(String ids);
	public List<Customer> getCustomerList();
	public String repushOrder(String orderCode);
	public Map<String, String> previewResource(DResource resource);
}
