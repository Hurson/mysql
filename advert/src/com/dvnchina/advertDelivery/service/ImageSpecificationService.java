package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.model.ImageSpecification;

public interface ImageSpecificationService {
	/**
	 * 新增
	 * @param pictureMaterialSpeci
	 * @return
	 */
	public Map<String,String> save(ImageSpecification object);
	/**
	 * 分页查询
	 * @param condition
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ImageSpecification> page(Map condition, int start, int end);
	/**
	 * 查询总记录数
	 * @param condition
	 * @return
	 */
	public int queryCount(Map condition);
	
	/**
	 * 删除
	 * @param pictureMaterialSpeciId
	 * @return
	 */
	public boolean remove(int objectId);
	/**
	 * 更新
	 * @param pictureMaterialSpeci
	 * @return
	 */
	public boolean update(ImageSpecification object);
	/**
	 * 获取添加记录Id
	 * @param pictureMaterialSpeci
	 * @return
	 */
	public Integer getPictureMaterialSpeciIdByCondition(ImageSpecification object);
	
	public ImageSpecification get(Integer id);
	
}
