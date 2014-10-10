package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.AdvertPositionType;
import com.dvnchina.advertDelivery.model.PlayCategory;
import com.dvnchina.advertDelivery.model.PortalPlayCategory;

public interface CPSPositionDao {
	
	/**
	 * 新增
	 * @param advertPositionType
	 * @return
	 */
	public int saveAdvertPositionTypeAndReturnId(AdvertPositionType advertPositionType);
	
	
	
	/**
	 * 
	 * 根据广告位类型id 得到相应的记录
	 * 
	 */
	public List<AdvertPosition> getAdvertPositionByAdvertPositionTypeId(Integer id);
	
	/**
	 * 更新PORTAL信息
	 * 
	 */
	public int updatePortalPlayCategory(PortalPlayCategory portalPlayCategory);
	
	/**
	 * 通过CategoryId查询表单结果
	 * @param categoryId
	 * @return
	 */
	
	public List<PortalPlayCategory> findPORTALByCategoryId(Integer categoryId);
	
	
	/**
	 * 
	 * 通过投放方式，查询表单中的所有数据
	 * @return
	 */
	public List<AdvertPosition> getAdvertPositionByAdvertiseWay();
	
	
	/**
	 * 根据categoryId删除PORTAL投放节点
	 * @param advertPosition
	 * @return
	 */
	public int removePORTALByPlayCategoryId(int playCategoryId);
	
	/**
	 * 根据advertPositionId删除PORTAL投放节点
	 * @param advertPosition
	 * @return
	 */
	public int removePORTALByAdvertPositionId(int advertPositionId);
	
	/**
	 * 删除投放节点
	 * @param advertPosition
	 * @return
	 */
	public int removePlayCategory(int playCategoryId);
	
	/**
	 * 增加投放节点
	 * @param playCategory
	 * @return
	 */
	public int savePlayCategory(PlayCategory playCategory);
	
	
	/**
	 * 更新投放节点
	 * @param advertPosition
	 * @return
	 */
	public int updatePlayCategory(PlayCategory playCategory);	
	
	
	
	 /**
	 * 保存Portal投放节点信息
	 * @param advertPosition
	 * @return
	 */
	public int savePortalPlayCategory(PortalPlayCategory portalPlayCategory);
	
	
	/**
	 * 通过特征值查询广告位
	 * @param eigenValue
	 * @return
	 */
	public List<AdvertPosition> getAdvertPositionIdByEigenValue(String eigenValue);
	
	
	/**
	 * 根据查询条件直接对投放节点信息表单进行查询
	 * @return
	 */
	public List<AdvertPosition> queryAdvertPositionBeanBySql(String queryAdvertPositionSql);
	
	
	
	/**
	 * 根据查询条件直接对投放节点信息表单进行查询
	 * @return
	 */
	public List<PlayCategory> queryCategoryBeanBySql(String queryPlayCategorySql);
	
	
	/**
	 * 批量更新数据
	 * @param categoryBeanList
	 * @param operationType 1 修改 2 删除
	 * @return
	 */
	public int[] updateAdvertPositionTemplateBatch(List<AdvertPosition> listAdvertPosition,Integer operationType);
	
	/**
	 * 批量插入数据
	 * @param advertPositionList
	 * @return
	 */
	public int[] saveAdvertPositionTemplateBatch(List<AdvertPosition> advertPositionList);
	
	/**
	 * 批量插入数据
	 * @param playCategoryList
	 * @return
	 */
	public int[] savePlayCategoryTemplateBatch(List<PlayCategory> playCategoryList);
	/**
	 * 批量更新数据
	 * @param playCategoryList
	 * @return
	 */
	public int[] updatePlayCategoryTemplateBatch(List<PlayCategory> playCategoryList);

}
