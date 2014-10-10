package com.dvnchina.advertDelivery.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.dao.TextSpecificationDao;
import com.dvnchina.advertDelivery.model.TextSpecification;

public class TextSpecificationDaoImpl extends JdbcDaoSupport implements TextSpecificationDao{

	
	public int remove(final int objectId) {
		String removeSql = "DELETE FROM T_TEXT_SPECIFICATION WHERE ID=?";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,objectId);
			}
		});
	}

	
	public List<TextSpecification> page(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= ?) where rownum_ >?";
		RowMapper<TextSpecification> rowMapper = BeanPropertyRowMapper.newInstance(TextSpecification.class);
	return getJdbcTemplate().query(pageSql, new Object[] {end, start},rowMapper);
	}

	
	public int queryTotalCount(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}

	
	public int save(final 
			TextSpecification object) {
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("INSERT INTO T_TEXT_SPECIFICATION(ID,TEXT_DESC,TEXT_LENGTH,IS_LINK) VALUES (?,?,?,?)");
		return getJdbcTemplate().update(insertSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1,object.getId());
						ps.setString(2,object.getTextDesc());
						ps.setString(3,object.getTextLength());
						ps.setInt(4,object.getIsLink());
					}
				});
	}

	
	public int update(
			final TextSpecification object) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE T_TEXT_SPECIFICATION")
		         .append(" SET TEXT_DESC=?,")
		         .append(" TEXT_LENGTH=?,")
		         .append(" IS_LINK=?")
		         .append(" WHERE ID=?");
				
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1,object.getTextDesc());
						ps.setString(2,object.getTextLength());
						ps.setInt(3,object.getIsLink());
						ps.setInt(4,object.getId());
					}
				});
	}


	@Override
	public Integer getCurrentSequence() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T_TEXT_SPECIFICATION_SEQ.NEXTVAL FROM DUAL");
		return getJdbcTemplate().queryForInt(sql.toString());
	}


	@Override
	public TextSpecification get(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_TEXT_SPECIFICATION WHERE id=?");
		RowMapper<TextSpecification> rowMapper = BeanPropertyRowMapper.newInstance(TextSpecification.class);
		TextSpecification textSpecification = null;
		List<TextSpecification> textSpecificationList = null;
		textSpecificationList = getJdbcTemplate().query(sql.toString(), new Object[] {id},rowMapper);
		if(textSpecificationList!=null&&textSpecificationList.size()>0){
			textSpecification = textSpecificationList.get(0);
		}
		return textSpecification;
	}
}
