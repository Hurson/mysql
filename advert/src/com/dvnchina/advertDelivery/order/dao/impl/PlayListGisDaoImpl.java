package com.dvnchina.advertDelivery.order.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelation;
import com.dvnchina.advertDelivery.order.bean.PlayListGis;
import com.dvnchina.advertDelivery.order.bean.playlist.PloyPlayListGisRel;
import com.dvnchina.advertDelivery.order.bean.playlist.PrecisePlayListGisRel;
import com.dvnchina.advertDelivery.order.bean.playlist.TextMate;
import com.dvnchina.advertDelivery.order.dao.PlayListGisDao;
import com.dvnchina.advertDelivery.utils.StringUtil;

public class PlayListGisDaoImpl extends PlayListDaoImpl implements
		PlayListGisDao {
	private DefaultLobHandler lobHandler;

	public DefaultLobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(DefaultLobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	/**
	 * 获取策略对应的素材信息
	 * @param orderId
	 * @return
	 */
//	public List<MaterialBean> getPloyMaterialByOrder(Integer orderId) {
//		String sql = "select rel.mate_id,rel.play_location,rel.poll_index,r.resource_type from t_order_mate_rel rel,t_resource r " +
//				"where rel.mate_id=r.id and rel.precise_id = 0 and rel.order_id=? order by rel.poll_index asc,rel.play_location";
//		List<MaterialBean> materials = getJdbcTemplate().query(sql,
//				new Object[] { orderId }, new RowMapper<MaterialBean>() {
//					@Override
//					public MaterialBean mapRow(ResultSet rs, int num)
//							throws SQLException {
//						MaterialBean m = new MaterialBean();
//						m.setId(rs.getInt("mate_id"));
//						m.setPlayLocation(rs.getString("play_location"));
//						m.setLoopNo(rs.getInt("poll_index"));
//						m.setType(rs.getInt("resource_type"));
//						getMaterialById(m);
//						return m;
//					}
//
//					@SuppressWarnings("unchecked")
//					private void getMaterialById(final MaterialBean m) {
//						switch (m.getType()) {
//						case Constant.IMAGE:
//							String imageSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
//									+ "where r.resource_id=m.id and r.id="
//									+ m.getId()
//									+ " and r.resource_type="
//									+ m.getType();
//							Map iMap = getJdbcTemplate().queryForMap(imageSql);
//							m.setPath(iMap.get("path").toString());
//							break;
//						case Constant.VIDEO:
//							String videoSql = "select CONCAT(m.video_pump_path,'/',m.name) as path from t_resource r,t_video_meta m  "
//									+ "where r.resource_id=m.id and r.id="
//									+ m.getId()
//									+ " and r.resource_type="
//									+ m.getType();
//							Map vMap = getJdbcTemplate().queryForMap(videoSql);
//							m.setPath(vMap.get("path").toString());
//							break;
//						case Constant.WRITING:
//							String textSql = "select m.id,m.name,m.content,m.url,m.action,m.duration_time,"
//									+"m.font_size,m.font_color,m.background_color,m.roll_speed,"
//									+"m.position_vertex_coordinates,m.position_width_height from t_resource r,T_TEXT_META m "
//									+ " where r.resource_id=m.id and r.id="
//									+ m.getId()
//									+ " and r.resource_type="
//									+ m.getType();
//							getJdbcTemplate().query(textSql,new RowMapper(){
//								@Override
//								public Object mapRow(ResultSet rs, int num)
//										throws SQLException {
//									TextMate text = new TextMate();
//									text.setId(rs.getInt("id"));
//									text.setName(rs.getString("name"));
//									text.setContent(rs.getBlob("content"));
//									text.setURL(rs.getString("url"));
//									text.setAction(rs.getString("action"));
//									text.setDurationTime(rs.getInt("duration_time"));
//									text.setFontSize(rs.getInt("font_size"));
//									text.setFontColor(rs.getString("font_color"));
//									text.setBkgColor(rs.getString("background_color"));
//									text.setRollSpeed(rs.getInt("roll_speed"));
//									text.setPositionVertexCoordinates(rs.getString("position_vertex_coordinates"));
//									text.setPositionWidthHeight(rs.getString("position_width_height"));
//									m.setText(text);
//									return null;
//								}
//							});
//							break;
//						case Constant.QUESTIONNAIRE:
//							String qSql = "select FORMAL_FILE_PATH from t_resource r,t_questionnaire_real q  where "
//									+ "r.resource_id=q.id and r.id="
//									+ m.getId()
//									+ " and r.resource_type="
//									+ m.getType();
//							Map qMap = getJdbcTemplate().queryForMap(qSql);
//							m.setPath(qMap.get("FORMAL_FILE_PATH").toString());
//							break;
//						}
//					}
//				});
//		return materials;
//	}
	
	/**
	 * 获取策略对应的播出单
	 * @param orderId
	 * @param positionCode
	 * @return
	 */
	public List<PloyPlayListGisRel> getPloyPlayListGisByOrder(Integer orderId,final String positionCode){
		StringBuffer sql = new StringBuffer();
		sql.append("select rel.mate_id,rel.play_location,rel.poll_index,r.resource_type,rel.precise_id,");
		sql.append(" p.ploy_id,rel.start_time,rel.end_time,rel.channel_group_id,rel.area_code,p.userindustrys,p.userlevels,p.tvn_number");
		sql.append(" from t_order_mate_rel rel,t_resource r ,t_ploy p");
		sql.append(" where rel.mate_id=r.id and rel.precise_id=p.id ");
		sql.append(" and rel.type = 1 and rel.order_id=? order by rel.area_code,p.start_time,p.priority desc,rel.poll_index asc,rel.play_location");
		List<PloyPlayListGisRel> playList = getJdbcTemplate().query(sql.toString(),
		new Object[] { orderId }, new RowMapper<PloyPlayListGisRel>() {
			@Override
			public PloyPlayListGisRel mapRow(ResultSet rs, int num)
					throws SQLException {
				PloyPlayListGisRel p = new PloyPlayListGisRel();
				p.setMateId(rs.getInt("mate_id"));
				p.setPlayLocation(rs.getString("play_location"));
				p.setPollIndex(rs.getInt("poll_index"));
				p.setResourceType(rs.getInt("resource_type"));
				p.setPreciseId(rs.getInt("precise_id"));
				p.setPloyId(rs.getInt("ploy_id"));
				if("0".equals(rs.getString("start_time"))){
					//时间段如未选，则后台默认设定时间为0，对应订单生成播出单时，时间为00:00:00-23:59:59
					p.setStartTime("00:00:00");
					p.setEndTime("23:59:59");
				}else{
					p.setStartTime(rs.getString("start_time"));
					p.setEndTime(rs.getString("end_time"));
				}
				p.setChannelGroupId(rs.getInt("channel_group_id"));
				p.setAreaCode(rs.getString("area_code"));
				p.setUserIndustrys(rs.getString("userindustrys"));
				p.setUserLevels(rs.getString("userlevels"));
				p.setTvnNumber(rs.getString("tvn_number"));
				//查询所有
				p.setLookbackCategoryIdList(getValueByIds("category_id","t_loopback_category"));
				getMaterialById(p);
				setServiceIdList(p);
				return p;
			}

			@SuppressWarnings("unchecked")
			private void getMaterialById(final PloyPlayListGisRel p) {
				switch (p.getResourceType()) {
				case Constant.IMAGE:
					String imageSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
							+ "where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map iMap = getJdbcTemplate().queryForMap(imageSql);
					p.setPath(iMap.get("path").toString());
					break;
				case Constant.VIDEO:
					String videoSql = "select CONCAT(m.video_pump_path,'/',m.name) as path from t_resource r,t_video_meta m  "
							+ "where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map vMap = getJdbcTemplate().queryForMap(videoSql);
					p.setPath(vMap.get("path").toString());
					break;
				case Constant.WRITING:
					String textSql = "select m.id,m.name,m.content,m.url,m.action,m.duration_time,"
							+"m.font_size,m.font_color,m.background_color,m.roll_speed,"
							+"m.position_vertex_coordinates,m.position_width_height from t_resource r,T_TEXT_META m "
							+ " where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					getJdbcTemplate().query(textSql,new RowMapper(){
						@Override
						public Object mapRow(ResultSet rs, int num)
								throws SQLException {
							TextMate text = new TextMate();
							text.setId(rs.getInt("id"));
							text.setName(rs.getString("name"));
							text.setContent(rs.getBlob("content"));
							text.setURL(rs.getString("url"));
							text.setAction(rs.getString("action"));
							text.setDurationTime(rs.getInt("duration_time"));
							text.setFontSize(rs.getInt("font_size"));
							text.setFontColor(rs.getString("font_color"));
							text.setBkgColor(rs.getString("background_color"));
							text.setRollSpeed(rs.getInt("roll_speed"));
							text.setPositionVertexCoordinates(rs.getString("position_vertex_coordinates"));
							text.setPositionWidthHeight(rs.getString("position_width_height"));
							p.setText(text);
							return null;
						}
					});
					break;
				case Constant.QUESTIONNAIRE:
					String qSql = "select FORMAL_FILE_PATH from t_resource r,t_questionnaire_real q  where "
							+ "r.resource_id=q.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map qMap = getJdbcTemplate().queryForMap(qSql);
					p.setPath(qMap.get("FORMAL_FILE_PATH").toString());
					break;
				case Constant.ZIP:
					String zipSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
							+ "where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map zMap = getJdbcTemplate().queryForMap(zipSql);
					p.setPath(zMap.get("path").toString());
					break;
				}
			}
			
			private void setServiceIdList(final PloyPlayListGisRel p){
				if(p.getChannelGroupId() != null){
					StringBuffer sql = new StringBuffer();
					if(p.getChannelGroupId().intValue() == 0){
						//组ID位0，取所有频道
						if("02074".equals(positionCode) || "02084".equals(positionCode) || "02094".equals(positionCode)){
							sql.append("select c.service_id from t_channelinfo_npvr c where 1=1 ");
						}else{
							sql.append("select c.service_id from t_channelinfo c where 1=1 ");
						}
					}else{
						if("02074".equals(positionCode) || "02084".equals(positionCode) || "02094".equals(positionCode)){
							sql.append("select c.service_id from t_channel_group_ref_npvr rel, t_channelinfo_npvr c ");
						}else{
							sql.append("select c.service_id from t_channel_group_ref rel, t_channelinfo c ");
						}
						sql.append(" where rel.channel_id=c.channel_id");
						sql.append(" and rel.group_id = ").append(p.getChannelGroupId());
					}
					if("02061".equals(positionCode) || "02062".equals(positionCode)){
						//广播收听背景广告，只查询音频类频道
						sql.append(" and c.channel_type='音频直播类业务' ");
					}
					List<String> list = getJdbcTemplate().query(sql.toString(),new RowMapper<String>() {
						public String mapRow(ResultSet rs, int num)
								throws SQLException {
							String cId = rs.getString("service_id");
							return cId;
						}
					});
					p.setServiceIdList(list);
				}
			}
			
			/**
			 * 根据ids获取对应的值
			 * @param field
			 * @param table
			 * @param ids
			 * @return
			 */
			private List<String> getValueByIds(final String field,String table){
				StringBuffer vSql = new StringBuffer();
				vSql.append("select ").append(field).append(" from ").append(table);
				List<String> list = getJdbcTemplate().query(vSql.toString(),new RowMapper<String>() {
					public String mapRow(ResultSet rs, int num)
							throws SQLException {
						return rs.getString(field);
					}
				});
				return list;
			}
		});
		return playList;
	}
	
	
	/**
	 * 获取策略对应的播出单   点播随片
	 * @param orderId
	 * @param positionCode
	 * @return
	 */
	public List<PloyPlayListGisRel> getPloyPlayListGisByFollowOrder(Integer orderId,final String positionCode){
		StringBuffer sql = new StringBuffer();
		sql.append("select rel.mate_id,rel.play_location,rel.poll_index,r.resource_type,rel.precise_id,");
		sql.append(" p.ploy_id,rel.start_time,rel.end_time,rel.channel_group_id,rel.area_code,p.userindustrys,p.userlevels,p.tvn_number,rel.PRECISETYPE");
		sql.append(" from t_order_mate_rel rel,t_resource r ,t_ploy p");
		sql.append(" where rel.mate_id=r.id and rel.precise_id=p.id ");
		sql.append(" and rel.type = 1 and rel.order_id=? order by rel.area_code,p.start_time,p.priority desc,rel.poll_index asc,rel.play_location");
		List<PloyPlayListGisRel> playList = getJdbcTemplate().query(sql.toString(),
		new Object[] { orderId }, new RowMapper<PloyPlayListGisRel>() {
			@Override
			public PloyPlayListGisRel mapRow(ResultSet rs, int num)
					throws SQLException {
				PloyPlayListGisRel p = new PloyPlayListGisRel();
				p.setMateId(rs.getInt("mate_id"));
				p.setPlayLocation(rs.getString("play_location"));
				p.setPollIndex(rs.getInt("poll_index"));
				p.setResourceType(rs.getInt("resource_type"));
				p.setPreciseId(rs.getInt("precise_id"));
				p.setPloyId(rs.getInt("ploy_id"));
				p.setPRECISETYPE(rs.getInt("PRECISETYPE"));
				if("0".equals(rs.getString("start_time"))){
					//时间段如未选，则后台默认设定时间为0，对应订单生成播出单时，时间为00:00:00-23:59:59
					p.setStartTime("00:00:00");
					p.setEndTime("23:59:59");
				}else{
					p.setStartTime(rs.getString("start_time"));
					p.setEndTime(rs.getString("end_time"));
				}
				p.setChannelGroupId(rs.getInt("channel_group_id"));
				p.setAreaCode(rs.getString("area_code"));
				p.setUserIndustrys(rs.getString("userindustrys"));
				p.setUserLevels(rs.getString("userlevels"));
				p.setTvnNumber(rs.getString("tvn_number"));
				//查询所有栏目，再过滤， 但是点播随片的"所有栏目"不用查出来
				//p.setLookbackCategoryIdList(getValueByIds("category_id","t_loopback_category"));
				getMaterialById(p);
				return p;
			}

			@SuppressWarnings("unchecked")
			private void getMaterialById(final PloyPlayListGisRel p) {
				switch (p.getResourceType()) {
				case Constant.IMAGE:
					String imageSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
							+ "where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map iMap = getJdbcTemplate().queryForMap(imageSql);
					p.setPath(iMap.get("path").toString());
					break;
				case Constant.VIDEO:
					String videoSql = "select CONCAT(m.video_pump_path,'/',m.name) as path from t_resource r,t_video_meta m  "
							+ "where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map vMap = getJdbcTemplate().queryForMap(videoSql);
					p.setPath(vMap.get("path").toString());
					break;
				case Constant.WRITING:
					String textSql = "select m.id,m.name,m.content,m.url,m.action,m.duration_time,"
							+"m.font_size,m.font_color,m.background_color,m.roll_speed,"
							+"m.position_vertex_coordinates,m.position_width_height from t_resource r,T_TEXT_META m "
							+ " where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					getJdbcTemplate().query(textSql,new RowMapper(){
						@Override
						public Object mapRow(ResultSet rs, int num)
								throws SQLException {
							TextMate text = new TextMate();
							text.setId(rs.getInt("id"));
							text.setName(rs.getString("name"));
							text.setContent(rs.getBlob("content"));
							text.setURL(rs.getString("url"));
							text.setAction(rs.getString("action"));
							text.setDurationTime(rs.getInt("duration_time"));
							text.setFontSize(rs.getInt("font_size"));
							text.setFontColor(rs.getString("font_color"));
							text.setBkgColor(rs.getString("background_color"));
							text.setRollSpeed(rs.getInt("roll_speed"));
							text.setPositionVertexCoordinates(rs.getString("position_vertex_coordinates"));
							text.setPositionWidthHeight(rs.getString("position_width_height"));
							p.setText(text);
							return null;
						}
					});
					break;
				case Constant.QUESTIONNAIRE:
					String qSql = "select FORMAL_FILE_PATH from t_resource r,t_questionnaire_real q  where "
							+ "r.resource_id=q.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map qMap = getJdbcTemplate().queryForMap(qSql);
					p.setPath(qMap.get("FORMAL_FILE_PATH").toString());
					break;
				case Constant.ZIP:
					String zipSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
							+ "where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map zMap = getJdbcTemplate().queryForMap(zipSql);
					p.setPath(zMap.get("path").toString());
					break;
				}
			}
			
		});
		return playList;
	}
	
	
	
	
	/**
	 * 获取策略对应的播出单
	 * @param orderId
	 * @param positionCode
	 * @return
	 */
	private List<PloyPlayListGisRel> getPloyPlayListGisByOrderbak(Integer orderId,final String positionCode){
		StringBuffer sql = new StringBuffer();
		sql.append("select rel.mate_id,rel.play_location,rel.poll_index,r.resource_type,rel.precise_id,");
		sql.append(" p.ploy_id,p.start_time,p.end_time,p.channel_group_id,p.area_id,p.userindustrys,p.userlevels,p.tvn_number");
		sql.append(" from t_order_mate_rel rel,t_resource r ,t_ploy p");
		sql.append(" where rel.mate_id=r.id and rel.precise_id=p.id ");
		sql.append(" and rel.type = 1 and rel.order_id=? order by p.id,p.start_time,p.priority desc,rel.poll_index asc,rel.play_location");
		List<PloyPlayListGisRel> playList = getJdbcTemplate().query(sql.toString(),
		new Object[] { orderId }, new RowMapper<PloyPlayListGisRel>() {
			@Override
			public PloyPlayListGisRel mapRow(ResultSet rs, int num)
					throws SQLException {
				PloyPlayListGisRel p = new PloyPlayListGisRel();
				p.setMateId(rs.getInt("mate_id"));
				p.setPlayLocation(rs.getString("play_location"));
				p.setPollIndex(rs.getInt("poll_index"));
				p.setResourceType(rs.getInt("resource_type"));
				p.setPreciseId(rs.getInt("precise_id"));
				p.setPloyId(rs.getInt("ploy_id"));
				if("0".equals(rs.getString("start_time"))){
					//时间段如未选，则后台默认设定时间为0，对应订单生成播出单时，时间为00:00:00-23:59:59
					p.setStartTime("00:00:00");
					p.setEndTime("23:59:59");
				}else{
					p.setStartTime(rs.getString("start_time"));
					p.setEndTime(rs.getString("end_time"));
				}
				p.setChannelGroupId(rs.getInt("channel_group_id"));
//				p.setAreaId(StringUtil.toLong(rs.getLong("area_id")));
				p.setUserIndustrys(rs.getString("userindustrys"));
				p.setUserLevels(rs.getString("userlevels"));
				p.setTvnNumber(rs.getString("tvn_number"));
				p.setLookbackCategoryIdList(getValueByIds("category_id","t_loopback_category"));
				getMaterialById(p);
				setServiceIdList(p);
				return p;
			}

			@SuppressWarnings("unchecked")
			private void getMaterialById(final PloyPlayListGisRel p) {
				switch (p.getResourceType()) {
				case Constant.IMAGE:
					String imageSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
							+ "where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map iMap = getJdbcTemplate().queryForMap(imageSql);
					p.setPath(iMap.get("path").toString());
					break;
				case Constant.VIDEO:
					String videoSql = "select CONCAT(m.video_pump_path,'/',m.name) as path from t_resource r,t_video_meta m  "
							+ "where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map vMap = getJdbcTemplate().queryForMap(videoSql);
					p.setPath(vMap.get("path").toString());
					break;
				case Constant.WRITING:
					String textSql = "select m.id,m.name,m.content,m.url,m.action,m.duration_time,"
							+"m.font_size,m.font_color,m.background_color,m.roll_speed,"
							+"m.position_vertex_coordinates,m.position_width_height from t_resource r,T_TEXT_META m "
							+ " where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					getJdbcTemplate().query(textSql,new RowMapper(){
						@Override
						public Object mapRow(ResultSet rs, int num)
								throws SQLException {
							TextMate text = new TextMate();
							text.setId(rs.getInt("id"));
							text.setName(rs.getString("name"));
							text.setContent(rs.getBlob("content"));
							text.setURL(rs.getString("url"));
							text.setAction(rs.getString("action"));
							text.setDurationTime(rs.getInt("duration_time"));
							text.setFontSize(rs.getInt("font_size"));
							text.setFontColor(rs.getString("font_color"));
							text.setBkgColor(rs.getString("background_color"));
							text.setRollSpeed(rs.getInt("roll_speed"));
							text.setPositionVertexCoordinates(rs.getString("position_vertex_coordinates"));
							text.setPositionWidthHeight(rs.getString("position_width_height"));
							p.setText(text);
							return null;
						}
					});
					break;
				case Constant.QUESTIONNAIRE:
					String qSql = "select FORMAL_FILE_PATH from t_resource r,t_questionnaire_real q  where "
							+ "r.resource_id=q.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map qMap = getJdbcTemplate().queryForMap(qSql);
					p.setPath(qMap.get("FORMAL_FILE_PATH").toString());
					break;
				case Constant.ZIP:
					String zipSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
							+ "where r.resource_id=m.id and r.id="
							+ p.getMateId()
							+ " and r.resource_type="
							+ p.getResourceType();
					Map zMap = getJdbcTemplate().queryForMap(zipSql);
					p.setPath(zMap.get("path").toString());
					break;
				}
			}
			
			private void setServiceIdList(final PloyPlayListGisRel p){
				if(p.getChannelGroupId() != null){
					StringBuffer sql = new StringBuffer();
					if(p.getChannelGroupId().intValue() == 0){
						//组ID位0，取所有频道
						if("02074".equals(positionCode) || "02084".equals(positionCode) || "02094".equals(positionCode)){
							sql.append("select c.service_id from t_channelinfo_npvr c where 1=1 ");
						}else{
							sql.append("select c.service_id from t_channelinfo c where 1=1 ");
						}
					}else{
						sql.append("select c.service_id from t_channel_group_ref rel, t_channelinfo c ");
						sql.append(" where rel.channel_id=c.channel_id");
						sql.append(" and rel.group_id = ").append(p.getChannelGroupId());
					}
					if("02061".equals(positionCode) || "02062".equals(positionCode)){
						//广播收听背景广告，只查询音频类频道
						sql.append(" and c.channel_type='音频直播类业务' ");
					}					
					List<String> list = getJdbcTemplate().query(sql.toString(),new RowMapper<String>() {
						public String mapRow(ResultSet rs, int num)
								throws SQLException {
							String cId = rs.getString("service_id");
							return cId;
						}
					});
					p.setServiceIdList(list);
				}
			}
			
			/**
			 * 根据ids获取对应的值
			 * @param field
			 * @param table
			 * @param ids
			 * @return
			 */
			private List<String> getValueByIds(final String field,String table){
				StringBuffer vSql = new StringBuffer();
				vSql.append("select ").append(field).append(" from ").append(table);
				List<String> list = getJdbcTemplate().query(vSql.toString(),new RowMapper<String>() {
					public String mapRow(ResultSet rs, int num)
							throws SQLException {
						return rs.getString(field);
					}
				});
				return list;
			}
		});
		return playList;
	}
	public List<PrecisePlayListGisRel> getPrecisePlayListByNPVROrder(Integer orderId) {
		StringBuffer sql = new StringBuffer("select rel.mate_id,rel.poll_index,r.resource_type,");
		sql.append(" rel.precise_id,pm.precisetype,pm.ploy_id,pm.dtv_channel_id,pm.playback_channel_id,");
		sql.append(" pm.user_area,pm.user_area2,pm.user_area3,pm.userindustrys,pm.userlevels,");
		sql.append(" pm.lookback_category_id,pm.asset_name,pm.priority,pm.channel_group_id,rel.start_time,rel.end_time");
		sql.append(" from t_order_mate_rel rel,t_resource r,t_precise_match pm");
		sql.append(" where rel.mate_id=r.id and rel.precise_id = pm.id and rel.type=0");
		sql.append(" and rel.order_id=? order by pm.id,pm.priority desc");
		List<PrecisePlayListGisRel> pp = getJdbcTemplate().query(sql.toString(),
				new Object[] { orderId }, new RowMapper<PrecisePlayListGisRel>() {
					@Override
					public PrecisePlayListGisRel mapRow(ResultSet rs, int num)
							throws SQLException {
						PrecisePlayListGisRel p = new PrecisePlayListGisRel();
						p.setMateId(rs.getInt("mate_id"));
						p.setPollIndex(rs.getInt("poll_index"));
						p.setResourceType(rs.getInt("resource_type"));
						p.setPreciseId(rs.getInt("precise_id"));
						p.setPreciseType(rs.getInt("precisetype"));
						p.setPloyId(rs.getInt("ploy_id"));
						p.setDtvChannelId(rs.getString("dtv_channel_id"));
						p.setPlaybackChannelId(rs.getString("playback_channel_id"));
						if(StringUtils.isNotBlank(rs.getString("user_area3")) && !"0".equals(rs.getString("user_area3"))){
							p.setUserArea(rs.getString("user_area3"));
						}else if(StringUtils.isNotBlank(rs.getString("user_area2")) && !"0".equals(rs.getString("user_area2"))){
							p.setUserArea(rs.getString("user_area2"));
						}else if(StringUtils.isNotBlank(rs.getString("user_area")) && !"0".equals(rs.getString("user_area"))){
							p.setUserArea(rs.getString("user_area"));
						}
						p.setIndustryList(getValueByIds("user_industry_category_code","t_user_industry_category",rs.getString("userindustrys")));
						p.setLevelList(getValueByIds("user_rank_code","t_user_rank",rs.getString("userlevels")));
						p.setLookbackCategoryIdList(getValueByIds("category_id","t_loopback_category",rs.getString("lookback_category_id")));
						p.setAssetIdList(getAssetIdList(rs.getString("asset_name")));
						p.setChannelGroupId(rs.getInt("channel_group_id"));
						p.setServiceIdList(getServiceIdList(rs.getInt("channel_group_id")));
						p.setStartTime(rs.getString("start_time"));
						p.setEndTime(rs.getString("end_time"));
						getMaterialById(p);
						return p;
					}
					
					/**
					 * 根据ids获取对应的值
					 * @param field
					 * @param table
					 * @param ids
					 * @return
					 */
					private List<String> getValueByIds(final String field,String table,String ids){
						if(StringUtils.isEmpty(ids)){
							return null;
						}
						StringBuffer vSql = new StringBuffer();
						vSql.append("select ").append(field).append(" from ").append(table).append(" where id in (");
						String[] vIds = ids.split(",");
						if(vIds.length>0){
							for(int i = 0;i<vIds.length;i++){
								if(i>0){
									vSql.append(",");
								}
								Integer cId = Integer.parseInt(vIds[i]);
								vSql.append(cId);
							}
							vSql.append(")");
							
							List<String> list = getJdbcTemplate().query(vSql.toString(),new RowMapper<String>() {
								public String mapRow(ResultSet rs, int num)
										throws SQLException {
									return rs.getString(field);
								}
							});
							return list;
						}
						return null;
					}
					
					/**
					 * 根据资源名称模糊查询资源ID列表
					 * @param assetName
					 * @return
					 */
					private List<String> getAssetIdList(String assetName){
						if(StringUtils.isEmpty(assetName)){
							return null;
						}
						String sql  = "select asset_id from t_assetinfo where asset_name like '%"+assetName+"%'";
						List<String> list = getJdbcTemplate().query(sql,new RowMapper<String>() {
							public String mapRow(ResultSet rs, int num)
									throws SQLException {
								return rs.getString("asset_id");
							}
						});
						return list;
					}
					
					/**
					 * 根据频道组ID查询频道serviceId列表
					 * @param groupId
					 * @return
					 */
					private List<String> getServiceIdList(Integer groupId){
						if(groupId == null){
							return null;
						}
						StringBuffer sql = new StringBuffer();
						sql.append("select c.service_id from t_channel_group_ref_npvr rel, t_channelinfo_npvr c ");
						sql.append(" where rel.channel_id=c.channel_id");
						sql.append(" and rel.group_id = ").append(groupId);
						List<String> list = getJdbcTemplate().query(sql.toString(),new RowMapper<String>() {
							public String mapRow(ResultSet rs, int num)
									throws SQLException {
								return rs.getString("service_id");
							}
						});
						return list;
					}

					@SuppressWarnings("unchecked")
					private void getMaterialById(final PrecisePlayListGisRel p) {
						switch (p.getResourceType()) {
						case Constant.IMAGE:
							String imageSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
									+ "where r.resource_id=m.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							Map iMap = getJdbcTemplate().queryForMap(imageSql);
							p.setPath(iMap.get("path").toString());
							break;
						case Constant.VIDEO:
							String videoSql = "select CONCAT(m.video_pump_path,'/',m.name) as path from t_resource r,t_video_meta m  "
									+ "where r.resource_id=m.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							Map vMap = getJdbcTemplate().queryForMap(videoSql);
							p.setPath(vMap.get("path").toString());
							break;
						case Constant.WRITING:
							String textSql = "select m.id,m.name,m.content,m.url,m.action,m.duration_time,"
									+"m.font_size,m.font_color,m.background_color,m.roll_speed,"
									+"m.position_vertex_coordinates,m.position_width_height from t_resource r,T_TEXT_META m "
									+ " where r.resource_id=m.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							getJdbcTemplate().query(textSql,new RowMapper(){
								@Override
								public Object mapRow(ResultSet rs, int num)
										throws SQLException {
									TextMate text = new TextMate();
									text.setId(rs.getInt("id"));
									text.setName(rs.getString("name"));
									text.setContent(rs.getBlob("content"));
									text.setURL(rs.getString("url"));
									text.setAction(rs.getString("action"));
									text.setDurationTime(rs.getInt("duration_time"));
									text.setFontSize(rs.getInt("font_size"));
									text.setFontColor(rs.getString("font_color"));
									text.setBkgColor(rs.getString("background_color"));
									text.setRollSpeed(rs.getInt("roll_speed"));
									text.setPositionVertexCoordinates(rs.getString("position_vertex_coordinates"));
									text.setPositionWidthHeight(rs.getString("position_width_height"));
									p.setText(text);
									return null;
								}
							});
							break;
						case Constant.QUESTIONNAIRE:
							String qSql = "select FORMAL_FILE_PATH from t_resource r,t_questionnaire_real q  where "
									+ "r.resource_id=q.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							Map qMap = getJdbcTemplate().queryForMap(qSql);
							p.setPath(qMap.get("FORMAL_FILE_PATH").toString());
							break;
						}
					}
				});
		return pp;
	}
	public List<OrderMaterialRelation> getNVODAngleRelMateAreaCodeByOrder(Integer orderId){
		
		String sql ="SELECT g.area_code,g.start_time,g.end_time FROM t_order_mate_rel g WHERE g.ORDER_ID =? GROUP BY g.area_code,g.start_time,g.end_time";
		List<OrderMaterialRelation> pp=getJdbcTemplate().query(sql, new Object[] { orderId },new RowMapper<OrderMaterialRelation>(){
			@Override
			public OrderMaterialRelation mapRow(ResultSet arg0, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				OrderMaterialRelation materialRelation = new OrderMaterialRelation();
				materialRelation.setAreaCode(arg0.getString("area_code"));
				materialRelation.setEndTime(arg0.getString("end_time"));
				materialRelation.setStartTime(arg0.getString("start_time"));
				return materialRelation;
			}
			
		});
		return pp;
	}
	public List<PrecisePlayListGisRel> getNVODAnglePrecisePlayListByOrder(Integer orderId){
		StringBuffer sql = new StringBuffer("SELECT rel.mate_id, rel.area_code, r.resource_type,rel.precise_id,");
		sql.append("rel.start_time,rel.end_time,rel.poll_index ");
		sql.append("FROM t_order_mate_rel rel, t_resource r  ");
		sql.append("WHERE rel.mate_id = r.id AND rel.type = 1 AND rel.order_id = ? ");
		sql.append("ORDER BY rel.start_time,rel.area_code,rel.POLL_INDEX DESC");
		List<PrecisePlayListGisRel> pp = getJdbcTemplate().query(sql.toString(),
				new Object[] { orderId }, new RowMapper<PrecisePlayListGisRel>() {

					@Override
					public PrecisePlayListGisRel mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						// TODO Auto-generated method stub
						PrecisePlayListGisRel p = new PrecisePlayListGisRel();
						p.setMateId(arg0.getInt("mate_id"));
						p.setAreaCode(arg0.getString("area_code"));
						p.setResourceType(arg0.getInt("resource_type"));
						p.setPreciseId(arg0.getInt("precise_id"));
						//p.setPreciseType(arg0.getInt("precisetype"));
						p.setPloyId(arg0.getInt("precise_id"));
						//p.setPriority(arg0.getInt("priority"));
						p.setStartTime(arg0.getString("start_time"));
						p.setEndTime(arg0.getString("end_time"));
						//p.setMenuTypeCode(arg0.getString("menu_type_code"));
						p.setPollIndex(arg0.getInt("poll_index"));
						getMaterialById(p);
						return p;
					}

					private void getMaterialById(final PrecisePlayListGisRel p) {
						String imageSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
								+ "where r.resource_id=m.id and r.id="
								+ p.getMateId()
								+ " and r.resource_type="
								+ p.getResourceType();
						Map<String,Object> iMap = getJdbcTemplate().queryForMap(imageSql);
						p.setPath(iMap.get("path").toString());

					}
			
		});
		return pp;
	}
	public List<PrecisePlayListGisRel> getNVODMenuPrecisePlayListByOrder(Integer orderId){
		StringBuffer sql = new StringBuffer("SELECT rel.mate_id, rel.area_code, r.resource_type, rel.precise_id, pm.precisetype,");
		sql.append("pm.ploy_id, pm.priority, rel.start_time,rel.end_time,rel.menu_type_code ");
		sql.append("FROM t_order_mate_rel rel, t_resource r, t_precise_match pm ");
		sql.append("WHERE rel.mate_id = r.id AND rel.precise_id = pm.id AND rel.type = 0 AND rel.order_id = ? ");
		sql.append("ORDER BY pm.id, pm.priority DESC");
		List<PrecisePlayListGisRel> pp = getJdbcTemplate().query(sql.toString(),
				new Object[] { orderId }, new RowMapper<PrecisePlayListGisRel>() {

					@Override
					public PrecisePlayListGisRel mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						// TODO Auto-generated method stub
						PrecisePlayListGisRel p = new PrecisePlayListGisRel();
						p.setMateId(arg0.getInt("mate_id"));
						p.setAreaCode(arg0.getString("area_code"));
						p.setResourceType(arg0.getInt("resource_type"));
						p.setPreciseId(arg0.getInt("precise_id"));
						p.setPreciseType(arg0.getInt("precisetype"));
						p.setPloyId(arg0.getInt("ploy_id"));
						p.setPriority(arg0.getInt("priority"));
						p.setStartTime(arg0.getString("start_time"));
						p.setEndTime(arg0.getString("end_time"));
						p.setMenuTypeCode(arg0.getString("menu_type_code"));
						getMaterialById(p);
						return p;
					}

					private void getMaterialById(final PrecisePlayListGisRel p) {
						String imageSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
								+ "where r.resource_id=m.id and r.id="
								+ p.getMateId()
								+ " and r.resource_type="
								+ p.getResourceType();
						Map<String,Object> iMap = getJdbcTemplate().queryForMap(imageSql);
						p.setPath(iMap.get("path").toString());

					}
			
		});
		return pp;
	
	}
	/**
	 * 获取投放式精准相关的播出单信息
	 * @param orderId
	 * @return
	 */
	public List<PrecisePlayListGisRel> getPrecisePlayListByOrder(Integer orderId) {
		StringBuffer sql = new StringBuffer("select rel.mate_id,rel.poll_index,r.resource_type,");
		sql.append(" rel.precise_id,pm.precisetype,pm.ploy_id,pm.dtv_channel_id,pm.playback_channel_id,");
		sql.append(" pm.user_area,pm.user_area2,pm.user_area3,pm.userindustrys,pm.userlevels,");
		sql.append(" pm.lookback_category_id,pm.asset_name,pm.priority,pm.channel_group_id,rel.start_time,rel.end_time");
		sql.append(" from t_order_mate_rel rel,t_resource r,t_precise_match pm");
		sql.append(" where rel.mate_id=r.id and rel.precise_id = pm.id and rel.type=0");
		sql.append(" and rel.order_id=? order by pm.id,pm.priority desc");
		List<PrecisePlayListGisRel> pp = getJdbcTemplate().query(sql.toString(),
				new Object[] { orderId }, new RowMapper<PrecisePlayListGisRel>() {
					@Override
					public PrecisePlayListGisRel mapRow(ResultSet rs, int num)
							throws SQLException {
						PrecisePlayListGisRel p = new PrecisePlayListGisRel();
						p.setMateId(rs.getInt("mate_id"));
						p.setPollIndex(rs.getInt("poll_index"));
						p.setResourceType(rs.getInt("resource_type"));
						p.setPreciseId(rs.getInt("precise_id"));
						p.setPreciseType(rs.getInt("precisetype"));
						p.setPloyId(rs.getInt("ploy_id"));
						p.setDtvChannelId(rs.getString("dtv_channel_id"));
						p.setPlaybackChannelId(rs.getString("playback_channel_id"));
						if(StringUtils.isNotBlank(rs.getString("user_area3")) && !"0".equals(rs.getString("user_area3"))){
							p.setUserArea(rs.getString("user_area3"));
						}else if(StringUtils.isNotBlank(rs.getString("user_area2")) && !"0".equals(rs.getString("user_area2"))){
							p.setUserArea(rs.getString("user_area2"));
						}else if(StringUtils.isNotBlank(rs.getString("user_area")) && !"0".equals(rs.getString("user_area"))){
							p.setUserArea(rs.getString("user_area"));
						}
						p.setIndustryList(getValueByIds("user_industry_category_code","t_user_industry_category",rs.getString("userindustrys")));
						p.setLevelList(getValueByIds("user_rank_code","t_user_rank",rs.getString("userlevels")));
						p.setLookbackCategoryIdList(getValueByIds("category_id","t_loopback_category",rs.getString("lookback_category_id")));
						p.setAssetIdList(getAssetIdList(rs.getString("asset_name")));
						p.setChannelGroupId(rs.getInt("channel_group_id"));
						p.setServiceIdList(getServiceIdList(rs.getInt("channel_group_id")));
						p.setStartTime(rs.getString("start_time"));
						p.setEndTime(rs.getString("end_time"));
						getMaterialById(p);
						return p;
					}
					
					/**
					 * 根据ids获取对应的值
					 * @param field
					 * @param table
					 * @param ids
					 * @return
					 */
					private List<String> getValueByIds(final String field,String table,String ids){
						if(StringUtils.isEmpty(ids)){
							return null;
						}
						StringBuffer vSql = new StringBuffer();
						vSql.append("select ").append(field).append(" from ").append(table).append(" where id in (");
						String[] vIds = ids.split(",");
						if(vIds.length>0){
							for(int i = 0;i<vIds.length;i++){
								if(i>0){
									vSql.append(",");
								}
								Integer cId = Integer.parseInt(vIds[i]);
								vSql.append(cId);
							}
							vSql.append(")");
							
							List<String> list = getJdbcTemplate().query(vSql.toString(),new RowMapper<String>() {
								public String mapRow(ResultSet rs, int num)
										throws SQLException {
									return rs.getString(field);
								}
							});
							return list;
						}
						return null;
					}
					
					/**
					 * 根据资源名称模糊查询资源ID列表
					 * @param assetName
					 * @return
					 */
					private List<String> getAssetIdList(String assetName){
						if(StringUtils.isEmpty(assetName)){
							return null;
						}
						String sql  = "select asset_id from t_assetinfo where asset_name like '%"+assetName+"%'";
						List<String> list = getJdbcTemplate().query(sql,new RowMapper<String>() {
							public String mapRow(ResultSet rs, int num)
									throws SQLException {
								return rs.getString("asset_id");
							}
						});
						return list;
					}
					
					/**
					 * 根据频道组ID查询频道serviceId列表
					 * @param groupId
					 * @return
					 */
					private List<String> getServiceIdList(Integer groupId){
						if(groupId == null){
							return null;
						}
						StringBuffer sql = new StringBuffer();
						sql.append("select c.service_id from t_channel_group_ref rel, t_channelinfo c ");
						sql.append(" where rel.channel_id=c.channel_id");
						sql.append(" and rel.group_id = ").append(groupId);
						List<String> list = getJdbcTemplate().query(sql.toString(),new RowMapper<String>() {
							public String mapRow(ResultSet rs, int num)
									throws SQLException {
								return rs.getString("service_id");
							}
						});
						return list;
					}

					@SuppressWarnings("unchecked")
					private void getMaterialById(final PrecisePlayListGisRel p) {
						switch (p.getResourceType()) {
						case Constant.IMAGE:
							String imageSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
									+ "where r.resource_id=m.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							Map iMap = getJdbcTemplate().queryForMap(imageSql);
							p.setPath(iMap.get("path").toString());
							break;
						case Constant.VIDEO:
							String videoSql = "select CONCAT(m.video_pump_path,'/',m.name) as path from t_resource r,t_video_meta m  "
									+ "where r.resource_id=m.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							Map vMap = getJdbcTemplate().queryForMap(videoSql);
							p.setPath(vMap.get("path").toString());
							break;
						case Constant.WRITING:
							String textSql = "select m.id,m.name,m.content,m.url,m.action,m.duration_time,"
									+"m.font_size,m.font_color,m.background_color,m.roll_speed,"
									+"m.position_vertex_coordinates,m.position_width_height from t_resource r,T_TEXT_META m "
									+ " where r.resource_id=m.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							getJdbcTemplate().query(textSql,new RowMapper(){
								@Override
								public Object mapRow(ResultSet rs, int num)
										throws SQLException {
									TextMate text = new TextMate();
									text.setId(rs.getInt("id"));
									text.setName(rs.getString("name"));
									text.setContent(rs.getBlob("content"));
									text.setURL(rs.getString("url"));
									text.setAction(rs.getString("action"));
									text.setDurationTime(rs.getInt("duration_time"));
									text.setFontSize(rs.getInt("font_size"));
									text.setFontColor(rs.getString("font_color"));
									text.setBkgColor(rs.getString("background_color"));
									text.setRollSpeed(rs.getInt("roll_speed"));
									text.setPositionVertexCoordinates(rs.getString("position_vertex_coordinates"));
									text.setPositionWidthHeight(rs.getString("position_width_height"));
									p.setText(text);
									return null;
								}
							});
							break;
						case Constant.QUESTIONNAIRE:
							String qSql = "select FORMAL_FILE_PATH from t_resource r,t_questionnaire_real q  where "
									+ "r.resource_id=q.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							Map qMap = getJdbcTemplate().queryForMap(qSql);
							p.setPath(qMap.get("FORMAL_FILE_PATH").toString());
							break;
						}
					}
				});
		return pp;
	}
	
	/**
	 * 获取投放式精准相关的播出单信息 点播随片
	 * @param orderId
	 * @return
	 */
	public List<PrecisePlayListGisRel> getPrecisePlayListByFollowOrder(Integer orderId) {
		StringBuffer sql = new StringBuffer("select rel.mate_id,rel.poll_index,r.resource_type,");
		sql.append(" rel.precise_id,pm.precisetype,pm.ploy_id,pm.dtv_channel_id,pm.playback_channel_id,");
		sql.append(" pm.user_area,pm.user_area2,pm.user_area3,pm.userindustrys,pm.userlevels,");
		sql.append(" pm.ASSET_SORT_ID,pm.asset_name,pm.priority,pm.channel_group_id,rel.start_time,rel.end_time, pm.priority");
		sql.append(" from t_order_mate_rel rel,t_resource r,t_precise_match pm");
		sql.append(" where rel.mate_id=r.id and rel.precise_id = pm.id and rel.type=0");
		sql.append(" and rel.order_id=? order by pm.id,pm.priority desc");
		List<PrecisePlayListGisRel> pp = getJdbcTemplate().query(sql.toString(),
				new Object[] { orderId }, new RowMapper<PrecisePlayListGisRel>() {
					@Override
					public PrecisePlayListGisRel mapRow(ResultSet rs, int num)
							throws SQLException {
						PrecisePlayListGisRel p = new PrecisePlayListGisRel();
						p.setMateId(rs.getInt("mate_id"));
						p.setPollIndex(rs.getInt("poll_index"));
						p.setPriority(rs.getInt("priority"));
						p.setResourceType(rs.getInt("resource_type"));
						p.setPreciseId(rs.getInt("precise_id"));
						p.setPreciseType(rs.getInt("precisetype"));
						p.setPloyId(rs.getInt("ploy_id"));
						p.setDtvChannelId(rs.getString("dtv_channel_id"));
						p.setPlaybackChannelId(rs.getString("playback_channel_id"));
						if(StringUtils.isNotBlank(rs.getString("user_area3")) && !"0".equals(rs.getString("user_area3"))){
							p.setUserArea(rs.getString("user_area3"));
						}else if(StringUtils.isNotBlank(rs.getString("user_area2")) && !"0".equals(rs.getString("user_area2"))){
							p.setUserArea(rs.getString("user_area2"));
						}else if(StringUtils.isNotBlank(rs.getString("user_area")) && !"0".equals(rs.getString("user_area"))){
							p.setUserArea(rs.getString("user_area"));
						}
						p.setIndustryList(getValueByIds("user_industry_category_code","t_user_industry_category",rs.getString("userindustrys")));
						p.setLevelList(getValueByIds("user_rank_code","t_user_rank",rs.getString("userlevels")));
						//其实不是回看栏目，是影片分类栏目，但是还是用这个字段存放
						p.setLookbackCategoryIdList(getValueByIds("category_id","t_categoryinfo",rs.getString("ASSET_SORT_ID")));
						p.setAssetIdList(getAssetIdList(rs.getString("asset_name")));
						p.setChannelGroupId(rs.getInt("channel_group_id"));
						p.setServiceIdList(getServiceIdList(rs.getInt("channel_group_id")));
						p.setStartTime(rs.getString("start_time"));
						p.setEndTime(rs.getString("end_time"));
						getMaterialById(p);
						return p;
					}
					
					/**
					 * 根据ids获取对应的值
					 * @param field
					 * @param table
					 * @param ids
					 * @return
					 */
					private List<String> getValueByIds(final String field,String table,String ids){
						if(StringUtils.isEmpty(ids)){
							return null;
						}
						StringBuffer vSql = new StringBuffer();
						vSql.append("select ").append(field).append(" from ").append(table).append(" where id in (");
						String[] vIds = ids.split(",");
						if(vIds.length>0){
							for(int i = 0;i<vIds.length;i++){
								if(i>0){
									vSql.append(",");
								}
								Integer cId = Integer.parseInt(vIds[i]);
								vSql.append(cId);
							}
							vSql.append(")");
							
							List<String> list = getJdbcTemplate().query(vSql.toString(),new RowMapper<String>() {
								public String mapRow(ResultSet rs, int num)
										throws SQLException {
									return rs.getString(field);
								}
							});
							return list;
						}
						return null;
					}
					
					/**
					 * 根据资源名称模糊查询资源ID列表
					 * @param assetName
					 * @return
					 */
					private List<String> getAssetIdList(String assetName){
						if(StringUtils.isEmpty(assetName)){
							return null;
						}
						String sql  = "select asset_id from t_assetinfo where asset_name like '%"+assetName+"%'";
						List<String> list = getJdbcTemplate().query(sql,new RowMapper<String>() {
							public String mapRow(ResultSet rs, int num)
									throws SQLException {
								return rs.getString("asset_id");
							}
						});
						return list;
					}
					
					/**
					 * 根据频道组ID查询频道serviceId列表
					 * @param groupId
					 * @return
					 */
					private List<String> getServiceIdList(Integer groupId){
						if(groupId == null){
							return null;
						}
						StringBuffer sql = new StringBuffer();
						sql.append("select c.service_id from t_channel_group_ref rel, t_channelinfo c ");
						sql.append(" where rel.channel_id=c.channel_id");
						sql.append(" and rel.group_id = ").append(groupId);
						List<String> list = getJdbcTemplate().query(sql.toString(),new RowMapper<String>() {
							public String mapRow(ResultSet rs, int num)
									throws SQLException {
								return rs.getString("service_id");
							}
						});
						return list;
					}

					@SuppressWarnings("unchecked")
					private void getMaterialById(final PrecisePlayListGisRel p) {
						switch (p.getResourceType()) {
						case Constant.IMAGE:
							String imageSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
									+ "where r.resource_id=m.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							Map iMap = getJdbcTemplate().queryForMap(imageSql);
							p.setPath(iMap.get("path").toString());
							break;
						case Constant.VIDEO:
							String videoSql = "select CONCAT(m.video_pump_path,'/',m.name) as path from t_resource r,t_video_meta m  "
									+ "where r.resource_id=m.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							Map vMap = getJdbcTemplate().queryForMap(videoSql);
							p.setPath(vMap.get("path").toString());
							break;
						case Constant.WRITING:
							String textSql = "select m.id,m.name,m.content,m.url,m.action,m.duration_time,"
									+"m.font_size,m.font_color,m.background_color,m.roll_speed,"
									+"m.position_vertex_coordinates,m.position_width_height from t_resource r,T_TEXT_META m "
									+ " where r.resource_id=m.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							getJdbcTemplate().query(textSql,new RowMapper(){
								@Override
								public Object mapRow(ResultSet rs, int num)
										throws SQLException {
									TextMate text = new TextMate();
									text.setId(rs.getInt("id"));
									text.setName(rs.getString("name"));
									text.setContent(rs.getBlob("content"));
									text.setURL(rs.getString("url"));
									text.setAction(rs.getString("action"));
									text.setDurationTime(rs.getInt("duration_time"));
									text.setFontSize(rs.getInt("font_size"));
									text.setFontColor(rs.getString("font_color"));
									text.setBkgColor(rs.getString("background_color"));
									text.setRollSpeed(rs.getInt("roll_speed"));
									text.setPositionVertexCoordinates(rs.getString("position_vertex_coordinates"));
									text.setPositionWidthHeight(rs.getString("position_width_height"));
									p.setText(text);
									return null;
								}
							});
							break;
						case Constant.QUESTIONNAIRE:
							String qSql = "select FORMAL_FILE_PATH from t_resource r,t_questionnaire_real q  where "
									+ "r.resource_id=q.id and r.id="
									+ p.getMateId()
									+ " and r.resource_type="
									+ p.getResourceType();
							Map qMap = getJdbcTemplate().queryForMap(qSql);
							p.setPath(qMap.get("FORMAL_FILE_PATH").toString());
							break;
						}
					}
				});
		return pp;
	}
	
	
	
	
	
	
	
	
	/**
	 * 获取精准对应的素材信息
	 * @param orderId
	 * @return
	 */
