package com.dvnchina.advertDelivery.order.dao.impl;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.channelGroup.bean.TChannelGroup;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.Contract;
import com.dvnchina.advertDelivery.model.ImageReal;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.model.ResourceReal;
import com.dvnchina.advertDelivery.model.UserLogin;
import com.dvnchina.advertDelivery.model.VideoReal;
import com.dvnchina.advertDelivery.order.bean.AreaResource;
import com.dvnchina.advertDelivery.order.bean.DtvFtpInfo;
import com.dvnchina.advertDelivery.order.bean.Order;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelation;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelationTmp;
import com.dvnchina.advertDelivery.order.bean.playlist.MaterialBean;
import com.dvnchina.advertDelivery.order.bean.playlist.TextMate;
import com.dvnchina.advertDelivery.order.dao.OrderDao;
import com.dvnchina.advertDelivery.ploy.bean.Ploy;
import com.dvnchina.advertDelivery.ploy.bean.PloyBackup;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatch;
import com.dvnchina.advertDelivery.ploy.dao.PloyDao;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.DateUtil;

public class OrderDaoImpl extends BaseDaoImpl implements OrderDao{
	
	private PloyDao ployDao;
	public PloyDao getPloyDao() {
		return ployDao;
	}

	public void setPloyDao(PloyDao ployDao) {
		this.ployDao = ployDao;
	}

	/**
	 * 分页查询订单信息
	 * @param userId
	 * @param order
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryOrderList(Order order,int pageNo, int pageSize){
		StringBuffer sql =  new StringBuffer();
		sql.append("select distinct o.id,o.order_code,o.contract_id,o.position_id,o.ploy_id,o.order_type,o.start_time,o.end_time,");
		sql.append(" o.create_time,o.modify_time,o.state,c.contract_name,ad.position_name,p.ploy_name,'' ");
		sql.append(" from t_order o left join t_contract c on o.contract_id = c.id, t_advertposition ad, t_ploy p");
		sql.append(" where  o.position_id = ad.id and o.ploy_id = p.ploy_id");

		HttpSession session = ServletActionContext.getRequest().getSession();
		UserLogin user = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		List<Integer> positionIdList = null;
		if(user.getRoleType() == 1){
			//广告商，只能查询自己的创建的订单
			sql.append(" and exists (select uc.id from t_user_adcustomer uc  ");
			sql.append(" where uc.cutomer_id=c.custom_id and uc.cutomer_id=").append(user.getCustomerId()).append(" )");
//			positionIdList = this.getADPackageIdByCust(user.getCustomerId());
		}else if(user.getRoleType() == 2){
			//运营商，只能查询自己有广告位权限的订单
			positionIdList = user.getPositionIds();
			String positionPackageIds = "-1,";
			for(Integer packageId : positionIdList){
				positionPackageIds += packageId+",";
			}
			positionPackageIds = positionPackageIds.substring(0,positionPackageIds.length()-1); 
			sql.append(" and ad.position_package_id in (").append(positionPackageIds).append(")");
		}
		
		if(order != null){
			if (StringUtils.isNotBlank(order.getContractName())) {
				sql.append(" and c.contract_name like '%" + order.getContractName().trim() + "%' ");
			}
			if (StringUtils.isNotBlank(order.getPositionName())) {
				sql.append(" and ad.position_name like '%" + order.getPositionName().trim() + "%' ");
			}
			if (StringUtils.isNotBlank(order.getPloyName())) {
				sql.append(" and p.ploy_name like '%" + order.getPloyName().trim() + "%' ");
			}
			if (StringUtils.isNotBlank(order.getState())) {
				sql.append(" and o.state in ( " + order.getState() +" )" );
			}else{
				//查询非执行完毕的订单
				sql.append(" and o.state <> 7 " );
			}
			if (StringUtils.isNotBlank(order.getStartDateStr())) {
				sql.append(" and  o.start_time >= str_to_date('" + order.getStartDateStr() + "','%Y-%m-%d') ");
				//sql.append(" and o.end_time >= str_to_date('" + order.getStartDateStr() + "','%Y-%m-%d') )");
			}
			if (StringUtils.isNotBlank(order.getEndDateStr())) {
				sql.append(" and  o.end_time <= str_to_date('" + order.getEndDateStr() + "','%Y-%m-%d') ");
				//sql.append(" and o.start_time <= str_to_date('" + order.getEndDateStr() + "','%Y-%m-%d') )");
			}
		}else{
			//查询非执行完毕的订单
			sql.append(" and o.state <> 7 " );
		}
		
		sql.append(" order by o.state,o.CREATE_TIME desc ");
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<Order> list = getOrderList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	
	private List<Order> getOrderList(List<?> resultList) {
		List<Order> list = new ArrayList<Order>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Order order = new Order();
			order.setId(toInteger(obj[0]));
			order.setOrderCode((String)(obj[1]));
			order.setContractId(toInteger(obj[2]));
			order.setPositionId(toInteger(obj[3]));
			order.setPloyId(toInteger(obj[4]));
			order.setOrderType(toInteger(obj[5]));
			order.setStartTime((Date)obj[6]);
			order.setEndTime((Date)obj[7]);
			order.setCreateTime((Date)obj[8]);
			order.setModifyTime((Date)obj[9]);
			order.setState(((Character)(obj[10])).toString());
			if(order.getContractId() == null || order.getContractId().intValue()==0){
				order.setContractName("无合同");
			}else{
				order.setContractName((String)(obj[11]));
			}
			order.setPositionName((String)(obj[12]));
			order.setPloyName((String)(obj[13]));
			order.setCustomerName((String)(obj[14]));
			list.add(order);
		}
		return list;
	}
	
	/**
	 * 根据客户ID获取合同对应的广告位包ID
	 * @param customerId
	 * @return
	 */
	private List<Integer> getADPackageIdByCust(Integer customerId){
		String sql = " select pp.id from t_contract c ,t_contract_ad ca ,t_position_package pp "
			+ "where c.id = ca.contract_id and ca.ad_id = pp.id " 
			//+ "and ca.valid_start <= NOW() " 
			//+ "and ca.valid_end > DATE_ADD(now(), Interval -1 day) " 
			+ "and c.custom_id="+customerId.intValue();
		List<?> list = this.getDataBySql(sql, null);
		List<Integer> packageIdList = new ArrayList<Integer>();
		if(list != null && list.size()>0){
			for(Object obj : list){
				packageIdList.add(toInteger(obj));
			}
		}
		return packageIdList;
	}
	
	/**
	 * 分页查询审核订单信息（只有运营商能审核）
	 * @param order
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryOrderAuditList(Order order,int pageNo, int pageSize){
		PageBeanDB page = new PageBeanDB();
		HttpSession session = ServletActionContext.getRequest().getSession();
		UserLogin user = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		if(user.getRoleType() != 2){
			//非运营商
			return page;
		}
		String pids = "-1,";
		for(Integer pid : user.getPositionIds()){
			pids += pid+",";
		}
		pids = pids.substring(0,pids.length()-1);
		StringBuffer sql =  new StringBuffer();
		sql.append("select distinct o.id,o.order_code,o.contract_id,o.position_id,o.ploy_id,o.order_type,o.start_time,o.end_time,");
		sql.append(" o.create_time,o.modify_time,o.state,c.contract_name,ad.position_name,pl.ploy_name,cust.advertisers_name");	
		sql.append(" from t_order o left join (t_contract c join t_customer cust on c.custom_id = cust.id) on o.contract_id = c.id,");
		sql.append(" t_advertposition ad,t_position_package pp,t_ploy pl");
		sql.append(" where pl.ploy_id=o.ploy_id and ad.id=o.position_id and ad.position_package_id = pp.id");
		sql.append(" and o.state in(");
		sql.append(Constant.ORDER_PENDING_CHECK).append(",").append(Constant.ORDER_PENDING_CHECK_UPDATE);
		sql.append(",").append(Constant.ORDER_PENDING_CHECK_DELETE).append(") ");
		sql.append(" and pp.id in ( ").append(pids).append(" ) ");

		
		if(order != null){
			if(order.getCustomerId() != null){
				sql.append(" and c.custom_id = " + order.getCustomerId().intValue());
			}
			
			if (order.getOrderType() != null ) {
				sql.append(" and o.order_type=" + order.getOrderType());
			}
			if (StringUtils.isNotBlank(order.getContractName())) {
				sql.append(" and c.contract_name like '%" + order.getContractName() + "%' ");
			}
			if (StringUtils.isNotBlank(order.getPositionName())) {
				sql.append(" and ad.position_name like '%" + order.getPositionName() + "%' ");
			}
			if (StringUtils.isNotBlank(order.getStartDateStr())) {
				sql.append(" and o.start_time >= str_to_date('" + order.getStartDateStr() + "','%Y-%m-%d') ");
				//sql.append(" and o.end_time >= str_to_date('" + order.getStartDateStr() + "','%Y-%m-%d') )");
			}
			if (StringUtils.isNotBlank(order.getEndDateStr())) {
				sql.append(" and o.end_time <= str_to_date('" + order.getEndDateStr() + "','%Y-%m-%d') ");
				//sql.append(" and o.start_time <= str_to_date('" + order.getEndDateStr() + "','%Y-%m-%d') )");
			}
		}
		
		
//		StringBuffer sql =  new StringBuffer();
//		sql.append("select distinct o.id,o.order_code,o.contract_id,o.position_id,o.ploy_id,o.order_type,o.start_time,o.end_time,");
//		sql.append(" o.create_time,o.modify_time,o.state,c.contract_name,ad.position_name,pl.ploy_name,cust.advertisers_name");	
//		sql.append(" from t_order o,t_advertposition ad,t_ploy pl,t_contract c, t_customer cust");
//		sql.append(" where pl.ploy_id=o.ploy_id and ad.id=o.position_id and o.contract_id=c.id and c.custom_id = cust.id");
//		sql.append(" and o.state in(");
//		sql.append(Constant.ORDER_PENDING_CHECK).append(",").append(Constant.ORDER_PENDING_CHECK_UPDATE);
//		sql.append(",").append(Constant.ORDER_PENDING_CHECK_DELETE).append(") ");
//
//		if(userId != null && userId != 0){
//			sql.append(" and exists (select uc.id from t_user u, t_user_adcustomer uc  ");
//			sql.append(" where uc.user_id=u.user_id and uc.cutomer_id=c.custom_id and u.user_id= ").append(userId).append(" )");
//		}
//		
//		if(order != null){
//			if(order.getCustomerId() != null){
//				sql.append(" and c.custom_id = " + order.getCustomerId().intValue());
//			}
//			
//			if (order.getOrderType() != null ) {
//				sql.append(" and o.order_type=" + order.getOrderType());
//			}
//			if (StringUtils.isNotBlank(order.getContractName())) {
//				sql.append(" and c.contract_name like '%" + order.getContractName() + "%' ");
//			}
//			if (StringUtils.isNotBlank(order.getPositionName())) {
//				sql.append(" and ad.position_name like '%" + order.getPositionName() + "%' ");
//			}
//			if (StringUtils.isNotBlank(order.getStartDateStr())) {
//				sql.append(" and ( o.start_time <= str_to_date('" + order.getStartDateStr() + "','%Y-%m-%d') ");
//				sql.append(" and o.end_time >= str_to_date('" + order.getStartDateStr() + "','%Y-%m-%d') )");
//			}
//			if (StringUtils.isNotBlank(order.getEndDateStr())) {
//				sql.append(" and ( o.end_time >= str_to_date('" + order.getEndDateStr() + "','%Y-%m-%d') ");
//				sql.append(" and o.start_time <= str_to_date('" + order.getEndDateStr() + "','%Y-%m-%d') )");
//			}
//		}
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		sql.append(" order by o.start_time ");
		List<Order> list = getOrderList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	
	/**
	 * 根据广告商ID分页查询合同信息
	 * @param customId
	 * @param contract
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryContractList(Integer customId,Contract contract,int pageNo, int pageSize){
		
		StringBuffer sql =  new StringBuffer();
		sql.append("select distinct con.id,con.contract_number,con.contract_code,con.contract_name");
		sql.append(" ,con.effective_start_date,con.effective_end_date");
		sql.append(" from t_contract con,t_contract_ad ca where ");
		sql.append(" con.id = ca.contract_id");
		sql.append(" and con.custom_id = ").append(customId);
		sql.append(" and con.status=").append(Constant.CONTRACT_AUDIT_STATUS_PASS);
		sql.append(" and ca.valid_end > DATE_ADD(now(), Interval -1 day)");
		
		if(contract != null){
			if (StringUtils.isNotBlank(contract.getContractName())) {
				sql.append(" and con.contract_name like '%" + contract.getContractName().trim() + "%' ");
			}
			if (StringUtils.isNotBlank(contract.getContractCode())) {
				sql.append(" and con.contract_code like '%" + contract.getContractCode().trim() + "%' ");
			}
			if (StringUtils.isNotBlank(contract.getContractNumber())) {
				sql.append(" and con.contract_number like '%" + contract.getContractNumber().trim() + "%' ");
			}
		}
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		sql.append(" order by con.effective_start_date ");
		List<Contract> list = getContractList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	
	private List<Contract> getContractList(List<?> resultList) {
		List<Contract> list = new ArrayList<Contract>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Contract con = new Contract();
			con.setId(toInteger(obj[0]));
			con.setContractNumber((String)(obj[1]));
			con.setContractCode((String)(obj[2]));
			con.setContractName((String)(obj[3]));
			con.setEffectiveStartDate((Date)obj[4]);
			con.setEffectiveEndDate((Date)obj[5]);
			list.add(con);
		}
		return list;
	}
	
	/**
	 * 根据合同分页查询广告位信息
	 * @param position
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPositionList(AdvertPosition position,int pageNo, int pageSize){
	
		PageBeanDB page = new PageBeanDB();
		StringBuffer sql =  new StringBuffer();
		if(position.getContractId().intValue() == 0){
			//运营商创建的订单
			HttpSession session = ServletActionContext.getRequest().getSession();
			UserLogin user = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
			String pids = "";
			for(Integer pid : user.getPositionIds()){
				pids += pid+",";
			}
			if(pids.length()>1){
				pids = pids.substring(0,pids.length()-1);
			}
			
			sql.append("select p.id,p.position_name,p.position_code,p.is_hd,p.is_add,p.is_loop,p.loop_count,p.delivery_mode,");
			sql.append(" p.background_path,p.coordinate,p.width_height,now(),str_to_date('2100-01-01','%Y-%m-%d')");
			sql.append(" from t_advertposition p, t_position_package pp where pp.id = p.position_package_id  ");
			sql.append(" and pp.id in ( ").append(pids).append(" ) ");
			
			if(position != null){
				if (StringUtils.isNotBlank(position.getPositionName())) {
					sql.append(" and p.position_name like '%" + position.getPositionName().trim() + "%' ");
				}
				if (position.getDeliveryMode() != null) {
					sql.append(" and p.delivery_mode = " + position.getDeliveryMode());
				}
			}
		}else{
			//广告商创建的订单
			sql.append("select p.id,p.position_name,p.position_code,p.is_hd,p.is_add,p.is_loop,p.loop_count,p.delivery_mode,");
			sql.append(" p.background_path,p.coordinate,p.width_height,");
			sql.append(" str_to_date(date_format(ca.valid_start,'%Y-%m-%d'),'%Y-%m-%d') as valid_start,");
			sql.append(" str_to_date(date_format(ca.valid_end,'%Y-%m-%d'),'%Y-%m-%d') as valid_end");
			sql.append(" from t_advertposition p, t_contract_ad ca,t_position_package pp where pp.id = p.position_package_id  ");
			sql.append(" and ca.ad_id = pp.id and ca.valid_end>DATE_ADD(now(), Interval -1 day) ");
			if(position != null){
				if (position.getContractId() != null) {
					sql.append(" and ca.contract_id = " + position.getContractId());
				}
				if (StringUtils.isNotBlank(position.getPositionName())) {
					sql.append(" and p.position_name like '%" + position.getPositionName().trim() + "%' ");
				}
				if (position.getDeliveryMode() != null) {
					sql.append(" and p.delivery_mode = " + position.getDeliveryMode());
				}
			}
		}
			
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<AdvertPosition> list = getPositionList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		
		return page;
	}
	
	private List<AdvertPosition> getPositionList(List<?> resultList) {
		List<AdvertPosition> list = new ArrayList<AdvertPosition>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			AdvertPosition ad = new AdvertPosition();
			ad.setId(toInteger(obj[0]));
			ad.setPositionName((String)(obj[1]));
			ad.setPositionCode((String)(obj[2]));
			ad.setIsHD(toInteger(obj[3]));
			ad.setIsAdd(toInteger(obj[4]));
			ad.setIsLoop(toInteger(obj[5]));
			ad.setLoopCount(toInteger(obj[6]));
			ad.setDeliveryMode(toInteger(obj[7]));
			ad.setBackgroundPath((String)(obj[8]));
			ad.setCoordinate((String)(obj[9]));
			ad.setWidthHeight((String)(obj[10]));
			ad.setValidStart((Date)obj[11]);
			ad.setValidEnd((Date)obj[12]);
			list.add(ad);
		}
		return list;
	}
	
	/**
	 * 分页查询策略信息
	 * 创建、修改订单时，策略根据广告位和广告商查询（运营商定义策略时（广告商ID=0），可由其他广告商使用；广告商定义策略只能自己使用）
	 * 单向广告位的有分策略、无精准，双向广告位无分策略，有主策略和精准（已作废）
	 * 双向广告位只查询主策略信息
	 * @param ploy
	 * @param adPackageType
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPloyList(Ploy ploy,Integer adPackageType,int pageNo, int pageSize){
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		UserLogin user = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		
		//查询单向广告位
		boolean isOneWay = Constant.POSITION_TYPE_ONE_NOT_REAL_TIME == adPackageType.intValue() 
				|| Constant.POSITION_TYPE_ONE_REAL_TIME == adPackageType.intValue() 
				|| Constant.POSITION_TYPE_ONE_DATA_BROADCASTING == adPackageType.intValue();
		
		//回放（频道）广告 ，  IS_PLAYBACK = 1
		boolean isPlayBack = ploy.getPositionId().intValue() == 17 
				|| ploy.getPositionId().intValue() == 39 
				|| ploy.getPositionId().intValue() == 40;
		
		//回看产品、回看栏目（菜单）广告，  IS_LOOKBACK_PRODUCT=1、IS_COLUMN=1
		boolean isLookBack = ploy.getPositionId().intValue() == 15 || ploy.getPositionId().intValue() == 16 			
				|| ploy.getPositionId().intValue() == 19 || ploy.getPositionId().intValue() == 20
				|| ploy.getPositionId().intValue() == 43;
		
		StringBuffer sql =  new StringBuffer();
		if(isOneWay){			
			sql.append("select A.id,A.ploy_id,A.ploy_name");
			sql.append(" from t_ploy A join (select Max(id) id from t_ploy group by ploy_id,ploy_name) B on A.id=B.id ");
		}else if(isPlayBack|| isLookBack || ploy.getPositionId().intValue() == 25 || ploy.getPositionId().intValue() == 26  //随片广告
				){			
			sql.append("select A.id,A.ploy_id,A.ploy_name");
			sql.append(" from t_ploy A join (select Max(id) id from t_ploy group by ploy_id,ploy_name) B on A.id=B.id ");
		}else{
			//查询双向广告位策略
			sql.append("select A.id,A.ploy_id,A.ploy_name,A.start_time,A.end_time,A.ploy_number from t_ploy A ");
			sql.append("join (select Max(id) id from t_ploy group by ploy_id,ploy_name) B on A.id=B.id");
		}
		
		
		sql.append(" where A.customer_id in ( ").append(user.getCustomerId()+",0").append(" ) ");
		if(ploy != null){
			if (ploy.getPositionId() != null && ploy.getPositionId() != 0) {
				sql.append(" and A.position_id = " + ploy.getPositionId());
			}
			if (StringUtils.isNotBlank(ploy.getPloyName())) {
				sql.append(" and A.ploy_name like '%" + ploy.getPloyName().trim() + "%' ");
			}
		}
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<Ploy> list = null;
		if(isOneWay){
			if(ploy.getPositionId().intValue() == 1||ploy.getPositionId().intValue() == 2
					||ploy.getPositionId().intValue() == 13||ploy.getPositionId().intValue() == 14
					||ploy.getPositionId().intValue() == 45||ploy.getPositionId().intValue() == 46){
				
				list = getOne2PloyList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
			}else{
				list = getOnePloyList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
			}
			
		}else if(isPlayBack){
			list = getPlayBackList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		}else if(isLookBack || ploy.getPositionId().intValue() == 25 || ploy.getPositionId().intValue() == 26){
			list = getLookBackList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		}else{
			//双向广告位
			list = getTwoPloyList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		}
		page.setDataList(list);
		return page;
	}
	
	/**
	 * 设置回放（频道）广告位信息
	 * @param resultList
	 * @return
	 */
	private List<Ploy> getPlayBackList(List<?> resultList) {
		List<Ploy> list = new ArrayList<Ploy>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Ploy ploy = new Ploy();
			ploy.setId(toInteger(obj[0]));
			ploy.setPloyId(toInteger(obj[1]));
			ploy.setPloyName((String)(obj[2]));
			ploy.setSubPloyList(getSubPlayBackPloyList(toInteger(obj[1])));
			list.add(ploy);
		}
		return list;
	}
	
	/**
	 * 根据策略ID获取回放（频道）广告子策略
	 * @param ployId
	 * @return
	 */
	public List<Ploy> getSubPlayBackPloyList(Integer ployId){
		String sql = " select p.id,p.start_time,p.end_time,p.priority,g.name,a.AREA_NAME from t_ploy p left join t_channel_group_npvr g  "
			+ "on p.channel_group_id = g.id "
			+ "  left join t_release_area a on p.AREA_ID = a.AREA_CODE "
			+ "where p.ploy_id=? order by p.start_time,g.name,p.priority";
		List<Ploy> subPloyList = getSubPloyList(this.getDataBySql(sql, new Object[]{ployId}),ployId);
		return subPloyList;
	}
	
	/**
	 * 设置单向广告位策略信息
	 * @param resultList
	 * @return
	 */
	private List<Ploy> getOnePloyList(List<?> resultList) {
		List<Ploy> list = new ArrayList<Ploy>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Ploy ploy = new Ploy();
			ploy.setId(toInteger(obj[0]));
			ploy.setPloyId(toInteger(obj[1]));
			ploy.setPloyName((String)(obj[2]));
			ploy.setSubPloyList(getSubPloyListByPloy(toInteger(obj[1])));
			list.add(ploy);
		}
		return list;
	}
	/**
	 * 设置单向广告位策略信息
	 * @param resultList
	 * @return
	 */
	private List<Ploy> getOne2PloyList(List<?> resultList) {
		List<Ploy> list = new ArrayList<Ploy>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Ploy ploy = new Ploy();
			ploy.setId(toInteger(obj[0]));
			ploy.setPloyId(toInteger(obj[1]));
			ploy.setPloyName((String)(obj[2]));
			ploy.setSubPloyList(getStartSubPloyListByPloy(toInteger(obj[1])));
			list.add(ploy);
		}
		return list;
	}
	/**
	 * 设置回看回放广告位信息
	 * @param resultList
	 * @return
	 */
	private List<Ploy> getLookBackList(List<?> resultList) {
		List<Ploy> list = new ArrayList<Ploy>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Ploy ploy = new Ploy();
			ploy.setId(toInteger(obj[0]));
			ploy.setPloyId(toInteger(obj[1]));
			ploy.setPloyName((String)(obj[2]));
			ploy.setSubPloyList(getLookBackSubPloyListByPloy(toInteger(obj[1])));
			list.add(ploy);
		}
		return list;
	}
	
	/**
	 * 设置双向广告位策略信息
	 * @param resultList
	 * @return
	 */
	private List<Ploy> getTwoPloyList(List<?> resultList) {
		List<Ploy> list = new ArrayList<Ploy>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Ploy ploy = new Ploy();
			ploy.setId(toInteger(obj[0]));
			ploy.setPloyId(toInteger(obj[1]));
			ploy.setPloyName((String)(obj[2]));
			if(obj[3]==null || "0".equals((String)obj[3])){
				ploy.setStartTime("00:00:00");
				ploy.setEndTime("23:59:59");
			}else{
				ploy.setStartTime((String)obj[3]);
				ploy.setEndTime((String)obj[4]);
			}
			ploy.setSubPloyList(getSubPloyListByPloyID(toInteger(obj[1])));
			ploy.setPloyNumber(toInteger(obj[5]));
			list.add(ploy);
		}
		return list;
	}
	
	/**
	 * 根据策略ID  ployId获取子策略
	 * @param ployId
	 * @return
	 */
	public List<Ploy> getSubPloyListByPloy(Integer ployId){
		String sql = " select p.id,p.start_time,p.end_time,p.priority,g.name,a.AREA_NAME from t_ploy p left join t_channel_group g  "
			+ "on p.channel_group_id = g.id "
			+ "  left join t_release_area a on p.AREA_ID = a.AREA_CODE "
			+ "where p.ploy_id=? order by p.start_time,g.name,p.priority";
		List<Ploy> subPloyList = getSubPloyList(this.getDataBySql(sql, new Object[]{ployId}),ployId);
		return subPloyList;
	}
	/**
	 * 根据策略ID  ployId获取子策略
	 * @param ployId
	 * @return
	 */
	public List<Ploy> getStartSubPloyListByPloy(Integer ployId){
		String sql = " select p.id,p.start_time,p.end_time,p.priority,g.name,a.AREA_NAME from t_ploy p left join t_channel_group g  "
			+ "on p.channel_group_id = g.id "
			+ "  left join t_release_area a on p.AREA_ID = a.AREA_CODE "
			+ "where p.ploy_id=? order by p.start_time,g.name,p.priority";
		List<Ploy> subPloyList = getStartSubPloyList(this.getDataBySql(sql, new Object[]{ployId}),ployId);
		return subPloyList;
	}
	/**
	 * 根据策略ID  ployId获取子策略
	 * @param ployId33 107 153 153 176 176 185 194 194
	 * @return
	 */
	public List<Ploy> getSubPloyListByPloyID(Integer ployId){
		String sql = " select p.id,p.start_time,p.end_time from t_ploy p   "
			+ "where p.ploy_id=? order by p.start_time";
		List<Ploy> subPloyList = getSubPloyList2(this.getDataBySql(sql, new Object[]{ployId}),ployId);
		return subPloyList;
	}
	
	/**
	 * 回看回放菜单广告位根据策略ID  ployId获取子策略
	 * @param ployId
	 * @return
	 */
	private List<Ploy> getLookBackSubPloyListByPloy(Integer ployId){
//		String sql = " select p.id,p.start_time,p.end_time from t_ploy p "
//			+ "where p.ploy_id=? order by p.start_time";
		String sql = " select p.id,p.start_time,p.end_time,m.match_name from t_ploy p "
				+" left join t_precise_match m on p.ploy_id=m.ploy_id "
				+ "where p.ploy_id=? order by p.start_time";
		List<Ploy> subPloyList = getLookBackSubPloyList(this.getDataBySql(sql, new Object[]{ployId}),ployId);
		return subPloyList;
	}
	
	private List<Ploy> getLookBackSubPloyList(List<?> resultList,Integer ployId) {
		List<Ploy> list = new ArrayList<Ploy>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Ploy ploy = new Ploy();
			ploy.setId(toInteger(obj[0]));
			ploy.setPloyId(ployId);
			if(obj[1]==null || "0".equals((String)obj[1])){
				ploy.setStartTime("00:00:00");
				ploy.setEndTime("23:59:59");
			}else{
				ploy.setStartTime((String)obj[1]);
				ploy.setEndTime((String)obj[2]);
			}
			ploy.setDescription((String)obj[3]);
			list.add(ploy);
		}
		return list;
	}
	
	private List<Ploy> getSubPloyList(List<?> resultList,Integer ployId) {
		List<Ploy> list = new ArrayList<Ploy>();
		//int i=0;
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Ploy ploy = new Ploy();
			ploy.setId(toInteger(obj[0]));
			ploy.setPloyId(ployId);
			if(obj[1]==null || "0".equals((String)obj[1])){
				ploy.setStartTime("00:00:00");
				ploy.setEndTime("23:59:59");
			}else{
				ploy.setStartTime((String)obj[1]);
				ploy.setEndTime((String)obj[2]);
			}
			ploy.setPriority(toInteger(obj[3]));
			
			if(obj[4] == null){
				ploy.setGroupName("无频道组");
			}else{
				ploy.setGroupName((String)obj[4]);
			}
			if(obj[5] == null){   
				//区域为空 不赋值
				ploy.setDescription("河南");   //用该字段存放区域名
			}else{
				ploy.setDescription((String)obj[5]);
			}
			list.add(ploy);
		}
		return list;
	}
	
	private List<Ploy> getStartSubPloyList(List<?> resultList,Integer ployId) {
		List<Ploy> list = new ArrayList<Ploy>();
		//int i=0;
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Ploy ploy = new Ploy();
			ploy.setId(toInteger(obj[0]));
			ploy.setPloyId(ployId);
			if(obj[1]==null || "0".equals((String)obj[1])){
				ploy.setStartTime("00:00:00");
				ploy.setEndTime("23:59:59");
			}else{
				ploy.setStartTime((String)obj[1]);
				ploy.setEndTime((String)obj[2]);
			}
			ploy.setPriority(toInteger(obj[3]));
			
			if(obj[4] == null){
				ploy.setGroupName("无频道组");
			}else{
				ploy.setGroupName((String)obj[4]);
			}
			if(obj[5] == null){   
				//区域为空 不赋值
				ploy.setDescription("河南");   //用该字段存放区域名
			}else{
				ploy.setDescription((String)obj[5]);
			}
			list.add(ploy);
		}
		return list;
	}
	private List<Ploy> getSubPloyList2(List<?> resultList,Integer ployId) {
		List<Ploy> list = new ArrayList<Ploy>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			Ploy ploy = new Ploy();
			ploy.setId(toInteger(obj[0]));
			ploy.setPloyId(ployId);
			if(obj[1]==null || "0".equals((String)obj[1])){
				ploy.setStartTime("00:00:00");
				ploy.setEndTime("23:59:59");
			}else{
				ploy.setStartTime((String)obj[1]);
				ploy.setEndTime((String)obj[2]);
			}
			/*ploy.setPriority(toInteger(obj[3]));
			
			if(obj[4] == null){
				ploy.setGroupName("无频道组");
			}else{
				ploy.setGroupName((String)obj[4]);
			}
			if(obj[5] == null){
				//区域为空 不赋值
			}else{
				ploy.setGroupName((String)obj[5]+"-"+ploy.getGroupName());
			}*/
			list.add(ploy);
		}
		return list;
	}
	
