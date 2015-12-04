package com.avit.dtmb.order.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.dtmb.order.bean.DOrder;
import com.avit.dtmb.order.dao.DOrderDao;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
@Repository
public class DOrderDaoImpl extends BaseDaoImpl implements DOrderDao {

	@Override
	public PageBeanDB queryDTMBOrderList(DOrder order, int pageNo, int pageSize) {
		String hql = "from DOrder";
		return this.getPageList2(hql, null, pageNo, pageSize);
	}

	@Override
	public DOrder getDTMBOrderById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDOrder(DOrder order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteOrder(List<String> ids) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DAdPosition> queryPositionList() {
		String hql = "from DAdPosition";
		return (List<DAdPosition>)this.getListForAll(hql, null);
	}

	@Override
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize) {
		String hql = "from DPloy ploy where ploy.dposition.positionCode ='" + ploy.getDposition().getPositionCode() + "'";
		return this.getPageList2(hql, null, pageNo, pageSize);
	}

}
