package com.dvnchina.advertDelivery.order.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.order.bean.playlist.OrderBean;
import com.dvnchina.advertDelivery.order.bean.playlist.PloyBean;
import com.dvnchina.advertDelivery.order.dao.PlayListDao;
import com.dvnchina.advertDelivery.sysconfig.bean.ChannelInfo;

public class PlayListDaoImpl extends JdbcDaoSupport implements PlayListDao {

//	public OrderBean getOrderById(Integer orderId) {
//		
//		StringBuffer sql = new StringBuffer();
//		sql.append("select o.id,o.order_code,o.ploy_id,o.start_time,o.end_time,o.contract_id,o.position_id,");
//		sql.append(" pp.position_package_code,pp.position_package_type,ad.position_code,ad.position_package_id,ad.is_hd,ad.is_loop,");
//		sql.append(" ad.text_rule_id,ad.is_channel,ad.is_freq,ad.is_playback,ad.is_column,ad.is_lookback_product,ad.is_asset,ad.is_follow_asset,");
//		sql.append(" p.start_time pStart,p.end_time pEnd,p.userindustrys,p.userlevels,p.ploy_number,p.tvn_number,p.area_id");
//		sql.append(" from t_order o,t_advertposition ad,t_position_package pp,t_ploy p,");
//		sql.append(" (select distinct(ploy_id),max(id) pId from t_ploy group by ploy_id) pl");
//		sql.append(" where p.id = pl.pId and o.ploy_id = pl.ploy_id and o.position_id = ad.id ");
//		sql.append(" and ad.position_package_id = pp.id and o.id = ?");
//		
//		OrderBean order = getJdbcTemplate().queryForObject(sql.toString(),
//				new Object[] { orderId }, new OrderBean());
//		return order;
//	}
	
	public OrderBean getOrderById(Integer orderId) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select o.id,o.order_code,o.ploy_id,o.start_time,o.end_time,o.contract_id,o.position_id,o.order_type,");
		sql.append(" o.user_number,o.questionnaire_number,o.integral_ratio,");
		sql.append(" pp.position_package_code,pp.position_package_type,ad.position_code,ad.position_package_id,ad.is_hd,ad.is_loop,");
		sql.append(" ad.text_rule_id,ad.is_channel,ad.is_freq,ad.is_playback,ad.is_column,ad.is_lookback_product,ad.is_asset,ad.is_follow_asset");
		sql.append(" from t_order o,t_advertposition ad,t_position_package pp");
		sql.append(" where o.position_id = ad.id ");
		sql.append(" and ad.position_package_id = pp.id and o.id = ?");
		
		OrderBean order = getJdbcTemplate().queryForObject(sql.toString(),
				new Object[] { orderId }, new OrderBean());
		if(order.getOrderType().intValue() == 1){
			//请求式订单，将策略信息查询出来
			order.setPloyList(getSubPloyListByPloy(order.getPloyId()));
		}
		return order;
	}
	
	/**
	 * 根据策略ID获取分策略信息
	 * @param ployId
	 * @return
	 */
	private List<PloyBean> getSubPloyListByPloy(Integer ployId){
		String sql = "select p.start_time,p.end_time,p.userindustrys,p.userlevels,p.ploy_number,p.tvn_number,p.area_id" +
				",p.channel_group_id  from t_ploy p  where  p.ploy_id=? order by p.start_time asc,p.priority desc " ;
		List<PloyBean> list = getJdbcTemplate().query(sql,
				new Object[] { ployId }, new RowMapper<PloyBean>() {
					@Override
					public PloyBean mapRow(ResultSet rs, int num)
							throws SQLException {
						PloyBean p = new PloyBean();
						p.setStartTime(rs.getString("start_time"));
						p.setEndTime(rs.getString("end_time"));
						p.setUserIndustrys(rs.getString("userindustrys"));
						p.setUserLevels(rs.getString("userlevels"));
						p.setPloyNumber(rs.getInt("ploy_number"));
						p.setTvnNumber(rs.getString("tvn_number"));
						p.setAreaId(rs.getInt("area_id"));
						p.setChannelGroupId(rs.getInt("channel_group_id"));
						p.setServiceIdList(getServiceIdByGroupIds(rs.getInt("channel_group_id")+""));
						return p;
					}
				});

		return list;
	}

	/**
	 * 根据策略ID获取区域、serviceId列表
	 * @param ployId
	 * @return
	 */
