package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.QuestionnaireSpecification;
/**
 * 调查问卷相关Service方法
 * @author lester
 *
 */
public interface QuestionnaireSpecificationService {
	/**
	 * 新增
	 * @param questionMaterialSpeci
	 * @return
	 */
	public Map<String,String> save(QuestionnaireSpecification object);
	/**
	 * 分页查询
	 * @param condition
	 * @param start
	 * @param end
	 * @return
	 */
	public List<QuestionnaireSpecification> page(Map condition, int start, int end);
	/**
	 * 查询总记录数
	 * @param condition
	 * @return
	 */
	public int queryCount(Map condition);
	/**
	 * 删除
	 * @param questionMaterialSpeciId
	 * @return
	 */
	public boolean remove(int objectId);
	/**
	 * 更新
	 * @param questionMaterialSpeci
	 * @return
	 */
	public boolean update(QuestionnaireSpecification object);
	/**
	 * 获取添加记录Id
	 * @param QuestionnaireSpecification
	 * @return
	 */
	public Integer getQuestionMaterialSpeciIdByCondition(QuestionnaireSpecification questionMaterialSpeci);
	
	public QuestionnaireSpecification get(Integer id);
}
