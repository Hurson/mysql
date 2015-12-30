package com.avit.dtmb.order.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Repository;

import com.avit.dtmb.material.bean.DResource;
import com.avit.dtmb.order.bean.DOrder;
import com.avit.dtmb.order.bean.DOrderMateRel;
import com.avit.dtmb.order.bean.DOrderMateRelTmp;
import com.avit.dtmb.order.dao.DOrderDao;
import com.avit.dtmb.ploy.bean.DPloy;
import com.avit.dtmb.position.bean.DAdPosition;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.Customer;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.model.UserLogin;
@Repository
public class DOrderDaoImpl extends BaseDaoImpl implements DOrderDao {

	@SuppressWarnings("all")
	@Override
	public PageBeanDB queryDTMBOrderList(DOrder order, int pageNo, int pageSize) {
		String hql = "from DOrder dorder where 1=1";
		List param = new ArrayList();
			   if(order != null && order.getDposition() != null && StringUtils.isNotBlank(order.getDposition().getPositionName())){
				   hql += " and dorder.dposition.positionName like '%" + order.getDposition().getPositionName() + "%'";
			   }
			   if(order != null && order.getContract() != null && StringUtils.isNotBlank(order.getContract().getContractName())){
				   hql += " and dorder.contract.contractName like '%" + order.getContract().getContractName() + "%'";
			   }
			   if(order != null && order.getDploy() != null && StringUtils.isNotBlank(order.getDploy().getPloyName())){
				   hql += " and dorder.dploy.ployName like '%" + order.getDploy().getPloyName() + "%'";
			   }
			   if(order != null && order.getStartDate() != null){
				   hql += " and dorder.startDate <= ?";
				   param.add(order.getStartDate());
			   }
			   if(order != null && order.getEndDate() != null){
				   hql += " and dorder.endDate<= ?";
				   param.add(order.getEndDate());
			   }
			   if(order != null && StringUtils.isNotBlank(order.getState())){
				   hql += " and dorder.state='" + order.getState() + "'";
			   }else{
				   hql += " and dorder.state <> '7'";
			   }
			   HttpSession session = ServletActionContext.getRequest().getSession();
			   UserLogin userLogin = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
			   List<Integer> accessUserId = userLogin.getAccessUserIds();
			   hql +=" and dorder.operatorId in ("+accessUserId.toString().replaceAll("\\[|\\]|\\s", "")+")";
			   hql +=" and dorder.dposition.id in ("+userLogin.getDtmbPositionIds()+")";
			   
			hql+= " ORDER BY dorder.id desc";
		return this.getPageList2(hql, param.toArray(), pageNo, pageSize);
	}

