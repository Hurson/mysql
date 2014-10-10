package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.TextSpecification;
import com.dvnchina.advertDelivery.utils.page.PageBean;
/**
 * 文字规格相关Service操作
 * @author lester
 *
 */
public interface TextSpecificationService{
	/**
	 * 新增
	 * @param object
	 * @return
	 */
	public Map<String,String> save(TextSpecification object);
	/**
	 * 分页查询
	 * @param condition
	 * @param start
	 * @param end
	 * @return
	 */
	public List<TextSpecification> page(Map condition, int start, int end);
	/**
	 * 查询总记录数
	 * @param condition
	 * @return
	 */
	public int queryCount(Map condition);
	/**
	 * 删除
	 * @param  textSpecificationId
	 * @return
	 */
	public boolean remove(int objectId);
	/**
	 * 更新
	 * @param textSpecification
	 * @return
	 */
	public boolean update(TextSpecification object);
	/**
	 * 
	 * @return
	 */
	public PageBean page(HttpServletRequest request);
	
	public TextSpecification get(Integer id);
}
