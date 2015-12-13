package com.avit.dtmb.order.dao.impl;

import java.text.SimpleDateFormat;
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
		String hql = "from DOrder order where order.state < '7'";
		return this.getPageList2(hql, null, pageNo, pageSize);
	}

	@Override
	public void saveDOrder(DOrder order) {
		this.saveOrUpdate(order);

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

	@SuppressWarnings("unchecked")
	@Override
	public void insertDOrderMateRelTmp(DOrder order) {
		int count = order.getDposition().getResourceCount();
		String mainPloy = order.getDposition().getMainPloy();
		String sql0 = "select count(1) from d_ploy_detail d where d.ploy_type = ? and d.ploy_id = ?";
		List<Integer> list = (List<Integer>)this.getDataBySql(sql0, new Object[]{mainPloy, order.getDploy().getId()});
		int num = 0;
		if(list != null && list.size() > 0){
			num = this.toInteger(list.get(0));
		}
		
		for(int i = 0; i < count; i ++){
			String sql = "insert into d_order_mate_rel_tmp(order_code,area_code,start_time, end_time,ploy_type,type_value,value_name,index_num)" +
					"select "+order.getOrderCode()+",a.type_value,b.type_value,b.value_name,"+mainPloy+","+(StringUtils.isBlank(mainPloy) || num == 0?"0,'全频道'":"c.type_value,c.value_name") +","+i+
					" from d_ploy_detail a,d_ploy_detail b"+(StringUtils.isBlank(mainPloy) || num == 0?"":",d_ploy_detail c")+
					" where a.ploy_id = b.ploy_id"+(StringUtils.isBlank(mainPloy) || num == 0?"":" and b.ploy_id= c.ploy_id")+
	                " and a.ploy_type='1' and  b.ploy_type='2'"+(StringUtils.isBlank(mainPloy) || num == 0?"":" and c.ploy_type='"+mainPloy+"'")+" and a.ploy_id=" + order.getDploy().getId();
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
					 "(select od.order_code,od.position_code,omr.area_code,concat(od.start_date,' ',omr.start_time),concat(od.end_date,' ',omr.end_time),omr.ploy_type,omr.type_value," +
					 "omr.resource_id,CASE res.resource_type" +
					 " WHEN 0 THEN (SELECT name from t_image_meta where id=res.resource_id)" +
					 " WHEN 1 THEN (SELECT name FROM t_video_meta where id=res.resource_id)" +
					 " WHEN 2 THEN (SELECT content FROM t_text_meta where id = res.resource_id)" +
					 " ELSE '' END,'0'"+
				     "from d_order od, d_order_mate_rel omr,d_resource res where od.order_code = omr.order_code and od.id="+order.getId()+")";
		return this.executeBySQL(sql, null);
	}

	@Override
	public int updatePlayListEndDate(DOrder order) {
		String sql = "update d_play_list set end_time = concat('"+(new SimpleDateFormat("yyyy-MM-dd").format(order.getEndDate()))+"',right(end_time,9)) where order_code = '" +order.getOrderCode()+"'";
		return this.executeBySQL(sql, null);
	}

}
