package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.PlayCategory;

public interface PlayCategoryService {
	
	public String savePlayCategory(PlayCategory playCategory);
	
	@SuppressWarnings("unchecked")
	public List<PlayCategory> page(Map condition, int start, int end);
	
	public int queryCount(Map condition);
}
