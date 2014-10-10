package com.dvnchina.advertDelivery.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.constant.CPSConstant;
import com.dvnchina.advertDelivery.dao.CPSPositionDao;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.PlayCategory;
import com.dvnchina.advertDelivery.model.PortalPlayCategory;

public class CPSPositionDaoImpl  extends JdbcDaoSupport implements CPSPositionDao {
	//共25个属性值
	private final String insertAdvertPositionSql = "INSERT INTO T_ADVERTPOSITION(ID,CHARACTERISTIC_IDENTIFICATION,POSITION_NAME,DESCRIPTION,IMAGE_RULE_ID,VIDEO_RULE_ID,TEXT_RULE_ID,QUESTION_RULE_ID,IS_HD,IS_ADD,IS_LOOP,MATERIAL_NUMBER,DELIVERY_MODE,PRICE,DISCOUNT,OPERATION_ID,STATE,CREATE_TIME,POSITION_TYPE_ID,BACKGROUND_PATH,COORDINATE,DELIVERY_PLATFORM,START_TIME,END_TIME,WIDTH_HEIGHT) VALUES(T_CATEGORY_TEMPLATE_INFO_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String updateAdvertPositionSql ="UPDATE T_ADVERTPOSITION SET CHARACTERISTIC_IDENTIFICATION=?,POSITION_NAME=?,DESCRIPTION=?,IMAGE_RULE_ID=?,VIDEO_RULE_ID=?,TEXT_RULE_ID=?,QUESTION_RULE_ID=?,IS_HD=?,IS_ADD=?,IS_LOOP=?,MATERIAL_NUMBER=?,DELIVERY_MODE=?,PRICE=?,DISCOUNT=?,OPERATION_ID=?,STATE=?,CREATE_TIME=?,POSITION_TYPE_ID=?,BACKGROUND_PATH=?,COORDINATE=?,DELIVERY_PLATFORM=?,START_TIME=?,END_TIME=?,WIDTH_HEIGHT=? WHERE ID=?";
	
	private final String insertPlayCategorySql = "INSERT INTO T_PLAT_CATEGORY(ID,CATEGORY_ID,CATEGORY_NAME,CATEGORY_TYPE,TEMPLATE_ID,TEMPLATE_NAME,CREATE_TIME,MODIFY_TIME) VALUES (?,?,?,?,?,?,?,?)";
	private final String updatePlayCategorySql = "UPDATE T_PLAT_CATEGORY SET CATEGORY_ID =?,CATEGORY_NAME=?,CATEGORY_TYPE=?,TEMPLATE_ID=?,TEMPLATE_NAME=?,CREATE_TIME=?,MODIFY_TIME=? where id=?";
	
	
	
	@Override
	public int[] saveAdvertPositionTemplateBatch(final List<AdvertPosition> listAdvertPosition) {
		return getJdbcTemplate().batchUpdate(insertAdvertPositionSql, new BatchPreparedStatementSetter(){

			@Override
			public int getBatchSize() {
				return listAdvertPosition.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				
				AdvertPosition advertPosition = listAdvertPosition.get(i);
				
				ps.setString(1, advertPosition.getCharacteristicIdentification());
				ps.setString(2, advertPosition.getPositionName());
				ps.setString(3, advertPosition.getDescription());
				ps.setInt(4, advertPosition.getImageRuleId());
				ps.setInt(5, advertPosition.getVideoRuleId());
				ps.setInt(6,advertPosition.getTextRuleId());
				ps.setInt(7,advertPosition.getQuestionRuleId());
				ps.setInt(8,advertPosition.getIsHd());
				ps.setInt(9,advertPosition.getIsAdd());
				ps.setInt(10,advertPosition.getIsLoop());
				ps.setInt(11,advertPosition.getMaterialNumber());
				ps.setInt(12,advertPosition.getDeliveryMode());
				ps.setString(13,advertPosition.getPrice());
				ps.setString(14,advertPosition.getDiscount());
//---可能有错
				ps.setInt(15,Integer.parseInt(advertPosition.getOperationId()));
//---可能有错
				ps.setString(16,String.valueOf(CPSConstant.CATEGORY_TEMPLATE_STATE_CREATE));
				ps.setDate(17, new Date(advertPosition.getCreateTime().getTime()));
				ps.setInt(18,advertPosition.getPositionTypeId());
				ps.setString(19,advertPosition.getBackgroundPath());
				ps.setString(20,advertPosition.getCoordinate());
				ps.setString(21,advertPosition.getDeliveryPlatform());
				ps.setString(22,advertPosition.getStartTime());
				ps.setString(23,advertPosition.getEndTime());
				ps.setString(24,advertPosition.getWidthHeight());
				
			}
			
		});
	}

