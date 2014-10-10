package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.ImageSpecification;
/**
 * 图片规格相关Dao
 * @author lester
 *
 */
public interface ImageSpecificationDao {
	/**
	 * 新增
	 * @param pictureMaterialSpeci
	 * @return
	 */
	public int save(final ImageSpecification object);
	/**
	 * 删除
	 * @param pictureMaterialSpeciId
	 * @return
	 */
	public int remove(final int objectId);
	/**
	 * 更新
	 * @param pictureMaterialSpeci
	 * @return
	 */
	public int update(final ImageSpecification object);
	/**
	 * 分页查询
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ImageSpecification> page(String sql, int start, int end);
	/**
	 * 查询总数
	 * @param sql
	 * @return
	 */
	public int queryTotalCount(String sql);
	/**
	 * 条件查询
	 * @param sql
	 * @return
	 */
	public List<ImageSpecification> query(String sql);
	
	public Integer getCurrentSequence();
	
	public ImageSpecification get(Integer id);
}
