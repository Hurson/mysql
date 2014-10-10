package com.dvnchina.advertDelivery.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.dao.VideoSpecificationDao;
import com.dvnchina.advertDelivery.model.VideoSpecification;

public class VideoSpecificationDaoImpl extends JdbcDaoSupport implements VideoSpecificationDao {

	@Override
	public int remove(
			final int objectId) {
		String removeSql = "DELETE FROM T_VIDEO_SPECIFICATION WHERE ID=?";
		return getJdbcTemplate().update(removeSql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1,objectId);
			}
		});
	}

	@Override
	public List<VideoSpecification> page(String sql, int start, int end) {
		String pageSql = "select * from (select row_.*, rownum rownum_ from ("
			+ sql + ") row_ where rownum <= ?) where rownum_ >?";
	RowMapper<VideoSpecification> rowMapper = BeanPropertyRowMapper.newInstance(VideoSpecification.class);  
	return getJdbcTemplate().query(pageSql, new Object[] { end, start },
			rowMapper);
	}

	@Override
	public int queryTotalCount(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}

	@Override
	public int save(final VideoSpecification object) {
		String insertSql = "INSERT INTO T_VIDEO_SPECIFICATION(ID,MOVIE_DESC,RESOLUTION,DURATION,TYPE,FILE_SIZE) VALUES(?,?,?,?,?,?)";
		return getJdbcTemplate().update(insertSql,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1,object.getId());
						ps.setString(2, object.getMovieDesc());
						ps.setString(3, object.getResolution());
						ps.setInt(4, object.getDuration());
						ps.setString(5, object.getType());
						ps.setString(6, object.getFileSize());
					}
				});
	}

	@Override
	public int update(final VideoSpecification object) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE T_VIDEO_SPECIFICATION SET MOVIE_DESC=?,RESOLUTION=?,DURATION=?,TYPE=?,FILE_SIZE=? WHERE ID=?");
		return getJdbcTemplate().update(updateSql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, object.getMovieDesc());
						ps.setString(2, object.getResolution());
						ps.setInt(3, object.getDuration());
						ps.setString(4, object.getType());
						ps.setString(5, object.getFileSize());
						ps.setInt(6, object.getId());;
					}
				});
	}

	@Override
	public List<VideoSpecification> query(String sql) {
		RowMapper<VideoSpecification> rowMapper = BeanPropertyRowMapper.newInstance(VideoSpecification.class); 
		return getJdbcTemplate().query(sql,rowMapper);
	}

	@Override
	public Integer getCurrentSequence() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T_MOVIE_SPECIFICATION_SEQ.NEXTVAL FROM DUAL");
		return getJdbcTemplate().queryForInt(sql.toString());
	}

	@Override
	public VideoSpecification get(Integer id) {
		RowMapper<VideoSpecification> rowMapper = BeanPropertyRowMapper.newInstance(VideoSpecification.class); 
		String sql = "SELECT * FROM T_VIDEO_SPECIFICATION WHERE ID=?";
		List<VideoSpecification>  videoSpecificationList = getJdbcTemplate().query(sql,new Object[]{id},rowMapper);
		VideoSpecification videoSpecification = null;
		
		if(videoSpecificationList!=null&&videoSpecificationList.size()>0){
			videoSpecification=videoSpecificationList.get(0);
		}
		return videoSpecification;	
	}

}