	@Override
	public void saveDOrder(DOrder order) {
		this.saveOrUpdate(order);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DAdPosition> queryPositionList() {
		
		 HttpSession session = ServletActionContext.getRequest().getSession();
		 UserLogin userLogin = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		
		String hql = "select ad from DAdPosition ad where ad.id in ("+ userLogin.getDtmbPositionIds()+")";
		return (List<DAdPosition>)this.getListForAll(hql, null);
	}

	@Override
	public PageBeanDB queryDTMBPloyList(DPloy ploy, int pageNo, int pageSize) {
		String hql = "from DPloy ploy where ploy.dposition.positionCode ='" + ploy.getDposition().getPositionCode() + "' and ploy.status in(2,4)";
		
		HttpSession session = ServletActionContext.getRequest().getSession();
	    UserLogin userLogin = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		List<Integer> accessUserId = userLogin.getAccessUserIds();
		hql +=" and ploy.operatorId in ("+accessUserId.toString().replaceAll("\\[|\\]|\\s", "")+")";
		
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
					"select "+order.getOrderCode()+",a.type_value,b.type_value,b.value_name,"+(StringUtils.isBlank(mainPloy)?"\"\"":mainPloy)+","+(StringUtils.isBlank(mainPloy) || num == 0?"0,'全频道'":"c.type_value,c.value_name") +","+i+
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
		if(omrTmp != null && omrTmp.getReleaseArea() != null && StringUtils.isNotBlank(omrTmp.getReleaseArea().getAreaCode())){
			hql += " and tmp.releaseArea.areaCode = '"+ omrTmp.getReleaseArea().getAreaCode()+"'";
		}
		if(omrTmp != null && StringUtils.isNotBlank(omrTmp.getStartTime())){
			hql += " and tmp.startTime <='" + omrTmp.getStartTime() + "'";
		}
		if(omrTmp != null && StringUtils.isNotBlank(omrTmp.getEndTime())){
			hql += " and tmp.endTime >='" + omrTmp.getEndTime() + "'";
		}
		hql += " order by tmp.releaseArea.areaCode, tmp.indexNum";
		return this.getPageList2(hql, null, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReleaseArea> queryReleaseAreaList() {
		
		 HttpSession session = ServletActionContext.getRequest().getSession();
		 UserLogin userLogin = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		
		String hql = "select area from ReleaseArea area,UserArea ua where area.areaCode = ua.releaseAreacode and ua.userId = ?";
		return (List<ReleaseArea>)this.getListForAll(hql, new Object[]{userLogin.getUserId()});
	}

	@Override
	public PageBeanDB queryDResourceList(DResource resource, int pageNo, int pageSize) {
		String hql = "from DResource res where res.status='2' and res.positionCode='" + resource.getPositionCode()+"'";
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

	@SuppressWarnings("all")
	@Override
	public PageBeanDB queryAuditDOrderList(DOrder order, int pageNo, int pageSize) {
		List param = new ArrayList();
		String hql = "from DOrder order where order.state < '3'";
		if(order != null && order.getCustomer() != null && order.getCustomer().getId() != null){
			hql += " and order.customer.id=" + order.getCustomer().getId();
		}
		if(order != null && order.getContract() != null && StringUtils.isNotBlank(order.getContract().getContractName())){
			hql += " and order.contract.contractName like '%" + order.getContract().getContractName() + "%'";
		}
		if(order != null && order.getDposition() != null && StringUtils.isNotBlank(order.getDposition().getPositionName())){
			hql += " and order.dposition.positionName like '%" + order.getDposition().getPositionName() + "%'";
		}
		if(order != null && order.getStartDate() != null){
			hql += " and order.startDate= ?";
			param.add(order.getStartDate());
		}
		if(order != null && order.getEndDate() != null){
			hql += " and order.endDate = ?";
			param.add(order.getEndDate());
		}
		HttpSession session = ServletActionContext.getRequest().getSession();
	    UserLogin userLogin = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		List<Integer> accessUserId = userLogin.getAccessUserIds();
		hql +=" and order.operatorId in ("+accessUserId.toString().replaceAll("\\[|\\]|\\s", "")+")";
		hql +=" and order.dposition.id in ("+userLogin.getDtmbPositionIds()+")";
		return this.getPageList2(hql, param.toArray(), pageNo, pageSize);
	}

	@Override
	public int insertPlayList(DOrder order) {
		String sql = "insert into d_play_list(order_code, position_code, area_code, start_date, end_date,start_time,end_time,ploy_type,type_value,resource_id,resource_path,index_num,status,user_industrys,user_levels,tvn,is_default) "+
					 "(select od.order_code,od.position_code,omr.area_code,od.start_date,od.end_date,omr.start_time,omr.end_time,omr.ploy_type," +
					 "(SELECT group_concat(ch.service_id) FROM d_channel_group_ref gr,d_channelinfo_sync ch WHERE gr.channel_id = ch.channel_id AND gr.group_id = omr.type_value)," +
					 "omr.resource_id,CASE res.resource_type" +
					 " WHEN 0 THEN (SELECT name from t_image_meta where id=res.resource_id)" +
					 " WHEN 1 THEN (SELECT name FROM t_video_meta where id=res.resource_id)" +
					 " WHEN 2 THEN (SELECT content FROM t_text_meta where id=res.resource_id)" +
					 " ELSE '' END,omr.index_num,'0',"+
					 "(SELECT group_concat(type_value) from d_ploy_detail where ploy_type='5' and ploy_id = od.ploy_id group by ploy_id),"+
					 "(SELECT group_concat(type_value) from d_ploy_detail where ploy_type='6' and ploy_id = od.ploy_id group by ploy_id),"+
					 "(SELECT group_concat(type_value) from d_ploy_detail where ploy_type='7' and ploy_id = od.ploy_id group by ploy_id),"+
				     "od.is_default "+
					 "from d_order od, d_order_mate_rel omr,d_resource res where od.order_code = omr.order_code and omr.resource_id = res.id and od.id="+order.getId()+")";
		return this.executeBySQL(sql, null);
	}

	@Override
	public int updatePlayListEndDate(DOrder order) {
		String sql = "update d_play_list set end_date =? , end_time= ? where order_code = '" +order.getOrderCode()+"'";
		return this.executeBySQL(sql, new Object[]{order.getEndDate(),new SimpleDateFormat("HH:mm:ss").format(order.getEndDate())});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> checkDOrderRule(DOrder order) {
		String sql = "select od.id from d_order od, d_order_mate_rel omr1,d_order_mate_rel_tmp omr2 "+
					 "where od.order_code = omr1.order_code and omr1.resource_id is not null "+
					 "and omr1.area_code = omr2.area_code and omr2.order_code = ? and omr2.resource_id is not null "+
					 "and od.position_code = ? and od.order_code <> ? and od.start_date < ? and od.end_date > ? and omr1.start_time < omr2.end_time and omr1.end_time > omr2.start_time "+
					 "and (omr1.ploy_type = \"\" or (omr1.type_value = omr2.type_value or omr1.type_value=0 or omr2.type_value=0))";
		return (List<Integer>)this.getDataBySql(sql, new Object[]{order.getOrderCode(),order.getDposition().getPositionCode(),order.getOrderCode(),order.getEndDate(),order.getStartDate()});
	}

	@Override
	public void delDOrderMateRelTmp(String ids) {
		String hql = "update d_order_mate_rel_tmp set resource_id = null where id in("+ids+")";
		this.executeBySQL(hql, null);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerList() {
		String hql = "from Customer";
		return (List<Customer>)this.getListForAll(hql, null);
	}

	@Override
	public int updatePlayListState(String orderCode) {
		String sql = "update d_play_list set status = '0' where order_code = ?";
		return this.executeBySQL(sql, new Object[]{orderCode});
	}

	@Override
	public int updateOrderState(String orderCode) {
		String hql = "update DOrder od set od.state='6' where od.orderCode = ?";
		return this.executeByHQL(hql, new Object[]{orderCode});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DOrderMateRel> getOrderMateRelList(String orderCode) {
		String hql = "from DOrderMateRel omr where omr.orderCode = ? and omr.resource is not null";
		return this.getListForAll(hql, new Object[]{orderCode});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getChannelGroupServiceIds(Integer groupId) {
		String hql = "select ref.serviceId from DChannelGroupRef ref where ref.groupId = ?";
		return (List<String>)this.getListForAll(hql, new Object[]{groupId.longValue()});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPloyValueByType(Integer ployId, String ployType) {
		String hql = "select detail.typeValue from DPloyDetail detail where detail.ployId=? and detail.ployType = ?";
		return this.getListForAll(hql, new Object[]{ployId, ployType});
	}

	@Override
	public DAdPosition getDPostionByPositionCode(String positionCode) {
		String hql = "from DAdPosition ap where ap.positionCode = ?";
		return (DAdPosition)this.get(hql, positionCode);
	}

}
