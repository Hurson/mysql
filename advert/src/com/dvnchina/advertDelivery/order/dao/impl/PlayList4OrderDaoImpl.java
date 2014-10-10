package com.dvnchina.advertDelivery.order.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.order.bean.PlayListResource;
import com.dvnchina.advertDelivery.order.bean.PutInPlayListBean;
import com.dvnchina.advertDelivery.order.bean.RequestPlayListBean;
import com.dvnchina.advertDelivery.order.dao.PlayList4OrderDao;

public class PlayList4OrderDaoImpl extends JdbcDaoSupport implements PlayList4OrderDao {

	/**
	 * 根据订单id查询投放式播出单记录
	 * */
	public PutInPlayListBean getPutInPlayListByOrderId(Integer orderId){
		
//		StringBuffer sql = new StringBuffer();
//		sql.append("select order_id,contract_id,content_type,content_id,ploy_id,position_id,characteristic_identification,s,e");
//		sql.append(" from (select distinct pg.order_id,pg.contract_id,pg.content_type,pg.content_id,");
//		sql.append(" pg.ploy_id,p.position_id,pg.characteristic_identification");
//		sql.append(" from ad_playlist_gis pg, t_ploy p");
//		sql.append(" where pg.ploy_id = p.ploy_id and order_id = ?),");
//		sql.append(" (select min(start_time) s, max(end_time) e");
//		sql.append(" from ad_playlist_gis where order_id = ?) ");
//		
//		List list = this.getDataBySql(sql.toString(), new Object[]{orderId,orderId});
//		if(list != null && list.size()>0){
//			Object[] obj = (Object[]) list.get(0);
//			PutInPlayListBean p = new PutInPlayListBean();
//			p.setOrderId(toInteger(obj[0]));
//			p.setContractId(toInteger(obj[1]));
//			p.setContentType((String)obj[2]);
//			p.setContentId((String)obj[3]);
//			p.setPloyId(toInteger(obj[4]));
//			p.setPositionId(toInteger(obj[5]));
//			p.setIdentification((String)obj[6]);
//			p.setStartTime((Date)obj[7]);
//			p.setEndTime((Date)obj[8]);
//			return p;
//		}else{
//			return null;
//		}
		
		String sql = "select * from (select distinct pl.order_id,pl.contract_id,pl.content_type,pl.content_id,pl.ploy_id,p.position_id" +
				",pl.characteristic_identification from ad_playlist_gis pl,t_ploy p where pl.ploy_id=p.ploy_id and order_id=?) t1," +
				"(select min(start_time) s,max(end_time) e from ad_playlist_gis where order_id=?) t2";
		List<PutInPlayListBean> pBeans = getJdbcTemplate().query(sql,new Object[]{orderId,orderId},new RowMapper<PutInPlayListBean>(){
			@Override
			public PutInPlayListBean mapRow(ResultSet rs, int num) throws SQLException {
				PutInPlayListBean p = new PutInPlayListBean();
				p.setOrderId(rs.getInt("order_id"));
				p.setContractId(rs.getInt("contract_id"));
				p.setContentType(rs.getString("content_type"));
				p.setContentId(rs.getString("content_id"));
				p.setPloyId(rs.getInt("ploy_id"));
				p.setPositionId(rs.getInt("position_id"));
				p.setIdentification(rs.getString("characteristic_identification"));
				p.setStartTime(rs.getTimestamp("s"));
				p.setEndTime(rs.getTimestamp("e"));
				return p;
			}
		});		
		if(pBeans.size()>0){
			return pBeans.get(0);
		}
		return null;
	}
	
