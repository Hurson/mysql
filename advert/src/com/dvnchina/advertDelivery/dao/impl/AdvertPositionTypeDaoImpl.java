package com.dvnchina.advertDelivery.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.dao.AdvertPositionTypeDao;
import com.dvnchina.advertDelivery.model.AdvertPositionType;

public class AdvertPositionTypeDaoImpl extends JdbcDaoSupport implements AdvertPositionTypeDao {

	@Override
	public int remove(final int objectId) {
		String removeSql = "DELETE FROM T_POSITION_TYPE WHERE ID=?";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,objectId);
			}
		});
	}

	@Override
	public List<AdvertPositionType> page(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= ?) where rownum_ >?";
		RowMapper<AdvertPositionType> rowMapper = BeanPropertyRowMapper.newInstance(AdvertPositionType.class);  
		return getJdbcTemplate().query(pageSql, new Object[] { end, start },rowMapper);
	}

	@Override
	public int queryTotalCount(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}

	@Override
	public int save(final AdvertPositionType object) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("INSERT INTO T_POSITION_TYPE(ID,POSITION_TYPE_CODE,POSITION_TYPE_NAME) VALUES(T_POSITION_TYPE_SEQ.nextval,?,?)");
				
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, object.getPositionTypeCode());
						ps.setString(2, object.getPositionTypeName());
					}
				});
	}

	@Override
	public int update(final AdvertPositionType object) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE T_POSITION_TYPE")
			     .append(" SET POSITION_TYPE_CODE = ?,")
			     .append("POSITION_TYPE_NAME=? ")
			     .append(" WHERE ID=?");
				
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, object.getPositionTypeCode());
						ps.setString(2, object.getPositionTypeName());
						ps.setInt(3,object.getId());
					}
				});
	}

	@Override
	public List<AdvertPositionType> query(String sql) {
		RowMapper<AdvertPositionType> rowMapper = BeanPropertyRowMapper.newInstance(AdvertPositionType.class);
		return getJdbcTemplate().query(sql,rowMapper);
	}
	
	@Override
	public AdvertPositionType get(Integer id) {
		String sql = "SELECT * FROM T_POSITION_TYPE WHERE ID=?";
		AdvertPositionType advertPositionType = null;
		List<AdvertPositionType> advertPositionTypeList = null;
		
		RowMapper<AdvertPositionType> rowMapper = BeanPropertyRowMapper.newInstance(AdvertPositionType.class);
		advertPositionTypeList = getJdbcTemplate().query(sql,new Object[]{id},rowMapper);
		
		if(advertPositionTypeList!=null&&advertPositionTypeList.size()>0){
			advertPositionType =  advertPositionTypeList.get(0);
		}
		return advertPositionType;
	}

}
