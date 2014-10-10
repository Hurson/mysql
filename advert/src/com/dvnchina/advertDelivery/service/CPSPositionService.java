package com.dvnchina.advertDelivery.service;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import com.dvnchina.advertDelivery.bean.cpsPosition.CategoryBean;
import com.dvnchina.advertDelivery.model.AdvertPosition;
import com.dvnchina.advertDelivery.model.PlayCategory;
import com.dvnchina.advertDelivery.model.PortalPlayCategory;


/**
 * 根据从cps获取的分类和节点信息对数据库和缓存进行操作
 *
 */
public interface CPSPositionService {
	
	
	
    /**
	 * 保存Portal投放节点信息
	 * @param advertPosition
	 * @return
	 */
	public String savePortalPlayCategory(PortalPlayCategory portalPlayCategory);
	
	
	/**
	 * 通过特征值查询广告位
	 * @param eigenValue
	 * @return
	 */
	public List<AdvertPosition> getAdvertPositionIdByEigenValue(String eigenValue);
	
	
	/**
	 * 批量更新AdvertPosition数据
	 * @param categoryBeanList
	 * @return
	 */
	public int[] updateAdvertPositionList(List<AdvertPosition> advertPosition);
	
	
	/**
	 * 批量更新PlayCategory数据
	 * @param categoryBeanList
	 * @return
	 */
	public int[] updatePlayCategoryList(List<PlayCategory> playCategory);
	
	
	
	/**
	 * 批量保存PlayCategory数据
	 * @param categoryBeanList
	 * @return
	 */
	public int[] savePlayCategoryList(List<PlayCategory> playCategory);
	
	/**
	 * 批量保存AdvertPosition数据
	 * @param categoryBeanList
	 * @return
	 */
	public int[] saveAdvertPositionList(List<AdvertPosition> advertPosition);
	
	/**
	 * 从cps中获取数据
	 * @param remoteInterfaceUrl
	 * @return
	 */
	public Document getDocumentFromCps(String remoteInterfaceUrl);
	
	/**
	 * 查询待删除记录
	 * @return
	 */
	public List<CategoryBean> queryWaitBeDeleteCategory();
	
	/**
	 * 分段从cps中同步数据
	 * @param document
	 * @return
	 */
	public Map updateCategoryAndTemplateInfoStage(Document document);
	

}
