package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.QuestionnaireSpecification;
/**
 * 调查问卷相关Dao操作
 * @author lester
 *
 */
public interface QuestionnaireSpecificationDao {
	/**
	 * 新增
	 * @param questionMaterialSpeci
	 * @return
	 */
	public int save(final QuestionnaireSpecification object);
	/**
	 * 删除
	 * @param questionMaterialSpeciId
	 * @return
	 */
	public int remove(final int  objectId);
	/**
	 * 更新
	 * @param questionMaterialSpeci
	 * @return
	 */
	public int update(QuestionnaireSpecification object);
	/**
	 * 分页查询
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public List<QuestionnaireSpecification> page(String sql, int start, int end);
	/**
	 * 统计总数
	 * @param sql
	 * @return
	 */
	public int queryTotalCount(String sql);
	/**
	 * 条件查询
	 * @param sql
	 * @return
	 */
	public List<QuestionnaireSpecification> query(String sql);
	
	public Integer getCurrentSequence();
	
	public QuestionnaireSpecification get(Integer id);
}
