package com.avit.dtmb.ploy.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.ploy.dao.DPloyDao;
import com.avit.dtmb.position.bean.AdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
@Repository("dPloyDao")
public class DPloyDaoImpl extends BaseDaoImpl implements DPloyDao {

	@Override
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize) {
		String hql = "select new DPloy(ploy, cust.advertisersName,ap.positionName) from DPloy ploy, Customer cust, AdPosition ap"
				+ " where ploy.customerId = cust.id and ploy.positionCode = ap.positionCode";
		return this.getPageList2(hql, null, pageNo, pageSize);
	}
	@Override
	public void saveDTMBPloy(DPloy ploy) {
		this.saveOrUpdate(ploy);

	}
	@Override
	public DPloy getDTMBPloy(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteDTMBPloy(Integer id) {
		// TODO Auto-generated method stub

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AdPosition> queryPositionList() {
		String hql = "from AdPosition";
		return this.getListForAll(hql, null);
	}
	@Override
	public AdPosition getPositionByCode(String positionCode) {
		String hql = "from AdPosition ap where ap.positionCode = ?";
		return (AdPosition)this.get(hql, positionCode);
	}

}