	/**
	 * 根据订单id和订单类型查询请求式播出单记录
	 * */
	public List<RequestPlayListBean> getRequestPlayListByOrderId(Integer orderId) {
		String sql = "select pl.id,order_id,begin_date,end_date,begin,end,pl.ploy_id,pl.contract_id,p.position_id,pl.characteristic_identification" +
				" from ad_playlist_req pl,t_ploy p where pl.ploy_id=p.ploy_id and pl.order_id=?";
		List<RequestPlayListBean> ps = getJdbcTemplate().query(sql, new Object[]{orderId}, new RowMapper<RequestPlayListBean>() {
			@Override
			public RequestPlayListBean mapRow(ResultSet rs, int num) throws SQLException {
				RequestPlayListBean p = new RequestPlayListBean();
				p.setId(rs.getInt("id"));
				p.setOrderId(rs.getInt("order_id"));
				p.setStartDate(rs.getDate("begin_date"));
				p.setEndDate(rs.getDate("end_date"));
				p.setStartTime(rs.getString("begin"));
				p.setEndTime(rs.getString("end"));
				p.setPloyId(rs.getInt("ploy_id"));
				p.setContractId(rs.getInt("contract_id"));
				p.setPositionId(rs.getInt("position_id"));
				p.setIdentification(rs.getString("characteristic_identification"));
				return p;
			}
		});
		for(RequestPlayListBean pBean : ps){
			List<PlayListResource> resources = new ArrayList<PlayListResource>();
			String ployContent = "select content_type,content_id from ad_playlist_req_content where playlist_id=?";
			String preciseContent = "select p.precision_id,c.content_type,c.content_id from ad_playlist_req_precision p,ad_playlist_req_p_content c " +
					"where p.id=c.precision_id and p.playlist_id=?";
			List<PlayListResource> res = getJdbcTemplate().query(ployContent,new Object[]{pBean.getId()},new RowMapper<PlayListResource>(){
				@Override
				public PlayListResource mapRow(ResultSet rs, int num)
						throws SQLException {
					PlayListResource r = new PlayListResource();
					r.setPreciseId(0);
					r.setContentId(rs.getString("content_id"));
					r.setContentType(rs.getString("content_type"));
					return r;
				}
			});
			resources.addAll(res);
			List<PlayListResource> pres = getJdbcTemplate().query(preciseContent,new Object[]{pBean.getId()},new RowMapper<PlayListResource>(){
				@Override
				public PlayListResource mapRow(ResultSet rs, int num)
						throws SQLException {
					PlayListResource r = new PlayListResource();
					r.setPreciseId(rs.getInt("precision_id"));
					r.setContentId(rs.getString("content_id"));
					r.setContentType(rs.getString("content_type"));
					return r;
				}
			});
			if(pres.size()>0){
				resources.addAll(pres);
			}
			pBean.setResources(resources);
		}
		return ps;
	}
	
