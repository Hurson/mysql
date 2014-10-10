package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.VideoSpecification;

public interface VideoSpecificationDao {
	
	public int save(final VideoSpecification object);
	
	public int remove(final int objectId);
	
	public int update(final VideoSpecification object);
	
	public List<VideoSpecification> page(String sql, int start, int end);
	
	public int queryTotalCount(String sql);
	
	public List<VideoSpecification> query(String sql);
	
	public Integer getCurrentSequence();
	
	public VideoSpecification get(Integer id);
}
