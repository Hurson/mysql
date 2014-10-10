package com.dvnchina.advertDelivery.dao;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.model.ProductInfo;

public interface ProductDao {

	/**
	 * 从数据库实体表中查询产品及产品与资源关系信息,保存在Map中返回
	 * 
	 * @return 
	 *         封装的Map对象，key=>Product对象hashcode，value=>Map(key=>[PRODUCT|ASSETMAP]
	 *         ,value=[Product对象|Map(key=>AssetProduct对象的hashcode
	 *         ,value=>AssetProduct对象)]>
	 */
	public Map<Integer, Map<String, Object>> getProducts(); 
	
	/**
	 * 从数据库实体表中查询产品及产品与资源关系信息,保存在Map中返回
	 * 
	 * @param flag
	 *            标识（0-查询产品和资源关系时，不查询productid和assetid，1-查询productid和assetid）
	 * @return 
	 *         封装的Map对象，key=>Product对象hashcode，value=>Map(key=>[PRODUCT|ASSETMAP|
	 *         ASSETMAPFORBACKUP]
	 *         ,value=[Product对象|Map(key=>AssetProduct对象的hashcode
	 *         ,value=>AssetProduct对象)
	 *         |Map(key=>AssetProduct对象的id,value=>AssetProduct对象)]>
	 */
	public Map<Integer, Map<String, Object>> getProductsByPId(
			List<String> productIds);

	/**
	 * 批量插入产品
	 * 
	 * @param products
	 *            产品集合
	 * */
	public void insertProduct(final List<ProductInfo> products);

	/**
	 * 批量删除产品
	 * 
	 * @param ids
	 *            产品id的集合
	 * */
	public void deleteProduct(final List<Integer> ids);

	/**
	 * 批量更新产品
	 * 
	 * @param products
	 *            产品集合
	 * */
	public void updateProduct(final List<ProductInfo> products);

	/**
	 * 批量插入产品资源关系
	 * 
	 * @param assetProducts
	 *            产品和资源的关系数组
	 * */
	public void insertAssetProduct(final Object[] assetProducts); 
	/**
	 * 批量删除产品和资源的关系
	 * 
	 * @param ids
	 *            产品和资源关系记录id的集合
	 * */
	public void deleteAssetProduct(final List<Integer> ids);
	
	/**
	 * 根据产品编号，产品名称分页查询产品信息
	 * */
	public List<ProductInfo> getProducts(int begin,int end,String id,String name);
	
	/**
	 * 根据产品编号，产品名称查询产品数量
	 * */
	public int getProductCount(String id,String name);

}
