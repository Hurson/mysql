package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dvnchina.advertDelivery.model.VideoSpecification;
import com.dvnchina.advertDelivery.utils.page.PageBean;
/**
 * 视频规格相关Service方法
 * @author lester
 *
 */
public interface VideoSpecificationService {
	
	public List<VideoSpecification> page(Map condition, int start, int end);
	
	public int queryCount(Map condition);
	
	public boolean remove(int objectId);

	public boolean update(VideoSpecification object);

	public Map<String,String> save(VideoSpecification object);
	
	public PageBean page(HttpServletRequest request);
	
	public VideoSpecification get(Integer id);
}
