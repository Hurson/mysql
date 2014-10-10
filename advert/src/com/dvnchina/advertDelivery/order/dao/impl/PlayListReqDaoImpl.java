package com.dvnchina.advertDelivery.order.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;

import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.order.bean.OrderMaterialRelation;
import com.dvnchina.advertDelivery.order.bean.PlayListReq;
import com.dvnchina.advertDelivery.order.bean.PlayListReqContent;
import com.dvnchina.advertDelivery.order.bean.PlayListReqPContent;
import com.dvnchina.advertDelivery.order.bean.PlayListReqPrecise;
import com.dvnchina.advertDelivery.order.bean.playlist.MaterialBean;
import com.dvnchina.advertDelivery.order.bean.playlist.TextMate;
import com.dvnchina.advertDelivery.order.dao.PlayListReqDao;
import com.dvnchina.advertDelivery.order.dao.PlayListReqPreciseDao;
import com.dvnchina.advertDelivery.ploy.bean.TPreciseMatch;

public class PlayListReqDaoImpl extends PlayListDaoImpl implements
		PlayListReqDao {
	private DefaultLobHandler lobHandler;
	private PlayListReqPreciseDao playListReqPreciseDao;

	public DefaultLobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(DefaultLobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public void setPlayListReqPreciseDao(
			PlayListReqPreciseDao playListReqPreciseDao) {
		this.playListReqPreciseDao = playListReqPreciseDao;
	}

//	public List<MaterialBean> getMaterialByOrder(Integer orderId) {
//		String sql = "select rel.mate_id,rel.poll_index,rel.play_location,rel.precise_id,r.resource_type,"
//			+ "p.match_name,p.precisetype,p.product_id,p.asset_name,p.asset_key,p.asset_sort_id,p.dtv_channel_id,p.playback_channel_id,"
//			+ "p.lookback_category_id,p.user_area,p.userindustrys,p.userlevels,p.tvn_number,p.tvn_expression,p.priority,p.ploy_id "
//			+ "from t_order_mate_rel rel left join t_precise_match p on rel.precise_id=p.id,t_resource r "
//			+ "where rel.mate_id=r.id and order_id=?";
//		List<MaterialBean> materials = getJdbcTemplate().query(sql,
//				new Object[] { orderId }, new RowMapper<MaterialBean>() {
//					@Override
//					public MaterialBean mapRow(ResultSet rs, int num)
//							throws SQLException {
//						MaterialBean m = new MaterialBean();
//						m.setId(rs.getInt("mate_id"));
//						m.setLoopNo(rs.getInt("poll_index"));
//						m.setPlayLocation(rs.getString("play_location"));
//						m.setType(rs.getInt("resource_type"));
//
//						if (rs.getInt("precise_id") != 0) {
//							TPreciseMatch p = new TPreciseMatch();
//							p.setId(rs.getLong("precise_id"));
//							p.setMatchName(rs.getString("match_name"));
//							p.setPrecisetype(rs.getLong("precisetype"));
//							p.setProductId(getValueByIds("biz_id","t_productinfo",rs.getString("product_id")));
//							p.setAssetIds(getValueByIds("asset_id","t_assetinfo",rs.getString("asset_name")));
//							p.setAssetKey(rs.getString("asset_key"));
//							p.setAssetSortId(getValueByIds("category_id","t_categoryinfo",rs.getString("asset_sort_id")));
//							p.setDtvChannelId(getDtvServiceId(rs.getString("dtv_channel_id")));
////							p.setPlaybackChannelId(getPlaybackServiceId(rs.getString("playback_channel_id")));
//							p.setPlaybackChannelId(rs.getString("playback_channel_id"));
//							p.setLookbackCategoryId(getValueByIds("category_id","t_loopback_category",rs.getString("lookback_category_id")));
//							p.setUserArea(rs.getString("user_area"));
//							p.setUserindustrys(getValueByIds("user_industry_category_code","t_user_industry_category",rs.getString("userindustrys")));
//							p.setUserlevels(getValueByIds("user_rank_code","t_user_rank",rs.getString("userlevels")));
//							p.setTvnNumber(rs.getString("tvn_number"));
//							p.setTvnExpression(rs.getString("tvn_expression"));
//							p.setPriority(rs.getShort("priority"));
//							p.setPloyId(rs.getLong("ploy_id"));
//							m.setPrecise(p);
//						}
//						getMaterialById(m);
//						return m;
//					}
//					
//					/**
//					 * 根据ids获取对应的值
//					 * @param field
//					 * @param table
//					 * @param ids
//					 * @return
//					 */
//					private String getValueByIds(String field,String table,String ids){
//						if(StringUtils.isEmpty(ids)){
//							return null;
//						}
//						StringBuffer vSql = new StringBuffer();
//						vSql.append("select ").append(field).append(" from ").append(table).append(" where id in (");
//						String[] vIds = ids.split(",");
//						StringBuffer value = new StringBuffer();
//						if(vIds.length>0){
//							for(int i = 0;i<vIds.length;i++){
//								if(i>0){
//									vSql.append(",");
//								}
//								Integer cId = Integer.parseInt(vIds[i]);
//								vSql.append(cId);
//							}
//							vSql.append(")");
//							List<Map<String,Object>> sIds = getJdbcTemplate().queryForList(vSql.toString());
//							
//							for(int i = 0;i<sIds.size();i++){
//								Map<String,Object> sId = sIds.get(i);
//								if(i>0){
//									value.append(",");
//								}
//								value.append(sId.get(field));
//							}
//						}
//						return value.toString();
//					}
//					
//					//DTV根据频道ID获取serviceId
//					private String getDtvServiceId(String channelIds){
//						if(StringUtils.isEmpty(channelIds)){
//							return null;
//						}
//						StringBuffer cSql = new StringBuffer("select c.service_id from t_channelinfo c where c.channel_id in (");
//						String[] cIds = channelIds.split(",");
//						StringBuffer serviceId = new StringBuffer();
//						if(cIds.length>0){
//							for(int i = 0;i<cIds.length;i++){
//								if(i>0){
//									cSql.append(",");
//								}
//								Integer cId = Integer.parseInt(cIds[i]);
//								cSql.append(cId);
//							}
//							cSql.append(")");
//							List<Map<String,Object>> sIds = getJdbcTemplate().queryForList(cSql.toString());
//							
//							for(int i = 0;i<sIds.size();i++){
//								Map<String,Object> sId = sIds.get(i);
//								if(i>0){
//									serviceId.append(",");
//								}
//								serviceId.append(sId.get("SERVICE_ID"));
//							}
//						}
//						return serviceId.toString();
//					}
//					//回放根据频道ID获取serviceId
//					private String getPlaybackServiceId(String channelIds){
//						if(StringUtils.isEmpty(channelIds)){
//							return null;
//						}
//						StringBuffer cSql = new StringBuffer("select c.service_id from t_channelinfo_npvr c where c.channel_id in (");
//						String[] cIds = channelIds.split(",");
//						StringBuffer serviceId = new StringBuffer();
//						if(cIds.length>0){
//							for(int i = 0;i<cIds.length;i++){
//								if(i>0){
//									cSql.append(",");
//								}
//								Integer cId = Integer.parseInt(cIds[i]);
//								cSql.append(cId);
//							}
//							cSql.append(")");
//							List<Map<String,Object>> sIds = getJdbcTemplate().queryForList(cSql.toString());
//							
//							for(int i = 0;i<sIds.size();i++){
//								Map<String,Object> sId = sIds.get(i);
//								if(i>0){
//									serviceId.append(",");
//								}
//								serviceId.append(sId.get("SERVICE_ID"));
//							}
//						}
//						return serviceId.toString();
//					}
//					
//					@SuppressWarnings("unchecked")
//					private void getMaterialById(final MaterialBean m) {
//						switch (m.getType()) {
//						case Constant.IMAGE:
////							String imageSql = "select m.formal_file_path||'/'||m.name as path from t_resource r,t_image_meta m "
//							String imageSql = "select m.name as path from t_resource r,t_image_meta m "
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
//							String qSql = "select q.file_path from t_resource r,t_questionnaire_real q  where "
//									+ "r.resource_id=q.id and r.id="
//									+ m.getId()
//									+ " and r.resource_type="
//									+ m.getType();
//							Map qMap = getJdbcTemplate().queryForMap(qSql);
//							m.setPath(qMap.get("file_path").toString());
//							break;
//						}
//					}
//				});
//		return materials;
//	}
	
	public List<MaterialBean> getMaterialByOrder(Integer orderId) {
		String sql = "select rel.mate_id,rel.poll_index,rel.play_location,rel.precise_id,rel.type,rel.start_time,rel.end_time,r.resource_type,"
			+ "p.match_name,p.precisetype,p.product_id,p.asset_name,p.asset_key,p.asset_sort_id,p.dtv_channel_id,p.playback_channel_id,"
			+ "p.lookback_category_id,p.user_area,p.user_area2,p.user_area3,p.userindustrys,p.userlevels,p.tvn_number,p.tvn_expression,p.priority,p.ploy_id "
			+ "from t_order_mate_rel rel left join t_precise_match p on rel.precise_id=p.id,t_resource r "
			+ "where rel.mate_id=r.id and order_id=?";
		System.out.println(sql);
		List<MaterialBean> materials = getJdbcTemplate().query(sql,
				new Object[] { orderId }, new RowMapper<MaterialBean>() {
					@Override
					public MaterialBean mapRow(ResultSet rs, int num)
							throws SQLException {
						MaterialBean m = new MaterialBean();
						m.setId(rs.getInt("mate_id"));
						m.setLoopNo(rs.getInt("poll_index"));
						m.setPlayLocation(rs.getString("play_location"));
						m.setType(rs.getInt("resource_type"));
						m.setPreciseType(rs.getInt("type"));
						m.setStartTime(rs.getString("start_time"));
						m.setEndTime(rs.getString("end_time"));
						
						if (rs.getInt("type") == 0) {
							TPreciseMatch p = new TPreciseMatch();
							p.setId(rs.getLong("precise_id"));
							p.setMatchName(rs.getString("match_name"));
							p.setPrecisetype(rs.getLong("precisetype"));
							p.setProductId(getValueByIds("biz_id","t_productinfo",rs.getString("product_id")));
//							p.setAssetIds(getAssetIds(rs.getString("asset_name")));
							p.setAssetIds(rs.getString("asset_name"));
							p.setAssetKey(rs.getString("asset_key"));
							p.setAssetSortId(getValueByIds("category_id","t_categoryinfo",rs.getString("asset_sort_id")));
							p.setDtvChannelId(getDtvServiceId(rs.getString("dtv_channel_id")));
							p.setPlaybackChannelId(rs.getString("playback_channel_id"));
							p.setLookbackCategoryId(getValueByIds("category_id","t_loopback_category",rs.getString("lookback_category_id")));
							p.setUserArea(rs.getString("user_area"));
							p.setUserArea2(rs.getString("user_area2"));
							p.setUserArea3(rs.getString("user_area3"));
							
							p.setUserindustrys(getValueByIds("user_industry_category_code","t_user_industry_category",rs.getString("userindustrys")));
							p.setUserlevels(getValueByIds("user_rank_code","t_user_rank",rs.getString("userlevels")));
							p.setTvnNumber(rs.getString("tvn_number"));
							p.setTvnExpression(rs.getString("tvn_expression"));
							p.setPriority(rs.getShort("priority"));
							p.setPloyId(rs.getLong("ploy_id"));
							m.setPrecise(p);
						}
						getMaterialById(m);
						return m;
					}
					
					/**
					 * 根据ids获取对应的值
					 * @param field
					 * @param table
					 * @param ids
					 * @return
					 */
					private String getValueByIds(String field,String table,String ids){
						if(StringUtils.isEmpty(ids)){
							return null;
						}
						StringBuffer vSql = new StringBuffer();
						vSql.append("select ").append(field).append(" from ").append(table).append(" where id in (");
						String[] vIds = ids.split(",");
						StringBuffer value = new StringBuffer();
						if(vIds.length>0){
							for(int i = 0;i<vIds.length;i++){
								if(i>0){
									vSql.append(",");
								}
								Integer cId = Integer.parseInt(vIds[i]);
								vSql.append(cId);
							}
							vSql.append(")");
							List<Map<String,Object>> sIds = getJdbcTemplate().queryForList(vSql.toString());
							
							for(int i = 0;i<sIds.size();i++){
								Map<String,Object> sId = sIds.get(i);
								if(i>0){
									value.append(",");
								}
								value.append(sId.get(field));
							}
						}
						return value.toString();
					}
					
					private String getAssetIds(String assetName){
						if(StringUtils.isEmpty(assetName)){
							return null;
						}
						String sql = "select asset_id from t_assetinfo where asset_name like '%"+assetName+"%'";
						List<Map<String,Object>> sIds = getJdbcTemplate().queryForList(sql);
						StringBuffer value = new StringBuffer();
						for(int i = 0;i<sIds.size();i++){
							Map<String,Object> sId = sIds.get(i);
							if(i>0){
								value.append(",");
							}
							value.append(sId.get("asset_id"));
						}
						return value.toString();
					}
					
					//DTV根据频道ID获取serviceId
					private String getDtvServiceId(String channelIds){
						if(StringUtils.isEmpty(channelIds)){
							return null;
						}
						StringBuffer cSql = new StringBuffer("select c.service_id from t_channelinfo c where c.channel_id in (");
						String[] cIds = channelIds.split(",");
						StringBuffer serviceId = new StringBuffer();
						if(cIds.length>0){
							for(int i = 0;i<cIds.length;i++){
								if(i>0){
									cSql.append(",");
								}
								Integer cId = Integer.parseInt(cIds[i]);
								cSql.append(cId);
							}
							cSql.append(")");
							List<Map<String,Object>> sIds = getJdbcTemplate().queryForList(cSql.toString());
							
							for(int i = 0;i<sIds.size();i++){
								Map<String,Object> sId = sIds.get(i);
								if(i>0){
									serviceId.append(",");
								}
								serviceId.append(sId.get("SERVICE_ID"));
							}
						}
						return serviceId.toString();
					}
					
					@SuppressWarnings("unchecked")
					private void getMaterialById(final MaterialBean m) {
						switch (m.getType()) {
						case Constant.IMAGE:
							String imageSql = "select m.name as path from t_resource r,t_image_meta m "
									+ "where r.resource_id=m.id and r.id="
									+ m.getId()
									+ " and r.resource_type="
									+ m.getType();
							Map iMap = getJdbcTemplate().queryForMap(imageSql);
							m.setPath(iMap.get("path").toString());
							break;
						case Constant.VIDEO:
							String videoSql = "select CONCAT(m.video_pump_path,'/',m.name) as path from t_resource r,t_video_meta m  "
									+ "where r.resource_id=m.id and r.id="
									+ m.getId()
									+ " and r.resource_type="
									+ m.getType();
							Map vMap = getJdbcTemplate().queryForMap(videoSql);
							m.setPath(vMap.get("path").toString());
							break;
						case Constant.WRITING:
							String textSql = "select m.id,m.name,m.content,m.url,m.action,m.duration_time,"
									+"m.font_size,m.font_color,m.background_color,m.roll_speed,"
									+"m.position_vertex_coordinates,m.position_width_height from t_resource r,T_TEXT_META m "
									+ " where r.resource_id=m.id and r.id="
									+ m.getId()
									+ " and r.resource_type="
									+ m.getType();
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
									m.setText(text);
									return null;
								}
							});
							break;
						case Constant.QUESTIONNAIRE:
							String qSql = "select q.file_path from t_resource r,t_questionnaire_real q  where "
									+ "r.resource_id=q.id and r.id="
									+ m.getId()
									+ " and r.resource_type="
									+ m.getType();
							Map qMap = getJdbcTemplate().queryForMap(qSql);
							m.setPath(qMap.get("file_path").toString());
							break;
						}
					}
				});
		return materials;
	}

	public void insertPlayList(final PlayListReq playList) {
		String sql = "insert into ad_playlist_req(ploy_id,begin_date,end_date,begin,end,play_time,ad_site_code,"
				+ "characteristic_identification,channels,areas,userindustrys,userlevels,tvn,state,contract_id,"
				+ "order_id,user_number,questionnaire_number,integral_ratio) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		getJdbcTemplate().execute(
				sql,
				new AbstractLobCreatingPreparedStatementCallback(
						this.lobHandler) {
					@Override
					protected void setValues(PreparedStatement ps,
							LobCreator lobCreator) throws SQLException,
							DataAccessException {
						ps.setInt(1, playList.getPloyId());
						ps.setDate(2, new java.sql.Date(playList.getBeginDate().getTime()));
						if(playList.getEndDate()==null){
							//按次播放，结束时间为空
							ps.setDate(3, null);
						}else{
							ps.setDate(3, new java.sql.Date(playList.getEndDate().getTime()));
						}
						ps.setString(4, playList.getBegin());
						ps.setString(5, playList.getEnd());
						ps.setInt(6, playList.getPlayTime());
						ps.setString(7, playList.getAdSiteCode());
						ps.setString(8, playList.getCharacteristicIdentification());
						lobCreator.setClobAsString(ps, 9, playList.getChannels());
						lobCreator.setClobAsString(ps, 10, playList.getAreas());
						ps.setString(11, playList.getUserIndustrys());
						ps.setString(12, playList.getUserLevels());
						ps.setString(13, playList.getTvn());
						ps.setInt(14, playList.getState());
						ps.setInt(15, playList.getContractId());
						ps.setInt(16, playList.getOrderId());
						ps.setInt(17, playList.getUserNumber());
						ps.setInt(18, playList.getQuestionnaireNumber());
						ps.setString(19, playList.getIntegralRatio());
					}
				});
	}

	public void insertPlayList(List<PlayListReq> playLists) {
		for (PlayListReq p : playLists) {
			this.insertPlayList(p);
		}

	}

	public void insertContents(final List<PlayListReqContent> contents) {
		String sql = "insert into ad_playlist_req_content(playlist_id,content_type,content_path,content_id) values(?,?,?,?)";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, contents.get(i).getPlayListId());
				ps.setString(2, contents.get(i).getContentType());
				ps.setString(3, contents.get(i).getContentPath());
				ps.setString(4, contents.get(i).getContentId());
			}

			@Override
			public int getBatchSize() {
				return contents.size();
			}
		});
	}
	
	public void insertPContent(final List<PlayListReqPContent> pContents) {
		String sql = "insert into ad_playlist_req_p_content(precision_id,content_path,content_type,content_id) "
				+ "values(?,?,?,?)";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, pContents.get(i).getPreciseId());
				ps.setString(2, pContents.get(i).getContentPath());
				ps.setString(3, pContents.get(i).getContentType());
				ps.setString(4, pContents.get(i).getContentId());
			}

			@Override
			public int getBatchSize() {
				return pContents.size();
			}
		});

	}

	public Integer insertPrecise(PlayListReqPrecise precise) {
		return playListReqPreciseDao.insertPrecise(precise);

	}

	public void updateEndTime(Integer orderId, Date endDate) {
		String sql = "update ad_playlist_req set end_date=? where order_id=?";
		getJdbcTemplate().update(sql, new Object[] { endDate, orderId });

	}

	public Integer getPlayListId(Integer orderId) {
		String sql = "select max(id) from ad_playlist_req t where order_id=?";
		Integer id = getJdbcTemplate().queryForInt(sql,
				new Object[] { orderId });
		return id;
	}

	public Map<Integer, String> getPlayListMap(Integer orderId) {
		String sql = "select id,CHARACTERISTIC_IDENTIFICATION,begin from ad_playlist_req t where order_id=?";
		final Map<Integer, String> idMap = new HashMap<Integer, String>();
		getJdbcTemplate().query(sql, new Object[] { orderId },
				new RowMapper<Object>() {
					@Override
					public Object mapRow(ResultSet rs, int num)
							throws SQLException {
						idMap.put(rs.getInt("id"), rs
								.getString("CHARACTERISTIC_IDENTIFICATION")+","+rs.getString("begin"));
						return null;
					}
				});
		return idMap;
	}

	public Date getOrderEndDate(Integer orderId) {
		String sql = "select end_time from t_order where id=?";
		Date end = getJdbcTemplate().queryForObject(sql,
				new Object[] { orderId }, Date.class);
		return end;
	}

	public List<Integer> getPlayListIds(Integer orderId) {
		String sql = "select id from ad_playlist_req where order_id="+orderId;
		List<Integer> ids = getJdbcTemplate().query(sql,new RowMapper<Integer>(){
			@Override
			public Integer mapRow(ResultSet rs, int num) throws SQLException {
				Integer id = rs.getInt("id");
				return id;
			}
		});
		return ids;
	}
	
	/**
	 * 根据订单ID获取请求播出单列表
	 * @param orderId
	 * @return
	 */
	public List<PlayListReq> getPlayList(Integer orderId) {
		String sql = "select id,begin,end from ad_playlist_req where order_id="+orderId;
		List<PlayListReq> list = getJdbcTemplate().query(sql,new RowMapper<PlayListReq>(){
			@Override
			public PlayListReq mapRow(ResultSet rs, int num) throws SQLException {
				PlayListReq req = new PlayListReq();
				req.setId(rs.getInt("id"));
				req.setBegin(rs.getString("begin"));
				req.setEnd(rs.getString("end"));
				return req;
			}
		});
		return list;
	}

	/**
	 * 根据播出单ID获取请求式精准投放表ID
	 */
	public List<Integer> getPreciseIds(List<Integer> playListIds) {
		StringBuffer sql = new StringBuffer("select id from ad_playlist_req_precision where playlist_id in(");
		for(int i = 0;i<playListIds.size();i++){
			if(i>0){
				sql.append(",");
			}
			sql.append(playListIds.get(i));
		}
		sql.append(")");
		List<Integer> ids = getJdbcTemplate().query(sql.toString(),new RowMapper<Integer>(){
			@Override
			public Integer mapRow(ResultSet rs, int num) throws SQLException {
				Integer id = rs.getInt("id");
				return id;
			}
		});
		return ids;
	}

	public void deletePlayLists(List<Integer> playListIds,
			List<Integer> preciseIds) {
		StringBuffer delPContentSql = new StringBuffer(
				"delete from ad_playlist_req_p_content where precision_id in(");
		StringBuffer delPrecisionSql = new StringBuffer(
				"delete from ad_playlist_req_precision where id in(");
		for (int i = 0; i < preciseIds.size(); i++) {
			if (i > 0) {
				delPContentSql.append(",");
				delPrecisionSql.append(",");
			}
			delPContentSql.append(preciseIds.get(i));
			delPrecisionSql.append(preciseIds.get(i));
		}
		delPContentSql.append(")");
		delPrecisionSql.append(")");
		StringBuffer delContentSql = new StringBuffer(
				"delete from ad_playlist_req_content where playlist_id in(");
		StringBuffer delPlayListsql = new StringBuffer(
				"delete from ad_playlist_req where id in(");
		for (int i = 0; i < playListIds.size(); i++) {
			if (i > 0) {
				delContentSql.append(",");
				delPlayListsql.append(",");
			}
			delContentSql.append(playListIds.get(i));
			delPlayListsql.append(playListIds.get(i));
		}
		delContentSql.append(")");
		delPlayListsql.append(")");
		getJdbcTemplate().batchUpdate(
				new String[] { delPContentSql.toString(),
						delPrecisionSql.toString(), delContentSql.toString(),
						delPlayListsql.toString() });
	}

	public void deletePlayLists(List<Integer> playListIds) {
		StringBuffer delContentSql = new StringBuffer(
				"delete from ad_playlist_req_content where playlist_id in(");
		StringBuffer delPlayListsql = new StringBuffer(
				"delete from ad_playlist_req where id in(");
		for (int i = 0; i < playListIds.size(); i++) {
			if (i > 0) {
				delContentSql.append(",");
				delPlayListsql.append(",");
			}
			delContentSql.append(playListIds.get(i));
			delPlayListsql.append(playListIds.get(i));
		}
		delContentSql.append(")");
		delPlayListsql.append(")");
		getJdbcTemplate().batchUpdate(
				new String[] { delContentSql.toString(),
						delPlayListsql.toString() });

	}

	public List<String> getPWritingContentPath(List<Integer> preciseIds) {
		StringBuffer sql = new StringBuffer("select content_path from ad_playlist_req_p_content where content_type=");
		sql.append(Constant.WRITING_MATERIAL_TYPE);
		sql.append(" and precision_id in(");
		for (int i = 0; i < preciseIds.size(); i++) {
			if (i > 0) {
				sql.append(",");
			}
			sql.append(preciseIds.get(i));
		}
		sql.append(")");
		List<String> paths = getJdbcTemplate().query(sql.toString(), new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int num) throws SQLException {
				String path = rs.getString("content_path");
				return path;
			}
		});
		return paths;
	}

	public List<String> getWritingContentPath(List<Integer> playListIds) {
		StringBuffer sql = new StringBuffer("select content_path from ad_playlist_req_content where content_type=");
		sql.append(Constant.WRITING_MATERIAL_TYPE);
		sql.append(" and playlist_id in(");
		for (int i = 0; i < playListIds.size(); i++) {
			if (i > 0) {
				sql.append(",");
			}
			sql.append(playListIds.get(i));
		}
		sql.append(")");
		List<String> paths = getJdbcTemplate().query(sql.toString(), new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int num) throws SQLException {
				String path = rs.getString("content_path");
				return path;
			}
		});
		return paths;
	}
	
	
//	publicff List<OrderMaterialRelation> getOrderMaterialRelationByOrder(Integer orderId) {
//		String sql = "select rel.mate_id,rel.poll_index,rel.play_location,rel.precise_id,rel.type"
//			+ "from t_order_mate_rel rel ,t_resource r "
//			+ "where rel.mate_id=r.id and order_id=?";
//		System.out.println(sql);
//		List<OrderMaterialRelation> materials = getJdbcTemplate().query(sql,
//				new Object[] { orderId }, new RowMapper<OrderMaterialRelation>() {
//					@Override
//					public OrderMaterialRelation mapRow(ResultSet rs, int num)
//							throws SQLException {
//						OrderMaterialRelation rel = new OrderMaterialRelation();
//						rel.setMateId(rs.getInt("mate_id"));
//						rel.setPlayLocation(rs.getString("play_location"));
//						
//						return rel;
//					}
//				});
//		return materials;
//	}
}
