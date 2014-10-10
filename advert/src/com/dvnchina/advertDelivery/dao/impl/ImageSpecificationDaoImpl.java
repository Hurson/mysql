package com.dvnchina.advertDelivery.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.dao.ImageSpecificationDao;
import com.dvnchina.advertDelivery.model.ImageSpecification;

public class ImageSpecificationDaoImpl extends JdbcDaoSupport implements ImageSpecificationDao {

	@Override
	public int remove(
			final int objectId) {
		String removeSql = "DELETE FROM T_IMAGE_SPECIFICATION WHERE ID=?";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,objectId);
			}
		});
	}

	@Override
	public List<ImageSpecification> page(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= ?) where rownum_ >?";
	RowMapper<ImageSpecification> rowMapper = BeanPropertyRowMapper.newInstance(ImageSpecification.class);
	return getJdbcTemplate().query(pageSql, new Object[] { end, start },rowMapper);
	}

	@Override
	public int queryTotalCount(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}

	@Override
	public int save(
			final ImageSpecification object) {
		String insertSql = "INSERT INTO T_IMAGE_SPECIFICATION(ID,IMAGE_DESC,IMAGE_LENGTH,IMAGE_WIDTH,TYPE,FILE_SIZE,IS_LINK) VALUES(?,?,?,?,?,?,?)";
		return getJdbcTemplate().update(insertSql,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1,object.getId());
						ps.setString(2, object.getImageDesc());
						ps.setString(3, object.getImageLength());
						ps.setString(4, object.getImageWidth());
						ps.setString(5, object.getType());
						ps.setString(6, object.getFileSize());
						ps.setInt(7, object.getIsLink());
					}
				});
	}

	@Override
	public int update(
			final ImageSpecification object) {
		String insertSql = "UPDATE T_IMAGE_SPECIFICATION SET IMAGE_DESC=?,IMAGE_LENGTH=?,IMAGE_WIDTH=?,TYPE=?,FILE_SIZE=?,IS_LINK=? WHERE ID=?";
		return getJdbcTemplate().update(insertSql,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, object.getImageDesc());
						ps.setString(2, object.getImageLength());
						ps.setString(3, object.getImageWidth());
						ps.setString(4, object.getType());
						ps.setString(5, object.getFileSize());
						ps.setInt(6, object.getIsLink());
						ps.setInt(7, object.getId());
					}
				});
	}

	@Override
	public List<ImageSpecification> query(String sql) {
		RowMapper<ImageSpecification> rowMapper = BeanPropertyRowMapper.newInstance(ImageSpecification.class);
		getJdbcTemplate().query(sql,rowMapper);
		return null;
	}

	@Override
	public Integer getCurrentSequence() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T_IMAGE_SPECIFICATION_SEQ.NEXTVAL FROM DUAL");
		return getJdbcTemplate().queryForInt(sql.toString());
	}

	@Override
	public ImageSpecification get(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_IMAGE_SPECIFICATION WHERE id=?");
		RowMapper<ImageSpecification> rowMapper = BeanPropertyRowMapper.newInstance(ImageSpecification.class);
		ImageSpecification imageSpecification = null;
		List<ImageSpecification> imageSpecificationList = null;
		imageSpecificationList=getJdbcTemplate().query(sql.toString(),new Object[]{id},rowMapper);
		
		if(imageSpecificationList!=null&&imageSpecificationList.size()>0){
			imageSpecification= imageSpecificationList.get(0);
		}
		
		return imageSpecification;
	}

}