	@Override
	public int[] updateAdvertPositionTemplateBatch(final List<AdvertPosition> listAdvertPosition, Integer operationType) {
		return getJdbcTemplate().batchUpdate(updateAdvertPositionSql,new BatchPreparedStatementSetter(){

			@Override
			public int getBatchSize() {
				return listAdvertPosition.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				
               AdvertPosition advertPosition = listAdvertPosition.get(i);
				
				ps.setString(1, advertPosition.getCharacteristicIdentification());
				ps.setString(2, advertPosition.getPositionName());
				ps.setString(3, advertPosition.getDescription());
				ps.setInt(4, advertPosition.getImageRuleId());
				ps.setInt(5, advertPosition.getVideoRuleId());
				ps.setInt(6,advertPosition.getTextRuleId());
				ps.setInt(7,advertPosition.getQuestionRuleId());
				ps.setInt(8,advertPosition.getIsHd());
				ps.setInt(9,advertPosition.getIsAdd());
				ps.setInt(10,advertPosition.getIsLoop());
				ps.setInt(11,advertPosition.getMaterialNumber());
				ps.setInt(12,advertPosition.getDeliveryMode());
				ps.setString(13,advertPosition.getPrice());
				ps.setString(14,advertPosition.getDiscount());
//---可能有错
				ps.setInt(15,Integer.parseInt(advertPosition.getOperationId()));
//---可能有错
				ps.setString(16,String.valueOf(CPSConstant.WAIT_BE_UPDATE_CATEGORY));
				ps.setDate(17, new Date(advertPosition.getCreateTime().getTime()));
				ps.setInt(18,advertPosition.getPositionTypeId());
				ps.setString(19,advertPosition.getBackgroundPath());
				ps.setString(20,advertPosition.getCoordinate());
				ps.setString(21,advertPosition.getDeliveryPlatform());
				ps.setString(22,advertPosition.getStartTime());
				ps.setString(23,advertPosition.getEndTime());
				ps.setString(24,advertPosition.getWidthHeight());
				
				ps.setInt(25, advertPosition.getId());
				
			}
			
		});
	}

	@Override
	public List<PlayCategory> queryCategoryBeanBySql(String queryPlayCategorySql) {
		return getJdbcTemplate().query(queryPlayCategorySql,new PlayCategory());
	}

	@Override
	public List<AdvertPosition> queryAdvertPositionBeanBySql(String queryAdvertPositionSql) {
		RowMapper rowMapper = BeanPropertyRowMapper.newInstance(AdvertPosition.class);
		return getJdbcTemplate().query(queryAdvertPositionSql,rowMapper);
	}

