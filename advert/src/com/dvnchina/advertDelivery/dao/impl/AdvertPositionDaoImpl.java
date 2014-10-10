package com.dvnchina.advertDelivery.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.dao.AdvertPositionDao;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.PositionOccupyStatesInfo;
import com.dvnchina.advertDelivery.utils.Transform;

public class AdvertPositionDaoImpl extends JdbcDaoSupport implements
		AdvertPositionDao {

	@Override
	public int removeAdvertPosition(final int advertPositionId) {
		String removeSql = "DELETE FROM T_ADVERTPOSITION WHERE ID=?";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,advertPositionId);
			}
		});
	}
	
	public int[] removeBatchAdvertPosition(final String[] apIdList) {
		String removeSql = "DELETE FROM T_ADVERTPOSITION WHERE ID=?";
		return getJdbcTemplate().batchUpdate(removeSql,new BatchPreparedStatementSetter(){

			@Override
			public int getBatchSize() {
				return apIdList.length;
			}

			@Override
			public void setValues(PreparedStatement ps, int index)
					throws SQLException {
				String ids = apIdList[index];
				if(StringUtils.isNotBlank(ids)){
					ps.setInt(1,Integer.valueOf(ids));
				}
				
			}
			
		});
	}

	@Override
	public int saveAdvertPosition(final AdvertPosition advertPosition) {
		String insertSql = "INSERT INTO T_ADVERTPOSITION(ID,CHARACTERISTIC_IDENTIFICATION,POSITION_NAME,IMAGE_RULE_ID,VIDEO_RULE_ID,TEXT_RULE_ID,QUESTION_RULE_ID,IS_HD,IS_ADD,IS_LOOP,MATERIAL_NUMBER,DELIVERY_MODE,PRICE,BACKGROUND_PATH,OPERATION_ID,CREATE_TIME,POSITION_TYPE_ID,COORDINATE,WIDTH_HEIGHT) VALUES(T_ADVERTPOSITION_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return getJdbcTemplate().update(insertSql,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, advertPosition.getCharacteristicIdentification());
						ps.setString(2, advertPosition.getPositionName());
						
						ps.setInt(3, (advertPosition.getImageRuleId()!=null)?advertPosition.getImageRuleId():0);
						ps.setInt(4, (advertPosition.getVideoRuleId()!=null)?advertPosition.getVideoRuleId():0);
						ps.setInt(5, (advertPosition.getTextRuleId()!=null)?advertPosition.getTextRuleId():0);
						ps.setInt(6, (advertPosition.getQuestionRuleId()!=null)?advertPosition.getQuestionRuleId():0);
						
						ps.setInt(7, advertPosition.getIsHd());
						ps.setInt(8, advertPosition.getIsAdd());
						ps.setInt(9, advertPosition.getIsLoop());
						ps.setInt(10, advertPosition.getMaterialNumber());
						ps.setInt(11, advertPosition.getDeliveryMode());
						ps.setString(12, advertPosition.getPrice());
						ps.setString(13, advertPosition.getBackgroundPath());
						ps.setString(14, advertPosition.getOperationId());
						ps.setDate(15, new Date(advertPosition.getCreateTime().getTime()));
						ps.setInt(16, advertPosition.getPositionTypeId());
						ps.setString(17, advertPosition.getCoordinate());
						ps.setString(18, advertPosition.getWidthHeight());
					}
				});

	}

	@Override
	public List<AdvertPosition> page(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
				+ sql + ") row_ where rownum <= ?) where rownum_ >?";
		RowMapper<AdvertPosition> rowMapper = BeanPropertyRowMapper.newInstance(AdvertPosition.class);
		return getJdbcTemplate().query(pageSql, new Object[] { end, start },rowMapper);
	}

	/**
	 * SELECT COUNT(*) FROM t_advertposition
	 */
	@Override
	public int queryTotalCount(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}

	@Override
	public int updateAdvertPosition(final AdvertPosition advertPosition) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE t_advertposition").append(
				" SET CHARACTERISTIC_IDENTIFICATION = ?,").append(
				"POSITION_NAME                 = ?,").append(
				"IMAGE_RULE_ID                 = ?,").append(
				"VIDEO_RULE_ID                 = ?,").append(
				"TEXT_RULE_ID                  = ?,").append(
				"QUESTION_RULE_ID              = ?,").append(
				"IS_HD                         = ?,").append(
				"IS_ADD                        = ?,").append(
				"IS_LOOP                       = ?,").append(
				"MATERIAL_NUMBER               = ?,").append(
				"DELIVERY_MODE                 = ?,").append(
				"PRICE                         = ?,").append(
				"BACKGROUND_PATH               = ?,").append(
				"OPERATION_ID                  = ?,").append(
				"CREATE_TIME                   = ?,").append(
				"POSITION_TYPE_ID              = ?,").append(
				"COORDINATE             	   = ?,").append(
				"WIDTH_HEIGHT                  = ?,").append(
				"MODIFY_TIME                  = ?").append(" WHERE ID = ?");
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, advertPosition.getCharacteristicIdentification());
						ps.setString(2, advertPosition.getPositionName());
						
						ps.setInt(3, (advertPosition.getImageRuleId()!=null)?advertPosition.getImageRuleId():0);
						ps.setInt(4, (advertPosition.getVideoRuleId()!=null)?advertPosition.getVideoRuleId():0);
						ps.setInt(5, (advertPosition.getTextRuleId()!=null)?advertPosition.getTextRuleId():0);
						ps.setInt(6, (advertPosition.getQuestionRuleId()!=null)?advertPosition.getQuestionRuleId():0);
						
						ps.setInt(7, advertPosition.getIsHd());
						ps.setInt(8, advertPosition.getIsAdd());
						ps.setInt(9, advertPosition.getIsLoop());
						ps.setInt(10, advertPosition.getMaterialNumber());
						ps.setInt(11, advertPosition.getDeliveryMode());
						ps.setString(12, advertPosition.getPrice());
						ps.setString(13, advertPosition.getBackgroundPath());
						ps.setString(14, advertPosition.getOperationId());
						ps.setDate(15, new Date(advertPosition.getCreateTime().getTime()));
						ps.setInt(16, advertPosition.getPositionTypeId());
						ps.setString(17, advertPosition.getCoordinate());
						ps.setString(18, advertPosition.getWidthHeight());
						ps.setDate(19, new Date(advertPosition.getModifyTime().getTime()));
						ps.setInt(20, advertPosition.getId());
					}
				});
	}

	@Override
	public List<AdvertPosition> find(String sql) {
		RowMapper<AdvertPosition> rowMapper = BeanPropertyRowMapper.newInstance(AdvertPosition.class);
		return getJdbcTemplate().query(sql,rowMapper);
	}

	@Override
	public int[] saveBatchAdvertPosition(final List<AdvertPosition> advertPositionList) {
		String insertSql = "INSERT INTO T_ADVERTPOSITION(ID,CHARACTERISTIC_IDENTIFICATION,POSITION_NAME,IMAGE_RULE_ID,VIDEO_RULE_ID,TEXT_RULE_ID,QUESTION_RULE_ID,IS_HD,IS_ADD,IS_LOOP,MATERIAL_NUMBER,DELIVERY_MODE,PRICE,BACKGROUND_PATH,OPERATION_ID,CREATE_TIME,POSITION_TYPE_ID,COORDINATE,WIDTH_HEIGHT,MODIFY_TIME) VALUES(T_ADVERTPOSITION_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return getJdbcTemplate().batchUpdate(insertSql,new BatchPreparedStatementSetter(){

			@Override
			public int getBatchSize() {
				return advertPositionList.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int index)
					throws SQLException {
				AdvertPosition advertPosition = advertPositionList.get(index);
				ps.setString(1, advertPosition.getCharacteristicIdentification());
				ps.setString(2, advertPosition.getPositionName());
				
				ps.setInt(3, (advertPosition.getImageRuleId()!=null)?advertPosition.getImageRuleId():0);
				ps.setInt(4, (advertPosition.getVideoRuleId()!=null)?advertPosition.getVideoRuleId():0);
				ps.setInt(5, (advertPosition.getTextRuleId()!=null)?advertPosition.getTextRuleId():0);
				ps.setInt(6, (advertPosition.getQuestionRuleId()!=null)?advertPosition.getQuestionRuleId():0);
				
				ps.setInt(7, advertPosition.getIsHd());
				ps.setInt(8, advertPosition.getIsAdd());
				ps.setInt(9, advertPosition.getIsLoop());
				ps.setInt(10, advertPosition.getMaterialNumber());
				ps.setInt(11, advertPosition.getDeliveryMode());
				ps.setString(12, advertPosition.getPrice());
				ps.setString(13, advertPosition.getBackgroundPath());
				ps.setString(14, advertPosition.getOperationId());
				ps.setDate(15, new Date(advertPosition.getCreateTime().getTime()));
				ps.setInt(16, advertPosition.getPositionTypeId());
				ps.setString(17, advertPosition.getCoordinate());
				ps.setString(18, advertPosition.getWidthHeight());
				ps.setDate(19, new Date(advertPosition.getModifyTime().getTime()));
			}
		});
	}

	@Override
	public int[] updateBatchAdvertPosition(
			final List<AdvertPosition> advertPositionList) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE t_advertposition").append(
				" SET CHARACTERISTIC_IDENTIFICATION = ?,").append(
				"POSITION_NAME                 = ?,").append(
				"IMAGE_RULE_ID                 = ?,").append(
				"VIDEO_RULE_ID                 = ?,").append(
				"TEXT_RULE_ID                  = ?,").append(
				"QUESTION_RULE_ID              = ?,").append(
				"IS_HD                         = ?,").append(
				"IS_ADD                        = ?,").append(
				"IS_LOOP                       = ?,").append(
				"MATERIAL_NUMBER               = ?,").append(
				"DELIVERY_MODE                 = ?,").append(
				"PRICE                         = ?,").append(
				"BACKGROUND_PATH               = ?,").append(
				"OPERATION_ID                  = ?,").append(
				"CREATE_TIME                   = ?,").append(
				"POSITION_TYPE_ID              = ?,").append(
				"COORDINATE             = ?,").append(
				"WIDTH_HEIGHT           = ?").append(" WHERE ID = ?");
		return getJdbcTemplate().batchUpdate(updateSql.toString(),new BatchPreparedStatementSetter(){

			@Override
			public int getBatchSize() {
				return advertPositionList.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int index)
					throws SQLException {
				AdvertPosition advertPosition = advertPositionList.get(index);
				ps.setString(1, advertPosition.getCharacteristicIdentification());
				ps.setString(2, advertPosition.getPositionName());
				
				ps.setInt(3, (advertPosition.getImageRuleId()!=null)?advertPosition.getImageRuleId():0);
				ps.setInt(4, (advertPosition.getVideoRuleId()!=null)?advertPosition.getVideoRuleId():0);
				ps.setInt(5, (advertPosition.getTextRuleId()!=null)?advertPosition.getTextRuleId():0);
				ps.setInt(6, (advertPosition.getQuestionRuleId()!=null)?advertPosition.getQuestionRuleId():0);
				
				ps.setInt(7, advertPosition.getIsHd());
				ps.setInt(8, advertPosition.getIsAdd());
				ps.setInt(9, advertPosition.getIsLoop());
				ps.setInt(10, advertPosition.getMaterialNumber());
				ps.setInt(11, advertPosition.getDeliveryMode());
				ps.setString(12, advertPosition.getPrice());
				ps.setString(13, advertPosition.getBackgroundPath());
				ps.setString(14, advertPosition.getOperationId());
				ps.setDate(15, new Date(advertPosition.getCreateTime().getTime()));
				ps.setInt(16, advertPosition.getPositionTypeId());
				ps.setString(17, advertPosition.getCoordinate());
				ps.setString(18, advertPosition.getWidthHeight());
				ps.setInt(19, advertPosition.getId());
			}
		});
	}

	@Override
	public List<AdvertPosition> getAdvertPositionById(Integer advertPositionId) {
		StringBuffer queryPosition = new StringBuffer();
		queryPosition.append("SELECT * FROM t_advertposition");
		queryPosition.append(" WHERE id=?");
		RowMapper<AdvertPosition> rowMappper = BeanPropertyRowMapper.newInstance(AdvertPosition.class);
		return getJdbcTemplate().query(queryPosition.toString(),new Object[]{advertPositionId},rowMappper);
	}

	@Override
	public List<PositionOccupyStatesInfo> page4OccupyStates(String sql,Integer type,Integer positionId,
			int start, int end,String startDate,String endDate) {
		Object[] param = null;
		List condition = new ArrayList();
		if(type.intValue()==1){
			
			if(positionId!=null){
				condition.add(positionId);
			}
			
			try {
				if (StringUtils.isNotBlank(startDate)) {
					condition.add(Transform.string4SqlDateYYYYMMDD(startDate));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(StringUtils.isNotBlank(endDate)){
				try {
					condition.add(Transform.string4SqlDateYYYYMMDD(endDate));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			condition.add(end);
			condition.add(start);
			param = condition.toArray();

		}else if(type.intValue()==2){
			param = new Object[] {positionId,end, start };
		}else if(type.intValue()==3){
			param = new Object[] {positionId,end, start };
		}
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= ?) where rownum_ >?";
		
		RowMapper<PositionOccupyStatesInfo> rowMapper = BeanPropertyRowMapper.newInstance(PositionOccupyStatesInfo.class);  
		return getJdbcTemplate().query(pageSql,param,rowMapper);
	}

	@Override
	public int saveAdvertPositionReturnPrimaryKey(final AdvertPosition advertPosition) {
		String querySequence= "SELECT T_ADVERTPOSITION_SEQ.nextval FROM DUAL";
		Integer id = this.getJdbcTemplate().queryForInt(querySequence);
		advertPosition.setId(id);
		
		String insertSql = "INSERT INTO T_ADVERTPOSITION(ID,CHARACTERISTIC_IDENTIFICATION,POSITION_NAME,IMAGE_RULE_ID,VIDEO_RULE_ID,TEXT_RULE_ID,QUESTION_RULE_ID,IS_HD,IS_ADD,IS_LOOP,MATERIAL_NUMBER,DELIVERY_MODE,PRICE,BACKGROUND_PATH,OPERATION_ID,CREATE_TIME,POSITION_TYPE_ID,COORDINATE,WIDTH_HEIGHT,MODIFY_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		getJdbcTemplate().update(insertSql,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, advertPosition.getId());
						ps.setString(2, advertPosition.getCharacteristicIdentification());
						ps.setString(3, advertPosition.getPositionName());
						
						ps.setInt(4, (advertPosition.getImageRuleId()!=null)?advertPosition.getImageRuleId():0);
						ps.setInt(5, (advertPosition.getVideoRuleId()!=null)?advertPosition.getVideoRuleId():0);
						ps.setInt(6, (advertPosition.getTextRuleId()!=null)?advertPosition.getTextRuleId():0);
						ps.setInt(7, (advertPosition.getQuestionRuleId()!=null)?advertPosition.getQuestionRuleId():0);
						
						ps.setInt(8, advertPosition.getIsHd());
						ps.setInt(9, advertPosition.getIsAdd());
						ps.setInt(10, advertPosition.getIsLoop());
						ps.setInt(11, advertPosition.getMaterialNumber());
						ps.setInt(12, advertPosition.getDeliveryMode());
						ps.setString(13, advertPosition.getPrice());
						ps.setString(14, advertPosition.getBackgroundPath());
						ps.setString(15, advertPosition.getOperationId());
						ps.setDate(16, new Date(advertPosition.getCreateTime().getTime()));
						ps.setInt(17, advertPosition.getPositionTypeId());
						ps.setString(18, advertPosition.getCoordinate());
						ps.setString(19, advertPosition.getWidthHeight());
						ps.setDate(20, new Date(advertPosition.getModifyTime().getTime()));
					}
				});
		return id;
	}

	@Override
	public List<Integer> getAdvertPositionOccupyStatus(
			List<AdvertPosition> advertPositionList) {
		StringBuffer queryString = new StringBuffer();
		StringBuffer queryConditionSb = new StringBuffer();
		queryString.append("select * from VIEW_AP_OCCUPY_STATUS ");
		String queryCondition = "";
		int size = advertPositionList.size();
		
		if(size>0){
			queryString.append(" WHERE ad_id IN(");
			for (int i = 0; i < size; i++) {
				queryConditionSb.append("?,");
			}
			queryCondition=queryConditionSb.toString();
			
			if(queryCondition.lastIndexOf(",")!=-1){
				queryCondition=queryCondition.substring(0,queryCondition.lastIndexOf(","));
			}
			queryString.append(queryCondition);
			queryString.append(")");
		}
		
		List<Integer> positionIdList = new ArrayList<Integer>();
		for (AdvertPosition advertPosition : advertPositionList) {
			positionIdList.add(advertPosition.getId());
		}
		
		return getJdbcTemplate().query(queryString.toString(),positionIdList.toArray(),new RowMapper<Integer>(){
			@Override
			public Integer mapRow(ResultSet rs, int index) throws SQLException {
				return rs.getInt("ad_id");
			}
			
		});
		
	}

}