//	public List<MaterialBean> getPreciseMaterialByOrder(Integer orderId) {
//		StringBuffer sql = new StringBuffer("select rel.mate_id,rel.poll_index,r.resource_type,");
//		sql.append(" rel.precise_id,pm.precisetype,pm.ploy_id,pm.dtv_channel_id,pm.playback_channel_id,pm.user_area,pm.userindustrys,pm.userlevels,");
//		sql.append(" pm.tvn_number,pm.lookback_category_id,pm.asset_id,pm.priority,pm.channel_group_id");
//		sql.append(" from t_order_mate_rel rel,t_resource r,t_precise_match pm");
//		sql.append(" where rel.mate_id=r.id and rel.precise_id = pm.id and rel.precise_id >0");
//		sql.append(" and rel.order_id=? order by pm.priority desc,pm.id ");
//		List<MaterialBean> materials = getJdbcTemplate().query(sql.toString(),
//				new Object[] { orderId }, new RowMapper<MaterialBean>() {
//					@Override
//					public MaterialBean mapRow(ResultSet rs, int num)
//							throws SQLException {
//						MaterialBean m = new MaterialBean();
//						m.setId(rs.getInt("mate_id"));
//						m.setLoopNo(rs.getInt("poll_index"));
//						m.setType(rs.getInt("resource_type"));
//						TPreciseMatch precise = new TPreciseMatch();
//						precise.setId(rs.getLong("precise_id"));
//						precise.setPrecisetype(rs.getLong("precisetype"));
//						precise.setPloyId(rs.getLong("ploy_id"));
//						precise.setDtvChannelId(rs.getString("dtv_channel_id"));
//						precise.setPlaybackChannelId(rs.getString("playback_channel_id"));
//						precise.setUserArea(rs.getString("user_area"));
//						precise.setUserindustrys(rs.getString("userindustrys"));
//						precise.setUserlevels(rs.getString("userlevels"));
//						precise.setTvnNumber(rs.getString("tvn_number"));
//						precise.setLookbackCategoryId(rs.getString("lookback_category_id"));
//						precise.setAssetId(rs.getString("asset_id"));
//						precise.setPriority(rs.getShort("priority"));
//						precise.setGroupId(rs.getString("channel_group_id"));
//						m.setPrecise(precise);
//						getMaterialById(m);
//						return m;
//					}
//
//					@SuppressWarnings("unchecked")
//					private void getMaterialById(final MaterialBean m) {
//						switch (m.getType()) {
//						case Constant.IMAGE:
////							String imageSql = "select m.formal_file_path||'/'||m.name as path from t_resource r,t_image_meta m "
//							String imageSql = "select CONCAT(m.formal_file_path,'/',m.name) as path from t_resource r,t_image_meta m "
//									+ "where r.resource_id=m.id and r.id="
//									+ m.getId()
//									+ " and r.resource_type="
//									+ m.getType();
//							Map iMap = getJdbcTemplate().queryForMap(imageSql);
//							m.setPath(iMap.get("path").toString());
//							break;
//						case Constant.VIDEO:
////							String videoSql = "select m.formal_file_path||'/'||m.name as path from t_resource r,t_video_meta m  "
//							String videoSql = "select CONCAT(m.video_pump_path,'/',m.name) as path from t_resource r,t_video_meta m  "
//									+ "where r.resource_id=m.id and r.id="
//									+ m.getId()
//									+ " and r.resource_type="
//									+ m.getType();
//							Map vMap = getJdbcTemplate().queryForMap(videoSql);
//							m.setPath(vMap.get("path").toString());
//							break;
//						case Constant.WRITING:
//							String textSql = "select m.id,m.name,m.content,m.url,m.action,m.duration_time,"
//									+"m.font_size,m.font_color,m.background_color,m.roll_speed,"
//									+"m.position_vertex_coordinates,m.position_width_height from t_resource r,T_TEXT_META m "
//									+ " where r.resource_id=m.id and r.id="
//									+ m.getId()
//									+ " and r.resource_type="
//									+ m.getType();
//							getJdbcTemplate().query(textSql,new RowMapper(){
//								@Override
//								public Object mapRow(ResultSet rs, int num)
//										throws SQLException {
//									TextMate text = new TextMate();
//									text.setId(rs.getInt("id"));
//									text.setName(rs.getString("name"));
//									text.setContent(rs.getBlob("content"));
//									text.setURL(rs.getString("url"));
//									text.setAction(rs.getString("action"));
//									text.setDurationTime(rs.getInt("duration_time"));
//									text.setFontSize(rs.getInt("font_size"));
//									text.setFontColor(rs.getString("font_color"));
//									text.setBkgColor(rs.getString("background_color"));
//									text.setRollSpeed(rs.getInt("roll_speed"));
//									text.setPositionVertexCoordinates(rs.getString("position_vertex_coordinates"));
//									text.setPositionWidthHeight(rs.getString("position_width_height"));
//									m.setText(text);
//									return null;
//								}
//							});
//							break;
//						case Constant.QUESTIONNAIRE:
//							String qSql = "select FORMAL_FILE_PATH from t_resource r,t_questionnaire_real q  where "
//									+ "r.resource_id=q.id and r.id="
//									+ m.getId()
//									+ " and r.resource_type="
//									+ m.getType();
//							Map qMap = getJdbcTemplate().queryForMap(qSql);
//							m.setPath(qMap.get("FORMAL_FILE_PATH").toString());
//							break;
//						}
//					}
//				});
//		return materials;
//	}
	
	/**
	 * 根据精准ids获取精准对应的素材列表
	 * @param preciseIds
	 * @return
	 */