	@Override
	public int[] savePlayCategoryTemplateBatch(final List<PlayCategory> playCategoryList) {
		return getJdbcTemplate().batchUpdate(insertPlayCategorySql,new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return playCategoryList.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				PlayCategory playCategory = playCategoryList.get(i);
				
				ps.setInt(1, playCategory.getId());
				ps.setString(2,playCategory.getCategoryId());
				ps.setString(3,playCategory.getCategoryName());
				//类型
				ps.setString(4,playCategory.getCategoryType());
				ps.setString(5, playCategory.getTemplateId());
				ps.setString(6, playCategory.getTemplateName());
			//	ps.setTimestamp(7, playCategory.getCreateTime());
			//	ps.setTimestamp(8, playCategory.getModifyTime());
				ps.setTime(7, (Time)playCategory.getCreateTime());
				ps.setTime(8,(Time) playCategory.getCreateTime());
				
			}
			
		});
	}

	@Override
	public int[] updatePlayCategoryTemplateBatch(final List<PlayCategory> playCategoryList) {
		return getJdbcTemplate().batchUpdate(updatePlayCategorySql,new BatchPreparedStatementSetter(){
			@Override
			public int getBatchSize() {
				return playCategoryList.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)throws SQLException {
				PlayCategory playCategory = playCategoryList.get(i);
				
				ps.setString(1,playCategory.getCategoryId());
				ps.setString(2,playCategory.getCategoryName());
				//类型
				ps.setString(3,playCategory.getCategoryType());
				ps.setString(4, playCategory.getTemplateId());
				ps.setString(5, playCategory.getTemplateName());
		//		ps.setTimestamp(6, playCategory.getCreateTime());
		//		ps.setTimestamp(7, playCategory.getModifyTime());
				ps.setTime(6,(Time) playCategory.getCreateTime());
				ps.setTime(7,(Time) playCategory.getModifyTime());
				
				ps.setInt(8, playCategory.getId());
				
			}
			
		});
	}

	@Override
	public List<AdvertPosition> getAdvertPositionIdByEigenValue(String eigenValue) {
		StringBuffer queryPosition = new StringBuffer();
		queryPosition.append("SELECT * FROM T_ADVERTPOSITION");
		queryPosition.append(" WHERE CHARACTERISTIC_IDENTIFICATION =?");
		RowMapper rowMapper = BeanPropertyRowMapper.newInstance(AdvertPosition.class);
		return getJdbcTemplate().query(queryPosition.toString(),new Object[]{eigenValue},rowMapper);
	}

	@Override
	public int savePortalPlayCategory(final PortalPlayCategory portalPlayCategory) {
		String insertSql = "INSERT INTO T_PORTAL_PLAT_CATEGORY(ID,CATEGORY_ID,POSITION_ID) VALUES (T_ADVERTPOSITION_SEQ.nextval,?,?)";
		return getJdbcTemplate().update(insertSql, new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, portalPlayCategory.getCategoryId());
				ps.setInt(2, portalPlayCategory.getPositionId());
			}
			
		});
				
	}

	@Override
	public int updatePlayCategory(final PlayCategory playCategory) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE T_PLAT_CATEGORY").append(
				" SET CATEGORY_ID = ?,").append(
				"CATEGORY_NAME                 = ?,").append(
				"CATEGORY_TYPE             	   = ?,").append(
				"TEMPLATE_ID                   = ?,").append(
				"TEMPLATE_NAME                 = ?,").append(
				"CREATE_TIME                   = ?,").append(
	            "MODIFY_TIME                   =?" ).append(" WHERE ID = ?");
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, playCategory.getCategoryId());
						ps.setString(2, playCategory.getCategoryName());
						ps.setString(3, playCategory.getCategoryType());
						ps.setString(4, playCategory.getTemplateId());
						ps.setString(5, playCategory.getTemplateName());
					//	ps.setTimestamp(6, playCategory.getCreateTime());
					//	ps.setTimestamp(7,playCategory.getModifyTime());
						
						ps.setTime(6, (Time) playCategory.getCreateTime());
						ps.setTime(7,(Time) playCategory.getModifyTime());
						
						ps.setInt(8, playCategory.getId());
					}
				});
	}

	@Override
	public int savePlayCategory(PlayCategory playCategory) {
		return getJdbcTemplate().update(insertPlayCategorySql, new PlayCategory());
	}

	@Override
	public List<AdvertPosition> getAdvertPositionByAdvertiseWay() {
		
		StringBuffer sb = new StringBuffer("");
		
		sb.append("select * from T_ADVERTPOSITION t where t.delivery_platform ='PORTAL'");
		RowMapper rowMapper = BeanPropertyRowMapper.newInstance(AdvertPosition.class);
		return this.getJdbcTemplate().query(sb.toString(),rowMapper);
	}
	
	@Override
	public int removePlayCategory(final int playCategoryId) {
		String removeSql = "DELETE FROM T_PLAT_CATEGORY WHERE ID=?";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,playCategoryId);
			}
		});
	}

	@Override
	public int removePORTALByPlayCategoryId(final int playCategoryId) {
		String removeSql = "DELETE FROM T_PORTAL_PLAT_CATEGORY WHERE CATEGORY_ID=?";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,playCategoryId);
			}
		});
	}

	@Override
	public int removePORTALByAdvertPositionId(final int advertPositionId) {
		String removeSql = "DELETE FROM T_PORTAL_PLAT_CATEGORY WHERE POSITION_ID = ? ";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,advertPositionId);
			}
		});
	}

	@Override
	public List<PortalPlayCategory> findPORTALByCategoryId(final Integer categoryId) {
		StringBuffer queryPosition = new StringBuffer();
		queryPosition.append("SELECT * FROM T_PORTAL_PLAT_CATEGORY");
		queryPosition.append(" WHERE CATEGORY_ID=?");
		return getJdbcTemplate().query(queryPosition.toString(),new Object[]{categoryId},new PortalPlayCategory());
		
	}

	@Override
	public int updatePortalPlayCategory(final PortalPlayCategory portalPlayCategory) {
		
		String updateSql =" UPDATE T_PORTAL_PLAT_CATEGORY SET CATEGORY_ID=?,POSITION_ID=? WHERE ID = ?";
		
		return getJdbcTemplate().update(updateSql,new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setInt(1, portalPlayCategory.getCategoryId());
						ps.setInt(2, portalPlayCategory.getPositionId());
						ps.setInt(3, portalPlayCategory.getId());
					}
				});
	}

	@Override
	public List<AdvertPosition> getAdvertPositionByAdvertPositionTypeId(Integer id) {
		RowMapper rowMapper = BeanPropertyRowMapper.newInstance(AdvertPosition.class);
		StringBuffer queryPosition = new StringBuffer("");
		queryPosition.append("SELECT * FROM T_ADVERTPOSITION");
		queryPosition.append(" WHERE POSITION_TYPE_ID=?");
		return this.getJdbcTemplate().query(queryPosition.toString(),new Object[]{id},rowMapper);
	}

	@Override
	public int saveAdvertPositionTypeAndReturnId(final AdvertPositionType advertPositionType) {
		String querySequence= "SELECT T_POSITION_TYPE_SEQ.nextval FROM DUAL";
		Integer id = this.getJdbcTemplate().queryForInt(querySequence);
		advertPositionType.setId(id);
		String insertSql = "INSERT INTO T_POSITION_TYPE(ID,POSITION_TYPE_CODE,POSITION_TYPE_NAME) VALUES (?,?,?)";
		getJdbcTemplate().update(insertSql,new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps)throws SQLException {
				ps.setInt(1, advertPositionType.getId());
				ps.setString(2, advertPositionType.getPositionTypeCode());
				ps.setString(3, advertPositionType.getPositionTypeName());
			}
		});
		return id;
	}	
}
