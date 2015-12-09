package com.avit.dtmb.order.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.order.bean.DOrder;
import com.avit.dtmb.order.bean.DOrderMateRelTmp;
import com.avit.dtmb.order.dao.DOrderDao;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.ReleaseArea;
@Repository
public class DOrderDaoImpl extends BaseDaoImpl implements DOrderDao {

	@Override
	public PageBeanDB queryDTMBOrderList(DOrder order, int pageNo, int pageSize) {
		String hql = "from DOrder";
		return this.getPageList2(hql, null, pageNo, pageSize);
	}

	@Override
	public void saveDOrder(DOrder order) {
		this.saveOrUpdate(order);

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

	@Override
	public void insertDOrderMateRelTmp(DOrder order) {
		int count = order.getDposition().getResourceCount();
		String mainPloys = order.getDposition().getMainPloy();
		String mainPloy = "";
		if(StringUtils.isNotBlank(mainPloys)&& mainPloys.split("\\|").length > 1){
			for(String type : mainPloys.split("\\|")){
				if(type.equals("1") || type.equals("2")){
					continue;
				}
				mainPloy = type;
				break;
			}
		}
		for(int i = 0; i < count; i ++){
			String sql = "insert into d_order_mate_rel_tmp(order_code,area_code,start_time, end_time,ploy_detail_id,index_num)" +
					"select "+order.getOrderCode()+",a.type_value,LEFT(b.type_value,8),RIGHT(b.type_value,8)"+(StringUtils.isBlank(mainPloy)?",0":",c.id") +","+i+" from d_ploy_detail a,d_ploy_detail b"+(StringUtils.isBlank(mainPloy)?"":",d_ploy_detail c")+
					" where a.ploy_id = b.ploy_id"+(StringUtils.isBlank(mainPloy)?"":" and b.ploy_id= c.ploy_id")+
	                " and a.ploy_type='1' and  b.ploy_type='2'"+(StringUtils.isBlank(mainPloy)?"":" and c.ploy_type='"+mainPloy+"'")+" and a.ploy_id=" + order.getDploy().getId();
			this.executeBySQL(sql, null);
			
		}
		
	}

	@Override
	public void deleteDOrderMateRelTmp(DOrder order) {
		String hql = "delete from DOrderMateRelTmp where orderCode ='" + order.getOrderCode()+"'";
		this.executeByHQL(hql, null);
		
	}

	@Override
	public PageBeanDB queryDOrderMateRelTmpList(DOrderMateRelTmp omrTmp, int pageNo, int pageSize) {
		String hql = "from DOrderMateRelTmp tmp where tmp.orderCode = '" + omrTmp.getOrderCode()+"'";
		if(omrTmp.getResource() == null && StringUtils.isBlank(omrTmp.getContain())){
			hql += " and tmp.resource.id is null";
		}
		if(omrTmp.getResource() != null && omrTmp.getResource().getId() != null){
			hql += " and tmp.resource.id =" + omrTmp.getResource().getId();
		}
		return this.getPageList2(hql, null, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReleaseArea> queryReleaseAreaList() {
		String hql = "from ReleaseArea";
		return (List<ReleaseArea>)this.getListForAll(hql, null);
	}

	@Override
	public PageBeanDB queryDResourceList(DResource resource, int pageNo, int pageSize) {
		String hql = "from DResource res where res.status='1' and res.positionCode='" + resource.getPositionCode()+"'";
		return this.getPageList2(hql, null, pageNo, pageSize);
	}

	@Override
	public void saveOrderMateRelTmp(String ids, Integer id) {
		String sql = "update d_order_mate_rel_tmp omr set omr.resource_id = ? where omr.id in ("+ ids +")";
		this.executeBySQL(sql, new Object[]{id});
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DResource> getOrderResourceJson(DOrderMateRelTmp omrTmp) {
		String hql = "select distinct res from DResource res,DOrderMateRelTmp omr where res.id = omr.resource.id and omr.orderCode = '"+omrTmp.getOrderCode()+"'";
		return (List<DResource>)this.getListForAll(hql, null);
	}

	@Override
	public void auditDTMBPloy(DOrder order) {
		
		
	}

	@Override
	public void saveDOrderMateRel(DOrder order) {
		String sql = "insert into d_order_mate_rel (select * from d_order_mate_rel_tmp where order_code ='" + order.getOrderCode()+"')";
		this.executeBySQL(sql, null);
	}

	@Override
	public void deleteDOrderMateRel(DOrder order) {
		String hql = "delete from DOrderMateRel where orderCode ='" + order.getOrderCode() + "'";
		this.executeByHQL(hql, null);
		
	}

	@Override
	public void copyDOrderMateRelTmp(DOrder order) {
		String sql = "insert into d_order_mate_rel_tmp (select * from d_order_mate_rel where order_code ='" + order.getOrderCode()+"')";
		this.executeBySQL(sql, null);
		
	}

	@Override
	public PageBeanDB queryAuditDOrderList(DOrder order, int pageNo, int pageSize) {
		String hql = "from DOrder order where order.state < '3'";
		return this.getPageList2(hql, null, pageNo, pageSize);
	}

	@Override
	public int insertPlayList(DOrder order) {
		String sql = "insert into d_play_list(order_code, position_code, area_code, start_time, end_time,ploy_type,type_value,resource_ids,resource_paths,status) "+
					 "(select od.order_code,od.position_code,omr.area_code,concat(od.start_date,' ',omr.start_time),concat(od.end_date,' ',omr.end_time),dt.ploy_type,dt.type_value,'','','0' "+
				     "from d_order od, d_order_mate_rel omr,d_ploy_detail dt where od.order_code = omr.order_code and omr.ploy_detail_id=dt.id and od.id="+order.getId()+")";
		return this.executeBySQL(sql, null);
	}
	

}