//	public List<PloyBean> getPloyChannelList(Integer ployId){
//		String sql = "select distinct p.area_id,p.channel_id,c.service_id from t_ploy p left join t_channelinfo c on p.channel_id=c.channel_id " +
//		" where  p.ploy_id=? order by p.area_id ";
//		List<PloyBean> list = getJdbcTemplate().query(sql,
//				new Object[] { ployId }, new RowMapper<PloyBean>() {
//					@Override
//					public PloyBean mapRow(ResultSet rs, int num)
//							throws SQLException {
//						PloyBean p = new PloyBean();
//						p.setAreaId(rs.getInt("area_id"));
//						p.setChannelId(rs.getInt("channel_id"));
//						p.setServiceId(rs.getString("service_id"));
//						return p;
//					}
//				});
//
//		return list;
//	}
	
	/**
	 * 根据策略ID获取区域、serviceId列表
	 * @param ployId
	 * @return
	 */
//	public List<PloyBean> getPloyNpvrList(Integer ployId){
////		String sql = "select distinct p.area_id,p.channel_id,c.service_id from t_ploy p, t_channelinfo_npvr c where p.channel_id=c.channel_id(+) " +
////				" and  p.ploy_id=? order by p.area_id ";
//		String sql = "select distinct p.area_id,p.channel_id,c.service_id from t_ploy p left join t_channelinfo_npvr c on p.channel_id=c.channel_id " +
//		" where  p.ploy_id=? order by p.area_id ";
//		List<PloyBean> list = getJdbcTemplate().query(sql,
//				new Object[] { ployId }, new RowMapper<PloyBean>() {
//					@Override
//					public PloyBean mapRow(ResultSet rs, int num)
//							throws SQLException {
//						PloyBean p = new PloyBean();
//						p.setAreaId(rs.getInt("area_id"));
//						p.setChannelId(rs.getInt("channel_id"));
//						p.setServiceId(rs.getString("service_id"));
//						return p;
//					}
//				});
//
//		return list;
//	}

	/**
	 * 根据策略ID、区域ID获取频道的serviceId列表
	 */
	public List<String> getChannelByArea(Integer ployId, String areaId) {
		String sql = "select distinct c.service_id from t_ploy p,t_channelinfo c where p.channel_id=c.channel_id and ploy_id=? and area_id=? ";
		List<String> cIds = getJdbcTemplate().query(sql,
				new Object[] { ployId, areaId }, new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int num)
							throws SQLException {
						String cId = rs.getString("service_id");
						return cId;
					}
				});

		return cIds;
	}
	
	/**
	 * 根据频道ID获取serviceId列表（0获取全部）
	 * @param channelId
	 * @param channel
	 * @return
	 */
	public List<String> getDtvServiceById(String channelIds, ChannelInfo channel) {
		if(StringUtils.isEmpty(channelIds)){
			return null ;
		}
		StringBuffer sql = new StringBuffer("select distinct c.service_id from t_channelinfo c where 1=1 ");
		if(channel != null){
			if(StringUtils.isNotBlank(channel.getChannelType())){
				sql.append(" and c.channel_type='"+channel.getChannelType()+"'");
			}
		}
		if(!"0".equals(channelIds)){
			sql.append(" and c.channel_id in (");
			String[] cIds = channelIds.split(",");
			for(int i = 0;i<cIds.length;i++){
				if(i>0){
					sql.append(",");
				}
				Integer cId = Integer.parseInt(cIds[i]);
				sql.append(cId);
			}
			sql.append(")");
		}
		List<String> cIds = getJdbcTemplate().query(sql.toString(),new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int num)
							throws SQLException {
						String cId = rs.getString("service_id");
						return cId;
					}
				});

		return cIds;
	}
	
	/**
	 * 获取频道表中的所有serviceId列表
	 */
