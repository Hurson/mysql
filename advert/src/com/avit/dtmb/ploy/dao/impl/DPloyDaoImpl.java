package com.avit.dtmb.ploy.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.ploy.dao.DPloyDao;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.ReleaseArea;
@Repository("dPloyDao")
public class DPloyDaoImpl extends BaseDaoImpl implements DPloyDao {

	@Override
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize) {
		String hql = "from DPloy ploy where 1 = 1";
		if(ploy != null && StringUtils.isNotBlank(ploy.getPloyName())){
			hql += " and ploy.ployName like '%" + ploy.getPloyName() + "%'";
		}
		if(ploy != null && StringUtils.isNotBlank(ploy.getPositionName())){
			hql += " and ploy.dposition.positionName= like '%" + ploy.getPositionName() + "%'";
		}
		if(ploy != null && StringUtils.isNotBlank(ploy.getStatus())){
			hql += " and ploy.status= '" + ploy.getStatus() + "'";
		}
		return this.getPageList2(hql, null, pageNo, pageSize);
	}
	@Override
	public void saveDTMBPloy(DPloy ploy) {
		this.saveOrUpdate(ploy);
		String hql = "delete from DPloyDetail detail where detail.ployId is null";
		this.executeByHQL(hql, null);
	}
	@Override
	public DPloy getDTMBPloy(Integer id) {
		
		return (DPloy)this.get(DPloy.class, id);
	}
	@Override
	public void deleteDTMBPloy(Integer id) {
		this.delete(this.get(DPloy.class, id));

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<DAdPosition> queryPositionList() {
		String hql = "from DAdPosition";
		return (List<DAdPosition>)this.getListForAll(hql, null);
	}
	@Override
	public DAdPosition getPositionByCode(String positionCode) {
		String hql = "from DAdPosition ap where ap.positionCode = ?";
		return (DAdPosition)this.get(hql, positionCode);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ReleaseArea> listReleaseArea() {
		String hql = "from ReleaseArea";
		return (List<ReleaseArea>)this.getListForAll(hql, null);
	}
	@Override
	public DPloy getDPloyByName(String ployName) {
		String hql = "from DPloy ploy where ploy.ployName=?";
		return (DPloy)this.get(hql, ployName);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getUserAccessAreaCode(Integer userId) {
		String sql = "select release_areacode from t_user_area where user_id =" + userId;
		return (List<String>)this.getDataBySql(sql, null);
	}

}