	/**
	 * 根据订单ID查询投放式播出单的最小开始时间
	 * */
	public Date getPutInPlayListStart(Integer orderId) {
		String sql = "select min(start_time) startTime from ad_playlist_gis where order_id=? and state="+Constant.VALID;
		try {
			Date d = getJdbcTemplate().queryForObject(sql,new Object[]{orderId},Date.class);
			return d;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 根据订单ID查询请求式播出单的开始时间
	 * 开始日期:BEGIN_DATE [Date],
	 * 开始时间:BEGIN [String]
	 * */
	public Map<String,Object> getRequestPlayListStart(Integer orderId){
		String sql = "select distinct begin_date,begin from ad_playlist_req where order_id=?";
		try {
			Map<String, Object> map = getJdbcTemplate().queryForMap(sql,new Object[]{orderId});
			return map;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 查询排除指定订单编号的投放式订单日期信息
	 * 订单主键：ORDER_ID [Integer]，
	 * 开始日期：STARTTIME [Date]，
	 * 结束日期：ENDTIME [Date]
	 * */
	public  List<Map<String,Object>> getPutInOrder(Integer ployId,Integer orderId){
		String sql = "select order_id,min(start_time) startTime,max(end_time) endTime " +
				"from ad_playlist_gis where ploy_id=? and order_id!=? group by order_id";
		return getJdbcTemplate().queryForList(sql,new Object[]{ployId,orderId});
	}
	
	/**
	 * 根据日期范围和策略id查询排除指定订单编号的请求式订单编号
	 * */
	public List<Integer> getRequestOrder(Integer ployId, Date startDate, Date endDate, Integer orderId){
		String sql = "select order_id from ad_playlist_req where ((begin_date>=? and "
	        + "begin_date<=?) or  (begin_date<=? and end_date>=?)) and ploy_id=? and order_id!=?";
			List<Integer> oIds = getJdbcTemplate().queryForList(sql,
					new Object[]{startDate,endDate,startDate,startDate,ployId,orderId},Integer.class);
			return oIds;
	}
	
	/**
	 * 根据订单ID获取订单号
	 */
	public String getOrderNoById(List<Integer> ids){
		String orderNo = "";
		StringBuffer sql = new StringBuffer(
				"select order_code from t_order where id ");
		if (ids.size() == 1) {
			sql.append("=");
			sql.append(ids.get(0));
		} else if (ids.size() > 1) {
			sql.append("in (");
			for (int i = 0; i < ids.size(); i++) {
				if (i > 0) {
					sql.append(",");
				}
				sql.append(ids.get(i));
			}
			sql.append(")");
		}
		List<Map<String, Object>> orderNos = getJdbcTemplate().queryForList(
				sql.toString());
		for (int j = 0; j < orderNos.size(); j++) {
			if(j>0){
				orderNo += ",";
			}
			orderNo += orderNos.get(j).get("ORDER_CODE");
		}
		return orderNo;
	}
	
	/**
	 * 查询投放式订单日期信息
	 * 订单主键：ORDER_ID [Integer]，
	 * 开始日期：STARTTIME [Date]，
	 * 结束日期：ENDTIME [Date]
	 * */
	public  List<Map<String,Object>> getPutInOrder(Integer ployId){
		String sql = "select order_id,min(start_time) startTime,max(end_time) endTime from ad_playlist_gis where ploy_id=? group by order_id";
		return getJdbcTemplate().queryForList(sql,new Object[]{ployId});
	}
	
	/**
	 * 根据日期范围和策略id查询请求式订单编号
	 * */
	public  List<Integer> getRequestOrder(Integer ployId, Date startDate, Date endDate){
		String sql = "select order_id from ad_playlist_req where ((begin_date>=? and "
	        + "begin_date<=?) or  (begin_date<=? and end_date>=?)) and ploy_id=?";
			List<Integer> oIds = getJdbcTemplate().queryForList(sql,new Object[]{startDate,endDate,startDate,startDate,ployId},Integer.class);
			return oIds;
	}
	
	/**
	 * 获取投放式播出单结束时间
	 * 订单主键:ORDER_ID [Integer],
	 * 结束日期时间 :ENDTIME [Date]
	 * */
	public List<Map<String, Object>> getPutInPlayListEndTime() {
		String gisSql = "select gis.order_id,max(gis.end_time) endTime from ad_playlist_gis gis,t_order o " +
				"where gis.order_id=o.id and o.state<7 and o.end_time<=? group by gis.order_id";
		Date nowDate= new Date();
        Calendar c1 =Calendar.getInstance();
        c1.setTime(nowDate);
        c1.add(Calendar.DATE,-1);
        nowDate=c1.getTime(); 
		return getJdbcTemplate().queryForList(gisSql,new Object[]{nowDate});
		
	}

	/**
	 * 获取请求式播出单结束时间
	 * 订单主键:ORDER_ID [Integer],
	 * 结束日期:END_DATE [Date],
	 * 结束时间:END [String]
	 * */
	public List<Map<String,Object>> getRequestPlayListEndDate(){
		String reqSql = "select req.order_id,req.end_date,req.end from ad_playlist_req req,t_order o " +
				"where req.order_id=o.id and o.state<7 and o.end_time<=?";
		Date nowDate= new Date();
        Calendar c1 =Calendar.getInstance();
        c1.setTime(nowDate);
        c1.add(Calendar.DATE,-1);
        nowDate=c1.getTime();
		return getJdbcTemplate().queryForList(reqSql,new Object[]{nowDate});
	}
	
	/**
	 * 批量更新投放式播出单状态
	 * */
	public void updatePutInPlayListsState(List<Integer> orderIds, int state){
		StringBuffer sql = new StringBuffer("update ad_playlist_gis set state=");
		sql.append(state);
		int size = orderIds.size();
		int num = 0;
		/**处理sql in条件超过1000抛出异常*/
		while(size>0){
			sql.append(this.getSqlStr(orderIds, num));
			num=num+1000;
			size = size-1000;
		}		
		getJdbcTemplate().update(sql.toString());
	}
	
	/**
	 * 批量更新请求式播出单状态
	 * */
	public void updateRequestPlayListsState(List<Integer> orderIds, int state){
		StringBuffer sql = new StringBuffer("update ad_playlist_req set state=");
		sql.append(state);
		int size = orderIds.size();
		int num = 0;
		/**处理sql in条件超过1000抛出异常*/
		while(size>0){
			sql.append(this.getSqlStr(orderIds, num));
			num=num+1000;
			size = size-1000;
		}
		getJdbcTemplate().update(sql.toString());
	}
	
	private StringBuffer getSqlStr(List<Integer> orderIds,int num){
		StringBuffer sql = new StringBuffer();
		for(int i = num;i<orderIds.size()&&i-num<1000;i++){
			if(i==0&&num==0){
				sql.append(" where order_id in(");
			}else if(i==num){
				sql.append(" or order_id in(");
			}
			if(i!=num){
				sql.append(",");
			}
			sql.append(orderIds.get(i));
		}
		sql.append(")");
		return sql;
	}

}
