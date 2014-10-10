package com.dvnchina.advertDelivery.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.dao.QuestionnaireSpecificationDao;
import com.dvnchina.advertDelivery.model.QuestionnaireSpecification;

public class QuestionnaireSpecificationDaoImpl<T> extends JdbcDaoSupport implements
		QuestionnaireSpecificationDao {

	@Override
	public int remove(final int objectId) {
		String removeSql = "DELETE FROM T_QUESTIONNAIRE_SPECIFICATION WHERE ID=?";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,objectId);
			}
		});
	}

	@Override
	public List<QuestionnaireSpecification> page(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= ?) where rownum_ >?";
	RowMapper<QuestionnaireSpecification> rowMapper = BeanPropertyRowMapper.newInstance(QuestionnaireSpecification.class);  

	return getJdbcTemplate().query(pageSql, new Object[] { end, start },rowMapper);
	}

	@Override
	public int queryTotalCount(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}

	@Override
	public int save(final QuestionnaireSpecification object) {
		String insertSql = "INSERT INTO T_QUESTIONNAIRE_SPECIFICATION(ID,TYPE,FILE_SIZE,OPTION_NUMBER,MAX_LENGTH,EXCLUDE_CONTENT) VALUES(?,?,?,?,?,?)";
		return getJdbcTemplate().update(insertSql,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1,object.getId());
						ps.setString(2,object.getType());
						ps.setString(3,object.getFileSize());
						
						ps.setInt(4,object.getOptionNumber());
						ps.setInt(5,object.getMaxLength());
						ps.setString(6,object.getExcludeContent());
					}
				});
	}

	@Override
	public int update(final QuestionnaireSpecification object) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE T_QUESTIONNAIRE_SPECIFICATION SET TYPE=?,FILE_SIZE=?,OPTION_NUMBER=?,MAX_LENGTH=?,EXCLUDE_CONTENT=? WHERE ID=?");
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1,object.getType());
						ps.setString(2,object.getFileSize());
						ps.setInt(3,object.getOptionNumber());
						ps.setInt(4,object.getMaxLength());
						ps.setString(5,object.getExcludeContent());
						ps.setInt(6,object.getId());
					}
				});
	}

	@Override
	public List<QuestionnaireSpecification> query(String sql) {
		RowMapper<QuestionnaireSpecification> rowMapper = BeanPropertyRowMapper.newInstance(QuestionnaireSpecification.class);  
		return getJdbcTemplate().query(sql,rowMapper);
	}

	@Override
	public Integer getCurrentSequence() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T_QUESTIONNAIRE_SPECIF_SEQ.NEXTVAL FROM DUAL");
		return getJdbcTemplate().queryForInt(sql.toString());
	}
	
	@Override
	public QuestionnaireSpecification get(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_QUESTIONNAIRE_SPECIFICATION WHERE id=?");
		RowMapper<QuestionnaireSpecification> rowMapper = BeanPropertyRowMapper.newInstance(QuestionnaireSpecification.class);  
		QuestionnaireSpecification questionnaireSpecification= null;
		List<QuestionnaireSpecification> questionnaireSpecificationList = null;
		questionnaireSpecificationList=getJdbcTemplate().query(sql.toString(),new Object[]{id},rowMapper);
		
		if(questionnaireSpecificationList!=null&&questionnaireSpecificationList.size()>0){
			questionnaireSpecification=questionnaireSpecificationList.get(0);
		}
		
		return questionnaireSpecification;
	}
	
}
