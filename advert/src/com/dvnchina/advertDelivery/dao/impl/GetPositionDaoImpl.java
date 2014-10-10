package com.dvnchina.advertDelivery.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dvnchina.advertDelivery.dao.GetPositionDao;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.ImageSpecification;
import com.dvnchina.advertDelivery.model.TextSpecification;
import com.dvnchina.advertDelivery.model.VideoSpecification;

public class GetPositionDaoImpl extends JdbcDaoSupport implements GetPositionDao {

	@Override
	public List<AdvertPosition> getAllPosition() {
		String sql = "select * from t_advertposition";
		RowMapper<AdvertPosition> rowMapper = BeanPropertyRowMapper.newInstance(AdvertPosition.class);
		return getJdbcTemplate().query(sql,rowMapper);
	}
	
	@Override
	public List<ImageSpecification> getImageFileSpeci(Integer positionId) {
		String sql = "select ti.* from t_image_specification ti,t_advertposition ta where ti.id=ta.image_rule_id and ta.id="+positionId;
		RowMapper<ImageSpecification> rowMapper = BeanPropertyRowMapper.newInstance(ImageSpecification.class);
		return getJdbcTemplate().query(sql,rowMapper);
	}

	@Override
	public List<TextSpecification> getMessageSpeci(Integer positionId) {
		String sql = "select tt.* from t_text_specification tt,t_advertposition ta where tt.id=ta.text_rule_id and ta.id="+positionId;
		RowMapper<TextSpecification> rowMapper = BeanPropertyRowMapper.newInstance(TextSpecification.class);
		return getJdbcTemplate().query(sql, rowMapper);
	}

	@Override
	public List<VideoSpecification> getVideoFileSpeci(Integer positionId) {
		String sql = "select tm.* from t_video_specification tm,t_advertposition ta where tm.id=ta.video_rule_id and ta.id="+positionId;
		RowMapper<VideoSpecification> rowMapper = BeanPropertyRowMapper.newInstance(VideoSpecification.class);
		return getJdbcTemplate().query(sql,rowMapper);
	}

}