//	public List<MaterialBean> getPreciseMaterialByPreciseIds(String preciseIds) {
//		if(StringUtils.isEmpty(preciseIds)){
//			return null;
//		}
//		StringBuffer sql = new StringBuffer("select rel.mate_id,r.resource_type,");
//		sql.append(" rel.precise_id,pm.dtv_channel_id");
//		sql.append(" from t_order_mate_rel rel,t_resource r,t_precise_match pm");
//		sql.append(" where rel.mate_id=r.id and rel.precise_id = pm.id and rel.precise_id in (").append(preciseIds).append(")");
//		sql.append(" order by pm.priority desc,pm.id ");
//		List<MaterialBean> materials = getJdbcTemplate().query(sql.toString(),new RowMapper<MaterialBean>() {
//					@Override
//					public MaterialBean mapRow(ResultSet rs, int num)
//							throws SQLException {
//						MaterialBean m = new MaterialBean();
//						m.setId(rs.getInt("mate_id"));
//						m.setType(rs.getInt("resource_type"));
//						TPreciseMatch precise = new TPreciseMatch();
//						precise.setId(rs.getLong("precise_id"));
//						precise.setDtvChannelId(rs.getString("dtv_channel_id"));
//						m.setPrecise(precise);
//						getMaterialById(m);
//						return m;
//					}
//
//					@SuppressWarnings("unchecked")
//					private void getMaterialById(final MaterialBean m) {
//						switch (m.getType()) {
//						case Constant.IMAGE:
//							String imageSql = "select m.file_size from t_resource r,t_image_meta m "
//									+ "where r.resource_id=m.id and r.id="
//									+ m.getId()
//									+ " and r.resource_type="
//									+ m.getType();
//							Map iMap = getJdbcTemplate().queryForMap(imageSql);
//							m.setFileSize(iMap.get("file_size").toString());
//							break;
//						}
//					}
//				});
//		return materials;
//	}
	
	/**
	 * 根据素材ID获取图片文件大小
	 * @param id
	 * @return
	 */
	public String getImageMateFileSize(Integer id) {
		String sql = "select m.file_size from t_resource r,t_image_meta m where r.resource_id=m.id and r.id="+ id;
		String fileSize = null;
		List<String> list = getJdbcTemplate().query(sql,
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int num)
							throws SQLException {
						String str = rs.getString("file_size");
						return str;
					}
				});
		if (list != null && list.size() > 0) {
			fileSize = list.get(0);
		}
		return fileSize;
	}
	
	/**
	 * 获取素材和文件大小关系
	 * @param mateIds
	 * @return
	 */
	public Map<Integer,String> getImageMateFileSizeMap(String mateIds) {
		String sql = "select distinct r.id,m.file_size " +
				"from t_resource r,t_image_meta m where r.resource_id=m.id and r.id in ("+ mateIds + ")";
		final Map<Integer, String> map = new HashMap<Integer, String>();
		getJdbcTemplate().query(sql, new RowMapper<Object>() {
					@Override
					public Object mapRow(ResultSet rs, int num)
							throws SQLException {
						map.put(rs.getInt("id"), rs.getString("file_size"));
						return null;
					}
				});
		return map;
	}
	
	public void insertPlayList(final List<PlayListGis> playLists) {
		String sql = "insert into ad_playlist_gis(ploy_id,start_time,end_time,content_path,content_type,ad_site_code,"
				+ "characteristic_identification,service_id,areas,userindustrys,userlevels,tvn,state,contract_id,"
				+ "order_id,content_id,category_id,asset_id,priority,menu_type_code) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		for (int i = 0; i < playLists.size(); i++) {
			final PlayListGis playList = playLists.get(i);
			getJdbcTemplate().execute(
					sql,
					new AbstractLobCreatingPreparedStatementCallback(
							this.lobHandler) {
						@Override
						protected void setValues(PreparedStatement ps,
								LobCreator lobCreator) throws SQLException,
								DataAccessException {
							ps.setInt(1, playList.getPloyId());
							ps.setTimestamp(2, new Timestamp(playList
									.getStartTime().getTime()));
							ps.setTimestamp(3, new Timestamp(playList
									.getEndTime().getTime()));
							ps.setString(4, playList.getContentPath());
							ps.setString(5, playList.getContentType());
							ps.setString(6, playList.getAdSiteCode());
							ps.setString(7, playList
									.getCharacteristicIdentification());
							lobCreator.setClobAsString(ps, 8, playList.getServiceId());
							lobCreator.setClobAsString(ps, 9, playList.getAreas());
							ps.setString(10, playList.getUserIndustrys());
							ps.setString(11, playList.getUserLevels());
							ps.setString(12, playList.getTvn());
							ps.setInt(13, playList.getState());
							ps.setInt(14, playList.getContractId());
							ps.setInt(15, playList.getOrderId());
							ps.setString(16, playList.getContentId());
							lobCreator.setClobAsString(ps, 17, playList.getCategoryId());
							lobCreator.setClobAsString(ps, 18, playList.getAssetId());
							ps.setInt(19, playList.getPriority());
							ps.setString(20, playList.getMenuTypeCode());
						}
					});
		}

	}

	/**
	 * 未执行的播出单，物理删除播出单记录
	 * 已经准星的播出单，将播出单结束时间修改为当前时间
	 */
	public void deleteAllPlayList(Integer orderId) {
		String sql = "delete from ad_playlist_gis where start_time >= now() and order_id=" + orderId;
		getJdbcTemplate().update(sql);
		sql = "update ad_playlist_gis set end_time=now() where end_time > now() and order_id=" + orderId;
		getJdbcTemplate().update(sql);
		
	}


	public void deletePlayListByDate(Integer orderId, String endTime) {
		String sql = "delete from ad_playlist_gis where order_id=? and end_time>str_to_date(?,'%Y-%m-%d %H:%i:%S')";
		getJdbcTemplate().update(sql, new Object[] { orderId, endTime });

	}


	public Date getPlayListEndTime(Integer orderId) {
		String sql = "select max(end_time) from ad_playlist_gis where order_id =" + orderId;
		return getJdbcTemplate().queryForObject(sql, Date.class);
	}


	public void updateEndTime(final Integer orderId, final Date endTime) {
		String sql = "update ad_playlist_gis set end_time=? where order_id=?";
		getJdbcTemplate().update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setTimestamp(1, new Timestamp(endTime.getTime()));
				ps.setInt(2, orderId);

			}
		});

	}


	public PlayListGis getPlayList(Integer orderId) {
		String sql = "select * from ad_playlist_gis where id=(select max(id) from ad_playlist_gis where order_id=?)";
		PlayListGis pl = getJdbcTemplate().queryForObject(sql,
				new Object[] { orderId }, new PlayListGis());
		return pl;
	}


	public String getContentPath(Integer orderId) {
		String sql = "select distinct(content_path) from ad_playlist_gis where order_id="
				+ orderId;
		String contentPath = null;
		List<String> paths = getJdbcTemplate().query(sql,
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int num)
							throws SQLException {
						String str = rs.getString("content_path");
						return str;
					}
				});
		if (paths != null && paths.size() > 0) {
			contentPath = paths.get(0);
		}
		return contentPath;
	}

	/**
	 * 根据订单ID查询订单对应的播出单ID列表
	 * @param orderId
	 * @return
	 */
	public List<Integer> getPlayListIds(Integer orderId) {
		String sql = "select id from ad_playlist_gis where order_id="+orderId;
		List<Integer> ids = getJdbcTemplate().query(sql, new RowMapper<Integer>(){
			@Override
			public Integer mapRow(ResultSet rs, int num) throws SQLException {
				Integer id = rs.getInt("id");
				return id;
			}
		});
		return ids;
	}

	/**
	 * 根据订单ID重新投放
	 * @param orderId
	 * @return
	 */
	public void repush(Integer orderId) {
	String sql="UPDATE ad_playlist_gis SET START_TIME = NOW(), STATE = 0,re_push='1' WHERE ORDER_ID ="+orderId+ " AND STATE = 2 AND END_TIME > NOW();";
//	Query query = this.getSession().createSQLQuery(sql);
//	query.executeUpdate();
	String sql1="update t_order set state=6 where ID = "+orderId;
	getJdbcTemplate().update(sql);
	getJdbcTemplate().update(sql1);
	}

	
	
}
