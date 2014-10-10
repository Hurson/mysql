package com.dvnchina.advertDelivery.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.dao.PlayCategoryDao;
import com.dvnchina.advertDelivery.model.PlayCategory;

public class PlayCategoryDaoImpl extends JdbcDaoSupport implements PlayCategoryDao {

	@Override
	public boolean deletePlayCategory(PlayCategory playCategory) {
		return false;
	}

	@Override
	public List<PlayCategory> page(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= ?) where rownum_ >=?";
	return getJdbcTemplate().query(pageSql, new Object[] { end, start },
			new PlayCategory());
	}

	@Override
	public int queryTotalCount(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}

	@Override
	public int savePlayCategory(final PlayCategory playCategory) {
		String insertSql = "INSERT INTO T_PLAT_CATEGORY(ID,CATEGORY_ID,CATEGORY_NAME,CATEGORY_TYPE,TEMPLATE_ID,TEMPLATE_NAME,CREATE_TIME,MODIFY_TIME) VALUES(?,?,?,?,?,?,?,?)";
		return getJdbcTemplate().update(insertSql,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setInt(1, playCategory.getId());
						ps.setString(2, playCategory.getCategoryId());
						ps.setString(3, playCategory.getCategoryName());
						ps.setString(4,playCategory.getCategoryType());
						ps.setString(5, playCategory.getTemplateId());
						ps.setString(6, playCategory.getTemplateName());
						ps.setDate(7,new java.sql.Date(playCategory.getCreateTime().getTime()));
						ps.setDate(8,new java.sql.Date(playCategory.getModifyTime().getTime()));
					}
				});
	}

	@Override
	public int updatePlayCategory(final PlayCategory playCategory) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE T_PLAT_CATEGORY SET CATEGORY_ID=?,CATEGORY_NAME=?,CATEGORY_TYPE=?,TEMPLATE_ID=?,TEMPLATE_NAME=? ,CREATE_TIME=?,MODIFY_TIME=? WHERE ID=?");
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setString(1, playCategory.getCategoryId());
						ps.setString(2, playCategory.getCategoryName());
						ps.setString(3,playCategory.getCategoryType());
						ps.setString(4, playCategory.getTemplateId());
						ps.setString(5, playCategory.getTemplateName());
						ps.setDate(6,new java.sql.Date(playCategory.getCreateTime().getTime()));
						ps.setDate(7,new java.sql.Date(playCategory.getModifyTime().getTime()));
					//	ps.setTime(6,playCategory.getCreateTime());
					//	ps.setTime(7, (Time) playCategory.getModifyTime());
						ps.setInt(8, playCategory.getId());
					}
				});
	}

	@Override
	public List<PlayCategory> getAllPlayCategory() {
		StringBuffer sb = new StringBuffer("");
		sb.append("select * from T_PLAT_CATEGORY ");
		return getJdbcTemplate().query(sb.toString(), new PlayCategory());
	}

	@Override
	public int removePlayCategory(final Integer playCategoryId) {
		
		String removeSql = "DELETE FROM T_PLAT_CATEGORY WHERE ID=?";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,playCategoryId);
			}
		});
	}
}
