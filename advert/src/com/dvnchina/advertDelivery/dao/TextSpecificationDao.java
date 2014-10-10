package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.TextSpecification;
/**
 * 文字规格相关Dao操作
 * @author lester
 *
 */
public interface TextSpecificationDao{
	
	public int save(final TextSpecification object);
	
	public int remove(final int  objectId);
	
	public int update(final TextSpecification object);
	
	public List<TextSpecification> page(String sql, int start, int end);
    
	public int queryTotalCount(String sql);
	
	public Integer getCurrentSequence();
	
	public TextSpecification get(Integer id);
}