//	/**
//	 * 根据策略ID获取区域信息
//	 * @param ployId
//	 * @return
//	 */
//	private List<CommonParentBean> getAreaByPloy(Integer ployId){
//		String sql = "select distinct p.area_id,a.area_name from t_ploy p,t_release_area a "
//			+ "where p.area_id=a.id and p.ploy_id=?";
//		List<CommonParentBean> areaList = getAreaList(this.getDataBySql(sql, new Object[]{ployId}),ployId);
//		return areaList;
//	}
//	
//	/**
//	 * 设置策略对应的区域信息
//	 * @param resultList
//	 * @return
//	 */
//	private List<CommonParentBean> getAreaList(List<?> resultList,Integer ployId) {
//		List<CommonParentBean> list = new ArrayList<CommonParentBean>();
//		for (int i=0; i<resultList.size(); i++) {
//			Object[] obj = (Object[]) resultList.get(i);
//			CommonParentBean area = new CommonParentBean();
//			area.setId(toInteger(obj[0]));
//			area.setName((String)obj[1]);
//			area.setChild(getChannelByPloyAndArea(ployId,toInteger(obj[0])));
//			list.add(area);
//		}
//		return list;
//	}
//	
//	/**
//	 * 根据策略ID和区域ID获取频道信息
//	 * @param ployId
//	 * @param areaId
//	 * @return
//	 */
//	private List<CommonBean> getChannelByPloyAndArea(Integer ployId,Integer areaId){
//		String sql = "select distinct(p.channel_id),c.channel_name from t_ploy p,t_channelInfo c "
//			+ "where p.channel_id=c.channel_id and p.ploy_id=? and p.area_id=?";
//		List<CommonBean> areaList = getChannelList(this.getDataBySql(sql, new Object[]{ployId,areaId}));
//		return areaList;
//	}
//	
//	/**
//	 * 设置策略和区域对应的频道信息
//	 * @param resultList
//	 * @return
//	 */
//	private List<CommonBean> getChannelList(List<?> resultList) {
//		List<CommonBean> list = new ArrayList<CommonBean>();
//		for (int i=0; i<resultList.size(); i++) {
//			Object[] obj = (Object[]) resultList.get(i);
//			CommonBean channel = new CommonBean();
//			channel.setId(toInteger(obj[0]));
//			channel.setName((String)obj[1]);
//			list.add(channel);
//		}
//		return list;
//	}
	
	/**
	 * 根据策略ID获取精准列表
	 * @param ployId
	 * @return
	 */
	public List<TPreciseMatch> queryPreciseListByPloy(Integer ployId){
		List<TPreciseMatch> list = new ArrayList<TPreciseMatch>();
		setPloy(list,ployId);
		String sql = "select id,match_name from t_precise_match where ploy_id=?";
		getPreciseList(list,this.getDataBySql(sql, new Object[]{ployId}));
		
		return list;
	}
	
	private void getPreciseList(List<TPreciseMatch> list,List<?> resultList) {
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			TPreciseMatch precise = new TPreciseMatch();
			precise.setAssetIds("0_"+toLong(obj[0]));
			precise.setMatchName((String)obj[1]);
			list.add(precise);
		}
	}
	
	private void setPloy(List<TPreciseMatch> list,Integer ployId){
		String sql = "select id,ploy_name from t_ploy where ploy_id=?";
		List ployList = this.getDataBySql(sql, new Object[]{ployId});
		if(ployList != null && ployList.size()>0){
			Object[] obj = (Object[]) ployList.get(0);
			TPreciseMatch precise = new TPreciseMatch();
			precise.setAssetIds("1_"+toLong(obj[0]));
			precise.setMatchName((String)obj[1]);
			list.add(precise);
		}
	}
	
	/**
	 * 查询素材信息
	 * @param resource
	 * @return
	 */
	public List<ResourceReal> queryResourceList(ResourceReal resource){
		StringBuffer hql = new StringBuffer("from ResourceReal where (isDefault = 0 or isDefault is null) ");
		HttpSession session = ServletActionContext.getRequest().getSession();
		UserLogin user = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		if(user.getRoleType() == 1){
			//广告商
			hql.append(" and customerId=").append(user.getCustomerId());
		}else if(user.getRoleType() == 2){
			//运营商
			hql.append(" and customerId=0");
		}
		
		if(resource != null){
			if(resource.getAdvertPositionId() != null && resource.getAdvertPositionId() != 0){
				hql.append(" and advertPositionId = ").append(resource.getAdvertPositionId());
			}
			if(StringUtils.isNotEmpty(resource.getResourceName())){
				hql.append(" and resourceName like '%").append(resource.getResourceName().trim()).append("%'");
			}
		}
		hql.append(" and state <> 4");
		return this.list(hql.toString());
	}
	
	/**
	 * 查询素材信息
	 * @param ids
	 * @return
	 */
	public List<ResourceReal> getResourceListByIds(String ids){
		String hql = "from ResourceReal where id in ("+ids+")";
		return this.list(hql, null);
	}
	
	/**
	 * 根据ID获取视频规格
	 * @param id
	 * @return
	 */
	public VideoReal getVideoRealById(Integer id){
		return this.getHibernateTemplate().get(VideoReal.class, id);
	}
	
	/**
	 * 根据ID获取图片规格
	 * @param id
	 * @return
	 */
	public ImageReal getImageRealById(Integer id){
		return this.getHibernateTemplate().get(ImageReal.class, id);
	}
	
	/**
	 * 根据日期范围，广告位ID和要排除的订单主键查询订单记录数
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @param positionId
	 *            广告位ID
	 * @param orderId
	 *            订单主键
	 * @return 订单记录数
	 */
	public int getOrderNumByDate(String start, String end, Integer positionId,Integer orderId){
		String sql = "select t.id from t_order t where((start_time>=str_to_date(?,'%Y-%m-%d') ";
		if(StringUtils.isNotBlank(end)){
			sql = sql + "and start_time<=str_to_date('"+end+"','%Y-%m-%d')";
		}
		sql += ") or (start_time<=str_to_date(?,'%Y-%m-%d') and end_time>=str_to_date(?,'%Y-%m-%d'))) and position_id=? ";
		
		if(orderId != null && orderId.intValue() != 0){
			sql = sql + "and id!="+orderId.intValue();
		}
	
		List list = this.getDataBySql(sql, new Object[]{start, start, start, positionId});
		if(list != null && list.size()>0){
			return list.size();
		}
		return 0;
	}
	
	
	
	@Override
	public int getOrderNumByDateAndArea(String start, String end,Integer positionId, Integer orderId) {
		
		String areaSql = "SELECT in_p.AREA_ID FROM t_order in_o, t_ploy in_p WHERE in_o.ID = " + orderId.intValue() + " AND in_o.PLOY_ID = in_p.PLOY_ID";
		List areaList = this.getDataBySql(areaSql, null);
		if(areaList != null && areaList.size()>0){
			String areaCode = areaList.get(0).toString();
			if("0".equals(areaCode) || "152000000000".equals(areaCode)){
				return getOrderNumByDate(start, end, positionId, orderId);
			}
		}
		
		String sql = "select t.id from t_order t, t_ploy p where t.PLOY_ID = p.PLOY_ID and ((t.start_time>=str_to_date(?,'%Y-%m-%d') ";
		if(StringUtils.isNotBlank(end)){
			sql = sql + "and t.start_time<=str_to_date('"+end+"','%Y-%m-%d')";
		}
		sql += ") or (t.start_time<=str_to_date(?,'%Y-%m-%d') and t.end_time>=str_to_date(?,'%Y-%m-%d'))) and t.position_id=? ";
		sql = sql + "and t.id!="+orderId.intValue();
		sql += " AND p.AREA_ID IN (SELECT in_p.AREA_ID FROM t_order in_o, t_ploy in_p WHERE in_o.ID =" + orderId.intValue();;
		sql += " AND in_o.PLOY_ID = in_p.PLOY_ID )";
	
		List list = this.getDataBySql(sql, new Object[]{start, start, start, positionId});
		if(list != null && list.size()>0){
			return list.size();
		}
		return 0;
	}

	/**
	 * 判断是否存在响应区域的订单
	 * @param start
	 * @param end
	 * @param positionId
	 * @param orderId
	 * @param ployId
	 * @return
	 */
	public boolean exsiteAreaOrder(String start, String end, Integer positionId,Integer orderId, Integer ployId){
		String sql = "select p.area_id from t_order t,t_ploy p where t.ploy_id=p.ploy_id and ((t.start_time>=str_to_date(?,'%Y-%m-%d') ";
		if(StringUtils.isNotBlank(end)){
			sql = sql + "and t.start_time<=str_to_date('"+end+"','%Y-%m-%d')";
		}
		sql += ") or (t.start_time<=str_to_date(?,'%Y-%m-%d') and t.end_time>=str_to_date(?,'%Y-%m-%d'))) and t.position_id=? ";
		
		if(orderId != null && orderId.intValue() != 0){
			sql = sql + "and t.id!="+orderId.intValue();
		}
	
		List<?> orderAreaList = this.getDataBySql(sql, new Object[]{start, start, start, positionId});
		if(orderAreaList != null && orderAreaList.size()>0){
			List<String> oldOrderAreaList = new ArrayList<String>();
			for(Object obj : orderAreaList){
				oldOrderAreaList.add(obj.toString());
			}
			if(oldOrderAreaList.contains("") || oldOrderAreaList.contains("0") || oldOrderAreaList.contains("152000000000")){
				//原策略区域包括河南
				return true;
			}else{
				sql = "select p.area_id from t_ploy p where p.ploy_id=?";
				List<?> areaList = this.getDataBySql(sql, new Object[]{ployId});
				for(Object areaCode : areaList){
					if(oldOrderAreaList.contains(areaCode.toString())){
						return true;
					}
					if(areaCode.toString().equals("") || areaCode.toString().equals("0") || areaCode.toString().equals("152000000000")){
						//策略区域包括河南
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 根据区域、时段、频道组判断是否有冲突的订单
	 * @param positionId
	 * @param orderCode
	 * @return
	 */
	public boolean exsiteOrder(String start, String end,Integer positionId,String orderCode){
		StringBuffer sql = new StringBuffer();
		sql.append(" select om.order_code from");
		sql.append(" (select o.ORDER_CODE,rel.area_code,rel.start_time,rel.end_time,IFNULL(rel.channel_group_id,1) channel_group_id ");
		sql.append(" from t_order_mate_rel rel,t_order o where rel.ORDER_ID = o.ID ");
		sql.append(" and ((o.start_time>=str_to_date(?,'%Y-%m-%d') ");
		if(StringUtils.isNotBlank(end)){
			sql.append(" and o.start_time<=str_to_date('"+end+"','%Y-%m-%d')");
		}
		sql.append(" ) or (o.start_time<=str_to_date(?,'%Y-%m-%d') and o.end_time>=str_to_date(?,'%Y-%m-%d')))");
		sql.append(" and o.position_id=? ) om");
		
		sql.append(" , (select ORDER_CODE,area_code,start_time,end_time,IFNULL(channel_group_id,1) channel_group_id ");
		sql.append(" from t_order_mate_rel_tmp where mate_id is not null and order_code=?) tmp");
		sql.append(" where om.order_code !=tmp.order_code");
		//17回放菜单广告，不分地市，分频道组和时间段
		if(positionId!=17){
			sql.append(" and om.area_code = tmp.area_code");
		}
		
		
		sql.append(" AND ((om.start_time = tmp.start_time )");
		sql.append(" or (om.start_time > tmp.start_time and om.start_time < tmp.end_time)");
		sql.append(" or (om.start_time < tmp.start_time and om.end_time > tmp.start_time))");
		//channel_group_id为0(无频道组)，表示所有频道组
		sql.append(" AND ((om.channel_group_id = tmp.channel_group_id) or (om.channel_group_id =0))");
		
	
		List<?> list = this.getDataBySql(sql.toString(), new Object[]{start, start, start,positionId, orderCode});
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据订单ID获取订单信息
	 * @param id
	 * @return
	 */
	public Order getOrderById(Integer id){
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct o.id, o.order_code,o.contract_id,o.position_id,p.position_name,o.ploy_id,pl.ploy_name,");
		sql.append(" o.start_time,o.end_time,o.state,o.description,o.order_type,o.create_time,o.play_number,o.played_number,");
		sql.append(" o.user_number,o.questionnaire_number,o.integral_ratio,o.threshold_number");
		sql.append(" from t_order o,t_advertposition p,t_ploy pl");
		sql.append(" where pl.ploy_id=o.ploy_id and p.id=o.position_id and o.id=? ");
		sql.append("  ");
		List list = this.getDataBySql(sql.toString(), new Object[]{id});
		if(list != null && list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			Order order = new Order();
			order.setId(toInteger(obj[0]));
			order.setOrderCode((String)obj[1]);
			order.setContractId(toInteger(obj[2]));
			order.setPositionId(toInteger(obj[3]));
			order.setPositionName((String)obj[4]);
			order.setPloyId(toInteger(obj[5]));
			order.setPloyName((String)obj[6]);
			order.setStartTime((Date)obj[7]);
			order.setEndTime((Date)obj[8]);
			order.setState(((Character)obj[9]).toString());
			order.setDescription((String)obj[10]);
			order.setOrderType(toInteger(obj[11]));
			order.setCreateTime((Date)obj[12]);
			order.setPlayNumber(toInteger(obj[13]));
			order.setPlayedNumber(toInteger(obj[14]));
			order.setUserNumber(toInteger(obj[15]));
			order.setQuestionnaireNumber(toInteger(obj[16]));
			order.setIntegralRatio((String)obj[17]);
			order.setThresholdNumber(toInteger(obj[18]));
			if(order.getContractId() == null || order.getContractId().intValue() == 0){
				order.setContractName("无合同");
			}else{
				setOrderContract(order);
			}
			
			List<Ploy> ployList = getPloyByPloyId(order.getPloyId());
			if(ployList != null && ployList.size()>0){
				Ploy p = ployList.get(0);
				if(StringUtils.isEmpty(p.getStartTime()) || "0".equals(p.getStartTime())){
					order.setPloyStartTime("00:00:00");
					order.setPloyEndTime("23:59:59");
				}else{
					order.setPloyStartTime(p.getStartTime());
					order.setPloyEndTime(p.getEndTime());
				}
			}
			return order;
		}else{
			return null;
		}
	}
	
//	private Ploy getPloyByPloyId(Integer ployId){
//		String sql = "select pl.start_time,pl.end_time from t_ploy pl where pl.ploy_id = ? order by pl.start_time";
//		List list = this.getDataBySql(sql, new Object[]{ployId});
//		if(list != null && list.size()>0){
//			Object[] obj = (Object[]) list.get(0);
//			Ploy p = new Ploy();
//			p.setStartTime((String)obj[0]);
//			p.setEndTime((String)obj[1]);
//			return p;
//		}else{
//			return null;
//		}
//	}
	
	/**
	 * 根据策略ID查询策略列表
	 * @param ployId
	 * @return
	 */
	public List<Ploy> getPloyByPloyId(Integer ployId){
		String hql = "from Ploy where ployId=? order by startTime";
		return this.list(hql, ployId);
	}
	
	private void setOrderContract(Order order){
		StringBuffer sql = new StringBuffer("select c.contract_name,");
		sql.append(" str_to_date(date_format(ca.valid_start,'%Y-%m-%d'),'%Y-%m-%d') as valid_start,");
		sql.append(" str_to_date(date_format(ca.valid_end,'%Y-%m-%d'),'%Y-%m-%d') as valid_end");
		sql.append(" from t_contract c, t_contract_ad ca, t_position_package pp, t_advertposition p");
		sql.append(" where c.id=ca.contract_id and pp.id=ca.ad_id and pp.id=p.position_package_id and ca.contract_id=? and p.id=? ");
		List list = this.getDataBySql(sql.toString(), new Object[]{order.getContractId(),order.getPositionId()});
		if(list != null && list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			order.setContractName((String)obj[0]);
			order.setValidStart((Date)obj[1]);
			order.setValidEnd((Date)obj[2]);
		}
	}
	
	/**
	 * 根据订单ID获取订单与素材关系列表
	 * @param id
	 * @return
	 */
	public List<OrderMaterialRelation> getOrderMaterialRelList(OrderMaterialRelation rel){
		StringBuffer hql = new StringBuffer();
		hql.append("select new OrderMaterialRelation(");
		hql.append(" rel.id, rel.orderId, rel.mateId,r.resourceName, rel.playLocation, rel.isHD,rel.pollIndex, rel.preciseId,rel.type)");
		hql.append(" ,rel.startTime,rel.endTime,rel.areaId,rel.channelGroupId");
		hql.append(" from OrderMaterialRelation rel,ResourceReal r");
		hql.append(" where rel.mateId=r.id");
		if(rel != null){
			if(rel.getOrderId() != null && rel.getOrderId() != 0){
				hql.append(" and rel.orderId=").append(rel.getOrderId());
			}
			if(rel.getPreciseId() != null && rel.getPreciseId() != 0){
				hql.append(" and rel.preciseId=").append(rel.getPreciseId());
			}
		}
		hql.append(" order by rel.preciseId,rel.type,rel.pollIndex,rel.playLocation ");
		return this.list(hql.toString(), null);
	}
	
	/**
	 * 获取开机订单与素材关系列表
	 * @param rel
	 * @return
	 */
	public List<OrderMaterialRelation> getBootOrderMaterialRelList(OrderMaterialRelation rel){
		StringBuffer sql = new StringBuffer();
		sql.append("select rel.id, rel.order_id, rel.mate_id,r.resource_name, rel.play_location, rel.is_hd,rel.poll_index");
		sql.append(" , rel.precise_id , rel.type, rel.start_time, rel.end_time, rel.area_code, rel.channel_group_id");
		sql.append(" from t_order_mate_rel rel,t_resource r");
		sql.append(" where rel.mate_id = r.id");
		if(rel != null){
			if(rel.getOrderId() != null && rel.getOrderId() != 0){
				sql.append(" and rel.order_id=").append(rel.getOrderId());
			}
			if(rel.getPreciseId() != null && rel.getPreciseId() != 0){
				sql.append(" and rel.precise_id=").append(rel.getPreciseId());
			}
		}
		sql.append(" order by rel.precise_id,rel.type,cast(rel.play_location as unsigned int)");
		
		List<OrderMaterialRelation> list = getBootOrderMaterialRelList(this.getDataBySql(sql.toString(), null));
		return list;
	}
	
	private List<OrderMaterialRelation> getBootOrderMaterialRelList(List<?> resultList) {
		List<OrderMaterialRelation> list = new ArrayList<OrderMaterialRelation>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelation rel = new OrderMaterialRelation();
			rel.setId(toInteger(obj[0]));
			rel.setOrderId(toInteger(obj[1]));
			rel.setMateId(toInteger(obj[2]));
			rel.setMateName((String)obj[3]);
			rel.setPlayLocation((String)obj[4]);
			rel.setIsHD(toInteger(obj[5]));
			rel.setPollIndex(toInteger(obj[6]));
			rel.setPreciseId(toInteger(obj[7]));
			rel.setType(toInteger(obj[8]));
			rel.setStartTime((String)obj[9]);
			rel.setEndTime((String)obj[10]);
			rel.setAreaCode((String)obj[11]);
			rel.setChannelGroupId(toInteger(obj[12]));
			list.add(rel);
		}
		return list;
	}
	
	/**
	 * 根据订单号删除订单素材关系
	 * @param orderId
	 */
	public void delOrderMaterialRelation(Integer orderId){
		String hql = "delete from OrderMaterialRelation where orderId = ?";
		this.executeByHQL(hql, new Object[]{orderId});
	}
	
	/**
	 * 根据订单ID查询订单列表信息
	 * @param ids
	 * @return
	 */
	public List<Order> findOrderListByIds(String ids){
		
		String sql = "select distinct o.id,o.state,date_format(o.start_time,'%Y-%m-%d') ,p.ploy_id from "
			+ "t_order o,t_ploy p where o.ploy_id=p.ploy_id and o.id in ("+ids+")";
		List list = this.getDataBySql(sql, null);
		
		
		List<Order> orderList = new ArrayList<Order>();
		for (int i=0; i<list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			Order order = new Order();
			order.setId(toInteger(obj[0]));
			order.setState(((Character)obj[1]).toString());
			order.setStartDateStr((String)obj[2]);
			Ploy p = getPloyByPloyId(toInteger(obj[3])).get(0);
			order.setPloyStartTime(p.getStartTime());
			orderList.add(order);
		}
		return orderList;
	}
	
	/**
	 * 根据订单id删除订单关系记录
	 * @param ids
	 */
	public void deleteRelationByIds(String ids){
		String sql = "delete from t_order_mate_rel where order_id in ("+ids+")";
		this.executeBySQL(sql, null);
	}
	
	/**
	 * 根据订单id删除订单
	 * @param ids
	 */
	public void deleteOrderByIds(String ids){
		String hql = "delete from Order where id in ("+ids+")";
		this.executeByHQL(hql, null);
	}
	
	/**
	 * 根据订单id修改订单状态
	 * @param orderId
	 * @param orderState
	 * @param userId
	 */
	public void updateOrderState(Integer orderId, String orderState,Integer userId){
		String sql = "update t_order set state=?";
		if(userId != null){
			sql += ",operator_id="+userId.intValue();
		}
		sql += " where id="+orderId.intValue();
		this.executeBySQL(sql, new Object[]{orderState});
	}
	
	/**
	 * @description: 首页代办获取待审批的订单的总数
	 * @return 待审批的订单的总数
	 */
	public int getWaitingAuditOrderCount(String ids){
		StringBuffer sql =  new StringBuffer();
		sql.append("select distinct o.id,o.order_code,o.contract_id,o.position_id,o.ploy_id,o.order_type,o.start_time,o.end_time,");
		sql.append(" o.create_time,o.modify_time,o.state,c.contract_name,ad.position_name,pl.ploy_name,cust.advertisers_name");	
		sql.append(" from t_order o,t_advertposition ad,t_ploy pl,t_contract c, t_customer cust");
		sql.append(" where pl.ploy_id=o.ploy_id and ad.id=o.position_id and o.contract_id=c.id and c.custom_id = cust.id");
		sql.append(" and o.state in(");
		sql.append(Constant.ORDER_PENDING_CHECK).append(",").append(Constant.ORDER_PENDING_CHECK_UPDATE);
		sql.append(",").append(Constant.ORDER_PENDING_CHECK_DELETE).append(") ");
		sql.append(" and ad.id in (").append(ids).append(")");
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		return rowcount;
	}
	
	/**
	 * 根据广告位类型获取图片素材文件总大小
	 * @param positionType 广告位类型
	 * @param order  订单对象
	 * @param mateIds  素材IDS
	 * @return
	 */
	public long getImageFileSumSize(int positionType,Order order,String mateIds){
		StringBuffer sql = new StringBuffer();
//		sql.append("select ifnull(sum(im.file_size),0)");
		sql.append("select distinct r.id,im.file_size*1.0");
		sql.append(" from t_order_mate_rel rel, t_resource r, t_image_meta im");
		sql.append(" where rel.mate_id = r.id and r.resource_id = im.id and r.resource_type = ").append(Constant.IMAGE);
		sql.append(" and rel.order_id in ( ");
		sql.append(" select o.id from t_order o,t_advertposition ad, t_position_package pp, t_ploy p");
		sql.append(" where o.position_id = ad.id and ad.position_package_id = pp.id and o.ploy_id = p.ploy_id");
		sql.append(" and ( ( o.start_time<=str_to_date('").append(DateUtil.formatDate(order.getStartTime())).append("', '%Y-%m-%d')")
		.append(" and o.end_time > str_to_date('").append(DateUtil.formatDate(order.getStartTime())).append("', '%Y-%m-%d') ) ");
		sql.append(" or ( o.start_time>=str_to_date('").append(DateUtil.formatDate(order.getStartTime())).append("', '%Y-%m-%d')")
		.append(" and o.start_time < str_to_date('").append(DateUtil.formatDate(order.getEndTime())).append("', '%Y-%m-%d') ) ) ");
		sql.append(" and ( ( IF(LENGTH(p.start_time)=1,'00:00:00',p.start_time)<='").append(order.getPloyStartTime()).append("' and IF(LENGTH(p.end_time)=1,'00:00:00',p.end_time) > '")
		.append(order.getPloyStartTime()).append("' ) ");
		sql.append(" or ( IF(LENGTH(p.start_time)=1,'00:00:00',p.start_time)>='").append(order.getPloyStartTime()).append("' and IF(LENGTH(p.start_time)=1,'00:00:00',p.start_time) < '")
//				sql.append(" and ( ( p.start_time<='").append(order.getPloyStartTime()).append("' and p.end_time > '")
//		.append(order.getPloyStartTime()).append("' ) ");
//		sql.append(" or ( p.start_time>='").append(order.getPloyStartTime()).append("' and p.start_time < '")
		.append(order.getPloyEndTime()).append("' ) ) ");
		sql.append(" and o.state in (").append(Constant.ORDER_PENDING_CHECK_UPDATE).append(",");
		sql.append(Constant.ORDER_PENDING_CHECK_DELETE).append(",");
		sql.append(Constant.ORDER_CHECK_NOT_PASS_UPDATE).append(",");
		sql.append(Constant.ORDER_CHECK_NOT_PASS_DELETE).append(",");
		sql.append(Constant.ORDER_PUBLISHED).append(")");
		sql.append(" and pp.position_package_type = ").append(positionType).append(" )");
		
		if(StringUtils.isNotBlank(mateIds)){
			sql.append(" and r.id not in ( ").append(mateIds).append(" )");
		}
		
		List list = this.getDataBySql(sql.toString(),null);
		long fileSize = 0;
		if(list != null && list.size()>0){
			for (int i=0; i<list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				fileSize += toLong(obj[1]);
			}
		}
		
		return fileSize;
	}
	
	/**
	 * 根据广告位类型获取视频素材文件总大小
	 * @param positionType 广告位类型
	 * @param order  订单对象
	 * @return
	 */
	public long getVideoFileSumSize(int positionType,Order order){
		StringBuffer sql = new StringBuffer();
//		sql.append("select ifnull(sum(vm.file_size),0)");
		sql.append("select distinct r.id,vm.file_size");
		sql.append(" from t_order_mate_rel rel, t_resource r, t_video_meta vm");
		sql.append(" where rel.mate_id = r.id and r.resource_id = vm.id and r.resource_type = ").append(Constant.VIDEO);
		sql.append(" and rel.order_id in ( ");
		sql.append(" select o.id from t_order o,t_advertposition ad, t_position_package pp, t_ploy p");
		sql.append(" where o.position_id = ad.id and ad.position_package_id = pp.id and o.ploy_id = p.ploy_id");
		sql.append(" and ( ( o.start_time<=str_to_date('").append(order.getStartTime().toString()).append("', '%Y-%m-%d')")
		.append(" and o.end_time > str_to_date('").append(order.getStartTime().toString()).append("', '%Y-%m-%d') ) ");
		sql.append(" or ( o.start_time>=str_to_date('").append(order.getStartTime()).append("', '%Y-%m-%d')")
		.append(" and o.start_time < str_to_date('").append(order.getEndTime()).append("', '%Y-%m-%d') ) ) ");
		sql.append(" and ( ( p.start_time<='").append(order.getPloyStartTime()).append("' and p.end_time > '")
		.append(order.getPloyStartTime()).append("' ) ");
		sql.append(" or ( p.start_time>='").append(order.getPloyStartTime()).append("' and p.start_time < '")
		.append(order.getPloyEndTime()).append("' ) ) ");
		sql.append(" and o.state in (").append(Constant.ORDER_PENDING_CHECK_UPDATE).append(",");
		sql.append(Constant.ORDER_PENDING_CHECK_DELETE).append(",");
		sql.append(Constant.ORDER_CHECK_NOT_PASS_UPDATE).append(",");
		sql.append(Constant.ORDER_CHECK_NOT_PASS_DELETE).append(",");
		sql.append(Constant.ORDER_PUBLISHED).append(")");
		sql.append(" and pp.position_package_type = ").append(positionType).append(" )");
		
		List list = this.getDataBySql(sql.toString(),null);
		long fileSize = 0;
		if(list != null && list.size()>0){
			for (int i=0; i<list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				fileSize += toLong(obj[1]);
			}
		}
		
		return fileSize;
	}
	
	/**
	 * 根据订单ID获取DTV发送FTP需要的信息
	 * @param orderId
	 * @return
	 */
	public List<DtvFtpInfo> getDtvFtpInfoList(Integer orderId){
		StringBuffer sql = new StringBuffer();
		//sql.append("select rel.type,rel.precise_id,pm.user_area,CONCAT(vm.formal_file_path,'/',vm.name) as filePath ");
		//sql.append(" from t_order_mate_rel rel left join t_precise_match pm on rel.precise_id = pm.id,t_resource r,t_video_meta vm");
		//sql.append(" where rel.mate_id = r.id and r.resource_id = vm.id ");
		//sql.append(" and r.resource_type = 1 and rel.order_id = ? ");
		sql.append("select rel.type,rel.precise_id,CONCAT(vm.formal_file_path,'/',vm.name) as filePath,pm.START_TIME,pm.END_TIME,date_format(o.START_TIME,'%Y%m%d') startdate,date_format(o.END_TIME,'%Y%m%d') enddate");
		sql.append(" from t_order_mate_rel rel ,t_ploy pm ,t_resource r,t_video_meta vm,t_order o");
		sql.append(" where rel.mate_id = r.id and r.resource_id = vm.id and rel.ORDER_ID=o.ID and rel.PRECISE_ID = pm.id");
		sql.append(" and r.resource_type = 1 and rel.order_id = ? ");
		
		List list = this.getDataBySql(sql.toString(),new Object[]{orderId});
		List<DtvFtpInfo> ftpList = new ArrayList<DtvFtpInfo>();
		for (int i=0; i<list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			DtvFtpInfo ftp = new DtvFtpInfo();
			ftp.setType(toInteger(obj[0]));
			ftp.setPreciseId(toInteger(obj[1]));
			//ftp.setAreaIds((String)obj[2]);
			ftp.setFilePath((String)obj[2]);
			
			ftp.setStarttime((String)obj[3]);
			ftp.setEndtime((String)obj[4]);
			
			ftp.setStartdate((String)obj[5]);
			ftp.setEnddate((String)obj[6]);
			ftpList.add(ftp);
		}
		return ftpList;
	}
	
	/**
	 * 获取区域列表
	 * @return
	 */
	public List<ReleaseArea> getAreaList(){
		String hql = "from ReleaseArea";
		return this.list(hql, null);
	}
	
	/**
	 * 根据订单ID获取待恢复订单列表
	 * @param ids
	 * @return
	 */
	public List<Order> findRestoreOrderByIds(String ids){
		String hql = "from Order where state in (?,?) and id in ("+ids+")";
		return this.list(hql, 
				new Object[]{Constant.ORDER_PENDING_CHECK_UPDATE, Constant.ORDER_CHECK_NOT_PASS_UPDATE});
	}
	
	/**
	 * 根据订单id集合修改订单状态
	 * 
	 * @param ids
	 *            订单id集合
	 * @param orderState
	 *            订单状态
	 * */
	public void updateOrdersState(List<Integer> ids, String orderState){
		StringBuffer sql = new StringBuffer("update t_order set state=? where id in(");
		for(int i=0;i<ids.size();i++){
			if(i>0){
				sql.append(",");
			}
			sql.append(ids.get(i));
		}
		sql.append(")");
		this.executeBySQL(sql.toString(), new Object[]{orderState});
	}
	
	/**
	 * 根据素材ID获取素材信息
	 * @param id
	 * @return
	 */
	public MaterialBean getMaterialById(Integer id){
		String sql = "select resource_id,resource_type from t_resource where id=?  ";
		List list = this.getDataBySql(sql,new Object[]{id});
		if(list != null && list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			MaterialBean m = new MaterialBean();
			int resourceId = toInteger(obj[0]);
			m.setType(toInteger(obj[1]));
			setMaterial(resourceId,m);
			return m;
		}
		return null;
	}
	public List<AreaResource> getSelectMaterialJsonByOrderId(String id){
		//String sql = "select t.MATE_ID,t.start_time,t.end_time,t.channel_group_id from t_order_mate_rel_tmp t where t.MATE_ID is not null and t.ORDER_CODE=? order by t.MATE_ID ";
		String sql = "select t.MATE_ID,t.start_time,t.end_time,t.channel_group_id,(select r.RESOURCE_NAME from t_resource r where r.ID=t.mate_id) resourceName, t.POLL_INDEX from t_order_mate_rel_tmp t where t.MATE_ID is not null and t.ORDER_CODE=? order by t.MATE_ID ";
		List list = this.getDataBySql(sql,new Object[]{id.trim()});
		List<AreaResource> areaResourceList=new ArrayList<AreaResource>();
		if(list != null && list.size()>0){
			Object[] obj = null;
			Integer preResourceId=0;
			AreaResource m = null;
			for (int i=0; i<list.size(); i++) {
				obj = (Object[]) list.get(i);
				if(preResourceId.intValue() != toInteger(obj[0]).intValue()){
					m = new AreaResource();
					m.setResourceId(toInteger(obj[0]));
					m.setStartTime((String)obj[1]);
					m.setEndTime((String)obj[2]);
					m.setChannelGroupId(toInteger(obj[3]));
					m.setResourceName((String)obj[4]);
					m.setPollIndex(toInteger(obj[5]));
					areaResourceList.add(m);
				}
				preResourceId = toInteger(obj[0]);
			}
		}
		if(areaResourceList.size()>0){
			return areaResourceList;
		}else{
			return null;	
		}
		
	}
	
	/**
	 * 设置素材路径或文字属性
	 * @param resourceId
	 * @param m
	 */
	private void setMaterial(Integer resourceId,MaterialBean m){
		String sql = "";
		ConfigureProperties config = ConfigureProperties.getInstance();
		String ip=config.getValueByKey("ftp.ip");
        String path="";
		if(m.getType() == Constant.IMAGE){//图片素材
			sql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_image_meta m where m.id=?";
			List list = this.getDataBySql(sql,new Object[]{resourceId});
			if(list != null && list.size()>0){
				path = (String)list.get(0);
				path="http://"+ip+path.substring(5, path.length());
				m.setPath(path);
			}
		}else if(m.getType() == Constant.VIDEO){//视频素材
			sql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_video_meta m where m.id=? ";
			List list = this.getDataBySql(sql,new Object[]{resourceId});
			if(list != null && list.size()>0){
				path = (String)list.get(0);
				path="http://"+ip+path.substring(5, path.length());
				m.setPath(path);
			}
		}else if(m.getType() == Constant.WRITING){//文字素材
			sql = "select m.id,m.name,m.content,m.url,m.action,m.duration_time,m.font_size,m.font_color,"
				+"m.background_color,m.roll_speed,m.position_vertex_coordinates,m.position_width_height "
				+"from t_text_meta m where m.id=?";
			List list = this.getDataBySql(sql,new Object[]{resourceId});
			if(list != null && list.size()>0){
				Object[] obj = (Object[]) list.get(0);
				TextMate text = new TextMate();
				text.setId(toInteger(obj[0]));
				text.setName((String)obj[1]);
				text.setContent((Blob)obj[2]);
				text.setURL((String)obj[3]);
				text.setAction((String)obj[4]);
				text.setDurationTime(toInteger(obj[5]));
				text.setFontSize(toInteger(obj[6]));
				text.setFontColor((String)obj[7]);
				text.setBkgColor((String)obj[8]);
				text.setRollSpeed(toInteger(obj[9]));
				text.setPositionVertexCoordinates((String)obj[10]);
				text.setPositionWidthHeight((String)obj[11]);
				m.setText(text);
			}
		}else if(m.getType() == Constant.ZIP){//ZIP素材
			//sql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_image_meta m where m.id=?";
			sql = "select m.formal_file_path,m.name from t_image_meta m where m.id=?";
			List list = this.getDataBySql(sql,new Object[]{resourceId});
			if(list != null && list.size()>0){
				Object[] obj = (Object[])list.get(0);
				String nameString = obj[1].toString();
				String tempName = "";
				if(nameString!="" && nameString != null){
					tempName = nameString.substring(0,nameString.lastIndexOf("."));
				}
				path = (String)obj[0]+"/"+tempName+".jpg";
				path="http://"+ip+path.substring(5, path.length());
				m.setPath(path);
			}
		}
	}
	
	/**
	 * 根据策略IDS  策略列表
	 * @param ids
	 * @return
	 */
	public List<Ploy> getPloyListByIds(String ids){
		String hql = "from Ploy where id in ("+ids+") order by startTime,priority desc";
		return this.list(hql);
	}
	
	/**
	 * 根据频道组ID获取频道serviceId列表
	 * @param groupId
	 * @param channel
	 * @return
	 */
	public List<String> getServiceIdList(Integer groupId,ChannelInfo channel){
		StringBuffer sql = new StringBuffer();
		if(groupId != null && groupId.intValue() == 0){
			//组ID位0，取所有频道
			sql.append("select c.service_id from t_channelinfo c where 1=1 ");
		}else{
			sql.append("select c.service_id from t_channel_group_ref rel, t_channelinfo c ");
			sql.append(" where rel.channel_id=c.channel_id");
			sql.append(" and rel.group_id = ").append(groupId);
		}
		if(channel != null){
			if(StringUtils.isNotBlank(channel.getChannelType())){
				sql.append(" and c.channel_type='").append(channel.getChannelType()).append("'");
			}
		}
		return this.getDataBySql(sql.toString(),null);
	}
	
	/**
	 * 根据广告位和位移天数查找空档订单日期
	 * @param positionIds
	 * @param shiftDate
	 * @return
	 */
	public List<Order> getFreePositionRemindOrders(String positionIds,  Date today, Date  shiftDate){
		Session session = this.getSession();
		String hql = "from Order o where o.startTime <= :shiftdate and o.endTime >= :today and o.positionId in ("+positionIds+") order by o.positionId, o.startTime asc"; 
		Query query = session.createQuery(hql);
		query.setDate("shiftdate", shiftDate);
		query.setDate("today", today);
		List<Order> list = query.list();
		return list;
	}
	
	/**
	 * 根据广告位和位移天数查找空档订单日期
	 * @param positionIds
	 * @param shiftDate
	 * @return
	 */
	public List<Object[]> getCustomerPositions(Integer customerId, Date today, Date  shiftDate){
		List<Object[]>  list = null;
		Session session = this.getSession();
		String sql = "select ca.ad_id, ca.VALID_START, ca.VALID_END, ca.contract_id from t_contract c, t_contract_ad ca where c.custom_id = :customer and c.id=ca.contract_id and ca.VALID_START <= :shiftdate and ca.VALID_END >= :today";
		Query query = session.createSQLQuery(sql);
		query.setInteger("customer", customerId);
		query.setDate("shiftdate", shiftDate);
		query.setDate("today", today);
		if(query.list() != null && query.list().size() > 0){
			list = (List<Object[]>) query.list();
		}
		return list;
	}

	@Override
	public List<Order> getCustomerFreePositionRemindOrders(String positionIds,
			Date today, Date shiftDate, String contractIds) {
		Session session = this.getSession();
		String hql = "from Order o where o.startTime <= :shiftdate and o.endTime >= :today and o.contractId in ("+contractIds+") and o.positionId in ("+positionIds+") order by o.positionId, o.startTime asc"; 
		Query query = session.createQuery(hql);
		query.setDate("shiftdate", shiftDate);
		query.setDate("today", today);
		List<Order> list = query.list();
		return list;
	}
	
	/**
	 * 获取问卷订单已请求的记录数
	 * @param orderId
	 * @return
	 */
	public int getQuestionnaireCount(Integer orderId){
		String sql = " select count(1) from t_user_questionnaire uq where uq.order_id="+orderId.intValue();
		
		List list = this.getDataBySql(sql, null);
		if(list != null && list.size()>0){
			return toInteger(list.get(0));
		}else{
			return 0;
		}
	}
	
	/**
	 * 保存问卷订单在代办中已阅记录
	 * @param orderId
	 */
	public void saveRealQuestionnaire(Integer orderId){
		String sql = "insert into t_order_real(order_id,operator_id,create_time) values (?,?,now())";
		HttpSession session = ServletActionContext.getRequest().getSession();
		UserLogin user = (UserLogin)session.getAttribute("USER_LOGIN_INFO");
		this.executeBySQL(sql.toString(), new Object[]{orderId,user.getUserId()});
	}
	
	/**
	 * 根据策略ID获取频道组列表
	 * @param ployId
	 * @return
	 */
	public List<TChannelGroup> getChannelGroupListByPloyId(Integer ployId){
		String sql = " select distinct cg.ID,cg.CODE,cg.NAME from t_channel_group cg, t_ploy p where cg.ID=p.CHANNEL_GROUP_ID and p.PLOY_ID="+ployId;
		List<TChannelGroup> groupList = getGroupList(this.getDataBySql(sql, null));
		return groupList;
	}
	public List<TChannelGroup> getNPVRChannelGroupListByPloyId(Integer ployId){
		String sql = " select distinct cg.ID,cg.CODE,cg.NAME from t_channel_group_npvr cg, t_ploy p where cg.ID=p.CHANNEL_GROUP_ID and p.PLOY_ID="+ployId;
		List<TChannelGroup> groupList = getGroupList(this.getDataBySql(sql, null));
		return groupList;
	}
	private List<TChannelGroup> getGroupList(List<?> resultList) {
		List<TChannelGroup> list = new ArrayList<TChannelGroup>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			TChannelGroup group = new TChannelGroup();
			group.setId(toLong(obj[0]));
			group.setCode((String)obj[1]);
			group.setName((String)obj[2]);
			list.add(group);
		}
		return list;
	}
	
	/**
	 * 查询订单可选素材列表信息
	 * @param areaResource
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryNPVRAreaResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize){
		//select p.START_TIME,p.END_TIME,p.CHANNEL_GROUP_ID,ra.AREA_CODE,ra.AREA_NAME from t_ploy p,t_release_area ra where p.PLOY_NAME='sssss';
		StringBuffer sql =  new StringBuffer();
		sql.append("select tmp.id,tmp.MATE_ID,tmp.START_TIME,tmp.END_TIME,tmp.CHANNEL_GROUP_ID,tmp.area_code,");
		sql.append(" (select cg.NAME from t_channel_group_npvr cg where cg.ID=tmp.channel_group_id) groupName,");
		sql.append(" (select ca.AREA_NAME from t_release_area ca where ca.AREA_CODE=tmp.area_code) areaName,");
		//sql.append(" (select ca.AREA_NAME from t_release_area ca) areaName,");
		sql.append(" (select r.RESOURCE_NAME from t_resource r where r.ID=tmp.mate_id) resourceName,");
		sql.append(" tmp.play_location,tmp.is_hd,tmp.poll_index");
		sql.append(" from t_order_mate_rel_tmp tmp where 1=1 ");
		
		if(omRelTmp != null){
			if(StringUtils.isNotBlank(omRelTmp.getOrderCode())){
				sql.append(" and tmp.ORDER_CODE = '" + omRelTmp.getOrderCode() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getStartTime())) {
				sql.append(" and tmp.START_TIME >= '" + omRelTmp.getStartTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getEndTime())) {
				sql.append(" and tmp.END_TIME <= '" + omRelTmp.getEndTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getAreaCode())) {
				sql.append(" and tmp.AREA_CODE = '" + omRelTmp.getAreaCode() + "' ");
			}
			if (omRelTmp.getChannelGroupId() != null) {
				sql.append(" and tmp.CHANNEL_GROUP_ID = " + omRelTmp.getChannelGroupId() + " ");
			}
			if(omRelTmp.getMateId()!=null){
				sql.append(" and tmp.MATE_ID = " + omRelTmp.getMateId() );
			}
			
			if(omRelTmp.isNotNull()){
					sql.append(" and tmp.MATE_ID is  not null " );
			}else{
				if(!"1".equals(omRelTmp.getContain())){
					sql.append(" and tmp.MATE_ID is null " );
				}
			}
		}
		//sql.append(" order by tmp.area_code,tmp.START_TIME,tmp.poll_index ");
		sql.append(" order by tmp.START_TIME,tmp.poll_index ");
		
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<OrderMaterialRelationTmp> list = getNPVRAreaResourceList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	private List<OrderMaterialRelationTmp> getNPVRAreaResourceList(List<?> resultList) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
	
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelationTmp ar = new OrderMaterialRelationTmp();
			ar.setId(toInteger(toInteger(obj[0])));
			ar.setMateId(toInteger(obj[1]));
			ar.setStartTime((String)(obj[2]));
			ar.setEndTime((String)(obj[3]));
			ar.setChannelGroupId(toInteger(obj[4]));
			ar.setAreaCode((String)(obj[5]));
			ar.setChannelGroupName((String)(obj[6]));
			ar.setAreaName((String)(obj[7]));
			ar.setMateName((String)(obj[8]));
			ar.setPlayLocation((String)(obj[9]));
			ar.setIsHD(toInteger(obj[10]));
			ar.setPollIndex(toInteger(obj[11]));
		    list.add(ar);
		}
		return list;
	}
	public PageBeanDB queryTheAreaResourceList(OrderMaterialRelationTmp omRelTmp, Integer ployId, int pageNo,int pageSize){
		
		StringBuffer sql =  new StringBuffer();
		sql.append("select tmp.id,tmp.MATE_ID,tmp.START_TIME,tmp.END_TIME,tmp.CHANNEL_GROUP_ID,tmp.area_code,");
		sql.append(" (select cg.NAME from t_channel_group cg where cg.ID=tmp.channel_group_id) groupName,");
		sql.append(" (select ca.AREA_NAME from t_release_area ca where ca.AREA_CODE=tmp.area_code) areaName,");
		sql.append(" (select r.RESOURCE_NAME from t_resource r where r.ID=tmp.mate_id) resourceName,");
		sql.append(" tmp.play_location,tmp.is_hd,tmp.poll_index");
		sql.append(" from t_order_mate_rel_tmp tmp where 1=1 ");
		
		if(omRelTmp != null){
			if(StringUtils.isNotBlank(omRelTmp.getOrderCode())){
				sql.append(" and tmp.ORDER_CODE = '" + omRelTmp.getOrderCode() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getStartTime())) {
				sql.append(" and tmp.START_TIME >= '" + omRelTmp.getStartTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getEndTime())) {
				sql.append(" and tmp.END_TIME <= '" + omRelTmp.getEndTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getAreaCode())) {
				sql.append(" and tmp.AREA_CODE = '" + omRelTmp.getAreaCode() + "' ");
			}
			
			if (omRelTmp.getChannelGroupId() != null) {
				sql.append(" and tmp.CHANNEL_GROUP_ID = " + omRelTmp.getChannelGroupId() + " ");
			}
			if(omRelTmp.getMateId()!=null){
				sql.append(" and tmp.MATE_ID = " + omRelTmp.getMateId() );
			}
			
			if(omRelTmp.isNotNull()){
					sql.append(" and tmp.MATE_ID is  not null " );
			}else{
				if(!"1".equals(omRelTmp.getContain())){
					sql.append(" and tmp.MATE_ID is null " );
				}
			}
		}
		sql.append(" order by tmp.area_code,tmp.START_TIME,tmp.poll_index ");
		
		List<OrderMaterialRelationTmp> list = getTheAreaResourceList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		//rowcount=list.size();
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		page.setDataList(list);
		return page;
		
	}	
	
	@Override
	public PageBeanDB queryBootPicResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize) {
		StringBuffer sql =  new StringBuffer();
		sql.append("SELECT DISTINCT  tmp.area_code, ra.AREA_NAME,tmp.mate_Id FROM t_order_mate_rel_tmp tmp, t_release_area ra  ");
		sql.append("WHERE tmp.area_code = ra.AREA_CODE" );
		sql.append(" and tmp.AREA_CODE<>'152000000000'");
	
		if(omRelTmp != null){
			if(StringUtils.isNotBlank(omRelTmp.getOrderCode())){
				sql.append(" and tmp.ORDER_CODE = '" + omRelTmp.getOrderCode() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getStartTime())) {
				sql.append(" and tmp.START_TIME >= '" + omRelTmp.getStartTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getEndTime())) {
				sql.append(" and tmp.END_TIME <= '" + omRelTmp.getEndTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getAreaCode())) {
				sql.append(" and tmp.AREA_CODE = '" + omRelTmp.getAreaCode() + "' ");
			}
			
			if (omRelTmp.getChannelGroupId() != null) {
				sql.append(" and tmp.CHANNEL_GROUP_ID = " + omRelTmp.getChannelGroupId() + " ");
			}
			if(omRelTmp.getMateId()!=null){
				sql.append(" and tmp.MATE_ID = " + omRelTmp.getMateId() );
			}
			
			if(omRelTmp.isNotNull()){
					sql.append(" and tmp.MATE_ID is  not null " );
			}else{
				if(!"1".equals(omRelTmp.getContain())){
					sql.append(" and tmp.MATE_ID is null " );
				}
			}
		}
		sql.append(" order by tmp.AREA_CODE,tmp.START_TIME ");
		List<OrderMaterialRelationTmp> list = getBootPicResourceList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		//rowcount=list.size();
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		page.setDataList(list);
		return page;
	}

	/**
	 * 查询订单可选素材列表信息
	 * @param areaResource
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryAreaResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize){
		//select p.START_TIME,p.END_TIME,p.CHANNEL_GROUP_ID,ra.AREA_CODE,ra.AREA_NAME from t_ploy p,t_release_area ra where p.PLOY_NAME='sssss';
		StringBuffer sql =  new StringBuffer();
		//sql.append("select tmp.id,tmp.MATE_ID,tmp.START_TIME,tmp.END_TIME,tmp.CHANNEL_GROUP_ID,");
		sql.append("select tmp.id,tmp.MATE_ID,tmp.START_TIME,tmp.END_TIME,tmp.CHANNEL_GROUP_ID,tmp.area_code,");
		sql.append(" (select cg.NAME from t_channel_group cg where cg.ID=tmp.channel_group_id) groupName,");
		sql.append(" (select ca.AREA_NAME from t_release_area ca where ca.AREA_CODE=tmp.area_code ) areaName,");
		sql.append(" (select r.RESOURCE_NAME from t_resource r where r.ID=tmp.mate_id) resourceName,");
		sql.append(" tmp.play_location,tmp.is_hd,tmp.poll_index");
		sql.append(" from t_order_mate_rel_tmp tmp where 1=1 and tmp.AREA_CODE<>'152000000000'");
		
		if(omRelTmp != null){
			if(StringUtils.isNotBlank(omRelTmp.getOrderCode())){
				sql.append(" and tmp.ORDER_CODE = '" + omRelTmp.getOrderCode() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getStartTime())) {
				sql.append(" and tmp.START_TIME >= '" + omRelTmp.getStartTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getEndTime())) {
				sql.append(" and tmp.END_TIME <= '" + omRelTmp.getEndTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getAreaCode())) {
				sql.append(" and tmp.AREA_CODE = '" + omRelTmp.getAreaCode() + "' ");
			}
			if (omRelTmp.getChannelGroupId() != null) {
				sql.append(" and tmp.CHANNEL_GROUP_ID = " + omRelTmp.getChannelGroupId() + " ");
			}
			if(omRelTmp.getMateId()!=null){
				sql.append(" and tmp.MATE_ID = " + omRelTmp.getMateId() );
			}
			
			if(omRelTmp.isNotNull()){
					sql.append(" and tmp.MATE_ID is  not null " );
			}else{
				if(!"1".equals(omRelTmp.getContain())){
					sql.append(" and tmp.MATE_ID is null " );
				}
			}
		}
		sql.append(" order by tmp.area_code,tmp.START_TIME,tmp.poll_index ");
		
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<OrderMaterialRelationTmp> list = getSelectAreaResourceList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	
	
	
	
	@Override
	public PageBeanDB queryRadioResourceList(OrderMaterialRelationTmp omRelTmp,int pageNo, int pageSize) {
		StringBuffer sql =  new StringBuffer();
		sql.append("select tmp.id,tmp.MATE_ID,tmp.START_TIME,tmp.END_TIME,tmp.CHANNEL_GROUP_ID,tmp.area_code,");
		sql.append(" (select cg.NAME from t_channel_group cg where cg.ID=tmp.channel_group_id) groupName,");
		sql.append(" (select ca.AREA_NAME from t_release_area ca where ca.AREA_CODE=tmp.area_code ) areaName,");
		sql.append(" (select r.RESOURCE_NAME from t_resource r where r.ID=tmp.mate_id) resourceName,");
		sql.append(" tmp.play_location,tmp.is_hd,tmp.poll_index");
		sql.append(" from t_order_mate_rel_tmp tmp where 1=1");
		
		if(omRelTmp != null){
			if(StringUtils.isNotBlank(omRelTmp.getOrderCode())){
				sql.append(" and tmp.ORDER_CODE = '" + omRelTmp.getOrderCode() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getStartTime())) {
				sql.append(" and tmp.START_TIME >= '" + omRelTmp.getStartTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getEndTime())) {
				sql.append(" and tmp.END_TIME <= '" + omRelTmp.getEndTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getAreaCode())) {
				sql.append(" and tmp.AREA_CODE = '" + omRelTmp.getAreaCode() + "' ");
			}
			if (omRelTmp.getChannelGroupId() != null) {
				sql.append(" and tmp.CHANNEL_GROUP_ID = " + omRelTmp.getChannelGroupId() + " ");
			}
			
			if(omRelTmp.isNotNull()){
					sql.append(" and tmp.MATE_ID is  not null " );
			}else{
				if(!"1".equals(omRelTmp.getContain())){
					sql.append(" and tmp.MATE_ID is null " );
				}
			}
		}
		sql.append(" order by tmp.area_code,tmp.START_TIME,tmp.poll_index ");
		
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<OrderMaterialRelationTmp> list = getSelectAreaResourceList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}

	@Override
	public PageBeanDB queryBootResourceDetailList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize) {
		StringBuffer sql =  new StringBuffer();
		sql.append("SELECT ra.AREA_NAME, omt.PLAY_LOCATION, sr.RESOURCE_NAME FROM t_order_mate_rel_tmp omt, t_release_area ra, t_resource sr ");
		sql.append("WHERE omt.area_code = ra.AREA_CODE AND sr.ID = omt.MATE_ID AND omt.ORDER_CODE = '" + omRelTmp.getOrderCode());
		sql.append("'AND omt.MATE_ID = " + omRelTmp.getMateId());
		if (StringUtils.isNotBlank(omRelTmp.getAreaCode())) {
			sql.append(" and omt.AREA_CODE = '" + omRelTmp.getAreaCode() + "' ");
		}
		sql.append(" ORDER BY omt.area_code, (omt.PLAY_LOCATION + 0)");
		
		List<OrderMaterialRelationTmp> list = getBootPicResourceDetailList(this.getDataBySql(sql.toString(), null));
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		//rowcount=list.size();
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}	
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		page.setDataList(list);
		return page;
	}

	/**
	 * 查询回看菜单广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryLookResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize){
		StringBuffer sql =  new StringBuffer();
		sql.append("select tmp.id,tmp.MATE_ID,tmp.START_TIME,tmp.END_TIME,tmp.CHANNEL_GROUP_ID,tmp.area_code,");
		sql.append(" (select pm.MATCH_NAME from t_precise_match pm where pm.ID=tmp.precise_id) matchName,'',");
		sql.append(" (select r.RESOURCE_NAME from t_resource r where r.ID=tmp.mate_id) resourceName,");
		sql.append(" tmp.play_location,tmp.is_hd,tmp.poll_index,tmp.type");
		sql.append(" from t_order_mate_rel_tmp tmp where 1=1 ");
		
		if(omRelTmp != null){
			if(StringUtils.isNotBlank(omRelTmp.getOrderCode())){
				sql.append(" and tmp.ORDER_CODE = '" + omRelTmp.getOrderCode() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getStartTime())) {
				sql.append(" and tmp.START_TIME >= '" + omRelTmp.getStartTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getEndTime())) {
				sql.append(" and tmp.END_TIME <= '" + omRelTmp.getEndTime() + "' ");
			}
			if(omRelTmp.getMateId()!=null){
				sql.append(" and tmp.MATE_ID = " + omRelTmp.getMateId() + " ");
			}
			if(omRelTmp.isNotNull()){
				sql.append(" and tmp.MATE_ID is  not null " );
			}else{
				if(!"1".equals(omRelTmp.getContain())){
					sql.append(" and tmp.MATE_ID is null " );
				}
			}
		}
		sql.append(" order by tmp.START_TIME ");
		
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<OrderMaterialRelationTmp> list = getLookResourceList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	public PageBeanDB queryLookResourceListbyPre(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize){
		StringBuffer sql =  new StringBuffer();
		sql.append("select tmp.id,tmp.MATE_ID,tmp.START_TIME,tmp.END_TIME,tmp.CHANNEL_GROUP_ID,tmp.area_code,");
		sql.append(" (select pm.MATCH_NAME from t_precise_match pm where pm.ID=tmp.precise_id order by pm.ID desc) matchName ,");
		sql.append("(select pm.PRECISETYPE from t_precise_match pm where pm.ID=tmp.precise_id ) pretype , ");
		sql.append(" (select r.RESOURCE_NAME from t_resource r where r.ID=tmp.mate_id) resourceName,");
		sql.append(" tmp.play_location,tmp.is_hd,tmp.poll_index,tmp.type");
		sql.append(" from t_order_mate_rel_tmp tmp where 1=1 ");
		
		if(omRelTmp != null){
			if(StringUtils.isNotBlank(omRelTmp.getOrderCode())){
				sql.append(" and tmp.ORDER_CODE = '" + omRelTmp.getOrderCode() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getStartTime())) {
				sql.append(" and tmp.START_TIME >= '" + omRelTmp.getStartTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getEndTime())) {
				sql.append(" and tmp.END_TIME <= '" + omRelTmp.getEndTime() + "' ");
			}
			if(omRelTmp.getMateId()!=null){
				sql.append(" and tmp.MATE_ID = " + omRelTmp.getMateId() + " ");
			}
			if(omRelTmp.isNotNull()){
				sql.append(" and tmp.MATE_ID is  not null " );
			}else{
				if(!"1".equals(omRelTmp.getContain())){
					sql.append(" and tmp.MATE_ID is null " );
				}
			}
		}
		sql.append(" order by tmp.START_TIME ");
		
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<OrderMaterialRelationTmp> list = getLookResourceListPre(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	private List<OrderMaterialRelationTmp> getLookResourceList(List<?> resultList) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelationTmp ar = new OrderMaterialRelationTmp();
			ar.setId(toInteger(toInteger(obj[0])));
			ar.setMateId(toInteger(obj[1]));
			ar.setStartTime((String)(obj[2]));
			ar.setEndTime((String)(obj[3]));
			ar.setChannelGroupId(toInteger(obj[4]));
			ar.setAreaCode((String)(obj[5]));
			int type = toInteger(obj[12]);
			if(type==1){//基本策略中的回看栏目
				ar.setChannelGroupName("所有");
			}else{//精准中的回看栏目
				ar.setChannelGroupName((String)(obj[6]));
			}
			
			ar.setAreaName((String)(obj[7]));
			ar.setMateName((String)(obj[8]));
			ar.setPlayLocation((String)(obj[9]));
			ar.setIsHD(toInteger(obj[10]));
			ar.setPollIndex(toInteger(obj[11]));
			
			list.add(ar);
		}
		return list;
	}
	private List<OrderMaterialRelationTmp> getLookResourceListPre(List<?> resultList) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelationTmp ar = new OrderMaterialRelationTmp();
			ar.setId(toInteger(toInteger(obj[0])));
			ar.setMateId(toInteger(obj[1]));
			ar.setStartTime((String)(obj[2]));
			ar.setEndTime((String)(obj[3]));
			ar.setChannelGroupId(toInteger(obj[4]));
			ar.setAreaCode((String)(obj[5]));
			int type = toInteger(obj[12]);
			if(type==1){//基本策略中的回看栏目
				ar.setChannelGroupName("无");
				ar.setAreaName((String)(obj[7]));
			}else{//精准中的回看栏目
				ar.setChannelGroupName((String)(obj[6]));
				ar.setPreciseType(toInteger(obj[7]));
			}
			ar.setMateName((String)(obj[8]));
			ar.setPlayLocation((String)(obj[9]));
			ar.setIsHD(toInteger(obj[10]));
			ar.setPollIndex(toInteger(obj[11]));
			ar.setType(type);
			list.add(ar);
		}
		return list;
	}
	@SuppressWarnings("unused")
	private List<OrderMaterialRelationTmp> reArayyList(List<OrderMaterialRelationTmp> listOr){
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		List<OrderMaterialRelationTmp> listFilm = new ArrayList<OrderMaterialRelationTmp>();
		List<OrderMaterialRelationTmp> listCate = new ArrayList<OrderMaterialRelationTmp>();
		OrderMaterialRelationTmp ar1 = new OrderMaterialRelationTmp();
		OrderMaterialRelationTmp ar2 = new OrderMaterialRelationTmp();
		for(int i=0;i<listOr.size();i++){
			OrderMaterialRelationTmp ar =listOr.get(i) ;
			if(ar.getPreciseType()!=null){
				if(ar.getPreciseType()==4){
					listFilm.add(ar);
				}else if((ar.getPreciseType()==8)){
					listCate.add(ar);
				}
			}else{
				if(ar1.getPreciseType()==null){
					ar1=ar;
					ar1.setPreciseType(4);
					ar1.setChannelGroupName("所有栏目");
				}else {
					ar2=ar;
					ar2.setPreciseType(4);
					ar2.setChannelGroupName("所有影片");
				}
			}
		}
		list.add(ar1);
		list.addAll(listFilm);
		list.add(ar2);
		list.addAll(listCate);
		return list;
		
	}
	
	
	/**
	 * 查询插播广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param positionId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryInstreamResourceList(OrderMaterialRelationTmp omRelTmp,int positionId, int pageNo, int pageSize){
		StringBuffer sql =  new StringBuffer();
		sql.append("select tmp.id,tmp.MATE_ID,tmp.START_TIME,tmp.END_TIME,tmp.CHANNEL_GROUP_ID,tmp.area_code,");
		sql.append(" (select pm.MATCH_NAME from t_precise_match pm where pm.ID=tmp.precise_id) matchName,");
		sql.append("(select pm.PRECISETYPE from t_precise_match pm where pm.ID=tmp.precise_id ) pretype , ");
		sql.append(" (select r.RESOURCE_NAME from t_resource r where r.ID=tmp.mate_id) resourceName,");
		sql.append(" tmp.play_location,tmp.is_hd,tmp.poll_index,tmp.type");
		sql.append(" from t_order_mate_rel_tmp tmp where 1=1 ");
		
		if(omRelTmp != null){
			if(StringUtils.isNotBlank(omRelTmp.getOrderCode())){
				sql.append(" and tmp.ORDER_CODE = '" + omRelTmp.getOrderCode() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getStartTime())) {
				sql.append(" and tmp.START_TIME >= '" + omRelTmp.getStartTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getEndTime())) {
				sql.append(" and tmp.END_TIME <= '" + omRelTmp.getEndTime() + "' ");
			}
			if(omRelTmp.getMateId()!=null){
				sql.append(" and tmp.MATE_ID = " + omRelTmp.getMateId() + " ");
			}
			if(omRelTmp.isNotNull()){
				sql.append(" and tmp.MATE_ID is  not null " );
			}else{
				if(!"1".equals(omRelTmp.getContain())){
					sql.append(" and tmp.MATE_ID is null " );
				}
			}
		}
		sql.append(" order by tmp.type desc,tmp.START_TIME,tmp.play_location ");
		
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<OrderMaterialRelationTmp> list = getInstreamResourceList(this.getListBySql(sql.toString(), null, pageNo, pageSize),positionId);
		page.setDataList(list);
		return page;
	}
	
	private List<OrderMaterialRelationTmp> getInstreamResourceList(List<?> resultList,int positionId) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelationTmp ar = new OrderMaterialRelationTmp();
			ar.setId(toInteger(toInteger(obj[0])));
			ar.setMateId(toInteger(obj[1]));
			ar.setStartTime((String)(obj[2]));
			ar.setEndTime((String)(obj[3]));
			ar.setChannelGroupId(toInteger(obj[4]));
			ar.setAreaCode((String)(obj[5]));
			int type = toInteger(obj[12]);
			if(type==1 && positionId==40){//基本策略中的频道
				ar.setChannelGroupName("所有频道");
				ar.setAreaName((String)(obj[7]));
			}else if(type==1 && positionId==43){//基本策略中的产品
				ar.setChannelGroupName("所有产品");
				ar.setAreaName((String)(obj[7]));
			}else{//精准中的回看栏目
				ar.setChannelGroupName((String)(obj[6]));
			}
			ar.setPreciseType( toInteger(obj[7]));
			ar.setMateName((String)(obj[8]));
			ar.setPlayLocation((String)(obj[9]));
			ar.setIsHD(toInteger(obj[10]));
			ar.setPollIndex(toInteger(obj[11]));
			
			list.add(ar);
		}
		return list;
	}
	
	/**
	 * 查询暂停广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param positionId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPauseResourceList(OrderMaterialRelationTmp omRelTmp,int positionId, int pageNo, int pageSize){
		StringBuffer sql =  new StringBuffer();
		sql.append("select tmp.id,tmp.MATE_ID,tmp.START_TIME,tmp.END_TIME,tmp.CHANNEL_GROUP_ID,tmp.area_code,");
		sql.append(" (select pm.MATCH_NAME from t_precise_match pm where pm.ID=tmp.precise_id) matchName,'',");
		sql.append(" (select r.RESOURCE_NAME from t_resource r where r.ID=tmp.mate_id) resourceName,");
		sql.append(" tmp.play_location,tmp.is_hd,tmp.poll_index,tmp.type");
		sql.append(" from t_order_mate_rel_tmp tmp where 1=1 ");
		
		if(omRelTmp != null){
			if(StringUtils.isNotBlank(omRelTmp.getOrderCode())){
				sql.append(" and tmp.ORDER_CODE = '" + omRelTmp.getOrderCode() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getStartTime())) {
				sql.append(" and tmp.START_TIME >= '" + omRelTmp.getStartTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getEndTime())) {
				sql.append(" and tmp.END_TIME <= '" + omRelTmp.getEndTime() + "' ");
			}
			if(omRelTmp.getMateId()!=null){
				sql.append(" and tmp.MATE_ID = " + omRelTmp.getMateId() + " ");
			}
			if(omRelTmp.isNotNull()){
				sql.append(" and tmp.MATE_ID is  not null " );
			}else{
				if(!"1".equals(omRelTmp.getContain())){
					sql.append(" and tmp.MATE_ID is null " );
				}
			}
		}
		sql.append(" order by tmp.type desc,tmp.START_TIME,tmp.play_location ");
		
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<OrderMaterialRelationTmp> list = getPauseResourceList(this.getListBySql(sql.toString(), null, pageNo, pageSize),positionId);
		page.setDataList(list);
		return page;
	}
	
	private List<OrderMaterialRelationTmp> getPauseResourceList(List<?> resultList,int positionId) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelationTmp ar = new OrderMaterialRelationTmp();
			ar.setId(toInteger(toInteger(obj[0])));
			ar.setMateId(toInteger(obj[1]));
			ar.setStartTime((String)(obj[2]));
			ar.setEndTime((String)(obj[3]));
			ar.setChannelGroupId(toInteger(obj[4]));
			ar.setAreaCode((String)(obj[5]));
			int type = toInteger(obj[12]);
			if(type==1 && positionId==39){//基本策略中的频道
				ar.setChannelGroupName("所有频道");
			}else if(type==1 && positionId==19){//基本策略中的产品
				ar.setChannelGroupName("所有产品");
			}else if(type==1 && positionId==20){//基本策略中的产品
				ar.setChannelGroupName("所有产品");
			}else{//精准中的回看栏目
				ar.setChannelGroupName((String)(obj[6]));
			}
			
			ar.setAreaName((String)(obj[7]));
			ar.setMateName((String)(obj[8]));
			ar.setPlayLocation((String)(obj[9]));
			ar.setIsHD(toInteger(obj[10]));
			ar.setPollIndex(toInteger(obj[11]));
			
			list.add(ar);
		}
		return list;
	}
	
	/**
	 * 查询请求式广告订单可选素材列表信息
	 * @param omRelTmp
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryReqResourceList(OrderMaterialRelationTmp omRelTmp, int pageNo, int pageSize){
		StringBuffer sql =  new StringBuffer();
		sql.append("select tmp.id,tmp.MATE_ID,tmp.START_TIME,tmp.END_TIME,tmp.CHANNEL_GROUP_ID,tmp.area_code,");
		sql.append(" (select pm.MATCH_NAME from t_precise_match pm where pm.ID=tmp.precise_id) matchName,");
		sql.append("(select pm.PRECISETYPE from t_precise_match pm where pm.ID=tmp.precise_id ) pretype , ");
		sql.append(" (select r.RESOURCE_NAME from t_resource r where r.ID=tmp.mate_id) resourceName,");
		sql.append(" tmp.play_location,tmp.is_hd,tmp.poll_index,tmp.type");
		sql.append(" from t_order_mate_rel_tmp tmp where 1=1 ");
		
		if(omRelTmp != null){
			if(StringUtils.isNotBlank(omRelTmp.getOrderCode())){
				sql.append(" and tmp.ORDER_CODE = '" + omRelTmp.getOrderCode() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getStartTime())) {
				sql.append(" and tmp.START_TIME >= '" + omRelTmp.getStartTime() + "' ");
			}
			if (StringUtils.isNotBlank(omRelTmp.getEndTime())) {
				sql.append(" and tmp.END_TIME <= '" + omRelTmp.getEndTime() + "' ");
			}
			if(omRelTmp.getMateId()!=null){
				sql.append(" and tmp.MATE_ID = " + omRelTmp.getMateId() + " ");
			}
			if(omRelTmp.isNotNull()){
				sql.append(" and tmp.MATE_ID is  not null " );
			}else{
				if(!"1".equals(omRelTmp.getContain())){
					sql.append(" and tmp.MATE_ID is null " );
				}
			}
		}
		sql.append(" order by tmp.START_TIME ");
		
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<OrderMaterialRelationTmp> list = getReqResourceList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	
	private List<OrderMaterialRelationTmp> getReqResourceList(List<?> resultList) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelationTmp ar = new OrderMaterialRelationTmp();
			ar.setId(toInteger(toInteger(obj[0])));
			ar.setMateId(toInteger(obj[1]));
			ar.setStartTime((String)(obj[2]));
			ar.setEndTime((String)(obj[3]));
			ar.setChannelGroupId(toInteger(obj[4]));
			ar.setAreaCode((String)(obj[5]));
			int type = toInteger(obj[12]);
			if(type==1){//基本策略
				ar.setChannelGroupName("无");
				ar.setAreaName((String)(obj[7]));
			}else{//精准名称
				ar.setChannelGroupName((String)(obj[6]));
			}
			ar.setPreciseType(toInteger(obj[7]));
			ar.setMateName((String)(obj[8]));
			ar.setPlayLocation((String)(obj[9]));
			ar.setIsHD(toInteger(obj[10]));
			ar.setPollIndex(toInteger(obj[11]));
			
			list.add(ar);
		}
		return list;
	}
	
	public PageBeanDB getSelectedResource(AreaResource areaResource, int pageNo, int pageSize){
		//select p.START_TIME,p.END_TIME,p.CHANNEL_GROUP_ID,ra.AREA_CODE,ra.AREA_NAME from t_ploy p,t_release_area ra where p.PLOY_NAME='sssss';
		StringBuffer sql =  new StringBuffer();
		sql.append("select tmp.id,tmp.MATE_ID,tmp.START_TIME,tmp.END_TIME,tmp.CHANNEL_GROUP_ID,tmp.area_code,");
		sql.append(" (select cg.NAME from t_channel_group cg where cg.ID=tmp.channel_group_id) groupName,");
		sql.append(" (select ca.AREA_NAME from t_release_area ca where ca.AREA_CODE=tmp.area_code) areaName,");
		sql.append(" (select r.RESOURCE_NAME from t_resource r where r.ID=tmp.mate_id) resourceName,");
		sql.append(" tmp.play_location,tmp.is_hd,tmp.poll_index");
		sql.append(" from t_order_mate_rel_tmp tmp where 1=1 ");
		
		if(areaResource != null){
			if(StringUtils.isNotBlank(areaResource.getOrderCode())){
				sql.append(" and tmp.ORDER_CODE = '" + areaResource.getOrderCode() + "' ");
			}
			if (StringUtils.isNotBlank(areaResource.getStartTime())) {
				sql.append(" and tmp.START_TIME >= '" + areaResource.getStartTime() + "' ");
			}
			if (StringUtils.isNotBlank(areaResource.getEndTime())) {
				sql.append(" and tmp.END_TIME <= '" + areaResource.getEndTime() + "' ");
			}
			if (areaResource.getAreaCode() != null && areaResource.getAreaCode() != 0) {
				sql.append(" and tmp.AREA_CODE = " + areaResource.getAreaCode() + " ");
			}
			if (areaResource.getChannelGroupId() != null) {
				sql.append(" and tmp.CHANNEL_GROUP_ID = " + areaResource.getChannelGroupId() + " ");
			}
			if(areaResource.getResourceId()!=null){
				sql.append(" and tmp.MATE_ID = " + areaResource.getResourceId() + " ");
			}
		}
		
		int rowcount = this.getTotalCountSQL(sql.toString(), null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List<OrderMaterialRelationTmp> list = getSelectAreaResourceList(this.getListBySql(sql.toString(), null, pageNo, pageSize));
		page.setDataList(list);
		return page;
	}
	
	private List<OrderMaterialRelationTmp> getSelectAreaResourceList(List<?> resultList) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelationTmp ar = new OrderMaterialRelationTmp();
			ar.setId(toInteger(toInteger(obj[0])));
			ar.setMateId(toInteger(obj[1]));
			ar.setStartTime((String)(obj[2]));
			ar.setEndTime((String)(obj[3]));
			ar.setChannelGroupId(toInteger(obj[4]));
			ar.setAreaCode((String)(obj[5]));
			ar.setChannelGroupName((String)(obj[6]));
			ar.setAreaName((String)(obj[7]));
			ar.setMateName((String)(obj[8]));
			ar.setPlayLocation((String)(obj[9]));
			ar.setIsHD(toInteger(obj[10]));
			ar.setPollIndex(toInteger(obj[11]));
			list.add(ar);
			
		}
		return list;
	}
	private List<OrderMaterialRelationTmp> getAreaResourceList(List<?> resultList) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		List<OrderMaterialRelationTmp> list2= new ArrayList<OrderMaterialRelationTmp>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelationTmp ar = new OrderMaterialRelationTmp();
			ar.setId(toInteger(toInteger(obj[0])));
			ar.setMateId(toInteger(obj[1]));
			ar.setStartTime((String)(obj[2]));
			ar.setEndTime((String)(obj[3]));
			ar.setChannelGroupId(toInteger(obj[4]));
			ar.setAreaCode((String)(obj[5]));
			ar.setChannelGroupName((String)(obj[6]));
			ar.setAreaName((String)(obj[7]));
			ar.setMateName((String)(obj[8]));
			ar.setPlayLocation((String)(obj[9]));
			ar.setIsHD(toInteger(obj[10]));
			ar.setPollIndex(toInteger(obj[11]));
			if(ar.getAreaName()!=null){
				list.add(ar);
			}else {
				//temp=ar;
			}
			
		}
		//list2.add(temp);
		list2.addAll(list);
		return list2;
	}
	private List<OrderMaterialRelationTmp> getTheAreaResourceList(List<?> resultList) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		List<OrderMaterialRelationTmp> list2= new ArrayList<OrderMaterialRelationTmp>();
		OrderMaterialRelationTmp temp = new OrderMaterialRelationTmp();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelationTmp ar = new OrderMaterialRelationTmp();
			ar.setId(toInteger(toInteger(obj[0])));
			ar.setMateId(toInteger(obj[1]));
			ar.setStartTime((String)(obj[2]));
			ar.setEndTime((String)(obj[3]));
			ar.setChannelGroupId(toInteger(obj[4]));
			ar.setAreaCode((String)(obj[5]));
			ar.setChannelGroupName((String)(obj[6]));
			ar.setAreaName((String)(obj[7]));
			ar.setMateName((String)(obj[8]));
			ar.setPlayLocation((String)(obj[9]));
			ar.setIsHD(toInteger(obj[10]));
			//ar.setPollIndex(toInteger(obj[11]));
			if(ar.getAreaName()!=null){
				list.add(ar);
			}else {
				
			}
			
		}
		list2.addAll(list);
		return list2;
	}
	
	private List<OrderMaterialRelationTmp> getBootPicResourceList(List<?> resultList) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelationTmp ar = new OrderMaterialRelationTmp();			
			ar.setStartTime("00:00:00");
			ar.setEndTime("23:59:59");			
			ar.setAreaCode((String)(obj[0]));		
			ar.setAreaName((String)(obj[1]));
			if(obj[2]!=null){
				Object ob = obj[2];
				ar.setMateId(Integer.parseInt(ob.toString()));
			}
			list.add(ar);
		}
		return list;
	}
	
	private List<OrderMaterialRelationTmp> getBootPicResourceDetailList(List<?> resultList) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		String areaNameFlag = "";
		OrderMaterialRelationTmp entity = null;
		String playLocations = "";
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			String areaName = (String)(obj[0]);
			if(!areaName.equals(areaNameFlag)){
				if(null != entity){
					if(playLocations.endsWith(",")){
						playLocations = playLocations.substring(0, playLocations.length() - 1);
					}
					entity.setPlayLocation(playLocations);
					list.add(entity);
				}
				entity = new OrderMaterialRelationTmp();
				entity.setStartTime("00:00:00");
				entity.setEndTime("23:59:59");	
				entity.setAreaName(areaName);
				entity.setMateName((String)obj[2]);
				areaNameFlag = areaName;
				playLocations = "";
			}
			playLocations += (String)(obj[1]) + ",";
		}
		if(null != entity){
			if(playLocations.endsWith(",")){
				playLocations = playLocations.substring(0, playLocations.length() - 1);
			}
			entity.setPlayLocation(playLocations);
			list.add(entity);
		}
		return list;
	}
	
	
	/**
	 * 删除订单和素材临时关系数据
	 * @param orderCode
	 */
	public void delOrderMateRelTmp(String orderCode){
		String delSql = "delete from t_order_mate_rel_tmp where order_code='"+orderCode+"'";
		this.executeBySQL(delSql, null);
	}
	
	/**
	 * 添加订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertOrderMateRelTmp(String orderCode,int ployId,int positionId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//新增订单和素材临时关系数据
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel_tmp(order_code,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,channel_group_id)");
		insertSql.append(" select o.order_code,rel.MATE_ID,rel.PLAY_LOCATION,rel.is_hd,rel.poll_index,");
		insertSql.append("rel.precise_id,rel.TYPE,rel.start_time,rel.end_time,rel.area_code,rel.channel_group_id ");
		insertSql.append("from t_order_mate_rel rel, t_order o ");
		insertSql.append(" where rel.order_id=o.id and o.order_code='").append(orderCode).append("'");
		this.executeBySQL(insertSql.toString(), null);
		
//		if(positionId == 1 || positionId == 2 || positionId == 45 || positionId == 46){
//			//开机广告、菜单视频外框广告、热点推荐页面广告
//			//添加区域中的数据到订单和素材临时关系表
//			StringBuffer insertSql2 =  new StringBuffer();
//			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code,channel_group_id)");
//			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
//			insertSql2.append(" from t_ploy p,t_release_area ra ");
//			insertSql2.append(" where p.AREA_ID=ra.AREA_CODE and p.PLOY_ID= ").append(ployId);
//			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
//			insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
//			insertSql2.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
//			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
//			this.executeBySQL(insertSql2.toString(), null);
//		}else 
		List<PloyBackup> ployList= ployDao.getPloyListByPloyID(ployId);
		boolean isQuanSheng=false;
		for(int i=0;i<ployList.size();i++){
			if( 152000000000L == ployList.get(i).getAreaId() || 0L == ployList.get(i).getAreaId() ){
				isQuanSheng=true;
				break;
			}
		}
		if( positionId == 44|| positionId == 21 || positionId == 22 || positionId == 45 || positionId == 46 ||positionId == 49){
			
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code,channel_group_id)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
			insertSql2.append(" from t_ploy p,t_release_area ra ");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			//选择了地市，则只插入该地市的，否则默认全省，则插入18个地市，
			if(!isQuanSheng){
				insertSql2.append(" and p.AREA_ID=ra.AREA_CODE ");
			}
			insertSql2.append(" and ra.AREA_CODE <> '152000000000' ");
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			//modify 张晓峰	2014-8-20 兼容 策略中全时段开始时间和结束时间保存为0
			insertSql2.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))" +
					"and rel.area_code=ra.AREA_CODE");
			insertSql2.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}else if(positionId == 5 || positionId == 6 || positionId == 7 || positionId == 8
				|| positionId == 9 || positionId == 10 || positionId == 11 || positionId == 12 
				|| positionId == 41 || positionId == 42 || positionId == 50){
			//按时间段、区域、频道组选择素材（导航条广告、快捷切换列表广告、音量条广告、预告提示广告、收藏列表广告）
			//添加策略中的数据到订单和素材临时关系表
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code,channel_group_id)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
			insertSql2.append(" from t_ploy p,t_channel_group cg,t_release_area ra ");
			insertSql2.append(" where p.CHANNEL_GROUP_ID=cg.ID and p.PLOY_ID= ").append(ployId);
			
			//选择了地市，则只插入该地市的，否则默认全省，则插入18个地市，
			if(!isQuanSheng){
				insertSql2.append(" and p.AREA_ID=ra.AREA_CODE ");
			}
			insertSql2.append(" AND ra.AREA_CODE <> '152000000000'");
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))" +
					"and rel.area_code=ra.AREA_CODE");
			//insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME and rel.area_code=ra.AREA_CODE");
			insertSql2.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
			
			//添加精准中的数据到订单和素材临时关系表
//			StringBuffer insertSql3 =  new StringBuffer();
//			insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code,channel_group_id)");
//			insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
//			insertSql3.append(" from t_ploy p,t_precise_match pm,t_channel_group cg,t_release_area ra ");
//			insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.CHANNEL_GROUP_ID=cg.ID and p.PLOY_ID= ").append(ployId);
//			insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
//			insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME and rel.area_code=ra.AREA_CODE");
//			insertSql3.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = pm.id ");
//			insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
//			this.executeBySQL(insertSql3.toString(), null);
			
			//插入频道组为空的数据
			StringBuffer insertSql3 =  new StringBuffer();
			insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code,channel_group_id)");
			insertSql3.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
			insertSql3.append(" from t_ploy p,t_release_area ra ");
			insertSql3.append(" where p.CHANNEL_GROUP_ID=0 and p.PLOY_ID= ").append(ployId);
			
			//选择了地市，则只插入该地市的，否则默认全省，则插入18个地市，
			if(!isQuanSheng){
				insertSql3.append(" and p.AREA_ID=ra.AREA_CODE ");
			}
			insertSql3.append(" AND ra.AREA_CODE <> '152000000000'");
			insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql3.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))" +
					"and rel.area_code=ra.AREA_CODE");
			//insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME and rel.area_code=ra.AREA_CODE");
			insertSql3.append(" and rel.channel_group_id=0 and rel.precise_id = p.id ");
			insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql3.toString(), null);
			
		}
		//广播背景
		else if(positionId == 13 || positionId == 14){
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code,channel_group_id)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
			insertSql2.append(" from t_ploy p, t_release_area ra ");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			if(!isQuanSheng){
				insertSql2.append(" and p.AREA_ID=ra.AREA_CODE ");
			}
			insertSql2.append(" and ra.AREA_CODE <> '152000000000' ");
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))" +
					"and rel.area_code=ra.AREA_CODE");
			//insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME and rel.area_code=ra.AREA_CODE");
			insertSql2.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
//			
//			//插入频道组为空的数据
//			StringBuffer insertSql3 =  new StringBuffer();
//			insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code,channel_group_id)");
//			insertSql3.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,p.AREA_ID,p.CHANNEL_GROUP_ID");
//			insertSql3.append(" from t_ploy p, t_release_area ra ");
//			insertSql3.append(" where p.CHANNEL_GROUP_ID=0 and p.PLOY_ID= ").append(ployId);
//			if(!isQuanSheng){
//				insertSql3.append(" and p.AREA_ID=ra.AREA_CODE ");
//			}
//			insertSql3.append(" and ra.AREA_CODE <> '152000000000' ");
//			insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
//			insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME and rel.area_code=ra.AREA_CODE");
//			insertSql3.append(" and rel.channel_group_id=0 and rel.precise_id = p.id ");
//			insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
//			this.executeBySQL(insertSql3.toString(), null);
		}
	}
	//用于修改订单时选择策略
	public void insertOrderMateRelTmp2(String orderCode,int ployId,int positionId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		List<PloyBackup> ployList= ployDao.getPloyListByPloyID(ployId);
		boolean isQuanSheng=false;
		for(int i=0;i<ployList.size();i++){
			if( 152000000000L == ployList.get(i).getAreaId() || 0L == ployList.get(i).getAreaId() ){
				isQuanSheng=true;
				break;
			}
		}
		if( positionId == 44|| positionId == 21 || positionId == 22 || positionId == 45 || positionId == 46){
			
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code,channel_group_id)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
			insertSql2.append(" from t_ploy p,t_release_area ra ");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			//选择了地市，则只插入该地市的，否则默认全省，则插入18个地市，
			if(!isQuanSheng){
				insertSql2.append(" and p.AREA_ID=ra.AREA_CODE ");
			}
			insertSql2.append(" and ra.AREA_CODE <> '152000000000' ");
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			//modify 张晓峰	2014-8-20 兼容 策略中全时段开始时间和结束时间保存为0
			insertSql2.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))" +
					"and rel.area_code=ra.AREA_CODE");
			insertSql2.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}else if(positionId == 5 || positionId == 6 || positionId == 7 || positionId == 8
				|| positionId == 9 || positionId == 10 || positionId == 11 || positionId == 12 
				|| positionId == 41 || positionId == 42 ){
			//按时间段、区域、频道组选择素材（导航条广告、快捷切换列表广告、音量条广告、预告提示广告、收藏列表广告）
			//添加策略中的数据到订单和素材临时关系表
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code,channel_group_id)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
			insertSql2.append(" from t_ploy p,t_channel_group cg,t_release_area ra ");
			insertSql2.append(" where p.CHANNEL_GROUP_ID=cg.ID and p.PLOY_ID= ").append(ployId);
			insertSql2.append(" AND ra.AREA_CODE <> '152000000000'");
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME and rel.area_code=ra.AREA_CODE");
			insertSql2.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
					
			//插入频道组为空的数据
			StringBuffer insertSql3 =  new StringBuffer();
			insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code,channel_group_id)");
			insertSql3.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
			insertSql3.append(" from t_ploy p,t_release_area ra ");
			insertSql3.append(" where p.CHANNEL_GROUP_ID=0 and p.PLOY_ID= ").append(ployId);
			insertSql3.append(" AND ra.AREA_CODE <> '152000000000'");
			insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME and rel.area_code=ra.AREA_CODE");
			insertSql3.append(" and rel.channel_group_id=0 and rel.precise_id = p.id ");
			insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql3.toString(), null);
			
		}
		//广播背景
		else if(positionId == 13 || positionId == 14){
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code,channel_group_id)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
			insertSql2.append(" from t_ploy p, t_release_area ra ");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			if(!isQuanSheng){
				insertSql2.append(" and p.AREA_ID=ra.AREA_CODE ");
			}
			insertSql2.append(" and ra.AREA_CODE <> '152000000000' ");
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME and rel.area_code=ra.AREA_CODE");
			insertSql2.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}
	}
	
	/**
	 * 添加回看回放菜单广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertLookBackOrderMateRelTmp(String orderCode,int ployId,int positionId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//新增已有订单和素材关系数据到临时表
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel_tmp(order_code,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,channel_group_id)");
		insertSql.append(" select o.order_code,rel.MATE_ID,rel.PLAY_LOCATION,rel.is_hd,rel.poll_index,");
		insertSql.append("rel.precise_id,rel.TYPE,rel.start_time,rel.end_time,rel.area_code,rel.channel_group_id ");
		insertSql.append("from t_order_mate_rel rel, t_order o ");
		insertSql.append(" where rel.order_id=o.id and o.order_code='").append(orderCode).append("'");
		this.executeBySQL(insertSql.toString(), null);
		
		//将策略中的时段添加到临时表
		if(positionId!=17){
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME");
			insertSql2.append(" from t_ploy p");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
			//insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
			insertSql2.append(" and rel.precise_id = p.id ");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}else{
			//将策略中的频道组添加到临时表
			StringBuffer insertSql22 =  new StringBuffer();
			insertSql22.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,channel_group_id)");
			insertSql22.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,p.CHANNEL_GROUP_ID");
			insertSql22.append(" from t_ploy p,t_channel_group_npvr cg");
			insertSql22.append(" where p.CHANNEL_GROUP_ID=cg.ID and p.PLOY_ID= ").append(ployId);
			insertSql22.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql22.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
			//insertSql22.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
			insertSql22.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
			insertSql22.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql22.toString(), null);
		}
		//未设置的时段、精准数据到临时表
		StringBuffer insertSql3 =  new StringBuffer();
		insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME");
		insertSql3.append(" from t_ploy p,t_precise_match pm ");
		insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.PLOY_ID= ").append(ployId);
		insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql3.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
				"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
		//insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql3.append(" and rel.precise_id = pm.id ");
		insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql3.toString(), null);
	}
	public void insertLookBackOrderMateRelTmp2(String orderCode,int ployId,int positionId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//将策略中的时段添加到临时表
		if(positionId!=17){
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME");
			insertSql2.append(" from t_ploy p");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
			insertSql2.append(" and rel.precise_id = p.id ");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}else{
			//将策略中的频道组添加到临时表
			StringBuffer insertSql22 =  new StringBuffer();
			insertSql22.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,channel_group_id)");
			insertSql22.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,p.CHANNEL_GROUP_ID");
			insertSql22.append(" from t_ploy p,t_channel_group_npvr cg");
			insertSql22.append(" where p.CHANNEL_GROUP_ID=cg.ID and p.PLOY_ID= ").append(ployId);
			insertSql22.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql22.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
			insertSql22.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
			insertSql22.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql22.toString(), null);
		}
		//未设置的时段、精准数据到临时表
		StringBuffer insertSql3 =  new StringBuffer();
		insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME");
		insertSql3.append(" from t_ploy p,t_precise_match pm ");
		insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.PLOY_ID= ").append(ployId);
		insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql3.append(" and rel.precise_id = pm.id ");
		insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql3.toString(), null);
	}
	
	
	
	/** 
	 * 添加回看回放菜单广告订单和素材临时关系数据  点播随片
	 * @param orderCode
	 * @param ployId
	 */
	public void insertFollowOrderMateRelTmp(String orderCode,int ployId,int positionId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//新增已有订单和素材关系数据到临时表
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel_tmp(order_code,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,PRECISETYPE)");
		insertSql.append(" select o.order_code,rel.MATE_ID,rel.PLAY_LOCATION,rel.is_hd,rel.poll_index,");
		insertSql.append("rel.precise_id,rel.TYPE,rel.start_time,rel.end_time,rel.area_code,rel.PRECISETYPE ");
		insertSql.append("from t_order_mate_rel rel, t_order o ");
		insertSql.append(" where rel.order_id=o.id and o.order_code='").append(orderCode).append("'");
		this.executeBySQL(insertSql.toString(), null);
		
		//将策略中的时段添加到临时表
		StringBuffer insertSql22 =  new StringBuffer();
		insertSql22.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql22.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME");
		insertSql22.append(" from t_ploy p");
		insertSql22.append(" where p.PLOY_ID= ").append(ployId);
		insertSql22.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql22.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
				"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
		//insertSql22.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql22.append(" and rel.precise_id = p.id ");
		insertSql22.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql22.toString(), null);
		
		
		//未设置的时段、精准数据到临时表
		StringBuffer insertSql3 =  new StringBuffer();
		insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time, PRECISETYPE)");
		insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME,pm.PRECISETYPE");
		insertSql3.append(" from t_ploy p,t_precise_match pm ");
		insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.PLOY_ID= ").append(ployId);
		insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql3.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
				"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
		//insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql3.append(" and rel.precise_id = pm.id ");
		insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql3.toString(), null);
	}
	public void insertFollowOrderMateRelTmp2(String orderCode,int ployId,int positionId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//将策略中的时段添加到临时表
		StringBuffer insertSql22 =  new StringBuffer();
		insertSql22.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql22.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME");
		insertSql22.append(" from t_ploy p");
		insertSql22.append(" where p.PLOY_ID= ").append(ployId);
		insertSql22.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql22.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql22.append(" and rel.precise_id = p.id ");
		insertSql22.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql22.toString(), null);
		
		
		//未设置的时段、精准数据到临时表
		StringBuffer insertSql3 =  new StringBuffer();
		insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time, PRECISETYPE)");
		insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME,pm.PRECISETYPE");
		insertSql3.append(" from t_ploy p,t_precise_match pm ");
		insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.PLOY_ID= ").append(ployId);
		insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql3.append(" and rel.precise_id = pm.id ");
		insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql3.toString(), null);
	}
	
	
	
	
	
	/**
	 * 添加回看回放菜单广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertLookRepalyOrderMateRelTmp(String orderCode,int ployId,int positionId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//System.out.println("ployid="+ployId+"orderCode="+orderCode+"positionId="+positionId);
		//新增已有订单和素材关系数据到临时表
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel_tmp(order_code,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,channel_group_id)");
		insertSql.append(" select o.order_code,rel.MATE_ID,rel.PLAY_LOCATION,rel.is_hd,rel.poll_index,");
		insertSql.append("rel.precise_id,rel.TYPE,rel.start_time,rel.end_time,rel.area_code,rel.channel_group_id ");
		insertSql.append("from t_order_mate_rel rel, t_order o ");
		insertSql.append(" where rel.order_id=o.id and o.order_code='").append(orderCode).append("'");
		this.executeBySQL(insertSql.toString(), null);
		
		//将策略中的时段添加到临时表
			//将策略中的频道组添加到临时表
//			StringBuffer insertSql22 =  new StringBuffer();
//			insertSql22.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,channel_group_id)");
//			insertSql22.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,p.CHANNEL_GROUP_ID");
//			insertSql22.append(" from t_ploy p,t_channel_group_npvr cg");
//			insertSql22.append(" where p.CHANNEL_GROUP_ID=cg.ID and p.PLOY_ID= ").append(ployId);
//			insertSql22.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
//			insertSql22.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
//			insertSql22.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
//			insertSql22.append(" and o.ORDER_CODE='").append(orderCode).append("')");
//			this.executeBySQL(insertSql22.toString(), null);
			StringBuffer insertSql22 =  new StringBuffer();
			insertSql22.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,channel_group_id)");
			insertSql22.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,p.CHANNEL_GROUP_ID");
			insertSql22.append(" from t_ploy p left JOIN t_channel_group_npvr cg");
			insertSql22.append(" ON p.CHANNEL_GROUP_ID=cg.ID where p.PLOY_ID= ").append(ployId);
			insertSql22.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql22.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
			//insertSql22.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
			insertSql22.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
			insertSql22.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql22.toString(), null);
		//}
		//未设置的时段、精准数据到临时表
//		StringBuffer insertSql3 =  new StringBuffer();
//		insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
//		insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME");
//		insertSql3.append(" from t_ploy p,t_precise_match pm ");
//		insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.PLOY_ID= ").append(ployId);
//		insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
//		insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
//		insertSql3.append(" and rel.precise_id = pm.id ");
//		insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
//		this.executeBySQL(insertSql3.toString(), null);
//		
	}
	public void insertLookRepalyOrderMateRelTmp2(String orderCode,int ployId,int positionId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
			StringBuffer insertSql22 =  new StringBuffer();
			insertSql22.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,channel_group_id)");
			insertSql22.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,p.CHANNEL_GROUP_ID");
			insertSql22.append(" from t_ploy p left JOIN t_channel_group_npvr cg");
			insertSql22.append(" ON p.CHANNEL_GROUP_ID=cg.ID where p.PLOY_ID= ").append(ployId);
			insertSql22.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql22.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
			insertSql22.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id ");
			insertSql22.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql22.toString(), null);

	}
	/**
	 * 添加插播广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertInstreamOrderMateRelTmp(String orderCode,int ployId,int instreamNumber){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//新增已有订单和素材关系数据到临时表
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel_tmp(order_code,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,channel_group_id)");
		insertSql.append(" select o.order_code,rel.MATE_ID,rel.PLAY_LOCATION,rel.is_hd,rel.poll_index,");
		insertSql.append("rel.precise_id,rel.TYPE,rel.start_time,rel.end_time,rel.area_code,rel.channel_group_id ");
		insertSql.append("from t_order_mate_rel rel, t_order o ");
		insertSql.append(" where rel.order_id=o.id and o.order_code='").append(orderCode).append("'");
		this.executeBySQL(insertSql.toString(), null);
		
		String playLocation = "";
		//将策略中的时段添加到临时表
		for(int i=0;i<instreamNumber;i++){
			playLocation = i+"/"+instreamNumber;
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,play_location)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,'").append(playLocation).append("'");
			insertSql2.append(" from t_ploy p");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
			//insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
			insertSql2.append(" and rel.precise_id = p.id and rel.play_location= '").append(playLocation).append("'");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}
		
		//未设置的时段、精准数据到临时表
		for(int i=0;i<instreamNumber;i++){
			playLocation = i+"/"+instreamNumber;
			StringBuffer insertSql3 =  new StringBuffer();
			insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,play_location)");
			insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME,'").append(playLocation).append("'");
			insertSql3.append(" from t_ploy p,t_precise_match pm ");
			insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.PLOY_ID= ").append(ployId);
			insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql3.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
			//insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
			insertSql3.append(" and rel.precise_id = pm.id and rel.play_location= '").append(playLocation).append("'");
			insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql3.toString(), null);
		}
	}
	public void insertInstreamOrderMateRelTmp2(String orderCode,int ployId,int instreamNumber){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		String playLocation = "";
		//将策略中的时段添加到临时表
		for(int i=0;i<instreamNumber;i++){
			playLocation = i+"/"+instreamNumber;
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,play_location)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,'").append(playLocation).append("'");
			insertSql2.append(" from t_ploy p");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
			insertSql2.append(" and rel.precise_id = p.id and rel.play_location= '").append(playLocation).append("'");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}
		
		//未设置的时段、精准数据到临时表
		for(int i=0;i<instreamNumber;i++){
			playLocation = i+"/"+instreamNumber;
			StringBuffer insertSql3 =  new StringBuffer();
			insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,play_location)");
			insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME,'").append(playLocation).append("'");
			insertSql3.append(" from t_ploy p,t_precise_match pm ");
			insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.PLOY_ID= ").append(ployId);
			insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
			insertSql3.append(" and rel.precise_id = pm.id and rel.play_location= '").append(playLocation).append("'");
			insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql3.toString(), null);
		}
	}
	
	/**
	 * 轮询菜单图片广告位中的订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 * @param loops
	 */
	public void loopMenuPosition(String orderCode,int ployId,int loops){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//新增订单和素材临时关系数据
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel_tmp(order_code,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,channel_group_id)");
		insertSql.append(" select o.order_code,rel.MATE_ID,rel.PLAY_LOCATION,rel.is_hd,rel.poll_index,");
		insertSql.append("rel.precise_id,rel.TYPE,rel.start_time,rel.end_time,rel.area_code,rel.channel_group_id ");
		insertSql.append("from t_order_mate_rel rel, t_order o ");
		insertSql.append(" where rel.order_id=o.id and o.order_code='").append(orderCode).append("'");
		this.executeBySQL(insertSql.toString(), null);
		
		//添加区域中的数据到订单和素材临时关系表
		for(int i=1;i<=loops;i++){
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,poll_index,precise_id,type,start_time,end_time,area_code,channel_group_id)");
			insertSql2.append(" select '").append(orderCode).append("',").append(i).append(",p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
			insertSql2.append(" from t_ploy p,t_release_area ra ");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))" +
					"and rel.area_code=ra.AREA_CODE");
			//insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME and rel.area_code=ra.AREA_CODE");
			insertSql2.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id and rel.poll_index=").append(i);
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}
	}
	public void loopMenuPosition2(String orderCode,int ployId,int loops){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//新增订单和素材临时关系数据
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel_tmp(order_code,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,channel_group_id)");
		insertSql.append(" select o.order_code,rel.MATE_ID,rel.PLAY_LOCATION,rel.is_hd,rel.poll_index,");
		insertSql.append("rel.precise_id,rel.TYPE,rel.start_time,rel.end_time,rel.area_code,rel.channel_group_id ");
		insertSql.append("from t_order_mate_rel rel, t_order o ");
		insertSql.append(" where rel.order_id=o.id and o.order_code='").append(orderCode).append("'");
		this.executeBySQL(insertSql.toString(), null);
		
		//添加区域中的数据到订单和素材临时关系表
		for(int i=1;i<=loops;i++){
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,poll_index,precise_id,type,start_time,end_time,area_code,channel_group_id)");
			insertSql2.append(" select '").append(orderCode).append("',").append(i).append(",p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
			insertSql2.append(" from t_ploy p,t_release_area ra ");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME and rel.area_code=ra.AREA_CODE");
			insertSql2.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id and rel.poll_index=").append(i);
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}
	}
	
	
	public void multiposition(String orderCode, int ployId, int positionCount) {
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//新增订单和素材临时关系数据
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel_tmp(order_code,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,channel_group_id)");
		insertSql.append(" select o.order_code,rel.MATE_ID,rel.PLAY_LOCATION,rel.is_hd,rel.poll_index,");
		insertSql.append("rel.precise_id,rel.TYPE,rel.start_time,rel.end_time,rel.area_code,rel.channel_group_id ");
		insertSql.append("from t_order_mate_rel rel, t_order o ");
		insertSql.append(" where rel.order_id=o.id and o.order_code='").append(orderCode).append("'");
		this.executeBySQL(insertSql.toString(), null);
		
		
		List<PloyBackup> ployList= ployDao.getPloyListByPloyID(ployId);
		boolean isQuanSheng=false;
		if(null != ployList && ployList.size() > 0){
			if(152000000000L == ployList.get(0).getAreaId() || 0L == ployList.get(0).getAreaId()){
				isQuanSheng=true;
			}
		}
		
		//添加区域中的数据到订单和素材临时关系表
		for(int i=1;i<=positionCount;i++){
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,poll_index,precise_id,type,start_time,end_time,area_code,channel_group_id)");
			insertSql2.append(" select '").append(orderCode).append("',").append(i).append(",p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE,p.CHANNEL_GROUP_ID");
			insertSql2.append(" from t_ploy p,t_release_area ra ");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			//选择了地市，则只插入该地市的，否则默认全省，则插入18个地市，
			if(!isQuanSheng){
				insertSql2.append(" and p.AREA_ID=ra.AREA_CODE");
			}
			insertSql2.append(" and ra.AREA_CODE <> '152000000000' ");
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
					"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))" +
					"and rel.area_code=ra.AREA_CODE");
			insertSql2.append(" and rel.channel_group_id=p.CHANNEL_GROUP_ID and rel.precise_id = p.id and rel.poll_index=").append(i);
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}
	}

	/**
	 * 添加回看回放暂停广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertPauseOrderMateRelTmp(String orderCode,int ployId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//新增已有订单和素材关系数据到临时表
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel_tmp(order_code,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,channel_group_id)");
		insertSql.append(" select o.order_code,rel.MATE_ID,rel.PLAY_LOCATION,rel.is_hd,rel.poll_index,");
		insertSql.append("rel.precise_id,rel.TYPE,rel.start_time,rel.end_time,rel.area_code,rel.channel_group_id ");
		insertSql.append("from t_order_mate_rel rel, t_order o ");
		insertSql.append(" where rel.order_id=o.id and o.order_code='").append(orderCode).append("'");
		this.executeBySQL(insertSql.toString(), null);
		
		//将策略中的时段添加到临时表
		StringBuffer insertSql2 =  new StringBuffer();
		insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME");
		insertSql2.append(" from t_ploy p");
		insertSql2.append(" where p.PLOY_ID= ").append(ployId);
		insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql2.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
				"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
		//insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql2.append(" and rel.precise_id = p.id ");
		insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql2.toString(), null);
		
		//未设置的时段、精准数据到临时表
		StringBuffer insertSql3 =  new StringBuffer();
		insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME");
		insertSql3.append(" from t_ploy p,t_precise_match pm ");
		insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.PLOY_ID= ").append(ployId);
		insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql3.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
				"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
		//insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql3.append(" and rel.precise_id = pm.id ");
		insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql3.toString(), null);
	}
	public void insertPauseOrderMateRelTmp2(String orderCode,int ployId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//将策略中的时段添加到临时表
		StringBuffer insertSql2 =  new StringBuffer();
		insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME");
		insertSql2.append(" from t_ploy p");
		insertSql2.append(" where p.PLOY_ID= ").append(ployId);
		insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql2.append(" and rel.precise_id = p.id ");
		insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql2.toString(), null);
		
		//未设置的时段、精准数据到临时表
		StringBuffer insertSql3 =  new StringBuffer();
		insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME");
		insertSql3.append(" from t_ploy p,t_precise_match pm ");
		insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.PLOY_ID= ").append(ployId);
		insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql3.append(" and rel.precise_id = pm.id ");
		insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql3.toString(), null);
	}
	/**
	 * 添加请求式广告订单和素材临时关系数据
	 * @param orderCode
	 * @param ployId
	 */
	public void insertReqOrderMateRelTmp(String orderCode,int ployId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//新增已有订单和素材关系数据到临时表
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel_tmp(order_code,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,channel_group_id)");
		insertSql.append(" select o.order_code,rel.MATE_ID,rel.PLAY_LOCATION,rel.is_hd,rel.poll_index,");
		insertSql.append("rel.precise_id,rel.TYPE,rel.start_time,rel.end_time,rel.area_code,rel.channel_group_id ");
		insertSql.append("from t_order_mate_rel rel, t_order o ");
		insertSql.append(" where rel.order_id=o.id and o.order_code='").append(orderCode).append("'");
		this.executeBySQL(insertSql.toString(), null);
		
		//将策略中的时段添加到临时表
		StringBuffer insertSql2 =  new StringBuffer();
		insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME");
		insertSql2.append(" from t_ploy p");
		insertSql2.append(" where p.PLOY_ID= ").append(ployId);
		insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql2.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
				"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
		//insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql2.append(" and rel.precise_id = p.id ");
		insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql2.toString(), null);
		
		//未设置的时段、精准数据到临时表
		StringBuffer insertSql3 =  new StringBuffer();
		insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME");
		insertSql3.append(" from t_ploy p,t_precise_match pm ");
		insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.PLOY_ID= ").append(ployId);
		insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql2.append(" AND (rel.start_time = p.START_TIME 	AND rel.end_time = p.END_TIME " +
				"  or (rel.start_time = '00:00:00' and rel.end_time = '23:59:59' and p.START_TIME='0' and p.END_TIME='0'))");
		//insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql3.append(" and rel.precise_id = pm.id ");
		insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql3.toString(), null);
	}
	public void insertReqOrderMateRelTmp2(String orderCode,int ployId){
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		
		//将策略中的时段添加到临时表
		StringBuffer insertSql2 =  new StringBuffer();
		insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME");
		insertSql2.append(" from t_ploy p");
		insertSql2.append(" where p.PLOY_ID= ").append(ployId);
		insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql2.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql2.append(" and rel.precise_id = p.id ");
		insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql2.toString(), null);
		
		//未设置的时段、精准数据到临时表
		StringBuffer insertSql3 =  new StringBuffer();
		insertSql3.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time)");
		insertSql3.append(" select '").append(orderCode).append("',pm.id,0,p.START_TIME,p.END_TIME");
		insertSql3.append(" from t_ploy p,t_precise_match pm ");
		insertSql3.append(" where p.PLOY_ID=pm.PLOY_ID and p.PLOY_ID= ").append(ployId);
		insertSql3.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
		insertSql3.append(" and rel.start_time=p.START_TIME and rel.end_time=p.END_TIME ");
		insertSql3.append(" and rel.precise_id = pm.id ");
		insertSql3.append(" and o.ORDER_CODE='").append(orderCode).append("')");
		this.executeBySQL(insertSql3.toString(), null);
	}
	
	
	
	@Override
	public void insertBootOrderMateRelTmp(String orderCode, int ployId) {
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//新增订单和素材临时关系数据
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel_tmp(order_code,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,channel_group_id)");
		insertSql.append(" select o.order_code,rel.MATE_ID,rel.PLAY_LOCATION,rel.is_hd,rel.poll_index,");
		insertSql.append("rel.precise_id,rel.TYPE,rel.start_time,rel.end_time,rel.area_code,rel.channel_group_id ");
		insertSql.append("from t_order_mate_rel rel, t_order o ");
		insertSql.append(" where rel.order_id=o.id and o.order_code='").append(orderCode).append("'");
		this.executeBySQL(insertSql.toString(), null);
		
		List<PloyBackup> ployList= ployDao.getPloyListByPloyID(ployId);
		boolean isQuanSheng=false;
		boolean isDefault = false;
		if(null != ployList && ployList.size() > 0){
			if(152000000000l == ployList.get(0).getAreaId()){
				isQuanSheng=true;
			}
			if("1".equals(ployList.get(0).getDefaultstart())){
				isDefault = true;
			}
		}
		
		//添加区域中的数据到订单和素材临时关系表
		if(isDefault){
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE");
			insertSql2.append(" from t_ploy p, t_release_area ra ");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			//选择了地市，则只插入该地市的，否则默认全省，则插入18个地市，
			if(!isQuanSheng){
				insertSql2.append(" and p.AREA_ID=ra.AREA_CODE");
			}
			insertSql2.append(" and ra.AREA_CODE <> '152000000000' ");
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" and rel.area_code=p.AREA_ID");
			insertSql2.append(" and rel.area_code = ra.AREA_CODE");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}else{
			for(int i=0; i<24; i++){
				StringBuffer insertSql2 =  new StringBuffer();
				insertSql2.append("insert into t_order_mate_rel_tmp(order_code,PLAY_LOCATION,precise_id,type,start_time,end_time,area_code)");
				insertSql2.append(" select '").append(orderCode).append("',").append(i).append(",p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE");
				insertSql2.append(" from t_ploy p, t_release_area ra ");
				insertSql2.append(" where p.PLOY_ID= ").append(ployId);
				//选择了地市，则只插入该地市的，否则默认全省，则插入18个地市，
				if(!isQuanSheng){
					insertSql2.append(" and p.AREA_ID=ra.AREA_CODE");
				}
				insertSql2.append(" and ra.AREA_CODE <> '152000000000' ");
				insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
				insertSql2.append(" and rel.area_code=p.AREA_ID");
				insertSql2.append(" and rel.PLAY_LOCATION=").append(i);
				insertSql2.append(" and rel.area_code = ra.AREA_CODE");
				insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
				this.executeBySQL(insertSql2.toString(), null);
			}
		}
	}
	public void insertBootOrderMateRelTmp2(String orderCode, int ployId) {
		//删除订单和素材临时关系数据
		delOrderMateRelTmp(orderCode);
		
		//新增订单和素材临时关系数据		
		List<PloyBackup> ployList= ployDao.getPloyListByPloyID(ployId);
		boolean isQuanSheng=false;
		boolean isDefault = false;
		if(null != ployList && ployList.size() > 0){
			if(152000000000l == ployList.get(0).getAreaId()){
				isQuanSheng=true;
			}
			if("1".equals(ployList.get(0).getDefaultstart())){
				isDefault = true;
			}
		}
		
		//添加区域中的数据到订单和素材临时关系表
		if(isDefault){
			StringBuffer insertSql2 =  new StringBuffer();
			insertSql2.append("insert into t_order_mate_rel_tmp(order_code,precise_id,type,start_time,end_time,area_code)");
			insertSql2.append(" select '").append(orderCode).append("',p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE");
			insertSql2.append(" from t_ploy p, t_release_area ra ");
			insertSql2.append(" where p.PLOY_ID= ").append(ployId);
			//选择了地市，则只插入该地市的，否则默认全省，则插入18个地市，
			if(!isQuanSheng){
				insertSql2.append(" and p.AREA_ID=ra.AREA_CODE");
			}
			insertSql2.append(" and ra.AREA_CODE <> '152000000000' ");
			insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
			insertSql2.append(" and rel.area_code=p.AREA_ID");
			insertSql2.append(" and rel.area_code = ra.AREA_CODE");
			insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
			this.executeBySQL(insertSql2.toString(), null);
		}else{
			for(int i=0; i<24; i++){
				StringBuffer insertSql2 =  new StringBuffer();
				insertSql2.append("insert into t_order_mate_rel_tmp(order_code,PLAY_LOCATION,precise_id,type,start_time,end_time,area_code)");
				insertSql2.append(" select '").append(orderCode).append("',").append(i).append(",p.id,1,p.START_TIME,p.END_TIME,ra.AREA_CODE");
				insertSql2.append(" from t_ploy p, t_release_area ra ");
				insertSql2.append(" where p.PLOY_ID= ").append(ployId);
				//选择了地市，则只插入该地市的，否则默认全省，则插入18个地市，
				if(!isQuanSheng){
					insertSql2.append(" and p.AREA_ID=ra.AREA_CODE");
				}
				insertSql2.append(" and ra.AREA_CODE <> '152000000000' ");
				insertSql2.append(" and not exists (select * from t_order_mate_rel rel,t_order o where rel.order_id=o.id");
				insertSql2.append(" and rel.area_code=p.AREA_ID");
				insertSql2.append(" and rel.PLAY_LOCATION=").append(i);
				insertSql2.append(" and rel.area_code = ra.AREA_CODE");
				insertSql2.append(" and o.ORDER_CODE='").append(orderCode).append("')");
				this.executeBySQL(insertSql2.toString(), null);
			}
		}
	}

	/**
	 * 修改全时段的开始、结束时段
	 */
	public void updateAllTimeOrderMateRelTmp(){
		String updateSql = "update t_order_mate_rel_tmp set start_time='00:00:00',end_time='23:59:59' where start_time='0' or start_time is null";
		this.executeBySQL(updateSql, null);
	}
	
	/**
	 * 保存订单和素材临时数据
	 * @param ids
	 * @param mateId
	 */
	public void saveOrderMateRelTmp(String ids, Integer mateId){
		
		String updateSql = "update t_order_mate_rel_tmp set mate_id="+mateId + " where id in ("+ids+")";
		this.executeBySQL(updateSql, null);
		
	}	
	
	@Override
	public void saveBootOrderMateRelTmp(Integer mateId, String orderCode,String locations, String areas) {
		String updateSql = "update t_order_mate_rel_tmp set mate_id="+mateId + " where ORDER_CODE = '" + orderCode
				+ "' AND PLAY_LOCATION IN( " + locations + ") AND area_code IN(" + areas + ")";
		this.executeBySQL(updateSql, null);
	}
	
	

	@Override
	public void saveDefaultBootOrderMateRelTmp(Integer mateId, String orderCode, String areas) {
		String updateSql = "update t_order_mate_rel_tmp set mate_id="+mateId + " where ORDER_CODE = '" + orderCode
				+ "' AND area_code IN(" + areas + ")";
		this.executeBySQL(updateSql, null);
	}

	/**
	 * 将临时订单和素材关系数据保存到正式表
	 * @param orderId
	 * @param orderCode
	 */
	public void saveOrderMaterialRelationFromTmp(Integer orderId, String orderCode){
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into t_order_mate_rel(order_id,mate_id,play_location,is_hd,poll_index,precise_id,");
		insertSql.append("type,start_time,end_time,area_code,channel_group_id,PRECISETYPE)");
		insertSql.append(" select ").append(orderId).append(",tmp.mate_id,tmp.play_location,tmp.is_hd,tmp.poll_index,");
		insertSql.append(" tmp.precise_id,tmp.TYPE,tmp.start_time,tmp.end_time,tmp.area_code,tmp.channel_group_id,tmp.PRECISETYPE ");
		insertSql.append(" from t_order_mate_rel_tmp tmp,t_resource r ");
		insertSql.append(" where tmp.mate_id=r.id and tmp.order_code='").append(orderCode).append("' ");
		this.executeBySQL(insertSql.toString(), null);
	}
	
	/**
	 * 查询订单和素材临时关系数据
	 * @param orderCode
	 * @return
	 */
	public List<OrderMaterialRelationTmp> getOrderMaterialRelationTmpList(String orderCode){
		StringBuffer sql = new StringBuffer();
		sql.append("select tmp.id,tmp.order_code,tmp.mate_id,tmp.play_location,tmp.is_hd,tmp.poll_index,tmp.precise_id,");
		sql.append(" tmp.type,tmp.start_time,tmp.end_time,tmp.area_code,tmp.channel_group_id");
		sql.append(" from t_order_mate_rel_tmp tmp,t_resource r where tmp.mate_id=r.id ");
		if(StringUtils.isNotBlank(orderCode)){
			sql.append(" and tmp.order_code='").append(orderCode).append("'");
		}
		sql.append(" order by tmp.start_time ");
		List<OrderMaterialRelationTmp> relList = getOMTmpList(this.getDataBySql(sql.toString(), null));
		return relList;
	}
	
	private List<OrderMaterialRelationTmp> getOMTmpList(List<?> resultList) {
		List<OrderMaterialRelationTmp> list = new ArrayList<OrderMaterialRelationTmp>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrderMaterialRelationTmp rel = new OrderMaterialRelationTmp();
			rel.setId(toInteger(obj[0]));
			rel.setOrderCode((String)obj[1]);
			rel.setMateId(toInteger(obj[2]));
			rel.setPlayLocation((String)obj[3]);
			rel.setIsHD(toInteger(obj[4]));
			rel.setPollIndex(toInteger(obj[5]));
			rel.setPreciseId(toInteger(obj[6]));
			rel.setType(toInteger(obj[7]));
			rel.setStartTime((String)obj[8]);
			rel.setEndTime((String)obj[9]);
			rel.setAreaCode((String)obj[10]);
			rel.setChannelGroupId(toInteger(obj[11]));
			list.add(rel);
		}
		return list;
	}

	public String queryResourcePath(String metaId) {
		String sql = "select t.RESOURCE_TYPE,t.RESOURCE_ID from t_resource t where t.ID=? ";
		List list = this.getDataBySql(sql,new Object[]{Integer.parseInt(metaId.trim())});
		Object[] obj = (Object[]) list.get(0);
		Integer type=toInteger(obj[0]);
		Integer resourceId=toInteger(obj[1]);
		if(type==0){
			String metaSql = "select t.NAME,t.FORMAL_FILE_PATH from t_image_meta t where t.ID=? ";
			List resultlist1 = this.getDataBySql(metaSql,new Object[]{resourceId});
			Object[] obj1 = (Object[]) resultlist1.get(0);
			String path=(String)obj1[1];
			String imaname=(String)obj1[0];
			return path+"/"+imaname;
		}else if(type==1){
			String metaSql = "select t.NAME,t.FORMAL_FILE_PATH from t_video_meta t where t.ID=? ";
			List resultlist2 = this.getDataBySql(metaSql,new Object[]{resourceId});
			Object[] obj2 = (Object[]) resultlist2.get(0);
			String path=((String)obj2[1]).replace("/root", "");//用vlc播放视频路径有/root无法显示
			String imaname=(String)obj2[0];
			return path+"/"+imaname;
		}else if(type==2){
			String metaSql = "select t.URL from t_text_meta t where t.ID=? ";
			List resultlist3 = this.getDataBySql(metaSql,new Object[]{resourceId});
			Object obj3 = (Object) resultlist3.get(0);
			String path=(String)obj3;
			return path;	
		}else if(type==4){
			String metaSql = "select t.NAME,t.FORMAL_FILE_PATH from t_image_meta t where t.ID=? ";
			List resultlist1 = this.getDataBySql(metaSql,new Object[]{resourceId});
			Object[] obj1 = (Object[]) resultlist1.get(0);
			String path=(String)obj1[1];
			String imaname=(String)obj1[0];
			return (path+"/"+imaname).replace(".zip", ".jpg");
		}
		return null;
	}
	public String queryResourcePath2(String metaId) {
		String sql = "select t.RESOURCE_TYPE,t.RESOURCE_ID from t_resource t where t.ID=? ";
		List list = this.getDataBySql(sql,new Object[]{Integer.parseInt(metaId.trim())});
		Object[] obj = (Object[]) list.get(0);
		Integer type=toInteger(obj[0]);
		Integer resourceId=toInteger(obj[1]);
		if(type==0){
			String metaSql = "select t.NAME,t.FORMAL_FILE_PATH from t_image_meta t where t.ID=? ";
			List resultlist1 = this.getDataBySql(metaSql,new Object[]{resourceId});
			Object[] obj1 = (Object[]) resultlist1.get(0);
			String path=(String)obj1[1];
			String imaname=(String)obj1[0];
			return imaname;
		}else if(type==1){
			String metaSql = "select t.NAME,t.FORMAL_FILE_PATH from t_video_meta t where t.ID=? ";
			List resultlist2 = this.getDataBySql(metaSql,new Object[]{resourceId});
			Object[] obj2 = (Object[]) resultlist2.get(0);
			String path=((String)obj2[1]).replace("/root", "");//用vlc播放视频路径有/root无法显示
			String imaname=(String)obj2[0];
			return imaname;
		}else if(type==2){
			String metaSql = "select t.URL from t_text_meta t where t.ID=? ";
			List resultlist3 = this.getDataBySql(metaSql,new Object[]{resourceId});
			Object obj3 = (Object) resultlist3.get(0);
			String path=(String)obj3;
			return path;	
		}else if(type==4){
			String metaSql = "select t.NAME,t.FORMAL_FILE_PATH from t_image_meta t where t.ID=? ";
			List resultlist1 = this.getDataBySql(metaSql,new Object[]{resourceId});
			Object[] obj1 = (Object[]) resultlist1.get(0);
			String path=(String)obj1[1];
			String imaname=(String)obj1[0];
			return imaname.replace(".zip", ".jpg");
		}
		return null;
	}
	

	@Override
	public List getSelectedLoopPicNumList(String orderCode) {
		String sql = "SELECT COUNT(1) FROM t_order_mate_rel_tmp WHERE ORDER_CODE = ? AND !ISNULL(MATE_ID) GROUP BY area_code, start_time, end_time";
		List result = getDataBySql(sql, new Object[]{orderCode});
		return result;
	}
	
	@Override
	public List valiIsHasSuCai(String orderCode) {
		String sql = "SELECT COUNT(1) FROM t_order_mate_rel_tmp WHERE ORDER_CODE = ? AND !ISNULL(MATE_ID) ";
		List result = getDataBySql(sql, new Object[]{orderCode});
		return result;
	}
	
	
	
	
	
}