//	public List<String> getAllServiceIdList() {
//		StringBuffer sql = new StringBuffer("select c.service_id from t_channelinfo c");
//		List<String> serviceIdList = getJdbcTemplate().query(sql.toString(),new RowMapper<String>() {
//					@Override
//					public String mapRow(ResultSet rs, int num)
//							throws SQLException {
//						return rs.getString("service_id");
//					}
//				});
//
//		return serviceIdList;
//	}
	
	/**
	 * 回放根据频道ID获取serviceId
	 */
	public List<String> getPlaybackServiceId(String channelIds){
		if(StringUtils.isEmpty(channelIds)){
			return null;
		}
		StringBuffer sql = new StringBuffer("select c.service_id from t_channelinfo_npvr c where c.channel_id in (");
		String[] cIds = channelIds.split(",");
		for(int i = 0;i<cIds.length;i++){
			if(i>0){
				sql.append(",");
			}
			Integer cId = Integer.parseInt(cIds[i]);
			sql.append(cId);
		}
		sql.append(")");
		List<String> list = getJdbcTemplate().query(sql.toString(),new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int num)
					throws SQLException {
				String cId = rs.getString("service_id");
				return cId;
			}
		});

		return list;
	}
	
	/**
	 * 回看根据ID获取栏目ID
	 */
	public List<String> getLookbackCategortId(String ids){
		if(StringUtils.isEmpty(ids)){
			return null;
		}
		StringBuffer sql = new StringBuffer("select category_id from t_loopback_category where id in (");
		String[] cIds = ids.split(",");
		for(int i = 0;i<cIds.length;i++){
			if(i>0){
				sql.append(",");
			}
			Integer cId = Integer.parseInt(cIds[i]);
			sql.append(cId);
		}
		sql.append(")");
		List<String> list = getJdbcTemplate().query(sql.toString(),new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int num)
					throws SQLException {
				String cId = rs.getString("category_id");
				return cId;
			}
		});

		return list;
	}
	
	/**
	 * 根据频道组IDS获取serviceIds
	 */
	public List<String> getServiceIdByGroupIds(String groupIds){
		if(StringUtils.isEmpty(groupIds)){
			return null;
		}
		StringBuffer sql = new StringBuffer("select c.service_id from t_channel_group_ref rel, t_channelinfo c ");
		sql.append(" where rel.channel_id=c.channel_id and rel.group_id in (");
		String[] cIds = groupIds.split(",");
		for(int i = 0;i<cIds.length;i++){
			if(i>0){
				sql.append(",");
			}
			Integer cId = Integer.parseInt(cIds[i]);
			sql.append(cId);
		}
		sql.append(")");
		List<String> list = getJdbcTemplate().query(sql.toString(),new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int num)
					throws SQLException {
				String cId = rs.getString("service_id");
				return cId;
			}
		});

		return list;
	}
	
	/**
	 * 节目ID获取assetId
	 */
	public List<String> getAssetId(String ids){
		if(StringUtils.isEmpty(ids)){
			return null;
		}
		StringBuffer sql = new StringBuffer("select asset_id from t_assetinfo where id in (");
		String[] cIds = ids.split(",");
		for(int i = 0;i<cIds.length;i++){
			if(i>0){
				sql.append(",");
			}
			Integer cId = Integer.parseInt(cIds[i]);
			sql.append(cId);
		}
		sql.append(")");
		List<String> list = getJdbcTemplate().query(sql.toString(),new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int num)
					throws SQLException {
				String cId = rs.getString("asset_id");
				return cId;
			}
		});

		return list;
	}

	public int getAreaSize() {
		String sql = "select count(id) from t_release_area";
		return getJdbcTemplate().queryForInt(sql);
	}

	public int getChannelSize() {
		String sql = "select count(channel_id) from t_channelinfo";
		return getJdbcTemplate().queryForInt(sql);
	}

}
